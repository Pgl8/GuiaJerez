package com.pgl8.guajerez;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;


public class activityMapa extends ActionBarActivity {

    GoogleMap googleMap;
    //MapView mapView;
    MapFragment mapFragment;
    UiSettings uiSettings;
    //private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        //mapView = (MapView) findViewById(R.id.map);
        //mapView.onCreate(savedInstanceState);
        //googleMap = mapView.getMap();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync((com.google.android.gms.maps.OnMapReadyCallback) this);
        googleMap = mapFragment.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);

        // Sets camera to location and zoom
        /*Location mLocation = googleMap.getMyLocation();
        LatLng actual = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual, 15));*/

        uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setAllGesturesEnabled(true);
        /*Location mLocation = googleMap.getMyLocation();
        double Lat = mLocation.getLatitude();
        double Lon = mLocation.getLongitude();
        Log.v(TAG, "Latitud"+Lat);
        Log.v(TAG, "Longitud"+Lon);*/

        /*LatLng coordinate = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
        googleMap.animateCamera(yourLocation);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mapView.onResume();
        mapFragment.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
