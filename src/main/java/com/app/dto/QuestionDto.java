package com.app.dto;

public class QuestionDto {
	private Long id;
	private String questionName;
	private String description;

	public QuestionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionDto(Long id, String questionName, String description) {
		super();
		this.questionName = questionName;
		this.description = description;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
