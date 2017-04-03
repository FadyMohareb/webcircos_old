/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;
import uk.ac.cranfield.bix.services.ProjectService;
import uk.ac.cranfield.bix.services.UserService;
import static uk.ac.cranfield.bix.utilities.Utilities.folderExist;

/**
 *
 * @author solene
 */
@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/project/newProject", method = RequestMethod.POST)
    public @ResponseBody
    RestResponse upload(@RequestBody(required = true) String projectName) {
        try {
            //Find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);
            System.out.println("project name " +projectName);

            //Check if project allready exist
            Project project = projectService.findByProjectName(projectName, user);
            
            //If not create one, else return project already exist. 
            if(project==null){
                Project p = new Project();
                p.setUser(user);
                p.setP_name(projectName);
                projectService.save(p);
                
                //Check if user has a folder with his id 
                String folderPath = "C:/Users/solene/documents/"+ user.getLogin();
                boolean folderExist = folderExist(folderPath);
                
                if(!folderExist){
                    new File(folderPath).mkdir();
                }
                
                //Create a folder for the user with the project name.
                new File(folderPath+"/"+projectName).mkdir();
                
                 return new RestResponse(null, null);
            }else{
                return new RestResponse("A project with this name already exist", null);
            }   
        } catch (Exception e) {
            return new RestResponse(e.getMessage(), null);
        }
    }

}
