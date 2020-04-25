package com.rstack.devnet.service.search;

import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.exception.RecordNotFoundException;
import com.rstack.devnet.model.Post;
import com.rstack.devnet.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public List<PostDTO> getSearchResults(String filterBy, String searchString, boolean caseSensitive, boolean diacriticSensitive) {
        List<PostDTO> questions = searchRepository.searchQuestions(filterBy, searchString, caseSensitive, diacriticSensitive);
        return questions;
    }
}
