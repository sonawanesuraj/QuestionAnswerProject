package com.app.dto;

public class UserQuestionDto {
	private Long id;
	private Long userId;
	private Long questionId;

	public UserQuestionDto() {
		super();
	}

	public UserQuestionDto(Long id, Long userId, Long questionId) {
		super();
		this.id = id;
		this.userId = userId;
		this.questionId = questionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}
