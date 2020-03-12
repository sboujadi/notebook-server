package com.notebook.notebookserver.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class InterpreterRequestDto {
	
	/**
     * The code to execute in format: %<interpreter-name><whitespace><code>
     * example: %js console.log(1);
     */
	@NotEmpty
	private String code;
	
	/**
     * The session id used to preserve the state of the interpreter
     */
	private String sessionId;
   
}
