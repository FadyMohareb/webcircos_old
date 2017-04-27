package uk.ac.cranfield.bix.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;

/**
 * Class containing function for all the basic operation that can be done in a database for the Projects table. 
 * @author vmuser
 */
@Repository
@Transactional
public class ProjectDao {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Project project) {
        getSession().save(project);
        return;
    }

    public void delete(Project project) {
        getSession().delete(project);
        return;
    }

    @SuppressWarnings("unchecked")
    public List<Project> getAll(User user) {
        return (List<Project>) getSession()
                .createQuery("Select p from Project p where p.user=:user")
                .setParameter("user", user)
                .list();
    }

    public Project getById(long id) {
        return (Project) getSession().load(Project.class, id);
    }

    public void update(Project user) {
        getSession().update(user);
        return;
    }

    public Project getByName(String projectName, User user) {
        return (Project) getSession()
                .createQuery("Select p from Project p where p.p_name=:project and p.user=:user")
                .setParameter("project", projectName)
                .setParameter("user", user)
                .uniqueResult();
    }
} // class ProjectDao
