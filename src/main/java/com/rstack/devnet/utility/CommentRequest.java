package com.rstack.devnet.utility;

public class CommentRequest {
    private String commentBody;

    public CommentRequest() {
    }

    public CommentRequest(String commentBody) {
        this.commentBody = commentBody;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
}
