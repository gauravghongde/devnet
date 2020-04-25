package com.rstack.devnet.repository;

import com.rstack.devnet.model.Comment;
import com.rstack.devnet.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class VoteRepository {

    private static final Logger LOG = LoggerFactory.getLogger(VoteRepository.class);

    private static final String POST_COLLECTION = "POST";
    private static final int NOVOTE_ID = 0;
    private static final int DOWNVOTE_ID = 1;
    private static final int UPVOTE_ID = 2;
    private static final String ID_FIELD = "id";
    private static final String COMMENT_FIELD = "commentObj";
    private static final String UPVOTE_FIELD = "vote.upVotes";
    private static final String DOWNVOTE_FIELD = "vote.downVotes";
    private static final String COMMENT_VOTE_FIELD = COMMENT_FIELD + ".%d.%s";
    private static final String USERS_INTERACTED_FIELD = "usersInteracted.%s";
    private static final String COMMENT_USERS_INTERACTED_FIELD = COMMENT_FIELD + ".%d." + USERS_INTERACTED_FIELD;
    @Autowired
    private MongoTemplate mongoTemplate;

    public String insertVote(String username, int voteId, String postId, String commentId) {
        int index = -1;
        Update update = new Update();
        Query query = new Query(where(ID_FIELD).is(postId));
        Post post = mongoTemplate.findOne(query, Post.class);
        try {
            if (commentId.isEmpty()) {
                update.set(String.format(USERS_INTERACTED_FIELD, username), voteId);
                int prevVoteId = post.getUsersInteracted().getOrDefault(username, 0);

                switch (prevVoteId) {
                    case NOVOTE_ID:
                        if (voteId == UPVOTE_ID) {
                            update.inc(UPVOTE_FIELD, 1);
                            LOG.info("[upvote++]");
                        } else if (voteId == DOWNVOTE_ID) {
                            update.inc(DOWNVOTE_FIELD, 1);
                            LOG.info("[downvote++]");
                        }
                        break;
                    case UPVOTE_ID:
                        update.inc(UPVOTE_FIELD, -1);
                        LOG.info("[upvote--]");
                        if (voteId == DOWNVOTE_ID) {
                            update.inc(DOWNVOTE_FIELD, 1);
                            LOG.info("[downvote++]");
                        }
                        break;
                    case DOWNVOTE_ID:
                        update.inc(DOWNVOTE_FIELD, -1);
                        LOG.info("[downvote--]");
                        if (voteId == UPVOTE_ID) {
                            update.inc(UPVOTE_FIELD, 1);
                            LOG.info("[upvote++]");
                        }
                }
            } else {
                List<Comment> cmtList = post.getComments();
                index = IntStream.range(0, cmtList.size())
                        .filter(i -> commentId.equals(cmtList.get(i).getId()))
                        .findFirst().getAsInt();
                update.set(String.format(COMMENT_USERS_INTERACTED_FIELD, index, username), voteId);

                int prevVoteId = cmtList.get(index).getUsersInteracted().getOrDefault(username, 0);
                switch (prevVoteId) {
                    case NOVOTE_ID:
                        if (voteId == UPVOTE_ID) {
                            incCommentVote(update, index, UPVOTE_FIELD, 1);
                            LOG.info("[comment upvote++]");
                        } else if (voteId == DOWNVOTE_ID) {
                            incCommentVote(update, index, DOWNVOTE_FIELD, 1);
                            LOG.info("[comment downvote++]");
                        }
                        break;
                    case UPVOTE_ID:
                        incCommentVote(update, index, UPVOTE_FIELD, -1);
                        LOG.info("[comment upvote--]");
                        if (voteId == DOWNVOTE_ID) {
                            incCommentVote(update, index, DOWNVOTE_FIELD, 1);
                            LOG.info("[comment downvote++]");
                        }
                        break;
                    case DOWNVOTE_ID:
                        incCommentVote(update, index, DOWNVOTE_FIELD, -1);
                        LOG.info("[comment downvote--]");
                        if (voteId == UPVOTE_ID) {
                            incCommentVote(update, index, UPVOTE_FIELD, 1);
                            LOG.info("[comment upvote++]");
                        }
                }
            }
            mongoTemplate.findAndModify(query, update, Post.class, POST_COLLECTION);
            return "SUCCESS " + index;
        } catch (Exception e) {
            return "FAILURE- ".concat(e.getMessage());
        }
    }

    private void incCommentVote(Update update, int index, String voteField, int inc) {
        update.inc(String.format(COMMENT_VOTE_FIELD, index, voteField), inc);
    }

}
