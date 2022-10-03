package com.app.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class QuestionDto {
	private Long id;
	private String questionName;
	private String description;
	private boolean is_Draft;
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	private Date date;

	public QuestionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionDto(Long id, String questionName, String description, boolean is_Draft, Date date) {
		super();
		this.id = id;
		this.questionName = questionName;
		this.description = description;
		this.is_Draft = is_Draft;
		this.date = date;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
