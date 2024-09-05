package com.simpleshowassignment.userProject.service;

import com.simpleshowassignment.userProject.dto.ResponseDto;
import com.simpleshowassignment.userProject.dto.UserDto;
import com.simpleshowassignment.userProject.entity.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {


    ResponseDto createUser(UserDto userdto);

    ResponseDto findById(UUID id);

    List<ResponseDto> getAllUsers(Integer pageNumber, Integer pageSize);
}
