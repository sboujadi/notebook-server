package com.notebook.notebookserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a piece of code take too long 
 * 
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Request Timeout")
public class TimeOutException extends RuntimeException {

}
