package com.rstack.devnet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@Document(collection = "POST")
public class Post {
    @Id
    private String id;
    @TextIndexed(weight = 2)
    private String questionHeader;
    @TextIndexed
    private String questionBody;
    private String answerBody;
    private String username;
    private Instant postedAt;
    private String questionId;
    private Integer noOfAnswers; //only in case of question
    //    private Boolean isEdited;
    private Vote vote;
    private HashMap<String, Integer> usersInteracted;
    private List<Comment> comments;

    public Post() {
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

    public Integer getNoOfAnswers() {
        return noOfAnswers;
    }

    public void setNoOfAnswers(Integer noOfAnswers) {
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
