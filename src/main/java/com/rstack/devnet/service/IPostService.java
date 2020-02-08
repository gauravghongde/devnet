package com.rstack.devnet.service;

import com.rstack.devnet.model.QUESTION;
import com.rstack.devnet.utility.*;

public interface IPostService {
    PostQuestionResponse postAQuestion(PostQuestionRequest postQuestionRequest, String username);

    QUESTION getAQuestion(String qId);

    PostAnswerResponse postAnAnswer(PostAnswerRequest postAnswerRequest, String username, String questionId);

    PostCommentResponse postAComment(PostCommentRequest postCommentRequest);
}
