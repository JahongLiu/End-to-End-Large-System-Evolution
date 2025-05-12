package com.ntw.auth.service;

import com.ntw.auth.core.AuthMgr;
import com.ntw.auth.core.AuthMgrFactory;
import com.ntw.auth.entity.OAuthToken;
import com.ntw.common.entity.UserAuth;
import com.ntw.common.security.JwtUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

@Component
public class AuthServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthMgrFactory authMgrFactory;

    private AuthMgr authBean;

    @Autowired
    private JwtUtility jwtUtilBean;

    @Value("${database.type}")
    private String authMgrType;

    @Value("${auth.client.id:web-client}")
    private String authClientId;

    @Value("${auth.client.secret:secret}")
    private String authClientSecret;

    @PostConstruct
    public void postConstruct()
    {
        this.authBean = authMgrFactory.getAuthMgr(authMgrType);
    }

    public void setAuthBean(AuthMgr authBean) {
        this.authBean = authBean;
    }

    public AuthMgr getAuthBean() {
        return authBean;
    }

    public void setJwtUtilBean(JwtUtility jwtUtilBean) {
        this.jwtUtilBean = jwtUtilBean;
    }

    public JwtUtility getJwtUtilBean() {
        return jwtUtilBean;
    }

    public OAuthToken getOAuthToken(String userId, String password) {
        boolean isAuthenticated = getAuthBean().authenticate(userId,password);

        if (!isAuthenticated) {
            logger.info("User log in FAILED; userId={}", userId);
            return null;
        }

        UserAuth userAuth = getAuthBean().getUserAuth(userId);
        List<String> userRoleIds = getAuthBean().getUserRole(userId);
        userAuth.setRoles(userRoleIds);
        logger.info("User log in successful; context={}", userAuth);

        String token = getJwtUtilBean().generateToken(userAuth);
        OAuthToken authToken = new OAuthToken(token);
        logger.debug("Generated auth token; userId={}; context={}", userId, authToken);

        return authToken;
    }

    public UserAuth getUserAuth(String accessToken) {
        if (accessToken == null) {
            logger.error("Access token sent is null");
            return null;
        }
        return getJwtUtilBean().parseToken(accessToken);
    }

    public List<UserAuth> getUserAuthList() {
        List<UserAuth> userAuthList = getAuthBean().getAllUserAuth();
        userAuthList.sort(new Comparator<UserAuth>() {
            @Override
            public int compare(UserAuth o1, UserAuth o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return userAuthList;
    }

    public boolean createUserAuth(UserAuth userAuth) {
        return getAuthBean().createUser(userAuth);
    }

    public boolean deleteUsersAuth() {
        return getAuthBean().deleteUsers();
    }

    /**
     *
     * @param basicAuthHeader     Authorization: Basic base64(user:pass)
     * @return true if client is authenticated
     */
    boolean authenticateClient(String basicAuthHeader) {
        String base64Credentials = basicAuthHeader.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String userColonPass = new String(credDecoded, StandardCharsets.UTF_8);
        final String[] credentials = userColonPass.split(":", 2);
        if (credentials[0].equals(authClientId)) {
            if (credentials[1].equals(authClientSecret)) {
                logger.debug("Client validated as {}", authClientId);
                return true;
            }
        }
        logger.warn("Incorrect client credentials {}", userColonPass);
        return false;
    }

}
