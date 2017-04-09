/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.models.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;

/**
 *
 * @author s262012
 */
@Repository
@Transactional
public class FileDao {
    
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
      return _sessionFactory.getCurrentSession();
    }
    
    public void save(FileInput file, int projectId) {
      getSession().save(file);
      return;
    }

    public void delete(FileInput file) {
      getSession().delete(file);
      return;
    }

    @SuppressWarnings("unchecked")
    public List<FileInput> getAll(Project project) {
      return (List<FileInput>) getSession()
              .createQuery("Select f from FileInput f where f.project=:project")
              .setParameter("project", project)
              .list(); 
    }
    
//        public Project getByName(String projectName, User user) {
//        return (Project) getSession()
//                .createQuery("Select p from Project p where p.p_name=:project and p.user=:user")
//                .setParameter("project", projectName)
//                .setParameter("user", user)
//                .uniqueResult();
//        @SuppressWarnings("unchecked")
//    public List<Project> getAll(User user) {
//        return (List<Project>) getSession()
//                .createQuery("Select p from Project p where p.user=:user")
//                .setParameter("user", user)
//                .list();

    public FileInput getById(long id) {
      return (FileInput) getSession().load(FileInput.class, id);
    }

    public void update(FileInput file) {
      getSession().update(file);
      return;
    }
}
