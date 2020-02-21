package com.rstack.devnet.repository;

import com.rstack.devnet.model.POST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class AnswerRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String POST_COLLECTION = "POST";

//    public List<ANSWER> findAnswersByQuestion(String questionId){
//
//    }

    public List<POST> getAllAnswersByQuestionId(String questionId) {
        Query query = new Query();
        query.addCriteria(where("forQuestion").is(questionId));
        return mongoTemplate.find(query, POST.class, POST_COLLECTION);
    }

//    public String insertUserVote(String username, int voteId, String answerId, String commentId) {
//        int index = -1;
//        Update update = new Update();
//        Query query = new Query(where("answerId").is(answerId));
//        ANSWER answerObj = mongoTemplate.findOne(query, ANSWER.class);
//        try {
//            if (commentId.isEmpty()) {
//                update.set("usersInteracted." + username, voteId);
//
//                int prevVoteId = answerObj.getUsersInteracted().getOrDefault(username, 0);
//                if (prevVoteId == NOVOTE) {
//                    if (voteId == UPVOTE) {
//                        update.inc("upVotes", 1);
//                    } else if (voteId == DOWNVOTE) {
//                        update.inc("downVotes", 1);
//                    }
//                } else if (prevVoteId == UPVOTE) {
//                    update.inc("upVotes", -1);
//                    if (voteId == DOWNVOTE) {
//                        update.inc("downVotes", 1);
//                    }
//                } else {
//                    update.inc("downVotes", -1);
//                    if (voteId == UPVOTE) {
//                        update.inc("upVotes", 1);
//                    }
//                }
//            } else {
//                List<COMMENT> cmtList = answerObj.getCommentObj();
//                index = IntStream.range(0, cmtList.size())
//                        .filter(i -> commentId.equals(cmtList.get(i).getCommentId()))
//                        .findFirst().getAsInt();
//                update.set("commentObj." + index + ".usersInteracted." + username, voteId);
//
//                int prevVoteId = cmtList.get(index).getUsersInteracted().getOrDefault(username, 0);
//                if (prevVoteId == NOVOTE) {
//                    if (voteId == UPVOTE) {
//                        update.inc("commentObj." + index + ".upVotes", 1);
//                    } else if (voteId == DOWNVOTE) {
//                        update.inc("commentObj." + index + ".downVotes", 1);
//                    }
//                } else if (prevVoteId == UPVOTE) {
//                    update.inc("commentObj." + index + ".upVotes", -1);
//                    if (voteId == DOWNVOTE) {
//                        update.inc("commentObj." + index + ".downVotes", 1);
//                    }
//                } else {
//                    update.inc("commentObj." + index + ".downVotes", -1);
//                    if (voteId == UPVOTE) {
//                        update.inc("commentObj." + index + ".upVotes", 1);
//                    }
//                }
//            }
//            mongoTemplate.findAndModify(query, update, ANSWER.class, ANSWER_COLLECTION);
//            return "SUCCESS " + index;
//        } catch (Exception e) {
//            return "FAILURE- ".concat(e.toString());
//        }
//    }
}
