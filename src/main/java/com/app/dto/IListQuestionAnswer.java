package com.app.dto;

public interface IListQuestionAnswer {

	public Long getId();

//
//	public IListAnswerDto getAnswer();
	public IListQuestionDto getQuestion();

	public IListAnswerDto getAnswer();
}
