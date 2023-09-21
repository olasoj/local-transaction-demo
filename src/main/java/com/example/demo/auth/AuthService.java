package com.example.demo.auth;

import com.example.demo.auth.model.request.AuthenticateViaAccessJWTRequest;
import com.example.demo.auth.model.request.AuthenticateViaUsernameAndPasswordRequest;
import com.example.demo.auth.model.response.AuthenticateViaAccessJWTResult;
import com.example.demo.auth.model.response.AuthenticateViaUsernameAndPasswordResult;

public interface AuthService {

    AuthenticateViaAccessJWTResult authenticateViaAccessJWT(AuthenticateViaAccessJWTRequest authenticateViaAccessJWTRequest);

    AuthenticateViaUsernameAndPasswordResult authenticateViaUsernameAndPassword(AuthenticateViaUsernameAndPasswordRequest authRestInboundRequest);
}
