package ca.mattpayne.progresstracker.asynctasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ca.mattpayne.progresstracker.helpers.ApplicationHelper;
import ca.mattpayne.progresstracker.helpers.ConnectivityHelper;
import ca.mattpayne.progresstracker.helpers.LocationHelperImpl;
import android.app.Activity;

public class SendDistressAlertTask extends AbtractHttpPostingWithResultAsyncTask {

	private final String _message;
	private final LocationHelperImpl _locationHelper;
	
	public SendDistressAlertTask(Activity activity, LocationHelperImpl locationHelper, ConnectivityHelper connectivityHelper, String message)
	{
		super(activity, connectivityHelper);
		_message = message;
		_locationHelper = locationHelper;
	}
	
	@Override
	protected boolean showsDialogAfterComplete()
	{
		return false;
	}

	@Override
	protected List<NameValuePair> getPostParameters() {
		final double latitude = _locationHelper.getLatitude();
		final double longitude = _locationHelper.getLongitude();
		
		final Date currentDate = new Date();
		final SimpleDateFormat dateFormat = ApplicationHelper.getDateFormat();
		final String date = dateFormat.format(currentDate);
		
		final List<NameValuePair> args = new ArrayList<NameValuePair>();
		args.add(new BasicNameValuePair("message", _message));
		args.add(new BasicNameValuePair("latitude", String
				.valueOf(latitude)));
		args.add(new BasicNameValuePair("longitude", String
				.valueOf(longitude)));
		args.add(new BasicNameValuePair("create_date", date));
		return args;
	}

	@Override
	protected String getDialogTitle() {
		return null;
	}

	@Override
	protected String getDialogMessage() {
		return null;
	}
}
