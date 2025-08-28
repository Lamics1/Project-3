package com.example.project_3_security.Service;

import com.example.project_3_security.Api.ApiException;
import com.example.project_3_security.Model.Account;
import com.example.project_3_security.Model.Customer;
import com.example.project_3_security.Model.User;
import com.example.project_3_security.Repository.AccountRepository;
import com.example.project_3_security.Repository.CustomerRepository;
import com.example.project_3_security.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;


    private User getUser(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");
        return user;
    }
    private Customer getCustomerByUser(User user) {
        Customer customer= customerRepository.findCustomerByUser(user);
        if (customer == null)
            throw new ApiException("Customer profile not found");
        return customer;
    }
    private Account getAccount(Integer id) {
        Account a = accountRepository.findAccountById(id);
        if (a == null) throw new ApiException("Account not found");
        return a;
    }
    private void ensureOwner(Account account, Integer userId) {
        Integer owner = account.getCustomer().getUser().getId();
        if (!owner.equals(userId))
            throw new ApiException("User not have auth");
    }

    private void ensureActive(Account account) {
        if (!account.isActive())
            throw new ApiException("Account is not active");
    }


    // 2) Create a new bank account
    public void createMyAccount(Integer customer_Id, Account account) {

        Customer customer = customerRepository.findCustomerById(customer_Id);
        if (customer == null){
            throw new ApiException(" CUSTOMER not found");
        }

        // CHECK for account
        if (accountRepository.findAccountByAccountNumber(account.getAccountNumber()) != null)
            throw new ApiException("accountNumber already exists");

        Account a = new Account();
        a.setAccountNumber(account.getAccountNumber());
        a.setBalance(account.getBalance());
        a.setActive(false);
        a.setCustomer(customer);
        accountRepository.save(a);
    }

    //ADMIN
    public void activate(Integer id) {
        Account acc = getAccount(id);
        if (acc.isActive()) return;
        acc.setActive(true);
        accountRepository.save(acc);
    }

    //ADMIN
    public void block(Integer id) {
        Account acc = getAccount(id);
        if (!acc.isActive()) return;
        acc.setActive(false);
        accountRepository.save(acc);
    }


    // 4) View account details (CUSTOMER)
    public Account viewMyAccount(Integer customer_Id, Integer accountId) {
        Customer customer = customerRepository.findCustomerById(customer_Id);
        if (customer == null){
            throw new ApiException(" CUSTOMER not found");
        }
        Account a = getAccount(accountId);
        ensureOwner(a, customer_Id);
        return a;
    }

    // 5) List user's accounts (ADMIN)
    public List<Account> listCustomerAccounts(Integer customerId) {
        Customer c = customerRepository.findCustomerById(customerId);
        if (c == null)
            throw new ApiException("Customer not found");
        return accountRepository.findAllByCustomer(c);
    }

    // 6)  (CUSTOMER)
    public void deposit(Integer customerId, Integer accountId, Integer amount) {
        if (amount <= 0)
            throw new ApiException("Amount must be positive");

        Customer c = customerRepository.findCustomerById(customerId);
        if (c == null)
            throw new ApiException("Customer not found");
        Account a = getAccount(accountId);
        ensureOwner(a, customerId);
        ensureActive(a);
        a.setBalance(a.getBalance() + amount);
        accountRepository.save(a);
    }

    // 6)  (CUSTOMER)
    public void withdraw(Integer customerId, Integer accountId, Integer amount) {
        if (amount <= 0)
            throw new ApiException("Amount must be positive");

        Customer c = customerRepository.findCustomerById(customerId);
        if (c == null)
            throw new ApiException("Customer not found");

        Account a = getAccount(accountId);
        ensureOwner(a, customerId);
        ensureActive(a);
        if (a.getBalance() < amount)
            throw new ApiException("Insufficient balance");
        a.setBalance(a.getBalance() - amount);
        accountRepository.save(a);
    }

    // 7)  (CUSTOMER)
    public void transfer(Integer customerId, Integer fromAccountId, String toAccountNumber, Integer amount) {
        if (amount <= 0)
            throw new ApiException("Amount must be positive");

        Customer c = customerRepository.findCustomerById(customerId);
        if (c == null)
            throw new ApiException("Customer not found");


        Account from = getAccount(fromAccountId);
        ensureOwner(from, customerId);
        ensureActive(from);

        Account to = accountRepository.findAccountByAccountNumber(toAccountNumber);
        if (to == null)
            throw new ApiException("Destination account not found");
        ensureActive(to);

        if (from.getBalance() < amount)
            throw new ApiException("Insufficient balance");


        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);
        accountRepository.save(to);
    }
}
