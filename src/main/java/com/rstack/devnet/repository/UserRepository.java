package com.rstack.devnet.repository;

import com.rstack.devnet.dto.mapper.UserMapper;
import com.rstack.devnet.dto.model.UserDTO;
import com.rstack.devnet.exception.RecordNotFoundException;
import com.rstack.devnet.model.User;
import com.rstack.devnet.utility.LoginRequest;
import com.rstack.devnet.utility.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    private static final String USER_COLLECTION = "USER";
    private static final String USERNAME_FIELD = "username";
    private static final String EMAIL_FIELD = "email";
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsernameOrEmail(String usernameOrEmail) {
        Criteria loginUserCriteria = new Criteria(USERNAME_FIELD).is(usernameOrEmail);
        Query query = new Query();
        query.addCriteria(loginUserCriteria);
        return mongoTemplate.findOne(query, User.class, USER_COLLECTION);
    }

    public User loginUser(LoginRequest loginRequest) {
        Criteria loginUsernameCriteria = new Criteria(USERNAME_FIELD).is(loginRequest.getUsername());
        Query query = new Query();
        query.addCriteria(loginUsernameCriteria);
        return mongoTemplate.findOne(query, User.class, USER_COLLECTION);
    }

    public boolean checkIfUsernameExists(String username) {
        Criteria usernameCriteria = new Criteria(USERNAME_FIELD).is(username);
        Query query = new Query();
        query.addCriteria(usernameCriteria);
        List<User> users = mongoTemplate.find(query, User.class, USER_COLLECTION);
        return users.size() > 0; //returns TRUE if exists
    }

    public boolean checkIfEmailExists(String emailId) {
        Criteria emailIdCriteria = new Criteria(EMAIL_FIELD).is(emailId);
        Query query = new Query();
        query.addCriteria(emailIdCriteria);
        List<User> users = mongoTemplate.find(query, User.class, USER_COLLECTION);
        return users.size() > 0; //returns TRUE if exists
    }

    public User registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEncryptedPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        return mongoTemplate.insert(user, USER_COLLECTION);
    }

    public UserDTO getUserProfile(String username) {
        Query query = new Query();
        Criteria usernameCriteria = new Criteria(USERNAME_FIELD).is(username);
        query.addCriteria(usernameCriteria);
        User user = mongoTemplate.findOne(query, User.class, USER_COLLECTION);
        if (user == null) {
            throw new RecordNotFoundException("GetUserProfile Failed, for username - " + username);
        }
        LOG.info("User {} successfully retrieved", username);
        return userMapper.toUserDTO(user);
    }
}
