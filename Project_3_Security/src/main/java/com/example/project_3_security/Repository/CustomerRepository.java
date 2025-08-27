package com.example.project_3_security.Repository;

import com.example.project_3_security.Model.Customer;
import com.example.project_3_security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findCustomerById(Integer id);

    Customer findCustomerByUser(User user);
}
