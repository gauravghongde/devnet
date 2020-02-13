package com.rstack.devnet.controller;

import com.rstack.devnet.model.ANSWER;
import com.rstack.devnet.model.QUESTION;
import com.rstack.devnet.security.JwtTokenProvider;
import com.rstack.devnet.service.IPostService;
import com.rstack.devnet.service.ISearchService;
import com.rstack.devnet.service.MyUserDetailsService;
import com.rstack.devnet.utility.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
public class DevnetController {

    private final Logger log = LoggerFactory.getLogger(DevnetController.class);


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private IPostService postService;

    @Autowired
    private ISearchService searchService;

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
    public ResponseEntity<String> postQuestion(@RequestBody PostQuestionRequest postQuestionRequest, Authentication authentication) throws Exception {
        String username = authentication.getName();
        postService.postAQuestion(postQuestionRequest, username);
        return ResponseEntity.ok().body("ADDED");
    }

    /////// POST ANSWER /////////
    @PostMapping(value = "questions/{questionId}/answers/post")
    public ResponseEntity<String> postAnswer(@PathVariable String questionId,
                                             @RequestBody PostAnswerRequest postAnswerRequest,
                                             Authentication authentication) throws Exception {
        String username = authentication.getName();
        PostAnswerResponse postAnswerResponse = postService.postAnAnswer(postAnswerRequest, username, questionId);
        return ResponseEntity.ok().body(postAnswerResponse.getPostAnswerMessage());
    }

    /////////// ADD A COMMENT ///////////////
    @PostMapping(value = "posts/{questionId}/add")
    public ResponseEntity<?> postComment(@PathVariable String questionId,
                                         @RequestParam(value = "answerId", defaultValue = "") String answerId, //required is set to false if defaultValue is used
                                         @RequestBody PostCommentRequest postCommentRequest,
                                         Authentication authentication) {
        String username = authentication.getName();
        PostCommentResponse postCommentResponse = postService.postAComment(postCommentRequest, username, questionId, answerId);
        return ResponseEntity.ok().body(postCommentResponse.getMessage());
    }

    /////////// VIEW A QUESTION ///////////////
    @GetMapping(value = "/questions/{questionId}/{questionHeader}", produces = "application/json")
    public ResponseEntity<QueWithAnsResponse> viewQuestion(@PathVariable String questionId,
                                                           @PathVariable String questionHeader) {
        HashMap<QUESTION, List<ANSWER>> questionObj = new HashMap<>();
        //check if this is a good practice to combine two queries
        //OR to use JOIN $lookup
        QueWithAnsResponse queWithAnsResponse = new QueWithAnsResponse();
        queWithAnsResponse.setQuestion(postService.getAQuestion(questionId));
        queWithAnsResponse.setListOfAnswers(postService.getAllAnswersOfAQuestion(questionId));
        return ResponseEntity.ok(queWithAnsResponse);
    }

    /////////// SEARCH QUESTIONS ///////////////
    //accepts -> string of search query
    //returns -> questions matching the string
    @GetMapping(value = "/search")
    public ResponseEntity<?> searchQuestions(@RequestParam(value = "filterBy", defaultValue = "relevance") String filterBy,
                                             @RequestParam("query") String searchQuery) {
        List<QUESTION> questions = searchService.getSearchResults(filterBy, searchQuery, false, false);
        return ResponseEntity.ok(!questions.isEmpty() ? questions : "NO QUESTION FOUND");
    }

    @PostMapping(value = "/vote")
    public ResponseEntity<?> vote(@RequestBody PostQuestionRequest postQuestionRequest) throws Exception {
//        ADD
//        username
//        postQuestionRequest.getQuestionHeader();
//        postQuestionRequest.getQuestionBody();
//        to database
        return ResponseEntity.ok("ADDED");
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
