package com.example.project_3_security.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @NotEmpty(message = "position must be not empty")
    private String position;

    @NotNull(message = "salary must be not null")
    @PositiveOrZero(message = "salary must be non-negative")
    private Integer salary;
}
