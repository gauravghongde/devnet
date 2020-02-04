package com.rstack.devnet.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String QUESTION_COLLECTION = "QUESTION";



}
