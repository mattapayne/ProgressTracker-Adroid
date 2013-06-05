package ca.mattpayne.progresstracker.helpers;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ApplicationHelper {

	public static SimpleDateFormat getDateFormat()
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
	}
}
