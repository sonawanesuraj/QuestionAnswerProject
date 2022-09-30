package com.app.util;

import javax.servlet.http.HttpServletRequest;

import com.app.configuration.jwtTokenUtil;
import com.app.entities.UserEntity;
import com.app.repository.AuthRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class GlobalFunction {

	@Autowired
	static jwtTokenUtil jwtTokenUtil;

	@Autowired
	static AuthRepository authRepository;

	@Autowired
	static HttpServletRequest request;

	public static Long getUserId(Long userId) {
		final String header = request.getHeader("Authorization");
		String requestToken = header.substring(7);
		final String email = jwtTokenUtil.getEmailFromToken(requestToken);
		UserEntity userEntity = authRepository.findByEmailContainingIgnoreCase(email);
		System.out.println("TOKEN :" + requestToken);
		return userEntity.getId();

	}
}
