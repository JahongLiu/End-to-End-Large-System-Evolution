package com.ntw.common.security;

import com.ntw.common.entity.UserAuth;

/**
 * JwtUtility provides helper interface over JWT Token libraries
 */
public interface JwtUtility {

    public UserAuth parseToken(String token);

    public String generateToken(UserAuth u);
}