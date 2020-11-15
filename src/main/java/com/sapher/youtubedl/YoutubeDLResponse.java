package com.sapher.youtubedl;

import java.util.Map;

/**
 * YoutubeDL response
 */
public class YoutubeDLResponse {

    private Map<String, String> options;
    private String command;
    private int exitCode;
    private String out;
    private String err;
    private String directory;
    private int elapsedTime;
    private Process process;

    public YoutubeDLResponse(String command, Map<String, String> options, String directory, int exitCode, int elapsedTime, String out, String err, Process process) {
        this.command = command;
        this.options = options;
        this.directory = directory;
        this.elapsedTime = elapsedTime;
        this.exitCode = exitCode;
        this.out = out;
        this.err = err;
        this.process = process;
    }

    public String getCommand() {
        return command;
    }

    public int getExitCode() {
        return exitCode;
    }

    public String getOut() {
        return out;
    }

    public String getErr() {
        return err;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public String getDirectory() {
        return directory;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
}
