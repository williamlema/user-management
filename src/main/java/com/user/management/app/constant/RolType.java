package com.user.management.app.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RolType {
    ADMIN(Long.valueOf(1)),
    AGENT(Long.valueOf(2)),
    CUSTOMER(Long.valueOf(3));

    Long id;

    public static boolean isAdmin(Long id){
        return id.equals(ADMIN.id);
    }

    public static boolean isAgent(Long id){
        return id.equals(AGENT.id);
    }
}
