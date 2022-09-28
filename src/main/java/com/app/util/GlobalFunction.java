package com.app.util;

import javax.servlet.http.HttpServletRequest;

import com.app.configuration.jwtTokenUtil;
import com.app.entities.UserEntity;
import com.app.repository.AuthRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class GlobalFunction {

	public static final String USER_ID = "User-Id";

	@Autowired
	static jwtTokenUtil jwtTokenUtil;

	@Autowired
	static AuthRepository authRepository;

	public static void getUserId(HttpServletRequest request) {
		final String header = request.getHeader("Authorization");
		String requestToken = header.substring(7);
		final String email = jwtTokenUtil.getEmailFromToken(requestToken);
		UserEntity userEntity = authRepository.findByEmailContainingIgnoreCase(email);
		request.setAttribute("User-Id", userEntity.getId());
	}
}
