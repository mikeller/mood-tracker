package ch.ike.moodtracker.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ike.moodtracker.component.Mood;
import ch.ike.moodtracker.component.MoodDataSet;
import ch.ike.moodtracker.component.MoodValue;
import ch.ike.moodtracker.component.MoodValue.Moods;
import ch.ike.moodtracker.component.PersonalToken;
import ch.ike.moodtracker.repository.MoodDaoImpl;
import ch.ike.moodtracker.utils.LogHelper;

@Service
public class MoodServiceImpl {

	@SuppressWarnings("serial")
	public class MoodAlreadySubmittedException extends Exception {
		public MoodAlreadySubmittedException() {
			super();
		}
	}
	
	@SuppressWarnings("serial")
	public class MoodNotYetSubmittedException extends Exception {
		public MoodNotYetSubmittedException() {
			super();
		}
	}
	
	@Autowired
	MoodDaoImpl personalMoodDao;

	public MoodDataSet getAllMoods(PersonalToken token) throws MoodNotYetSubmittedException {
		
		System.out.println("token: " + LogHelper.toJson(token));
		
		Date date = new Date();
	    
		if (personalMoodDao.hasSubmittedMoodForToday(token)) {			
			return new MoodDataSet(date, personalMoodDao.getAllMoodsForDay(date));
		} else {
			throw new MoodNotYetSubmittedException();
		}
	}

	public void setPersonalMood(PersonalToken token, Mood mood) throws MoodAlreadySubmittedException {
		System.out.println("token: " + LogHelper.toJson(token) + " mood: " + LogHelper.toJson(mood));
		
		if (!personalMoodDao.hasSubmittedMoodForToday(token)) {
			personalMoodDao.submitMoodForToday(token, mood);
		} else {
			throw new MoodAlreadySubmittedException();
		}
	}

	public List<MoodValue> getMoodValues() {
		List<MoodValue> values = new ArrayList<MoodValue>();
		for (Moods mood: Moods.values()) {
			  MoodValue moodValue = new MoodValue();
			  moodValue.setName(mood.name());
			  moodValue.setFeeling(mood.getFeeling());
			  values.add(moodValue);
		}
		  
		Collections.reverse(values);
		
		return values;
	}
}
