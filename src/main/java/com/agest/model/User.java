package com.agest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;

    public static User getAdminUser() {
        return new User("administrator", "");
    }

    public static User getUser1() {
        return new User("User1", "123");
    }

    public static User getUser2() {
        return new User("User2", "123");
    }

    public static User getUser3() {
        return new User("User3", "123");
    }
}
