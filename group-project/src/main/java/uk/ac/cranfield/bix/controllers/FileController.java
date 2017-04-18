package uk.ac.cranfield.bix.controllers;

import java.io.BufferedReader;
import uk.ac.cranfield.bix.services.PathFinder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;
import uk.ac.cranfield.bix.services.FileService;
import uk.ac.cranfield.bix.services.ProjectService;
import uk.ac.cranfield.bix.services.UserService;

@Controller
public class FileController {
    @Autowired
    private PathFinder pathFinder;
    
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
        String fileName, path, userID, result;
        File dir1, dir2, dir1_5;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        String[] splittedFileName;
        FileReader fileReader;
        BufferedReader bufferedReader;
        boolean flag=false;
        
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//          System.out.println("User is ANONYMOUS");
            path = pathFinder.getEntireFilePathNotLogged();
            try
            {
                //GFF files
                if ("annotation".equals(fileType))
                {
                    if (checkIfGFF(projectName))
                    {
                        FileUtils.deleteDirectory(new File(path+"/annotation/"));
                        FileUtils.deleteDirectory(new File(path+"/difExpression"));
                        FileUtils.deleteDirectory(new File(path+"/expression"));
                        FileUtils.deleteDirectory(new File(path+"/bedcov"));
                    }
                }
                //newPath with added FileType
                path=(path+"/"+fileType);
                dir2 = new File(path);
                if (!dir2.exists())
                    dir2.mkdir();

                //filename
                fileName = multipartFile.getOriginalFilename();
                splittedFileName = fileName.split("\\\\");
                if (splittedFileName.length>1)
                    fileName = splittedFileName[splittedFileName.length-1];

                //copy file
                FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(path+"/"+fileName));

                //new file
                fileWriter = new FileWriter(path+"/contentOfFolder.txt",true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.append(path+"/"+fileName);
                bufferedWriter.append("\t");
                //bufferedWriter.newLine();
                bufferedWriter.close();
                fileWriter.close();
                
                // Parse and serialize files. Give an empty String for project name as project do not exist for unlogged user.
                pathFinder.parseFile(path+"/"+fileName, fileType,"");
                
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
            
//            System.out.println("User is LOGGED");
            path = pathFinder.getEntireFilePathLogged() + '/' + projectName;
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
                
                //filename
                fileName = multipartFile.getOriginalFilename();
                splittedFileName = fileName.split("\\\\");
                if (splittedFileName.length>1)
                    fileName = splittedFileName[splittedFileName.length-1];
                
                //copy file
                FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(path+"/"+fileName));

                // Parse and serialize files
                pathFinder.parseFile(path+"/"+fileName, fileType, projectName);
                
                FileInput file = new FileInput();
                file.setF_name(fileName);
                file.setF_path(path+"/"+fileName);
                file.setF_type(fileType);
                file.setProject(project);

                fileService.save(file);

                return new RestResponse(null, null);
            }
            catch(Exception e)
            {
                return new RestResponse("fileError", e.getMessage());
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
            
//            path = pathFinder.getGffFilePath(projectName);
            path = pathFinder.getEntireFilePathNotLogged()+ "/annotation";
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
