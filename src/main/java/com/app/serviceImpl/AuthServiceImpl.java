package com.app.serviceImpl;

import java.util.ArrayList;

import com.app.dto.UserDto;
import com.app.entities.UserEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AuthRepository;
import com.app.serviceInterface.AuthInterface;
import com.app.util.CacheOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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

	@Autowired
	private CacheOperation cache;

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
		UserEntity userEntity = new UserEntity();

		if (!cache.isKeyExist(email, email)) {

			System.out.println("FETCH FROM DATABASE");

			userEntity = this.authRepository.findByEmailContainingIgnoreCase(email);
			cache.addInCache(email, email, userEntity.toString());

		} else {

			String jsonString = (String) cache.getFromCache(email, email);
			System.out.println("FROM CACHE");
			JSONObject jsonObject = null;

			try {
				jsonObject = new JSONObject(jsonString);
				userEntity.setId(Long.parseLong(jsonObject.getString("id")));
				userEntity.setEmail(jsonObject.getString("email"));
				userEntity.setPassword(jsonObject.getString("password"));
				System.out.println("ADD IN CACHE");

			} catch (JSONException e) {
				userEntity = null;
			}

		}

		if (userEntity == null)

		{

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
