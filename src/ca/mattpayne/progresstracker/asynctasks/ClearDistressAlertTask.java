package ca.mattpayne.progresstracker.asynctasks;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import ca.mattpayne.progresstracker.R;
import android.app.Activity;

public class ClearDistressAlertTask extends AbstractHttpPostingWithResultAsyncTask {

	public ClearDistressAlertTask(Activity activity)
	{
		super(activity);
	}

	@Override
	protected List<NameValuePair> getPostParameters() {
		return new ArrayList<NameValuePair>();
	}

	@Override
	protected String getDialogTitle() {
		return getActivity().getString(R.string.clear_distress_alert_dialog_title);
	}

	@Override
	protected String getDialogMessage() {
		return getActivity().getString(R.string.clear_distress_alert_message_text);
	}
}
