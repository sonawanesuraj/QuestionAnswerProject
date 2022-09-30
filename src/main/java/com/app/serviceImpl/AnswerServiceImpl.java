package com.app.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.configuration.jwtTokenUtil;
import com.app.dto.AnswerDto;
import com.app.dto.IListAnswerDto;
import com.app.entities.AnswerEntity;
import com.app.entities.QuestionEntity;
import com.app.entities.UserEntity;
import com.app.entities.UserRoleEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AnswerRepository;
import com.app.repository.AuthRepository;
import com.app.repository.QuestionRepository;
import com.app.repository.UserRoleRepository;
import com.app.serviceInterface.AnswerInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerInterface {

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private jwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public AnswerDto addAnswer(AnswerDto answerDto, HttpServletRequest request) {

		AnswerEntity answerEntity = new AnswerEntity();
		answerEntity.setAnswer(answerDto.getAnswer());
		QuestionEntity questionEntity = questionRepository.findById(answerDto.getQuestion())
				.orElseThrow(() -> new ResourceNotFoundException());
		final String header = request.getHeader("Authorization");
		String requestToken = header.substring(7);
		final String email = jwtTokenUtil.getEmailFromToken(requestToken);
		UserEntity userEntity = authRepository.findByEmailContainingIgnoreCase(email);
		answerEntity.setUserId(userEntity.getId());
		answerEntity.setQuestion(questionEntity);
		answerRepository.save(answerEntity);
		return answerDto;
	}

	@Override
	public AnswerDto updateAnswer(Long id, AnswerDto answerDto, HttpServletRequest request) {
		AnswerEntity answerEntity = this.answerRepository.findById(id).orElseThrow();
		final String header = request.getHeader("Authorization");
		String requestToken = header.substring(7);
		final String email = jwtTokenUtil.getEmailFromToken(requestToken);
		UserEntity userEntity = authRepository.findByEmailContainingIgnoreCase(email);
		answerEntity.setUserId(userEntity.getId());
		answerEntity.setAnswer(answerDto.getAnswer());
		this.answerRepository.save(answerEntity);
		return answerDto;

	}

	@Override
	public void deleteAnswer(Long id, HttpServletRequest request) throws Exception {

		AnswerEntity answerEntity = this.answerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found for this Id "));

		final String header = request.getHeader("Authorization");
		String requestToken = header.substring(7);
		final String email = jwtTokenUtil.getEmailFromToken(requestToken);
		UserEntity userEntity = authRepository.findByEmailContainingIgnoreCase(email);
		Long userId = userEntity.getId();
		System.out.println(userId);
		if (answerEntity.getUserId().equals(id)) {

			answerRepository.deleteById(id);
			return;

		} else {
			List<UserRoleEntity> userRoleEntity1 = userRoleRepository.findByRole1(userId);
			for (int i = 0; i < userRoleEntity1.size(); i++) {
				String roleName = userRoleEntity1.get(i).getRole().getRoleName();
				if (roleName.equals("admin")) {
					answerRepository.deleteById(id);
					return;
				}

			}

			throw new Exception("Only Admin and User Can Access to Delete the Comment ");
		}
	}

	@Override
	public List<IListAnswerDto> getAnswerById(Long id) {
		List<IListAnswerDto> iListAnswerDto;
		return iListAnswerDto = this.answerRepository.findById(id, IListAnswerDto.class);

	}

	@Override
	public Page<IListAnswerDto> getAllAnswer(String search, String pageNumber, String pageSize) {

		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListAnswerDto> iListAnswerDto;
		iListAnswerDto = answerRepository.findByOrderByIdAsc(paging, IListAnswerDto.class);

		return iListAnswerDto;
	}

}
