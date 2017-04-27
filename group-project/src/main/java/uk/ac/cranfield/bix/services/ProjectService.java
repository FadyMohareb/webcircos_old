/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.services;

import java.util.List;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;

/**
 * ProjectService is an interface. It is used in controllers as an intermediate to communicate with the database. 
 * Especially the ProjectController uses this service to check if project get and save projects. 
 * @author solene
 */
public interface ProjectService {
    Project findByProjectName(String projectName, User user);
    void save(Project project);
    void delete(Project project);
    List<Project> findAll(User user);
}
