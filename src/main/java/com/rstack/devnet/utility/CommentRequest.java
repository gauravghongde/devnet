package com.rstack.devnet.utility;

import javax.validation.constraints.NotNull;

public class CommentRequest {
    @NotNull
    private String body;

    public CommentRequest() {
    }

    public CommentRequest(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
