//
// This is the activity for both complete questionnaire and report something now options
// 

package uk.ac.ucl.uctzrso.foca;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionnaireActivity extends Activity implements LocationListener {

	private Button saveButton2;
	private Button backButton2;
	private RadioGroup isWorried;
	private RadioGroup isTransport;
	private RadioButton radioButton;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private TextView userName;
	private String timeStamp;
	private String hCrime;
	private GPSProvider gpsProvider;
	private String foc;
	private String type;
	private String transport;
	private Spinner spinnerCrimeType;
	private Spinner spinnerIsHateCrime;
	public TextView gpsText;
	private Activity self = this;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionnaire);
		nullpage();

	}

	private void nullpage() {

		setContentView(R.layout.questionnaire);
		Log.d("THING", "onCreateddd");

		saveButton2 = (Button) findViewById(R.id.saveButton2);
		gpsProvider = new GPSProvider(this);
		isWorried = (RadioGroup) findViewById(R.id.isWorried);
		isTransport = (RadioGroup) findViewById(R.id.isTransport);
		gpsText = (TextView) findViewById(R.id.textView1);

		gpsProvider.locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 1000, 0, this);

		double lat = gpsProvider.getLatitude();

		if (lat == 0.0) {

			if (!gpsProvider.locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

				Builder dialog = new AlertDialog.Builder(this);
				dialog.setMessage("Please go to 'Settings' to activate your GPS and try again!");
				dialog.setPositiveButton("Settings",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface paramDialogInterface,
									int paramInt) {
								// TODO Auto-generated method stub
								startActivityForResult(
										new Intent(
												android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
										100);
							}
						});
				dialog.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface paramDialogInterface,
									int paramInt) {
								// TODO Auto-generated method stub

							}
						});
				dialog.show();

			}
			saveButton2.setEnabled(false);
			saveButton2.setClickable(false);

			gpsText.setText("You must have GPS signal to complete the questionnaire. Please try again later (possibly outdoors)");

		}

		else {

			saveButton2.setEnabled(true);
			saveButton2.setClickable(true);

			gpsText.setText("");

		}

		saveButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// just for testing
				/*
				 * if (gpsProvider.getLatitude() == 0.0) {
				 * 
				 * if (!gpsProvider.locationManager
				 * .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				 * 
				 * Builder dialog = new AlertDialog.Builder(
				 * QuestionnaireActivity.this); dialog.setMessage(
				 * "Please go to 'Settings' to activate your GPS and try again!"
				 * ); dialog.setPositiveButton("Settings", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick( DialogInterface
				 * paramDialogInterface, int paramInt) { // TODO Auto-generated
				 * method stub startActivityForResult( new Intent(
				 * android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
				 * 100); } }); dialog.setNegativeButton("Cancel", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick( DialogInterface
				 * paramDialogInterface, int paramInt) { // TODO Auto-generated
				 * method stub
				 * 
				 * } }); dialog.show();
				 * 
				 * }
				 * 
				 * Toast.makeText(QuestionnaireActivity.this,
				 * Util.waitingForGPS, Toast.LENGTH_SHORT).show(); return; }
				 */

				Log.d("THING", "listener started");

				radioButton = (RadioButton) isWorried.findViewById(isWorried
						.getCheckedRadioButtonId());
				foc = radioButton.getText().toString();

				radioButton2 = (RadioButton) isTransport
						.findViewById(isTransport.getCheckedRadioButtonId());
				transport = radioButton2.getText().toString();
				timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(new Date());

						// check if they reported an instance of worry, so that we can ask
						// what it was that made them worried. 
						// but if they said they're not worried then this is the end. 

				if (foc.equals("Fairly worried") || foc.equals("Very worried")) {
					setContentView(R.layout.crimetype);
					nextpage();
				}

				else {

					Bundle extras = getIntent().getExtras();
					if (extras != null) {
						String googleAccount = extras.getString("acct");

						uploaderV2 ard = new uploaderV2(); // async task to
															// upload
															// form data to db
						ard.AddName(googleAccount);
						ard.AddFOC(foc);
						ard.AddTime(timeStamp); // send time stamp of when data
												// was
												// received
						ard.AddLat(gpsProvider.getLatitude()); // send lat/long
						ard.AddLng(gpsProvider.getLongitude());
						ard.AddTransp(transport);
						ard.AddQID("1");
						ard.AddWinner(Util.getGoogleAccount(self));

						ard.writeout();
						Log.d("THING", "writeout");
						ard.submitDataPost();
						Log.d("THING", "submitDataPost");

						/*
						 * HttpPostTask task = new HttpPostTask(self, payload);
						 * task.execute(
						 * "http://80.99.97.156:8080/TestWsWithHibernate/phoneservice/regUser"
						 * , "POST");
						 * Log.d("MESSAGE",radioButton.getText().toString());
						 */
						Intent data = new Intent();
						data.putExtra("id", String.valueOf(isWorried
								.getCheckedRadioButtonId()));
						setResult(RESULT_OK, data);
						Toast.makeText(QuestionnaireActivity.this,
								Util.requestSent, Toast.LENGTH_SHORT).show();

						finish();
					}
				}
			}

		});
	}

	public void nextpage() {

		setContentView(R.layout.crimetype);

		Log.d("TESTSSS", "nextpage opened");

		// radioButton =
		// (RadioButton)isWorried.findViewById(isWorried.getCheckedRadioButtonId());
		saveButton2 = (Button) findViewById(R.id.saveButton2);
		backButton2 = (Button) findViewById(R.id.backButton2);

		spinnerCrimeType = (Spinner) findViewById(R.id.crimeType);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				this, R.array.crimetype_array,
				android.R.layout.simple_spinner_item);
		
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerCrimeType.setAdapter(adapter3);

		spinnerIsHateCrime = (Spinner) findViewById(R.id.isHateCrime);
		ArrayAdapter<CharSequence> adapterHC = ArrayAdapter
				.createFromResource(this, R.array.hatecrime_array,
						android.R.layout.simple_spinner_item);
		
		adapterHC
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinnerIsHateCrime.setAdapter(adapterHC);
		backButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nullpage();

			}
		});

		saveButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("TESTSSS", "2nd onClick opened");

				type = spinnerCrimeType.getSelectedItem().toString();
				hCrime = spinnerIsHateCrime.getSelectedItem().toString();

				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					String googleAccount = extras.getString("acct");
					// timeStamp = new
					// SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

					uploaderV2 ard = new uploaderV2();
					ard.AddName(googleAccount);
					ard.AddLat(gpsProvider.getLatitude()); // send lat/long
					ard.AddLng(gpsProvider.getLongitude());
					ard.AddQID("1");
					ard.AddFOC(foc);
					ard.AddTime(timeStamp);
					ard.AddType(type);
					ard.AddTransp(transport);
					ard.AddHCrime(hCrime);
					ard.AddWinner(Util.getGoogleAccount(self));

					ard.writeout();
					Log.d("THING", "writeout");
					ard.submitDataPost();
					Log.d("THING", "submitDataPost");

					/*
					 * HttpPostTask task = new HttpPostTask(self, payload);
					 * task.execute(
					 * "http://80.99.97.156:8080/TestWsWithHibernate/phoneservice/regUser"
					 * , "POST");
					 * Log.d("MESSAGE",radioButton.getText().toString());
					 */
					Intent data = new Intent();
					// data.putExtra("id",
					// String.valueOf(isWorried2.getCheckedRadioButtonId()));
					setResult(RESULT_OK, data);
					Toast.makeText(QuestionnaireActivity.this,
							Util.requestSent, Toast.LENGTH_SHORT).show();

					finish();
				}
			}

		});

	}

	@Override
	public void onLocationChanged(Location location) {

		saveButton2.setEnabled(true);
		saveButton2.setClickable(true);

		gpsText.setText("");

		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
