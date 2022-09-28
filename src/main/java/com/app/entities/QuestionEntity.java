package com.app.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "questions")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE Questions SET is_active=false WHERE id=?")
public class QuestionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "question_name")
	private String questionName;

	@Column(name = "question_description")
	private String description;

	@Column(name = "is_active")
	private boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL)
	@JsonBackReference
	List<UserQuestionEntity> userQuestion;

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL)
//	@JsonBackReference
//	List<QuestionAnswerEntity> questionAnswer;

	public QuestionEntity() {
		super();
	}

	public QuestionEntity(Long id, String questionName, String description, boolean isActive, Date createdAt,
			Date updatedAt, List<UserQuestionEntity> userQuestion) {
		super();
		this.id = id;
		this.questionName = questionName;
		this.description = description;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userQuestion = userQuestion;
//		this.questionAnswer = questionAnswer;
	}

//	public List<QuestionAnswerEntity> getQuestionAnswer() {
//		return questionAnswer;
//	}
//
//	public void setQuestionAnswer(List<QuestionAnswerEntity> questionAnswer) {
//		this.questionAnswer = questionAnswer;
//	}

	public List<UserQuestionEntity> getUserQuestion() {
		return userQuestion;
	}

	public void setUserQuestion(List<UserQuestionEntity> userQuestion) {
		this.userQuestion = userQuestion;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
