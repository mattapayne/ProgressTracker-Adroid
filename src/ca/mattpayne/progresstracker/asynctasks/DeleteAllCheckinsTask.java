package ca.mattpayne.progresstracker.asynctasks;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ca.mattpayne.progresstracker.R;
import ca.mattpayne.progresstracker.helpers.ConnectivityHelper;
import android.app.Activity;;

public class DeleteAllCheckinsTask extends AbtractHttpPostingWithResultAsyncTask {

	public DeleteAllCheckinsTask(Activity activity, ConnectivityHelper connectivityHelper) {
		super(activity, connectivityHelper);
	}

	@Override
	protected List<NameValuePair> getPostParameters() {
		final List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("all", String.valueOf(true)));
		return parameters;
	}

	@Override
	protected String getDialogTitle() {
		return getActivity().getString(R.string.deleted_all_checkins_dialog_title);
	}

	@Override
	protected String getDialogMessage() {
		return getActivity().getString(R.string.deleted_all_checkins_message_text);
	}
}
