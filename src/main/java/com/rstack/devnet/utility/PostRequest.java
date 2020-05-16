package com.rstack.devnet.utility;

public class PostRequest {
    private String questionHeader;
    private String questionBody;
    private String answerBody;

    public PostRequest() {
    }

    public PostRequest(String questionHeader, String questionBody, String answerBody) {
        this.questionHeader = questionHeader;
        this.questionBody = questionBody;
        this.answerBody = answerBody;
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
}
