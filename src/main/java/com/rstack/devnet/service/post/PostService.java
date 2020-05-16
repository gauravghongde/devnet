package com.rstack.devnet.service.post;

import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.model.Comment;
import com.rstack.devnet.utility.CommentRequest;
import com.rstack.devnet.utility.PostRequest;

import java.util.List;

public interface PostService {

    Comment addComment(CommentRequest commentRequest, String username, String postId);
    //

    List<PostDTO> getQuestionWithAnswers(String questionId, String username);
    //returns question with all its answers

    PostDTO getQuestion(String questionId, String username);
    //Kept for later purpose

    PostDTO getPostById(String postId);

    List<PostDTO> getAllQuestions(String username);

    List<PostDTO> getAllQuestionsByUsername(String username);

    List<PostDTO> getAllAnswersByUsername(String username);

    PostDTO updatePostById(PostRequest postRequest, String username, String postId);

    PostDTO addPost(PostRequest postRequest, String username, String questionId);

    void deletePostById(String postId, String username);

    void deleteCommentById(String commentId, String postId, String username);

    void updateCommentById(CommentRequest commentRequest, String commentId, String postId, String username);
}
