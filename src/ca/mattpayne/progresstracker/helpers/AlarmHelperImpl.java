package ca.mattpayne.progresstracker.helpers;

import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import ca.mattpayne.progresstracker.StartProgressTrackerServiceReceiver;

public class AlarmHelperImpl implements AlarmHelper
{
	private final Context _context;
	
	private static final Logger Logger = LoggerFactory.getLogger(AlarmHelperImpl.class);

	public AlarmHelperImpl(Context context)
	{
		_context = context;
	}
	
	public void setAlarm(int intervalInMilliseconds)
	{
		Logger.info("Setting alarm for interval: " + String.valueOf(intervalInMilliseconds));
		
		final Intent intent = new Intent(_context, StartProgressTrackerServiceReceiver.class);
		final PendingIntent pendingIntent = PendingIntent.getBroadcast(_context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		
		final AlarmManager alarmManager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
		
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, intervalInMilliseconds);
		
		//cancel any pre-existing alarms
		alarmManager.cancel(pendingIntent);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), intervalInMilliseconds, pendingIntent);
	}
}
