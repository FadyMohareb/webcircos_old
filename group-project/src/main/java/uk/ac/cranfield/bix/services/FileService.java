package uk.ac.cranfield.bix.services;

import java.util.List;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.Project;

public interface FileService {
    void save(FileInput file);
    void delete(FileInput file);
    List<FileInput> findAll(Project project);
}
