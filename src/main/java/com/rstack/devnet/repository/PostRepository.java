package com.rstack.devnet.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.rstack.devnet.dto.mapper.PostMapper;
import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.exception.HttpBadRequestException;
import com.rstack.devnet.exception.RecordNotFoundException;
import com.rstack.devnet.model.Comment;
import com.rstack.devnet.model.Post;
import com.rstack.devnet.model.Vote;
import com.rstack.devnet.shared.PostType;
import com.rstack.devnet.utility.CommentRequest;
import com.rstack.devnet.utility.PostRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

//Criteria regex = Criteria.where("postId").regex("^q.*", "i");
//mongoOperations.find(new Query().addCriteria(regex), User.class);

@Repository
public class PostRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PostRepository.class);
    private static final String POST_COLLECTION = "POST";
    private static final String ANSWER_BODY_FIELD = "answerBody";
    private static final String QUESTION_BODY_FIELD = "questionBody";
    private static final String QUESTION_HEADER_FIELD = "questionHeader";
    private static final String COMMENT_FIELD = "comments";
    private static final String USERNAME_FIELD = "username";
    private static final String QUESTION_ID_FIELD = "questionId";
    private static final String ID_FIELD = "id";
    private static final String NO_OF_ANSWERS_FIELD = "noOfAnswers";
    private static final String IS_EDITED_FIELD = "isEdited";

    private Criteria idQuestionMatchCriteria = where(ID_FIELD).regex("^".concat(PostType.QUESTION.getIdPrefix()));
    private Criteria idAnswerMatchCriteria = where(ID_FIELD).regex("^".concat(PostType.ANSWER.getIdPrefix()));

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PostMapper postMapper;

    public PostDTO insertPost(PostRequest postRequest, String username, Instant currentTimestamp, String questionId, String answerId) {
        List<Comment> comments = new ArrayList<>();
        Vote vote = new Vote();
        Post post = new Post();
        if (answerId.isEmpty()) {
            post.setId(questionId);
            post.setQuestionHeader(postRequest.getQuestionHeader());
            post.setQuestionBody(postRequest.getQuestionBody());
            post.setNoOfAnswers(0);
        } else {
            post.setId(answerId);
            post.setAnswerBody(postRequest.getAnswerBody());
            incNoOfAnswersOfQue(questionId);
        }
        post.setQuestionId(questionId);
        post.setUsername(username);
        post.setComments(comments);
        post.setUsersInteracted(new HashMap<>());
        post.setPostedAt(currentTimestamp);
        vote.setUpVotes(0);
        vote.setDownVotes(0);
        post.setVote(vote);
        mongoTemplate.insert(post, POST_COLLECTION);
        LOG.info("Post inserted successfully");
        return postMapper.toPostDTO(post);
    }

    public Comment insertComment(CommentRequest commentRequest, String username, Instant currentTimestamp, String postId, String commentId) {
        Comment comment = new Comment();
        Vote vote = new Vote();
        Update update = new Update();
        comment.setBody(commentRequest.getBody());
        comment.setId(commentId);
        comment.setUsername(username);
        comment.setPostedAt(currentTimestamp);
        vote.setUpVotes(0);
        vote.setDownVotes(0);
        comment.setVote(vote);
        comment.setUsersInteracted(new HashMap<>());
        Query query = new Query();
        update.push(COMMENT_FIELD, comment);
        try {
            query.addCriteria(new Criteria(ID_FIELD).is(postId));
            UpdateResult result = mongoTemplate.updateFirst(query, update, Post.class, POST_COLLECTION);
            if (result == null || result.getModifiedCount() == 0) {
                LOG.info("No comment inserted");
            }
            return comment;
        } catch (Exception e) {
            LOG.info("Insert comment failed, reason - " + e.getMessage());
            return null;
        }
    }

    public PostDTO updatePostById(PostRequest postUpdateRequest, String postId) {
        Query query = new Query(where(ID_FIELD).is(postId));
        Update update = new Update();
        if (postId.startsWith(PostType.QUESTION.getIdPrefix())) {
            update.set(QUESTION_BODY_FIELD, postUpdateRequest.getQuestionBody());
            update.set(QUESTION_HEADER_FIELD, postUpdateRequest.getQuestionHeader());
        } else if (postId.startsWith(PostType.ANSWER.getIdPrefix())) {
            update.set(ANSWER_BODY_FIELD, postUpdateRequest.getAnswerBody());
        } else {
            throw new HttpBadRequestException(HttpStatus.BAD_REQUEST, "Id provided is illegal - " + postId);
        }
        UpdateResult result = mongoTemplate.upsert(query, update, Post.class);
        LOG.info("Update Post Modified count - " + result.getModifiedCount());
        Post post = mongoTemplate.findOne(query, Post.class);
        if (post == null) {
            throw new RecordNotFoundException(postId);
        }
        return postMapper.toPostDTO(post);
    }

    public void updateCommentById(List<Comment> updatedCommentList, String postId) {
        Query query = new Query(where(ID_FIELD).is(postId));
        Update update = new Update();
        update.set(COMMENT_FIELD, updatedCommentList);
        mongoTemplate.findAndModify(query, update, Post.class);
    }

    public PostDTO getPostById(String postId) {
        Query query = new Query(where(ID_FIELD).is(postId));
        Post post = mongoTemplate.findOne(query, Post.class, POST_COLLECTION);
        if (post == null) {
            String errMsg = "Post not found with the postId - " + postId;
            LOG.warn(errMsg);
            throw new RecordNotFoundException("GetPostById Failed " + errMsg);
        }
        return postMapper.toPostDTO(post);
    }

    private void incNoOfAnswersOfQue(String questionId) {
        Update update = new Update();
        Query query = new Query(where(ID_FIELD).is(questionId));
        Post question = mongoTemplate.findOne(query, Post.class);
        if (question == null) {
            String errMsg = "Question not found with the questionId - " + questionId;
            LOG.warn(errMsg);
            throw new RecordNotFoundException("Update NoOfAnswers Failed " + errMsg);
        }
        update.inc(NO_OF_ANSWERS_FIELD, 1);
        mongoTemplate.findAndModify(query, update, Post.class, POST_COLLECTION);
        LOG.info("NoOfAnswers incremented successfully");
    }

    public List<PostDTO> getAllPostsByQuestionId(String questionId) {
        Query query = new Query();
        List<Post> posts = null;
        try {
            query.addCriteria(where(QUESTION_ID_FIELD).is(questionId));
            posts = mongoTemplate.find(query, Post.class, POST_COLLECTION);
        } catch (Exception e) {
            LOG.info("Get posts failed, reason - " + e.getMessage());
        }
        LOG.info("Retrieved posts successfully");
        return postMapper.toPostDTOList(posts);
    }

    public PostDTO getQuestionById(String questionId) {
        Criteria findQuestionByIdCriteria = new Criteria(ID_FIELD).is(questionId);
        Query query = new Query();
        query.addCriteria(findQuestionByIdCriteria);
        Post question = mongoTemplate.findOne(query, Post.class, POST_COLLECTION);
        if (question == null) {
            throw new RecordNotFoundException("getQuestionById Failed, for questionId - " + questionId);
        }
        LOG.info("Question {} successfully retrieved", questionId);
        return postMapper.toPostDTO(question);
    }

    public List<PostDTO> getAllQuestionsByUsername(String username) {
        Criteria usernameMatchCriteria = where(USERNAME_FIELD).is(username);
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(idQuestionMatchCriteria, usernameMatchCriteria));
        List<Post> questions = mongoTemplate.find(query, Post.class, POST_COLLECTION);
        if (questions.isEmpty()) {
            throw new RecordNotFoundException("getAllQuestionsByUsername Failed, for username - " + username);
        }
        LOG.info("getAllQuestionsByUsername for {} successfully retrieved", username);
        return postMapper.toPostDTOList(questions);
    }

    public List<PostDTO> getAllAnswersByUsername(String username) {
        Criteria usernameMatchCriteria = Criteria.where(USERNAME_FIELD).is(username);
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(idAnswerMatchCriteria, usernameMatchCriteria));
        List<Post> posts = mongoTemplate.find(query, Post.class, POST_COLLECTION);
        return postMapper.toPostDTOList(posts);
    }

    public List<PostDTO> getAllQuestions() {
        Query query = new Query();
        query.addCriteria(idQuestionMatchCriteria);
        List<Post> posts = mongoTemplate.find(query, Post.class, POST_COLLECTION);
        return postMapper.toPostDTOList(posts);
    }

    public void deletePostById(String postId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID_FIELD).is(postId));
        DeleteResult deleteResult = mongoTemplate.remove(query, Post.class);
        if (deleteResult.getDeletedCount() != 1) {
            throw new RecordNotFoundException("Delete post failed!, postId - " + postId);
        }
        LOG.info("Record successfully deleted, postId - {}\n", postId);
    }

}
