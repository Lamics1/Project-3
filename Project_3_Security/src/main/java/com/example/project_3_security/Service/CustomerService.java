package com.example.project_3_security.Service;

import com.example.project_3_security.Api.ApiException;
import com.example.project_3_security.DTO.CustomerDTO;
import com.example.project_3_security.Model.Customer;
import com.example.project_3_security.Model.User;
import com.example.project_3_security.Repository.CustomerRepository;
import com.example.project_3_security.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;


    public void createMyCustomer(Integer userId, CustomerDTO customerDTO) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!"CUSTOMER".equals(user.getRole()))
            throw new ApiException("Only CUSTOMER can create customer profile");

        if (user.getEmployee() != null)
            throw new ApiException("User cannot be both CUSTOMER and EMPLOYEE");

        if (user.getCustomer() != null)
            throw new ApiException("Customer profile already exists");

        Customer customer = new Customer();

         customer.setPhoneNumber(customerDTO.getPhoneNumber());

        customer.setUser(user);
        user.setCustomer(customer);

        customerRepository.save(customer);
    }

    public void updateMyCustomer(Integer userId, CustomerDTO customerDTO) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!"CUSTOMER".equals(user.getRole()))
            throw new ApiException("Only CUSTOMER can update customer profile");

        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null)
            throw new ApiException("Customer profile not found");

         customer.setPhoneNumber(customerDTO.getPhoneNumber());

        customerRepository.save(customer);
    }


    public void deleteMyCustomer(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null)
            throw new ApiException("Customer profile not found");

        //فك الربط
        user.setCustomer(null);
        customer.setUser(null);

        customerRepository.delete(customer);
    }


    public Customer getMyCustomer(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null)
            throw new ApiException("Customer profile not found");

        return customer;
    }
}
