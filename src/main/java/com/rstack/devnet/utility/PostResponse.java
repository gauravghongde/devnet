package com.rstack.devnet.utility;

import com.rstack.devnet.model.POST;

public class PostResponse {
    private String message;
    private POST postObj;

    public PostResponse() {
    }

    public PostResponse(String message, POST postObj) {
        this.message = message;
        this.postObj = postObj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public POST getPostObj() {
        return postObj;
    }

    public void setPostObj(POST postObj) {
        this.postObj = postObj;
    }
}
