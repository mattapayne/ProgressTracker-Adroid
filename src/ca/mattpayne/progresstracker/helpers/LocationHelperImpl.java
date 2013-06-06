package ca.mattpayne.progresstracker.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class LocationHelperImpl implements LocationHelper {
	private LocationManager _locationManager;
	private boolean _isGPSEnabled;
	private boolean _isNetworkEnabled;
	private boolean _canGetLocation;
	private final Context _context;
	private Location _location;
	private double _latitude;
	private double _longitude;
	
	private static final Logger Logger = LoggerFactory.getLogger(LocationHelperImpl.class);

	
	public LocationHelperImpl(Context context)
	{
		_context = context;
		initialize();
		_latitude = NOT_SET;
		_longitude = NOT_SET;
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
				Logger.info("GPS is NOT enabled AND Network is NOT enabled.");
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
					Logger.info("Network is NOT enabled.");
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
					Logger.info("GPS is NOT enabled.");
				}
			}

		} 
		catch (Exception e) {
			Logger.error(e.getMessage());
		}
	}
}
