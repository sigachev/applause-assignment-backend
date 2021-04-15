package com.applause.services;

import com.applause.exceptions.DeviceNotFoundException;
import com.applause.model.Device;
import com.applause.model.Tester;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DeviceService {
    List<Device> findAll();
    Device findById(Long id) throws DeviceNotFoundException;
    List<Device> findAllByIdsIn(List<Long> ids);
}
