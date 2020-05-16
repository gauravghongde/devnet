package com.rstack.devnet.service.post;

import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.model.Comment;
import com.rstack.devnet.utility.CommentRequest;
import com.rstack.devnet.utility.PostRequest;

import java.util.List;

public interface PostService {
    PostDTO addQuestion(PostRequest postRequest, String username);
    //

    PostDTO addAnswer(PostRequest postRequest, String username, String questionId);
    //

    Comment addComment(CommentRequest commentRequest, String username, String postId);
    //

    List<PostDTO> getQuestionWithAnswers(String questionId, String username);
    //returns question with all its answers

    PostDTO getQuestion(String questionId, String username);
    //Kept for later purpose

}
