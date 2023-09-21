package com.example.demo.customer;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {
    Customer getCustomer(String customerID);
}
