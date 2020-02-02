package com.rstack.devnet.repository;

import com.rstack.devnet.model.USER;
import com.rstack.devnet.utility.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String USER_COLLECTION = "USER";

    public USER findByUsernameOrEmail(String usernameOrEmail){
        Criteria loginUserCriteria = new Criteria("username").is(usernameOrEmail);
        Query query = new Query();
        query.addCriteria(loginUserCriteria);
        return mongoTemplate.findOne(query, USER.class, USER_COLLECTION);
    }

    public USER loginUser(LoginRequest loginRequest){
        Criteria loginUsernameCriteria = new Criteria("username").is(loginRequest.getUsername());
        Criteria loginPasswordCriteria = new Criteria("encryptedPassword").is(loginRequest.getPassword());
        Query query = new Query();
        query.addCriteria(loginUsernameCriteria).addCriteria(loginPasswordCriteria);
        return mongoTemplate.findOne(query, USER.class, USER_COLLECTION);
    }

    public boolean checkIfUsernameExists(String username){
//        Criteria loginUsernameCriteria = new Criteria("username").is(authenticationRequest.getUsername());
//        Criteria loginPasswordCriteria = new Criteria("encryptedPassword").is(authenticationRequest.getPassword());
//        Query query = new Query();
//        query.addCriteria(loginUsernameCriteria).addCriteria(loginPasswordCriteria);
//        return mongoTemplate.findOne(query, USER.class, USER_COLLECTION);
        return false;
    }


}
