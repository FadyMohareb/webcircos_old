package uk.ac.cranfield.bix.utilities;

import java.io.File;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 
 * @author vmuser
 */
public class Utilities {

    public static String hashPassword(String password) {
        try {
            // Hashing password
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha.digest(password.getBytes("UTF-8"));
            // Encoding the hash back into a string
            StringBuilder hashedPassword = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hashedPassword.append('0');
                }
                hashedPassword.append(hex);
            }
            return hashedPassword.toString();
        } catch (Exception ex) {
            System.out.println("Hashing algorithm not failed.");
        }
        return null;
    }

    /**
     * Validate user email address before registering user in DB
     *
     * @param email user email
     * @return true if the email address is valid otherwise false
     */
    public static boolean emailValidator(String email) {

        String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();

    }

   
    public static boolean isUserAuthenticated() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if a folder with this name already exist for this user.
     *
     * @return if the folder exist or not.
     */
    public static boolean folderExist(String folderPath) {
        File userFolder = new File(folderPath);
        return userFolder.exists();
    }

}
