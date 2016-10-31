//
// This is a page where the user can get some info on how to use the app
// To make changes you will have to change help.xml in layout folder
//

package uk.ac.ucl.uctzrso.foca;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class help extends Activity {


	private Button helpOKButton;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// }
		setContentView(R.layout.help);

		helpOKButton = (Button) findViewById(R.id.helpOKButton);
		helpOKButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
	}
}
