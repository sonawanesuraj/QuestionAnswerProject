package com.app.dto;

public class AnswerDto {
	private Long id;
	private String answer;

	public AnswerDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnswerDto(Long id, String answer) {
		super();
		this.id = id;
		this.answer = answer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
