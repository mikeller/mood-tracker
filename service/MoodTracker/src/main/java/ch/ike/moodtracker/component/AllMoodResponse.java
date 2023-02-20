package ch.ike.moodtracker.component;

import java.util.Enumeration;

public class AllMoodResponse extends Response {
	
	Enumeration<Mood> moods;

	public Enumeration<Mood> getMoods() {
		return moods;
	}

	public void setMoods(Enumeration<Mood> moods) {
		this.moods = moods;
	}

}
