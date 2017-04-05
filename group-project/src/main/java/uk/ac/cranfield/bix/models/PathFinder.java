package uk.ac.cranfield.bix.models;

import java.io.File;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;

/**
 *
 * @author s260533
 */
public class PathFinder 
{
    public PathFinder() {
    }
    
    public String getFilePath()
    {
        String constantPath, userID, path="";
        File dir1, dir1_5, dir2;
        //current path THE ONE OF TWO TO CHANGE AT DIFFERENT COMPUTERS
        constantPath = ("Z:/ProfileData/s260592/Desktop/WebCircos/");
        //is user logged
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        {
//          System.out.println("User is ANONYMOUS");
            userID = RequestContextHolder.currentRequestAttributes().getSessionId();
            //newPath with added userID
            path = constantPath + "temp/" + userID;
            dir1 = new File(path);
            //creating new directory for user if doesn't exist
            if (!dir1.exists())
                dir1.mkdir();
        }
        else
        {
//          System.out.println("User is LOGGED");
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
        return path;
    }
}
