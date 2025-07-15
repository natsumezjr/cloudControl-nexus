package com.cloudcontrol.dto.command;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Bck2FtdCommandHttpResponse extends ResponseEntity<Object> implements CommandResponse{
    public Bck2FtdCommandHttpResponse(Object body, HttpStatus status) {
        super(body, status);
    }
}
