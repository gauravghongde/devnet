package com.rstack.devnet.utility;

public class PostRequest {
    private String contentHeader;
    private String contentBody;

    public PostRequest() {
    }

    public PostRequest(String contentHeader, String contentBody) {
        this.contentHeader = contentHeader;
        this.contentBody = contentBody;
    }

    public String getContentHeader() {
        return contentHeader;
    }

    public void setContentHeader(String contentHeader) {
        this.contentHeader = contentHeader;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }
}
