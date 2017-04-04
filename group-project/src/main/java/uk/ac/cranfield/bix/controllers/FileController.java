package uk.ac.cranfield.bix.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;
import static uk.ac.cranfield.bix.utilities.Utilities.parseFile;

@Controller
public class FileController {
    @RequestMapping(value = "/controller/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("String") String fileType) {
        //initializations
        String fileName, path, userID;
        File dir1, dir2;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        String[] splittedFileName;
        
        try {
            //current path THE ONE OF TWO TO CHANGE AT DIFFERENT COMPUTERS
            path = ("C:/Users/agata/Desktop/WebCircos/");
            //is user logged
            if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
            {
//                System.out.println("User is ANONYMOUS");
                userID = RequestContextHolder.currentRequestAttributes().getSessionId();
                //newPath with added userID
                path = path + "temp/" + userID;
                dir1 = new File(path);
                //creating new directory for user if doesn't exist
                if (!dir1.exists())
                    dir1.mkdir();
            }
            else
            {
//                System.out.println("User is LOGGED");
                //user id from SpringSecurity
                userID = SecurityContextHolder.getContext().getAuthentication().getName();
                //newPath with added userID
                path = path + "user/" + userID;
                dir1 = new File(path);
                //creating new directory for user if doesn't exist
                if (!dir1.exists())
                    dir1.mkdir();
                //project id needed
//                projectID=??
//                newPath=(newPath+projectID);
//                dir1_5 = new File(newPath);
//                //creating new directory for user if doesn't exist
//                if (!dir1_5.exists())
//                    dir1_5.mkdir();
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
        } catch (Exception e) {
            return new RestResponse(e.getMessage(), null);
        }
    }
}
