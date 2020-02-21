package com.rstack.devnet.service;

import com.rstack.devnet.model.POST;
import com.rstack.devnet.utility.CommentRequest;
import com.rstack.devnet.utility.CommentResponse;
import com.rstack.devnet.utility.PostRequest;
import com.rstack.devnet.utility.PostResponse;

import java.util.List;

public interface IPostService {
    PostResponse postAQuestion(PostRequest postRequest, String username);

    POST getAQuestion(String questionId);

    List<POST> getAllAnswersOfAQuestion(String questionId);

    PostResponse postAnAnswer(PostRequest postRequest, String username, String questionId);

    CommentResponse postAComment(CommentRequest commentRequest, String username, String postId);
}
