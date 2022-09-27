package com.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.configuration.jwtTokenUtil;
import com.app.dto.IListQuestionAnswer;
import com.app.dto.QuestionAnswerDto;
import com.app.entities.AnswerEntity;
import com.app.entities.QuestionAnswerEntity;
import com.app.entities.QuestionEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AnswerRepository;
import com.app.repository.QuestionAnswerRepository;
import com.app.repository.QuestionRepository;
import com.app.repository.UserRepository;
import com.app.serviceInterface.QuestionAnswerInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerInterface {

	@Autowired
	private QuestionAnswerRepository questionAnswerRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private jwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;

	@Override
	public QuestionAnswerDto assignAnswerToQuestion(QuestionAnswerDto questionAnswerDto, HttpServletRequest request) {
		QuestionAnswerEntity questionAnswerEntity = this.questionAnswerRepository
				.findByQuestionAnswerId(questionAnswerDto.getQuestionId(), questionAnswerDto.getAnswerId());

		if (questionAnswerEntity == null) {
			QuestionEntity questionEntity = this.questionRepository.findById(questionAnswerDto.getQuestionId())
					.orElseThrow(() -> new ResourceNotFoundException("Question Not Found"));

			AnswerEntity answerEntity = this.answerRepository.findById(questionAnswerDto.getAnswerId())
					.orElseThrow(() -> new ResourceNotFoundException("Answerfinal  Does Not Found"));

			ArrayList<QuestionAnswerEntity> questionAnswerEntityList = new ArrayList<QuestionAnswerEntity>();

			QuestionAnswerEntity newQuestionAnswerEntity = new QuestionAnswerEntity();
			newQuestionAnswerEntity.setQuestion(questionEntity);
			newQuestionAnswerEntity.setAnswers(answerEntity);

			questionAnswerEntityList.add(newQuestionAnswerEntity);
			questionAnswerRepository.saveAll(questionAnswerEntityList);

		} else {
			throw new ResourceNotFoundException("resource already exists");

		}
		return questionAnswerDto;
	}

	@Override
	public QuestionAnswerDto updateQuestionOrAnswer(Long id, QuestionAnswerDto questionAnswerDto) {
		QuestionAnswerEntity questionAnswerEntity = this.questionAnswerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
		QuestionEntity questionEntity = this.questionRepository.findById(questionAnswerDto.getQuestionId())
				.orElseThrow(() -> new ResourceNotFoundException("Question Not Found"));

		AnswerEntity answerEntity = this.answerRepository.findById(questionAnswerDto.getAnswerId())
				.orElseThrow(() -> new ResourceNotFoundException("Answer Does Not Found"));
		questionAnswerEntity.setQuestion(questionEntity);
		questionAnswerEntity.setAnswers(answerEntity);
		this.questionAnswerRepository.save(questionAnswerEntity);
		return questionAnswerDto;

	}

	@Override
	public void deleteQuestionAnswer(Long id) {
		this.questionAnswerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found For this Id "));
		questionAnswerRepository.deleteById(id);

	}

	@Override
	public List<IListQuestionAnswer> getQuestionAnswerById(Long id) {

		List<IListQuestionAnswer> iListQuestionAnswer;
		return iListQuestionAnswer = this.questionAnswerRepository.findById(id, IListQuestionAnswer.class);

	}

}
