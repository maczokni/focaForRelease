package uk.ac.ucl.uctzrso.foca;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ReviewActivity extends Activity {

	private Button saveButton2;
	private Button backButton2;
	private RadioGroup isWorried;
	private RadioGroup isTransport;
	private RadioButton radioButton;
	private RadioButton radioButton2;
	private TextView userName;
	private String timeStamp;
	private GPSProvider gpsProvider;
	private String foc;
	private String type;
	private String transport;
	private Spinner spinnerCrimeType;
	private Spinner spinnerIsHateCrime;
	private String hCrime;
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
		//userName = (TextView) findViewById(R.id.questionnaire_username);
		//userName.setText(Util.getGoogleAccount(this));
		// radio = (RadioGroup)findViewById(R.id.isWorried);

		gpsProvider = new GPSProvider(this);
		isWorried = (RadioGroup) findViewById(R.id.isWorried);
		isTransport = (RadioGroup) findViewById(R.id.isTransport);

		saveButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// just for testing
				if (gpsProvider.getLatitude() == 0.0) {

					if (!gpsProvider.locationManager
							.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

						Builder dialog = new AlertDialog.Builder(
								ReviewActivity.this);
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

					Toast.makeText(ReviewActivity.this, Util.waitingForGPS,
							Toast.LENGTH_SHORT).show();
					return;
				}

				Log.d("THING", "listener started");

				radioButton = (RadioButton) isWorried.findViewById(isWorried
						.getCheckedRadioButtonId());
				foc = radioButton.getText().toString();

				radioButton2 = (RadioButton) isTransport
						.findViewById(isTransport.getCheckedRadioButtonId());
				transport = radioButton2.getText().toString();
				timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(new Date());

				if (foc.equals("Fairly worried") || foc.equals("Very worried")) {
					setContentView(R.layout.crimetype);
					nextpage();
				}

				else {

					Bundle extras = getIntent().getExtras();
					if (extras != null) {
					String googleAccount = extras.getString("acct");
					
					uploaderV2 ard = new uploaderV2(); // async task to upload
														// form data to db
					ard.AddName(googleAccount);
					ard.AddFOC(radioButton.getText().toString());
					ard.AddTime(timeStamp); // send time stamp of when data was
											// received
					ard.AddLat(gpsProvider.getLatitude()); // send lat/long
					ard.AddLng(gpsProvider.getLongitude());
					ard.AddTransp(transport);
					ard.AddQID("2");
					ard.AddWinner(Util.getGoogleAccount(self));

					// ard.AddGender(radioButton.getText().toString());
					// ard.AddWheelchair(radioDisability.getText().toString());
					// ard.AddPropulsion(radioDevice.getText().toString());

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
					data.putExtra("id",
							String.valueOf(isWorried.getCheckedRadioButtonId()));
					setResult(RESULT_OK, data);
					Toast.makeText(ReviewActivity.this, Util.requestSent,
							Toast.LENGTH_SHORT).show();

					finish();
				}}
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
		// Specify the layout to use when the list of choices appears
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerCrimeType.setAdapter(adapter3);
		
		spinnerIsHateCrime = (Spinner) findViewById(R.id.isHateCrime);
		ArrayAdapter<CharSequence> adapterHC = ArrayAdapter.createFromResource(
				this, R.array.hatecrime_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterHC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerIsHateCrime.setAdapter(adapterHC);
		// hours = hoursIP.getText().toString();timeStamp = new
		// SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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
				ard.AddQID("2");
				ard.AddFOC(foc);
				ard.AddTime(timeStamp);
				ard.AddType(type);
				ard.AddTransp(transport);
				ard.AddHCrime(hCrime);
				ard.AddWinner(Util.getGoogleAccount(self));

				// do i send this to same php?

				ard.writeout();
				Log.d("THING", "writeout");
				ard.submitDataPost();
				Log.d("THING", "submitDataPost");

				/*
				 * HttpPostTask task = new HttpPostTask(self, payload);
				 * task.execute(
				 * "http://80.99.97.156:8080/TestWsWithHibernate/phoneservice/regUser"
				 * , "POST"); Log.d("MESSAGE",radioButton.getText().toString());
				 */
				Intent data = new Intent();
				// data.putExtra("id",
				// String.valueOf(isWorried2.getCheckedRadioButtonId()));
				setResult(RESULT_OK, data);
				Toast.makeText(ReviewActivity.this, Util.requestSent,
						Toast.LENGTH_SHORT).show();

				finish();
			}}

		});

	}
	
	

}
