package ch.ike.moodtracker.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import ch.ike.moodtracker.model.MoodModel;

public interface MoodDao {

	public void addMood(MoodModel mood);
	public void updateMood(MoodModel pmood);
	public List<MoodModel> listMoods();
	public MoodModel getMoodById(int id);
	public void removeMood(int id);
	
	public boolean hasSubmittedMood(String token, Date startTime, Date endTime);
	public Collection<MoodModel> getAllMoods(Date startTime, Date endTime);
}