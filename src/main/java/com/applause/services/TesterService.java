package com.applause.services;

import com.applause.exceptions.DeviceNotFoundException;
import com.applause.model.Tester;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TesterService {
    List<Tester> findAll();
    Tester findById(Long id) throws DeviceNotFoundException;
    List<Tester> findAllByCountryIn(List<String> countries);

}
