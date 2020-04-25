package com.rstack.devnet.service.search;

import com.rstack.devnet.dto.model.PostDTO;

import java.util.List;

public interface SearchService {
    List<PostDTO> getSearchResults(String filterBy, String query, boolean caseSensitive, boolean diacriticSensitive);
}
