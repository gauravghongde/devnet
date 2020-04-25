package com.rstack.devnet.controller.v1;

import com.rstack.devnet.dto.model.PostDTO;
import com.rstack.devnet.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /////////// SEARCH QUESTIONS ///////////////
    //accepts -> string of search query
    //returns -> questions matching the string

    @GetMapping(value = "/search")
    public ResponseEntity<List<PostDTO>> searchQuestions(@RequestParam(value = "filterBy", defaultValue = "relevance") String filterBy,
                                                         @RequestParam("query") String searchQuery) {
        List<PostDTO> questions = searchService.getSearchResults(filterBy, searchQuery, false, false);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}
