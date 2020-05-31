package com.rstack.devnet.service.post;

import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.exception.HttpBadRequestException;
import com.rstack.devnet.model.Comment;
import com.rstack.devnet.model.Vote;
import com.rstack.devnet.repository.PostRepository;
import com.rstack.devnet.shared.PostType;
import com.rstack.devnet.utility.CommentRequest;
import com.rstack.devnet.utility.PostRequest;
import com.rstack.devnet.utility.UniqueIdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO addPost(PostRequest postRequest, String username, String questionId) {
        Instant currentTimestamp = Instant.now();
        PostDTO post;
        //TODO: Add a custom postRequest validator
        if (questionId.isEmpty()) {
            if (StringUtils.isEmpty(postRequest.getQuestionHeader()) || StringUtils.isEmpty(postRequest.getQuestionBody())) {
                throw new HttpBadRequestException(HttpStatus.BAD_REQUEST, "Failed to add question");
            }
            questionId = UniqueIdHelper.getUniquePostId(PostType.QUESTION, username, currentTimestamp);
            post = postRepository.insertPost(postRequest, username, currentTimestamp, questionId, "");
        } else {
            if (StringUtils.isEmpty(postRequest.getAnswerBody())) {
                throw new HttpBadRequestException(HttpStatus.BAD_REQUEST, "Failed to add answer");
            }
            String answerId = UniqueIdHelper.getUniquePostId(PostType.ANSWER, username, currentTimestamp);
            post = postRepository.insertPost(postRequest, username, currentTimestamp, questionId, answerId);
        }
        return post;
    }

    @Override
    public PostDTO getPostById(String postId) {
        return postRepository.getPostById(postId);
    }

    @Override
    public PostDTO updatePostById(PostRequest postUpdateRequest, String username, String postId) {
        return postRepository.updatePostById(postUpdateRequest, postId);
    }

    @Override
    public void updateCommentById(CommentRequest commentRequest, String commentId, String postId, String username) {
        List<Comment> updatedCommentList = getPostById(postId).getComments();
        for (Comment comment : updatedCommentList) {
            if (comment.getId().equals(commentId)) {
                comment.setBody(commentRequest.getBody());
            }
        }
        postRepository.updateCommentById(updatedCommentList, postId);
    }

    @Override
    public PostDTO getQuestion(String questionId, String username) {
        PostDTO post;
        Vote vote = new Vote();
        post = postRepository.getQuestionById(questionId);

        // TODO: check below code necessary or not
        vote.setVoteStatus(post.getUsersInteracted().getOrDefault(username, 0));
        post.setVote(vote);
        post.getComments().forEach(comment -> {
            Vote commentVote = new Vote();
            commentVote.setVoteStatus(comment.getUsersInteracted().getOrDefault(username, 0));
            comment.setVote(commentVote);
        });

        return post;
    }

    @Override
    public List<PostDTO> getAllQuestions(String username) {
        return postRepository.getAllQuestions();
    }

    @Override
    public List<PostDTO> getAllQuestionsByUsername(String username) {
        return postRepository.getAllQuestionsByUsername(username);
    }

    @Override
    public List<PostDTO> getAllAnswersByUsername(String username) {
        return postRepository.getAllAnswersByUsername(username);
    }

    @Override
    public Comment addComment(CommentRequest commentRequest, String username, String postId) {
        Instant currentTimestamp = Instant.now();
        if (StringUtils.isEmpty(commentRequest.getBody())) {
            throw new HttpBadRequestException(HttpStatus.BAD_REQUEST, "Failed to add comment");
        }
        String commentId = UniqueIdHelper.getUniquePostId(PostType.COMMENT, username, currentTimestamp);
        return postRepository.insertComment(commentRequest, username, currentTimestamp, postId, commentId);
    }

    @Override
    public List<PostDTO> getQuestionWithAnswers(String questionId, String username) {
        return postRepository.getAllPostsByQuestionId(questionId);
    }

    @Override
    public void deletePostById(String postId, String username) {
        //Todo: check if username equals postedBy of post
        postRepository.deletePostById(postId);
    }

    @Override
    public void deleteCommentById(String commentId, String postId, String username) {
        List<Comment> oldCommentList = getPostById(postId).getComments();
        List<Comment> newCommentList = new ArrayList<>();
        for (Comment comment : oldCommentList) {
            if (!comment.getId().equals(commentId)) {
                newCommentList.add(comment);
            }
        }
        postRepository.updateCommentById(newCommentList, postId);
    }
}
