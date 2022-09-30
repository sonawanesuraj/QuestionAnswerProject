package com.app.serviceInterface;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.dto.AnswerDto;
import com.app.dto.IListAnswerDto;

import org.springframework.data.domain.Page;

public interface AnswerInterface {

	public AnswerDto updateAnswer(Long id, AnswerDto answerDto, HttpServletRequest request);

	public void deleteAnswer(Long id, HttpServletRequest request) throws Exception;

	public List<IListAnswerDto> getAnswerById(Long id);

	Page<IListAnswerDto> getAllAnswer(String search, String pageNumber, String pageSize);

	AnswerDto addAnswer(AnswerDto answerDto, HttpServletRequest request);

}
