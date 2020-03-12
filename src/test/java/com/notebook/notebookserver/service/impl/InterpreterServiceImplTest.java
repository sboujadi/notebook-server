package com.notebook.notebookserver.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.notebook.notebookserver.dto.InterpreterResponseDto;
import com.notebook.notebookserver.exception.NotSupportedLanguageException;
import com.notebook.notebookserver.exception.TimeOutException;
import com.notebook.notebookserver.model.InterpreterRequest;
import com.notebook.notebookserver.service.InterpreterService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class InterpreterServiceImplTest {
	
	@Autowired
	InterpreterService interpreterService;
	
	
	@Test
	public void whenValidCode_thenReturnResult() {
		InterpreterRequest interpreterRequest = createInterpreterRequest("js", "console.log('nominal case');", "mySessionId");	
		InterpreterResponseDto interpreterResponseDto = interpreterService.execute(interpreterRequest);
		
		assertEquals("nominal case\n", interpreterResponseDto.getResult());
		assertEquals("", interpreterResponseDto.getErrors());
	}
	
	@Test
	public void whenLangageNotSupported_thenThrowException() {
		InterpreterRequest interpreterRequest = createInterpreterRequest("NotSupportedLang", "console.log(1);", "mySessionId");	
		assertThrows(NotSupportedLanguageException.class, () -> interpreterService.execute(interpreterRequest));
	}
	
	@Test
	public void whenUndefinedVariable_thenThrowException() {
		InterpreterRequest firstInterpreterRequest = createInterpreterRequest("js", "var a = '1';", "firstSessionId");	
		InterpreterResponseDto firstInterpreterResponseDto = interpreterService.execute(firstInterpreterRequest);
		
		assertEquals("", firstInterpreterResponseDto.getResult());
		
		InterpreterRequest secondInterpreterRequest = createInterpreterRequest("js", "console.log(a);", "secondSessionId");	
		InterpreterResponseDto secondInterpreterResponseDto = interpreterService.execute(secondInterpreterRequest);
		
		assertEquals("ReferenceError: a is not defined", secondInterpreterResponseDto.getErrors());
	}
	
	@Test
	public void whenSameContextXWithVariable_thenReturnResult() {
		InterpreterRequest firstInterpreterRequest = createInterpreterRequest("js", "var a = '1';", "mySessionId");	
		InterpreterResponseDto firstInterpreterResponseDto = interpreterService.execute(firstInterpreterRequest);
		
		assertEquals("", firstInterpreterResponseDto.getResult());
		
		InterpreterRequest secondInterpreterRequest = createInterpreterRequest("js", "console.log(a);", "mySessionId");	
		InterpreterResponseDto secondInterpreterResponseDto = interpreterService.execute(secondInterpreterRequest);
		
		assertEquals("1\n", secondInterpreterResponseDto.getResult());
	}
	
	@Test
	public void whenInterpreterRequestTakeTooLong_thenThrowException() {
		InterpreterRequest interpreterRequest = createInterpreterRequest("js", "while(true) {console.log(1);}", "mySessionId");	
		assertThrows(TimeOutException.class, () -> interpreterService.execute(interpreterRequest));
	}
	
	private InterpreterRequest createInterpreterRequest(String lang, String code, String sessionId) {
		InterpreterRequest interpreterRequest = new InterpreterRequest();
		interpreterRequest.setLanguage(lang);
		interpreterRequest.setCode(code);
		interpreterRequest.setSessionId(sessionId);
		return interpreterRequest;
	}
	
}
