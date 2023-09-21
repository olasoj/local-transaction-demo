package com.example.demo.auth.jwt.provider;

import com.example.demo.auth.jwt.config.JwtConfig;
import com.example.demo.auth.jwt.config.JwtKey;
import com.example.demo.auth.jwt.model.CreateJwTokenRequest;
import com.example.demo.auth.jwt.model.CreateJwTokenResult;
import com.example.demo.auth.jwt.model.JwToken;
import com.example.demo.auth.jwt.model.JwTokenDecipherRequest;
import com.example.demo.customer.acl.transformer.AccessControlListAssembler;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwsJwtTokenProvider implements JwtTokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwsJwtTokenProvider.class);
    private static final String AUTHORITIES_KEY = "accessControlList";

    private final JwtKey jwtKey;
    private final JwtConfig jwtConfig;

    public JwsJwtTokenProvider(@Qualifier("jwtKey") JwtKey jwtKey, @Qualifier("jwtConfig") JwtConfig jwtConfig) {
        this.jwtKey = jwtKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public JwToken readToken(JwTokenDecipherRequest jwTokenDecipherRequest) {

        validateToken(jwTokenDecipherRequest);
        Claims claims = decodeClaims(jwTokenDecipherRequest);

        Instant issuedAt = claims.getIssuedAt().toInstant();
        Instant validity = claims.getExpiration().toInstant();
        return JwToken.builder()
                .subject(claims.getSubject())
                .issuedAt(issuedAt)
                .validity(validity)
                .expiration(Duration.between(issuedAt, validity))
                .build();
    }

    @Override
    public CreateJwTokenResult createToken(CreateJwTokenRequest createJwTokenRequest) {
        Assert.notNull(createJwTokenRequest, "JwToken cannot be null");

        Claims claims = Jwts.claims().setSubject(createJwTokenRequest.subject());

        List<String> accessControlLists = AccessControlListAssembler.toAccessControlListString(createJwTokenRequest.accessControlLists());
        claims.put(AUTHORITIES_KEY, accessControlLists);

        String token = createToken(claims);

        return CreateJwTokenResult.builder()
                .jwtToken(token)
                .build();
    }

    private String createToken(Claims claims) {

        Instant now = Instant.now();

        long expireLength = jwtConfig.expireLength();
        Instant validity = now.plusMillis(expireLength);
        Key signingKey = jwtKey.privateKeyForSigning();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setIssuer(ISSUER)
                .setExpiration(Date.from(validity))
                .signWith(signingKey, JwsJwtTokenProviderAdapter.signatureAlgorithm(signingKey))
                .compact();
    }

    private void validateToken(JwTokenDecipherRequest jwTokenDecipherRequest) {
        try {
            Claims decode = decodeClaims(jwTokenDecipherRequest);
            if (decode.getExpiration().before(new Date())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Expired JWT");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Failed to decode JWT; Expired JWT", expiredJwtException);
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Failed to decode JWT", e);
        }
    }

    private Jws<Claims> decode(JwTokenDecipherRequest jwTokenDecipherRequest) {
        JwtParser build = Jwts.parserBuilder()
                .setSigningKey(jwtKey.publicKeyForVerifying())
                .build();

        return build.parseClaimsJws(jwTokenDecipherRequest.token());
    }


    public Claims decodeClaims(JwTokenDecipherRequest jwTokenDecipherRequest) {
        return decode(jwTokenDecipherRequest).getBody();
    }

    private static class JwsJwtTokenProviderAdapter {
        private static SignatureAlgorithm signatureAlgorithm(Key signingKey) {
            boolean isSigningKeyRSA = "RSA".equals(signingKey.getAlgorithm());
            return isSigningKeyRSA ? SignatureAlgorithm.RS256 : SignatureAlgorithm.HS256;
        }
    }
}
