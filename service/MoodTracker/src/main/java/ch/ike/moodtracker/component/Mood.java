package ch.ike.moodtracker.component;

import org.springframework.stereotype.Component;

import ch.ike.moodtracker.component.MoodValue.Moods;

@Component
public class Mood {
	
	Moods mood;
	String comment;
	
	public Moods getMood() {
		return mood;
	}
	public void setMood(Moods mood) {
		this.mood = mood;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
