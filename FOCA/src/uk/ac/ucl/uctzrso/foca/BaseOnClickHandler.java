package uk.ac.ucl.uctzrso.foca;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class BaseOnClickHandler implements OnClickListener {

	protected Activity activity;
	protected String className;
	protected int requestCode;
	
	public BaseOnClickHandler(Activity activity,String className,int requestCode){
		this.activity = activity;
		this.className = className;
		this.requestCode = requestCode;
	}
	
	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent(className);
		activity.startActivityForResult(intent, requestCode);
		
	}

}

