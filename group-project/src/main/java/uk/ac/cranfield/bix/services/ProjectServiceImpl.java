/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;
import uk.ac.cranfield.bix.models.dao.ProjectDao;

/**
 *
 * @author solene
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectRepository;

    @Override
    public Project findByProjectName(String projectName, User user) {
        return projectRepository.getByName(projectName, user);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

}
