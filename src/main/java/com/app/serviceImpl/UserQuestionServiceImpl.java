package com.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.configuration.jwtTokenUtil;
import com.app.dto.IListUserQuestion;
import com.app.dto.UserQuestionDto;
import com.app.entities.QuestionEntity;
import com.app.entities.UserEntity;
import com.app.entities.UserQuestionEntity;
import com.app.entities.UserRoleEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AuthRepository;
import com.app.repository.QuestionRepository;
import com.app.repository.UserQuestionRepository;
import com.app.repository.UserRepository;
import com.app.repository.UserRoleRepository;
import com.app.serviceInterface.UserQuestionInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserQuestionServiceImpl implements UserQuestionInterface {

	@Autowired
	private UserQuestionRepository userQuestionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private jwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserQuestionDto addUserQuestion(UserQuestionDto userQuestionDto) {

		UserQuestionEntity userQuestionEntity = this.userQuestionRepository
				.findByuserQuestionId(userQuestionDto.getUserId(), userQuestionDto.getQuestionId());

		if (userQuestionEntity == null) {
			UserEntity userEntity = this.userRepository.findById(userQuestionDto.getUserId())
					.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

			QuestionEntity questionEntity = this.questionRepository.findById(userQuestionDto.getQuestionId())
					.orElseThrow(() -> new ResourceNotFoundException("Question Does Not Found"));

			ArrayList<UserQuestionEntity> userQuestionEntityList = new ArrayList<UserQuestionEntity>();

			UserQuestionEntity newUserQuestionEntity = new UserQuestionEntity();
			newUserQuestionEntity.setUser(userEntity);
			newUserQuestionEntity.setQuestion(questionEntity);
			userQuestionEntityList.add(newUserQuestionEntity);
			userQuestionRepository.saveAll(userQuestionEntityList);

		} else {
			throw new ResourceNotFoundException("resource already exists");

		}
		return userQuestionDto;

	}

	@Override
	public UserQuestionDto updateUserOrQuestion(Long id, UserQuestionDto userQuestionDto) {

		UserQuestionEntity userQuestionEntity = this.userQuestionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
		UserEntity userEntity = this.userRepository.findById(userQuestionDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

		QuestionEntity questionEntity = this.questionRepository.findById(userQuestionDto.getQuestionId())
				.orElseThrow(() -> new ResourceNotFoundException("Question Does Not Found"));
		userQuestionEntity.setUser(userEntity);
		userQuestionEntity.setQuestion(questionEntity);
		this.userQuestionRepository.save(userQuestionEntity);
		return userQuestionDto;
	}

	@Override
	public void deleteUserQuestion(Long id) {
		this.userQuestionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found For this Id "));
		userQuestionRepository.deleteById(id);
	}

	@Override
	public List<IListUserQuestion> getUserQuestionById(Long id) {
		List<IListUserQuestion> iListUserQuestion;
		return iListUserQuestion = this.userQuestionRepository.findById(id, IListUserQuestion.class);

	}

	@Override
	public Page<IListUserQuestion> getAllUserQuestion(String search, String pageNumber, String pageSize) {
		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListUserQuestion> iListUserQuestion;
		iListUserQuestion = userQuestionRepository.findByOrderByIdAsc(paging, IListUserQuestion.class);

		return iListUserQuestion;

	}

	@Override
	public List<IListUserQuestion> filterAllUserRecord(Long id, HttpServletRequest request) throws Exception {
		List<IListUserQuestion> iListUserQuestion;

		final String header = request.getHeader("Authorization");
		String requestToken = header.substring(7);
		final String email = jwtTokenUtil.getEmailFromToken(requestToken);
		UserEntity userEntity = authRepository.findByEmailContainingIgnoreCase(email);
		Long userId = userEntity.getId();
		UserRoleEntity userRoleEntity = userRoleRepository.findRoleIdByUserId(userId);
		String userRole = userRoleEntity.getRole().getRoleName();

		if (userRole.equals("admin")) {

			iListUserQuestion = this.userQuestionRepository.findQuationIdByUserId(id, IListUserQuestion.class);

			return iListUserQuestion;

		} else {
			throw new Exception("this user is not a admin , only admin have a access to see users information");
		}

	}
}
