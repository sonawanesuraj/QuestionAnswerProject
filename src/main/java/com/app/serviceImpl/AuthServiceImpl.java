package com.app.serviceImpl;

import java.util.ArrayList;

import com.app.dto.UserDto;
import com.app.entities.UserEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AuthRepository;
import com.app.serviceInterface.AuthInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthInterface, UserDetailsService {

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void addUser(UserDto user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setName(user.getName());
		userEntity.setEmail(user.getEmail());
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		authRepository.save(userEntity);

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = this.authRepository.findByEmailContainingIgnoreCase(email);

		if (userEntity == null) {
			throw new ResourceNotFoundException("User OR Password not found");
		}

		return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(),
				getAuthority(userEntity));
	}

	// for compare password
	public Boolean comparePassword(String password, String hashPassword) {

		return passwordEncoder.matches(password, hashPassword);

	}

	private ArrayList<SimpleGrantedAuthority> getAuthority(UserEntity user) {
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

		return authorities;
	}

}
