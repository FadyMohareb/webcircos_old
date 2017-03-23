package uk.ac.cranfield.bix.utilities;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uk.ac.cranfield.bix.models.User;

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

    public static Cookie createCookie(String cookieName, String cookieValue, int expiryTime ) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        //Put negative value for unlogged users (the cookie will not be stored and disappear when the usr close the application) and positive value for logged user 
        cookie.setMaxAge(expiryTime);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;

    }
    
    public static String greetingMessage(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false); 
        User user = (User)session.getAttribute("user");
        
        if(user!=null){
            return "Welcome "+user.getName()+"!";
        }else{
            return "Welcome!";
        }
    }
    
    /**
     * Validate user email address before registering user in DB 
     * @param email user email
     * @return true if the email address is valid otherwise false 
     */
    public static boolean emailValidator(String email){
        
        String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
        
    }

}
