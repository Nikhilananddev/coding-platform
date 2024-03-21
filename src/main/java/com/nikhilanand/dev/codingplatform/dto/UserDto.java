package com.nikhilanand.dev.codingplatform.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "userId cannot be Blank or null")
    private String userId;

    @NotBlank(message = "username cannot be Blank or null")
    @NotNull
    private String username;

    @Min(value = 0, message = "score must be greater or equal 0")
    @Max(value = 100, message = "score must be less  or equal to 100")
    private Integer score;

    @Size(min = 0, max = 3, message = "user cannot have more than 3 badges")
    private Set<String> badges = new HashSet<>();


}
