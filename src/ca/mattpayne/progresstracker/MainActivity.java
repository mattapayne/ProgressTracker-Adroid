package ca.mattpayne.progresstracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnSharedPreferenceChangeListener  {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		PreferenceManager.setDefaultValues(this, R.layout.preferences, false);
		setupDisplay();
		AlarmHelper.setAlarm(getBaseContext(), PreferencesHelper.getCheckinInterval(getBaseContext()));
	}

	private void setupDisplay() {
		Object o = findViewById(R.id.txtCurrentSettings);
		TextView txt = (TextView) o;
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String interval = prefs.getString(
				getString(R.string.pref_interval_key), "");
		txt.setText("Current checkin interval is: " + interval + " minutes");
		
		Button btnDeleteAll = (Button)findViewById(R.id.btnDeleteAll);
		btnDeleteAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new DeleteAllHandler(MainActivity.this).execute(getString(R.string.delete_all_checkin_url));
			}});
		
		Button btnDeleteSome = (Button)findViewById(R.id.btnDeleteSome);
		final TextView numberToDeleteView = (TextView)findViewById(R.id.txtNumberToDelete);
		
		btnDeleteSome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int numberToDelete = Integer.valueOf(numberToDeleteView.getText().toString());
				new DeleteSomeHandler(MainActivity.this, numberToDelete).execute(getString(R.string.delete_some_checkin_url));
			}
		});
		
		Button btnShowCheckins = (Button)findViewById(R.id.btnShowCheckins);
		btnShowCheckins.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new ShowCheckinsHandler(MainActivity.this).execute(getString(R.string.get_checkins_url));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.preferences_menu_item) {
			startActivity(new Intent(getBaseContext(), SettingsActivity.class));
		}

		return true;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		setupDisplay();
		AlarmHelper.setAlarm(getBaseContext(), PreferencesHelper.getCheckinInterval(getBaseContext()));
	}
}
