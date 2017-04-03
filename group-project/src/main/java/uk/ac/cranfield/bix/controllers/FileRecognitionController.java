package uk.ac.cranfield.bix.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;

@Controller
public class FileRecognitionController {
    @RequestMapping(value = "/recognizeFile", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse recognizeFile(@RequestParam("file") MultipartFile multipartFile)
    {
        String fileType="";
        int i;
        InputStream inputStream;
        char c;

        try {
            inputStream = multipartFile.getInputStream();
            while(((i = inputStream.read())!=-1) && (fileType==""))
            {
                c = (char)i;
                if (c=='>')
                {
                    fileType="sequence";
                }
                if (c=='#')
                {
                    c=(char)inputStream.read();
                    if (c=='#')
                    {
                        c=(char)inputStream.read();
                        if (c=='g')
                            fileType="annotation";
                        else if(c=='v')
                            fileType="variants";
                        else
                            fileType="unrecognized";
                    }
                }
            }
        }
        catch (IOException ex) 
        {
            Logger.getLogger(FileRecognitionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new RestResponse(fileType, null);
    }
}
