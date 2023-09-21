package com.example.demo.auth;

import com.example.demo.auth.model.authentication.AccessJWTAuthenticationToken;
import com.example.demo.auth.model.authentication.BankAppUsernamePasswordAuthenticationToken;
import com.example.demo.auth.model.request.AuthenticateViaAccessJWTRequest;
import com.example.demo.auth.model.request.AuthenticateViaUsernameAndPasswordRequest;
import com.example.demo.auth.model.response.AuthenticateViaAccessJWTResult;
import com.example.demo.auth.model.response.AuthenticateViaUsernameAndPasswordResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class DefaultAuthService implements AuthService {

    private final AuthenticationManager authenticationManager;


    public DefaultAuthService(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticateViaAccessJWTResult authenticateViaAccessJWT(AuthenticateViaAccessJWTRequest authenticateViaAccessJWTRequest) {

        String token = authenticateViaAccessJWTRequest.token();
        AccessJWTAuthenticationToken accessJwtAuthenticationToken = new AccessJWTAuthenticationToken(token, token);

        Authentication authenticate = authenticationManager.authenticate(accessJwtAuthenticationToken);
        return new AuthenticateViaAccessJWTResult(authenticate);
    }

    @Override
    public AuthenticateViaUsernameAndPasswordResult authenticateViaUsernameAndPassword(AuthenticateViaUsernameAndPasswordRequest authenticateViaUsernameAndPasswordRequest) {
        try {
            BankAppUsernamePasswordAuthenticationToken gatewayUsernamePasswordAuthenticationToken = new BankAppUsernamePasswordAuthenticationToken(authenticateViaUsernameAndPasswordRequest.username(), authenticateViaUsernameAndPasswordRequest.password());

            Authentication authenticate = authenticationManager.authenticate(gatewayUsernamePasswordAuthenticationToken);
            return new AuthenticateViaUsernameAndPasswordResult(String.valueOf(authenticate.getPrincipal()));
        } catch (UsernameNotFoundException | BadCredentialsException exception) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid username or password", exception);
        }
    }
}
