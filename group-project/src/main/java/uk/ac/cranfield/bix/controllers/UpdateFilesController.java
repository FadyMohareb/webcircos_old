package uk.ac.cranfield.bix.controllers;

import uk.ac.cranfield.bix.services.PathFinder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;
import uk.ac.cranfield.bix.services.FileService;
import uk.ac.cranfield.bix.services.ProjectService;
import uk.ac.cranfield.bix.services.UserService;

@Controller
public class UpdateFilesController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private PathFinder pathFinder;

    /**
     * update function is responsible for returning file lists for all panels
     * @param panelType String with panel type
     * @param projectName String with project name
     * @return RestResponse with file list or error
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse update(@RequestParam("panelType") String panelType, @RequestParam("projectName") String projectName) 
    {
        //initialize
        String path, newPath, type, line, fileList="";
        File dir1, dir2;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        FileReader fileReader;
        BufferedReader bufferedReader;
        
        //file type needs to consist only letters
        type = panelType.replaceAll("[^a-zA-Z]","");
        //check if user is registered
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//          System.out.println("User is ANONYMOUS");
            //get file path
            path = pathFinder.getEntireFilePathNotLogged();
            try
            {
                //newPath with added FileType
                newPath=(path+"/"+type);
                dir1 = new File(newPath);
                //create new directory for user if it doesn't exist or read from existing one
                newPath = newPath+"/contentOfFolder.txt";
                if (!dir1.exists())
                {
                    dir1.mkdir();
                    //new file
                    fileWriter = new FileWriter(newPath, true);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.close();
                    fileWriter.close();
                }
                else
                {
                    //reading from existing file
                    fileReader = new FileReader(newPath);
                    bufferedReader = new BufferedReader(fileReader);
                    while((line = bufferedReader.readLine())!=null)
                    {
                        fileList = fileList + line;
                    }
                    bufferedReader.close();
                    fileReader.close();
                    //alternative solution
    //                listFiles = dir2.listFiles();
    //                System.out.println(Arrays.toString(listFiles));
                }
                return new RestResponse(fileList, null); 
            }
            catch(IllegalStateException | IOException e)
            {
                return new RestResponse(e.getMessage(), e.getMessage());
            }
        }
        else
        {
//            System.out.println("User is LOGGED");
            //get path
            path = pathFinder.getEntireFilePathLogged();
            newPath=(path+"/"+projectName);
                
            try {
                Project project;

                dir1 = new File(newPath);
                if (!dir1.exists())
                {
                    dir1.mkdir();
                }
                //newPath with added FileType
                newPath=(newPath+"/"+type);
                dir2 = new File(newPath);
                if (!dir2.exists())
                {
                    dir2.mkdir();
                }
                //find user
                String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userService.findByUsername(userLogin);

                //get project
                project = projectService.findByProjectName(projectName, user);

                //get all files
                List<FileInput> findAll = fileService.findAll(project);
                List<String> toString = new ArrayList<>();

                //iterate through all files and add to list file names for matching types
                for (FileInput file : findAll)
                {
                    String fileType = file.getF_type();
                    if (type.matches(fileType))
                        toString.add(file.getF_name());
                }

                return new RestResponse(toString.toString(), null);
            }
            catch(Exception e)
            {
                System.out.println(e);
                return new RestResponse(e.getMessage(), e.getMessage());
            }
    }
}
}