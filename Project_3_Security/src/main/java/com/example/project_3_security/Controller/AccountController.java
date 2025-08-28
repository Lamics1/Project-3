package com.example.project_3_security.Controller;

import com.example.project_3_security.Api.ApiResponse;
import com.example.project_3_security.Model.Account;
import com.example.project_3_security.Model.Customer;
import com.example.project_3_security.Model.User;
import com.example.project_3_security.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;


    @PostMapping("/add")
    public ResponseEntity<?> createMyAccount(@AuthenticationPrincipal User user, @Valid @RequestBody Account account) {
        accountService.createMyAccount(user.getId(), account);
        return ResponseEntity.status(200).body(new ApiResponse("Account created"));
    }

    // (ADMIN )
    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable Integer id) {
        accountService.activate(id);
        return ResponseEntity.status(200).body(new ApiResponse("Account activated"));
    }

    //  (ADMIN )
    @PutMapping("/{id}/block")
    public ResponseEntity<?> block(@PathVariable Integer id) {
        accountService.block(id);
        return ResponseEntity.status(200).body(new ApiResponse("Account blocked"));
    }

    //  (CUSTOMER )
    @GetMapping("/{id}")
    public ResponseEntity<?> viewMyAccount(@AuthenticationPrincipal Customer customer, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(accountService.viewMyAccount(customer.getId(), id));
    }

    //  (ADMIN )
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> listCustomerAccounts(@PathVariable Integer customerId) {
        return ResponseEntity.status(200).body(accountService.listCustomerAccounts(customerId));
    }

    //  (CUSTOMER )
    @PostMapping("/{id}/deposit/{amount}")
    public ResponseEntity<?> deposit(@AuthenticationPrincipal Customer customer, @PathVariable Integer id, @PathVariable Integer amount) {
        accountService.deposit(customer.getId(), id, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Deposited"));
    }

    // (CUSTOMER)
    @PostMapping("/{id}/withdraw/{amount}")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal Customer customer, @PathVariable Integer id, @PathVariable Integer amount) {
        accountService.withdraw(customer.getId(), id, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Withdrawn"));
    }

//POST /api/v1/accounts/transfer?fromAccountId=11&toAccountNumber=1234-5678-9012-3456&amount=100
    //.requestMatchers("/api/v1/accounts/transfer").hasAuthority("CUSTOMER")

    //   (CUSTOMER)
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@AuthenticationPrincipal Customer customer, @RequestParam Integer fromAccountId, @RequestParam String toAccountNumber, @RequestParam Integer amount) {
        accountService.transfer(customer.getId(), fromAccountId, toAccountNumber, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Transferred"));
    }
}
