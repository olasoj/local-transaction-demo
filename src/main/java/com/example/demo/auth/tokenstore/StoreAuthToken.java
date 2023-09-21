package com.example.demo.auth.tokenstore;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class StoreAuthToken {

    private final String accessToken;
    private final String tokenId;

    public StoreAuthToken(String accessToken, String tokenId) {

        Assert.notNull(tokenId, "TokenId cannot be null");
        this.tokenId = tokenId;

        Assert.notNull(accessToken, "AccessToken cannot be null");
        this.accessToken = accessToken;
    }

    public String accessToken() {
        return accessToken;
    }

    public String tokenId() {
        return tokenId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof StoreAuthToken otherStoreAuthToken)) return false;

        return new EqualsBuilder()
                .append(accessToken, otherStoreAuthToken.accessToken)
                .append(tokenId, otherStoreAuthToken.tokenId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(accessToken)
                .append(tokenId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("accessToken", accessToken)
                .append("tokenId", tokenId)
                .toString();
    }
}
