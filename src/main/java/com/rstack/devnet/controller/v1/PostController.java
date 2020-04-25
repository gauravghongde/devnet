package com.rstack.devnet.controller.v1;

import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.model.Comment;
import com.rstack.devnet.service.post.PostService;
import com.rstack.devnet.utility.CommentRequest;
import com.rstack.devnet.utility.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1")
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping(value = "/questions/add")
    public ResponseEntity<PostDTO> addQuestion(@RequestBody PostRequest postRequest, Authentication authentication) {
        PostDTO post;
        String username = authentication.getName();
        post = postService.addQuestion(postRequest, username);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping(value = "questions/{questionId}/answers/add")
    public ResponseEntity<PostDTO> addAnswer(@PathVariable String questionId,
                                             @RequestBody PostRequest postRequest,
                                             Authentication authentication) {

        String username = authentication.getName();
        PostDTO post = postService.addAnswer(postRequest, username, questionId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping(value = "posts/{postId}/add")
    public ResponseEntity<Comment> addComment(@PathVariable String postId,
                                              @RequestBody CommentRequest commentRequest,
                                              Authentication authentication) {
        String username = authentication.getName();
        Comment comment = postService.addComment(commentRequest, username, postId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping(value = "/questions/{questionId}/{questionHeader}", produces = "application/json")
    public ResponseEntity<List<PostDTO>> viewQuestion(@PathVariable String questionId,
                                                      @PathVariable String questionHeader,
                                                      Authentication authentication) {
        String username = authentication.getName();
        List<PostDTO> questionWithAnswers = postService.getQuestionWithAnswers(questionId, username);
        return new ResponseEntity<>(questionWithAnswers, HttpStatus.OK);
    }

}
