package com.example.project_3_security.Service;

import com.example.project_3_security.Api.ApiException;
import com.example.project_3_security.DTO.EmployeeDTO;
import com.example.project_3_security.Model.Employee;
import com.example.project_3_security.Model.User;
import com.example.project_3_security.Repository.EmployeeRepository;
import com.example.project_3_security.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;


    public void createEmployee(Integer userId, EmployeeDTO employeeDTO) {
        User user = userRepository.findUserById(userId);

        if (user == null)
            throw new ApiException("User not found");

        if (!"EMPLOYEE".equals(user.getRole())) {
            throw new ApiException("Only EMPLOYEE role can have employee profile");
        }
        if (user.getCustomer() != null) {
            throw new ApiException("User cannot be both CUSTOMER and EMPLOYEE");
        }
        if (user.getEmployee() != null) {
            throw new ApiException("Employee profile already exists");
        }

        Employee employee = new Employee();

        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());

        // للربط
        employee.setUser(user);
        user.setEmployee(employee);

        employeeRepository.save(employee);
    }


    public void updateEmployee(Integer employeeId, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);

        if (employee == null)
            throw new ApiException("Employee not found");

        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());

        employeeRepository.save(employee);
    }


    public void deleteEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null) throw new ApiException("Employee not found");

        // فك الربط
        User user = employee.getUser();
        if (user != null)

            user.setEmployee(null);
        employee.setUser(null);

        employeeRepository.delete(employee);
    }


    public Employee getEmployee(Integer employeeId) {
        Employee emp = employeeRepository.findEmployeeById(employeeId);
        if (emp == null)
            throw new ApiException("Employee not found");
        return emp;
    }


    public Employee getEmployeeByUser(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Employee emp = employeeRepository.findEmployeeByUser(user);
        if (emp == null) throw new ApiException("Employee not found");
        return emp;
    }
}
