package com.example.demo.auth.tokenstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.example.demo.utils.Utils.sessionId;


@Service
public class InMemoryAuthTokenStore implements AuthTokenStore {
    public static final Logger LOGGER = LoggerFactory.getLogger(InMemoryAuthTokenStore.class);

    private final ConcurrentMap<String, StoreAuthToken> storeAuthTokens;

    protected InMemoryAuthTokenStore() {
        this.storeAuthTokens = new ConcurrentHashMap<>();
    }

    @Override
    public String create(String accessToken) {
        LOGGER.info("Creating Database  Auth Token");

        String sessionId = sessionId();
        StoreAuthToken storeAuthToken = new StoreAuthToken(accessToken, sessionId);

        synchronized (storeAuthTokens) {
            storeAuthTokens.put(sessionId, storeAuthToken);
            return sessionId;
        }
    }

    @Override
    public Optional<StoreAuthToken> read(String tokenId) {
        LOGGER.info("Reading Auth Token");

        StoreAuthToken storeAuthToken = storeAuthTokens.get(tokenId);
        return Optional.ofNullable(storeAuthToken);
    }

    @Override
    public StoreAuthToken update(String tokenId, String accessToken) {
        LOGGER.info("Updating Auth Token");

        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatusCode.valueOf(401), "Not Auth Token Found");

        StoreAuthToken storeAuthToken = read(tokenId)
                .orElseThrow(() -> responseStatusException);

        StoreAuthToken newStoreAuthToken = new StoreAuthToken(accessToken, tokenId);

        synchronized (storeAuthTokens) {
            storeAuthTokens.put(storeAuthToken.tokenId(), storeAuthToken);
            return newStoreAuthToken;
        }
    }

    public void revoke(String tokenId) {
        LOGGER.info("Revoking Auth Token");

        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatusCode.valueOf(401), "Not Auth Token Found");

        StoreAuthToken storeAuthToken = read(tokenId)
                .orElseThrow(() -> responseStatusException);

        synchronized (storeAuthTokens) {
            storeAuthTokens.remove(storeAuthToken.tokenId());
        }
    }
}

