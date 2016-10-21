package com.quseit.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;


public class LocationManager {
	//private static final String TAG = "LocationManager";
	//private static final int TWO_MINUTES = 1000 * 60 * 2;
	private static final int FIVE_MINUTES = 1000 * 60 * 5;
	private static final int FIFTEEN_MINUTES = 1000 * 60 * 10;

	private static final int LOCATION_SIGNIFICANTLY_NEWER = FIVE_MINUTES;
	private static final int LOCATION_SIGNIFICANTLY_OLDER = FIVE_MINUTES;
	
	private static final int UPDATE_PERIOD_MINIMUM_LENGTH = FIVE_MINUTES;
	private static final int UPDATE_PERIOD_MAXIMUM_LENGTH = FIFTEEN_MINUTES;
	
	private static final int LOCATION_HISTORY_SIZE = 5;
	
	private int mUpdatePeriodCurrent = UPDATE_PERIOD_MINIMUM_LENGTH;
	
	private static LocationManager INSTANCE = null;
	private android.location.LocationManager mLocationManager;
	private Location mCurrentLocation;
	private String mBestProvider = "";
	private Criteria mBestProviderCriteria = new Criteria();
	
	private LooperThread mLocationUpdateThread = new LooperThread();
	private LocationUpdateRunnable mLocationUpdater;
	
	private Map<String, Vector<Location>> mLocationHistory = new HashMap<String, Vector<Location>>();
	
	private class LocationUpdateRunnable implements Runnable, LocationListener {
		//private static final String TAG = "LocationUpdateRunnable";
		private boolean mActive = false;
		private Handler mHandler;
		private long mLastRun = 0;
		
		public LocationUpdateRunnable(LooperThread thread) {
			mHandler = thread.getHandler();
			
			// Listen passively for location updates
			mHandler.post(new Runnable() {	
				public void run() {
					//Log.d(TAG, "Starting passive location listener");
					mLocationManager.requestLocationUpdates(
							android.location.LocationManager.PASSIVE_PROVIDER, 0, 0, 
							LocationUpdateRunnable.this);
				}
			});
		}
		
		public void onLocationChanged(Location location) {
			//Log.d(TAG, "Received new location fix: " + location);
			
			// Update current location
			processNewLocation(location);
			
			// Disable GPS to save battery
			mLocationManager.removeUpdates(this);

			// Delay next update
			mHandler.postDelayed(this, mUpdatePeriodCurrent);
		}

		public void onProviderDisabled(String provider) {
			if (isSameProvider(provider, mBestProvider)) {
				updateLocationProvider();
			}
		}
		public void onProviderEnabled(String provider) {
			updateLocationProvider();
		}
		
		public void onStatusChanged(String provider, int status, Bundle extras) {
			updateLocationProvider();
		}

		public void run() {
			if (mBestProvider != null) {
				mLocationManager.requestLocationUpdates(mBestProvider, 0, 0, this);
				mLastRun = System.currentTimeMillis();
			}
		}
		
		public void activateLocationUpdates() {
			if (mActive == false) {
				long deltaTime = mLastRun + mUpdatePeriodCurrent - System.currentTimeMillis();
				if (deltaTime < 0) {
					// Update is overdue, do it now
					mHandler.post(this);
				} else {
					// Wait until next update is scheduled
					mHandler.postDelayed(this, deltaTime);
				}
				mActive = true;
			}
		}
		
		public void deactivateLocationUpdate() {
			if (mActive == true) {
				mLocationUpdateThread.getHandler().removeCallbacks(this);
				mActive = false;
			}
		}
	}
	
	public static LocationManager createInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new LocationManager(context);
		}
		
		return INSTANCE;
	}
	
	public static LocationManager getInstance () {
		return INSTANCE;
	}
	
	public void activateLocationUpdates() {
		updateLocationProvider();
		mLocationUpdater.activateLocationUpdates();
	}
	
	public void deactivateLocationUpdate() {
		mLocationUpdater.deactivateLocationUpdate();
	}
	
	private LocationManager(Context context) {
		mLocationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// Set criteria to choose best provider
		mBestProviderCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		updateLocationProvider();
		
		// Get initial location from last known locations of all providers
		List<String> providers = mLocationManager.getAllProviders();
		Location tmpLocation;
		if (!providers.isEmpty()) {
			for (String p : providers) {
				tmpLocation = mLocationManager.getLastKnownLocation(p);
				if ((tmpLocation != null) && 
					isBetterLocation(tmpLocation, mCurrentLocation)) {
					mCurrentLocation = tmpLocation;
				}
			}
		}

		// Start location update thread
		mLocationUpdateThread.start();
		mLocationUpdateThread.setName("LocationUpdater");
		mLocationUpdater = new LocationUpdateRunnable(mLocationUpdateThread);
		
		if ((mCurrentLocation != null) &&
			isSameProvider(mCurrentLocation.getProvider(), mBestProvider)) {
			mLocationUpdater.mLastRun = mCurrentLocation.getTime();
		}

		mLocationUpdater.activateLocationUpdates();
	}
	
	public Location getCurrentLocation() {
		return mCurrentLocation;
	}
	
	private void updateLocationProvider() {
		String bestProvider = mLocationManager.getBestProvider(mBestProviderCriteria, true);
		
		if (!isSameProvider(bestProvider, mBestProvider)) {
			mBestProvider = bestProvider;
		}
	}
	
	private void processNewLocation (Location location) {
		if (userHasMoved(location)) {
			// Shorten update period
			mUpdatePeriodCurrent = Math.max(UPDATE_PERIOD_MINIMUM_LENGTH, (int)(mUpdatePeriodCurrent * 0.5f));
			//Log.d(TAG, "Location update period shortened to " + mUpdatePeriodCurrent / 1000 + "s");
		} else {
			// Extend update period
			mUpdatePeriodCurrent = Math.min(UPDATE_PERIOD_MAXIMUM_LENGTH, (int)(mUpdatePeriodCurrent * 2.0f));
			//Log.d(TAG, "Location update period extended to " + mUpdatePeriodCurrent / 1000 + "s");
		}
		
		if (isBetterLocation(location, mCurrentLocation)) {
			mCurrentLocation = location;
		}
	} 
	
	private boolean userHasMoved(Location location) {
		Vector<Location> history = mLocationHistory.get(location.getProvider());
		if (history == null) {
			history = new Vector<Location>();
			history.add(location);
			mLocationHistory.put(location.getProvider(), history);
			return true;
		} else {
			if (history.lastElement().equals(location)) {
				return false;
			}
			
			history.add(location);
		}
		
		if (history.size() > LOCATION_HISTORY_SIZE) {
			history.remove(0);
		}
		
		// TODO improve ;)
		if (history.firstElement().distanceTo(history.lastElement()) > 100) {
			return true;
		} else {
			return false;
		}
	}
	
	/** Determines whether one Location reading is better than the current Location fix
	 * 
	 *  === Part of Android Documentation ===
	 *  
	 * @param location  The new Location that you want to evaluate
	 * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	 */
	private boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > LOCATION_SIGNIFICANTLY_NEWER;
	    boolean isSignificantlyOlder = timeDelta < -LOCATION_SIGNIFICANTLY_OLDER;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
}
