package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IListQuestionAnswer;
import com.app.dto.QuestionAnswerDto;
import com.app.dto.SuccessResponseDto;
import com.app.exception.ResourceNotFoundException;
import com.app.serviceInterface.QuestionAnswerInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question-answer")
public class QuestionAnswerController {

	@Autowired
	private QuestionAnswerInterface questionAnswerInterface;

	@PostMapping()
	public ResponseEntity<?> addAnswerToQuestion(@RequestBody QuestionAnswerDto questionAnswerDto,
			HttpServletRequest request) {

		try {
			this.questionAnswerInterface.assignAnswerToQuestion(questionAnswerDto, request);

			return new ResponseEntity<>(new SuccessResponseDto("Assign Succefully", "Assigned", "added"),
					HttpStatus.OK);

		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Please Enter Valid Question OR Answer"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/{id}")
	public QuestionAnswerDto updateQuestionOrAnswer(@PathVariable Long id,
			@RequestBody QuestionAnswerDto questionAnswerDto) {
		return this.questionAnswerInterface.updateQuestionOrAnswer(id, questionAnswerDto);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteQuestionAnswer(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			questionAnswerInterface.deleteQuestionAnswer(id);

			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getQuestionAnswerById(@PathVariable Long id) {
		try {
			List<IListQuestionAnswer> iListQuestionAnswer = this.questionAnswerInterface.getQuestionAnswerById(id);
			return new ResponseEntity<>(new SuccessResponseDto(" Question-Answers:", "Success", iListQuestionAnswer),
					HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Question-Answer Not Found "),
					HttpStatus.BAD_REQUEST);

		}
	}

}
