package com.notebook.notebookserver.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.notebook.notebookserver.dto.InterpreterResponseDto;
import com.notebook.notebookserver.exception.InterpreterException;
import com.notebook.notebookserver.exception.NotSupportedLanguageException;
import com.notebook.notebookserver.exception.TimeOutException;
import com.notebook.notebookserver.model.ExecutionContext;
import com.notebook.notebookserver.model.InterpreterRequest;
import com.notebook.notebookserver.service.InterpreterService;


@Service
public class InterpreterServiceImpl implements InterpreterService {

	private Map<String, ExecutionContext> sessionContexts = new ConcurrentHashMap<>();

	@Value("${execution.timeout}")
	private int executionTimeOut;

	@Override
	public InterpreterResponseDto execute(InterpreterRequest interpreterRequest) {

		// Check if language supported
		checkIfLanguageSupported(interpreterRequest.getLanguage());
			
		ExecutionContext executionContext = getContext(interpreterRequest);
		Context context = executionContext.getContext();
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		 
		 InterpreterResponseDto interpreterResponseDto = new InterpreterResponseDto();
		 interpreterResponseDto.setSessionId(interpreterRequest.getSessionId());
		// Callable, return a future, submit and run the task async
	        Future<InterpreterResponseDto> futureTask = executor.submit(() -> {
	        	 try {
		      			context.eval(interpreterRequest.getLanguage(), interpreterRequest.getCode());
		      			interpreterResponseDto.setResult(executionContext.getOutput()); 
		      			interpreterResponseDto.setErrors(executionContext.getErrors());
		      		} catch(PolyglotException e) {
		      			interpreterResponseDto.setErrors(e.getMessage());
		      		}
	        	 return interpreterResponseDto;
	        });
		
	        try {
				futureTask.get(executionTimeOut, TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException e) {
				   throw new InterpreterException(e.getMessage());
			} catch (TimeoutException e) {
				throw new TimeOutException();
			}
	        
		 return interpreterResponseDto;
	}

	private void checkIfLanguageSupported(String language) {
		try (Context context = Context.create()) {
			if(!context.getEngine().getLanguages().containsKey(language)) {
				throw new NotSupportedLanguageException();
			}
		}
	}

	private ExecutionContext getContext(InterpreterRequest interpreterRequest) {
		return sessionContexts.computeIfAbsent(interpreterRequest.getSessionId().concat(interpreterRequest.getLanguage()).toLowerCase(), 
				key -> buildContext(interpreterRequest.getLanguage()));
	}

	private ExecutionContext buildContext(String language) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ByteArrayOutputStream errorsStream = new ByteArrayOutputStream();
		Context context = Context.newBuilder(language).out(outputStream).err(errorsStream).build();
		return new ExecutionContext(context, outputStream, errorsStream);
	}
}
