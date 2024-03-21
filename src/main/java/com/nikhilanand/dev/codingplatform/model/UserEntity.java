package com.nikhilanand.dev.codingplatform.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;


@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private String userId;

    @NotBlank(message = "username cannot be Blank")
    private String username;

    @Min(value = 0, message = "score must be greater or equal 0  ")
    @Max(value = 100, message = "score must be less  or equal to 100")
    private Integer score = 0;

    @Size(min = 0, max = 3, message = "user cannot have more than 3 badges")
    private Set<String> badges = new HashSet<>();


}
