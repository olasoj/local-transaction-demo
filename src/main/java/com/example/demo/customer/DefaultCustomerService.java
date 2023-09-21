package com.example.demo.customer;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.customer.CustomerType.*;
import static com.example.demo.utils.TimeUtils.instant;

@Service
public class DefaultCustomerService implements CustomerService {

    private static final Map<String, Customer> customerMap;

    static {
        customerMap =
                Map.of(
                        "2344559", new Customer("2344559", "John Ole", RETAIL, instant(1992, Month.JANUARY, 1)),
                        "1344459", new Customer("1344459", "James Babalola", BUSINESS, instant(1993, Month.JANUARY, 1)),
                        "3344659", new Customer("3344659", "Peter Mod", RETAIL, instant(1994, Month.JANUARY, 1)),
                        "2311159", new Customer("2311159", "Alfred Wisdom", BUSINESS, instant(1995, Month.JANUARY, 1)),
                        "2311152", new Customer("2311152", "SYSTEM", SYSTEM, instant(1996, Month.JANUARY, 1))
                );
    }

    @Override
    public Customer getCustomer(String customerID) {
        return Optional.ofNullable(customerMap.get(customerID))
                .orElseThrow(() -> new CustomerServiceException("Customer not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomerPrincipal(getCustomer(username));
    }
}

