package com.rstack.devnet.repository;

import com.rstack.devnet.model.POST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String POST_COLLECTION = "POST";

//    public String getKeyForCriteria(String postId) {
//        List<String> keyList = new ArrayList<String>(Arrays.asList("questionId", "answerObj.answerId"));
//        for (String key : keyList) {
//            if (mongoTemplate.exists(new Query().addCriteria(new Criteria(key).is(postId)), QUESTION.class, QUESTION_COLLECTION)) {
//                return key;
//            }
//        }
//        return null;
//    }

    public POST findQuestionById(String questionId) {
        Criteria findQuestionByIdCriteria = new Criteria("postId").is(questionId);
        Query query = new Query();
        query.addCriteria(findQuestionByIdCriteria);
        return mongoTemplate.findOne(query, POST.class, POST_COLLECTION);
    }

}
