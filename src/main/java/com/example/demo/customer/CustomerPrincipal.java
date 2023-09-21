package com.example.demo.customer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.stream.Collectors;

public record CustomerPrincipal(Customer user) implements UserDetails {
    public CustomerPrincipal {
        Assert.notNull(user, "User cannot be null");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAccessControlList().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAccessControlList()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return user.getCustomerId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
