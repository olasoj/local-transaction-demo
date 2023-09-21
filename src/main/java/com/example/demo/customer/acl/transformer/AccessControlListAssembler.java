package com.example.demo.customer.acl.transformer;

import com.example.demo.customer.acl.AccessControlList;
import org.springframework.util.Assert;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class AccessControlListAssembler {

    private AccessControlListAssembler() {
    }

    public static List<String> toAccessControlListString(List<AccessControlList> accessControlLists) {
        Assert.notNull(accessControlLists, "AccessControlLists Cannot be null");

        return accessControlLists
                .stream().map(AccessControlList::getAccessControlList)
                .collect(toList());
    }

    public static List<AccessControlList> toAccessControlList(List<String> accessControlLists) {
        Assert.notNull(accessControlLists, "AccessControlLists Cannot be null");

        return accessControlLists.stream()
                .map(accessControlList -> (AccessControlList) () -> accessControlList)
                .collect(toList());
    }
}
