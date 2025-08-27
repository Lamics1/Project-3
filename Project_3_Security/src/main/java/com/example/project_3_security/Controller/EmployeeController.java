package com.example.project_3_security.Controller;

import com.example.project_3_security.Api.ApiResponse;
import com.example.project_3_security.DTO.EmployeeDTO;
import com.example.project_3_security.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> add(@PathVariable Integer userId,
                                 @Valid @RequestBody EmployeeDTO dto) {
        employeeService.createEmployee(userId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Employee profile created"));
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> update(@PathVariable Integer employeeId,
                                    @Valid @RequestBody EmployeeDTO dto) {
        employeeService.updateEmployee(employeeId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Employee profile updated"));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.status(200).body(new ApiResponse("Employee profile deleted"));
    }

    @GetMapping("/get/{employeeId}")
    public ResponseEntity<?> get(@PathVariable Integer employeeId) {
        return ResponseEntity.status(200).body(employeeService.getEmployee(employeeId));
    }

    @GetMapping("/get-by-user/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(employeeService.getEmployeeByUser(userId));
    }
}
