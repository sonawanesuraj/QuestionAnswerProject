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

	Page<IListQuestionDto> findByOrderByIdAsc(Pageable page, Class<IListQuestionDto> iListQuestionDto);

	Page<IListQuestionDto> findByQuestionName(String search, Pageable paging, Class<IListQuestionDto> class1);

	List<IListQuestionDto> findById(Long id, Class<IListQuestionDto> class1);

	Page<IListQuestionDto> findByIsDraftOrderByIdAsc(Boolean search, Pageable paging, Class<IListQuestionDto> class1);

}
