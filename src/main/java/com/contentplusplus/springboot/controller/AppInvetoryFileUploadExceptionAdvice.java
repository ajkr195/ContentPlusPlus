package com.contentplusplus.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppInvetoryFileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

  @SuppressWarnings("rawtypes")
@ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException exc) {
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new AppInventoryResponseMessage("File too large!",""));
  }
}

