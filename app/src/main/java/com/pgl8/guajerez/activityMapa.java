package com.pgl8.guajerez;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;


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
        //Inicializamos el MapFragment
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        //Obtenemos el mapa y activamos localización
        googleMap = mapFragment.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);

        //Activamos los elementos de la interfaz de usuario
        uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setAllGesturesEnabled(true);

        //Centramos el mapa en la posicion del usuario NO FUNCIONA
        Location location = googleMap.getMyLocation();
        LatLng latlon = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdateFactory.newLatLng(latlon);
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
