package com.rstack.devnet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "QUESTION")
public class QUESTION {
    @Id
    private String questionId;
    private String questionHeader;
    private String questionBody;
    private String byUser; //username
    private Instant postedAt;
    private int upVotes;
    private int downVotes;
    private List<ANSWER> answerObj;
    private List<COMMENT> commentObj;

    public QUESTION() {
    }

    public QUESTION(String questionId, String questionHeader, String questionBody, String byUser, Instant postedAt, int upVotes, int downVotes, List<ANSWER> answerObj, List<COMMENT> commentObj) {
        this.questionId = questionId;
        this.questionHeader = questionHeader;
        this.questionBody = questionBody;
        this.byUser = byUser;
        this.postedAt = postedAt;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.answerObj = answerObj;
        this.commentObj = commentObj;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
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

    public String getByUser() {
        return byUser;
    }

    public void setByUser(String byUser) {
        this.byUser = byUser;
    }

    public Instant getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Instant postedAt) {
        this.postedAt = postedAt;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public List<ANSWER> getAnswerObj() {
        return answerObj;
    }

    public void setAnswerObj(List<ANSWER> answerObj) {
        this.answerObj = answerObj;
    }

    public List<COMMENT> getCommentObj() {
        return commentObj;
    }

    public void setCommentObj(List<COMMENT> commentObj) {
        this.commentObj = commentObj;
    }
}
