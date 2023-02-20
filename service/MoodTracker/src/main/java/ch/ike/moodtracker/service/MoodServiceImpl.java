package ch.ike.moodtracker.service;

import java.util.ArrayList;
import java.util.Collection;
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
import ch.ike.moodtracker.model.MoodModel;
import ch.ike.moodtracker.repository.MoodDao;
import ch.ike.moodtracker.utils.Utils;

@Service
public class MoodServiceImpl implements MoodService {

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
	MoodDao moodDao;

	@Override
	public MoodDataSet getAllMoods(PersonalToken token) throws MoodNotYetSubmittedException {
		
		System.out.println("token: " + Utils.toJson(token));
			    
		Date date = new Date();

		if (moodDao.hasSubmittedMood(token.getToken(), Utils.startOfDay(date), Utils.endOfDay(date))) {
			Collection<MoodModel> moodModels = moodDao.getAllMoods(Utils.startOfDay(date), Utils.endOfDay(date));
			Collection<Mood> moods = new ArrayList<Mood>();
			for(MoodModel moodModel: moodModels) {
				Mood mood = new Mood();
				mood.setMood(Moods.getByValue(moodModel.getMoodValue()));
				mood.setComment(moodModel.getComment());
				
				moods.add(mood);
			}
				
			return new MoodDataSet(date, moods);
		} else {
			throw new MoodNotYetSubmittedException();
		}
	}

	@Override
	public void setPersonalMood(PersonalToken token, Mood mood) throws MoodAlreadySubmittedException {

		System.out.println("token: " + Utils.toJson(token) + " mood: " + Utils.toJson(mood));
		
		if (mood.getMood().equals(Moods.UNKNOWN)) {
			throw new IllegalArgumentException();
		}
		
		Date date = new Date();
		
		if (!moodDao.hasSubmittedMood(token.getToken(), Utils.startOfDay(date), Utils.endOfDay(date))) {
			MoodModel moodModel = new MoodModel();
			moodModel.setPersonalToken(token.getToken());			
			moodModel.setMoodValue(mood.getMood().getValue());
			moodModel.setComment(mood.getComment());
			moodModel.setCreateTime(date);
			
			moodDao.addMood(moodModel);			
		} else {
			throw new MoodAlreadySubmittedException();
		}
	}

	@Override
	public List<MoodValue> getMoodValues() {
		List<MoodValue> values = new ArrayList<MoodValue>();
		for (Moods mood: Moods.values()) {
			if (!mood.equals(Moods.UNKNOWN)) {
			  MoodValue moodValue = new MoodValue();
			  moodValue.setName(mood.name());
			  moodValue.setFeeling(mood.getFeeling());
			  values.add(moodValue);
			}
		}
		  
		Collections.reverse(values);
		
		return values;
	}
}
