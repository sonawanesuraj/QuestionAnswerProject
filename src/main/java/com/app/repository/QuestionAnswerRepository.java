package com.app.repository;

import com.app.entities.QuestionAnswerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswerEntity, Long> {

}
