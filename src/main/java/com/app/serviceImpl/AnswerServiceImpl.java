package com.app.serviceImpl;

import java.util.List;

import com.app.dto.AnswerDto;
import com.app.dto.IListAnswerDto;
import com.app.entities.AnswerEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AnswerRepository;
import com.app.serviceInterface.AnswerInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerInterface {

	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public AnswerDto addAnswer(AnswerDto answerDto) {
		AnswerEntity answerEntity = new AnswerEntity();
		answerEntity.setAnswer(answerDto.getAnswer());
		answerRepository.save(answerEntity);
		return answerDto;
	}

	@Override
	public AnswerDto updateAnswer(Long id, AnswerDto answerDto) {
		AnswerEntity answerEntity = this.answerRepository.findById(id).orElseThrow();
		answerEntity.setAnswer(answerDto.getAnswer());
		this.answerRepository.save(answerEntity);
		return answerDto;

	}

	@Override
	public void deleteAnswer(Long id) {
		this.answerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found for this Id "));
		answerRepository.deleteById(id);

	}

	@Override
	public List<IListAnswerDto> getAnswerById(Long id) {
		List<IListAnswerDto> iListAnswerDto;
		return iListAnswerDto = this.answerRepository.findById(id, IListAnswerDto.class);

	}

	@Override
	public Page<IListAnswerDto> getAllAnswer(String search, String pageNumber, String pageSize) {

		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListAnswerDto> iListAnswerDto;
		iListAnswerDto = answerRepository.findByOrderByIdAsc(paging, IListAnswerDto.class);

		return iListAnswerDto;
	}

}
