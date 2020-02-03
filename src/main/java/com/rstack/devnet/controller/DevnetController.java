package com.rstack.devnet.controller;

import com.rstack.devnet.security.JwtTokenProvider;
import com.rstack.devnet.service.MyUserDetailsService;
import com.rstack.devnet.utility.PostQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class DevnetController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    ////////// USER /////
    @GetMapping(value = "/home/ping")
    public ResponseEntity<String> pingHome() {
        return ResponseEntity.ok().body("Hi Home");
    }


    /////// POST QUESTION /////////
    @PostMapping(value = "/questions/post")
    public ResponseEntity<String> postQuestion(@RequestBody PostQuestionRequest postQuestionRequest) throws Exception {
        jwtTokenProvider.extractUsername("");
//        ADD
//        username UUID
//        postQuestionRequest.getQuestionHeader();
//        postQuestionRequest.getQuestionBody();
//        to database
        return ResponseEntity.ok().body("ADDED");
    }

    /////// POST ANSWER /////////
    @PostMapping(value = "questions/{questionId}/post/answer")
    public ResponseEntity<?> postAnswer() throws Exception {
//        ADD
//        username
//        qid
//        Answer body
//        to database
        return ResponseEntity.ok("ADDED");
    }

    /////////// VIEW A QUESTION ///////////////
    //accepts -> unique QID and QHeader
    //returns -> question matching the string
    @GetMapping(value = "/questions/{questionId}/{questionHeader}")
    public ResponseEntity<?> viewQuestion(@PathVariable String questionId, @PathVariable String pathHeader) {
        //return json response containing
        //question header+body + comments
        //all answers + comments
        //votes
        return null;
    }

    /////////// SEARCH QUESTIONS ///////////////
    //accepts -> string of search query
    //returns -> questions matching the string
    @GetMapping(value = "/questions/{searchQuery}")
    public ResponseEntity<?> searchQuestions(@PathVariable String searchQuery) {
        return null;
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
