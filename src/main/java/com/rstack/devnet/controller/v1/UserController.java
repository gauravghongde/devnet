package com.rstack.devnet.controller.v1;

import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.dto.model.UserDTO;
import com.rstack.devnet.service.post.PostService;
import com.rstack.devnet.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/{username}/profile", produces = "application/json")
    public ResponseEntity<UserDTO> getProfile(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserProfile(username), HttpStatus.OK);
    }


    @GetMapping(value = "/user/{username}/questions", produces = "application/json")
    public ResponseEntity<List<PostDTO>> getAllQuestionsByUsername(@PathVariable String username) {
        List<PostDTO> questionsByUsername = postService.getAllQuestionsByUsername(username);
        return new ResponseEntity<>(questionsByUsername, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{username}/answers", produces = "application/json")
    public ResponseEntity<List<PostDTO>> getAllAnswersByUsername(@PathVariable String username) {
        List<PostDTO> answersByUsername = postService.getAllAnswersByUsername(username);
        return new ResponseEntity<>(answersByUsername, HttpStatus.OK);
    }
}
