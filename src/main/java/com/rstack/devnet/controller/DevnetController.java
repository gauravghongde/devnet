package com.rstack.devnet.controller;

import com.rstack.devnet.model.POST;
import com.rstack.devnet.service.IPostService;
import com.rstack.devnet.service.ISearchService;
import com.rstack.devnet.service.IVoteService;
import com.rstack.devnet.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = "*")
public class DevnetController {

    @Autowired
    private IPostService postService;

    @Autowired
    private ISearchService searchService;

    @Autowired
    private IVoteService voteService;

    ////////// USER /////
    @GetMapping(value = "/home/ping")
    public ResponseEntity<String> pingHome() {
        return ResponseEntity.ok().body("Hi Home");
    }


    /*
     USERS                  /users/<userID>/<username>
     GET ALL QUESTIONS      /questions
     POST A QUESTION        /questions/post
     GET A QUESTION         /questions/<QID>
     GET ALL ANSWERS        /questions/<QID>/answers
     ^-------------^        Should get the response JSON, but URL should show 404
     POST AN ANSWER         /questions/<QID>/answers/post
     GET AN ANSWER          /questions/<QID>/answers/<AID>
     GET ALL COMMENTS       /questions/<QID>/answers/<AID>/comments
     POST A COMMENT         /questions/<QID>/answers/<AID>/comments/post
     GET A COMMENT          /questions/<QID>/answers/<AID>/comments/<CID>

     POST A COMMENT <OR>    /posts/<PID>/comments/add
     UPVOTE                 /posts/<PID>/1
     DOWNVOTE               /posts/<PID>/0
     FAVOURITE              /posts/<PID>?favourite=true
    */


    /////// POST QUESTION /////////
    @PostMapping(value = "/questions/post")
    public ResponseEntity<String> postQuestion(@RequestBody PostRequest postRequest, Authentication authentication) throws Exception {
        String username = authentication.getName();
        postService.postAQuestion(postRequest, username);
        return ResponseEntity.ok().body("ADDED");
    }

    /////// POST ANSWER /////////
    @PostMapping(value = "questions/{questionId}/answers/post")
    public ResponseEntity<String> postAnswer(@PathVariable String questionId,
                                             @RequestBody PostRequest postRequest,
                                             Authentication authentication) throws Exception {
        String username = authentication.getName();
        PostResponse postResponse = postService.postAnAnswer(postRequest, username, questionId);
        return ResponseEntity.ok().body(postResponse.getMessage());
    }

    /////////// ADD A COMMENT ///////////////
    @PostMapping(value = "posts/{postId}/add")
    public ResponseEntity<?> postComment(@PathVariable String postId,
                                         @RequestBody CommentRequest commentRequest,
                                         Authentication authentication) {
        String username = authentication.getName();
        CommentResponse commentResponse = postService.postAComment(commentRequest, username, postId);
        return ResponseEntity.ok().body(commentResponse.getMessage());
    }

    /////////// VIEW A QUESTION ///////////////
    @GetMapping(value = "/questions/{questionId}/{questionHeader}", produces = "application/json")
    public ResponseEntity<QueWithAnsResponse> viewQuestion(@PathVariable String questionId,
                                                           @PathVariable String questionHeader,
                                                           Authentication authentication) {
        //check if this is a good practice to combine two queries
        //OR to use JOIN $lookup
        String username = authentication.getName();
        QueWithAnsResponse queWithAnsResponse = new QueWithAnsResponse();
        queWithAnsResponse.setQuestion(postService.getAQuestion(questionId, username));
        queWithAnsResponse.setListOfAnswers(postService.getAllAnswersOfAQuestion(questionId, username));
        return ResponseEntity.ok(queWithAnsResponse);
    }

    /////////// SEARCH QUESTIONS ///////////////
    //accepts -> string of search query
    //returns -> questions matching the string
    @GetMapping(value = "/search")
    public ResponseEntity<?> searchQuestions(@RequestParam(value = "filterBy", defaultValue = "relevance") String filterBy,
                                             @RequestParam("query") String searchQuery) {
        List<POST> questions = searchService.getSearchResults(filterBy, searchQuery, false, false);
        return ResponseEntity.ok(!questions.isEmpty() ? questions : "NO QUESTION FOUND");
    }

    /////////// VOTING SYSTEM ///////////////
    /*
    BUD
    000 -> 0 -> No BM, No UV, No DV
    001 -> 1 -> No BM, No UV, DV
    010 -> 2 -> No BM, UV, No DV

    100 -> 4 -> BM, No UV, No DV
    101 -> 5 -> BM, No UV, DV
    110 -> 6 -> BM, UV, No DV

    */

    @GetMapping(value = "/posts/{postId}/vote")
    public ResponseEntity<?> vote(@PathVariable String postId,
                                  @RequestParam(value = "commentId", defaultValue = "") String commentId,
                                  @RequestParam("voteId") int voteId,
                                  Authentication authentication
    ) throws Exception {

        String username = authentication.getName();
        String message = voteService.voteAPost(username, voteId, postId, commentId);
        return ResponseEntity.ok(message);
    }

    //////////////////////////////////////////////////

    /* ALL TODOs

    - Add min and max char limit to
      - Question header
      - Question Body
      - Ans Body
      - Comment

     */

    ////////////////////////////////////////////////


    /* NOTES ->>>>>>>>>>

    URLS should be like

    for all questions subscribed tags
    /questions

    for a question
    /questions/UNIQUE_QUESTION_ID/complete-question-header-cause-google-search-algo-finds-it-quickly

    for posting question
    /questions/post

    for answer
    /question/UNIQUE_QUESTION_ID/answer/post<OR submit>

    for comment
    /question/UNIQUE_QUESTION_ID/comment/post<OR submit>

     */

    ////////////////////////////////////////////////

    /*
    What response should i get for post requests
    when to  use mongo Embedded vs mongo referenced?
    Optimal no of database queries for a single page loading

     */
}
