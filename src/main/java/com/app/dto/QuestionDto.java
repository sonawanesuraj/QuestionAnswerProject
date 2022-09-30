package com.app.dto;

public class QuestionDto {
	private Long id;
	private String questionName;
	private String description;
	private boolean is_Draft;

	public QuestionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionDto(Long id, String questionName, String description, boolean is_Draft) {
		super();
		this.id = id;
		this.questionName = questionName;
		this.description = description;
		this.is_Draft = is_Draft;
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

	public boolean isIs_Draft() {
		return is_Draft;
	}

	public void setIs_Draft(boolean is_Draft) {
		this.is_Draft = is_Draft;
	}

}
