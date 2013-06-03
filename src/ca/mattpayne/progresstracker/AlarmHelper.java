package ca.mattpayne.progresstracker;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHelper 
{
	public static void setAlarm(Context context, long interval)
	{
		AlarmManager service = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context,
				StartProgressTrackerServiceReceiver.class);

		PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,
				PendingIntent.FLAG_CANCEL_CURRENT);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 1);

		service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				PreferencesHelper.getCheckinInterval(context), pending);
	}
}
