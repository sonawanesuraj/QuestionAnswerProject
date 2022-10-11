package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.dto.AnswerDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.IListAnswerDto;
import com.app.dto.SuccessResponseDto;
import com.app.exception.ResourceNotFoundException;
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
	private AnswerInterface answerInterface;

	@PostMapping()
	public ResponseEntity<?> addAnswer(@RequestBody AnswerDto answerDto, HttpServletRequest request) {

		try {
			answerDto = this.answerInterface.addAnswer(answerDto, request);
			return new ResponseEntity<>(
					new SuccessResponseDto("Answer Added Successfully", "Answer Added", "Data added"),
					HttpStatus.ACCEPTED);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Not Added"), HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateAnswer(@RequestBody AnswerDto answerDto, @PathVariable long id,
			HttpServletRequest request) throws Exception {
		try {
			answerDto = this.answerInterface.updateAnswer(id, answerDto, request);
			return new ResponseEntity<>(
					new SuccessResponseDto(" Answer updated sucessfully", "Answer updated !!", answerDto),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Not updated"), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAnswer(@PathVariable(name = "id") Long id, HttpServletRequest request)
			throws ResourceNotFoundException {
		try {
			answerInterface.deleteAnswer(id, request);
			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAnswerById(@PathVariable Long id) {
		try {
			List<IListAnswerDto> iListAnswerDto = this.answerInterface.getAnswerById(id);
			return new ResponseEntity<>(new SuccessResponseDto(" Answers:", "Success", iListAnswerDto), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Answer not found "),
					HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping()
	public ResponseEntity<?> getAllAnswer(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String pageSize) {
		Page<IListAnswerDto> Answer = answerInterface.getAllAnswer(search, pageNo, pageSize);

		if (Answer.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("All Answers", "Success", Answer.getContent()),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}

	@GetMapping("/QuestionAnswer/{id}")
	public ResponseEntity<?> getAnswerQuestionById(@PathVariable Long id) {
		try {
			List<IListAnswerDto> answerDto = this.answerInterface.getQuestionAnswerById(id);
			return new ResponseEntity<>(new SuccessResponseDto(" Answers:", "Success", answerDto), HttpStatus.OK);
		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Answer not found "),
					HttpStatus.BAD_REQUEST);

		}

	}
}
