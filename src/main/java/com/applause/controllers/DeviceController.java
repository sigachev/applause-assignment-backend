package com.applause.controllers;

import com.applause.config.jsonViews.DeviceView;
import com.applause.exceptions.DeviceNotFoundException;
import com.applause.repositories.DeviceRepository;
import com.applause.services.DeviceService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;

@RestController
class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/devices")
    @JsonView(DeviceView.class)

    public ResponseEntity getAllDevices() {
        return new ResponseEntity(deviceService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/devicelist/{ids}")
    @JsonView(DeviceView.class)

    public ResponseEntity getAllDevicesByIds(@PathVariable("ids") List<Long> ids) {
        return new ResponseEntity(deviceService.findAll().stream().filter(d -> ids.contains(d.getId())), HttpStatus.OK);
    }


    @GetMapping("/device/{id}")
    @JsonView(DeviceView.class)
    @Validated
    public ResponseEntity getById(@PathVariable("id") Long id) throws DeviceNotFoundException {
        return new ResponseEntity(deviceService.findById(id), HttpStatus.OK);
    }

}
