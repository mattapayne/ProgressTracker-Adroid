package ca.mattpayne.progresstracker.helpers;

import ca.mattpayne.progresstracker.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesHelperImpl implements PreferencesHelper {

	private final Context _context;
	
	public PreferencesHelperImpl(Context context)
	{
		_context = context;
	}
	
	public int getCheckinInterval()
	{
		String selectedInterval = getCheckinIntervalDescription();
		final int minutes = Integer.parseInt(selectedInterval);
		return minutes * 60 * 1000;
	}
	
	public String getCheckinIntervalDescription()
	{
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(_context);
		
		return prefs.getString(_context.getString(R.string.pref_interval_key), "15");
	}
}
