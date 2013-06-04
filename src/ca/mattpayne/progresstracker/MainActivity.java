package ca.mattpayne.progresstracker;

import ca.mattpayne.progresstracker.asynctasks.ClearDistressAlertTask;
import ca.mattpayne.progresstracker.asynctasks.DeleteAllCheckinsTask;
import ca.mattpayne.progresstracker.asynctasks.DeleteSomeCheckinsTask;
import ca.mattpayne.progresstracker.asynctasks.ShowCheckinsTask;
import ca.mattpayne.progresstracker.helpers.AlarmHelper;
import ca.mattpayne.progresstracker.helpers.PreferencesHelper;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final MenuInflater inflater = getMenuInflater();
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
	
	private void setupDisplay() {
		final TextView txt = (TextView)findViewById(R.id.txtCurrentSettings);

		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		final String interval = prefs.getString(
				getString(R.string.pref_interval_key), "");
		txt.setText("Current checkin interval is: " + interval + " minutes");
		
		final Button btnDeleteAll = (Button)findViewById(R.id.btnDeleteAll);
		btnDeleteAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new DeleteAllCheckinsTask(MainActivity.this).execute(getString(R.string.delete_all_checkin_url));
			}});
		
		final Button btnDeleteSome = (Button)findViewById(R.id.btnDeleteSome);
		final TextView numberToDeleteView = (TextView)findViewById(R.id.txtNumberToDelete);
		
		btnDeleteSome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int numberToDelete = Integer.valueOf(numberToDeleteView.getText().toString());
				new DeleteSomeCheckinsTask(MainActivity.this, numberToDelete).execute(getString(R.string.delete_some_checkin_url));
			}
		});
		
		final Button btnShowCheckins = (Button)findViewById(R.id.btnShowCheckins);
		btnShowCheckins.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new ShowCheckinsTask(MainActivity.this).execute(getString(R.string.get_checkins_url));
			}
		});
		
		final Button btnSendDistressAlert = (Button)findViewById(R.id.btnSendDistressAlert);
		btnSendDistressAlert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), SendDistressAlertActivity.class));
			}
		});
		
		final Button btnClearDistressAlert = (Button)findViewById(R.id.btnClearDistressAlerts);
		btnClearDistressAlert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new ClearDistressAlertTask(MainActivity.this).execute(getString(R.string.delete_distress_alert_url));
			}
		});
	}
}
