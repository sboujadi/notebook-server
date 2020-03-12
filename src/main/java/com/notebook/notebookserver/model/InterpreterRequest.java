package com.notebook.notebookserver.model;

import lombok.Data;

/**
 * This class represent an interpreter request to execute
 */
@Data
public class InterpreterRequest {

    /**
     * The language
     */
    String language;
    
    /**
     * The code to execute
     */
    String code;
    
	/**
     * The session id used to preserve the state of the interpreter
     */
    String sessionId;
	
}
