package com.example.project_3_security.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$", message = "Account number must follow the format XXXX-XXXX-XXXX-XXXX")
    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "account Number must be not empty ")
    private String accountNumber;

    @PositiveOrZero(message = "balance must be zero or a positive number")
    @NotNull(message = "balance must be not null")
    private Integer balance;

    @Column(nullable = false)
    private boolean active = false;

    @ManyToOne
    @JsonIgnore
    private Customer customer;
}
