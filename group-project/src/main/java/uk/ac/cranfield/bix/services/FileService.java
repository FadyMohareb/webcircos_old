package uk.ac.cranfield.bix.services;

import java.util.List;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.Project;

/**
 * FileService is an interface. It is used in controllers as an intermediate to communicate with the database.
 * @author vmuser
 */
public interface FileService {
    void save(FileInput file);
    void delete(FileInput file);
    List<FileInput> findAll(Project project);
    FileInput findByFileType(String fileType, Project p);
    FileInput getByName(String f_name, Project project);
}
