package com.app.controller;

import java.util.List;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IListQuestionDto;
import com.app.dto.QuestionDto;
import com.app.dto.SuccessResponseDto;
import com.app.entities.QuestionEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.QuestionRepository;
import com.app.serviceImpl.QuestionServiceImpl;
import com.app.serviceInterface.QuestionInterface;

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
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionInterface questionInterface;

	@Autowired
	private QuestionServiceImpl questionServiceImpl;

	@PostMapping()
	public ResponseEntity<?> addQuestion(@RequestBody QuestionDto questionDto) {

		try {
			QuestionEntity questionEntity = this.questionRepository
					.findByQuestionNameContainingIgnoreCase(questionDto.getQuestionName());
			if (questionEntity == null) {
				QuestionDto questionDto1 = this.questionInterface.addQuestion(questionDto);
				return new ResponseEntity<>(
						new SuccessResponseDto("Question Added Successfully", "Question Added", "Data added"),
						HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(new ErrorResponseDto("Question already exist", "Please add new Question"),
						HttpStatus.BAD_REQUEST);
			}

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.ok(new ErrorResponseDto("Enter valid Question", "Invalid Question"));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateQuestion(@RequestBody QuestionDto questionDto, @PathVariable long id) {
		try {
			questionDto = this.questionInterface.updateQuestion(id, questionDto);
			return new ResponseEntity<>(
					new SuccessResponseDto(" Question updated sucessfully", "Question updated !!", questionDto),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("Question Id Not Found  ", "Something went wrong"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteQuestion(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			questionServiceImpl.deleteQuestion(id);
			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
		try {
			List<IListQuestionDto> iListUserRole = this.questionServiceImpl.getQuestionById(id);
			return new ResponseEntity<>(new SuccessResponseDto(" Questions:", "Success", iListUserRole), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Questions not found "),
					HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping()
	public ResponseEntity<?> getAllQuestions(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String PageSize) {
		Page<IListQuestionDto> question = questionServiceImpl.getAllQuestions(search, pageNo, PageSize);

		if (question.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("All Questions", "Success", question.getContent()),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}
}
