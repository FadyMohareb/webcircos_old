package uk.ac.cranfield.bix.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;
import uk.ac.cranfield.bix.models.PathFinder;
import static uk.ac.cranfield.bix.utilities.Utilities.parseFile;

@Controller
public class FileController {
    @RequestMapping(value = "/controller/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("String") String fileType) {
        //initializations
        String fileName, path, userID;
        File dir1, dir2;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        String[] splittedFileName;
        
        try {
            path = new PathFinder().getFilePath();
            //newPath with added FileType
            path=(path+"/"+fileType);
            dir2 = new File(path);
            if (!dir2.exists())
                dir2.mkdir();
            
            fileName = multipartFile.getOriginalFilename();
            splittedFileName = fileName.split("\\\\");
            if (splittedFileName.length>1)
                fileName = splittedFileName[splittedFileName.length-1];

            FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(path+"/"+fileName));
            //new file
            fileWriter = new FileWriter(path+"/contentOfFolder.txt",true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append(path+"/"+fileName);
            bufferedWriter.append("\t");
//            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            
            // Parse and serialize files
            parseFile(path+"/"+fileName, fileType);
            
            return new RestResponse(null, null);
        } catch (Exception e) {
            return new RestResponse(e.getMessage(), null);
        }
    }
}
