package com.app.serviceInterface;

import java.util.List;

import com.app.dto.IListUserQuestion;
import com.app.dto.UserQuestionDto;

import org.springframework.data.domain.Page;

public interface UserQuestionInterface {

	public UserQuestionDto addUserQuestion(UserQuestionDto userQuestionDto);

	public UserQuestionDto updateUserOrQuestion(Long id, UserQuestionDto userQuestionDto);

	public void deleteUserQuestion(Long id);

	public List<IListUserQuestion> getUserQuestionById(Long id);

	Page<IListUserQuestion> getAllUserQuestion(String search, String pageNumber, String pageSize);

}
