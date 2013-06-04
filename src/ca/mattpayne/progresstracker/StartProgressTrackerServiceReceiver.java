package ca.mattpayne.progresstracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartProgressTrackerServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final Intent service = new Intent(context, ProgressTrackerService.class);
		context.startService(service);
	}
}
