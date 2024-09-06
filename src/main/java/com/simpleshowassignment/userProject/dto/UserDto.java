package com.simpleshowassignment.userProject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be Blank")
    @Schema(example = "username")
    private String username;

    @Email
    @NotBlank(message = "Email can't be empty")
    @Schema(example = "username@gmail.com")

    private String email;

    @Schema(example = "123345")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Schema(example = "admin, user")
    private String role;

    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;

}
