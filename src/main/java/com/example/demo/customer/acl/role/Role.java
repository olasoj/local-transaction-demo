package com.example.demo.customer.acl.role;

import com.example.demo.customer.acl.AccessControlList;

public enum Role implements AccessControlList {
    READ("READ"), WRITE("WRITE");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getAccessControlList() {
        return roleName;
    }
}
