package com.ntw.oms.user.dao.cassandra;

import com.ntw.oms.user.entity.Address;
import com.ntw.oms.user.entity.UserProfile;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.LinkedList;
import java.util.List;

@Table("UserProfile")
public class DBUserProfile {

    @PrimaryKey
    private String id;

    private String name;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + (id == null ? "null" : "\"" + id + "\"") + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") + ", " +
                "\"email\":" + (email == null ? "null" : "\"" + email + "\"") +
                "}";
    }

    public static DBUserProfile createDBUserProfile(UserProfile userProfile) {
        DBUserProfile dbUserProfile = new DBUserProfile();
        dbUserProfile.setId(userProfile.getId());
        dbUserProfile.setName(userProfile.getName());
        dbUserProfile.setEmail(userProfile.getEmail());
        return dbUserProfile;
    }

    public com.ntw.oms.user.entity.UserProfile getUserProfile(List<DBAddress> dbAddresses) {

        Address contactAddress = null;
        List<com.ntw.oms.user.entity.Address> shippingAddresses = new LinkedList<>();

        for (DBAddress dbAddress : dbAddresses) {
            if (dbAddress.getAddressKey().getType().equals("bill")) {
                contactAddress = dbAddress.getAddress();
            } else if (dbAddress.getAddressKey().getType().equals("ship")) {
                shippingAddresses.add(dbAddress.getAddress());
            }
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setId(getId());
        userProfile.setName(getName());
        userProfile.setEmail(getEmail());
        userProfile.setContact(contactAddress.getContact());
        userProfile.setAddress(contactAddress);
        userProfile.setShippingAddresses(shippingAddresses);
        return userProfile;
    }
}
