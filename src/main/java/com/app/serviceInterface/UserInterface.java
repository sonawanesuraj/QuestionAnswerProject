package com.app.serviceInterface;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.dto.IUserListDto;
import com.app.dto.UserDto;

public interface UserInterface {

	public List<IUserListDto> getUserById(Long id);

	UserDto updateUser(Long id, UserDto userDto);

	void deleteUser(Long id);

	public List<IUserListDto> filterAllUserRecord(Long id, HttpServletRequest request) throws Exception;

}
