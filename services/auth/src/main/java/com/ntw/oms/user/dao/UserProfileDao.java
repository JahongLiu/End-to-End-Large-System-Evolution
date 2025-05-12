package com.ntw.oms.user.dao;

import com.ntw.oms.user.entity.UserProfile;

public interface UserProfileDao {

    public boolean createUserProfile(UserProfile userProfile);

    public UserProfile getUserProfile(String userId);

    public boolean modifyUserProfile(UserProfile userProfile);

    public boolean removeUserProfile(String id);

    public boolean removeUserProfiles();
}
