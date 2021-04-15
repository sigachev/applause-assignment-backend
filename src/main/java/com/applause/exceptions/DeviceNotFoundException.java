package com.applause.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DeviceNotFoundException extends Exception{

    private static final long serialVersionUID = 1105045175631879877L;

    public DeviceNotFoundException(Long id) {
        super("Device with id = "+ id + " was not found");
    }

    public DeviceNotFoundException(String message) {
        super(message);
    }

}
