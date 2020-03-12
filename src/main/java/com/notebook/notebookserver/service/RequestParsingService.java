package com.notebook.notebookserver.service;

import com.notebook.notebookserver.dto.InterpreterRequestDto;
import com.notebook.notebookserver.model.InterpreterRequest;

public interface RequestParsingService {
	  
    /**
     * parse the code and map the InterpreterRequestDto to InterpreterRequest.
     *
     * @param  interpreterRequest contains code and sessionId
     *
     *  if session id is null a new session will be generated 
     *  
     * @throws InvalidInterpreterRequestException if code not respect the format '%language code'
     *
     * @return The InterpreterRequest
     */
	InterpreterRequest parseInterpreterRequest(InterpreterRequestDto request);
}
