package com.notebook.notebookserver.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notebook.notebookserver.dto.InterpreterRequestDto;
import com.notebook.notebookserver.dto.InterpreterResponseDto;
import com.notebook.notebookserver.model.InterpreterRequest;
import com.notebook.notebookserver.service.InterpreterService;
import com.notebook.notebookserver.service.RequestParsingService;

@RestController
public class InterpreterController {

	@Autowired
	RequestParsingService requestParsingService;
	
	@Autowired
	InterpreterService interpreterService;
	
    /**
     * Execute pieces of code in an interpreter 
     * 
     * Supported languages depends on the GraalVM installation (js by default)
     * 
     * sessionId field used to differentiate users
     * 
     * Subsequent request with same session id can be run in the same execution context
     * 
     * @param request the code to execute in format : %<interpreter-name><whitespace><code> and the session id
     * @return the output of the execution if success or error message if code execution encounters some kind of problem
     */
	@PostMapping(path = "/execute")
	public InterpreterResponseDto execute(@Valid @RequestBody InterpreterRequestDto interpreterRequestDto) {
		InterpreterRequest interpreterRequest = requestParsingService.parseInterpreterRequest(interpreterRequestDto);
		return interpreterService.execute(interpreterRequest);
	}
	
}
