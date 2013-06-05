package ca.mattpayne.progresstracker.helpers;

public interface LocationHelper {
	boolean canGetLocation();
	double getLongitude();
	double getLatitude();
	boolean isLocationServicesGPSEnabled();
	boolean isLocationServicesNetworkEnabled();
}
