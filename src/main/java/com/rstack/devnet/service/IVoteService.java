package com.rstack.devnet.service;

public interface IVoteService {
    String voteAPost(String username, int voteId, String postId, String commentId);
}
