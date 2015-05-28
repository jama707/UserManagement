package com.comcast.coding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends HttpMessageConversionException {

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NotFoundException() {
        this("Requested resource doesn't exist");

    }
}
