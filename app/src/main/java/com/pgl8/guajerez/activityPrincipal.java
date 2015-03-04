package com.pgl8.guajerez;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.metaio.sdk.MetaioDebug;
import com.metaio.tools.io.AssetsManager;

import java.io.IOException;


public class activityPrincipal extends ActionBarActivity {
    /**
     * Task that will extract all the assets
     */
    AssetsExtracter mTask;

    /**
     * Progress view
     */
    View mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Habilita los mensajes de debug de metaio
        MetaioDebug.enableLogging(BuildConfig.DEBUG);

        // Identificamos la barra de progreso
        mProgress = findViewById(R.id.progress);

        // Extraemos los assets
        mTask = new AssetsExtracter();
        mTask.execute(0);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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

    //Inicia la actividad de vinos
    public void activityVinos(View view) {
        startActivity(new Intent(this, activityVinos.class));
    }

    //Inicia la actividad de mapa
    public void activityMapa(View view) {
        startActivity(new Intent(this, activityMapa.class));
    }

    //Inicia la actividad de realidad aumentada
    public void activityAR(View view) {
        startActivity(new Intent(this, activityAR.class));
    }

    //Clase interna para la extracción de assets para metaio SDK
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
