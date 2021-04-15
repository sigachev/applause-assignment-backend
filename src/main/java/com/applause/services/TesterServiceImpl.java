package com.applause.services;

import com.applause.exceptions.DeviceNotFoundException;
import com.applause.exceptions.TesterNotFoundException;
import com.applause.model.Tester;
import com.applause.repositories.TesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TesterServiceImpl implements TesterService {

    @Autowired
    TesterRepository testerRepository;

    @Override
    public List<Tester> findAll() {
        List<Tester> resultList = new ArrayList<>();
        testerRepository.findAll().forEach(t -> resultList.add(t));
        return resultList;
    }

    @Override
    public Tester findById(Long id) throws DeviceNotFoundException {
        return testerRepository.findById(id).orElseThrow(() -> new TesterNotFoundException(id));
    }

    public List<Tester> findAllByCountryIn(List<String> countries) {
        List<String> countriesUpperCase = countries.stream().map(c-> c.toUpperCase()).collect(Collectors.toList());
        List<Tester> resultList = this.findAll().stream().filter(t -> countriesUpperCase.contains(t.getCountry())).collect(Collectors.toList());
        return resultList;
    }





}
