//
// This is the activity that deploys the pre-experiment questionnaire that is launched when someone
// startes the application and their unique identifier is not already found in the database
// To change the questions you will also have to make changes in form.xml in layouts and strings.xml in values for the spinners
// 

package uk.ac.ucl.uctzrso.foca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class FormActivity extends Activity {

	private Button OKButton;
	private Button NOWButton;
	private Button nextButton1;
	private Button nextButton2;
	private Button nextButton3;
	private Button nextButton4;
	private Button backButton1;
	private Button backButton2;
	private Button backButton3;
	private Button backButton4;
	private Button backButton5;
	private Button sendButton;
	private RadioGroup isGender;
	private RadioGroup isBCS;
	private RadioGroup isPreVic;
	private RadioButton genderRadio;
	private RadioButton bcsRadio;
	private RadioButton preVicRadio;
	private String ethnicity;
	private String gender;
	private String sexuality;
	private EditText isAge;
	private EditText isHomePC;
	private EditText isWorkPC;
	private String age;
	private String bcs;
	private String homePC;
	private String workPC;
	private String preVic;
	private String gAccount;
	private Activity self = this;
	private Spinner spinnerEthnicity;
	private Spinner spinnerGender;
	private Spinner spinnerSexuality;
	private int ageNum;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);

		OKButton = (Button) findViewById(R.id.OKButton);
		NOWButton = (Button) findViewById(R.id.NOWButton);

		OKButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		NOWButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle extras = getIntent().getExtras();

				gAccount = extras.getString("gAccount");
				setContentView(R.layout.preq1);
				nextpage1();
			}
		});

	}

	public void nextpage1() {

		setContentView(R.layout.preq1);

		Log.d("TESTSSS", "nextpage opened");

		nextButton1 = (Button) findViewById(R.id.preq1next);
		backButton1 = (Button) findViewById(R.id.backButton);
		isAge = (EditText) findViewById(R.id.isAge);
		spinnerGender = (Spinner) findViewById(R.id.isGender);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapterGender = ArrayAdapter
				.createFromResource(this, R.array.gender_array,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterGender
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerGender.setAdapter(adapterGender);
		nextButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("TESTSSS", "2nd onClick opened");
				gender = spinnerGender.getSelectedItem().toString();
				age = isAge.getText().toString();
				ageNum = 444;

				try {
					ageNum = Integer.parseInt(age);                 // so here i show different questions
																	// depending on the age of the participant
				} catch (NumberFormatException e) {					// (under 18 or over 18) because I have	
					// do nothing.									// different ethics forms for each group	
				}													// this may not be necessary for other adaptations

				if (18 < ageNum && ageNum < 99) {

					setContentView(R.layout.preq2);
					nextpage2();
				}

				else if (5 < ageNum && ageNum <= 18) {

					setContentView(R.layout.preq2v2);
					nextpage2v2();
				}

				else {

					Toast.makeText(self, "That is not a valid age",
							Toast.LENGTH_LONG).show();

				}
			}
		});

		backButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

	public void nextpage2() {

		setContentView(R.layout.preq2);

		Log.d("TESTSSS", "nextpage opened");

		backButton2 = (Button) findViewById(R.id.backButton2);
		nextButton2 = (Button) findViewById(R.id.preq2next);
		spinnerEthnicity = (Spinner) findViewById(R.id.isEthnicity);

		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				this, R.array.ethnicity_array,
				android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerEthnicity.setAdapter(adapter3);

		spinnerSexuality = (Spinner) findViewById(R.id.isSexuality);
		ArrayAdapter<CharSequence> adapterSex = ArrayAdapter
				.createFromResource(this, R.array.sexuality_array,
						android.R.layout.simple_spinner_item);
		adapterSex
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSexuality.setAdapter(adapterSex);

		nextButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("TESTSSS", "2nd onClick opened");

				ethnicity = spinnerEthnicity.getSelectedItem().toString();
				sexuality = spinnerSexuality.getSelectedItem().toString();

				setContentView(R.layout.preq3);
				nextpage3();
			}
		});

		backButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextpage1();

			}
		});
	}

	public void nextpage2v2() {

		setContentView(R.layout.preq2v2);

		Log.d("TESTSSS", "nextpage opened");

		backButton2 = (Button) findViewById(R.id.backButton2);
		nextButton2 = (Button) findViewById(R.id.preq2next);
		spinnerEthnicity = (Spinner) findViewById(R.id.isEthnicity);

		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				this, R.array.ethnicity_array,
				android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerEthnicity.setAdapter(adapter3);

		nextButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("TESTSSS", "2nd onClick opened");

				ethnicity = spinnerEthnicity.getSelectedItem().toString();
				sexuality = "NULL";

				setContentView(R.layout.preq3);
				nextpage3();
			}
		});

		backButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextpage1();

			}
		});
	}

	public void nextpage3() {

		setContentView(R.layout.preq3);

		Log.d("TESTSSS", "page 3 opened");

		nextButton3 = (Button) findViewById(R.id.preq3next);
		backButton3 = (Button) findViewById(R.id.backButton3);
		isBCS = (RadioGroup) findViewById(R.id.isBCS);
		// hours = hoursIP.getText().toString();timeStamp = new
		// SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		nextButton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("TESTSSS", "pg3 onClick opened");

				bcsRadio = (RadioButton) isBCS.findViewById(isBCS
						.getCheckedRadioButtonId());
				bcs = bcsRadio.getText().toString();

				setContentView(R.layout.preq4);
				nextpage4();
			}
		});

		backButton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ageNum <= 18) {

					setContentView(R.layout.preq2v2);
					nextpage2v2();
				} else {

					setContentView(R.layout.preq2);
					nextpage2();

				}
			}
		});
	}

	public void nextpage4() {

		setContentView(R.layout.preq4);

		Log.d("TESTSSS", "page 4 opened");

		backButton4 = (Button) findViewById(R.id.backButton4);
		nextButton4 = (Button) findViewById(R.id.preq4next);
		isHomePC = (EditText) findViewById(R.id.isHomePC);
		isWorkPC = (EditText) findViewById(R.id.isWorkPC);

		// hours = hoursIP.getText().toString();timeStamp = new
		// SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		nextButton4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("TESTSSS", "2nd onClick opened");

				homePC = isHomePC.getText().toString();     
				workPC = isWorkPC.getText().toString();

				// need to make sure that we got at least some postcode info, but also
				// that they person didn't enter their ENTIRE postcode from which they
				// could be identified: 

				if (homePC.length() < 1 | homePC.length() > 5) {
					// not real
					Toast.makeText(
							self,
							"Please enter the FIRST PART of your postcode (e.g. 'N7' or 'WCH1')",
							Toast.LENGTH_LONG).show();

				} else {
					if (workPC.length() < 2 | workPC.length() > 5) {

						// not real
						Toast.makeText(
								self,
								"Please enter the FIRST PART of your postcode (e.g. 'N7' or 'WCH1')",
								Toast.LENGTH_LONG).show();

					}

					else {

						setContentView(R.layout.preq5);
						nextpage5();

					}
				}

			}
		});
		backButton4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextpage3();

			}
		});
	}

	public void nextpage5() {

		setContentView(R.layout.preq5);

		Log.d("TESTSSS", "nextpage opened");

		backButton5 = (Button) findViewById(R.id.backButton5);
		sendButton = (Button) findViewById(R.id.preq5fin);
		isPreVic = (RadioGroup) findViewById(R.id.isPreVic);
		// hours = hoursIP.getText().toString();timeStamp = new
		// SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());



		// Now we've asked all the questions, let's send it all to our
		// demographicsUpload so it can be sent off to our db and saved

		sendButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("TESTSSS", "page 5 onClick opened");

				preVicRadio = (RadioButton) isPreVic.findViewById(isPreVic
						.getCheckedRadioButtonId());
				preVic = preVicRadio.getText().toString();

				demographicsUpload ard = new demographicsUpload();
				// ard.AddName(Util.getGoogleAccount(self));
				ard.AddName(gAccount);
				ard.AddAge(age); // send lat/long
				ard.AddGender(gender);
				ard.AddEthnicity(ethnicity);
				ard.AddHomePC(homePC);
				ard.AddWorkPC(workPC);
				ard.AddBCS(bcs);
				ard.AddPreVic(preVic);
				ard.AddSex(sexuality);

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
				Toast.makeText(self, Util.requestSent, Toast.LENGTH_LONG)
						.show();

				finish();
				finish();
			}

		});

		backButton5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextpage4();

			}
		});
	}
}