package ca.mattpayne.progresstracker.asynctasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import ca.mattpayne.progresstracker.R;
import ca.mattpayne.progresstracker.ResultsDialogFragment;
import ca.mattpayne.progresstracker.models.Checkin;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;

public class ShowCheckinsTask extends AsyncTask<String, Void, List<Checkin>> {

	private final Activity _activity;
	
	public ShowCheckinsTask(Activity activity)
	{
		_activity = activity;
	}
	
	@Override
	protected List<Checkin> doInBackground(String... params) {
		List<Checkin> checkins = null;
		
		if (params != null && params.length > 0) {
			final HttpClient client = new DefaultHttpClient();
			final HttpGet get = new HttpGet(params[0]);

			try
			{
				final HttpResponse response = client.execute(get);
				checkins = processResponse(response);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return checkins;
	}
	
	@Override
	protected void onPostExecute(List<Checkin> result) {
		final FragmentTransaction ft = _activity.getFragmentManager()
				.beginTransaction();
		final Fragment prev = _activity.getFragmentManager().findFragmentByTag(
				"dialog");

		if (prev != null) {
			ft.remove(prev);
		}

		ft.addToBackStack(null);

		final StringBuilder sb = new StringBuilder();
		
		if(result != null && !result.isEmpty())
		{
			for(Checkin c : result)
			{
				sb.append(c.getDescription()).append("\n\n");
			}
		}
		else
		{
			sb.append("No checkins at this time.");
		}
		
		final String title = _activity
				.getString(R.string.show_checkins_dialog_title);

		final ResultsDialogFragment frag = ResultsDialogFragment.newInstance(title,
				sb.toString());
		
		frag.show(ft, "dialog");
	}

	private List<Checkin> processResponse(HttpResponse response) {
		List<Checkin> checkins = new ArrayList<Checkin>();
		
		try {
			
			final BufferedReader  in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			final StringBuilder sb = new StringBuilder();
			
			for (String line = null; (line = in.readLine()) != null;) {
			    sb.append(line).append("\n");
			}
			
			final JSONTokener tokener = new JSONTokener(sb.toString());
			final JSONArray json = new JSONArray(tokener);
			
			for(int i=0; i<json.length(); i++)
			{
				final JSONObject item = json.getJSONObject(i);
				final Checkin checkin = new Checkin();
				checkin.CheckinDate = item.getString("checkin_date");
				checkin.Latitude = item.getString("latitude");
				checkin.Longitude = item.getString("longitude");
				
				checkins.add(checkin);
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return checkins;
	}
}
