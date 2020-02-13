package com.rstack.devnet.serviceImpl;

import com.rstack.devnet.model.ANSWER;
import com.rstack.devnet.model.QUESTION;
import com.rstack.devnet.repository.AnswerRepository;
import com.rstack.devnet.repository.QuestionRepository;
import com.rstack.devnet.service.IPostService;
import com.rstack.devnet.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PostServiceImpl implements IPostService {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    String un = auth.getPrincipal().toString();
//    <OR> inject username
//    @CurrentUser
//    private User user;
//    TODO: Try getting username here

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public PostQuestionResponse postAQuestion(PostQuestionRequest postQuestionRequest, String username) {
        Instant currentTimestamp = Instant.now();
        String questionId = "q".concat(UniqueId.getUniqueId(username.concat(currentTimestamp.toString())));
        //^ Because two or more users can post at same instance of time
        questionRepository.insertQuestion(postQuestionRequest, username, currentTimestamp, questionId);
        return new PostQuestionResponse("SUCCESS");
    }

    @Override
    public QUESTION getAQuestion(String questionId) {
        return questionRepository.findQuestionById(questionId);
    }

    @Override
    public List<ANSWER> getAllAnswersOfAQuestion(String questionId) {
        return answerRepository.getAllAnswersByQuestionId(questionId);
    }

    @Override
    public PostAnswerResponse postAnAnswer(PostAnswerRequest postAnswerRequest, String username, String questionId) {
        Instant currentTimestamp = Instant.now();
        String answerId = "a".concat(UniqueId.getUniqueId(username.concat(currentTimestamp.toString())));
        return answerRepository.insertAnswer(postAnswerRequest, username, currentTimestamp, questionId, answerId);
    }

    @Override
    public PostCommentResponse postAComment(PostCommentRequest postCommentRequest, String username, String questionId, String answerId) {
        Instant currentTimestamp = Instant.now();
        String commentId = "c".concat(UniqueId.getUniqueId(username.concat(currentTimestamp.toString())));
        if(!answerId.isEmpty()){
            return answerRepository.insertComment(postCommentRequest, username, currentTimestamp, answerId, commentId);
        }
        return questionRepository.insertComment(postCommentRequest, username, currentTimestamp, questionId, commentId);
    }
}
