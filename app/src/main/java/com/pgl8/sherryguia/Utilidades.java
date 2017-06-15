package com.pgl8.sherryguia;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Clase con utilidades generales
 */

public class Utilidades implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mApiClient;
    Location mLocation;
    private Context mContext;

    public Utilidades(Context context){
        this.mContext = context;
        //buildGoogleApiClient();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(mContext, "Conexión suspendida...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(mContext, "Error al conectar...", Toast.LENGTH_SHORT).show();
    }

    //public void

}
