package ca.mattpayne.progresstracker;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class SettingsActivity extends Activity {

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction().
			replace(android.R.id.content, new SettingsFragment()).
			commit();
		
		PreferenceManager.setDefaultValues(this, R.layout.preferences, false);
	}
}
