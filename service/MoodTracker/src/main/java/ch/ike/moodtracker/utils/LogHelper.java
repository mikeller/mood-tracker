package ch.ike.moodtracker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogHelper {
	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
	    try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return "JSON serialisation exception!";
		}
	}

}
