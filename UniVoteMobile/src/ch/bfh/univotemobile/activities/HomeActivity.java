package ch.bfh.univotemobile.activities;

import ch.bfh.univotemobile.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Entry point (Start-screen) of the application.
 *  
 * @author Raphael Hänni
 */
public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		getActionBar().setTitle(R.string.blank);
		
		final Button button = (Button) findViewById(R.id.button_registration);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
				startActivity(intent);
			}
		});
	} 
}