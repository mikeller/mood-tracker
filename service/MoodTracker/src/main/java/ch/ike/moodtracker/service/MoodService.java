package ch.ike.moodtracker.service;

import java.util.List;

import ch.ike.moodtracker.component.Mood;
import ch.ike.moodtracker.component.MoodDataSet;
import ch.ike.moodtracker.component.MoodValue;
import ch.ike.moodtracker.component.PersonalToken;
import ch.ike.moodtracker.service.MoodServiceImpl.MoodAlreadySubmittedException;
import ch.ike.moodtracker.service.MoodServiceImpl.MoodNotYetSubmittedException;

public interface MoodService {

	MoodDataSet getAllMoods(PersonalToken token) throws MoodNotYetSubmittedException;

	void setPersonalMood(PersonalToken token, Mood mood) throws MoodAlreadySubmittedException;

	List<MoodValue> getMoodValues();

}