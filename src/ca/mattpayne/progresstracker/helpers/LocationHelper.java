package ca.mattpayne.progresstracker.helpers;

import android.location.LocationListener;

public interface LocationHelper {
	boolean canGetLocation();
	double getLongitude();
	double getLatitude();
	void setLatitude(double lat);
	void setLongitude(double lng);
	boolean isLocationServicesGPSEnabled();
	boolean isLocationServicesNetworkEnabled();
	void registerListener(LocationListener listener);
	void unregisterListener(LocationListener listener);
	public double NOT_SET = -1;
}
