//
// This is the about page that lets people know the version they have installed, and give contact details
// to get in touch in case they have any questions. To change text that appears here, edit the about.xml 
// in the layout folder. 
//


package uk.ac.ucl.uctzrso.foca;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class about extends Activity {
	
	public TextView textView1;
	public TextView textView2;
	public TextView textView3;
	public TextView textView4;
	public TextView textView5;
	public String versionName;
	private Button backButton;
	
	
protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	//}
	setContentView(R.layout.about);
	
	textView1 = (TextView) findViewById(R.id.textView1);
	textView2 = (TextView) findViewById(R.id.textView2);
	textView3 = (TextView) findViewById(R.id.textView3);
	textView4 = (TextView) findViewById(R.id.textView4);
	textView5 = (TextView) findViewById(R.id.textView5);
	backButton = (Button) findViewById(R.id.button1);
	
	try {
		versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
	} catch (NameNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
	textView5.setText(versionName);
	
	backButton.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();

		}
	});

}
}