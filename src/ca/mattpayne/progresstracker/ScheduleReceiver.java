package ca.mattpayne.progresstracker;

import ca.mattpayne.progresstracker.helpers.AlarmHelper;
import ca.mattpayne.progresstracker.helpers.AlarmHelperImpl;
import ca.mattpayne.progresstracker.helpers.PreferencesHelper;
import ca.mattpayne.progresstracker.helpers.PreferencesHelperImpl;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScheduleReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		AlarmHelper alarmHelper = new AlarmHelperImpl(context);
		PreferencesHelper preferencesHelper = new PreferencesHelperImpl(context);
		alarmHelper.setAlarm(preferencesHelper.getCheckinInterval());
	}
}
