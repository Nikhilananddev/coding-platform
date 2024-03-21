package com.nikhilanand.dev.codingplatform.exchanges;

import com.nikhilanand.dev.codingplatform.model.UserEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {

    @Valid UserEntity userDetails;
}