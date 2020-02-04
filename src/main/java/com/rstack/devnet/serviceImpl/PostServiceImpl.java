package com.rstack.devnet.serviceImpl;

import com.rstack.devnet.repository.QuestionRepository;
import com.rstack.devnet.service.IPostService;
import com.rstack.devnet.utility.PostQuestionRequest;
import com.rstack.devnet.utility.PostQuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class PostServiceImpl implements IPostService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public PostQuestionResponse postAQuestion(PostQuestionRequest postQuestionRequest) {

        return null;
    }
}
