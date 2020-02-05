package com.rstack.devnet.repository;

import com.rstack.devnet.model.QUESTION;
import com.rstack.devnet.utility.PostQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class QuestionRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String QUESTION_COLLECTION = "QUESTION";

    public QUESTION findQuestionById(String qId) {
        Criteria findQuestionByIdCriteria = new Criteria("qId").is(qId);
        Query query = new Query();
        query.addCriteria(findQuestionByIdCriteria);
        return mongoTemplate.findOne(query, QUESTION.class, QUESTION_COLLECTION);
    }

    public QUESTION insertQuestion(String qId, PostQuestionRequest postQuestionRequest, String currentUser) {
        QUESTION question = new QUESTION();
        question.setQuestionId(qId);
        question.setQuestionHeader(postQuestionRequest.getQuestionHeader());
        question.setQuestionBody(postQuestionRequest.getQuestionBody());
        question.setAnswerObj(null);
        question.setByUser(currentUser);
        question.setCommentObj(null);
        question.setPostedAt(LocalDate.now());
        question.setUpVotes(1);
        question.setDownVotes(0);
        return mongoTemplate.insert(question, QUESTION_COLLECTION);
    }

}
