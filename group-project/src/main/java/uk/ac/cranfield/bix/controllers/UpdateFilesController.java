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
    
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse update(@RequestParam("panelType") String panelType, @RequestParam("projectName") String projectName) 
    {
        
        String path, newPath, type, line, fileList="";
        File dir1, dir2;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        FileReader fileReader;
        BufferedReader bufferedReader;
        
        //file type needs to consist only letters
        type = panelType.replaceAll("[^a-zA-Z]","");
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//          System.out.println("User is ANONYMOUS");
            path = pathFinder.getEntireFilePathNotLogged();
            try
            {
                //newPath with added FileType
                newPath=(path+"/"+type);
                dir1 = new File(newPath);
                //creating new directory for user if it doesn't exist or reading from existing one
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
    //                System.out.println(fileList);
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
                path = pathFinder.getEntireFilePathLogged();
                newPath=(path+"/"+projectName);
                
                try {
                    Project project, project2;
                    //newPath with added FileType
                    
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
                    //Find user
                    String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
                    User user = userService.findByUsername(userLogin);

                //Check if project allready exist
                project = projectService.findByProjectName(projectName, user);

                List<FileInput> findAll = fileService.findAll(project);
                List<String> toString = new ArrayList<>();

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
    
