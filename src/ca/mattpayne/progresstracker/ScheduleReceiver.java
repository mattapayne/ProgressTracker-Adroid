package ca.mattpayne.progresstracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScheduleReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		AlarmHelper.setAlarm(context, PreferencesHelper.getCheckinInterval(context));
	}
}
