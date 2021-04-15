package com.applause.model.search;

import com.applause.config.jsonViews.SearchView;
import com.applause.model.Device;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonView(SearchView.class)
public class DeviceSearchResult {
    private Device device;
    private Long numberOfBugs;

}
