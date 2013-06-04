package ca.mattpayne.progresstracker.helpers;

import ca.mattpayne.progresstracker.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesHelper {

	public static long getCheckinInterval(Context ctx)
	{
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		
		final String interval = prefs.getString(ctx.getString(R.string.pref_interval_key), "15");
		final int minutes = Integer.parseInt(interval);
		return minutes * 60 * 1000;
	}
}
