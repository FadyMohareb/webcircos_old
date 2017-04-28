package uk.ac.cranfield.bix.controllers;

import java.io.BufferedReader;
import uk.ac.cranfield.bix.services.PathFinder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    
    /**
     * upload function is responsible for uploading desired file under file type to specified project
     * @param multipartFile MultipartFile users file
     * @param projectName String with project name
     * @param fileType String with recognised file type
     * @return RestResponse null or error
     * @throws Exception 
     */
    @RequestMapping(value = "/controller/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("projectName")String projectName, @RequestParam("fileType") String fileType) throws Exception 
    {
        //initializations
        String fileName, path;
        File dir2, dir1_5;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        String[] splittedFileName;
        
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
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

                //add file to list
                fileWriter = new FileWriter(path+"/contentOfFolder.txt",true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.append(path+"/"+fileName);
                bufferedWriter.append("\t");
                bufferedWriter.close();
                fileWriter.close();
                
                //parse and serialize files. Give an empty String for project name as project do not exist for unlogged user.
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
            //find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);
            
            //get project
            Project project = projectService.findByProjectName(projectName, user);
            
            //get path
            path = pathFinder.getEntireFilePathLogged() + '/' + projectName;
            
            //DFF files
            if ("annotation".equals(fileType))
            {
                if (checkIfGFF(projectName))
                {
                    List<FileInput> findAll = fileService.findAll(project);
                    findAll.forEach((file) -> 
                    {
                        if (null == file.getF_type()) 
                        {
                        } 
                        else switch (file.getF_type()) 
                        {
                            case "annotation":
                                fileService.delete(file);
                                break;
                            case "difExpression":
                                fileService.delete(file);
                                break;
                            case "expression":
                                fileService.delete(file);
                                break;
                            case "bedcov":
                                fileService.delete(file);
                                break;
                            default:
                                break;
                        }
                    });
                    FileUtils.deleteDirectory(new File(path+"/annotation"));
                    FileUtils.deleteDirectory(new File(path+"/difExpression"));
                    FileUtils.deleteDirectory(new File(path+"/expression"));
                    FileUtils.deleteDirectory(new File(path+"/bedcov"));
                }
            }
            try
            {
                //newPath with added ProjectName
                dir1_5 = new File(path);
                if (!dir1_5.exists())
                    dir1_5.mkdir();
                
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

                //save file in database
                fileService.save(file);

                return new RestResponse(null, null);
            }
            catch(Exception e)
            {
                return new RestResponse("fileError", e.getMessage());
            }
        }
    }

    /**
     * checkIfGFF is returning true if annotation file already exists in project
     * @param projectName String with project name
     * @return boolean if gff file exists
     * @throws Exception 
     */
    @SuppressWarnings("empty-statement")
    private boolean checkIfGFF(String projectName) throws Exception
    {
        //initialize
        Project project;
        boolean flag =false;
        String path, result; 
        FileReader fileReader; 
        BufferedReader bufferedReader;
            
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//            System.out.println("User is ANONYMOUS");
            
            //get path
            path = pathFinder.getEntireFilePathNotLogged()+ "/annotation";
            
            //read content of folder
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

            //find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);

            //find project
            project = projectService.findByProjectName(projectName, user);

            //get files
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
    
    /**
     * remove function is responsible for removing selected file from project
     * @param projectName String with project name
     * @param fileName String with file name
     * @return RestResponse with null or errors
     * @throws Exception 
     */
    @RequestMapping(value = "/controller/remove", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse remove(@RequestParam("projectName") String projectName, @RequestParam("fileName") String fileName) throws Exception 
    {
        String path, userLogin, extention, fileNameWE, nameOfFileInDirectory;
        Project project;
        FileInput file;
        File[] allFilesInDirectory;
        int i;
        Path fileToDeletePath;
        
        try
        {
            //find user
            userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);
            
            //get project
            project = projectService.findByProjectName(projectName, user);
            
            //find file
            if (fileName.equals("---"))
            {
//                //check if project has files
//                if (fileService.findAll(project).size()==0)
//                {
//                    
//                }
            }
//            else
                file = fileService.getByName(fileName, project);
            
            //get path
            path = pathFinder.getEntireFilePathLogged() + '/' + projectName + '/' + file.getF_type();// + '/' + fileName;
            File dir = new File(path);
            
            //remove database entry
            fileService.delete(file);
            
            //remove files from directory
                
            //get fileNames
            extention = fileName.split("\\.")[fileName.split("\\.").length-1];
            if ("sorted".equals(extention))
                extention=extention+"."+fileName.split("\\.")[fileName.split("\\.").length-2];
            
            fileNameWE = fileName.substring(0, (fileName.length()-extention.length()-1));
            
            //get list of all files in directory
            allFilesInDirectory = dir.listFiles();
            
            //remove all files connected with that file
            for(i=0;i<allFilesInDirectory.length;i++)
            {
                nameOfFileInDirectory = allFilesInDirectory[i].getName();
                if (nameOfFileInDirectory.startsWith(fileNameWE))
                {
                    fileToDeletePath = Paths.get(allFilesInDirectory[i].getAbsolutePath());
                    Files.deleteIfExists(fileToDeletePath);
                }
            }
            
            return new RestResponse(null, null);
        }
        catch(Exception e)
        {
            return new RestResponse("removeError", e.getMessage());
        }
    }
}