package com.notebook.notebookserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown if an error occured
 * 
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InterpreterException extends RuntimeException {

    public InterpreterException(String message) {
        super(message);
    }
}
