package com.applause.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BugNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1105045175631879877L;

    public BugNotFoundException(Long id) {
        super("Bug with id = "+ id + " was not found");
    }


}
