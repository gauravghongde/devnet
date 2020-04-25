package com.rstack.devnet.dto.mapper;

import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper {

    private static final Logger LOG = LoggerFactory.getLogger(PostMapper.class);

    public PostDTO toPostDTO(Post post) {
        LOG.info("Converting Post Entity to Post DTO");
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setQuestionHeader(post.getQuestionHeader());
        postDTO.setQuestionBody(post.getQuestionBody());
        postDTO.setAnswerBody(post.getAnswerBody());
        postDTO.setUsername(post.getUsername());
        postDTO.setPostedAt(post.getPostedAt());
        postDTO.setQuestionId(post.getQuestionId());
        postDTO.setVote(post.getVote());
        postDTO.setUsersInteracted(post.getUsersInteracted());
        postDTO.setComments(post.getComments());
        return postDTO;
    }

    public List<PostDTO> toPostDTOList(List<Post> posts) {
        LOG.info("Converting List of Post Entity to List of Post DTO");
        List<PostDTO> postDTOs = new ArrayList<>();
        for (int counter = 0; counter < posts.size(); counter++) {
            PostDTO postDTO = toPostDTO(posts.get(counter));
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }
}
