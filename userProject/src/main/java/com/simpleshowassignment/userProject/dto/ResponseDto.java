package com.simpleshowassignment.userProject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private UUID id;

    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be Blank")
    private String username;

    @Email
    @NotBlank(message = "Email can't be empty")
    private String email;

    private String role;

}
