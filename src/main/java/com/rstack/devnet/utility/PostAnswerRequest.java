package com.rstack.devnet.utility;

public class PostAnswerRequest {
    private String answerBody;

    PostAnswerRequest() {
    }

    public PostAnswerRequest(String answerBody) {
        this.answerBody = answerBody;
    }

    public String getAnswerBody() {
        return answerBody;
    }

    public void setAnswerBody(String answerBody) {
        this.answerBody = answerBody;
    }
}
