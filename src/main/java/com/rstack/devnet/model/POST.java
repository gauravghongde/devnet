package com.rstack.devnet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@Document(collection = "POST")
public class POST {
    @Id
    private String postId;
    @TextIndexed(weight = 2)
    private String questionHeader;
    @TextIndexed
    private String questionBody;
    private String answerBody;
    private String postedBy; //username
    private Instant postedAt;
    private String forQuestion;
    private int upVotes;
    private int downVotes;
    private int voteStatus;
    private HashMap<String, Integer> usersInteracted;
    private List<COMMENT> commentObj;

    public POST() {
    }

    public POST(String postId, String questionHeader, String questionBody, String answerBody, String postedBy, Instant postedAt, String forQuestion, int upVotes, int downVotes, int voteStatus, HashMap<String, Integer> usersInteracted, List<COMMENT> commentObj) {
        this.postId = postId;
        this.questionHeader = questionHeader;
        this.questionBody = questionBody;
        this.answerBody = answerBody;
        this.postedBy = postedBy;
        this.postedAt = postedAt;
        this.forQuestion = forQuestion;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.voteStatus = voteStatus;
        this.usersInteracted = usersInteracted;
        this.commentObj = commentObj;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public Instant getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Instant postedAt) {
        this.postedAt = postedAt;
    }

    public String getForQuestion() {
        return forQuestion;
    }

    public void setForQuestion(String forQuestion) {
        this.forQuestion = forQuestion;
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

    public int getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(int voteStatus) {
        this.voteStatus = voteStatus;
    }

    public HashMap<String, Integer> getUsersInteracted() {
        return usersInteracted;
    }

    public void setUsersInteracted(HashMap<String, Integer> usersInteracted) {
        this.usersInteracted = usersInteracted;
    }

    public List<COMMENT> getCommentObj() {
        return commentObj;
    }

    public void setCommentObj(List<COMMENT> commentObj) {
        this.commentObj = commentObj;
    }
}
