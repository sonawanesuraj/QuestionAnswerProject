package com.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.app.dto.IListUserQuestion;
import com.app.entities.UserQuestionEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestionEntity, Long> {

	@Query(value = "select * from user_question as u where u.user_id=:userId AND u.question_id=:questionId", nativeQuery = true)
	UserQuestionEntity findByuserQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE user_question u SET question_id=:id2 WHERE u.user_id=:id", nativeQuery = true)
	void updateUserRole(Long id, Long id2);

	List<IListUserQuestion> findById(Long id, Class<IListUserQuestion> class1);

	Page<IListUserQuestion> findByOrderByIdAsc(Pageable paging, Class<IListUserQuestion> class1);

}
