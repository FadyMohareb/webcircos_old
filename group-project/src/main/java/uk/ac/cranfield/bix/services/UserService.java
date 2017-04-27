package uk.ac.cranfield.bix.services;


import uk.ac.cranfield.bix.models.User;

/**
 * UserService is an interface. It is used in controllers as an intermediate to communicate with the database.
 * @author vmuser
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);
    
    void changePassword(String login, String password);
    
}
