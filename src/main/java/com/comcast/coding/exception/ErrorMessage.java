package com.comcast.coding.exception;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ErrorMessage {


    private  Throwable errors;

    public ErrorMessage() {
    }

    public ErrorMessage(Throwable errors) {
        this.errors = errors;
    }



}