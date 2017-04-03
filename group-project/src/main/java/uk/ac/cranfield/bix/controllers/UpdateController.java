package uk.ac.cranfield.bix.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
        String userID, currentPath, newPath, type, line, fileList="";
        File dir1, dir1_5, dir2;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        FileReader fileReader;
        BufferedReader bufferedReader;
        try
        {
            //file type needs to consist only letters
            type = panelType.replaceAll("[^a-zA-Z]","");
            //current path THE ONE OF TWO TO CHANGE AT DIFFERENT COMPUTERS
            currentPath = ("Z:/ProfileData/s260533/Desktop/");
            //is user logged
            if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
            {
//                System.out.println("User is ANONYMOUS");
                userID = RequestContextHolder.currentRequestAttributes().getSessionId();
                currentPath = currentPath + "temp/";
                //newPath with added userID
                newPath=(currentPath+userID);
                dir1 = new File(newPath);
                //creating new directory for user if doesn't exist
                if (!dir1.exists())
                    dir1.mkdir();
            }
            else
            {
                System.out.println("User is LOGGED");
                //user id from SpringSecurity
                userID = SecurityContextHolder.getContext().getAuthentication().getName();
                currentPath = currentPath + "user/";
                //newPath with added userID
                newPath=(currentPath+userID);
                dir1 = new File(newPath);
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
            newPath=(newPath+"/"+type);
            dir2 = new File(newPath);
            //creating new directory for user if it doesn't exist or reading from existing one
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
            return new RestResponse(e.getMessage(), null);
        }
    }
        
    
}
