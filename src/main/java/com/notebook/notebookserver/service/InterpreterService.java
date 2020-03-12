package com.notebook.notebookserver.service;

import com.notebook.notebookserver.dto.InterpreterResponseDto;
import com.notebook.notebookserver.model.InterpreterRequest;

public interface InterpreterService {

    /**
     * Execute a piece of code in the requested programming language and return the response.
     *
     * The service preserve the execution context based on the session id
     *
     * See application.properties for execution-timeout config
     *
     * @param  interpreterRequest contains  Execution language, Code to execute and sessionId
     *
     * @throws TimeoutException if code execution take too long.
     * @throws LanguageNotSupportedException if the interpreter doesn't support the language.
     *
     * @return The output of the code execution or error if execution encounter a problem.
     */
	public InterpreterResponseDto execute(InterpreterRequest interpreterRequest);
}
