package com.ntw.auth.core;

import com.ntw.auth.config.TestConfig;
import com.ntw.common.entity.Role;
import com.ntw.common.entity.UserAuth;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class LdapAuthTest
        extends TestCase
{
    public static final String AUTH_BEAN_NAME = "authBean";
    private static ApplicationContext springContext;

    private static AuthMgr authBean;

    static {
        springContext = new ClassPathXmlApplicationContext("springBeansLdap.xml");
        authBean = (AuthMgr) springContext.getBean(AUTH_BEAN_NAME);
    }


    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LdapAuthTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( LdapAuthTest.class );
    }

    /**
     * Test User Authentication
     */
    public void UserAuthentication()
    {
        boolean pass = authBean.authenticate(TestConfig.Test_Admin_Auth.getId(),
                                             TestConfig.Test_Admin_Auth.getPassword());
        Assert.assertTrue(pass);
        pass = authBean.authenticate("WrongId",TestConfig.Test_Admin_Auth.getPassword());
        Assert.assertTrue(!pass);
        pass = authBean.authenticate(TestConfig.Test_Admin_Auth.getId(),"WrongPass");
        Assert.assertTrue(!pass);
    }

    /**
     * Test User Details
     */
    public void UserDetails()
    {
        UserAuth userAuth = authBean.getUserAuth(TestConfig.Test_Admin_Auth.getId());
        Assert.assertNotNull(userAuth);
        Assert.assertEquals(TestConfig.Test_Admin_Auth.getId(), userAuth.getId());
        Assert.assertEquals(TestConfig.Test_Admin_Auth.getName(), userAuth.getName());
        Assert.assertEquals(TestConfig.Test_Admin_Auth.getPassword(), userAuth.getPassword());
        Assert.assertEquals(TestConfig.Test_Admin_Auth.getEmailId(), userAuth.getEmailId());
    }

    /**
     * Test User Role
     */
    public void UserRole()
    {
        List<String> userRoles = authBean.getUserRole(TestConfig.Test_Admin_Auth.getId());
        Assert.assertEquals(1, userRoles.size());
        System.out.println(userRoles.get(0));
        Assert.assertEquals(Role.ADMIN.toString(), userRoles.get(0));

        userRoles = authBean.getUserRole(TestConfig.Test_User_Auth.getId());
        Assert.assertEquals(1, userRoles.size());
        Assert.assertEquals(Role.USER.toString(), userRoles.get(0));
    }

    public void testDummyTest()
    {
        Assert.assertTrue(true);
    }

}

