package ca.mattpayne.progresstracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.mattpayne.progresstracker.asynctasks.SendCheckinTask;
import ca.mattpayne.progresstracker.helpers.ConnectivityHelperImpl;
import ca.mattpayne.progresstracker.helpers.LocationHelper;
import ca.mattpayne.progresstracker.helpers.LocationHelperImpl;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;

public class ProgressTrackerService extends Service implements LocationListener {
	
	private LocationHelper _locationHelper;
	private static final Logger Logger = LoggerFactory.getLogger(ProgressTrackerService.class);
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Logger.info("Got onStartCommand");
		
		_locationHelper = new LocationHelperImpl(getBaseContext());
		
		if(_locationHelper.canGetLocation())
		{
			_locationHelper.registerListener(this);
		}
		
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onLocationChanged(Location location) {
		
		_locationHelper.setLatitude(location.getLatitude());
		_locationHelper.setLongitude(location.getLongitude());
		
		new SendCheckinTask(getBaseContext(), 
				_locationHelper, new ConnectivityHelperImpl(getBaseContext())).
				execute();
		
		_locationHelper.unregisterListener(this);
		stopSelf();
	}

	@Override
	public void onProviderDisabled(String arg0) {

	}

	@Override
	public void onProviderEnabled(String arg0) {

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}
}
