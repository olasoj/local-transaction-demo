package com.example.demo.auth.model.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BankAppUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public BankAppUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public BankAppUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
