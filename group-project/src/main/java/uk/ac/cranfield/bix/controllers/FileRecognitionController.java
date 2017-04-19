package uk.ac.cranfield.bix.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
import uk.ac.cranfield.bix.services.PathFinder;
import uk.ac.cranfield.bix.services.ProjectService;
import uk.ac.cranfield.bix.services.UserService;

@Controller
public class FileRecognitionController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private PathFinder pathFinder;
    
    @RequestMapping(value = "/recognizeFileName", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse recognizeFileName(@RequestParam("fileName") String fileName, @RequestParam("projectName") String projectName) throws Exception
    {
        String fileType="", fileExtension;
        String[] splittedFileName;
        boolean isGFF;

        try {
            splittedFileName = fileName.split("\\\\");
            if (splittedFileName.length>1)
                fileName = splittedFileName[splittedFileName.length-1];
            splittedFileName = fileName.split("\\.");
            fileExtension = splittedFileName[splittedFileName.length-1];
            if (fileExtension.equals("gz") || fileExtension.equals("zip") || fileExtension.equals("7z"))
            {
                fileType="zipped";
                return new RestResponse(fileType, "Please unzip file and then upload again");
            }
            else if (checkIfFileExists(projectName, fileName, fileType))
            {
                fileType="existing";
                return new RestResponse(fileType, "File with this name already exists");
            }
            else
            {
                if (fileExtension.equals("fasta") || fileExtension.equals("fa") || fileExtension.equals("frn") || fileExtension.equals("ffn") || fileExtension.equals("fas") || fileExtension.equals("fna") || fileExtension.equals("faa"))
                {
                    fileType="sequence";
                    return new RestResponse(fileType, "");
                }
                else if (fileExtension.equals("gff") || fileExtension.equals("gtf") || fileExtension.equals("gff2") || fileExtension.equals("gff3"))
                {
                    fileType = "annotation";
                    isGFF = checkIfGFF(projectName);
                    if (isGFF)
                        return new RestResponse(fileType, "If you upload new annotation file, old one will be overwritten and expression, differencial expression and transcriptomic coverage files will be removed.");
                    else
                        return new RestResponse(fileType, "");
                }
                else if (fileExtension.equals("vcf"))
                {
                    fileType = "variants";
                    return new RestResponse(fileType, "");
                }
                else if (fileExtension.equals("sorted"))
                {
                    if (splittedFileName[splittedFileName.length-2].equals("results"))
                    {
                        fileType = "difExpression";
                        isGFF = checkIfGFF(projectName);
                        if (isGFF)
                            return new RestResponse(fileType, "");
                        else
                            return new RestResponse(fileType, "Please upload gff file first");
                    }
                    else
                        return new RestResponse("unrecognized", null);
                }
                else if (fileExtension.equals("results"))
                {
                    fileType = "expression";
                    isGFF = checkIfGFF(projectName);
                    if (isGFF)
                        return new RestResponse(fileType, "");
                    else
                        return new RestResponse(fileType, "Please upload gff file first");
                }
                else if (fileExtension.equals("bedcov"))
                {
                    fileType = "bedcov";
                    isGFF = checkIfGFF(projectName);
                    if (isGFF)
                        return new RestResponse(fileType, "");
                    else
                        return new RestResponse(fileType, "Please upload gff file first");
                }
                else
                {
                    fileType="unrecognized";
                    return new RestResponse(fileType, "");
                }
            }
        }
        catch (IOException ex) 
        {
            return new RestResponse(ex.getMessage(), null);
        }
    }
    
    @RequestMapping(value = "/recognizeFileType", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse recognizeFileType(@RequestParam("file") MultipartFile multipartFile) throws Exception
    {
        String fileType="";
        int i;
        InputStream inputStream;
        char c;

        try {
            inputStream = multipartFile.getInputStream();
            while(((i = inputStream.read())!=-1))
            {
                c = (char)i;
                if (c=='>')
                {
                    fileType="sequence";
                }
                if (c=='#')
                {
                    c=(char)inputStream.read();
                    if (c=='#')
                    {
                        c=(char)inputStream.read();
                        if (c=='g')
                            fileType="annotation";
                        else if(c=='v')
                            fileType="variants";
                        else
                            fileType="unrecognized";
                    }
                }
            }
            return new RestResponse(fileType, null);
        }
        catch (IOException ex) 
        {
            return new RestResponse(ex.getMessage(), null);
        }
    }
    
    @SuppressWarnings("empty-statement")
    private boolean checkIfFileExists(String projectName, String fileName, String fileType) throws Exception
    {
        String path, result, newpath="", fileNameFromFile;
        FileReader fileReader;
        BufferedReader bufferedReader;
        Boolean flag=false;
        String [] fileTypes = new String[6], splittedFileName;
        
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//            System.out.println("User is ANONYMOUS");
            //path
            path = pathFinder.getEntireFilePathNotLogged();
            fileTypes[0]="sequence";
            fileTypes[1]="variants";
            fileTypes[2]="bedcov";
            fileTypes[3]="expression";
            fileTypes[4]="difExpression";
            fileTypes[5]="annotation";
            
            for (int i=0;i<fileTypes.length;i++)
            {
                newpath = path+"/"+fileTypes[i];
                //check if file with the same name already exists
                fileReader = new FileReader(newpath+"/contentOfFolder.txt");
                bufferedReader = new BufferedReader(fileReader);
                result=bufferedReader.readLine();
                while (result!=null)
                {
                    splittedFileName = result.split("/");
                    fileNameFromFile = splittedFileName[splittedFileName.length-1];
                    fileNameFromFile = fileNameFromFile.replaceAll("\t", "");
                    int result2 = fileNameFromFile.compareTo(fileName);
                    if (fileNameFromFile.equals(fileName))
                        flag=true;
                    result=bufferedReader.readLine();
                }
                bufferedReader.close();
                fileReader.close();
            }
            return flag;
        }
        else
        {
//            System.out.println("User is LOGGED");
            
            Project project;
            //Find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);

            //Check if project allready exist
            project = projectService.findByProjectName(projectName, user);
            //check if file with the same name already exists
            List<FileInput> findAll = fileService.findAll(project);

            for (FileInput file : findAll)
            {
                if(fileName.equals(file.getF_name()))
                    flag=true;
            }
            return flag;
        }
    }
    @SuppressWarnings("empty-statement")
    private boolean checkIfGFF(String projectName) throws Exception
    {
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//            System.out.println("User is ANONYMOUS");
            String path, result; FileReader fileReader; BufferedReader bufferedReader;
            
            path = pathFinder.getEntireFilePathNotLogged()+"/annotation";
//            path = pathFinder.getGffFilePath("");
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
            boolean flag = false;
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
