package ca.mattpayne.progresstracker.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class LocationHelperImpl implements LocationHelper {
	private LocationManager _locationManager;
	private boolean _isGPSEnabled;
	private boolean _isNetworkEnabled;
	private boolean _canGetLocation;
	private final Context _context;
	private Location _location;
	private double _latitude;
	private double _longitude;
	
	public LocationHelperImpl(Context context)
	{
		_context = context;
		initialize();
	}
	
	public double getLatitude()
	{
		return _latitude;
	}
	
	public double getLongitude()
	{
		return _longitude;
	}
	
	public boolean isLocationServicesNetworkEnabled()
	{
		return _isGPSEnabled;
	}
	
	public boolean isLocationServicesGPSEnabled()
	{
		return _isGPSEnabled;
	}
	
	public boolean canGetLocation()
	{
		return _canGetLocation;
	}
	
	private void initialize() {
		try {
			_locationManager = (LocationManager) _context
					.getSystemService(Context.LOCATION_SERVICE);

			// getting GPS status
			_isGPSEnabled = _locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			_isNetworkEnabled = _locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if (!_isGPSEnabled && !_isNetworkEnabled) {
				Log.i("LocationHelper", "Unable to get location information via either GPS or network.");
			} 
			else {
				this._canGetLocation = true;
				if (_isNetworkEnabled) {
					if (_locationManager != null) {
						_location = _locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (_location != null) {
							_latitude = _location.getLatitude();
							_longitude = _location.getLongitude();
						}
					}
				}
				else
				{
					Log.i("LocationHelper", "Network is NOT enabled.");
				}
				if (_isGPSEnabled) {
					if (_location == null) {
						if (_locationManager != null) {
							_location = _locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (_location != null) {
								_latitude = _location.getLatitude();
								_longitude = _location.getLongitude();
							}
						}
					}
				}
				else
				{
					Log.i("LocationHelper", "GPS is NOT enabled.");
				}
			}

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
