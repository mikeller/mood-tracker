package ch.ike.moodtracker.component;

import org.springframework.stereotype.Component;

@Component
public class Response {
	
	int status;
	String statusText;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
}
