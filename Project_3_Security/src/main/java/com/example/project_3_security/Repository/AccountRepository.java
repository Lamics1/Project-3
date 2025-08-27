package com.example.project_3_security.Repository;

import com.example.project_3_security.Model.Account;
import com.example.project_3_security.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findAccountById(Integer id);

    Account findAccountByAccountNumber(String accountNumber);

    List<Account> findAllByCustomer(Customer customer);

    List<Account> findAllByCustomerAndActive(Customer customer, boolean active);
}
