package uk.ac.cranfield.bix.models.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.Project;

@Repository
@Transactional
public class FileDao {
    
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
      return _sessionFactory.getCurrentSession();
    }
    
    public void save(FileInput file) {
      getSession().save(file);
    }

    public void delete(FileInput file) {
      getSession().delete(file);
    }

    @SuppressWarnings("unchecked")
    public List<FileInput> getAll(Project project) {
      return (List<FileInput>) getSession()
              .createQuery("Select f from FileInput f where f.project=:project")
              .setParameter("project", project)
              .list(); 
    }
    
    public FileInput getById(long id) {
      return (FileInput) getSession().load(FileInput.class, id);
    }

    public void update(FileInput file) {
      getSession().update(file);
    }
}
