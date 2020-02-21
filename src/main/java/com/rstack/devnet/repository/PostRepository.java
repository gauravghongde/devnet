package com.rstack.devnet.repository;

import com.rstack.devnet.model.COMMENT;
import com.rstack.devnet.model.POST;
import com.rstack.devnet.utility.CommentRequest;
import com.rstack.devnet.utility.CommentResponse;
import com.rstack.devnet.utility.PostRequest;
import com.rstack.devnet.utility.PostResponse;
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
import java.util.stream.IntStream;

import static org.springframework.data.mongodb.core.query.Criteria.where;

//Criteria regex = Criteria.where("postId").regex("^q.*", "i");
//mongoOperations.find(new Query().addCriteria(regex), User.class);

@Repository
public class PostRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private static final String POST_COLLECTION = "POST";
    private static final int NOVOTE = 0;
    private static final int DOWNVOTE = 1;
    private static final int UPVOTE = 2;

    public PostResponse insertPost(PostRequest postRequest, String username, Instant currentTimestamp, String questionId, String answerId) {
        // TODO: Show warning if same user adds answer for same question, suggest for editing present answer
        try {
            List<COMMENT> comments = new ArrayList<>();
            POST post = new POST();
            if (answerId.isEmpty()) {
                post.setPostId(questionId);
                post.setQuestionHeader(postRequest.getContentHeader());
                post.setQuestionBody(postRequest.getContentBody());
            } else {
                post.setPostId(answerId);
                post.setAnswerBody(postRequest.getContentBody());
                post.setForQuestion(questionId);
            }
            post.setPostedBy(username);
            post.setCommentObj(comments);
            post.setUsersInteracted(new HashMap<>());
            post.setPostedAt(currentTimestamp);
            post.setUpVotes(0);
            post.setDownVotes(0);
            mongoTemplate.insert(post, POST_COLLECTION);
            return new PostResponse("Successfully updated!", post);
        } catch (Exception e) {
            return new PostResponse("Failed to update, Reason: " + e.toString(), null);
        }
    }

    public CommentResponse insertComment(CommentRequest commentRequest, String username, Instant currentTimestamp, String postId, String commentId) {
        COMMENT comment = new COMMENT();
        Update update = new Update();
        comment.setCommentBody(commentRequest.getCommentBody());
        comment.setCommentId(commentId);
        comment.setByUser(username);
        comment.setPostedAt(currentTimestamp);
        comment.setUpVotes(0);
        comment.setDownVotes(0);
        comment.setUsersInteracted(new HashMap<>());
        Query query = new Query();
        update.push("commentObj", comment);
        try {
            query.addCriteria(new Criteria("postId").is(postId));
            mongoTemplate.updateFirst(query, update, POST.class, POST_COLLECTION);
            return new CommentResponse("Successfully added comment to Answer!", comment);
        } catch (Exception e) {
            return new CommentResponse("Failed to update, Reason: " + e.toString(), null);
        }
    }

    public String insertUserVote(String username, int voteId, String postId, String commentId) {
        int index = -1;
        Update update = new Update();
        Query query = new Query(where("postId").is(postId));
        POST postObj = mongoTemplate.findOne(query, POST.class);
        try {
            if (commentId.isEmpty()) {
                update.set("usersInteracted." + username, voteId);

                int prevVoteId = postObj.getUsersInteracted().getOrDefault(username, 0);
                if (prevVoteId == NOVOTE) {
                    if (voteId == UPVOTE) {
                        update.inc("upVotes", 1);
                    } else if (voteId == DOWNVOTE) {
                        update.inc("downVotes", 1);
                    }
                } else if (prevVoteId == UPVOTE) {
                    update.inc("upVotes", -1);
                    if (voteId == DOWNVOTE) {
                        update.inc("downVotes", 1);
                    }
                } else {
                    update.inc("downVotes", -1);
                    if (voteId == UPVOTE) {
                        update.inc("upVotes", 1);
                    }
                }
            } else {
                List<COMMENT> cmtList = postObj.getCommentObj();
                index = IntStream.range(0, cmtList.size())
                        .filter(i -> commentId.equals(cmtList.get(i).getCommentId()))
                        .findFirst().getAsInt();
                update.set("commentObj." + index + ".usersInteracted." + username, voteId);

                int prevVoteId = cmtList.get(index).getUsersInteracted().getOrDefault(username, 0);
                if (prevVoteId == NOVOTE) {
                    if (voteId == UPVOTE) {
                        update.inc("commentObj." + index + ".upVotes", 1);
                    } else if (voteId == DOWNVOTE) {
                        update.inc("commentObj." + index + ".downVotes", 1);
                    }
                } else if (prevVoteId == UPVOTE) {
                    update.inc("commentObj." + index + ".upVotes", -1);
                    if (voteId == DOWNVOTE) {
                        update.inc("commentObj." + index + ".downVotes", 1);
                    }
                } else {
                    update.inc("commentObj." + index + ".downVotes", -1);
                    if (voteId == UPVOTE) {
                        update.inc("commentObj." + index + ".upVotes", 1);
                    }
                }
            }
            mongoTemplate.findAndModify(query, update, POST.class, POST_COLLECTION);
            return "SUCCESS " + index;
        } catch (Exception e) {
            return "FAILURE- ".concat(e.toString());
        }
    }
}
