package uk.ac.cranfield.bix.models;

import java.io.File;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;

public class PathFinder {

    //current path THE ONE OF TWO TO CHANGE AT DIFFERENT COMPUTERS
    private final String constantPath = ("/home/vmuser/WebCircos/");
    private String currentPath;

    public PathFinder() {
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public String getSimpeUserPath() {
        return constantPath + "user/";
    }
    
    public String getGffPath()
    {
        String path;
        path = getUserPathNotLogged();
        return path+"/annotation";
    }

    public String getUserPathNotLogged() {
        String userID, path;
        File dir1, dir1_5, dir2;

//        System.out.println("User is ANONYMOUS");
        userID = RequestContextHolder.currentRequestAttributes().getSessionId();
        //newPath with added userID
        path = constantPath + "temp/" + userID;
        dir1 = new File(path);
        //creating new directory for user if doesn't exist
        if (!dir1.exists()) {
            dir1.mkdir();
        }

        return path;
    }

    public String getUserPathLogged() {
        String userID, path;
        File dir1, dir1_5, dir2;

//        System.out.println("User is LOGGED");
        userID = SecurityContextHolder.getContext().getAuthentication().getName();
        //newPath with added userID
        path = constantPath + "user/" + userID;
        dir1 = new File(path);
        //creating new directory for user if doesn't exist
        if (!dir1.exists()) {
            dir1.mkdir();
        }

        return path;
    }

    public String getUserPathLogged(String projectName) {
        String userID, path;
        File dir1, dir2;

//        System.out.println("User is LOGGED");
        userID = SecurityContextHolder.getContext().getAuthentication().getName();
        //newPath with added userID
        path = constantPath + "user/" + userID;
        dir1 = new File(path);
        //creating new directory for user if doesn't exist
        if (!dir1.exists()) {
            dir1.mkdir();
        }
        //newPath with added projectName
        path = path + "/" + projectName;
        //creating new directory for project if doesn't exist
        dir2 = new File(path);
        if (!dir2.exists()) {
            dir2.mkdir();
        }
        return path;
    }

//    public String getFilePath() {
//        String userID, path = "";
//        File dir1, dir1_5, dir2;
//        //is user logged
//        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
////          System.out.println("User is ANONYMOUS");
//            userID = RequestContextHolder.currentRequestAttributes().getSessionId();
//            //newPath with added userID
//            path = constantPath + "temp/" + userID;
//            dir1 = new File(path);
//            //creating new directory for user if doesn't exist
//            if (!dir1.exists()) {
//                dir1.mkdir();
//            }
//        } else {
////          System.out.println("User is LOGGED");
//            //user id from SpringSecurity
//            userID = SecurityContextHolder.getContext().getAuthentication().getName();
//            //newPath with added userID
//            path = path + "user/" + userID;
//            dir1 = new File(path);
//            //creating new directory for user if doesn't exist
//            if (!dir1.exists()) {
//                dir1.mkdir();
//            }
//            //project id needed
////                projectID=??
////                newPath=(newPath+projectID);
////                dir1_5 = new File(newPath);
////                //creating new directory for user if doesn't exist
////                if (!dir1_5.exists())
////                    dir1_5.mkdir();
//        }
//        return path;
//    }
}
