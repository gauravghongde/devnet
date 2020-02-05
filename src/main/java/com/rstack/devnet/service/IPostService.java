package com.rstack.devnet.service;

import com.rstack.devnet.utility.*;

public interface IPostService {
    PostQuestionResponse postAQuestion(PostQuestionRequest postQuestionRequest, String username);

    PostAnswerResponse postAnAnswer(PostAnswerRequest postAnswerRequest);

    PostCommentResponse postAComment(PostCommentRequest postCommentRequest);
}
