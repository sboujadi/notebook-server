package com.notebook.notebookserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when request don't respect the format '%language code'
 * 
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid request format : \"%language code\"")
public class InvalidInterpreterRequestException extends RuntimeException{

}
