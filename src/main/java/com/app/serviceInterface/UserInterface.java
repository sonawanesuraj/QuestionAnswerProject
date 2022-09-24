package com.app.serviceInterface;

import com.app.dto.UserDto;

public interface UserInterface {

	UserDto getUserById(Long id);

	UserDto updateUser(Long id, UserDto userDto);

	void deleteUser(Long id);

}
