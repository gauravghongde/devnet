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

    /*
     * Note: A post can be a question or an answer.
     */

    @PostMapping(value = "/posts/add")
    public ResponseEntity<PostDTO> addPost(@RequestBody PostRequest postRequest,
                                           @RequestParam(value = "questionId", defaultValue = "") String questionId,
                                           Authentication authentication) {
        String username = authentication.getName();
        PostDTO post = postService.addPost(postRequest, username, questionId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping(value = "/comments/add")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest commentRequest,
                                              @RequestParam("postId") String postId,
                                              Authentication authentication) {
        String username = authentication.getName();
        Comment comment = postService.addComment(commentRequest, username, postId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping(value = "/posts/{postId}/update")
    public ResponseEntity<PostDTO> updatePostById(@RequestBody PostRequest postRequest,
                                                  @PathVariable String postId,
                                                  Authentication authentication) {
        String username = authentication.getName();
        PostDTO post = postService.updatePostById(postRequest, username, postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping(value = "/comments/{commentId}/update")
    public ResponseEntity<HttpStatus> updateCommentById(@RequestBody CommentRequest commentRequest,
                                                        @PathVariable String commentId,
                                                        @RequestParam("postId") String postId,
                                                        Authentication authentication) {
        String username = authentication.getName();
        try {
            postService.updateCommentById(commentRequest, commentId, postId, username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/posts/{postId}/delete")
    public ResponseEntity<HttpStatus> deletePostById(@PathVariable String postId,
                                                     Authentication authentication) {
        String username = authentication.getName();
        try {
            postService.deletePostById(postId, username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping(value = "/comments/{commentId}/delete")
    public ResponseEntity<HttpStatus> deleteCommentById(@PathVariable String commentId,
                                                        @RequestParam("postId") String postId,
                                                        Authentication authentication) {
        String username = authentication.getName();
        try {
            postService.deleteCommentById(commentId, postId, username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/posts/questions", produces = "application/json")
    public ResponseEntity<List<PostDTO>> getAllQuestions(Authentication authentication) {
        String username = authentication.getName();
        List<PostDTO> questions = postService.getAllQuestions(username);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping(value = "/posts/questions/{questionId}", produces = "application/json")
    public ResponseEntity<List<PostDTO>> viewQuestion(@PathVariable String questionId,
                                                      Authentication authentication) {
        String username = authentication.getName();
        List<PostDTO> questionWithAnswers = postService.getQuestionWithAnswers(questionId, username);
        return new ResponseEntity<>(questionWithAnswers, HttpStatus.OK);
    }

//    @GetMapping(value = "/answers", produces = "application/json")
//    public ResponseEntity<List<PostDTO>> getAllAnswers(Authentication authentication) {
//
//    }
//
//    @GetMapping(value = "/comments", produces = "application/json")
//    public ResponseEntity<List<PostDTO>> getAllComments(Authentication authentication) {
//
//    }

}
