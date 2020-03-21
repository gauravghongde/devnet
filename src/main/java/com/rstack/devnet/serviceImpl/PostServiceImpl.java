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
        String questionId = "q".concat(UniqueIdHelper.getUniqueId(username.concat(currentTimestamp.toString())));
        //^ Because two or more users can post at same instance of time
        return postRepository.insertPost(postRequest, username, currentTimestamp, questionId, "");
    }

    @Override
    public POST getAQuestion(String questionId, String username) {
        POST postRetObj;
        postRetObj = questionRepository.findQuestionById(questionId);
        postRetObj.setVoteStatus(postRetObj.getUsersInteracted().getOrDefault(username,0));
        postRetObj.getCommentObj().stream().forEach(commentObj -> commentObj.setVoteStatus(commentObj.getUsersInteracted().getOrDefault(username,0)));
        return postRetObj;
    }

    @Override
    public List<POST> getAllAnswersOfAQuestion(String questionId, String username) {
        List<POST> ansListRetObj;
        ansListRetObj = answerRepository.getAllAnswersByQuestionId(questionId);
        ansListRetObj.stream().forEach(ansObj -> {
            ansObj.setVoteStatus(ansObj.getUsersInteracted().getOrDefault(username, 0));
            ansObj.getCommentObj().stream().forEach(commentObj -> commentObj.setVoteStatus(commentObj.getUsersInteracted().getOrDefault(username,0)));
        });
        return ansListRetObj;
    }

    @Override
    public PostResponse postAnAnswer(PostRequest postRequest, String username, String questionId) {
        Instant currentTimestamp = Instant.now();
        String answerId = "a".concat(UniqueIdHelper.getUniqueId(username.concat(currentTimestamp.toString())));
        return postRepository.insertPost(postRequest, username, currentTimestamp, questionId, answerId);
    }

    @Override
    public CommentResponse postAComment(CommentRequest commentRequest, String username, String postId) {
        Instant currentTimestamp = Instant.now();
        String commentId = "c".concat(UniqueIdHelper.getUniqueId(username.concat(currentTimestamp.toString())));
        return postRepository.insertComment(commentRequest, username, currentTimestamp, postId, commentId);
    }
}
