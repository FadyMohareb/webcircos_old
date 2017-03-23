// Exemplary controller - not used in our application
package uk.ac.cranfield.bix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import uk.ac.cranfield.bix.services.objects.Comment;
import uk.ac.cranfield.bix.services.CommentService;
import uk.ac.cranfield.bix.React;

/**
 * @author Benjamin Winterberg
 */
@Controller
public class MainController {

    private CommentService service;

    private React react;

    private ObjectMapper mapper;

    @Autowired
    public MainController(CommentService service) {
        this.service = service;
        this.react = new React();
        this.mapper = new ObjectMapper();
    }

    @RequestMapping("/commenting")
    public String index(Map<String, Object> model) throws Exception {
        List<Comment> comments = service.getComments();
        String commentBox = react.renderCommentBox(comments);
        String data = mapper.writeValueAsString(comments);
        model.put("content", commentBox);
        model.put("data", data);
        return "index";
    }
    
    @RequestMapping("/")
    public String login() throws Exception {
        return "login";
    }
}
