package com.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.app.dto.IListQuestionAnswer;
import com.app.entities.QuestionAnswerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswerEntity, Long> {

	@Query(value = "select * from question_answer as q where q.question_id=:questionId AND q.answer_id=:answerId", nativeQuery = true)
	QuestionAnswerEntity findByQuestionAnswerId(@Param("questionId") Long questionId, @Param("answerId") Long answerId);

	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE question_answer q SET answer_id=:id2 WHERE q.question_id=:id", nativeQuery = true)
	void updateQuestionAnswer(Long id, Long id2);

	List<IListQuestionAnswer> findById(Long id, Class<IListQuestionAnswer> class1);

}
