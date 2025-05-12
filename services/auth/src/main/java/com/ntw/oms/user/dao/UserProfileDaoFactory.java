package com.ntw.oms.user.dao;

public interface UserProfileDaoFactory {
    public UserProfileDao getUserProfileDao(String dbType);
}
