package com.applause.services;

import com.applause.model.Bug;
import com.applause.model.Device;
import com.applause.model.Tester;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BugService {

  List<Bug> findAll();

  List<Bug> findAllByTestersAndDevices(List<Tester> testers, List<Device> devices);

  Long getBugsCount(Tester t, Device d);

}
