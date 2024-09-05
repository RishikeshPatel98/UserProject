package com.simpleshowassignment.userProject.controller;

import com.simpleshowassignment.userProject.dto.ResponseDto;
import com.simpleshowassignment.userProject.dto.UserDto;
import com.simpleshowassignment.userProject.entity.User;
import com.simpleshowassignment.userProject.mapper.UserMapper;
import com.simpleshowassignment.userProject.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;


    @PostMapping(value = "/create-user")
    public ResponseEntity<ResponseDto> createUser(@RequestBody @Valid UserDto userdto) {
        ResponseDto createdUser = userService.createUser(userdto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable UUID id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<ResponseDto>> getAllUsers( @RequestParam("pageNumber") Integer pageNumber,
                                                          @RequestParam("pageSize") Integer pageSize){
        return ResponseEntity.ok(userService.getAllUsers(pageNumber,pageSize));
    }

}
