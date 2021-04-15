package com.applause.controllers;

import com.applause.config.jsonViews.SearchView;
import com.applause.exceptions.DeviceNotFoundException;
import com.applause.exceptions.TesterNotFoundException;
import com.applause.model.Bug;
import com.applause.model.Device;
import com.applause.model.Tester;
import com.applause.model.search.DeviceSearchResult;
import com.applause.model.search.SearchResult;
import com.applause.services.BugService;
import com.applause.services.DeviceService;
import com.applause.services.TesterService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SearchController {

    @Autowired
    public TesterService testerService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    BugService bugService;


    @GetMapping("/search")
    @JsonView(SearchView.class)
    public ResponseEntity<List<SearchResult>> search(@RequestParam(value = "countries", required = false) Optional<List<String>> countriesOptList,
                                                     @RequestParam(value = "devices", required = false) Optional<List<Long>> deviceIdsOptList)
            throws TesterNotFoundException, DeviceNotFoundException {
        List<SearchResult> result = new ArrayList<>();
        List<Tester> testers;
        List<Device> devices;
        List<Bug> bugs;

        //ALL Countries
        if (!countriesOptList.isPresent()) {
            testers = testerService.findAll();
            if (testers.isEmpty()) throw new TesterNotFoundException("No testers exist in the system.");
        } else {
            testers = testerService.findAllByCountryIn(countriesOptList.get());
            System.out.println("Countries list:" + testers.toString());
        }

        //ALL Devices
        if (!deviceIdsOptList.isPresent()) {
            devices = deviceService.findAll();
            if (devices.isEmpty()) throw new DeviceNotFoundException("No devices exist in the system.");
        } else {
            devices = deviceService.findAllByIdsIn(deviceIdsOptList.get());
        }

        System.out.print("Testers list: ");
        testers.stream().forEach(t -> System.out.print(t.getId() + ", "));
        System.out.println();
        System.out.print("Devices list: ");
        devices.stream().forEach(d -> System.out.print(d.getId() + ", "));
        System.out.println();

        //Filtering bugs for only those who have testers and device ids satisfying search criteria:
        bugs = bugService.findAllByTestersAndDevices(testers, devices);

        // remove all testers and devices that are not in bugs list
        testers.retainAll(bugs.stream().map(b -> b.getTester()).collect(Collectors.toList()));
        devices.retainAll(bugs.stream().map(b -> b.getDevice()).collect(Collectors.toList()));

        //effectively final variables (to use in lambda)
        List<Device> finalDevices = devices;
        List<Bug> finalBugs = bugs;

        System.out.print("Final Testers list: ");
        testers.stream().forEach(t -> System.out.print(t.getId() + ", "));
        System.out.println();
        System.out.print("Final Devices list: ");
        finalDevices.stream().forEach(d -> System.out.print(d.getId() + ", "));
        System.out.println();


        testers.stream().forEach(t ->
        {
            SearchResult searchresult = new SearchResult();
            searchresult.setTester(t);
            finalDevices.stream().forEach(d -> {
                if (bugService.getBugsCount(t, d) > 0) {
                    searchresult.getDevicesSearchResults().add(new DeviceSearchResult(d, bugService.getBugsCount(t, d)));
                }
            });
            result.add(searchresult);        });


        return new ResponseEntity(result.stream().filter(r -> r.getTotalBugs() != 0).sorted(Comparator.comparing(SearchResult::getTotalBugs).reversed()).collect(Collectors.toList()), HttpStatus.OK);
    }


}
