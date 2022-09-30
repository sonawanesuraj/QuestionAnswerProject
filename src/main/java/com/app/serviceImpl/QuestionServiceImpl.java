package com.app.serviceImpl;

import java.util.List;

import com.app.dto.IListQuestionDto;
import com.app.dto.QuestionDto;
import com.app.entities.QuestionEntity;
import com.app.exception.ResourceNotFoundException;
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

	@Override
	public QuestionDto addQuestion(QuestionDto questionDto) {
		QuestionEntity questionEntity = new QuestionEntity();
		questionEntity.setQuestionName(questionDto.getQuestionName());
		questionEntity.setDescription(questionDto.getDescription());
		questionEntity.setDraft(questionDto.isIs_Draft());
		this.questionRepository.save(questionEntity);
		return questionDto;

	}

	@Override
	public QuestionDto updateQuestion(Long id, QuestionDto questionDto) {
		QuestionEntity questionEntity = this.questionRepository.findById(id).orElseThrow();
		questionEntity.setQuestionName(questionDto.getQuestionName());
		questionEntity.setDescription(questionDto.getDescription());
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
