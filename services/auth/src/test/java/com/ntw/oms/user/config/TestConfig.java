package com.ntw.oms.user.config;

import com.ntw.oms.user.entity.Address;
import com.ntw.oms.user.entity.Contact;
import com.ntw.oms.user.entity.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class TestConfig {

    static Contact createContact(String id) {
        Contact contact = new Contact();
        contact.setName(id.substring(0, 1).toUpperCase() + id.substring(1));
        contact.setEmail(id+"@test.com");
        contact.setTelephone("0001234567");
        return contact;
    }

    static Address createAddress(String id) {
        Address address = new Address();
        address.setStreet(id+", 12th cross, 2nd main");
        address.setArea("Indira Nagar");
        address.setCity("Bangalore");
        address.setState("KA");
        address.setCountry("IN");
        address.setContact(createContact(id));
        return address;
    }

    public static UserProfile createUserProfile(String id) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        userProfile.setName(id.substring(0, 1).toUpperCase() + id.substring(1));
        userProfile.setEmail(id+"@test.com");
        userProfile.setContact(createContact(id));
        userProfile.setAddress(createAddress("1"));
        List<Address> shipAddresses = new ArrayList<>();
        shipAddresses.add(createAddress("2"));
        shipAddresses.add(createAddress("3"));
        userProfile.setShippingAddresses(shipAddresses);
        return userProfile;
    }

    public static final String TEST_USER_PROFILE_ID_1 = "ID_1";
    public static final String TEST_USER_PROFILE_ID_2 = "ID_2";

}
