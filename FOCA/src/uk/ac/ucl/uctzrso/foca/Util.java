package uk.ac.ucl.uctzrso.foca;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

public class Util {
	public static final String postReportRequest = "<postReportRequest><userName>%s</userName><report><comment>%s</comment><gpsLat>%s</gpsLat><gpsLng>%s</gpsLng><reportFiles><reportFile><fileName>%s</fileName><base64String>%s</base64String></reportFile></reportFiles></report></postReportRequest>";
	public static final String postUserRegRequest = "<addUserRequest><user><name>%s</name><gender>%s</gender><disability>%s</disability><disabilitydevice>%s</disabilitydevice></user></addUserRequest>";
	public static final String requestSent = " Thank you for your submission. Request sent.";
	public static final String waitingForGPS = "Waiting for GPS... please try again in a minute";
	
	
	public static String getGoogleAccount(Context context){
		AccountManager manager = AccountManager.get(context);
		Account[] accounts = manager.getAccountsByType("com.google");
		return accounts[0].name;
	}
	
	public static String[] getGoogleAccountsAsStringArray(Context context){
		AccountManager manager = AccountManager.get(context);
		//Account[] accounts = manager.getAccounts();
		Account[] accounts = manager.getAccountsByType("com.google");
		
		String[] accountNames = new String[accounts.length];
		int i = 0;
		for(Account account:accounts){
			accountNames[i++] = account.name;
		}
		
		return accountNames;
	}
	
	public static boolean isGoogleAccountAvailable(Context context){
		return AccountManager.get(context).getAccountsByType("com.google").length>0;
	}
	
}

