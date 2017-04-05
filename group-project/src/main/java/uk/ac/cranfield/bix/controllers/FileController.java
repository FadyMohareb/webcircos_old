package uk.ac.cranfield.bix.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.PathFinder;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;
import uk.ac.cranfield.bix.services.FileService;
import uk.ac.cranfield.bix.services.ProjectService;
import uk.ac.cranfield.bix.services.UserService;
import static uk.ac.cranfield.bix.utilities.Utilities.parseFile;

@Controller
public class FileController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private FileService fileService;
    
    @RequestMapping(value = "/controller/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("String") String fileType) 
    {
        //initializations
        String fileName, path, userID;
        File dir1, dir2;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        String[] splittedFileName;
        
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//          System.out.println("User is ANONYMOUS");
            path = new PathFinder().getEntireFilePathNotLogged();
            try
            {
                //newPath with added FileType
                path=(path+"/"+fileType);
                dir2 = new File(path);
                if (!dir2.exists())
                    dir2.mkdir();

                fileName = multipartFile.getOriginalFilename();
                splittedFileName = fileName.split("\\\\");
                if (splittedFileName.length>1)
                    fileName = splittedFileName[splittedFileName.length-1];

                FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(path+"/"+fileName));
                
                //new file
                fileWriter = new FileWriter(path+"/contentOfFolder.txt",true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.append(path+"/"+fileName);
                bufferedWriter.append("\t");
    //            bufferedWriter.newLine();
                bufferedWriter.close();
                fileWriter.close();
                
                // Parse and serialize files
                parseFile(path+"/"+fileName, fileType);
                
                return new RestResponse(null, null);
            }
            catch (Exception e)
            {
                return new RestResponse(e.getMessage(), null);
            }
        }
        else
        {
            //Find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);
            
            //Check if project allready exist
            String projectName = "blablabla";
            Project project = projectService.findByProjectName(projectName, user);
            Integer projectId = project.getId();
            
//            System.out.println("User is LOGGED");
            path = new PathFinder().getEntireFilePathLogged();
            try
            {
                //newPath with added FileType
                path=(path+"/"+fileType);
                dir2 = new File(path);
                if (!dir2.exists())
                    dir2.mkdir();

                fileName = multipartFile.getOriginalFilename();
                splittedFileName = fileName.split("\\\\");
                if (splittedFileName.length>1)
                    fileName = splittedFileName[splittedFileName.length-1];

                FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(path+"/"+fileName));
                
                // Parse and serialize files
                parseFile(path+"/"+fileName, fileType);
                
                FileInput file = new FileInput();
                file.setF_name(fileName);
                file.setF_path(path+"/"+fileName);
                file.setF_type(fileType);
                file.setProject(project);
                
                fileService.save(file, projectId);
                
                return new RestResponse(null, null);
            }
            catch(Exception e)
            {
                return new RestResponse(e.getMessage(), null);
            }
        }
    }
}
