// https://developer.android.com/training/location/retrieve-current.html

package info.ginpei.androidui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {

    public static final int REQ_LOCATION_FROM_UPDATE = 1;
    public static final String TAG = "G#LocationActivity";
    private LocationManager manager;
    private boolean askedForPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateLocation();
    }

    /**
     * Called when the user allows or denies a permission.
     *
     * @see android.support.v4.app.FragmentActivity#onRequestPermissionsResult(int, String[], int[])
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_LOCATION_FROM_UPDATE:
                // Call again so that getting location success if granted,
                // otherwise it shows a message for the permission.
                updateLocation();
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Play with location.
     */
    private void updateLocation() {
        Location location = getLocation();
        Log.d(TAG, "updateLocation: location null? " + (location == null));

        if (location == null) {
            requestLocationPermission(REQ_LOCATION_FROM_UPDATE);
            return;
        }

        renderLocation(location);
    }

    private void renderLocation(Location location) {
        ((TextView) findViewById(R.id.textView_latitude)).setText(String.valueOf(location.getLatitude()));
        ((TextView) findViewById(R.id.textView_longitude)).setText(String.valueOf(location.getLongitude()));
    }

    /**
     * Return last known location.
     * If the permission is not granted, return null.
     *
     * @return Location if the permission is granted. Otherwise, null.
     */
    @Nullable
    private Location getLocation() {
        final String permissionName = Manifest.permission.ACCESS_FINE_LOCATION;

        if (ActivityCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getLocation: Yes, granted.");
            return manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            Log.d(TAG, "getLocation: Not granted.");
            return null;
        }
    }

    /**
     * Request the permission.
     * If already asked, show a message for the permission instead.
     *
     * @param requestCode Request code which shared in {@link #onRequestPermissionsResult}.
     */
    private void requestLocationPermission(int requestCode) {
        final String permissionName = Manifest.permission.ACCESS_FINE_LOCATION;

        boolean shouldAskNow = ActivityCompat.shouldShowRequestPermissionRationale(this, permissionName);
        Log.d(TAG, "requestLocationPermission: App should request? " + shouldAskNow + ". Already asked here? " + askedForPermission);

        if (shouldAskNow && !askedForPermission) {
            ActivityCompat.requestPermissions(this, new String[]{permissionName}, requestCode);
            askedForPermission = true;
        } else {
            Toast.makeText(this, "Please allow location access.", Toast.LENGTH_SHORT).show();
        }
    }
}
