package com.example.demo.auth.jwt.provider;

import com.example.demo.auth.jwt.model.CreateJwTokenRequest;
import com.example.demo.auth.jwt.model.CreateJwTokenResult;
import com.example.demo.auth.jwt.model.JwToken;
import com.example.demo.auth.jwt.model.JwTokenDecipherRequest;

public interface JwtTokenProvider {

    String ISSUER = "Blog App";

    JwToken readToken(JwTokenDecipherRequest jwTokenDecipherRequest);

    CreateJwTokenResult createToken(CreateJwTokenRequest jwToken);
}
