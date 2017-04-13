/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.services;

import java.util.List;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;

/**
 *
 * @author solene
 */
public interface FileService {
    void save(FileInput file, int projectId);
    List<FileInput> findAll(Project project);
    FileInput findByFileType(String fileType, Project p);
}
