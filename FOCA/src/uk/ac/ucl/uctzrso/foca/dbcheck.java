//
// This class is for checking whether the user has already completed the pre-experiment questionnaire
// it finds their username equivalent, and checks it against all the ones saved in the demographics database
// here you will need to change fields pointing to your php page or any other way you want to set up the db query
// example php can be found in phps folder, it's called: checkUserInDb.php 
//

package uk.ac.ucl.uctzrso.foca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import android.os.AsyncTask;
import android.util.Log;

public class dbcheck extends AsyncTask<String, Void, String> {

	URL url;
	URL url3;
	Document doc;
	String readPHP;
	String UName;
	boolean userreg;
	String line;
	String url2;

	public dbcheck() {
		UName = "";
		line = "";
	}

	public void googleAccount(String googleAccount) {
		
		UName = googleAccount;

	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		// return null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://....php"); //add url here

		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("name", UName)); // sent
																		// username
																		// to
																		// check

			Log.d("UNAME CHKD: ", UName);

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			httpclient.execute(httppost);

			url = new URL(
					"http://....php"); //add url here

			url2 = url.toString();

			// append the post information onto the main URL
			url2 = url2 + "?name=" + UName;

			url3 = new URL(url2);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					url3.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				line += inputLine;
			in.close();
			/*
			 * doc = Jsoup.parse(url, 3*1000); readPHP = doc.body().text();
			 * //read output from page Log.d("readPHP", readPHP);
			 * Log.d("MESSAGE", "postExecute()"); //if(context instanceof
			 * MainActivity){
			 */

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException er) {
			// TODO Auto-generated catch block
			er.printStackTrace();
		}

		return line;

	}

	@Override
	protected void onPostExecute(String readPHP) {
	//	Log.d("MESSAGE", "postExecute()");
	//	Log.d("WAT IS LINE", line);

	}

}
