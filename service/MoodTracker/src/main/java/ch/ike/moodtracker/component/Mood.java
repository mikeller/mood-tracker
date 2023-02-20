package ch.ike.moodtracker.component;

import org.springframework.stereotype.Component;

@Component
public class Mood {
	public enum Moods {
		STRESSED(0, "Stressed out – not a happy camper"),
		GRUMPY(1, "Grumpy"),
		MEH(2, "A bit “meh”"),
		NORMAL(3, "Just normal really"),
		HAPPY(5, "Happy");
				
	    private final int value;
	    private final String feeling;
	    
		public int getValue() {
			return value;
		}

		public String getFeeling() {
			return feeling;
		}

		Moods(int value, String feeling) {
	    	this.value = value;
	    	this.feeling = feeling;
	    }
	}
	
	Moods mood;
	String verbatim;
	
	public Moods getMood() {
		return mood;
	}
	public void setMood(Moods mood) {
		this.mood = mood;
	}
	public String getVerbatim() {
		return verbatim;
	}
	public void setVerbatim(String verbatim) {
		this.verbatim = verbatim;
	}
}
