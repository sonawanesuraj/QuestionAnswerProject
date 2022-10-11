package com.app.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.app.configuration.jwtTokenUtil;
import com.app.dto.AuthResponceDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.JwtRequestDto;
import com.app.dto.LoggerDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDto;
import com.app.entities.UserEntity;
import com.app.repository.AuthRepository;
import com.app.serviceImpl.AuthServiceImpl;
import com.app.serviceImpl.LoggerServiceImpl;
import com.app.serviceInterface.AuthInterface;
import com.app.util.PasswordValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private AuthInterface authInterface;

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@Autowired
	private jwtTokenUtil jwtTokenUtil;

	@Autowired
	private LoggerServiceImpl loggerServiceImpl;

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, HttpServletRequest request)
			throws Exception, DataIntegrityViolationException {

		String email = userDto.getEmail();
		String password = userDto.getPassword();

		if (PasswordValidator.isValidforEmail(email)) {

			if (PasswordValidator.isValid(password)) {

				UserEntity databaseName = authRepository.findByEmailContainingIgnoreCase(email);
				if (databaseName == null) {
					authInterface.addUser(userDto);
					return new ResponseEntity<>(new SuccessResponseDto("User Created", "userCreated", "data added"),
							HttpStatus.CREATED);

				} else {
					return new ResponseEntity<>(
							new ErrorResponseDto("User Email Id Already Exist", "userEmailIdAlreadyExist"),
							HttpStatus.BAD_REQUEST);
				}
			} else {

				return ResponseEntity.ok(new ErrorResponseDto(
						"Password should have Minimum 8 and maximum 50 characters, at least one uppercase letter, one lowercase letter, one number and one special character and No White Spaces",
						"Password validation not done"));
			}

		} else {
			return new ResponseEntity<>(new ErrorResponseDto("please check Email is not valid ", "Enter valid email"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDto createAuthenticationToken)
			throws Exception {
		try {

			UserEntity user = authRepository.findByEmailContainingIgnoreCase(createAuthenticationToken.getEmail());

			if (this.authServiceImpl.comparePassword(createAuthenticationToken.getPassword(), user.getPassword())) {
				final UserDetails userDetails = this.authServiceImpl
						.loadUserByUsername(createAuthenticationToken.getEmail());

				final UserEntity userEntity = this.authRepository
						.findByEmailContainingIgnoreCase(createAuthenticationToken.getEmail());

				final String token = this.jwtTokenUtil.generateToken(userDetails);

				LoggerDto loggerDto = new LoggerDto();

				loggerDto.setToken(token);

				Calendar calendar = Calendar.getInstance();

				calendar.add(Calendar.HOUR_OF_DAY, 5);

				loggerDto.setExpireAt(calendar.getTime());

				this.loggerServiceImpl.createLogger(loggerDto, userEntity);

				return ResponseEntity.ok(new SuccessResponseDto("Login Successfull", "token",
						new AuthResponceDto(token, userEntity.getEmail(), userEntity.getName(), userEntity.getId())));

			} else {
				return ResponseEntity.ok(new ErrorResponseDto("Invalid password ", "Please enter valid password"));

			}
		} catch (Exception e) {

			return ResponseEntity
					.ok(new ErrorResponseDto("Invalid email or Password", "Please enter valid email or password"));
		}

	}

}
