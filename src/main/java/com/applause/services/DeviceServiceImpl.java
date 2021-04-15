package com.applause.services;

import com.applause.exceptions.DeviceNotFoundException;
import com.applause.model.Device;
import com.applause.model.Tester;
import com.applause.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public List<Device> findAll() {
        List<Device> resultList = new ArrayList<>();
        deviceRepository.findAll().forEach(d -> resultList.add(d));
        return resultList;
    }

    @Override
    public Device findById(Long id) throws DeviceNotFoundException {
        return deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));
    }


    public List<Device> findAllByIdsIn(List<Long> ids) {
        List<Device> resultList = findAll().stream().filter(d -> ids.contains(d.getId())).collect(Collectors.toList());
        return resultList;
    }

}
