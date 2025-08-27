package com.example.project_3_security.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4,max = 10,message = "Length must be between 4 and 10 characters")
    @Column(columnDefinition = "varchar(50) unique not null ")
    @NotEmpty(message = "username must be not empty")
    private String username;

    @Size(min = 6, message = "Length must be at least 6 characters")
    @Column(columnDefinition = "varchar(255) not null ")
    @NotEmpty(message = "password must be not empty")
    private String password;

    @Size(min = 2,max = 20 ,message = "Length must be between 2 and 20 characters")
    @Column(columnDefinition = "varchar(50) not null ")
    @NotEmpty(message = "name must be not empty")
    private String name;

    @Column(columnDefinition = "varchar(70) unique not null ")
    @Email(message = "email must be valid")
    @NotEmpty(message = "email must be not empty")
    private String email;

    @Column(columnDefinition = "varchar(10) not null")
    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$", message = "role must be CUSTOMER or EMPLOYEE or ADMIN")
    private String role;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Employee employee;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
