package ca.mattpayne.progresstracker.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityHelperImpl implements ConnectivityHelper {

	private final Context _context;

	public ConnectivityHelperImpl(Context context) {
		_context = context;
	}

	@Override
	public boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
}
