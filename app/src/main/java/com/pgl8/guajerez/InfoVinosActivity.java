package com.pgl8.guajerez;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class InfoVinosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_vinos);
        setTitle("");
        Bundle bundle = getIntent().getExtras();
        new obtenerHTML(bundle.getInt("posicion")).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_vinos, menu);
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

    //Necesita ser una tarea asíncrona
    //Haremos una clase interna para ello
    class obtenerHTML extends AsyncTask<String, Void, Elements> {
        private static final String TAG = "Principal";
        private String[] vinos = {"Fino", "Manzanilla", "Amontillado", "Oloroso", "Palo Cortado", "Pale Cream",
                "Medium", "Cream", "Moscatel", "Pedro Ximenez"};
        private String titulo;
        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

        public obtenerHTML(int posicion) {
            this.titulo = vinos[posicion];
        }

        /*final int getPosicion() {
            return posicion;
        }*/

        final String getTitulo() {
            return titulo;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            linlaHeaderProgress.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }

        @Override
        protected Elements doInBackground(String... params) {
            //Método de obtención con JSOUP
            Elements contenido;
            try {
                if (getTitulo().equals("Palo Cortado")) {
                    Document doc = Jsoup.connect("http://www.sherry.org/es/fichapaloc.cfm").get();
                    contenido = doc.select("div.blanco");
                } else {
                    Document doc = Jsoup.connect("http://www.sherry.org/es/ficha" + getTitulo().toLowerCase().replace(" ", "") + ".cfm").get();
                    contenido = doc.select("div.blanco");
                }
                //Log.v(TAG, "http://www.sherry.org/es/ficha" + titulo.toLowerCase().replace(" ", "") + ".cfm");

            } catch (IOException e) {
                //e.printStackTrace();
                Log.e(TAG, "Error al cargar contenido", e);
                return null;
            }

            return contenido;
        }

        protected void onPostExecute(Elements result) {
            if (result != null) {
                //Asignamos el título de la activity
                setTitle(getTitulo());
                //Localizamos e inicializamos los elementos de la UI
                TextView txt1 = (TextView) findViewById(R.id.textView3);
                TextView txt2 = (TextView) findViewById(R.id.textView5);
                TextView txt3 = (TextView) findViewById(R.id.textView7);
                TextView txt4 = (TextView) findViewById(R.id.textView9);

                //Asignamos los elementos al objeto de la UI determinado
                txt1.setText(result.get(0).text());
                txt2.setText(result.get(1).text());
                txt3.setText(result.get(3).text());

                //Formateo para respetar saltos de línea en datos analíticos
                txt4.setText(Jsoup.parse(result.get(2).html().replaceAll("(?i)<br[^>]*>",
                        "<pre>\n</pre>")).text());

                //Quitamos Progress circle
                linlaHeaderProgress.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        }
    }
}
