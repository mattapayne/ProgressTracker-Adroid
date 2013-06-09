package ca.mattpayne.progresstracker.helpers;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ApplicationHelper {

	private static final SimpleDateFormat _dateFormat =  
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
	
	public static SimpleDateFormat getDateFormat()
	{
		return _dateFormat;
	}
}
