package com.rstack.devnet.model;

import java.time.LocalDate;

public class COMMENT {
    private String commentId;
    private String forContent; //AID/QID
    private String commentBody;
    private String byUser; //username
    private LocalDate postedAt;
    private int upVotes;
    private int downVotes;

    public COMMENT() {
    }

    public COMMENT(String commentId, String forContent, String commentBody, String byUser, LocalDate postedAt, int upVotes, int downVotes) {
        this.commentId = commentId;
        this.forContent = forContent;
        this.commentBody = commentBody;
        this.byUser = byUser;
        this.postedAt = postedAt;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getForContent() {
        return forContent;
    }

    public void setForContent(String forContent) {
        this.forContent = forContent;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
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
}
