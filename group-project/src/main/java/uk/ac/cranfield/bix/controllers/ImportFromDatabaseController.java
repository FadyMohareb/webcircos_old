package uk.ac.cranfield.bix.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;
import uk.ac.cranfield.bix.services.FileService;
import uk.ac.cranfield.bix.services.PathFinder;
import uk.ac.cranfield.bix.services.ProjectService;
import uk.ac.cranfield.bix.services.UserService;

@Controller
public class ImportFromDatabaseController 
{
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private PathFinder pathFinder;
    
    @RequestMapping(value = "/import/getAll", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse getAllFiles()
    {
        List<Project> projects;
        Project project;
        String projectName, result = "";
        List<FileInput> allFiles;
        int i, j;
                
        //Find user
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(userLogin);

        //Find projects
        projects = projectService.findAll(user);

        
        //Iterate through projects
        for(i=0; i<projects.size();i++)
        {
            project = projects.get(i);
            allFiles = fileService.findAll(project);
            projectName = project.getP_name();
            for(j=0;j<allFiles.size();j++)
                result = result + projectName + ": " + allFiles.get(j).getF_name() + "\n";
        }
        return new RestResponse(null, result);
    };
    
    @RequestMapping(value = "/import/copyFile", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse copyFile(@RequestParam("newProjectName") String newProjectName, @RequestParam("oldProjectName") String oldProjectName, @RequestParam("oldFileName") String oldFileName) throws Exception
    {
        String oldPath, newPath, fileType;
        Project oldProject, newProject;
        FileInput oldFile, newFile;
        File dir;
        
        try
        {
            newPath = pathFinder.getEntireFilePathLogged(newProjectName);
            oldPath = pathFinder.getEntireFilePathLogged(oldProjectName);

            //Find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);

            //Check if project allready exist
            oldProject = projectService.findByProjectName(oldProjectName, user);
            newProject = projectService.findByProjectName(newProjectName, user);

            oldFile = fileService.getByName(oldFileName, oldProject);
            //check if gff
            fileType = oldFile.getF_type();
            if ("annotation".equals(fileType))
            {
                if (checkIfGFF(newProject))
                {
                    List<FileInput> findAll = fileService.findAll(newProject);
                    for (FileInput file : findAll)
                    {
                        if ("annotation".equals(file.getF_type()))
                            fileService.delete(file);
                        else if ("difExpression".equals(file.getF_type()))
                            fileService.delete(file);
                        else if ("expression".equals(file.getF_type()))
                            fileService.delete(file);
                        else if ("bedcov".equals(file.getF_type()))
                            fileService.delete(file);
                        else
                        {}
                    };
                    new File(newPath+"/annotation").delete();
                    new File(newPath+"/difExpression").delete();
                    new File(newPath+"/expression").delete();
                    new File(newPath+"/bedcov").delete();
                    dir = new File(newPath + "/" + fileType);
                    if (!dir.exists())
                        dir.mkdir();
                }
            }
            //copy file
            FileCopyUtils.copy(new FileInputStream(oldPath + "/" + oldFileName), new FileOutputStream(newPath + "/" + oldFileName));

            // Parse and serialize files
//            parseFile(newPath + "/" + oldFileName, fileType);

            newFile = new FileInput();
            newFile.setF_name(oldFileName);
            newFile.setF_path(newPath + "/" + oldFileName);
            newFile.setF_type(fileType);
            newFile.setProject(newProject);
        
            fileService.save(newFile);
            return new RestResponse(null, null);
        }
        catch(Exception e)
        {
            return new RestResponse("Copying error", e.getMessage());
        }
    }
    @SuppressWarnings("empty-statement")
    private boolean checkIfGFF(Project project) throws Exception
    {
        boolean flag =false;
        List<FileInput> findAll = fileService.findAll(project);

        for (FileInput file : findAll)
        {
            String fileType = file.getF_type();
            if ("annotation".equals(fileType))
                flag = true;
        };
        return flag;
    }
}
