package com.simpleshowassignment.userProject.servicetest;

import com.simpleshowassignment.userProject.dto.ResponseDto;
import com.simpleshowassignment.userProject.dto.UserDto;
import com.simpleshowassignment.userProject.entity.User;
import com.simpleshowassignment.userProject.exception.UserNotFoundException;
import com.simpleshowassignment.userProject.mapper.UserMapper;
import com.simpleshowassignment.userProject.repository.UserPagingAndSortingRepository;
import com.simpleshowassignment.userProject.repository.UserRepository;
import com.simpleshowassignment.userProject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserPagingAndSortingRepository userPagingAndSortingRepository;

    private UserDto userDto;
    private User user;
    private ResponseDto responseDto;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();

        // Initialize UserDto and ResponseDto
        userDto = new UserDto();
        userDto.setId(userId);
        userDto.setUsername("John Doe");

        responseDto = new ResponseDto();
        responseDto.setId(userId);
        responseDto.setUsername("John Doe");

        user = new User();
        user.setId(userId);
        user.setUsername("John Doe");
    }

    @Test
    void testCreateUser() {
        // Arrange
        when(userMapper.convertToEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.convertToResponseDto(user)).thenReturn(responseDto);

        // Act
        ResponseDto createdUser = userService.createUser(userDto);

        // Assert
        assertNotNull(createdUser);
        assertEquals(userId, createdUser.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindById_UserExists() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.convertToResponseDto(user)).thenReturn(responseDto);

        // Act
        ResponseDto foundUser = userService.findById(userId);

        // Assert
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        verify(userRepository, times(1)).findById(userId);
    }


    @Test
    void testFindById_UserNotFound() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Mocking a paginated response
        List<User> users = Arrays.asList(user);
        Page<User> userPage = new PageImpl<>(users, pageable, users.size()); // Mocked Page<User>

        when(userPagingAndSortingRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.convertToResponseDto(user)).thenReturn(responseDto);

        // Act
        List<ResponseDto> responseDtos = userService.getAllUsers(pageNumber, pageSize);

        // Assert
        assertNotNull(responseDtos);
        assertEquals(1, responseDtos.size());
        assertEquals(userId, responseDtos.get(0).getId());

        // Verify that the paging repository method was called with the correct Pageable object
        verify(userPagingAndSortingRepository, times(1)).findAll(pageable);
    }

}
