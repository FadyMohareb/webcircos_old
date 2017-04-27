package uk.ac.cranfield.bix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cranfield.bix.models.User;
import uk.ac.cranfield.bix.models.dao.UserDao;
import uk.ac.cranfield.bix.utilities.BixPasswordEncoder;

/**
 * File UserServiceImpl override all methods from UserService.. 
 * UserService is used everywhere retrieving user id is needed. 
 * @author vmuser
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userRepository;

    @Override
    public void save(User user) {
        user.setPassword(BixPasswordEncoder.getInstance().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.getByLogin(username);
    }
    
    @Override
    public void changePassword(String login, String password){
        User currentUser = userRepository.getByLogin(login);
        currentUser.setPassword(BixPasswordEncoder.getInstance().encode(password)); 
        userRepository.update(currentUser);
    }
}
