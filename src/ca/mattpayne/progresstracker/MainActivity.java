package ca.mattpayne.progresstracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ca.mattpayne.progresstracker.asynctasks.ClearDistressAlertTask;
import ca.mattpayne.progresstracker.asynctasks.DeleteAllCheckinsTask;
import ca.mattpayne.progresstracker.asynctasks.DeleteSomeCheckinsTask;
import ca.mattpayne.progresstracker.asynctasks.ShowCheckinsTask;
import ca.mattpayne.progresstracker.helpers.AlarmHelper;
import ca.mattpayne.progresstracker.helpers.AlarmHelperImpl;
import ca.mattpayne.progresstracker.helpers.ConnectivityHelper;
import ca.mattpayne.progresstracker.helpers.ConnectivityHelperImpl;
import ca.mattpayne.progresstracker.helpers.PreferencesHelper;
import ca.mattpayne.progresstracker.helpers.PreferencesHelperImpl;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnSharedPreferenceChangeListener  {

	private PreferencesHelper _preferencesHelper;
	private ConnectivityHelper _connectivityHelper;
	private AlarmHelper _alarmHelper;
	
	private static final Logger Logger = LoggerFactory.getLogger(MainActivity.class);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_preferencesHelper = new PreferencesHelperImpl(getBaseContext());
		_connectivityHelper = new ConnectivityHelperImpl(getBaseContext());
		_alarmHelper = new AlarmHelperImpl(getBaseContext());
		setContentView(R.layout.main);
		PreferenceManager.setDefaultValues(this, R.layout.preferences, false);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		setupDisplay();
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
	
	private void setIntervalDisplay() {
		final TextView txt = (TextView)findViewById(R.id.txtCurrentSettings);
		String interval = _preferencesHelper.getCheckinIntervalDescription();
		txt.setText("Current checkin interval is: " + interval + " minutes");
	}
	
	private void showInvalidNumberSelectionToast()
	{
		Toast.makeText(this, "You must enter a number.",Toast.LENGTH_SHORT).show();
	}

	private void setupDisplay() {
		setIntervalDisplay();
		
		final Button btnDeleteAll = (Button)findViewById(R.id.btnDeleteAll);
		btnDeleteAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new DeleteAllCheckinsTask(MainActivity.this, _connectivityHelper).
				execute(getString(R.string.delete_all_checkin_url));
			}});
		
		final Button btnDeleteSome = (Button)findViewById(R.id.btnDeleteSome);
		final TextView numberToDeleteView = (TextView)findViewById(R.id.txtNumberToDelete);
		
		btnDeleteSome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
					int numberToDelete = Integer.valueOf(numberToDeleteView.getText().toString().trim());
					new DeleteSomeCheckinsTask(MainActivity.this, _connectivityHelper, numberToDelete).
						execute(getString(R.string.delete_some_checkin_url));
				}
				catch(NumberFormatException nfe)
				{
					showInvalidNumberSelectionToast();
					return;
				}
			}
		});
		
		final Button btnShowCheckins = (Button)findViewById(R.id.btnShowCheckins);
		
		btnShowCheckins.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new ShowCheckinsTask(MainActivity.this, _connectivityHelper).
				execute(getString(R.string.get_checkins_url));
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
				new ClearDistressAlertTask(MainActivity.this, _connectivityHelper).
					execute(getString(R.string.delete_distress_alert_url));
			}
		});
		
		final TextView lnkGoToWeb = (TextView)findViewById(R.id.lnkGoToWebsite);
		lnkGoToWeb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri uriUrl = Uri.parse(getString(R.string.checkins_url));
				Intent urlIntent = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(urlIntent);
			}
		});
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		setIntervalDisplay();
		int interval = _preferencesHelper.getCheckinInterval();
		String intervalDescription = _preferencesHelper.getCheckinIntervalDescription();
		Logger.info("Got preference change notification. New interval is now: " + 
				intervalDescription + ". Setting alarm.");
		_alarmHelper.setAlarm(interval);
	}
}
