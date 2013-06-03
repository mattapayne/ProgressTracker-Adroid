package ca.mattpayne.progresstracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesHelper {

	public static long getCheckinInterval(Context ctx)
	{
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		
		String interval = prefs.getString(ctx.getString(R.string.pref_interval_key), "15");
		int minutes = Integer.parseInt(interval);
		return minutes * 60 * 1000;
	}
}
