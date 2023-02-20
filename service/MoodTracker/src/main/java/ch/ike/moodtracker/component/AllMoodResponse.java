package ch.ike.moodtracker.component;

public class AllMoodResponse extends Response {
	
	MoodDataSet moodDataSet;

	public MoodDataSet getMoodDataSet() {
		return moodDataSet;
	}

	public void setMoodDataSet(MoodDataSet moodDataSet) {
		this.moodDataSet = moodDataSet;
	}

}
