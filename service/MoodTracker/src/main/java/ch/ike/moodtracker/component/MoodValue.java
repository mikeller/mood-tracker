package ch.ike.moodtracker.component;

public class MoodValue {
	public enum Moods {
		STRESSED(0, "Stressed out – not a happy camper"),
		GRUMPY(1, "Grumpy"),
		MEH(2, "A bit “meh”"),
		NORMAL(3, "Just normal really"),
		HAPPY(5, "Happy"),
		
		UNKNOWN(9999, "");
				
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
		
		public static Moods getByValue(int value) {
		    for(Moods mood: values()) {
		    	if (mood.value == value) {
		    		return mood;
		    	}
		    }
		    
		    return UNKNOWN;
		}
	}

	String name;
    String feeling;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFeeling() {
		return feeling;
	}
	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}
}
