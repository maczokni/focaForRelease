// 
// This is how I upload the reports, it's the same process as 
// how I upload the demographic info, with namevaluepairs + php + mysql query
// for php example see processForm.php file
//

package uk.ac.ucl.uctzrso.foca;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;


public class uploaderV2 {

	NameValuePair nameValuePairs[] = new NameValuePair[9];
	NameValuePair winnerPairs[] = new NameValuePair[1];
	String AName;
	String AFoc;
	String ATime;
	String ALat;
	String ALng;
	String AQid;
	String ATransp;
	String AType;
	String AHC;
	String AWinner;

	private static final String TAG = "uploadzzz";
	private uploaderV2 self = this;
	

	public uploaderV2() {
		// TODO Auto-generated constructor stub

		AName = "";
		AFoc = "";
		ATime = "";
		ALat = "";
		ALng = "";
		AQid = "";
		ATransp = "";
		AType = "";
		AHC = "";
		AWinner="";

	}

	public void AddFOC(String string) {
		// TODO Auto-generated method stub
		string = string.replace(" ", "_");
		AFoc = string;
	}

	public void AddName(String uname) {
		// TODO Auto-generated method stub
		AName = uname;
	}

	public void AddTime(String timeStamp) {
		// TODO Auto-generated method stub
		ATime = timeStamp;
	}

	public void AddLat(double latitude) {
		// TODO Auto-generated method stub
		ALat = Double.toString(latitude);

	}

	public void AddLng(double longitude) {
		// TODO Auto-generated method stub
		ALng = Double.toString(longitude);

	}

	public void AddQID(String string) {
		// TODO Auto-generated method stub
		AQid = string;
	}

	public void AddTransp(String transport) {
		// TODO Auto-generated method stub
		transport = transport.replace(" ", "_");
		ATransp = transport;
	}

	public void AddType(String type) {
		// TODO Auto-generated method stub
		type = type.replace(" ", "_");
		AType = type;
	}
	public void AddHCrime(String hCrime) {
		// TODO Auto-generated method stub
		hCrime = hCrime.replace(" ", "_");
		AHC = hCrime;
	}
	
	public void AddWinner(String googleAccount) {
		// TODO Auto-generated method stub
		AWinner = googleAccount;
		
	}

	

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
		// NameValuePair nameValuePairs[] = new NameValuePair[6];

		// String arduino = (String) txtArduino.getText().toString();
		nameValuePairs[0] = new BasicNameValuePair("name", AName);

		nameValuePairs[1] = new BasicNameValuePair("FOC", AFoc);

		nameValuePairs[2] = new BasicNameValuePair("time", ATime);

		nameValuePairs[3] = new BasicNameValuePair("latitude", ALat);

		nameValuePairs[4] = new BasicNameValuePair("longitude", ALng);

		nameValuePairs[5] = new BasicNameValuePair("questionid", AQid);

		nameValuePairs[6] = new BasicNameValuePair("transport", ATransp);

		nameValuePairs[7] = new BasicNameValuePair("type", AType);
		
		nameValuePairs[8] = new BasicNameValuePair("hc", AHC);
		
		winnerPairs[0] = new BasicNameValuePair("winner", AWinner);

		Log.d(TAG, "data: " + nameValuePairs[0] + nameValuePairs[1]
				+ nameValuePairs[2] + nameValuePairs[3] + nameValuePairs[4]
				+ nameValuePairs[5] + nameValuePairs[6] + nameValuePairs[7] + nameValuePairs[8]);

		// return nameValuePairs;

	}

	public void submitDataPost() {
		// extract the data from the form into an array of NameValuePair objects
		// NameValuePair nameValuePairs[] = writeout();

		Log.d(TAG, "Woohoo, we're in submitDataPost!!");
		// create an asynchronous operation that will take these values
		// and send them to the server
		SubmitFormData sfd = new SubmitFormData();
		Log.d(TAG, "Woohoo, we've tried SubmitFormData");
		sfd.execute(nameValuePairs);
		
		SubmitVoucherData svd = new SubmitVoucherData();
		Log.d(TAG, "Woohoo, we've tried SubmitFormData");
		svd.execute(winnerPairs);
		
	}

	// android.os.AsyncTask<Params, Progress, Result>
	private class SubmitFormData extends AsyncTask<NameValuePair, Void, String> {

		@Override
		protected void onPreExecute() {
			Log.d(TAG, "Woohoo, we're in SubmitFormData onPreExecute");

		}

		
		@Override
		protected String doInBackground(NameValuePair... dataValues) {
			String response = "";

			// set up the URL of the PHP file
			String url = "http://....php"; // add URL here

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
	
	private class SubmitVoucherData extends AsyncTask<NameValuePair, Void, String> {

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
			String url = "http://....php";  // add URL here

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