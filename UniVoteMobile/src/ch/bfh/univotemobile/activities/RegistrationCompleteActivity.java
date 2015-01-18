package ch.bfh.univotemobile.activities;

import ch.bfh.univotemobile.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Activity gets started after successfull UniVote registration process.
 * @author Raphael Hänni
 */
public class RegistrationCompleteActivity extends Activity {
	Button buttonHome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_complete);
		
		buttonHome = (Button) findViewById(R.id.button_home);
		buttonHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegistrationCompleteActivity.this, HomeActivity.class);
            	startActivity(intent);
			}
		});
	}
}
