package com.rstack.devnet.utility;

import com.rstack.devnet.model.COMMENT;

public class CommentResponse {
    private String message;
    private COMMENT commentObj;

    public CommentResponse() {
    }

    public CommentResponse(String message, COMMENT commentObj) {
        this.message = message;
        this.commentObj = commentObj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public COMMENT getCommentObj() {
        return commentObj;
    }

    public void setCommentObj(COMMENT commentObj) {
        this.commentObj = commentObj;
    }
}
