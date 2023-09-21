package com.example.demo.auth.adapter;

import com.example.demo.auth.AuthService;
import com.example.demo.auth.model.request.AuthRestInboundRequest;
import com.example.demo.auth.model.request.AuthenticateViaUsernameAndPasswordRequest;
import com.example.demo.auth.model.response.AuthRestInboundResponse;
import com.example.demo.auth.model.response.AuthenticateViaUsernameAndPasswordResult;
import com.example.demo.utils.response.model.Response;
import com.example.demo.utils.response.transformer.ResponseAssembler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService authService;

    public AuthController(@Qualifier("defaultAuthService") AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Response<AuthRestInboundResponse>> login(@Valid @RequestBody AuthRestInboundRequest authRestInboundRequest) {

        AuthenticateViaUsernameAndPasswordRequest authenticateViaUsernameAndPasswordRequest = new AuthenticateViaUsernameAndPasswordRequest(authRestInboundRequest.username(), authRestInboundRequest.password());
        AuthenticateViaUsernameAndPasswordResult authenticateViaUsernameAndPasswordResult = authService.authenticateViaUsernameAndPassword(authenticateViaUsernameAndPasswordRequest);

        AuthRestInboundResponse authRestInboundResponse = new AuthRestInboundResponse(authenticateViaUsernameAndPasswordResult.sessionId());
        Response<AuthRestInboundResponse> authResponse = ResponseAssembler.toResponse(HttpStatus.OK, authRestInboundResponse);

        return ResponseEntity.ok().body(authResponse);
    }
}