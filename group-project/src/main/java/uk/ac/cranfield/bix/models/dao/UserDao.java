package uk.ac.cranfield.bix.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.ac.cranfield.bix.models.User;

/**
 * Class containing function for all the basic operation that can be done in a database for the USERS table. 
 * @author vmuser
 */
@Repository
@Transactional
public class UserDao {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(User user) {
        getSession().save(user);
        return;
    }

    public void delete(User user) {
        getSession().delete(user);
        return;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return getSession().createQuery("from User").list();
    }

    public User getByEmail(String email) {
        return (User) getSession().createQuery(
                "Select u from User u where u.email = :email")
                .setParameter("email", email)
                .uniqueResult();
    }

    public User getById(long id) {
        return (User) getSession().load(User.class, id);
    }

    public User getByLoginAndPassword(String login, String password) {
        return (User) getSession().createQuery("Select u from User u where u.login =: login AND u.password =: password")
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult();
    }

    public User getByLogin(String login) {
        return (User) getSession()
                .createQuery("Select u from User u where u.login =:login")
                .setParameter("login", login)
                .uniqueResult();
    }

    public void update(User user) {
        getSession().update(user);
        return;
    }

} // class UserDao
