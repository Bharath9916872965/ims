package com.vts.ims.login;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
