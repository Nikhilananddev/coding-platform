package com.nikhilanand.dev.codingplatform.exchanges;

import com.nikhilanand.dev.codingplatform.dto.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {

    @Valid UserDto userDetails;
}


