package uk.ac.cranfield.bix.utilities;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;
import uk.ac.cranfield.bix.models.User;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeGff;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeSequence;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeVcf;
import static uk.ac.cranfield.bix.utilities.fileParser.Gff3Parser.GffParser;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.HistogramData;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.VCFHistParser;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.VcfToolsSNPDensity;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.VcfToolsSNPS;
import static uk.ac.cranfield.bix.utilities.fileParser.fastaParsers.fastaParser;

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

    public static String greetingMessage() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User user = (User) session.getAttribute("user");

        if (user != null) {
            return "Welcome " + user.getName() + "!";
        } else {
            return "Welcome!";
        }
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

    public static void parseFile(String filePath, String fileType) throws IOException, ClassNotFoundException {
        String fileWithoutExtension = filePath.substring(0, filePath.lastIndexOf("."));
        switch (fileType) {
            case "alignment":
                break;
            case "sequence":
                //Parse and Serialize fasta file
                List<Sequence> fastaParser = fastaParser(filePath);
                SerializeSequence(fastaParser, fileWithoutExtension+".txt");
                break;
            case "annotation":
                List<String[]> GffParser = GffParser(filePath);
                SerializeGff(GffParser, fileWithoutExtension+".txt");
                break;
            case "variants":
                System.out.println("" + filePath);
                String VcfToolsSNPS = VcfToolsSNPS(filePath);
                System.out.println("" + VcfToolsSNPS);
                String VcfToolsSNPDensity = VcfToolsSNPDensity(VcfToolsSNPS);
                System.out.println("" + VcfToolsSNPDensity);
                ArrayList<String[]> VCFHistParser = VCFHistParser(VcfToolsSNPDensity);
                SerializeVcf(VCFHistParser, fileWithoutExtension+".txt");
                break;
            default:
                break;

        }
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
     * @return if the folder exist or not.  
     */
    public static boolean folderExist(String folderPath) {
        File userFolder = new File(folderPath);
        return userFolder.exists();
    }

}
