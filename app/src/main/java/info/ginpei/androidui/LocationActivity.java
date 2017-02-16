// https://developer.android.com/training/location/retrieve-current.html

package info.ginpei.androidui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "G#LocationActivity";
    private GoogleApiClient googleApiClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        getPermission();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();
            Log.d(TAG, "Latitude=" + latitude + ", Longitute=" + longitude);

            ((TextView) findViewById(R.id.textView_latitude)).setText(String.valueOf(latitude));
            ((TextView) findViewById(R.id.textView_longitude)).setText(String.valueOf(longitude));
        } else {
            Log.w(TAG, "Failed to get the last location.");
            Toast.makeText(this, "Failed to get the last location.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "onConnectionSuspended. cause=" + cause);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed. Error message=" + connectionResult.getErrorMessage());
    }


    private void getPermission() {
        String permission = Manifest.permission.ACCESS_COARSE_LOCATION;

        int permittedStatus = ContextCompat.checkSelfPermission(this, permission);
        if (permittedStatus != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
            }
        }
    }
}

//public class LocationActivity extends AppCompatActivity implements
//        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//
//    protected static final String TAG = "MainActivity";
//
//    /**
//     * Provides the entry point to Google Play services.
//     */
//    protected GoogleApiClient mGoogleApiClient;
//
//    /**
//     * Represents a geographical location.
//     */
//    protected Location mLastLocation;
//
////    protected String mLatitudeLabel;
////    protected String mLongitudeLabel;
//    protected TextView mLatitudeText;
//    protected TextView mLongitudeText;
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_location);
//
////        mLatitudeLabel = getResources().getString(R.string.latitude_label);
////        mLongitudeLabel = getResources().getString(R.string.longitude_label);
//        mLatitudeText = (TextView) findViewById((R.id.textView_latitude));
//        mLongitudeText = (TextView) findViewById((R.id.textView_longitude));
//
//        buildGoogleApiClient();
//    }
//
//    /**
//     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
//     */
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }
//
//    /**
//     * Runs when a GoogleApiClient object successfully connects.
//     */
//    @Override
//    public void onConnected(Bundle connectionHint) {
//        // Provides a simple way of getting a device's location and is well suited for
//        // applications that do not require a fine-grained location and that do not need location
//        // updates. Gets the best and most recent location currently available, which may be null
//        // in rare cases when a location is not available.
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (mLastLocation != null) {
//            mLatitudeText.setText(String.format("%s: %f", "",
//                    mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.format("%s: %f", "",
//                    mLastLocation.getLongitude()));
//        } else {
//            Toast.makeText(this, "NO", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
//        // onConnectionFailed.
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
//    }
//
//
//    @Override
//    public void onConnectionSuspended(int cause) {
//        // The connection to Google Play services was lost for some reason. We call connect() to
//        // attempt to re-establish the connection.
//        Log.i(TAG, "Connection suspended");
//        mGoogleApiClient.connect();
//    }
//}
