package ca.mattpayne.progresstracker.asynctasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import ca.mattpayne.progresstracker.R;
import ca.mattpayne.progresstracker.helpers.ApplicationHelper;
import ca.mattpayne.progresstracker.helpers.ConnectivityHelper;
import ca.mattpayne.progresstracker.helpers.LocationHelper;

public class SendCheckinTask extends AsyncTask<Void, Void, Void> {
	
	private final ConnectivityHelper _connectivityHelper;
	private final LocationHelper _locationHelper;
	private final Context _context;
	
	private static final Logger Logger = LoggerFactory.getLogger(SendCheckinTask.class);
	
	public SendCheckinTask(Context context, LocationHelper locationHelper, ConnectivityHelper connectivityHelper)
	{
		_locationHelper = locationHelper;
		_connectivityHelper = connectivityHelper;
		_context = context;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		final String url = _context.getString(R.string.checkin_url);
		
		try {
			
			if(_connectivityHelper.isConnected())
			{
				if (_locationHelper.canGetLocation()) {
					
					final double lat = _locationHelper.getLatitude();
					final double lng = _locationHelper.getLongitude();
					
					if(lat != LocationHelper.NOT_SET && lng != LocationHelper.NOT_SET)
					{
						Logger.info("We have internet access and we can get location. Sending checkin. Latitude: " + 
								String.valueOf(lat) + ", Longitude: " + String.valueOf(lng));
						
						post(lat, lng, url);	
					}
					else
					{
						Logger.info("Unable to get location. Not posting checkin.");
					}
				}
				else
				{
					Logger.info("Can't get location. Not bothering to post.");
				}
			}
			else
			{
				Logger.info("No internet connectivity. Can't post.");
			}
		} 
		catch (Exception e) 
		{
			Logger.error(e.getMessage());
		}

		return null;
	}

	private void post(double lat, double lng, String url) {
		final HttpClient httpClient = new DefaultHttpClient();
		final HttpPost post = new HttpPost(url);
		final Date currentDate = new Date();
		final SimpleDateFormat dateFormat = ApplicationHelper.getDateFormat();
		final String date = dateFormat.format(currentDate);
		
		final List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("latitude", String
				.valueOf(lat)));
		parameters.add(new BasicNameValuePair("longitude", String
				.valueOf(lng)));
		parameters.add(new BasicNameValuePair("checkin_date", date));

		try {
			post.setEntity(new UrlEncodedFormEntity(parameters));
			httpClient.execute(post);
		} 
		catch (Exception e) {
			Log.e(this.getClass().getName(), e.getMessage());
		} 
	}
}
