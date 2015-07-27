package com.pgl8.guajerez;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.metaio.sdk.MetaioDebug;
import com.metaio.tools.io.AssetsManager;

import java.io.IOException;


public class activityPrincipal extends AppCompatActivity {
    AssetsExtracter mTask;
    View mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        if(!isNetworkAvailable()){
            // se muestra un dialog de error de conexión
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error de conexión")
                    .setMessage("No dispone de conexión a Internet, compruebe su conexión")
                    .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }else {
            // habilita los mensajes de debug de metaio
            MetaioDebug.enableLogging(BuildConfig.DEBUG);

            // identificamos la barra de progreso
            mProgress = findViewById(R.id.progress);

            // extraemos los assets
            mTask = new AssetsExtracter();
            mTask.execute(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    // función para comprobar si existe conexión disponible
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // inicia la actividad de vinos
    public void activityVinos(View view) {
        startActivity(new Intent(this, activityVinos.class));
    }

    // inicia la actividad de vinos
    public void activityLugares(View view) {
        startActivity(new Intent(this, activityLugares.class));
    }

    // inicia la actividad de mapa
    public void activityMapa(View view) {
        startActivity(new Intent(this, activityMapa.class));
    }

    // inicia la actividad de realidad aumentada
    public void activityAR(View view) {
        startActivity(new Intent(this, activityAR.class));
    }

    // inicia la actividad de realidad aumentada
    public void activityARLector(View view) {
        startActivity(new Intent(this, activityARLector.class));
    }

    // clase interna para la extracción de assets para metaio SDK
    private class AssetsExtracter extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            mProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                // Se extraen todos los assets excepto los pertenecientes a ignorelist
                final String[] ignoreList = {"Menu", "webkit", "sounds", "images", "webkitsec"};
                AssetsManager.extractAllAssets(getApplicationContext(), "", ignoreList, BuildConfig.DEBUG);
            } catch (IOException e) {
                MetaioDebug.printStackTrace(Log.ERROR, e);
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                mProgress.setVisibility(View.GONE);
            } else {
                MetaioDebug.log(Log.ERROR, "Error extrayendo assets, cerrando aplicación...");
                finish();
            }
        }
    }
}
