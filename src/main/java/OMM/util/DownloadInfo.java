package OMM.util;

public class DownloadInfo {

	private String url;
	private String output;
	private float percentage;
	private long eta;
	private DownloadStatus status;
	private String out;
	
	private boolean notify = true;

	public DownloadInfo(String url, String output) {
		this.setUrl(url);
		this.setOutput(output);
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

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
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
	
}
