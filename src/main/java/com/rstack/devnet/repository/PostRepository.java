package com.rstack.devnet.repository;

import com.mongodb.client.result.UpdateResult;
import com.rstack.devnet.dto.mapper.PostMapper;
import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.exception.RecordNotFoundException;
import com.rstack.devnet.model.Comment;
import com.rstack.devnet.model.Post;
import com.rstack.devnet.model.Vote;
import com.rstack.devnet.utility.CommentRequest;
import com.rstack.devnet.utility.PostRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    private static final String COMMENT_FIELD = "commentObj";
    private static final String QUESTION_ID_FIELD = "questionId";
    private static final String ID_FIELD = "id";
    private static final String NO_OF_ANSWERS = "noOfAnswers";

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PostMapper postMapper;

    public PostDTO insertPost(PostRequest postRequest, String username, Instant currentTimestamp, String questionId, String answerId) {
        List<Comment> comments = new ArrayList<>();
        Vote vote = new Vote();
        Post post = new Post();
        try {
            if (answerId.isEmpty()) {
                post.setId(questionId);
                post.setQuestionHeader(postRequest.getContentHeader());
                post.setQuestionBody(postRequest.getContentBody());
                post.setNoOfAnswers(0);
            } else {
                post.setId(answerId);
                post.setAnswerBody(postRequest.getContentBody());
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
        } catch (Exception e) {
            LOG.info("Insert post failed, reason - "+ e.getMessage());
        }
        LOG.info("Post inserted successfully");
        return postMapper.toPostDTO(post);
    }

    private void incNoOfAnswersOfQue(String questionId) {
        Update update = new Update();
        Query query = new Query(where(ID_FIELD).is(questionId));
        Post question = mongoTemplate.findOne(query, Post.class);
        if (question == null) {
            LOG.warn("question not found with the questionId - " + questionId);
            throw new RecordNotFoundException("Update NoOfAnswers Failed");
        }
        update.inc(NO_OF_ANSWERS, 1);
        mongoTemplate.findAndModify(query, update, Post.class, POST_COLLECTION);
        LOG.info("NoOfAnswers incremented successfully");
    }

    public Comment insertComment(CommentRequest commentRequest, String username, Instant currentTimestamp, String postId, String commentId) {
        Comment comment = new Comment();
        Vote vote = new Vote();
        Update update = new Update();
        comment.setBody(commentRequest.getCommentBody());
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
            LOG.info("Insert comment failed, reason - "+ e.getMessage());
            return null;
        }
    }

    public List<PostDTO> getAllPostsByQuestionId(String questionId) {
        Query query = new Query();
        List<Post> posts = null;
        try {
            query.addCriteria(where(QUESTION_ID_FIELD).is(questionId));
            posts = mongoTemplate.find(query, Post.class, POST_COLLECTION);
        } catch (Exception e) {
            LOG.info("Get posts failed, reason - "+ e.getMessage());
        }
        LOG.info("Retrieved posts successfully");
        return postMapper.toPostDTOList(posts);
    }

    public PostDTO getQuestionById(String questionId) {
        Criteria findQuestionByIdCriteria = new Criteria(ID_FIELD).is(questionId);
        Query query = new Query();
        query.addCriteria(findQuestionByIdCriteria);
        Post post = mongoTemplate.findOne(query, Post.class, POST_COLLECTION);
        return postMapper.toPostDTO(post);
    }


}
