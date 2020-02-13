package com.rstack.devnet.repository;

import com.rstack.devnet.model.ANSWER;
import com.rstack.devnet.model.COMMENT;
import com.rstack.devnet.model.QUESTION;
import com.rstack.devnet.utility.*;
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
public class QuestionRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String QUESTION_COLLECTION = "QUESTION";

//    public String getKeyForCriteria(String postId) {
//        List<String> keyList = new ArrayList<String>(Arrays.asList("questionId", "answerObj.answerId"));
//        for (String key : keyList) {
//            if (mongoTemplate.exists(new Query().addCriteria(new Criteria(key).is(postId)), QUESTION.class, QUESTION_COLLECTION)) {
//                return key;
//            }
//        }
//        return null;
//    }

    public QUESTION findQuestionById(String questionId) {
        Criteria findQuestionByIdCriteria = new Criteria("questionId").is(questionId);
        Query query = new Query();
        query.addCriteria(findQuestionByIdCriteria);
        return mongoTemplate.findOne(query, QUESTION.class, QUESTION_COLLECTION);
    }

    public QUESTION insertQuestion(PostQuestionRequest postQuestionRequest, String currentUser, Instant currentTimestamp, String questionId) {
        QUESTION question = new QUESTION();
        List<COMMENT> comments = new ArrayList<>();
        question.setQuestionId(questionId);
        question.setQuestionHeader(postQuestionRequest.getQuestionHeader());
        question.setQuestionBody(postQuestionRequest.getQuestionBody());
        question.setByUser(currentUser);
        question.setCommentObj(comments);
        question.setPostedAt(currentTimestamp);
        question.setUpVotes(1);
        question.setDownVotes(0);
        return mongoTemplate.insert(question, QUESTION_COLLECTION);
    }

    public PostCommentResponse insertComment(PostCommentRequest postCommentRequest, String username, Instant currentTimestamp, String questionId, String commentId) {
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
            query.addCriteria(new Criteria("questionId").is(questionId));
            mongoTemplate.updateFirst(query, update, QUESTION.class, QUESTION_COLLECTION);
            return new PostCommentResponse("Successfully Added comment to Question!", comment);
        } catch (Exception e) {
            return new PostCommentResponse("Failed to update, Reason: " + e.toString(), null);
        }
    }
}
