package com.rstack.devnet.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HttpBadRequestException extends HttpClientErrorException {

    public HttpBadRequestException(HttpStatus statusCode) {
        super(statusCode);
    }

    public HttpBadRequestException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public HttpBadRequestException(HttpStatus statusCode, String statusText, byte[] body, Charset responseCharset) {
        super(statusCode, statusText, body, responseCharset);
    }

    public HttpBadRequestException(HttpStatus statusCode, String statusText, HttpHeaders headers, byte[] body, Charset responseCharset) {
        super(statusCode, statusText, headers, body, responseCharset);
    }

    public HttpBadRequestException(String message, HttpStatus statusCode, String statusText, HttpHeaders headers, byte[] body, Charset responseCharset) {
        super(message, statusCode, statusText, headers, body, responseCharset);
    }
}