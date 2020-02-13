package com.rstack.devnet.service;

import com.rstack.devnet.model.QUESTION;

import java.util.List;

public interface ISearchService {
    List<QUESTION> getSearchResults(String filterBy, String query, boolean caseSensitive, boolean diacriticSensitive);
}
