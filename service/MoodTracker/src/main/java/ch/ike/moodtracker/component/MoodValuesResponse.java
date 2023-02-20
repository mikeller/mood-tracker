package ch.ike.moodtracker.component;

import java.util.Date;
import java.util.List;

public class MoodValuesResponse extends Response {
	
	Date date;
	List<MoodValue> moodValues;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<MoodValue> getMoodValues() {
		return moodValues;
	}

	public void setMoodValues(List<MoodValue> moodValues) {
		this.moodValues = moodValues;
	}
}
