package com.simpleshowassignment.userProject.controller;

import com.simpleshowassignment.userProject.dto.ResponseDto;
import com.simpleshowassignment.userProject.dto.UserDto;
import com.simpleshowassignment.userProject.entity.User;
import com.simpleshowassignment.userProject.mapper.UserMapper;
import com.simpleshowassignment.userProject.service.IUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"success\": true,\n" +
                                    "  \"message\": \"User created successfully\",\n" +
                                    "  \"data\": {\n" +
                                    "    \"id\": \"fc4eb306-bf45-4dfe-90dc-506708cf010f\",\n" +
                                    "    \"username\": \"john\",\n" +
                                    "    \"email\": \"test@gmail.com\",\n" +
                                    "    \"password\": \"12345\",\n" +
                                    "    \"role\": \"USER\"\n" +
                                    "  }\n" +
                                    "}"))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"success\": false,\n" +
                                    "  \"message\": \"Invalid request data\",\n" +
                                    "  \"data\": null\n" +
                                    "}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"success\": false,\n" +
                                    "  \"message\": \"Internal server error\",\n" +
                                    "  \"data\": null\n" +
                                    "}")))
    })
    @PostMapping(value = "/create-user")
    public ResponseEntity<ResponseDto> createUser(@RequestBody @Valid UserDto userdto) {
        ResponseDto createdUser = userService.createUser(userdto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"success\": true,\n" +
                                    "  \"message\": \"User retrieved successfully\",\n" +
                                    "  \"data\": {\n" +
                                    "    \"id\": \"fc4eb306-bf45-4dfe-90dc-506708cf010f\",\n" +
                                    "    \"username\": \"john\",\n" +
                                    "    \"email\": \"test@gmail.com\",\n" +
                                    "    \"role\": \"USER\"\n" +
                                    "  }\n" +
                                    "}"))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"success\": false,\n" +
                                    "  \"message\": \"User not found\",\n" +
                                    "  \"data\": null\n" +
                                    "}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"success\": false,\n" +
                                    "  \"message\": \"Internal server error\",\n" +
                                    "  \"data\": null\n" +
                                    "}")))
    })
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable UUID id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"success\": true,\n" +
                                    "  \"message\": \"Users retrieved successfully\",\n" +
                                    "  \"data\": [{\n" +
                                    "    \"id\": \"fc4eb306-bf45-4dfe-90dc-506708cf010f\",\n" +
                                    "    \"username\": \"john\",\n" +
                                    "    \"email\": \"test@gmail.com\",\n" +
                                    "    \"role\": \"USER\"\n" +
                                    "  }]\n" +
                                    "}"))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"success\": false,\n" +
                                    "  \"message\": \"Invalid pagination parameters\",\n" +
                                    "  \"data\": null\n" +
                                    "}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"success\": false,\n" +
                                    "  \"message\": \"Internal server error\",\n" +
                                    "  \"data\": null\n" +
                                    "}")))
    })
    @GetMapping(value = "/users")
    public ResponseEntity<List<ResponseDto>> getAllUsers( @RequestParam("pageNumber") Integer pageNumber,
                                                          @RequestParam("pageSize") Integer pageSize){
        return ResponseEntity.ok(userService.getAllUsers(pageNumber,pageSize));
    }

}
