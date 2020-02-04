package com.rstack.devnet.service;

import com.rstack.devnet.utility.PostQuestionRequest;
import com.rstack.devnet.utility.PostQuestionResponse;

public interface IPostService {
    PostQuestionResponse postAQuestion(PostQuestionRequest postQuestionRequest);
}
