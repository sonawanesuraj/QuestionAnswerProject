package com.app.serviceInterface;

import java.util.List;

import com.app.dto.AnswerDto;
import com.app.dto.IListAnswerDto;

import org.springframework.data.domain.Page;

public interface AnswerInterface {

	AnswerDto addAnswer(AnswerDto answerDto);

	public AnswerDto updateAnswer(Long id, AnswerDto answerDto);

	public void deleteAnswer(Long id);

	public List<IListAnswerDto> getAnswerById(Long id);

	Page<IListAnswerDto> getAllAnswer(String search, String pageNumber, String pageSize);

}