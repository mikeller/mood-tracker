package ch.ike.moodtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.ike.moodtracker.component.AllMoodResponse;
import ch.ike.moodtracker.component.Mood;
import ch.ike.moodtracker.component.MoodDataSet;
import ch.ike.moodtracker.component.MoodValuesResponse;
import ch.ike.moodtracker.component.PersonalToken;
import ch.ike.moodtracker.component.Response;
import ch.ike.moodtracker.service.MoodService;
import ch.ike.moodtracker.service.MoodServiceImpl.MoodAlreadySubmittedException;
import ch.ike.moodtracker.service.MoodServiceImpl.MoodNotYetSubmittedException;


@CrossOrigin(origins = { "http://localhost", "http://localhost:4200" })
@RestController
public class MoodTrackerController {

	@Autowired
	MoodService moodService;
	
	@RequestMapping(value = "/moods", method = RequestMethod.GET)
	public Response getAllMoods(PersonalToken token) {
		checkTokenValid(token);

		MoodDataSet allMoods;
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
		response.setMoodDataSet(allMoods);
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

	@RequestMapping(value = "/moodvalues", method = RequestMethod.GET)
	public MoodValuesResponse getMoodValues() {
		MoodValuesResponse response = new MoodValuesResponse();
		response.setStatus(200);
		response.setStatusText("Ok");
		response.setMoodValues(moodService.getMoodValues());
		return response;
	}

	private void checkTokenValid(PersonalToken token) {
		if (token == null || token.getToken() == null || token.getToken().isEmpty() || token.getToken().length() > 36) {
			throw new IllegalArgumentException("Invalid token");
		}
	}
}