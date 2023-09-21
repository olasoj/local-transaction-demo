package com.example.demo.auth.jwt.model;

import com.example.demo.customer.acl.AccessControlList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateJwTokenRequest {

    private final String subject;
    private final List<AccessControlList> accessControlLists;

    public CreateJwTokenRequest(CreateJwTokenRequestBuilder createJwTokenRequestBuilder) {
        this.subject = createJwTokenRequestBuilder.subject;
        this.accessControlLists = createJwTokenRequestBuilder.accessControlLists;
    }

    public static CreateJwTokenRequestBuilder builder() {
        return new CreateJwTokenRequestBuilder();
    }

    public String subject() {
        return subject;
    }

    public List<AccessControlList> accessControlLists() {
        return accessControlLists;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.subject)
                .append(this.accessControlLists)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof final CreateJwTokenRequest jwToken))
            return false;

        return new EqualsBuilder()
                .append(this.subject, jwToken.subject)
                .append(this.accessControlLists, jwToken.accessControlLists)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("subject", this.subject)
                .append("accessControlLists", this.accessControlLists)
                .toString();
    }

    public static class CreateJwTokenRequestBuilder {
        private String subject;
        private List<AccessControlList> accessControlLists;


        public CreateJwTokenRequestBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public CreateJwTokenRequestBuilder accessControlLists(List<AccessControlList> accessControlLists) {
            this.accessControlLists = accessControlLists;
            return this;
        }

        public CreateJwTokenRequest build() {
            return new CreateJwTokenRequest(this);
        }
    }

}
