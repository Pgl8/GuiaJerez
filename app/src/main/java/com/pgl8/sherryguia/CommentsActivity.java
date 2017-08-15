package com.pgl8.sherryguia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pgl8.sherryguia.models.Comentario;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CommentsActivity extends AppCompatActivity {

	private static final String TAG = "CommentsActivity";
	private ArrayList<Comentario> comentarios;
	private RecyclerView listaComentarios;
	private CardViewAdapter adaptador;
	private ImageView imageView;
    private String urlGet = "http://192.168.1.130:8080/conexiondb/demo/vinoService/comentarios/";
	public String accountName;
	public String accountPhoto;
    public String wineName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);
		imageView = (ImageView) findViewById(R.id.imageComment);

		Intent intent = getIntent();
		accountName = intent.getStringExtra("accountName");
		accountPhoto = intent.getStringExtra("accountPhoto");
        wineName = intent.getStringExtra("wineName");

		Picasso.with(this).load(accountPhoto).resize(150, 150).transform(new CropCircleTransformation()).into(imageView);

		listaComentarios = (RecyclerView) findViewById(R.id.recyclerView);
		listaComentarios.addItemDecoration(new DividerItemDecoration(this));
		LinearLayoutManager manager = new LinearLayoutManager(this);
		manager.setOrientation(LinearLayoutManager.VERTICAL);
		listaComentarios.setLayoutManager(manager);

        new WebService().execute(urlGet+wineName);

	}

    private class WebService extends AsyncTask<String, Void, Void> {

        private ProgressDialog mDialog = new ProgressDialog(CommentsActivity.this);
        private String message = null;
        private String jsonResponse = null;

        @Override
        protected void onPreExecute() {

            mDialog.setMessage("Cargando...");
            mDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            try {

                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setUseCaches(false);

                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestMethod("GET");

                int httpResponse = con.getResponseCode();
                message = String.valueOf(httpResponse);
                Log.d("doInBackground: ", message);

                if (httpResponse >= HttpURLConnection.HTTP_OK
                        && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                    Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    Log.d("HTTP_OK: ", jsonResponse);
                } else {
                    Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    Log.d("HTTP_ERROR: ", jsonResponse);
                }

            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            mDialog.dismiss();

            if(jsonResponse.isEmpty()){
                Toast.makeText(CommentsActivity.this, "Hubo un problema de conexión.", Toast.LENGTH_LONG).show();
            }else{
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                comentarios = gson.fromJson(jsonResponse, new TypeToken<ArrayList<Comentario>>(){}.getType());

                // Inicialización y asignación del adaptador para el RecyclerView
                adaptador = new CardViewAdapter(CommentsActivity.this, comentarios);
                listaComentarios.setAdapter(adaptador);

            }
        }
    }

}
