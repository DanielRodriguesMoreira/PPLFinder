package pt.isec.a21240321.pplfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity implements OnMapReadyCallback, LocationListener{

    GoogleMap mGoogleMap;
    LatLng ISEC; //= new LatLng(40.1925, -8.4115);//CASA -> new LatLng(40.500902, -8.473068);
    final LatLng DEIS = new LatLng(40.1925, -8.4128);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,  1, 20, this);

        //((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        getMenuInflater().inflate(R.menu.menu_filters, menu);
        getMenuInflater().inflate(R.menu.menu_messages, menu);
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    /*
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        mStatusTextView.setText(getString(R.string.signed_out_fmt));
                    }
                });
    }
    */

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("TESTE", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("TESTE", "onPause");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        CameraPosition cp = new CameraPosition.Builder().target(ISEC).zoom(17)
                .bearing(0).tilt(0).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
        googleMap.addCircle(new CircleOptions().center(ISEC).radius(150)
                .fillColor(Color.argb(128, 128, 128, 128))
                .strokeColor(Color.rgb(128, 0, 0)).strokeWidth(4));

        Intent intent = getIntent();

        MarkerOptions mo = new MarkerOptions().position(ISEC).title(intent.getStringExtra("NOME"))
                .snippet(intent.getStringExtra("GIVENNAME"));
        Marker isec = googleMap.addMarker(mo);
        isec.showInfoWindow();

        googleMap.addMarker(new MarkerOptions().position(DEIS).title("DEIS-ISEC"));
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("DANIEL", "onLocationChanged");
        ISEC = new LatLng(location.getLatitude(), location.getLongitude());
        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("DANIEL", "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("DANIEL", "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("DANIEL", "onProviderDisabled");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
