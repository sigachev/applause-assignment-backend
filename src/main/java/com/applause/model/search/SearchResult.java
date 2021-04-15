package com.applause.model.search;

import com.applause.model.Device;
import com.applause.model.Tester;
import com.applause.config.jsonViews.SearchView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonView(SearchView.class)
public class SearchResult {

    Tester tester;
    Set<DeviceSearchResult> devicesSearchResults = new HashSet<>();
   /* Long totalBugs;*/

    public Long getTotalBugs() {
        return this.devicesSearchResults.stream().mapToLong(o -> o.getNumberOfBugs()).sum();
    }

}
