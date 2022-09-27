package com.app.serviceInterface;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.dto.IListQuestionAnswer;
import com.app.dto.QuestionAnswerDto;

public interface QuestionAnswerInterface {

	public QuestionAnswerDto assignAnswerToQuestion(QuestionAnswerDto questionAnswerDto, HttpServletRequest request);

	public QuestionAnswerDto updateQuestionOrAnswer(Long id, QuestionAnswerDto questionAnswerDto);

	public void deleteQuestionAnswer(Long id);

	public List<IListQuestionAnswer> getQuestionAnswerById(Long id);

}
