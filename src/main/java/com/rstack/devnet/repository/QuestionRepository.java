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
        List<ANSWER> answers = new ArrayList<>();
        List<COMMENT> comments = new ArrayList<>();
        question.setQuestionId(questionId);
        question.setQuestionHeader(postQuestionRequest.getQuestionHeader());
        question.setQuestionBody(postQuestionRequest.getQuestionBody());
        question.setAnswerObj(answers);
        question.setByUser(currentUser);
        question.setCommentObj(comments);
        question.setPostedAt(currentTimestamp);
        question.setUpVotes(1);
        question.setDownVotes(0);
        return mongoTemplate.insert(question, QUESTION_COLLECTION);
    }

    public PostAnswerResponse insertAnswer(PostAnswerRequest postAnswerRequest, String username, Instant currentTimestamp, String questionId, String answerId) {
        // TODO: Show warning if same user adds answer for same question, suggest for editing present answer
        try {
            List<COMMENT> comments = new ArrayList<>();
            ANSWER answer = new ANSWER();
            Update update = new Update();
            Query query = new Query();
            answer.setAnswerBody(postAnswerRequest.getAnswerBody());
            answer.setAnswerId(answerId);
            answer.setByUser(username);
            answer.setCommentObj(comments);
            answer.setPostedAt(currentTimestamp);
            answer.setUpVotes(1);
            answer.setDownVotes(0);
            update.push("answerObj", answer);
            Criteria findQuestionByIdCriteria = new Criteria("questionId").is(questionId);
            query.addCriteria(findQuestionByIdCriteria);
            mongoTemplate.updateFirst(query, update, QUESTION.class, QUESTION_COLLECTION);
            return new PostAnswerResponse("Successfully updated!", answer);
        } catch (Exception e) {
            return new PostAnswerResponse("Failed to update, Reason: " + e.toString(), null);
        }
    }

    public PostCommentResponse insertComment(PostCommentRequest postCommentRequest, String username, Instant currentTimestamp, String questionId, String answerId, String commentId) {
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
            query.addCriteria(answerId.isEmpty() ?
                    (new Criteria("questionId").is(questionId)) :
                    (new Criteria("answerObj.answerId").is(answerId).andOperator(Criteria.where("questionId").is(questionId))));
            mongoTemplate.updateFirst(query, update, QUESTION.class, QUESTION_COLLECTION);
            return new PostCommentResponse("Successfully updated!", comment);
        } catch (Exception e) {
            return new PostCommentResponse("Failed to update, Reason: " + e.toString(), null);
        }
    }
}
