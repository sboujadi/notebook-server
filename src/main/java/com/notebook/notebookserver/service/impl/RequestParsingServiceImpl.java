package com.notebook.notebookserver.service.impl;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.notebook.notebookserver.dto.InterpreterRequestDto;
import com.notebook.notebookserver.exception.InvalidInterpreterRequestException;
import com.notebook.notebookserver.model.InterpreterRequest;
import com.notebook.notebookserver.service.RequestParsingService;


@Service
public class RequestParsingServiceImpl implements RequestParsingService {
	
	 private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";
	 private static final Pattern pattern = Pattern.compile(REQUEST_PATTERN);

	 	@Override
	    public InterpreterRequest parseInterpreterRequest(InterpreterRequestDto interpreterRequestDto) {
	        Matcher matcher = pattern.matcher(interpreterRequestDto.getCode());
	        if (matcher.matches()) {
	            String language = matcher.group(1);
	            String code = matcher.group(2);

	            InterpreterRequest interpreterRequest = new InterpreterRequest();
	            interpreterRequest.setLanguage(language);
	            interpreterRequest.setCode(code);
	            
	            if(interpreterRequestDto.getSessionId() == null) {
	            	interpreterRequest.setSessionId(UUID.randomUUID().toString());
	            } else {
	            	 interpreterRequest.setSessionId(interpreterRequestDto.getSessionId());
	            }

	            return interpreterRequest;
	        }

	        throw new InvalidInterpreterRequestException();
	    }
}
