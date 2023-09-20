package com.example.demo.user;

import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

public class Customer {
    private final  String customerId;
    private final  String customerName;
    private final  UserType customerType;
    private final Instant dateCreated;

    public Customer(String customerId, String customerName, UserType customerType, Instant dateCreated) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerType = customerType;
        this.dateCreated = dateCreated;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public UserType getCustomerType() {
        return customerType;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Customer customer)) return false;
        return Objects.equals(customerId, customer.customerId) && Objects.equals(customerName, customer.customerName) && customerType == customer.customerType && Objects.equals(dateCreated, customer.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, customerType, dateCreated);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
                .add("customerId='" + customerId + "'")
                .add("customerName='" + customerName + "'")
                .add("customerType=" + customerType)
                .add("dateCreated=" + dateCreated)
                .toString();
    }
}
