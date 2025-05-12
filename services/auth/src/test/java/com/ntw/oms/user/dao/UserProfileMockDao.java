package com.ntw.oms.user.dao;

import com.ntw.oms.user.config.TestConfig;
import com.ntw.oms.user.entity.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserProfileMockDao implements UserProfileDao {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileMockDao.class);

    @Override
    public boolean createUserProfile(UserProfile userProfile) {
        logger.debug("Added UserProfile; context={}", userProfile);
        return true;
    }

    @Override
    public UserProfile getUserProfile(String id) {
        UserProfile userProfile = TestConfig.createUserProfile(id);
        if (id.equals(TestConfig.TEST_USER_PROFILE_ID_2)) {
            logger.debug("Could not fetch UserProfile; context={}", id);
            return null;
        }
        logger.debug("Fetched UserProfile; context={}", userProfile);
        return userProfile;
    }

    @Override
    public boolean modifyUserProfile(UserProfile userProfile) {
        if (userProfile.getId().equals(TestConfig.TEST_USER_PROFILE_ID_2)) {
            logger.debug("Unable to modify UserProfile; context={}", userProfile);
            return false;
        }
        logger.debug("Modified UserProfile; context={}", userProfile);
        return true;
    }

    @Override
    public boolean removeUserProfile(String id) {
        if (id.equals(TestConfig.TEST_USER_PROFILE_ID_2)) {
            logger.debug("Not able to remove UserProfile; userId={}", id);
            return false;
        }
        logger.debug("Removed UserProfile; userId={}", id);
        return true;
    }

    @Override
    public boolean removeUserProfiles() {
        return false;
    }

}
