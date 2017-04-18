package uk.ac.cranfield.bix.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;
import uk.ac.cranfield.bix.models.dao.FileDao;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao FileRepository;

    @Override
    public void save(FileInput file) {
        FileRepository.save(file);
    }

    @Override
    public void delete(FileInput file) {
        FileRepository.save(file);
    }
    
    @Override
    public List<FileInput> findAll(Project project){
        return FileRepository.getAll(project);
    }
    
    @Override
    public FileInput getByName(String f_name, Project project)
    {
        return FileRepository.getByName(f_name, project);
    }
}
