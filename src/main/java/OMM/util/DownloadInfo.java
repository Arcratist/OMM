package OMM.util;

public class DownloadInfo {

	private String url;
	private String input;
	private String output;
	private String format;
	private int percentage;
	private long eta;
	private DownloadStatus status;
	private String out;
	
	private boolean notify = true;

	public DownloadInfo(String url, String output) {
		this.setUrl(url);
		this.setOutput(output);
	}
	
	public DownloadInfo(String input, String output, String format) {
		this.setInput(input);
		this.setOutput(output);
		this.setFormat(format);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public long getEta() {
		return eta;
	}

	public void setEta(long eta) {
		this.eta = eta;
	}

	public DownloadStatus getStatus() {
		return status;
	}

	public void setStatus(DownloadStatus status) {
		this.status = status;
	}
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public boolean isNotify() {
		return notify;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
}
