package com.app.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.configuration.jwtTokenUtil;
import com.app.dto.IUserListDto;
import com.app.dto.UserDto;
import com.app.entities.UserEntity;
import com.app.entities.UserRoleEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AuthRepository;
import com.app.repository.UserRepository;
import com.app.repository.UserRoleRepository;
import com.app.serviceInterface.UserInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private jwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

//	@Override
//	public UserDto getUserById(Long id) {
//
//		UserEntity userEntity = this.userRepository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("user id not found, Please enter correct userId"));
//		UserDto userDto = new UserDto();
//		userDto.setId(userEntity.getId());
//		userDto.setName(userEntity.getName());
//		userDto.setEmail(userEntity.getEmail());
//
//		return userDto;
//	}

	public Page<IUserListDto> getAllUsers(String search, String pageNo, String pageSize) {

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
		Page<IUserListDto> iUserListDto;
		if ((search == "") || (search == null) || (search.length() == 0)) {

			iUserListDto = userRepository.findByOrderByIdAsc(pageable, IUserListDto.class);
		} else {

			iUserListDto = userRepository.findByNameIgnoreCaseContaining(search, pageable, IUserListDto.class);
		}
		return iUserListDto;
	}

	@Override
	public UserDto updateUser(Long id, UserDto userDto) {
		UserEntity userEntity = this.userRepository.findById(id).orElseThrow();
		userEntity.setName(userDto.getName());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		this.userRepository.save(userEntity);
		return userDto;
	}

	@Override
	public void deleteUser(Long id) {
		this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found for this Id "));
		userRepository.deleteById(id);
	}

	@Override
	public List<IUserListDto> getUserById(Long id) {
		List<IUserListDto> iUserListDto;
		return iUserListDto = this.userRepository.findById(id, IUserListDto.class);
	}

	@Override
	public List<IUserListDto> filterAllUserRecord(Long id, HttpServletRequest request) throws Exception {
		List<IUserListDto> iUserListDto;
		final String header = request.getHeader("Authorization");
		String requestToken = header.substring(7);
		final String email = jwtTokenUtil.getEmailFromToken(requestToken);
		UserEntity userEntity = authRepository.findByEmailContainingIgnoreCase(email);
		Long userId = userEntity.getId();
		UserRoleEntity userRoleEntity = userRoleRepository.findRoleIdByUserId(userId);
		String userRole = userRoleEntity.getRole().getRoleName();
		if (userRole.equals("ADMIN")) {

			iUserListDto = this.userRepository.findById(id, IUserListDto.class);

			return iUserListDto;

		} else {

			throw new Exception("this user is not a admin , only admin have a access to see users information");
		}

	}

}
