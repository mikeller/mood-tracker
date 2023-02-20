package ch.ike.moodtracker.repository;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import ch.ike.moodtracker.component.Mood;
import ch.ike.moodtracker.component.PersonalToken;
import ch.ike.moodtracker.utils.LogHelper;

@Repository
public class MoodDaoImpl {	
	//TODO: Change this to use a real DB :-)
	static ConcurrentHashMap<String, Mood> db = new ConcurrentHashMap<String, Mood>();

	public boolean hasSubmittedMoodForToday(PersonalToken token) {
		System.out.println("db: " + LogHelper.toJson(db));
	    
		return db.containsKey(token.getToken());
	}

	public void submitMoodForToday(PersonalToken token, Mood mood) {
		db.put(token.getToken(), mood);
		
		System.out.println("db: " + LogHelper.toJson(db));	    
	}
	
	public Enumeration<Mood> getAllMoodsForToday() {
		System.out.println("db: " + LogHelper.toJson(db));
	    
		return db.elements();		
	}

}
