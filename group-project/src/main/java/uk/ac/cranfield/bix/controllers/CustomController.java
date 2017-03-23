package uk.ac.cranfield.bix.controllers;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cranfield.bix.controllers.rest.LoginCredentials;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;
import uk.ac.cranfield.bix.models.User;
import uk.ac.cranfield.bix.services.SecurityService;
import uk.ac.cranfield.bix.services.UserService;
import uk.ac.cranfield.bix.utilities.Utilities;

@Controller
public class CustomController {
    @Autowired
    @Qualifier("authenticationManager")
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/loginReact", method = RequestMethod.GET)
    public String loginReact() {
        return "loginReact";
    }

    @RequestMapping(value = "/registrationReact")
    public String registrationReact() {
        return "registrationReact";
    }

    @RequestMapping(value = "/comment")
    public String comments() {
        return "commentReact";
    }

    @RequestMapping(value = "/registrationReact", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse registration(@RequestBody(required = true) User user) {
        String password = user.getPassword();
        if (Utilities.emailValidator(user.getEmail())){
            userService.save(user);
        }else{
            System.out.println("Bad email");
        }
        securityService.autologin(user.getLogin(), password);
        return new RestResponse(null, null);   
    }

    @RequestMapping(value = "/loginReactAction", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse registration(@RequestBody LoginCredentials loginCredentials) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword());
        User details = new User();
        details.setLogin(loginCredentials.getUsername());
        token.setDetails(details);

        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new RestResponse(null, null);
        } catch (BadCredentialsException e) {
            return new RestResponse(e.getMessage(), null);
        }
    }
    
    @RequestMapping(value = "/manageAccount")
    public String test() {
        return "manageAccount";
    }
    
     @RequestMapping(value = "/changePassword")
    public String changeP() {
        return "changePassword";
    }
    
    @RequestMapping(value = "/changePasswordAction", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse changePassword(@RequestBody String newPassword) {
        try {
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.changePassword(userLogin, newPassword);
            User user = userService.findByUsername(userLogin);
            
            UsernamePasswordAuthenticationToken token = 
                    new UsernamePasswordAuthenticationToken(
                            user.getLogin(),
                            newPassword);
            
            token.setDetails(user);       
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new RestResponse(null, null);
        }catch(Exception e){
            return new RestResponse(e.getMessage(), null);
        }   
    }
    
     @RequestMapping(value = "/logOutAction", method = RequestMethod.GET)
    public
    @ResponseBody
    RestResponse logOut(HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return new RestResponse(null, null);
        }catch(Exception e){
            return new RestResponse(e.getMessage(), null);
        }   
    }
    
     @RequestMapping(value = "/home")
    public ModelAndView join() {
        Map<String, Object> map = new HashMap<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("userName", name);
        return new ModelAndView("MainWindow", map);
    }
}
