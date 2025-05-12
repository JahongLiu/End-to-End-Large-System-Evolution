package com.ntw.auth.core;

import com.ntw.auth.crypt.PasswordCrypt;
import com.ntw.auth.crypt.PasswordCryptFactory;
import com.ntw.common.entity.UserAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

public abstract class DBAuthMgr implements AuthMgr {

    private static Logger logger = LoggerFactory.getLogger(DBAuthMgr.class);

    @Value("${auth.password.plain:false}")
    private Boolean usePlainTextPassword;

    @Autowired
    private PasswordCryptFactory passwordCryptFactory;

    private PasswordCrypt passwordCrypt;

    public PasswordCrypt getPasswordCrypt() {
        return passwordCrypt;
    }

    public void setPasswordCrypt(PasswordCrypt passwordCrypt) {
        this.passwordCrypt = passwordCrypt;
    }

    @PostConstruct
    public void postConstruct()
    {
        passwordCrypt = passwordCryptFactory.getPasswordCrypt(usePlainTextPassword);
    }

    @Override
    public boolean createUser(UserAuth userAuth) {
        String hashedPassword = getPasswordCrypt().hashPassword(userAuth.getPassword());
        Object userAuthRet = createUser(userAuth, hashedPassword);
        return userAuthRet == null ? false : true;
    }

    @Override
    public boolean authenticate(String userId, String passwordParam) {
        UserAuth userAuth = getUserAuth(userId);
        if (userAuth == null) {
            logger.warn("User {} does not exist", userId);
            return false;
        }
        String dbPassword = userAuth.getPassword();
        if (dbPassword != null) {
            if (getPasswordCrypt().checkPassword(passwordParam, dbPassword)) {
                return true;
            }
            logger.warn("Incorrect password {} for user id {}", passwordParam, userId);
        }
        return false;
    }

    @Override
    public List<String> getUserRole(String userId) {
        UserAuth userAuth = getUserAuth(userId);
        return (userAuth == null) ? new LinkedList<>() : userAuth.getRoles();
    }

    @Override
    public abstract boolean deleteUsers();

    @Override
    public abstract UserAuth getUserAuth(String userId);

    @Override
    public abstract List<UserAuth> getAllUserAuth();

    protected abstract UserAuth createUser(UserAuth userAuth, String hashedPassword);
}
