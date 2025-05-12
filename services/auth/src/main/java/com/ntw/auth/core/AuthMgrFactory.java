package com.ntw.auth.core;

public interface AuthMgrFactory {
    AuthMgr getAuthMgr(String authMgrType);
}
