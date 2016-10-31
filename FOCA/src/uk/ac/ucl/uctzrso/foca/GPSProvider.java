package uk.ac.ucl.uctzrso.foca;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSProvider implements LocationListener {

	private static final int TWO_MINUTES = 1000 * 60 * 2;
	LocationManager locationManager;
	private Location currentLocation;
	private Context context;

	public GPSProvider(Context context) {
		this.context = context;
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0, this);


// NOTE: I disabled get last known location, because I needed the reports to have accurate GPS
// readings associated with them, it would not make sense for me to get the last place they had GPS
// so I would rather not receive a report than receive a report from a false location. If this
// does not matter to you, then just uncomment the below:

/*		if (isGpsEnabled()) {
			currentLocation = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		
		locationManager.requestLocationUpdates(gpsProvider, 1000, 1, listener);
		*/
	}

	public boolean isGpsEnabled() {
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public double getLatitude() {
		return ((currentLocation != null) ? currentLocation.getLatitude() : 0.0);
	}

	public double getLongitude() {
		return ((currentLocation != null) ? currentLocation.getLongitude()
				: 0.0);
	}

	@Override
	public void onLocationChanged(Location location) {

		if (isBetterLocation(location, currentLocation)) {
			currentLocation = location;
		}
		

		// Log.d("MESSAGE", "Location Changed.");
	}

	// http://developer.android.com/guide/topics/location/strategies.html
	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate) {
			return true;
		}
		return false;
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}

