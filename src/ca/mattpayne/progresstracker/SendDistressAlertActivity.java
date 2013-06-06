package ca.mattpayne.progresstracker;

import ca.mattpayne.progresstracker.asynctasks.SendDistressAlertTask;
import ca.mattpayne.progresstracker.helpers.ConnectivityHelperImpl;
import ca.mattpayne.progresstracker.helpers.LocationHelperImpl;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SendDistressAlertActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distress_alert);
		setupView();
	}

	private void setupView() {
		final TextView txtMessage = (TextView)findViewById(R.id.txtMessage);
		final Button btnSend = (Button)findViewById(R.id.btnSend);
		btnSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String message = txtMessage.getText().toString();
				
				if(message == null || message.trim().length() == 0)
				{
					Toast.makeText(SendDistressAlertActivity.this, "You must type a message.", Toast.LENGTH_SHORT).show();
					return;
				}
				
				new SendDistressAlertTask(SendDistressAlertActivity.this, 
						new LocationHelperImpl(getBaseContext()), 
						new ConnectivityHelperImpl(getBaseContext()),
						txtMessage.getText().toString().trim()).
						execute(getString(R.string.create_distress_alert_url));
			}
		});
	}
}
