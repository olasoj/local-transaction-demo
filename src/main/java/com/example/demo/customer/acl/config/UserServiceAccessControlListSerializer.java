package com.example.demo.customer.acl.config;

import com.example.demo.customer.acl.AccessControlList;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class UserServiceAccessControlListSerializer extends JsonSerializer<AccessControlList> {


    @Override
    public void serialize(AccessControlList value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getAccessControlList());
    }
}
