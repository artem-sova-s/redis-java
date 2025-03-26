package network.handler;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class CommandContext {
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private String command;

    // executed command counter
    // used to check the count of executed commands in the pipeline
    private int executedCommandCounter = 0;

    // list of arguments for the command execution
    private List<String> commandArgs = null;

    public CommandContext(String command, InputStream in, OutputStream out, List<String> args) {
        this.command = command;
        this.inputStream = in;
        this.outputStream = out;
        this.commandArgs = args;
    }

    public void incrementExecutedCommandCounter() {
        executedCommandCounter++;
    }

    public int getExecutedCommandCounter() {
        return executedCommandCounter;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
    
    public OutputStream getOutputStream() {
        return outputStream;
    }
    
    public String getCommand() {
        return command;
    }
}
