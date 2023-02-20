package ch.ike.moodtracker.service;

import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ike.moodtracker.component.Mood;
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

	public Enumeration<Mood> getAllMoods(PersonalToken token) throws MoodNotYetSubmittedException {
		
		System.out.println("token: " + LogHelper.toJson(token));
	    
		if (personalMoodDao.hasSubmittedMoodForToday(token)) {
			return personalMoodDao.getAllMoodsForToday();
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
}
