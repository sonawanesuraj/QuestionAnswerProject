package com.app.serviceInterface;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.dto.IListQuestionDto;
import com.app.dto.QuestionDto;

import org.springframework.data.domain.Page;

public interface QuestionInterface {

	public QuestionDto addQuestion(QuestionDto questionDto, HttpServletRequest request);

	public QuestionDto updateQuestion(Long id, QuestionDto questionDto);

	public void deleteQuestion(Long id);

	public List<IListQuestionDto> getQuestionById(Long id);

	public Page<IListQuestionDto> getAllQuestions(String search, String pageNumber, String pageSize);

	public Page<IListQuestionDto> getAllDraft(Boolean search, String pageNumber, String pageSize);

}
