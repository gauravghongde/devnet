package com.rstack.devnet.dto.model;

import com.rstack.devnet.model.Comment;
import com.rstack.devnet.model.Vote;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

public class PostDTO {
    private String id;
    private String questionHeader;
    private String questionBody;
    private String answerBody;
    private String username;
    private Instant postedAt;
    private String questionId;
    private int noOfAnswers; //only in case of question
    private Vote vote;
    private HashMap<String, Integer> usersInteracted;
    private List<Comment> comments;

    public PostDTO() {
    }

    public PostDTO(String id, String questionHeader, String questionBody, String answerBody, String username, Instant postedAt, String questionId, int noOfAnswers, Vote vote, HashMap<String, Integer> usersInteracted, List<Comment> comments) {
        this.id = id;
        this.questionHeader = questionHeader;
        this.questionBody = questionBody;
        this.answerBody = answerBody;
        this.username = username;
        this.postedAt = postedAt;
        this.questionId = questionId;
        this.noOfAnswers = noOfAnswers;
        this.vote = vote;
        this.usersInteracted = usersInteracted;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionHeader() {
        return questionHeader;
    }

    public void setQuestionHeader(String questionHeader) {
        this.questionHeader = questionHeader;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public String getAnswerBody() {
        return answerBody;
    }

    public void setAnswerBody(String answerBody) {
        this.answerBody = answerBody;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Instant postedAt) {
        this.postedAt = postedAt;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getNoOfAnswers() {
        return noOfAnswers;
    }

    public void setNoOfAnswers(int noOfAnswers) {
        this.noOfAnswers = noOfAnswers;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public HashMap<String, Integer> getUsersInteracted() {
        return usersInteracted;
    }

    public void setUsersInteracted(HashMap<String, Integer> usersInteracted) {
        this.usersInteracted = usersInteracted;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
