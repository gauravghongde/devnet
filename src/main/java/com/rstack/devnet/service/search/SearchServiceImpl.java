package com.rstack.devnet.service.search;

import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public List<PostDTO> getSearchResults(String filterBy, String searchString, boolean caseSensitive, boolean diacriticSensitive) {
        return searchRepository.searchQuestions(filterBy, searchString, caseSensitive, diacriticSensitive);
    }
}
