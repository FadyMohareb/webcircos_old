package uk.ac.cranfield.bix.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
