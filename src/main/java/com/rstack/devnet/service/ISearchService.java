package com.rstack.devnet.service;

import com.rstack.devnet.model.POST;

import java.util.List;

public interface ISearchService {
    List<POST> getSearchResults(String filterBy, String query, boolean caseSensitive, boolean diacriticSensitive);
}
