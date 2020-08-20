package com.simulation.server.chatlog.controller;

import com.simulation.server.chatlog.model.RecordUnavailableResponse;
import com.simulation.server.chatlog.service.RecordUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RecordUnavailableControllerAdvice {
    @ExceptionHandler(RecordUnavailableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RecordUnavailableResponse handleBadRequestException(RecordUnavailableException recordUnavailableException) {
        return new RecordUnavailableResponse(recordUnavailableException.getStatusMsg(), recordUnavailableException.getResponseMsg());
    }
}
