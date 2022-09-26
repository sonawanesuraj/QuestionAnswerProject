package com.app.controller;

import java.util.List;

import com.app.dto.AnswerDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.IListAnswerDto;
import com.app.dto.SuccessResponseDto;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AnswerRepository;
import com.app.serviceImpl.AnswerServiceImpl;
import com.app.serviceInterface.AnswerInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
public class AnswerController {

	@Autowired
	private AnswerServiceImpl answerServiceImpl;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private AnswerInterface answerInterface;

	@PostMapping()
	public ResponseEntity<?> addAnswer(@RequestBody AnswerDto answerDto) {

		try {
			answerDto = this.answerInterface.addAnswer(answerDto);
			return new ResponseEntity<>(
					new SuccessResponseDto("Answer Added Successfully", "Answer Added", "Data added"),
					HttpStatus.ACCEPTED);
		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Answer already exist", "Please add new Answer"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateAnswer(@RequestBody AnswerDto answerDto, @PathVariable long id) {
		try {
			answerDto = this.answerInterface.updateAnswer(id, answerDto);
			return new ResponseEntity<>(
					new SuccessResponseDto(" Answer updated sucessfully", "Answer updated !!", answerDto),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("Answer Id Not Found  ", "Something went wrong"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteQuestion(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			answerServiceImpl.deleteAnswer(id);
			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAnswerById(@PathVariable Long id) {
		try {
			List<IListAnswerDto> iListAnswerDto = this.answerServiceImpl.getAnswerById(id);
			return new ResponseEntity<>(new SuccessResponseDto(" Answers:", "Success", iListAnswerDto), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Answer not found "),
					HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping()
	public ResponseEntity<?> getAllUserRole(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String PageSize) {
		Page<IListAnswerDto> Answer = answerServiceImpl.getAllAnswer(search, pageNo, PageSize);

		if (Answer.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("All Answers", "Success", Answer.getContent()),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}
}