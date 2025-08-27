package com.example.project_3_security.Controller;

import com.example.project_3_security.Api.ApiResponse;
import com.example.project_3_security.DTO.CustomerDTO;
import com.example.project_3_security.Model.User;
import com.example.project_3_security.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@AuthenticationPrincipal User user, @Valid @RequestBody CustomerDTO dto) {
        customerService.createMyCustomer(user.getId(), dto);
        return ResponseEntity.status(200).body(new ApiResponse("Customer profile created"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@AuthenticationPrincipal User user, @Valid @RequestBody CustomerDTO dto) {
        customerService.updateMyCustomer(user.getId(), dto);
        return ResponseEntity.status(200).body(new ApiResponse("Customer profile updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user) {
        customerService.deleteMyCustomer(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Customer profile deleted"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(customerService.getMyCustomer(user.getId()));
    }
}
