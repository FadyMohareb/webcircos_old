package uk.ac.cranfield.bix.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
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
    RestResponse upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("projectName")String projectName, @RequestParam("fileType") String fileType) throws Exception 
    {
        
        //initializations
        String fileName, path, userID;
        File dir1, dir2, dir1_5;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        String[] splittedFileName;
        
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//          System.out.println("User is ANONYMOUS");
            path = new PathFinder().getUserPathNotLogged();
            try
            {
                if ("annotation".equals(fileType))
                {
                    if (checkIfGFF(projectName))
                    {
                        new File(path+"/annotation").delete();
                        new File(path+"/difExpression").delete();
                        new File(path+"/expression").delete();
                        new File(path+"/bedcov").delete();
                    }
                }
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
//            System.out.println("User is LOGGED");
            //Find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);
            
            //Check if project allready exist
            Project project = projectService.findByProjectName(projectName, user);
            Integer projectId = project.getId();
            
            PathFinder pathFinder = new PathFinder();
            path = pathFinder.getUserPathLogged() + '/' + projectName;
            
            if ("annotation".equals(fileType))
                {
                    if (checkIfGFF(projectName))
                    {
                        new File(path+"/annotation").delete();
                        //remove from database
                        new File(path+"/difExpression").delete();
                        //remove from database
                        new File(path+"/expression").delete();
                        //remove from database
                        new File(path+"/bedcov").delete();
                        //remove from database
                    }
                }
            try
            {
                //newPath with added ProjectName
                dir1_5 = new File(path);
                if (!dir1_5.exists())
                    dir1_5.mkdir();
                //temporary solution
                pathFinder.setCurrentPath(path);
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
    @SuppressWarnings("empty-statement")
    private boolean checkIfGFF(String projectName) throws Exception
    {
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//            System.out.println("User is ANONYMOUS");
            String path, result; FileReader fileReader; BufferedReader bufferedReader;
            
            path = new PathFinder().getGffPath();
            fileReader = new FileReader(path+"/contentOfFolder.txt");
            bufferedReader = new BufferedReader(fileReader);
            result = bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();
            return result!=null;
        }
        else
        {
//            System.out.println("User is LOGGED");
            Project project;
            boolean flag =false;
            //Find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);

            //Check if project allready exist
            project = projectService.findByProjectName(projectName, user);

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
}
