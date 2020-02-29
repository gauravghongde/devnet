package com.rstack.devnet.repository;

import com.rstack.devnet.model.USER;
import com.rstack.devnet.utility.LoginRequest;
import com.rstack.devnet.utility.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String USER_COLLECTION = "USER";

    public USER findByUsernameOrEmail(String usernameOrEmail) {
        Criteria loginUserCriteria = new Criteria("username").is(usernameOrEmail);
        Query query = new Query();
        query.addCriteria(loginUserCriteria);
        return mongoTemplate.findOne(query, USER.class, USER_COLLECTION);
    }

    public USER loginUser(LoginRequest loginRequest) {
        Criteria loginUsernameCriteria = new Criteria("username").is(loginRequest.getUsername());
        Query query = new Query();
        query.addCriteria(loginUsernameCriteria);
        return mongoTemplate.findOne(query, USER.class, USER_COLLECTION);
    }

    public boolean checkIfUsernameExists(String username) {
        Criteria usernameCriteria = new Criteria("username").is(username);
        Query query = new Query();
        query.addCriteria(usernameCriteria);
        List<USER> users = mongoTemplate.find(query, USER.class, USER_COLLECTION);
        return users.size() > 0; //returns TRUE if exists
    }

    public boolean checkIfEmailExists(String emailId) {
        Criteria emailIdCriteria = new Criteria("emailId").is(emailId);
        Query query = new Query();
        query.addCriteria(emailIdCriteria);
        List<USER> users = mongoTemplate.find(query, USER.class, USER_COLLECTION);
        return users.size() > 0; //returns TRUE if exists
    }

    public USER registerUser(RegisterRequest registerRequest) {
        USER user = new USER();
        user.setUsername(registerRequest.getUsername());
        user.setEncryptedPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        return mongoTemplate.insert(user, USER_COLLECTION);
    }
}
