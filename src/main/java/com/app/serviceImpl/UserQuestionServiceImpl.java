package com.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.app.dto.IListUserQuestion;
import com.app.dto.UserQuestionDto;
import com.app.entities.QuestionEntity;
import com.app.entities.UserEntity;
import com.app.entities.UserQuestionEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.QuestionRepository;
import com.app.repository.UserQuestionRepository;
import com.app.repository.UserRepository;
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

}
