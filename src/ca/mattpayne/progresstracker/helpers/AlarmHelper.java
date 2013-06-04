package ca.mattpayne.progresstracker.helpers;

import java.util.Calendar;

import ca.mattpayne.progresstracker.StartProgressTrackerServiceReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHelper 
{
	public static void setAlarm(Context context, long interval)
	{
		final AlarmManager service = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		final Intent i = new Intent(context,
				StartProgressTrackerServiceReceiver.class);

		final PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,
				PendingIntent.FLAG_CANCEL_CURRENT);

		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 1);

		service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				PreferencesHelper.getCheckinInterval(context), pending);
	}
}
