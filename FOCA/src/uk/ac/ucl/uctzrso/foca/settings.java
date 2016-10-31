//
// Settings is where user can modify the number of pings they're willing to
// receive in a day
//

package uk.ac.ucl.uctzrso.foca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class settings extends Activity {

	private Button saveSettings;
	private RadioGroup isPing;
	private RadioButton pingRadio;
	private String noOfPings;

	private Activity self = this;

	// private void settings() {
	// TODO Auto-generated method stub
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		saveSettings = (Button) findViewById(R.id.saveSettings);
		isPing = (RadioGroup) findViewById(R.id.isPing);

		SharedPreferences sp = getSharedPreferences("key", 0);
		noOfPings = sp.getString("nPings", "");
		Log.d("MESSAGE no pins is", noOfPings);
		
		if (noOfPings.equalsIgnoreCase("")){

		TextView textView = (TextView) findViewById(R.id.textView2);
		textView.setText("2");
		}
		else{
			TextView textView = (TextView) findViewById(R.id.textView2);
			textView.setText(noOfPings);
		}
		saveSettings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("TESTSSS", "pg3 onClick opened");

				pingRadio = (RadioButton) isPing.findViewById(isPing
						.getCheckedRadioButtonId());

				if (pingRadio == null) {

					// TOAST YOU ARE WRONGGGG!!!!
					Toast.makeText(self,
							"Please choose how often you'd like reminders",
							Toast.LENGTH_LONG).show();

				} else {
					SharedPreferences sp = getSharedPreferences("key", 0);

					SharedPreferences.Editor editor = sp.edit();
					editor.putString("nPings", pingRadio.getText().toString());
					// nPings = pingRadio.getText().toString();

					editor.commit();

					Intent intent = new Intent(settings.this, MainActivity.class);
					startActivity(intent);
					settings.this.finish();
					

				}
			}
		});

	}

}
