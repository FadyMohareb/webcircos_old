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
import uk.ac.cranfield.bix.models.File;

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
  
  public void save(File file) {
    getSession().save(file);
    return;
  }
  
  public void delete(File file) {
    getSession().delete(file);
    return;
  }
  
  @SuppressWarnings("unchecked")
  public List<File> getAll() {
    return getSession().createQuery("from File").list();
  }
  
  public File getById(long id) {
    return (File) getSession().load(File.class, id);
  }
  
  public void update(File file) {
    getSession().update(file);
    return;
  }
    
}
