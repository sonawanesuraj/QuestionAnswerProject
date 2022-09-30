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

}
