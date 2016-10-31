//////////////////////////////////////////////////////////////////////////////////////////
//																						//
//					Welcome to the FOCA code, hope that you might find this 			//
//					helpful. Please keep in mind this was written by a social 			//
//					scientist and therefore is not amazing from a computer 				//
//					science point of view - but it works! Feel free to improve 			//
//					on this and share your changes! Any questions just contact 			//
//					Reka at maczokni@gmail.com or r.solymosi.11@ucl.ac.uk 				//
//																						//																						
//////////////////////////////////////////////////////////////////////////////////////////


package uk.ac.ucl.uctzrso.foca;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	private Button launchQuestionnaire;
	private Button launchReport;
	private Button launchBluetooth;
	private Button helpButton;
	private Button helpOKButton;
	public TextView gpsText;
	public TextView notCrime;
	private GPSProvider gpsProvider;
	private String[] accounts;
	private String googleAccount;
	private String googleAccountPlain;
	private String line;
	public boolean isUserRegistred;
	private Activity self;
	private final int QUESTIONNAIRE_REQ_CODE = 111;
	private final int RETRO_REQ_CODE = 222;
	private final int REPORT_REQ_CODE = 333;
	private String noOfPings;
	private PendingIntent pendingIntent;
	private int randomHour;
	private int randomMin;
	private AlarmManager alarmManager;
	private Context context;
	private static boolean RUN_ONCE = true;
	public static int NO_OPTIONS=0;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity);

		initialize();
		Log.d("MSG", "OMG we've initialized");
		self = this;
		accounts = Util.getGoogleAccountsAsStringArray(this);						// to uniquely
																					// identify each user
		if (Util.isGoogleAccountAvailable(this)) {									// i take the google account on the phone
																					// and hash it using sha1	
					googleAccountPlain = accounts[0];								// (so it stays anonymous)
																					// if no account then they are anon
					MessageDigest mdSha1 = null;
		            try
		            {
		              mdSha1 = MessageDigest.getInstance("SHA-1");
		            } catch (NoSuchAlgorithmException e1) {
		              Log.e("myapp", "Error initializing SHA1 message digest");
		            }
		            try {
		                mdSha1.update(googleAccountPlain.getBytes("ASCII"));
		            } catch (UnsupportedEncodingException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		            byte[] data = mdSha1.digest();
		            try {
		                googleAccount=convertToHex(data);
		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
					
		            googleAccount = googleAccount.replace(" ", "_");
		    		googleAccount = googleAccount.replaceAll("=", "_");
		    		googleAccount = googleAccount.replaceAll(System.getProperty("line.separator"), "");
					
					try {
						Log.d("CHKUSER","in choosing and trying, this is googleAccount: "+googleAccount);
						queryUserFromDb(googleAccount);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else{
					googleAccount = "anon";
				}

	}

	// need to then check if they user has already completed the pre-experiment survey
	// if yes, the home screen will open
	// if no then the survey will open
	// this check is done in dbcheck.java 
	private void queryUserFromDb(String googleAccount) throws InterruptedException,
			ExecutionException {

		Toast.makeText(this, "Requesting user information,\n please wait...",
				Toast.LENGTH_LONG).show();
		
		Log.d("CHKUSER","in queryUserFromDb this is googleAccount: "+googleAccount);

		dbcheck check = new dbcheck();
		check.googleAccount(googleAccount); // check google account in db
		check.execute().get();
		line = check.line;
		System.out.println("line is : " + line);
		
		if (RUN_ONCE) {
		    RUN_ONCE = false;

		// check if user registered before or not
		if (!line.equalsIgnoreCase("USER REGGED"))
			launchUserRegistration(googleAccount);// if they're already in db,
	}
			 else{
				//watevz
			}
	}

	public void launchUserRegistration(String googleAccount) {
		
		Intent intent = new Intent("uk.ac.ucl.uctzrso.foca.FormActivity");
		intent.putExtra("gAccount", googleAccount);
		 this.startActivityForResult(intent, 700);
		startActivity(intent);
	}

	private void initialize() {

		gpsText = (TextView) findViewById(R.id.gpsText);
		notCrime = (TextView) findViewById(R.id.textView2);
		gpsProvider = new GPSProvider(this);
		gpsProvider.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0, this);
		helpButton = (Button) findViewById(R.id.helpButton);
		helpButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("TESTSSS", "2nd onClick opened");

				Intent intentHelp= new Intent("uk.ac.ucl.uctzrso.foca.help");
				// this.startActivityForResult(intent, 700);
				startActivity(intentHelp);
					}

			
				
				
			
		});
			

		

		launchQuestionnaire = (Button) findViewById(R.id.questionnaireButton);
		launchQuestionnaire.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
 
        Intent intent = new Intent("uk.ac.ucl.uctzrso.foca.QuestionnaireActivity");
 
        intent.putExtra("acct", googleAccount);
 
        startActivity(intent);
    }
		});
		
		
		
		launchBluetooth = (Button) findViewById(R.id.bluetoothButton);
		launchBluetooth.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
 
        Intent intent = new Intent("uk.ac.ucl.uctzrso.foca.RetroActivity");
 
        intent.putExtra("acct", googleAccount);
 
        startActivity(intent);
    }
		});
		launchReport = (Button) findViewById(R.id.reportButton);
		launchReport.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
 
        Intent intent = new Intent("uk.ac.ucl.uctzrso.foca.ReviewActivity");
 
        intent.putExtra("acct", googleAccount);
 
        startActivity(intent);
    }
		});
		
		restorePrefs();
		Log.d("MSG", "restorePref run");
		
	// since the first two options require GPS, they should only be enabled and available
	// for the user to select if they have GPS available
	// check here for GPS and proceed accordingly

double lat = gpsProvider.getLatitude();
		
		if (lat == 0.0) {
			
			if (!gpsProvider.locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

				Builder dialog = new AlertDialog.Builder(
						this);
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
			launchQuestionnaire.setEnabled(false);
			launchQuestionnaire.setClickable(false);
			launchReport.setEnabled(false);
			launchReport.setClickable(false);
			
			gpsText.setText("You must have GPS signal to use the first 2 options");
			
			
		}
		
		else{
			
			launchQuestionnaire.setEnabled(true);
			launchQuestionnaire.setClickable(true);
			launchReport.setEnabled(true);
			launchReport.setClickable(true);
			
			gpsText.setText("");
			
		}
		
		

	}

	private void restorePrefs() {

		Log.d("MSG", "we are in restorePref");
		// TODO Auto-generated method stub
		noOfPings="2";
		SharedPreferences sp = getSharedPreferences("key", 0);
		noOfPings = sp.getString("nPings", "");
		Log.d("MESSAGE no pins is", noOfPings);
		startAlarm(noOfPings);

	}

	

	// every time the app is opened the user is assigned new ping times randomly
	// they get as many pings as they have selected previously
	// or the default of 2 pings a day

	private void startAlarm(String noOfPings) {

		Log.d("ALARMMM", "we are in startAlarm()");

		if (noOfPings.equalsIgnoreCase("1")) {

			Random randomGenerator = new Random();
			for (int idx = 0; idx <= 1; ++idx) {
				setupAlarm(randomGenerator, idx);
			}

		} else if (noOfPings.equalsIgnoreCase("2")
				|| noOfPings.equalsIgnoreCase("")) {

			Log.d("ALARMMM", "in elseif noOfPings equals 2");

			Random randomGenerator = new Random();
			for (int idx = 1; idx <= 2; ++idx) {
				setupAlarm(randomGenerator, idx);
			}

		}

		else if (noOfPings.equalsIgnoreCase("3")) {

			Random randomGenerator = new Random();
			for (int idx = 1; idx <= 3; ++idx) {
				setupAlarm(randomGenerator, idx);
			}

		} else if (noOfPings.equalsIgnoreCase("4")) {

			Random randomGenerator = new Random();
			for (int idx = 1; idx <= 4; ++idx) {
				setupAlarm(randomGenerator, idx);
			}

		} else if (noOfPings.equalsIgnoreCase("0")) {

			alarmManager.cancel(pendingIntent); // cancel if there is
													// already an alarm set

			Log.d("ALARMMM",
					"we've set alarmManager repeating never because user wants 0 pings");
		}

	}

	private void setupAlarm(Random randomGenerator, int idx) {
		randomHour = randomGenerator.nextInt(17) + 6;
		randomMin = randomGenerator.nextInt(59);

		Calendar firingCal = Calendar.getInstance();
		firingCal.set(Calendar.HOUR_OF_DAY, randomHour);
		// firingCal.set(Calendar.HOUR_OF_DAY, 12); //set to now + 2 min to
		// debug
		firingCal.set(Calendar.MINUTE, randomMin);
		// firingCal.set(Calendar.MINUTE, 53); //set to now + 2 min to debug
		firingCal.set(Calendar.SECOND, 0);

		Log.d("ALARMMM", "minute is: " + randomMin);
		Log.d("ALARMMM", "hour is : " + randomHour);

		long intendedTime = firingCal.getTimeInMillis();

		Intent myIntent = new Intent();
		myIntent.setAction("uk.ac.ucl.uctzrso.foca.AlarmReceiver");
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		pendingIntent = PendingIntent.getBroadcast(this, idx, myIntent, 0);

		if (intendedTime < System.currentTimeMillis()) {

			intendedTime = intendedTime + 86400000; // if alarm is in past, add
													// 1 day to make it not go
													// off immediately

		} else {
			// nada
		}

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, intendedTime,
				AlarmManager.INTERVAL_DAY, pendingIntent);

		Log.d("ALARMMM", "alarm manager is actually set to : " + intendedTime);

		Log.d("ALARMMM", "we've set alarmManager repeating x : " + idx);
		Log.d("ALARMMM", "time it's setting it to is : " + intendedTime);

	}

	public class BootReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					"android.intent.action.BOOT_COMPLETED")) {
				// Here do the actual Alarm Manager Scheduling depending on the
				// Settings in the preferences
				if (noOfPings.equalsIgnoreCase("1")) {

					Random randomGenerator = new Random();
					for (int idx = 0; idx <= 1; ++idx) {
						setupAlarm(randomGenerator, idx);
					}

				} else if (noOfPings.equalsIgnoreCase("2")) {

					Log.d("ALARMMM", "in elseif noOfPings equals 2");

					Random randomGenerator = new Random();
					for (int idx = 1; idx <= 2; ++idx) {
						setupAlarm(randomGenerator, idx);
					}
					
				} else if (noOfPings.equalsIgnoreCase("")) {

					Log.d("ALARMMM", "in elseif noOfPings equals 2");

					Random randomGenerator = new Random();
					for (int idx = 1; idx <= 2; ++idx) {
						setupAlarm(randomGenerator, idx);
					}

				}

				else if (noOfPings.equalsIgnoreCase("3")) {

					Random randomGenerator = new Random();
					for (int idx = 1; idx <= 3; ++idx) {
						setupAlarm(randomGenerator, idx);
					}

				} else if (noOfPings.equalsIgnoreCase("4")) {

					Random randomGenerator = new Random();
					for (int idx = 1; idx <= 4; ++idx) {
						setupAlarm(randomGenerator, idx);
					}

				} else if (noOfPings.equalsIgnoreCase("0")) {

					// No Alarm
					if (alarmManager != null) {
						alarmManager.cancel(pendingIntent); // cancel if there
															// is
															// already an alarm
															// set
					}
				}
			}
		}
	}
	private static String convertToHex(byte[] data) throws java.io.IOException
    {
           
            
           StringBuffer sb = new StringBuffer();
           String hex=null;
           
           hex=Base64.encodeToString(data, 0, data.length, NO_OPTIONS);
           
           sb.append(hex);
                       
           return sb.toString();
       }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_settings:
			
			Intent intent2 = new Intent("uk.ac.ucl.uctzrso.foca.settings");
			// this.startActivityForResult(intent, 700);
			startActivity(intent2);
			 return true;

			
		case R.id.menu_about:
			
			Intent intent3= new Intent("uk.ac.ucl.uctzrso.foca.about");
			// this.startActivityForResult(intent, 700);
			startActivity(intent3);
			

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		// return true;

	}
	
	
	@Override
	public void onLocationChanged(Location location) {
		
		launchQuestionnaire.setEnabled(true);
		launchQuestionnaire.setClickable(true);
		launchReport.setEnabled(true);
		launchReport.setClickable(true);
		
		gpsText.setText("");

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

	@Override
	public void onStart()
    {
		gpsProvider.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0, this);
		super.onStart();
       Log.d("lifecycle", "In the onStart() event");
      // gpsProvider.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		//		1000, 0, this);
    }
    @Override
	public void onResume()
    {
    	line = "USER REGGED"; 
    	gpsProvider.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
 				1000, 0, this);
    	super.onResume();
      
       Log.d("lifecycle", "In the onResume() event");
    }
    public void onPause()
    {
       
      gpsProvider.locationManager.removeUpdates(this);    // make sure to kill the GPS when
       //finish();										  // the app is not in foreground		
       Log.d("lifecycle", "In the onPause() event");	  // to save battery
       super.onPause();
    }
    public void onStop()
    {
    	gpsProvider.locationManager.removeUpdates(this);
        
        super.onStop();
      
       Log.d("lifecycle", "In the onStop() event");
    }
    public void onDestroy()
    {
    	 gpsProvider.locationManager.removeUpdates(this);
         
    	super.onDestroy();
       Log.d("lifecycle", "In the onDestroy() event");
    }

}

/*
 * REFERENCES
 * https://stackoverflow.com/questions/843675/how-do-i-find-out-if-the
 * -gps-of-an-android-device-is-enabled
 * https://stackoverflow.com/questions/19937898
 * /android-the-constructor-alertdialog-buildernew-view-onclicklistener-is
 * http://stackoverflow.com/questions/5288181/adding-touch-event-listeners
 * -to-mapview
 * http://stackoverflow.com/questions/3472603/convert-geopoint-to-location
 * http://www.codeproject.com/Articles/614946/Android-google-map-api-v2-setup
 * http
 * ://wptrafficanalyzer.in/blog/android-reverse-geocoding-at-touched-location
 * -in-google-map-android-api-v2/
 * http://stackoverflow.com/questions/17379807/remove
 * -previous-marker-and-add-new-marker-in-google-map-v2
 * 
 */
