package com.app.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "question_answer")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE question_answer SET is_active=false WHERE id=?")
public class QuestionAnswerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private QuestionEntity question;

	@OneToOne(fetch = FetchType.LAZY)
	private AnswerEntity answer;

	private Boolean isActive = true;
	@CreationTimestamp
	private Date createdAt;
	@UpdateTimestamp
	private Date UpdatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	UserEntity user;

	public QuestionAnswerEntity() {
		super();
	}

	public QuestionAnswerEntity(Long id, QuestionEntity question, AnswerEntity answer, Boolean isActive, Date createdAt,
			Date updatedAt) {
		super();
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.isActive = isActive;
		this.createdAt = createdAt;
		UpdatedAt = updatedAt;
		this.user = user;
	}

	public AnswerEntity getAnswers() {
		return answer;
	}

	public void setAnswers(AnswerEntity answers) {
		this.answer = answers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuestionEntity getQuestion() {
		return question;
	}

	public void setQuestion(QuestionEntity question) {
		this.question = question;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return UpdatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		UpdatedAt = updatedAt;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
