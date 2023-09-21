package com.example.demo.customer.acl;

import com.example.demo.customer.acl.config.UserServiceAccessControlListDeserializer;
import com.example.demo.customer.acl.config.UserServiceAccessControlListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = UserServiceAccessControlListDeserializer.class)
@JsonSerialize(using = UserServiceAccessControlListSerializer.class)
public interface AccessControlList {
    String getAccessControlList();
}
