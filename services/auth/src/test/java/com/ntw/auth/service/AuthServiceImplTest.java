package com.ntw.auth.service;

import com.ntw.auth.config.TestConfig;
import com.ntw.auth.core.AuthMgr;
import com.ntw.auth.entity.OAuthToken;
import com.ntw.auth.mock.MockAuth;
import com.ntw.auth.mock.MockJwtUtility;
import com.ntw.common.entity.UserAuth;
import com.ntw.common.security.JwtUtility;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AuthServiceImplTest extends TestCase
{
    private AuthMgr authBean;
    private JwtUtility jwtUtilBean;
    private AuthServiceImpl authService;

    public void setUp() throws Exception {
        super.setUp();
        authBean = new MockAuth();
        jwtUtilBean = new MockJwtUtility();
        authService = new AuthServiceImpl();
        authService.setAuthBean(authBean);
        authService.setJwtUtilBean(jwtUtilBean);
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AuthServiceImplTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AuthServiceImplTest.class );
    }

    public void testDummy() {
        Assert.assertEquals(true, true);
    }

    /**
     * Test User Authentication
     */
    public void testGetAuthToken() {
        authService.createUserAuth(TestConfig.Test_Admin_Auth);
        OAuthToken authToken = authService.getOAuthToken(TestConfig.Test_Admin_Auth.getId(),
                TestConfig.Test_Admin_Auth.getPassword());
        Assert.assertNotNull(authToken);
        Assert.assertEquals(authToken.getAccess_token(),TestConfig.DUMMY_TOKEN);
    }

    public void testGetUserAuth() {
        UserAuth userAuth = authService.getUserAuth(TestConfig.DUMMY_TOKEN);
        Assert.assertNotNull(userAuth);
        Assert.assertEquals(userAuth.getId(), TestConfig.Test_Admin_Auth.getId());
    }

}

