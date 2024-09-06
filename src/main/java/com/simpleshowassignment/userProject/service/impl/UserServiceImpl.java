package com.simpleshowassignment.userProject.service.impl;

import com.simpleshowassignment.userProject.dto.ResponseDto;
import com.simpleshowassignment.userProject.dto.UserDto;
import com.simpleshowassignment.userProject.entity.User;
import com.simpleshowassignment.userProject.exception.UserNotFoundException;
import com.simpleshowassignment.userProject.mapper.UserMapper;
import com.simpleshowassignment.userProject.repository.UserPagingAndSortingRepository;
import com.simpleshowassignment.userProject.repository.UserRepository;
import com.simpleshowassignment.userProject.service.IUserService;
import com.simpleshowassignment.userProject.util.UserErrorResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl  implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserPagingAndSortingRepository userPagingAndSortingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResponseDto createUser(UserDto userdto) {
        User user = userMapper.convertToEntity(userdto);

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        try {
            log.info("Creating user with email: {}", user.getEmail());
            User savedUser = userRepository.save(user);
            return userMapper.convertToResponseDto(savedUser);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error creating user: {}", ex.getMessage());
            throw new DataIntegrityViolationException("Email already exists.");
        }
    }

    @Override
    public ResponseDto findById(UUID id) {
        log.info("getting user by id " + id);
        Optional<User> user = userRepository.findById(id);

        // Handle the case where the user is not found
        if (user.isPresent()) {
            // Convert the found User entity to UserDto
            return userMapper.convertToResponseDto(user.get());
        } else {
            // Handle the case where the user is not found, e.g., throw an exception or return null
            throw new UserNotFoundException(UserErrorResponseUtil.buildErrorResponse("user not found with this id " + id, null));
            // Alternatively, return null or handle it in another way
            // return null;
        }
    }

    @Override
    public List<ResponseDto> getAllUsers(Integer pageNumber, Integer pageSize) {
        // Create the pageable object
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Fetch paginated users from the repository
        Page<User> usersPage = userPagingAndSortingRepository.findAll(pageable);

        // Convert the Page<User> into List<ResponseDto>
        List<ResponseDto> responseDtoList = usersPage
                .stream()
                .map(userMapper::convertToResponseDto)  // Lambda function to convert each user to ResponseDto
                .collect(Collectors.toList());
        log.info("getting users according to page number and page size requested");
        return responseDtoList;
    }
}
