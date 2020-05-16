package com.rstack.devnet.service.post;

import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.model.Comment;
import com.rstack.devnet.model.Vote;
import com.rstack.devnet.repository.PostRepository;
import com.rstack.devnet.shared.PostType;
import com.rstack.devnet.utility.CommentRequest;
import com.rstack.devnet.utility.PostRequest;
import com.rstack.devnet.utility.UniqueIdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO addQuestion(PostRequest postRequest, String username) {
        Instant currentTimestamp = Instant.now();
        String questionId = UniqueIdHelper.getUniquePostId(PostType.QUESTION, username, currentTimestamp);
        return postRepository.insertPost(postRequest, username, currentTimestamp, questionId, "");
    }

    @Override
    public PostDTO getQuestion(String questionId, String username) {
        PostDTO post;
        Vote vote = new Vote();
        post = postRepository.getQuestionById(questionId);
        vote.setVoteStatus(post.getUsersInteracted().getOrDefault(username, 0));
        post.setVote(vote);
        post.getComments().stream().forEach(comment -> {
            Vote commentVote = new Vote();
            commentVote.setVoteStatus(comment.getUsersInteracted().getOrDefault(username, 0));
            comment.setVote(commentVote);
        });
        return post;
    }

    @Override
    public PostDTO addAnswer(PostRequest postRequest, String username, String questionId) {
        Instant currentTimestamp = Instant.now();
        String answerId = UniqueIdHelper.getUniquePostId(PostType.ANSWER, username, currentTimestamp);
        return postRepository.insertPost(postRequest, username, currentTimestamp, questionId, answerId);
    }

    @Override
    public Comment addComment(CommentRequest commentRequest, String username, String postId) {
        Instant currentTimestamp = Instant.now();
        String commentId = UniqueIdHelper.getUniquePostId(PostType.COMMENT, username, currentTimestamp);
        return postRepository.insertComment(commentRequest, username, currentTimestamp, postId, commentId);
    }

    @Override
    public List<PostDTO> getQuestionWithAnswers(String questionId, String username) {
        List<PostDTO> postsByQuestionId = postRepository.getAllPostsByQuestionId(questionId);
        return postsByQuestionId;
    }
}
