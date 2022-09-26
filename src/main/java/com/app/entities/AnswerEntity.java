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
@Table(name = "answer")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE ANSWER SET is_active=false WHERE id=?")
public class AnswerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "answer")
	private String answer;

	@Column(name = "is_active")
	private boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date CreatedAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date UpdatedAt;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "answer", cascade = CascadeType.ALL)
	@JsonBackReference
	List<QuestionAnswerEntity> answers;

	public AnswerEntity() {
		super();
	}

	public AnswerEntity(Long id, String answer, boolean isActive, Date createdAt, Date updatedAt,
			List<QuestionAnswerEntity> answers) {
		super();
		this.id = id;
		this.answer = answer;
		this.isActive = isActive;
		CreatedAt = createdAt;
		UpdatedAt = updatedAt;
		this.answers = answers;
	}

	public List<QuestionAnswerEntity> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionAnswerEntity> answers) {
		this.answers = answers;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedAt() {
		return CreatedAt;
	}

	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}

	public Date getUpdatedAt() {
		return UpdatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		UpdatedAt = updatedAt;
	}

}
