package ch.ike.moodtracker.component;

import org.springframework.stereotype.Component;

@Component
public class PersonalToken {
	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
