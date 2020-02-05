package com.rstack.devnet.serviceImpl;

import com.rstack.devnet.repository.QuestionRepository;
import com.rstack.devnet.service.IPostService;
import com.rstack.devnet.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public PostQuestionResponse postAQuestion(PostQuestionRequest postQuestionRequest, String username) {
        questionRepository.insertQuestion("q1234", postQuestionRequest, username);
        return new PostQuestionResponse("SUCCESS");
    }

    @Override
    public PostAnswerResponse postAnAnswer(PostAnswerRequest postAnswerRequest) {
        return null;
    }

    @Override
    public PostCommentResponse postAComment(PostCommentRequest postCommentRequest) {
        return null;
    }
}
