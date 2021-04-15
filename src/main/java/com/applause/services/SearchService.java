package com.applause.services;

import com.applause.model.search.SearchResult;

import java.util.List;

public interface SearchService {

    public List<SearchResult> getSearchResults(List<String> countries, List<Long> deviceIds);

}
