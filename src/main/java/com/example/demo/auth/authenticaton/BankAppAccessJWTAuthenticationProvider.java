package com.example.demo.auth.authenticaton;

import com.example.demo.auth.jwt.model.JwToken;
import com.example.demo.auth.jwt.model.JwTokenDecipherRequest;
import com.example.demo.auth.jwt.provider.JwtTokenProvider;
import com.example.demo.auth.model.authentication.AccessJWTAuthenticationToken;
import com.example.demo.auth.tokenstore.AuthTokenStore;
import com.example.demo.auth.tokenstore.StoreAuthToken;
import com.example.demo.customer.CustomerPrincipal;
import com.example.demo.customer.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class BankAppAccessJWTAuthenticationProvider implements AuthenticationProvider {
    private final AuthTokenStore authTokenStore;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService userService;

    public BankAppAccessJWTAuthenticationProvider(@Qualifier("inMemoryAuthTokenStore") AuthTokenStore authTokenStore,
                                                  @Qualifier("jwsJwtTokenProvider") JwtTokenProvider jwtTokenProvider,
                                                  CustomerService userService) {
        this.authTokenStore = authTokenStore;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String sessionId = String.valueOf(authentication.getCredentials());

        Optional<StoreAuthToken> storeAuthToken = authTokenStore.read(sessionId);

        StoreAuthToken accessToken = storeAuthToken.orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(401), "Auth token not found"));

        JwToken jwToken = readJwtToken(accessToken.accessToken());

        UserDetails userDetails = userService.loadUserByUsername(jwToken.subject());

        if (!(userDetails instanceof CustomerPrincipal userPrincipal)) throw new IllegalArgumentException("BlogUserPrincipal is required");

        return new AccessJWTAuthenticationToken(userPrincipal, sessionId, userPrincipal.getAuthorities());
    }

    private JwToken readJwtToken(String accessToken) {
        JwTokenDecipherRequest jwTokenDecipherRequest = new JwTokenDecipherRequest(accessToken);
        return jwtTokenProvider.readToken(jwTokenDecipherRequest);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccessJWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
