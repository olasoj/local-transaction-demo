package com.example.demo.customer.acl.config;

import com.example.demo.customer.acl.AccessControlList;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class UserServiceAccessControlListDeserializer extends JsonDeserializer<AccessControlList> {

    @Override
    public AccessControlList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String text = jsonParser.getText();
        return () -> text;
    }
}
