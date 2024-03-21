package com.nikhilanand.dev.codingplatform.exchanges;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserScoreRequest {

    @Min(value = 0, message = "score must be greater or equal 0")
    @Max(value = 100, message = "score must be less  or equal to 100")
    @NotNull
    private Integer score;
}
