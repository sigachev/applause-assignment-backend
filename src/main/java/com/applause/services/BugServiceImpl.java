package com.applause.services;

import com.applause.model.Bug;
import com.applause.model.Device;
import com.applause.model.Tester;
import com.applause.repositories.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BugServiceImpl implements BugService {

    @Autowired
    BugRepository bugRepository;

    @Override
    public List<Bug> findAll() {
        List<Bug> resultList = new ArrayList<>();
        bugRepository.findAll().forEach(b -> resultList.add(b));
        return resultList;
    }

    @Override
    public List<Bug> findAllByTestersAndDevices(List<Tester> testers, List<Device> devices) {
        return this.findAll().stream().filter(b ->
        testers.contains(b.getTester()) && devices.contains(b.getDevice())).collect(Collectors.toList());
    }

    @Override
    public Long getBugsCount(Tester tester, Device device) {
        List<Bug> bugs = bugRepository.findAllByTester_IdAndDevice_Id(tester.getId(), device.getId()).orElse(null);
        return Long.valueOf(bugs.size());
    }
}
