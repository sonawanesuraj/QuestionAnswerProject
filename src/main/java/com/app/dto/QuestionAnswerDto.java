package com.app.dto;

public class QuestionAnswerDto {
	private Long id;
	private Long questionId;
	private Long answerId;

	public QuestionAnswerDto() {
		super();
	}

	public QuestionAnswerDto(Long id, Long questionId, Long answerId) {
		super();
		this.id = id;
		this.questionId = questionId;
		this.answerId = answerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

}
