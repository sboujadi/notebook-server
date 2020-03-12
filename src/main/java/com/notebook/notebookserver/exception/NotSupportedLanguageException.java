package com.notebook.notebookserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception thrown when language not supported by graalVM 
 * 
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Language Not Supported")
public class NotSupportedLanguageException extends RuntimeException {

}
