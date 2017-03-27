package uk.ac.cranfield.bix.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.*;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uk.ac.cranfield.bix.controllers.rest.NewFile;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;

@Controller
public class FileController {
    @RequestMapping(value = "/controller/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse upload(@RequestBody(required = true) NewFile inputFile) {
        String filePath, fileName, currentPath, fileType, newPath="", userID;
        File oldFile, newFile, dir;
        
        
        try {
            filePath = inputFile.getFilePath();
            fileType = inputFile.getFileType();
            fileName = inputFile.getFileName();
            oldFile = new File(filePath);
            currentPath = ("Z:/ProfileData/s260533/Desktop/temp/");
//            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
//            {
//                userID = SecurityContextHolder.getContext().getAuthentication().getName();
//            }
//            else
//            {
                userID = RequestContextHolder.currentRequestAttributes().getSessionId();
//            }
            newPath=(currentPath+userID);
            dir = new File(newPath);
            dir.mkdir();
            newPath=newPath+"/"+fileName;
            newFile = new File(newPath);
//            Files.copy(oldFile, newFile);
            Files.copy(oldFile.toPath(), newFile.toPath());
                   
            return new RestResponse(null, null);
        } catch (Exception e) {
            return new RestResponse(e.getMessage(), null);
        }
    }
}