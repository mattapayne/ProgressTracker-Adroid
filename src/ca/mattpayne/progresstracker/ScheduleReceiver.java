package ca.mattpayne.progresstracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ca.mattpayne.progresstracker.helpers.AlarmHelper;
import ca.mattpayne.progresstracker.helpers.AlarmHelperImpl;
import ca.mattpayne.progresstracker.helpers.PreferencesHelper;
import ca.mattpayne.progresstracker.helpers.PreferencesHelperImpl;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScheduleReceiver extends BroadcastReceiver {
	
	private static final Logger Logger = LoggerFactory.getLogger(ScheduleReceiver.class);
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Logger.info("Got boot notification. Setting up alarm for Checkin.");
		
		final AlarmHelper alarmHelper = new AlarmHelperImpl(context);
		final PreferencesHelper preferencesHelper = new PreferencesHelperImpl(context);
		alarmHelper.setAlarm(preferencesHelper.getCheckinInterval());
	}
}
