package com.rstack.devnet.model;

import java.time.LocalDate;
import java.util.List;

public class ANSWER {
    private String answerId;
    private String forQuestion; //QID
    private String answerBody;
    private String byUser; //username
    private LocalDate postedAt;
    private int upVotes;
    private int downVotes;
    private List<COMMENT> commentObj;

    public ANSWER() {
    }

    public ANSWER(String answerId, String forQuestion, String answerBody, String byUser, LocalDate postedAt, int upVotes, int downVotes, List<COMMENT> commentObj) {
        this.answerId = answerId;
        this.forQuestion = forQuestion;
        this.answerBody = answerBody;
        this.byUser = byUser;
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

    public String getForQuestion() {
        return forQuestion;
    }

    public void setForQuestion(String forQuestion) {
        this.forQuestion = forQuestion;
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

    public LocalDate getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDate postedAt) {
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
