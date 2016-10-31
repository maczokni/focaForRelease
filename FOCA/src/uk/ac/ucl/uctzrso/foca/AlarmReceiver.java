//
// This is where you tell the app what to do when it is woken up by the alarm;
// at the moment it puts an icon and text in the notification bar, which when 
// clicked opens up the app on the 'complete questionnaire now' page
//


package uk.ac.ucl.uctzrso.foca;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	
	NotificationManager mNotificationManager;
	
    @Override
    public void onReceive(Context arg0, Intent arg1) {
    	
    	Log.d("ALARMMM","we are inonRecieve in AlarmReciever");
    	
         NotificationCompat.Builder mBuilder =
	            new NotificationCompat.Builder(arg0)
	            .setSmallIcon(R.drawable.notification_icon)
	            .setContentTitle("FOCA Reminder")
	            .setContentText("Please Complete Questionnaire at the soonest convenient time")
	            .setAutoCancel(true);
         
     Intent resultIntent = new Intent(arg0, MainActivity.class);
     TaskStackBuilder stackBuilder = TaskStackBuilder.create(arg0);
     stackBuilder.addParentStack(MainActivity.class);
     stackBuilder.addNextIntent(resultIntent);
     PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
     mBuilder.setContentIntent(resultPendingIntent);
     mNotificationManager = (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
     mNotificationManager.notify(9011, mBuilder.build());
    // mBuilder.setAutoCancel(true);
    
        
     Log.d("ALARMMM","we got to the end of onRecieve in AlarmReciever");
     
     
     
     
     Log.d("NotificationAlarm", "onReceive");

     
    }
    

}
