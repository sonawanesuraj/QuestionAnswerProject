package com.app.dto;

public interface IListAnswerDto {
	public Long getId();

	public Long getUserId();

	public IListQuestionDto getQuestion();

	public String getAnswer();

}
