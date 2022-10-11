package com.app.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.configuration.jwtTokenUtil;
import com.app.dto.IListQuestionDto;
import com.app.dto.QuestionDto;
import com.app.entities.QuestionEntity;
import com.app.entities.UserEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AnswerRepository;
import com.app.repository.AuthRepository;
import com.app.repository.QuestionRepository;
import com.app.serviceInterface.QuestionInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionInterface {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	private jwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthRepository authRepository;

	@Override
	public QuestionDto addQuestion(QuestionDto questionDto, HttpServletRequest request) {
		QuestionEntity questionEntity = new QuestionEntity();
		final String header = request.getHeader("Authorization");
		String requestToken = header.substring(7);
		final String email = jwtTokenUtil.getEmailFromToken(requestToken);
		UserEntity userEntity = authRepository.findByEmailContainingIgnoreCase(email);
		Long userId = userEntity.getId();
		questionEntity.setUserId(userId);
		questionEntity.setQuestionName(questionDto.getQuestionName());
		questionEntity.setDescription(questionDto.getDescription());
		questionEntity.setDraft(questionDto.isIs_Draft());
		questionEntity.setDate(questionDto.getDate());
		this.questionRepository.save(questionEntity);
		return questionDto;

	}

	@Override
	public QuestionDto updateQuestion(Long id, QuestionDto questionDto) {
		QuestionEntity questionEntity = this.questionRepository.findById(id).orElseThrow();
		questionEntity.setQuestionName(questionDto.getQuestionName());
		questionEntity.setDescription(questionDto.getDescription());
		questionEntity.setFlag(true);
		this.questionRepository.save(questionEntity);
		return questionDto;

	}

	@Override
	public void deleteQuestion(Long id) {
		this.questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found for this Id "));
		questionRepository.deleteById(id);

	}

	@Override
	public List<IListQuestionDto> getQuestionById(Long id) {
		List<IListQuestionDto> iListQuestionDto;
		return iListQuestionDto = this.questionRepository.findById(id, IListQuestionDto.class);
	}

	@Override
	public Page<IListQuestionDto> getAllQuestions(String search, String pageNumber, String pageSize) {

		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListQuestionDto> iListQuestionDto;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			iListQuestionDto = questionRepository.findByOrderByIdAsc(paging, IListQuestionDto.class);

		} else {

			iListQuestionDto = questionRepository.findByQuestionName(search, paging, IListQuestionDto.class);

		}

		return iListQuestionDto;

	}

	@Override
	public Page<IListQuestionDto> getAllDraft(Boolean search, String pageNumber, String pageSize) {
		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListQuestionDto> iListQuestionDto;
		iListQuestionDto = questionRepository.findByIsDraftOrderByIdAsc(true, paging, IListQuestionDto.class);

		return iListQuestionDto;

	}

}
