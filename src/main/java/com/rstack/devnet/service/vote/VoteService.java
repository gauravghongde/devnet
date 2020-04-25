package com.rstack.devnet.service.vote;

public interface VoteService {
    String addVote(String username, int voteId, String postId, String commentId);
}
