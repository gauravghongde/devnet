package com.rstack.devnet.repository;

import com.rstack.devnet.dto.mapper.PostMapper;
import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.exception.RecordNotFoundException;
import com.rstack.devnet.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SearchRepository.class);
    private static final String POST_COLLECTION = "POST";
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PostMapper postMapper;

    public List<PostDTO> searchQuestions(String filterBy, String searchString, boolean caseSensitive, boolean diacriticSensitive) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage()
                .matchingPhrase(searchString)
                .caseSensitive(caseSensitive)
                .diacriticSensitive(diacriticSensitive);

        Query query = TextQuery.queryText(criteria)
                .sortByScore()
                .with(PageRequest.of(0, 5));

        List<Post> questions = mongoTemplate.find(query, Post.class);
        if (questions == null || questions.isEmpty()) {
            LOG.warn("No record found");
            throw new RecordNotFoundException("No record found !!");
        }
        LOG.info("Retrieved posts successfully");
        return postMapper.toPostDTOList(questions);
    }
}
