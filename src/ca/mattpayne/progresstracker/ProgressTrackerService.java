package ca.mattpayne.progresstracker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class ProgressTrackerService extends Service {

	private LocationManager locationManager;
	private boolean isGPSEnabled;
	private boolean isNetworkEnabled;
	private boolean canGetLocation;
	private Context context;
	private Location location;
	private double latitude;
	private double longitude;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		context = getApplicationContext();
		new PostLocationTask().execute();
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	private Location getLocation() {
		try {
			locationManager = (LocationManager) context
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	private class PostLocationTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			String url = getString(R.string.checkin_url);
			Log.i("PostLocationTask", "Running Task");
			try {
				ProgressTrackerService.this.getLocation();

				if (ProgressTrackerService.this.canGetLocation) {

					double lat = ProgressTrackerService.this.latitude;
					double lng = ProgressTrackerService.this.longitude;

					if (ProgressTrackerService.this.isNetworkEnabled) {
						post(lat, lng, url);
					} else {
						Log.i("PostLocationTask",
								"Network not enabled. Can't post.");
					}
				}
			} catch (Exception e) {
				Log.e("PostLocationTask", e.getMessage());
			}

			return null;
		}

		private void post(double lat, double lng, String url) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			final Date currentDate = new Date();
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = dateFormat.format(currentDate);
			
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("latitude", String
					.valueOf(lat)));
			parameters.add(new BasicNameValuePair("longitude", String
					.valueOf(lng)));
			parameters.add(new BasicNameValuePair("checkin_date", date));

			try {
				post.setEntity(new UrlEncodedFormEntity(parameters));
				httpClient.execute(post);
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
