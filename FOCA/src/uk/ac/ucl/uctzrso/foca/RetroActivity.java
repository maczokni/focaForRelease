//
// This is the activity for the 'report a previous incident' option
// For the google map to work you will have to add your own maps api key to the manifest
//

package uk.ac.ucl.uctzrso.foca;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RetroActivity extends FragmentActivity {

	private String latitude;
	private String longitude;
	private Activity self = this;
	private RadioButton radioButton;
	private String timeStamp;
	private RadioGroup isWorried2;
	private String hours;
	private double lat;
	private double lng;
	private Button saveButton2;
	private EditText hoursIP;
	private String transport;
	private RadioButton radioButton2;
	private RadioGroup isTransport;
	private String foc;
	private RadioGroup crimeType;
	private RadioButton radioButton3;
	private String type;
	private GoogleMap googleMap;
	private MarkerOptions markerOptions;
	private LatLng latLng2;
	private TextView latTextView;
	private TextView lngTextView;
	private Button saveButton;
	private Marker marker;
	private Spinner spinnerCrimeType;
	private Button backButton;
	private Button backButton2;
	private Button backButton1;
	private Spinner spinnerIsHateCrime;
	private String hCrime;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.retrospec);
		// setRetainInstance(true);
		onRetainNonConfigurationInstance();

		latTextView = (TextView) findViewById(R.id.textViewLatitude);
		lngTextView = (TextView) findViewById(R.id.textViewLongitude);
		saveButton = (Button) findViewById(R.id.button1);
		
		googleMap = ((SupportMapFragment) (getSupportFragmentManager()
				.findFragmentById(R.id.map))).getMap();

		LatLng latLng = new LatLng(51.52228, -0.13256); // this is set to London, but you can change
														// to your city of interest
														// or the user's current location

		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
		googleMap.setMyLocationEnabled(true);

		/*
		 * class mapOverlay extends
		 * com.google.android.maps.ItemizedOverlay<OverlayItem> { private
		 * ArrayList<OverlayItem> markers = new ArrayList<OverlayItem>();
		 * 
		 * public mapOverlay(Drawable marker) {
		 * super(boundCenterBottom(marker)); }
		 * 
		 * Button saveButton = (Button) findViewById(R.id.button1);
		 * 
		 * public boolean onTouchEvent(MotionEvent event, MapView mapview) {
		 * 
		 * if(event.getAction() == 1) {
		 * 
		 * GeoPoint p = mapview.getProjection().fromPixels((int) event.getX(),
		 * (int) event.getY());
		 * 
		 * lat = p.getLatitudeE6() / 1E6; lng = p.getLongitudeE6() / 1E6;
		 * 
		 * latitude = Double.toString(lat); longitude = Double.toString(lng);
		 * 
		 * TextView latTextView = (TextView)
		 * findViewById(R.id.textViewLatitude); latTextView.setText(latitude);
		 * 
		 * TextView lngTextView = (TextView)
		 * findViewById(R.id.textViewLongitude); lngTextView.setText(longitude);
		 * 
		 * // add marker to clicked position OverlayItem overlayitem = new
		 * OverlayItem(p, null, null); markers.clear();
		 * markers.add(overlayitem); populate();
		 */

		googleMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng arg0) {

				if (marker != null) {
					marker.remove();
				}
				marker = googleMap.addMarker(new MarkerOptions()
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.map_icon))
						.position(new LatLng(arg0.latitude, arg0.longitude))
						.draggable(true).visible(true));

				lat = arg0.latitude;
				lng = arg0.longitude;

				latitude = Double.toString(lat);
				longitude = Double.toString(lng);

				latTextView.setText(latitude);
				lngTextView.setText(longitude);

			}
		});

		
		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Log.d("RETROACTIVITY",
						"got into first button before calling nextpage");
				
				if (lat == 0 && lng == 0) {
					
					Toast.makeText(self, "Please tap the map to choose location",
							Toast.LENGTH_LONG).show();
					
				}
				else {

				setContentView(R.layout.retrospec2);
				nextpage();
				}
				Log.d("RETROACTIVITY", "got into first button after calling nextpage");

			}

			public void nextpage() {

				setContentView(R.layout.retrospec2);

				Log.d("RETROACTIVITY", "nextpage opened");

				backButton = (Button) findViewById(R.id.backButton);
				saveButton2 = (Button) findViewById(R.id.saveButton2);
				hoursIP = (EditText) findViewById(R.id.edithours);
				isWorried2 = (RadioGroup) findViewById(R.id.isWorried2);
				isTransport = (RadioGroup) findViewById(R.id.isTransport);

				//
				// hours = hoursIP.getText().toString();timeStamp = new
				// SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
				backButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent (RetroActivity.this, RetroActivity.class);
						startActivity(intent);
					}
				});
				saveButton2.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						Log.d("RETROACTIVITY", "2nd onClick opened");

						radioButton = (RadioButton) isWorried2
								.findViewById(isWorried2
										.getCheckedRadioButtonId());
						foc = radioButton.getText().toString();
						hours = hoursIP.getText().toString();
						radioButton2 = (RadioButton) isTransport
								.findViewById(isTransport
										.getCheckedRadioButtonId());
						transport = radioButton2.getText().toString();
						timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
								.format(new Date());
						

						// check if they reported an instance of worry, so that we can ask
						// what it was that made them worried. 
						// but if they said they're not worried then this is the end. 
								
						if (foc.equals("Fairly worried")
								|| foc.equals("Very worried")) {
							setContentView(R.layout.crimetype);
							nextpage2();
						}

						else {
							
							
							Bundle extras = getIntent().getExtras();
							if (extras != null) {
							String googleAccount = extras.getString("acct");
							
							uploaderV2 ard = new uploaderV2();
							ard.AddName(googleAccount);
							ard.AddLat(lat);
							ard.AddLng(lng);
							// setContentView(R.layout.retrospec2);
							// nextpage();
							ard.AddFOC(radioButton.getText().toString());
							ard.AddTime(timeStamp + "-" + hours);
							ard.AddTransp(radioButton2.getText().toString());
							ard.AddQID("3");
							ard.AddWinner(Util.getGoogleAccount(self));

							ard.writeout();
							Log.d("THING", "writeout");
							ard.submitDataPost();
							Log.d("THING", "submitDataPost");

							/*
							 * HttpPostTask task = new HttpPostTask(self,
							 * payload); task.execute(
							 * "http://80.99.97.156:8080/TestWsWithHibernate/phoneservice/regUser"
							 * , "POST");
							 * Log.d("MESSAGE",radioButton.getText().toString
							 * ());
							 */
							Intent data = new Intent();
							data.putExtra("id", String.valueOf(isWorried2
									.getCheckedRadioButtonId()));
							setResult(RESULT_OK, data);
							Toast.makeText(self, Util.requestSent, 100).show();

							finish();
						}}
					}

					public void nextpage2() {

						setContentView(R.layout.crimetype);

						Log.d("RETROACTIVITY", "nextpage opened");

						// radioButton =
						// (RadioButton)isWorried.findViewById(isWorried.getCheckedRadioButtonId());
						backButton2 = (Button) findViewById(R.id.backButton2);
						saveButton2 = (Button) findViewById(R.id.saveButton2);

						spinnerCrimeType = (Spinner) findViewById(R.id.crimeType);
						ArrayAdapter<CharSequence> adapter3 = ArrayAdapter
								.createFromResource(RetroActivity.this,
										R.array.crimetype_array,
										android.R.layout.simple_spinner_item);
						
						adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					
						spinnerCrimeType.setAdapter(adapter3);// hours =
																// hoursIP.getText().toString();timeStamp
																// = new
																// SimpleDateFormat("yyyyMMdd_HHmmss").format(new
																// Date());
						spinnerIsHateCrime = (Spinner) findViewById(R.id.isHateCrime);
						ArrayAdapter<CharSequence> adapterHC = ArrayAdapter.createFromResource(
								RetroActivity.this, R.array.hatecrime_array,
								android.R.layout.simple_spinner_item);
						adapterHC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						
						spinnerIsHateCrime.setAdapter(adapterHC);
						
						backButton2
								.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										nextpage();

									}
								});
						saveButton2
								.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {

										Log.d("RETROACTIVITY", "2nd onClick opened");

										type = spinnerCrimeType
												.getSelectedItem().toString();
										hCrime = spinnerIsHateCrime.getSelectedItem().toString();
										
										Bundle extras = getIntent().getExtras();
										if (extras != null) {
										String googleAccount = extras.getString("acct");

										// timeStamp = new
										// SimpleDateFormat("yyyyMMdd_HHmmss").format(new
										// Date());

										uploaderV2 ard = new uploaderV2();

										Log.d("RETROACTIVITY", "started uploader");

										ard.AddName(googleAccount);

										Log.d("RETROACTIVITY", "got account");

										ard.AddLat(lat); // send lat/long
										ard.AddLng(lng);

										Log.d("RETROACTIVITY", "got latlong");
										ard.AddQID("3");
										ard.AddFOC(foc);
										Log.d("RETROACTIVITY", "got foc");
										ard.AddTime(timeStamp);

										Log.d("RETROACTIVITY", "got timestamp");
										ard.AddType(type);
										Log.d("RETROACTIVITY", "got type");
										ard.AddTransp(transport);
										ard.AddHCrime(hCrime);
										Log.d("RETROACTIVITY", "got transport");
										ard.AddWinner(Util.getGoogleAccount(self));

										ard.writeout();
										Log.d("RETROACTIVITY", "writeout");
										ard.submitDataPost();
										Log.d("RETROACTIVITY", "submitDataPost");

										Intent data = new Intent();
										setResult(RESULT_OK, data);
										Toast.makeText(self, Util.requestSent,
												100).show();

										finish();
									}}

								});

					}

				});

			}

		});

	}

	private void onPostExecute() {
		// TODO Auto-generated method stub

		Log.d("RETROACTIVITY", "in onpostexe");

	}

	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
