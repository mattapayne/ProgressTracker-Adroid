package ca.mattpayne.progresstracker;

import ca.mattpayne.progresstracker.asynctasks.SendCheckinTask;
import ca.mattpayne.progresstracker.helpers.LocationHelper;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ProgressTrackerService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		new SendCheckinTask(getApplicationContext(), 
				new LocationHelper(getApplicationContext())).
				execute();
		
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
