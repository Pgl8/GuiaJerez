package com.pgl8.guajerez;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class activityInfoLugares extends ActionBarActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_vinos);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_row, getData());
        gridView.setAdapter(gridAdapter);

        setTitle("");
        Bundle bundle = getIntent().getExtras();
        new obtenerHTML(bundle.getInt("posicion")).execute();
    }

    // Funci�n que prepara las im�genes para el adaptador
    /**
     *REVISAR
     */
    private ArrayList<Imagen> getData() {
        final ArrayList<Imagen> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new Imagen(bitmap, "Image#" + i));
        }
        return imageItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_info_lugares, menu);
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

    //Necesita ser una tarea as�ncrona
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
            // variable que contendr� el resultado del parser
            Elements contenido;
            try{
                if(getTitulo().equals("Palo Cortado")){
                    // conexi�n con servidor
                    Document doc = Jsoup.connect("http://www.sherry.org/es/fichapaloc.cfm").get();
                    // obtenci�n del fragmento de texto que queremos
                    contenido = doc.select("div.blanco");
                }else{
                    // conexi�n con servidor
                    Document doc = Jsoup.connect("http://www.sherry.org/es/ficha" + getTitulo()
                            .toLowerCase().replace(" ", "") + ".cfm").get();
                    // obtenci�n del fragmento de texto que queremos
                    contenido = doc.select("div.blanco");
                }
            }catch(IOException e){
                e.printStackTrace();
                Log.e(TAG, "Error al cargar contenido", e);
                // se muestra un dialog de error de conexi�n
                new AlertDialog.Builder(getApplicationContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Error de conexi�n")
                        .setMessage("Ha sido imposible conectar con el servidor, int�ntalo m�s tarde")
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();

                return null;
            }

            return contenido;
        }

        protected void onPostExecute(Elements result) {
            if (result != null) {
                //Inicializamos el objeto de la clase vino
                Vino vino;
                //Asignamos el t�tulo de la activity
                setTitle(getTitulo());
                //Localizamos e inicializamos los elementos de la UI
                TextView txt1 = (TextView) findViewById(R.id.textView3);
                TextView txt2 = (TextView) findViewById(R.id.tvWeb);
                TextView txt3 = (TextView) findViewById(R.id.textView7);
                TextView txt4 = (TextView) findViewById(R.id.textView9);

                //Creamos el objeto correspondiente con un poco de formateo del parser
                if(getTitulo().equals("Amontillado")) {
                    vino = new Vino(result.get(0).text() + " " + result.get(1).text(),
                            result.get(2).text() + " " + result.get(3).text(),
                            result.get(5).text(),
                            Jsoup.parse(result.get(4).html().replaceAll("(?i)<br[^>]*>", "<pre>\n</pre>")).text());
                }else if(getTitulo().equals("Oloroso")){
                    vino = new Vino(result.get(0).text() + " " + result.get(1).text(),
                            result.get(2).text(), result.get(4).text(),
                            Jsoup.parse(result.get(3).html().replaceAll("(?i)<br[^>]*>", "<pre>\n</pre>")).text());
                }else{
                    vino = new Vino(result.get(0).text(), result.get(1).text(), result.get(3).text(),
                            Jsoup.parse(result.get(2).html().replaceAll("(?i)<br[^>]*>", "<pre>\n</pre>")).text());
                }

                //Asignamos los elementos al objeto de la UI determinado
                txt1.setText(vino.getNotasCata());
                txt2.setText(vino.getElaboracion());
                txt3.setText(vino.getConsumo());
                txt4.setText(vino.getParametros());

                //Quitamos Progress circle
                linlaHeaderProgress.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        }
    }
}
