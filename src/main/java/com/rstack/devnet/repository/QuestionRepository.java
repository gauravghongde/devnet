package com.rstack.devnet.repository;

import com.rstack.devnet.model.ANSWER;
import com.rstack.devnet.model.QUESTION;
import com.rstack.devnet.utility.PostAnswerRequest;
import com.rstack.devnet.utility.PostAnswerResponse;
import com.rstack.devnet.utility.PostQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;

@Repository
public class QuestionRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String QUESTION_COLLECTION = "QUESTION";

    public QUESTION findQuestionById(String qId) {
        Criteria findQuestionByIdCriteria = new Criteria("questionId").is(qId);
        Query query = new Query();
        query.addCriteria(findQuestionByIdCriteria);
        return mongoTemplate.findOne(query, QUESTION.class, QUESTION_COLLECTION);
    }

    public QUESTION insertQuestion(PostQuestionRequest postQuestionRequest, String currentUser, Instant currentTimestamp, String qId) {
        QUESTION question = new QUESTION();
        question.setQuestionId(qId);
        question.setQuestionHeader(postQuestionRequest.getQuestionHeader());
        question.setQuestionBody(postQuestionRequest.getQuestionBody());
        question.setAnswerObj(null);
        question.setByUser(currentUser);
        question.setCommentObj(null);
        question.setPostedAt(currentTimestamp);
        question.setUpVotes(1);
        question.setDownVotes(0);
        return mongoTemplate.insert(question, QUESTION_COLLECTION);
    }

    public PostAnswerResponse insertAnswer(PostAnswerRequest postAnswerRequest, String username, Instant currentTimestamp, String questionId, String answerId) {
        try {
//            private String answerId;
//            private String forQuestion; //QID
//            private String answerBody;
//            private String byUser; //username
//            private LocalDate postedAt;
//            private int upVotes;
//            private int downVotes;
//            private List<COMMENT> commentObj;
            ANSWER answer = new ANSWER();
            answer.setAnswerBody(postAnswerRequest.getAnswerBody());
            answer.setAnswerId(answerId);
            answer.setByUser(username);
//            answer.setForQuestion(questionId); //remove from dto/model
            answer.setCommentObj(null);
            answer.setPostedAt(currentTimestamp);
            answer.setUpVotes(1);
            answer.setDownVotes(0);
            //////////////////////
            Update update = new Update();
            update.set("answerObj", answer);
            Criteria findQuestionByIdCriteria = new Criteria("questionId").is(questionId);
            Query query = new Query();
            query.addCriteria(findQuestionByIdCriteria);
            mongoTemplate.updateFirst(query, update, QUESTION.class, QUESTION_COLLECTION);
            return new PostAnswerResponse("Successfully updated!", answer);
        } catch (Exception e) {
            return new PostAnswerResponse("Failed to update", null);
        }
    }
}
