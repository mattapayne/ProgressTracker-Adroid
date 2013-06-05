package ca.mattpayne.progresstracker;

import ca.mattpayne.progresstracker.asynctasks.SendCheckinTask;
import ca.mattpayne.progresstracker.helpers.ConnectivityHelperImpl;
import ca.mattpayne.progresstracker.helpers.LocationHelperImpl;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ProgressTrackerService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.i("ProgressTrackerService", "Got woken up by alarm. Sending location data.");
		Log.i("ProgressTrackerService", "Intent: " + intent.describeContents());
		
		new SendCheckinTask(getBaseContext(), 
				new LocationHelperImpl(getBaseContext()), 
						new ConnectivityHelperImpl(getBaseContext())).
				execute();
		
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
