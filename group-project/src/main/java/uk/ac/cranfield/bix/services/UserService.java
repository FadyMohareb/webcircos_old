package uk.ac.cranfield.bix.services;


import uk.ac.cranfield.bix.models.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
    
    void changePassword(String login, String password);
    
}
