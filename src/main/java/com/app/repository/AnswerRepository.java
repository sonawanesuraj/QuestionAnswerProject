package com.app.repository;

import java.util.List;

import com.app.dto.IListAnswerDto;
import com.app.entities.AnswerEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

	List<IListAnswerDto> findById(Long id, Class<IListAnswerDto> class1);

	Page<IListAnswerDto> findByOrderByIdAsc(Pageable paging, Class<IListAnswerDto> class1);

	AnswerEntity findUserIdById(Long id);

	Page<IListAnswerDto> findByAnswer(String search, Pageable paging, Class<IListAnswerDto> class1);

	List<IListAnswerDto> findByQuestionId(Long id, Class<IListAnswerDto> class1);

//	@Transactional
//	@Query(value = "select a.user_id,a.answer from answer a where a.question_id=:question_id", nativeQuery = true)
//	List<AnswerDto> findByAnswerAndQuestionId(@Param("question_id") Long question_id);

//	@Transactional
//	@Query(value = "select a.user_id,a.answer from answer a where a.question_id=:question_id", nativeQuery = true)
//	List<IListAnswerDto> findIdByQuestion(@Param("question_id") Long questionId, Class<IListAnswerDto> class1);
}
