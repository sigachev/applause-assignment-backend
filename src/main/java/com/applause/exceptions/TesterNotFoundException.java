package com.applause.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TesterNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1105045175631879877L;

    public TesterNotFoundException(Long id) {
        super("Tester with id = "+ id + " was not found");
    }

    public TesterNotFoundException(String message) {
        super(message);
    }

}
