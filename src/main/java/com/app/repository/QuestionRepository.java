package com.app.repository;

import java.util.List;

import com.app.dto.IListQuestionDto;
import com.app.entities.QuestionEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

	QuestionEntity findByQuestionNameContainingIgnoreCase(String questionName);

	List<IListQuestionDto> findById(Long id, Class<IListQuestionDto> class1);

	Page<IListQuestionDto> findByOrderByIdAsc(Pageable paging, Class<IListQuestionDto> class1);

}
