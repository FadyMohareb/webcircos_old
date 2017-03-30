package uk.ac.cranfield.bix.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;

@Controller
public class UpdateController {
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse upload(@RequestBody(required = true) String panelType) 
    {
        String userID, currentPath, newPath, type, line;
        List<String> fileList = null;
        File dir1, dir2;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        FileReader fileReader;
        BufferedReader bufferedReader;
        File[] listFiles = null;
        try
        {
            type = panelType.replaceAll("[^a-zA-Z]","");
            //current path THE ONE TO CHANGE AT DIFFERENT COMPUTERS
            currentPath = ("Z:/ProfileData/s260533/Desktop/");
            //user id from SpringSecurity
            userID = SecurityContextHolder.getContext().getAuthentication().getName();
            //temporary solution
            if ("anonymousUser".equals(userID))
            {
                userID = RequestContextHolder.currentRequestAttributes().getSessionId();
                currentPath = currentPath + "temp/";
            }
            else
            {
                currentPath = currentPath + "/user/";
            }
            //newPath with added userID
            newPath=(currentPath+userID);
            dir1 = new File(newPath);
            //creating new directory for user if doesn't exist
            if (!dir1.exists())
                dir1.mkdir();
            //newPath with added FileType
            newPath=(newPath+"/"+type);
            dir2 = new File(newPath);
            //creating new directory for user if doesn't exist
            newPath = newPath+"/contentOfFolder.txt";
            if (!dir2.exists())
            {
                dir2.mkdir();
                //new file
                fileWriter = new FileWriter(newPath, true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.close();
                fileWriter.close();
            }
            else
            {
                listFiles = dir2.listFiles();
//                System.out.println(Arrays.toString(listFiles));
            }
            return new RestResponse(Arrays.toString(listFiles), null); 
        }
        catch(Exception e)
        {
            return new RestResponse(e.getMessage(), null);
        }
    }
        
    
}
