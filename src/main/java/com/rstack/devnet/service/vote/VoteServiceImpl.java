package com.rstack.devnet.service.vote;

import com.rstack.devnet.repository.PostRepository;
import com.rstack.devnet.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public String addVote(String username, int voteId, String postId, String commentId) {
        return voteRepository.insertVote(username, voteId, postId, commentId);
    }
}
