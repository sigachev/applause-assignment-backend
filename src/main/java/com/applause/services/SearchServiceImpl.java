package com.applause.services;

import com.applause.model.search.SearchResult;
import com.applause.repositories.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    BugRepository bugRepository;

    @Override
    public List<SearchResult> getSearchResults(List<String> countries, List<Long> deviceIds) {

        return null;

    }
}
