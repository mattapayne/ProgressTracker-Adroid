package ca.mattpayne.progresstracker.helpers;

import java.util.Calendar;
import ca.mattpayne.progresstracker.StartProgressTrackerServiceReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmHelperImpl implements AlarmHelper
{
	private final Context _context;

	public AlarmHelperImpl(Context context)
	{
		_context = context;
	}
	
	public void setAlarm(int intervalInMilliseconds)
	{
		Log.i(this.getClass().getName(), "Setting alarm for interval: " + String.valueOf(intervalInMilliseconds));
		
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
