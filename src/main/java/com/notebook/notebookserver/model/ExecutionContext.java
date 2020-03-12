package com.notebook.notebookserver.model;

import org.graalvm.polyglot.Context;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayOutputStream;

/**
 * This class represent an execution context associated with a sessionId.
 */
@Data
@AllArgsConstructor
public class ExecutionContext {

    /**
     * Thr GraalVM execution context
     */
    private Context context;
 
    /**
     * The output stream of the execution.
     * This where output of the interpreter is stored
     */
    private ByteArrayOutputStream outputStream;
    
    /**
     * The error stream of the execution.
     * This where error of the interpreter is stored
     */
    private ByteArrayOutputStream errorsStream;
    

    public String getOutput() {
        String output = this.outputStream.toString();
        this.outputStream.reset();
        return output;
    }

    public String getErrors() {
        String error = this.errorsStream.toString();
        this.errorsStream.reset();
        return error;
    }

}
