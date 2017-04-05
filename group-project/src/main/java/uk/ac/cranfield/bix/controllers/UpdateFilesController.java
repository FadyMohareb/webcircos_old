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
import uk.ac.cranfield.bix.controllers.rest.RestResponse;
import uk.ac.cranfield.bix.models.PathFinder;

@Controller
public class UpdateFilesController {
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse update(@RequestBody(required = true) String panelType) 
    {
        String path, newPath, type, line, fileList="";
        File dir1;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        FileReader fileReader;
        BufferedReader bufferedReader;
        
        
        //file type needs to consist only letters
        type = panelType.replaceAll("[^a-zA-Z]","");
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//          System.out.println("User is ANONYMOUS");
            path = new PathFinder().getEntireFilePathNotLogged();
            try
            {
                //newPath with added FileType
                newPath=(path+"/"+type);
                dir1 = new File(newPath);
                //creating new directory for user if it doesn't exist or reading from existing one
                newPath = newPath+"/contentOfFolder.txt";
                if (!dir1.exists())
                {
                    dir1.mkdir();
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
        else
        {
//            System.out.println("User is LOGGED");
            path = new PathFinder().getEntireFilePathLogged();
            try
            {
                fileList = "empty";
                return new RestResponse(fileList, null); 
            }
            catch(Exception e)
            {
                return new RestResponse(e.getMessage(), null);
            }
        }
    }
}
