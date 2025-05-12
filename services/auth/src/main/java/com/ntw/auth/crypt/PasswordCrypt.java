package com.ntw.auth.crypt;

public interface PasswordCrypt {

    public String hashPassword(String plainTextPassword);

    public boolean checkPassword(String plainTextPassword, String storedHash);
}
