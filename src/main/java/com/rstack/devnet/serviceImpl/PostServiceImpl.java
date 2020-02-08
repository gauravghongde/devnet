package com.rstack.devnet.serviceImpl;

import com.rstack.devnet.model.QUESTION;
import com.rstack.devnet.repository.QuestionRepository;
import com.rstack.devnet.service.IPostService;
import com.rstack.devnet.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PostServiceImpl implements IPostService {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    String un = auth.getPrincipal().toString();

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public PostQuestionResponse postAQuestion(PostQuestionRequest postQuestionRequest, String username) {
        Instant currentTimestamp = Instant.now();
        String qId = UniqueId.getUniqueId(currentTimestamp.toString());
        questionRepository.insertQuestion(postQuestionRequest, username, currentTimestamp, qId);
        return new PostQuestionResponse("SUCCESS");
    }

    @Override
    public QUESTION getAQuestion(String qId) {
        return questionRepository.findQuestionById(qId);
    }

    @Override
    public PostAnswerResponse postAnAnswer(PostAnswerRequest postAnswerRequest, String username, String questionId) {
        Instant currentTimestamp = Instant.now();
        String answerId = UniqueId.getUniqueId(currentTimestamp.toString());
        return questionRepository.insertAnswer(postAnswerRequest, username, currentTimestamp, questionId, answerId);
    }

    @Override
    public PostCommentResponse postAComment(PostCommentRequest postCommentRequest) {
        return null;
    }
}
