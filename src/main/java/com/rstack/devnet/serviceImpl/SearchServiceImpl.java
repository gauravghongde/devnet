package com.rstack.devnet.serviceImpl;

import com.rstack.devnet.model.QUESTION;
import com.rstack.devnet.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements ISearchService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<QUESTION> getSearchResults(String filterBy, String searchString, boolean caseSensitive, boolean diacriticSensitive) {
        try {
            TextCriteria criteria = TextCriteria.forDefaultLanguage()
                    .matchingPhrase(searchString)
                    .caseSensitive(caseSensitive)
                    .diacriticSensitive(diacriticSensitive);

            Query query = TextQuery.queryText(criteria)
                    .sortByScore()
                    .with(PageRequest.of(0, 5));

            List<QUESTION> questions = mongoTemplate.find(query, QUESTION.class);
            return questions;

        } catch (Exception e) {
            return null;
        }
    }
}
