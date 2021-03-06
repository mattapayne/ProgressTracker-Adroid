package ca.mattpayne.progresstracker.asynctasks;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ca.mattpayne.progresstracker.R;
import ca.mattpayne.progresstracker.helpers.ConnectivityHelper;
import android.app.Activity;

public class DeleteSomeCheckinsTask extends AbtractHttpPostingWithResultAsyncTask {

	private final int _count;

	public DeleteSomeCheckinsTask(Activity activity, ConnectivityHelper connectivityHelper, int count) {
		super(activity, connectivityHelper);
		_count = count;
	}

	@Override
	protected List<NameValuePair> getPostParameters() {
		final List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("count", String
				.valueOf(_count)));
		return parameters;
	}

	@Override
	protected String getDialogTitle() {
		return getActivity().getString(R.string.deleted_some_checkins_dialog_title);
	}

	@Override
	protected String getDialogMessage() {
		String message = getActivity().getString(R.string.deleted_some_checkins_message_text);
		message = message.replace("{number}", String.valueOf(_count));
		return message;
	}

	@Override
	protected String getActionName() {
		return "Delete some checkins";
	}
}
