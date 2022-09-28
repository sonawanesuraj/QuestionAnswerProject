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
@Table(name = "users")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE USERS SET is_active=false WHERE id=?")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_name ")
	private String name;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "is_active")
	private boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	@JsonBackReference
	List<UserRoleEntity> userRole;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	@JsonBackReference
	List<UserQuestionEntity> userQuestion;

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
//	@JsonBackReference
//	List<QuestionAnswerEntity> questionAnswer;

	public UserEntity() {
		super();
	}

	public UserEntity(Long id, String name, String email, String password, boolean isActive, Date createdAt,
			Date updatedAt, List<UserRoleEntity> userRole, List<UserQuestionEntity> userQuestion) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userRole = userRole;
		this.userQuestion = userQuestion;
		// this.questionAnswer = questionAnswer;

	}

	public List<UserRoleEntity> getUserRole() {
		return userRole;
	}

	public void setUserRole(List<UserRoleEntity> userRole) {
		this.userRole = userRole;
	}

	public List<UserQuestionEntity> getUserQuestion() {
		return userQuestion;
	}

	public void setUserQuestion(List<UserQuestionEntity> userQuestion) {
		this.userQuestion = userQuestion;
	}

//	public List<QuestionAnswerEntity> getQuestionAnswer() {
//		return questionAnswer;
//	}
//
//	public void setQuestionAnswer(List<QuestionAnswerEntity> questionAnswer) {
//		this.questionAnswer = questionAnswer;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
