// Exemplary code for controller managing comments adding on the webpage - not used in our application
package uk.ac.cranfield.bix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cranfield.bix.services.objects.Comment;
import uk.ac.cranfield.bix.services.CommentService;

/**
 * @author Benjamin Winterberg
 */
@RestController
@RequestMapping("/comments.json")
public class CommentController {

    private CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Comment> getComments() {
        return service.getComments();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<Comment> addComment(Comment comment) {
        return service.addComment(comment);
    }

}
