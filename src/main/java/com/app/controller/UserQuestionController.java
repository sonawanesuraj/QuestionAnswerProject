package com.app.controller;

import java.util.List;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IListUserQuestion;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserQuestionDto;
import com.app.exception.ResourceNotFoundException;
import com.app.serviceImpl.UserQuestionServiceImpl;

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
@RequestMapping("/userQuestion")
public class UserQuestionController {

	@Autowired
	private UserQuestionServiceImpl userQuestionServiceImpl;

	@PostMapping()
	public ResponseEntity<?> addUserQuestion(@RequestBody UserQuestionDto userQuestionDto) {
		try {

			UserQuestionDto dto = this.userQuestionServiceImpl.addUserQuestion(userQuestionDto);

			return new ResponseEntity<>(new SuccessResponseDto("Assign Succefully", "Assigned", "added"),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Please Enter Valid User OR Question"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/{id}")
	public UserQuestionDto updateUserQuestion(@PathVariable Long id, @RequestBody UserQuestionDto userQuestionDto) {
		return this.userQuestionServiceImpl.updateUserOrQuestion(id, userQuestionDto);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserQuestion(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			userQuestionServiceImpl.deleteUserQuestion(id);

			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserQuestionById(@PathVariable Long id) {
		try {

			List<IListUserQuestion> iListUserQuestion = this.userQuestionServiceImpl.getUserQuestionById(id);
			return new ResponseEntity<>(iListUserQuestion, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print("Not Found...");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping()
	public ResponseEntity<?> getAllUserQuestion(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String PageSize) {
		Page<IListUserQuestion> userQuestion = userQuestionServiceImpl.getAllUserQuestion(search, pageNo, PageSize);

		if (userQuestion.getTotalElements() != 0) {
			return new ResponseEntity<>(
					new SuccessResponseDto("All UserQuestion", "Success", userQuestion.getContent()), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}
}
