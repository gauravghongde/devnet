package com.rstack.devnet.repository;

import com.rstack.devnet.model.ANSWER;
import com.rstack.devnet.model.COMMENT;
import com.rstack.devnet.utility.PostAnswerRequest;
import com.rstack.devnet.utility.PostAnswerResponse;
import com.rstack.devnet.utility.PostCommentRequest;
import com.rstack.devnet.utility.PostCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnswerRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String ANSWER_COLLECTION = "ANSWER";

//    public List<ANSWER> findAnswersByQuestion(String questionId){
//
//    }
//
    public PostAnswerResponse insertAnswer(PostAnswerRequest postAnswerRequest, String username, Instant currentTimestamp, String questionId, String answerId) {
        // TODO: Show warning if same user adds answer for same question, suggest for editing present answer
        try {
            List<COMMENT> comments = new ArrayList<>();
            ANSWER answer = new ANSWER();
            answer.setAnswerBody(postAnswerRequest.getAnswerBody());
            answer.setAnswerId(answerId);
            answer.setByUser(username);
            answer.setForQuestion(questionId);
            answer.setCommentObj(comments);
            answer.setPostedAt(currentTimestamp);
            answer.setUpVotes(1);
            answer.setDownVotes(0);
            mongoTemplate.insert(answer, ANSWER_COLLECTION);
            return new PostAnswerResponse("Successfully updated!", answer);
        } catch (Exception e) {
            return new PostAnswerResponse("Failed to update, Reason: " + e.toString(), null);
        }
    }

    public PostCommentResponse insertComment(PostCommentRequest postCommentRequest, String username, Instant currentTimestamp, String answerId, String commentId) {
        COMMENT comment = new COMMENT();
        Update update = new Update();
        comment.setCommentBody(postCommentRequest.getCommentBody());
        comment.setCommentId(commentId);
        comment.setByUser(username);
        comment.setPostedAt(currentTimestamp);
        comment.setUpVotes(1);
        comment.setDownVotes(0);
        Query query = new Query();
        update.push("commentObj", comment);
        try {
            query.addCriteria(new Criteria("answerId").is(answerId));
            mongoTemplate.updateFirst(query, update, ANSWER.class, ANSWER_COLLECTION);
            return new PostCommentResponse("Successfully added comment to Answer!", comment);
        } catch (Exception e) {
            return new PostCommentResponse("Failed to update, Reason: " + e.toString(), null);
        }
    }

    public List<ANSWER> getAllAnswersByQuestionId(String questionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("forQuestion").is(questionId));
        return mongoTemplate.find(query, ANSWER.class, ANSWER_COLLECTION);
    }
}
