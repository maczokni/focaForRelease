//
// This is for uploading the data from the pre-experiment questionnaire to your database 
// I use name-value pairs and a php page to make quesries to a mysql database
// for php example see processForm.php file
//

package uk.ac.ucl.uctzrso.foca;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

public class demographicsUpload {
	NameValuePair nameValuePairs[] = new NameValuePair[9];
	String AName;
	String AAge;
	String AGender;
	String AEthnicity;
	String AHomePC;
	String AWorkPC;
	String ABCS;
	String APreVic;
	String ASex;

	private static final String TAG = "uploadzzz";

	public demographicsUpload() {
		// TODO Auto-generated constructor stub

		AName = "";
		AAge = "";
		AGender = "";
		AEthnicity = "";
		AHomePC = "";
		AWorkPC = "";
		ABCS = "";
		APreVic = "";
		ASex = "";

	}

	public void AddName(String uname) {
		// TODO Auto-generated method stub
		

		AName = uname;
	}

	public void AddAge(String age) {
		// TODO Auto-generated method stub
		age = age.replace(" ", "_");
		AAge = age;
	}

	public void AddGender(String gender) {
		// TODO Auto-generated method stub
		gender = gender.replace(" ", "_");
		AGender = gender;

	}

	public void AddEthnicity(String ethnicity) {
		// TODO Auto-generated method stub
		ethnicity = ethnicity.replace(" ", "_");
		AEthnicity = ethnicity;

	}

	public void AddHomePC(String homePC) {
		// TODO Auto-generated method stub
		AHomePC = homePC.replace(" ", "_");

	}

	public void AddWorkPC(String workPC) {
		// TODO Auto-generated method stub
		workPC = workPC.replace(" ", "_");
		AWorkPC = workPC;
	}

	public void AddBCS(String bcs) {
		// TODO Auto-generated method stub
		bcs = bcs.replace(" ", "_");
		ABCS = bcs;
	}

	public void AddPreVic(String preVic) {
		// TODO Auto-generated method stub
		preVic = preVic.replace(" ", "_");
		APreVic = preVic;
	}

	public void AddSex(String sexuality) {
		// TODO Auto-generated method stub
		sexuality = sexuality.replace(" ", "_");
		ASex = sexuality;
	}

	// Array writeout() {
	// TODO Auto-generated method stub
	// ard.writeout();

	public void writeout() {

		// then create a name value pair instance for each edit text on the form
		// add these to a list of name value pairs - where the name parameter is
		// the field name in the database
		// and the value is the value that you want to insert into that field

		// this will store the list of field names and values
		// the NameValuePair class is provided by Java to store this type of
		// paired data
		// NB we have increased the size of the array here as we have now got 5
		// values to pass to the server
		
		nameValuePairs[0] = new BasicNameValuePair("name", AName);

		nameValuePairs[1] = new BasicNameValuePair("age", AAge);

		nameValuePairs[2] = new BasicNameValuePair("gender", AGender);

		nameValuePairs[3] = new BasicNameValuePair("ethnicity", AEthnicity);

		nameValuePairs[4] = new BasicNameValuePair("homePC", AHomePC);

		nameValuePairs[5] = new BasicNameValuePair("workPC", AWorkPC);

		nameValuePairs[6] = new BasicNameValuePair("bcs", ABCS);

		nameValuePairs[7] = new BasicNameValuePair("preVic", APreVic);

		nameValuePairs[8] = new BasicNameValuePair("sex", ASex);

		// Log.d(TAG, "data: "+AData+ATime+ALat+ALng);

		Log.d(TAG, "data: " + nameValuePairs[0] + nameValuePairs[1]
				+ nameValuePairs[2] + nameValuePairs[3] + nameValuePairs[4]
				+ nameValuePairs[5] + nameValuePairs[6] + nameValuePairs[7]
				+ nameValuePairs[8]);

		// return nameValuePairs;

	}

	public void submitDataPost() {
		// extract the data from the form into an array of NameValuePair objects

		Log.d(TAG, "Woohoo, we're in submitDataPost!!");
		// create an asynchronous operation that will take these values
		// and send them to the server
		SubmitFormData sfd = new SubmitFormData();
		Log.d(TAG, "Woohoo, we've tried SubmitFormData");
		sfd.execute(nameValuePairs);
	}

	public class SubmitFormData extends AsyncTask<NameValuePair, Void, String> {

		@Override
		protected void onPreExecute() {
			Log.d(TAG, "Woohoo, we're in SubmitFormData onPreExecute");

		}

		// start the actual background activity
		// the three dots ... in the doInBackground method mean that
		// NameValuePair is an array - i.e. a collection of
		// the same type of information - in this case, a collection of
		// NameValuePair objects
		@Override
		protected String doInBackground(NameValuePair... dataValues) {
			String response = "";

			// set up the URL of the PHP file
			String url = "http://....php"; //add url here

			// create a new HTTP connection
			AndroidHttpClient httpClient = AndroidHttpClient
					.newInstance("Android");

			// loop through the dataValues array and extract the names and
			// values
			// make these into a string which will form the post request
			String postString = "";
			for (int i = 0; i < dataValues.length; i++) {
				postString = postString + dataValues[i].getName() + "="
						+ dataValues[i].getValue();
				postString = postString + "&"; // in an HTTP post request, the &
												// is used to separate the
												// name/value pairs
			}

			// append the post information onto the main URL
			url = url + "?" + postString;

			Log.d("postString", postString);

			// now make the actual post
			// this is done within a try/catch statement so that if an error
			// occurs the system
			// will recover neatly
			try {
				HttpPost httpPost = new HttpPost(url);
				HttpResponse httpResponse;
				httpResponse = httpClient.execute(httpPost);

				// this is the text that the server sends back
				// which for the simple example is the print_r statement
				// for example, it could be any echo or print_r statement from
				// your PHP file
				HttpEntity httpEntity = httpResponse.getEntity();

				// convert the response to a string
				String line = EntityUtils.toString(httpEntity);

				// send the response out of the asynchronous activity
				// to the onPostExecute method
				response = line;

				Log.d(TAG, "uploading...");

			} catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, "NOT uploading...");
			}
			return response;
		}

	}

}
