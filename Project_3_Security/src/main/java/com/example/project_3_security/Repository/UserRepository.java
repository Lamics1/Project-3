package com.example.project_3_security.Repository;

import com.example.project_3_security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserByUsername(String username);

    User findUserById(Integer id);

    User findUserByEmail(String email);
}
