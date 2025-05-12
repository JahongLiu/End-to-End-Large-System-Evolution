package com.ntw.auth.config;

import com.ntw.common.entity.UserAuth;

public class TestConfig {
    private static UserAuth createAdminUserAuth() {
        UserAuth userAuth = new UserAuth();
        userAuth.setId("jahong");
        userAuth.setPassword("password");
        userAuth.setName("Jahong");
        userAuth.getRoles().add("Admin");
        userAuth.getRoles().add("User");
        userAuth.setEmailId("jahongliu@gmai.com");
        return userAuth;
    }

    private static UserAuth createUserAuth() {
        UserAuth userAuth = new UserAuth();
        userAuth.setId("admin");
        userAuth.setPassword("password");
        userAuth.setName("Admin");
        userAuth.getRoles().add("User");
        userAuth.setEmailId("jiahongliudaily@gmail.com");
        return userAuth;
    }

    public static final UserAuth Test_Admin_Auth = createAdminUserAuth();
    public static final UserAuth Test_User_Auth = createUserAuth();

    public static final String DUMMY_TOKEN = "DUMMY_TOKEN";

}
