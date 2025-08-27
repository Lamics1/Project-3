package com.example.project_3_security.Repository;

import com.example.project_3_security.Model.Employee;
import com.example.project_3_security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findEmployeeById(Integer id);

    Employee findEmployeeByUser(User user);
}
