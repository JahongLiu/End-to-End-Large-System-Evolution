package com.ntw.auth.core;

import com.ntw.common.entity.UserAuth;

import java.util.List;

public interface AuthMgr {

    public boolean createUser(UserAuth userAuth);

    public boolean authenticate(String userId, String password);

    public UserAuth getUserAuth(String userId);

    public List<UserAuth> getAllUserAuth();

    public List<String> getUserRole(String userId);

    public boolean deleteUsers();
}