package com.rstack.devnet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "ANSWER")
public class ANSWER {
    @Id
    private String answerId;
    private String answerBody;
    private String byUser; //username
    private String forQuestion; //QID
    private Instant postedAt;
    private int upVotes;
    private int downVotes;
    private List<COMMENT> commentObj;

    public ANSWER() {
    }

    public ANSWER(String answerId, String answerBody, String byUser, String forQuestion, Instant postedAt, int upVotes, int downVotes, List<COMMENT> commentObj) {
        this.answerId = answerId;
        this.answerBody = answerBody;
        this.byUser = byUser;
        this.forQuestion = forQuestion;
        this.postedAt = postedAt;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.commentObj = commentObj;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerBody() {
        return answerBody;
    }

    public void setAnswerBody(String answerBody) {
        this.answerBody = answerBody;
    }

    public String getByUser() {
        return byUser;
    }

    public void setByUser(String byUser) {
        this.byUser = byUser;
    }

    public String getForQuestion() {
        return forQuestion;
    }

    public void setForQuestion(String forQuestion) {
        this.forQuestion = forQuestion;
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

    public List<COMMENT> getCommentObj() {
        return commentObj;
    }

    public void setCommentObj(List<COMMENT> commentObj) {
        this.commentObj = commentObj;
    }
}
