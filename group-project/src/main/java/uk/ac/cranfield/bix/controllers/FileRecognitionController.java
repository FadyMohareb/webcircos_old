package uk.ac.cranfield.bix.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    /**
     * recognizeFileName recognizes file type by extension from file name
     * @param fileName String with file name
     * @param projectName String with project name
     * @return RestResponse with null or error
     * @throws Exception
     */
    @RequestMapping(value = "/recognizeFileName", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse recognizeFileName(@RequestParam("fileName") String fileName, @RequestParam("projectName") String projectName) throws Exception
    {
        String fileType="", fileExtension;
        String[] splittedFileName;
        boolean isGFF;

        try {
            //trap if instead of file name front provides path
            splittedFileName = fileName.split("\\\\");
            if (splittedFileName.length>1)
                fileName = splittedFileName[splittedFileName.length-1];

            //split file name through "."
            splittedFileName = fileName.split("\\.");
            //get file extension
            fileExtension = splittedFileName[splittedFileName.length-1];
            //zipped files
            if (fileExtension.equals("gz") || fileExtension.equals("zip") || fileExtension.equals("7z"))
            {
                fileType="zipped";
                return new RestResponse(fileType, "Please unzip file and then upload again");
            }
            else if (checkIfFileExists(projectName, fileName, fileType))
            {
                //files with same name
                fileType="existing";
                return new RestResponse(fileType, "File with this name already exists");
            }
            else
            {
                //recognized file extensions
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
                    //check if extension equals "results.sorted"
                    if (splittedFileName[splittedFileName.length-2].equals("results"))
                    {
                        fileType = "difExpression";
                        //provide gff before diferential expression
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
                    //provide annotation before expresion
                    isGFF = checkIfGFF(projectName);
                    if (isGFF)
                        return new RestResponse(fileType, "");
                    else
                        return new RestResponse(fileType, "Please upload gff file first");
                }
                else if (fileExtension.equals("bedcov"))
                {
                    fileType = "bedcov";
                    //provide annotation before transcriptomic coverage
                    isGFF = checkIfGFF(projectName);
                    if (isGFF)
                        return new RestResponse(fileType, "");
                    else
                        return new RestResponse(fileType, "Please upload gff file first");
                }
                else
                {
                    //file wasn't recognized by extension
                    fileType="";
                    return new RestResponse(fileType, "");
                }
            }
        }
        catch (IOException ex) 
        {
            return new RestResponse(ex.getMessage(), null);
        }
    }

    /**
     * recognizeFileType recognizes file by content of first line
     * @param multipartFile
     * @param projectName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/recognizeFileType", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse recognizeFileType(@RequestParam("file") MultipartFile multipartFile, @RequestParam("projectName") String projectName) throws Exception
    {
        String fileType="", fileName, firstLine, message = "";
        InputStream inputStream;
        boolean isGFF;
        BufferedReader bufferedReader;

        try {
            //get file name
            fileName = multipartFile.getOriginalFilename();
            //read first line of file
            inputStream = multipartFile.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            firstLine = bufferedReader.readLine();
            //check if annotation file is already in project
            isGFF = checkIfGFF(projectName);
            while(firstLine!=null && "".equals(fileType))
            {
                if (firstLine.startsWith(">"))
                    fileType="sequence";
                else if (firstLine.startsWith("##gff"))
                {
                    fileType="annotation";
                    //if annotation file is already there it will be replaced
                    if (isGFF)
                        message = "If you upload new annotation file, old one will be overwritten and expression, differencial expression and transcriptomic coverage files will be removed.";
                }
                else if(firstLine.startsWith("##fileformat=VCF"))
                    fileType="variants";
                else if(firstLine.startsWith("\"PPEE\""))
                {
                    fileType = "difExpression";
                    if (!isGFF)
                        message = "Please upload gff file first";
                }
                else if(firstLine.startsWith("gene_id"))
                {
                    fileType = "expression";
                    if (!isGFF)
                        message = "Please upload gff file first";
                }
                else if(firstLine.startsWith("mRNA:"))
                {
                    fileType = "bedcov";
                    if (!isGFF)
                        message = "Please upload gff file first";
                }
                else
                    fileType="unrecognized";
            }
            return new RestResponse(fileType, message);
            }
        catch (IOException ex) 
        {
            return new RestResponse(ex.getMessage(), null);
        }
    }

    /**
     * checkIfFileExists returns true if file with given name already exists
     * @param projectName String with project name
     * @param fileName String with file name
     * @param fileType String with file type
     * @return boolean
     * @throws Exception
     */
    @SuppressWarnings("empty-statement")
    private boolean checkIfFileExists(String projectName, String fileName, String fileType) throws Exception
    {
        String path, result, newpath="", fileNameFromFile;
        FileReader fileReader;
        BufferedReader bufferedReader;
        Boolean flag=false;
        String [] fileTypes = new String[6], splittedFileName;
        Project project;

        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//            System.out.println("User is ANONYMOUS");
            //get path
            path = pathFinder.getEntireFilePathNotLogged();

            //add all file types to check in
            fileTypes[0]="sequence";
            fileTypes[1]="variants";
            fileTypes[2]="bedcov";
            fileTypes[3]="expression";
            fileTypes[4]="difExpression";
            fileTypes[5]="annotation";

            //iterate through all file types
            for (int i=0;i<fileTypes.length;i++)
            {
                //add file type to path
                newpath = path+"/"+fileTypes[i];
                //read file with content of folder
                fileReader = new FileReader(newpath+"/contentOfFolder.txt");
                bufferedReader = new BufferedReader(fileReader);
                result=bufferedReader.readLine();
                while (result!=null)
                {
                    //extract file name
                    splittedFileName = result.split("/");
                    fileNameFromFile = splittedFileName[splittedFileName.length-1];
                    fileNameFromFile = fileNameFromFile.replaceAll("\t", "");
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
            //Find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);

            //find project
            project = projectService.findByProjectName(projectName, user);

            //get list of all files in current project
            List<FileInput> findAll = fileService.findAll(project);

            //iterate through list of files
            for (FileInput file : findAll)
            {
                if(fileName.equals(file.getF_name()))
                    flag=true;
            }
            return flag;
        }
    }


    /**
     * checkIfGFF returns true if gff file is already in project
     * @param projectName String with project name
     * @return boolean
     * @throws Exception
     */
    @SuppressWarnings("empty-statement")
    private boolean checkIfGFF(String projectName) throws Exception
    {
        String path, result; FileReader fileReader; BufferedReader bufferedReader;
        Project project;
        boolean flag = false;

        //check if user is registrated
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//            System.out.println("User is ANONYMOUS");
            //get path
            path = pathFinder.getEntireFilePathNotLogged()+"/annotation";
            //read folder content
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

            //get project
            project = projectService.findByProjectName(projectName, user);

            //get all files
            List<FileInput> findAll = fileService.findAll(project);

            //iterate through files
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