package com.ntw.auth.mock;

import com.ntw.auth.config.TestConfig;
import com.ntw.common.entity.UserAuth;
import com.ntw.common.security.JwtUtility;

public class MockJwtUtility implements JwtUtility {

    @Override
    public UserAuth parseToken(String token) {
        if (token.equals(TestConfig.DUMMY_TOKEN)) {
            return TestConfig.Test_Admin_Auth;
        }
        return null;
    }

    @Override
    public String generateToken(UserAuth userAuth) {
        return TestConfig.DUMMY_TOKEN;
    }

}
