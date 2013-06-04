package ca.mattpayne.progresstracker.asynctasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import ca.mattpayne.progresstracker.R;
import ca.mattpayne.progresstracker.helpers.LocationHelper;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SendCheckinTask extends AsyncTask<Void, Void, Void> {
	
	private final LocationHelper _locationHelper;
	private final Context _context;
	
	public SendCheckinTask(Context context, LocationHelper locationHelper)
	{
		_locationHelper = locationHelper;
		_context = context;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		final String url = _context.getString(R.string.checkin_url);
		
		try {
			
			if (_locationHelper.canGetLocation()) {

				final double lat = _locationHelper.getLatitude();
				final double lng = _locationHelper.getLongitude();

				if (_locationHelper.isNetworkEnabled()) {
					post(lat, lng, url);
				} 
				else {
					Log.i("PostLocationTask","Network not enabled. Can't post.");
				}
			}
		} catch (Exception e) {
			Log.e("PostLocationTask", e.getMessage());
		}

		return null;
	}

	private void post(double lat, double lng, String url) {
		final HttpClient httpClient = new DefaultHttpClient();
		final HttpPost post = new HttpPost(url);
		final Date currentDate = new Date();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
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
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
