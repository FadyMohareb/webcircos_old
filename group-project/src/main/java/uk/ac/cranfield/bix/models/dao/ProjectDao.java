package uk.ac.cranfield.bix.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.ac.cranfield.bix.models.Project;

@Repository
@Transactional
public class ProjectDao {
  
  @Autowired
  private SessionFactory _sessionFactory;
  
  private Session getSession() {
    return _sessionFactory.getCurrentSession();
  }

  public void save(Project user) {
    getSession().save(user);
    return;
  }
  
  public void delete(Project user) {
    getSession().delete(user);
    return;
  }
  
  @SuppressWarnings("unchecked")
  public List<Project> getAll() {
    return getSession().createQuery("from Project").list();
  }

  public Project getById(long id) {
    return (Project) getSession().load(Project.class, id);
  }

  public void update(Project user) {
    getSession().update(user);
    return;
  }

} // class ProjectDao
