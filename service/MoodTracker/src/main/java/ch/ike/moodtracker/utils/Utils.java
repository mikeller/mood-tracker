package ch.ike.moodtracker.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
	    try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return "JSON serialisation exception!";
		}
	}
	
	public static Date startOfDay(Date date) {
	    return DateUtils.truncate(date, Calendar.DATE);
	}
	
	public static Date endOfDay(Date date) {
	    return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
	}

}
