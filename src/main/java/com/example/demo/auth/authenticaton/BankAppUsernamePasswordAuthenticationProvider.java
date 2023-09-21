package com.example.demo.auth.authenticaton;

import com.example.demo.auth.jwt.model.CreateJwTokenRequest;
import com.example.demo.auth.jwt.model.CreateJwTokenResult;
import com.example.demo.auth.jwt.provider.JwtTokenProvider;
import com.example.demo.auth.model.authentication.BankAppUsernamePasswordAuthenticationToken;
import com.example.demo.auth.tokenstore.AuthTokenStore;
import com.example.demo.customer.CustomerPrincipal;
import com.example.demo.customer.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BankAppUsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final CustomerService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenStore authTokenStore;


    public BankAppUsernamePasswordAuthenticationProvider(@Qualifier("defaultCustomerService") CustomerService userService,
                                                         @Qualifier("bCryptPasswordEncoder") PasswordEncoder passwordEncoder,
                                                         @Qualifier("jwsJwtTokenProvider") JwtTokenProvider jwtTokenProvider,
                                                         @Qualifier("inMemoryAuthTokenStore") AuthTokenStore authTokenStore) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authTokenStore = authTokenStore;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        CreateJwTokenResult createJwTokenResult = auth(username, password);
        String sessionId = authTokenStore.create(createJwTokenResult.jwtToken());

        return new BankAppUsernamePasswordAuthenticationToken(sessionId, sessionId);
    }

    private CreateJwTokenResult auth(String username, String password) {
        UserDetails userDetails = userService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Bad credentials.");

        if (!(userDetails instanceof CustomerPrincipal userPrincipal)) throw new IllegalArgumentException("BlogUserPrincipal is required");

        CreateJwTokenRequest createJwTokenRequest = CreateJwTokenRequest.builder()
                .subject(username)
                .accessControlLists((userPrincipal).user().getAccessControlList())
                .build();

        return jwtTokenProvider.createToken(createJwTokenRequest);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BankAppUsernamePasswordAuthenticationProvider.class.isAssignableFrom(authentication);
    }
}
