package com.training.directory.exception;

import com.training.directory.constant.Status;
import com.training.directory.dao.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NullPointerException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleNullPointerExceptions(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404
        return new ResponseEntity<>(new ErrorResponse(Status.ERROR, e.getMessage()), status);
    }

    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e) {
//        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), stackTrace), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new ErrorResponse(Status.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessExceptions(Exception e) {
        BusinessException be = (BusinessException) e;

        HttpStatus status = be.getStatus();

        return new ResponseEntity<>(new ErrorResponse(Status.ERROR, be.getMessage()), status);
    }

    private String generateErrorMessage(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);

        return stringWriter.toString();
    }
}
