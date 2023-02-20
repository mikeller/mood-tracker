package ch.ike.moodtracker.component;

import org.springframework.stereotype.Component;

@Component
public class Response {
	
	int status;
	String text;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
