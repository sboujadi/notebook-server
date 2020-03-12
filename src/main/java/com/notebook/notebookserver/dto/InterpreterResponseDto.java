package com.notebook.notebookserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterpreterResponseDto {

	 /**
     * The output of the code execution.
     */
	private String result;
	
    /**
     * The error message in case of the code execution failure 
     */
	private String errors;
	
	/**
     * The session id used to preserve the state of the interpreter
     */
	private String sessionId;
	
}
