package com.app.dto;

public class AnswerDto {
	private String answer;
	private Long question;
	private boolean isDraft;

	public AnswerDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnswerDto(String answer, Long question, boolean isDraft) {
		super();
		this.answer = answer;
		this.question = question;
		this.isDraft = isDraft;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getQuestion() {
		return question;
	}

	public void setQuestion(Long question) {
		this.question = question;
	}

	public boolean isDraft() {
		return isDraft;
	}

	public void setDraft(boolean isDraft) {
		this.isDraft = isDraft;
	}

}
