package com.nikhilanand.dev.codingplatform.exchanges;

import com.nikhilanand.dev.codingplatform.model.UserEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUsersResponse {

    @Valid List<UserEntity> usersList;
}
