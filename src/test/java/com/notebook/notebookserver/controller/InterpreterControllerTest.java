package com.notebook.notebookserver.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notebook.notebookserver.model.InterpreterRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class InterpreterControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static final String PATH = "/execute";
	
	@Test
	public void whenValidInput_thenReturnsResult() throws Exception {
		InterpreterRequest request = new InterpreterRequest();
		request.setCode("%js console.log(1+1);");
		request.setSessionId("sessionId");
		
		mockMvc.perform(
				post(PATH)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is("2\n")))
				.andExpect(jsonPath("$.errors", is("")));
	}
	
	@Test
	public void whenRequestTimeout_thenReturns400() throws Exception {
		InterpreterRequest request = new InterpreterRequest();
		request.setCode("%js while(true) {console.log(1+1);}");
		request.setSessionId("sessionId");
		
		mockMvc.perform(
				post(PATH)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenUnsupportedlangage_thenReturns400() throws Exception {
		InterpreterRequest request = new InterpreterRequest();
		request.setCode("%unsupportedLang console.log(1+1);");
		request.setSessionId("sessionId");
		
		mockMvc.perform(
				post(PATH)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenInValidInputCode_thenReturnsError() throws Exception {
		InterpreterRequest request = new InterpreterRequest();
		request.setCode("%js consolelog(1)");
		request.setSessionId("sessionId");
		
		mockMvc.perform(
				post(PATH)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors", is("ReferenceError: consolelog is not defined")));
	}
	
	@Test
	public void when_InputIsNull_thenReturns400() throws Exception {
		mockMvc.perform(
				post(PATH)
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void when_AllMandatoryFieldAreMissing_thenReturns400() throws Exception {
		InterpreterRequest request = new InterpreterRequest();
		
		mockMvc.perform(
				post(PATH)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void when_CodeMandatoryFieldIsMissing_thenReturns400() throws Exception {
		InterpreterRequest request = new InterpreterRequest();
		request.setSessionId("sessionId");
		
		mockMvc.perform(
				post(PATH)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
}
