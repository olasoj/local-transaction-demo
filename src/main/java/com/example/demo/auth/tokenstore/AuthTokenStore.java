package com.example.demo.auth.tokenstore;

import java.util.Optional;

public interface AuthTokenStore {

    String create(String accessToken);

    Optional<StoreAuthToken> read(String tokenId);

    void revoke(String tokenId);

    StoreAuthToken update(String tokenId, String accessToken);

}
