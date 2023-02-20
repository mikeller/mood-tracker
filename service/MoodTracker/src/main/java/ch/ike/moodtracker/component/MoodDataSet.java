package ch.ike.moodtracker.component;

import java.util.Collection;
import java.util.Date;

public class MoodDataSet {
	
	Date date;
	public Date getDate() {
		return date;
	}

	public Collection<Mood> getMoods() {
		return moods;
	}

	Collection<Mood> moods;

	public MoodDataSet(Date date, Collection<Mood> moods) {
		this.date = date;
		this.moods = moods;
	}
}
