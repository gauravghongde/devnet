package com.rstack.devnet.service;

import com.rstack.devnet.model.ANSWER;
import com.rstack.devnet.model.QUESTION;
import com.rstack.devnet.utility.*;

import java.util.List;

public interface IPostService {
    PostQuestionResponse postAQuestion(PostQuestionRequest postQuestionRequest, String username);

    QUESTION getAQuestion(String questionId);

    List<ANSWER> getAllAnswersOfAQuestion(String questionId);

    PostAnswerResponse postAnAnswer(PostAnswerRequest postAnswerRequest, String username, String questionId);

    PostCommentResponse postAComment(PostCommentRequest postCommentRequest, String username, String questionId, String answerId);
}
