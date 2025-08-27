package com.example.project_3_security.Service;

import com.example.project_3_security.Api.ApiException;

import com.example.project_3_security.Model.User;
import com.example.project_3_security.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User getUser(Integer id) {
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null)
            throw new ApiException("User not found");
        return oldUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Register new CUSTOMER
    public void register(User user) {

        User byUsername = userRepository.findUserByUsername(user.getUsername());
        if (byUsername != null) {
            throw new ApiException("Username already exists");
        }

        User byEmail = userRepository.findUserByEmail(user.getEmail());
        if (byEmail != null) {
            throw new ApiException("Email already exists");
        }

        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        user.setRole("CUSTOMER");
        userRepository.save(user);
    }

    public void updateUser(Integer id, User user) {

        User old = userRepository.findUserById(id);
        if (old == null)
            throw new ApiException("User not found");

        if (!old.getUsername().equals(user.getUsername())) {
            User byUsername = userRepository.findUserByUsername(user.getUsername());
            if (byUsername != null)
                throw new ApiException("Username already exists");
            old.setUsername(user.getUsername());
        }

        if (!old.getEmail().equals(user.getEmail())) {
            User byEmail = userRepository.findUserByEmail(user.getEmail());
            if (byEmail != null)
                throw new ApiException("Email already exists");
            old.setEmail(user.getEmail());
        }

        old.setName(user.getName());

        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        old.setPassword(hash);

        userRepository.save(old);
    }

    public void deleteUser(Integer id) {
        User old = userRepository.findUserById(id);
        if (old == null) throw new ApiException("User not found");
        userRepository.delete(old);
    }

    // ADMIN only
    public void changeRole(Integer userId, String newRole) {
        if (!newRole.matches("^(CUSTOMER|EMPLOYEE|ADMIN)$")) {
            throw new ApiException("Invalid role");
        }
        User u = userRepository.findUserById(userId);
        if (u == null)
            throw new ApiException("User not found");
        u.setRole(newRole);
        userRepository.save(u);
    }
}

