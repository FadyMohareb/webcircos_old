package uk.ac.cranfield.bix.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import uk.ac.cranfield.bix.controllers.rest.NewFile;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;

@Controller
public class FileController {
    @RequestMapping(value = "/controller/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse upload(@RequestBody(required = true) NewFile inputFile) {
        //initializations
        String filePath, fileName, currentPath, fileType, newPath="", userID;
        File oldFile, newFile, dir1, dir2;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        
        try {
            //information about old file to copy
            filePath = inputFile.getFilePath();
            fileType = inputFile.getFileType();
            fileName = inputFile.getFileName();
            //creating object file from path
            oldFile = new File(filePath);
            //current path THE ONE TO CHANGE AT DIFFERENT COMPUTERS
            currentPath = ("Z:/ProfileData/s260592/Desktop/temp/");
            //user id from SpringSecurity
            userID = SecurityContextHolder.getContext().getAuthentication().getName();
            //temporary solution
            if ("anonymousUser".equals(userID))
            {
                userID = RequestContextHolder.currentRequestAttributes().getSessionId();
            }
            //newPath with added userID
            newPath=(currentPath+userID);
            dir1 = new File(newPath);
            //creating new directory for user if doesn't exist
            if (!dir1.exists())
                dir1.mkdir();
            //newPath with added FileType
            newPath=(newPath+"/"+fileType);
            dir2 = new File(newPath);
            //creating new directory for user if doesn't exist
            if (!dir2.exists())
                dir2.mkdir();
            //copy file to new directory
            newFile = new File(newPath+"/"+fileName);
            Files.copy(oldFile.toPath(), newFile.toPath());
            //new file
            fileWriter = new FileWriter(newPath+"/contentOfFolder.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(fileName);
            bufferedWriter.close();
            fileWriter.close();
            return new RestResponse(null, null);
        } catch (Exception e) {
            return new RestResponse(e.getMessage(), null);
        }
    }
}