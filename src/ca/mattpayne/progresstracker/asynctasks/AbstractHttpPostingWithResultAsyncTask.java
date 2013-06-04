package ca.mattpayne.progresstracker.asynctasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import ca.mattpayne.progresstracker.ResultsDialogFragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;

public abstract class AbstractHttpPostingWithResultAsyncTask extends AsyncTask<String, Void, Void> {

	private final Activity _activity;
	
	protected AbstractHttpPostingWithResultAsyncTask(Activity activity)
	{
		_activity = activity;
	}
	
	protected abstract List<NameValuePair> getPostParameters();
	protected abstract String getDialogTitle();
	protected abstract String getDialogMessage();
	
	protected boolean showsDialogAfterComplete()
	{
		return true;
	}
	
	@Override
	protected Void doInBackground(String... params) {
		if (params != null && params.length > 0) {
			final HttpClient client = new DefaultHttpClient();
			final HttpPost post = new HttpPost(params[0]);
			final List<NameValuePair> parameters = getPostParameters();
			
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
		
		if(showsDialogAfterComplete())
		{
			final FragmentTransaction ft = _activity.getFragmentManager()
					.beginTransaction();
			final Fragment prev = _activity.getFragmentManager().findFragmentByTag(
					"dialog");

			if (prev != null) {
				ft.remove(prev);
			}

			ft.addToBackStack(null);

			final String message = getDialogMessage();
			final String title = getDialogTitle();

			final ResultsDialogFragment frag = ResultsDialogFragment.newInstance(title,
					message);
			frag.show(ft, "dialog");	
		}
		else
		{
			getActivity().finish();
		}
	}
	
	protected Activity getActivity()
	{
		return _activity;
	}
}
