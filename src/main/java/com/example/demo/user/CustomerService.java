package com.example.demo.user;

import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.user.UserType.BUSINESS;
import static com.example.demo.user.UserType.RETAIL;
import static com.example.demo.utils.TimeUtils.instant;

@Service
public class CustomerService {

    private static final Map<String, Customer> customerMap;

    static {
        customerMap =
                Map.of(
                        "2344559", new Customer("2344559", "John Ole", RETAIL, instant(1992, Month.JANUARY, 1)),
                        "1344459", new Customer("1344459", "James Babalola", BUSINESS, instant(1993, Month.JANUARY, 1)),
                        "3344659", new Customer("3344659", "Peter Mod", RETAIL, instant(1994, Month.JANUARY, 1)),
                        "2311159", new Customer("2311159", "Alfred Wisdom", BUSINESS, instant(1995, Month.JANUARY, 1))
                );
    }


    public Customer getCustomer(String customerID) {
        return Optional.ofNullable(customerMap.get(customerID))
                .orElseThrow(() -> new CustomerServiceException("Customer not found"));
    }
}

class CustomerServiceException extends RuntimeException {

    public CustomerServiceException(String message) {
        super(message);
    }
}