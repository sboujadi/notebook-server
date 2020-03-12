package com.notebook.notebookserver.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.notebook.notebookserver.dto.InterpreterRequestDto;
import com.notebook.notebookserver.exception.InvalidInterpreterRequestException;
import com.notebook.notebookserver.model.InterpreterRequest;
import com.notebook.notebookserver.service.RequestParsingService;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RequestParsingServiceImplTest {


	@Autowired
	RequestParsingService requestParsingService;
	
	   @Test
	    public void parseValidInterpreterRequest() {
	        InterpreterRequestDto interpreterRequestDto = new InterpreterRequestDto();
	        interpreterRequestDto.setCode("%js console.log(1);");
	        interpreterRequestDto.setSessionId("sessionId");
	        
	        InterpreterRequest executionRequest = requestParsingService.parseInterpreterRequest(interpreterRequestDto);
	        
	        assertEquals("js", executionRequest.getLanguage());
	        assertEquals("console.log(1);", executionRequest.getCode());
	        assertEquals("sessionId", executionRequest.getSessionId());
	    }

	    @Test
	    public void parseInvalidInterpreterRequest() {
	    	InterpreterRequestDto interpreterRequestDto = new InterpreterRequestDto();
	        
	    	interpreterRequestDto.setCode("js console.log(1);");
	       
	    	assertThrows(InvalidInterpreterRequestException.class, () -> requestParsingService.parseInterpreterRequest(interpreterRequestDto));
	    }
}
