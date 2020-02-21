package com.rstack.devnet.serviceImpl;

import com.rstack.devnet.model.POST;
import com.rstack.devnet.repository.AnswerRepository;
import com.rstack.devnet.repository.PostRepository;
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
    private PostRepository postRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public PostResponse postAQuestion(PostRequest postRequest, String username) {
        Instant currentTimestamp = Instant.now();
        String questionId = "q".concat(UniqueId.getUniqueId(username.concat(currentTimestamp.toString())));
        //^ Because two or more users can post at same instance of time
        return postRepository.insertPost(postRequest, username, currentTimestamp, questionId, "");
    }

    @Override
    public POST getAQuestion(String questionId) {
        return questionRepository.findQuestionById(questionId);
    }

    @Override
    public List<POST> getAllAnswersOfAQuestion(String questionId) {
        return answerRepository.getAllAnswersByQuestionId(questionId);
    }

    @Override
    public PostResponse postAnAnswer(PostRequest postRequest, String username, String questionId) {
        Instant currentTimestamp = Instant.now();
        String answerId = "a".concat(UniqueId.getUniqueId(username.concat(currentTimestamp.toString())));
        return postRepository.insertPost(postRequest, username, currentTimestamp, questionId, answerId);
    }

    @Override
    public CommentResponse postAComment(CommentRequest commentRequest, String username, String postId) {
        Instant currentTimestamp = Instant.now();
        String commentId = "c".concat(UniqueId.getUniqueId(username.concat(currentTimestamp.toString())));
        return postRepository.insertComment(commentRequest, username, currentTimestamp, postId, commentId);
    }
}
