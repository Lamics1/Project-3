package com.example.project_3_security.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(50)")
    @NotEmpty(message = "Position must be not empty")
    private String position;

    @PositiveOrZero(message = "Salary must be zero or a positive number")
    @Column(columnDefinition = "int not null")
    @NotEmpty(message = "salary must be not empty")
    private Integer salary;

    @OneToOne
    @JsonIgnore
    private User user;
}
