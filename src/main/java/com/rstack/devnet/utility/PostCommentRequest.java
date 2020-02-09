package com.rstack.devnet.utility;

public class PostCommentRequest {
    private String commentBody;

    public PostCommentRequest() {
    }

    public PostCommentRequest(String commentBody) {
        this.commentBody = commentBody;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
}
