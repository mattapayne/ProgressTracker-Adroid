package ca.mattpayne.progresstracker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;

public class DeleteSomeHandler extends AsyncTask<String, Void, Void> {

	private Activity _activity;
	private int _count;

	public DeleteSomeHandler(Activity activity, int count) {
		_activity = activity;
		_count = count;
	}

	@Override
	protected Void doInBackground(String... params) {
		if (params != null && params.length > 0 && _count > 0) {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(params[0]);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("count", String
					.valueOf(_count)));
			try {
				post.setEntity(new UrlEncodedFormEntity(parameters));
				client.execute(post);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		FragmentTransaction ft = _activity.getFragmentManager()
				.beginTransaction();
		Fragment prev = _activity.getFragmentManager().findFragmentByTag(
				"dialog");

		if (prev != null) {
			ft.remove(prev);
		}

		ft.addToBackStack(null);

		String message = _activity
				.getString(R.string.deleted_some_checkins_message_text);
		String title = _activity
				.getString(R.string.deleted_some_checkins_dialog_title);

		message = message.replace("{number}", String.valueOf(_count));

		ResultsDialogFragment frag = ResultsDialogFragment.newInstance(title,
				message);
		frag.show(ft, "dialog");
	}
}
