package com.comcast.coding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidRequestException extends HttpMessageConversionException{

    public InvalidRequestException(String msg) {
        super(msg);
    }

    public InvalidRequestException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidRequestException() {
        this("Something is missing");
    }
}
