package ch.ike.moodtracker.controller;

import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.ike.moodtracker.component.AllMoodResponse;
import ch.ike.moodtracker.component.Mood;
import ch.ike.moodtracker.component.PersonalToken;
import ch.ike.moodtracker.component.Response;
import ch.ike.moodtracker.service.MoodServiceImpl;
import ch.ike.moodtracker.service.MoodServiceImpl.MoodAlreadySubmittedException;
import ch.ike.moodtracker.service.MoodServiceImpl.MoodNotYetSubmittedException;



//@CrossOrigin(origins = "http://localhost, http://localhost:4200")
@RestController
public class MoodTrackerController {

	@Autowired
	MoodServiceImpl moodService;
	
	@RequestMapping(value = "/moods", method = RequestMethod.GET)
	public Response getAllMoods(PersonalToken token) {
		checkTokenValid(token);

		Enumeration<Mood> allMoods;
		try {
			allMoods =  moodService.getAllMoods(token);
		} catch (MoodNotYetSubmittedException e) {
			Response response = new Response();
			response.setStatus(403);
			response.setStatusText("Mood not yet submitted");
			return response;
		}
		
		AllMoodResponse response = new AllMoodResponse();
		response.setStatus(200);
		response.setStatusText("Ok");
		response.setMoods(allMoods);
		return response;
	}

	@RequestMapping(value = "/mood/{token}", method = RequestMethod.POST)
	public Response setPersonalMood(PersonalToken token, @RequestBody Mood mood) {
		checkTokenValid(token);
		if (mood != null && mood.getComment() != null && mood.getComment().length() > 350) {
			throw new IllegalArgumentException(String.format("Comment too long: %d", mood.getComment().length()));
		}

		try {
			moodService.setPersonalMood(token, mood);
		} catch (MoodAlreadySubmittedException e) {
			Response response = new Response();
			response.setStatus(409);
			response.setStatusText("Mood already submitted");
			return response;
		}
		
		Response response = new Response();
		response.setStatus(200);
		response.setStatusText("Ok");
		return response;
	}

	private void checkTokenValid(PersonalToken token) {
		if (token == null || token.getToken() == null || token.getToken().isEmpty()) {
			throw new IllegalArgumentException("Token cannot be empty");
		}
	}
}