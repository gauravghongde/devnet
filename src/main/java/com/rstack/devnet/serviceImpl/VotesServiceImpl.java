package com.rstack.devnet.serviceImpl;

import com.rstack.devnet.repository.PostRepository;
import com.rstack.devnet.service.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotesServiceImpl implements IVoteService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public String voteAPost(String username, int voteId, String postId, String commentId) {
        return postRepository.insertUserVote(username, voteId, postId, commentId);
    }
}
