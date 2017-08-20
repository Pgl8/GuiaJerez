package com.pgl8.sherryguia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pgl8.sherryguia.models.Comentario;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CommentsActivity extends AppCompatActivity {

	private static final String TAG = "CommentsActivity";
	private ArrayList<Comentario> comentarios;
	private RecyclerView listaComentarios;
	private CardViewAdapter adaptador;
	private ImageView imageView;
    private String urlGet = "http://92.222.216.247:8080/conexiondb/demo/vinoService/comentarios/";
	private Button btnEnviar;
	private RatingBar ratingBar;
	private EditText comentarioText;
	public String accountName;
	public String accountPhoto;
    public String wineName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);
		imageView = (ImageView) findViewById(R.id.imageComment);
		btnEnviar = (Button) findViewById(R.id.buttonEnviar);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		comentarioText = (EditText) findViewById(R.id.comentarioText);

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

		btnEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(ratingBar.getRating() < 1 || TextUtils.isEmpty(comentarioText.getText().toString())){
					comentarioText.setError("Debes escribir un comentario y puntuar.");
				}else{
					sendCommentJSON(wineName, accountName, comentarioText.getText().toString(), ratingBar.getRating());
				}
			}
		});

	}

	public void sendCommentJSON(String wineName, String accountName, String comentario, float puntuacion){
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fecha = sdf.format(new Date());

		try{
			json.put("vino", wineName);
			json.put("usuario", accountName);
			json.put("comentario", comentario);
			json.put("puntuacion", puntuacion);
			json.put("fecha", fecha);

			if (json.length() > 0) {
				// Llamada a la clase para mandar el comentario
				new SendCommentJsonData().execute(String.valueOf(json));
				Log.d(TAG, "sendCommentJSON: " + json);
			}
		}catch (JSONException e){
			e.printStackTrace();
		}
	}


	// Clase interna para obtener el listado de comentarios sobre un vino
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

	private class SendCommentJsonData extends AsyncTask<String, Void, String> {

		private String urlPost = "http://92.222.216.247:8080/conexiondb/demo/vinoService/comentario";
		private String jsonResponse;
		private int httpResponse = 0;

		@Override
		protected String doInBackground(String... params) {

			try{
				URL url = new URL(urlPost);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setUseCaches(false);
				con.setDoOutput(true);
				con.setDoInput(true);

				con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				con.setRequestMethod("POST");

				Log.d(TAG, "doInBackground: "+params[0]);

				byte[] sendBytes = params[0].getBytes("UTF-8");
				con.setFixedLengthStreamingMode(sendBytes.length);

				OutputStream outputStream = con.getOutputStream();
				outputStream.write(sendBytes);

				httpResponse = con.getResponseCode();

				Log.d(TAG, "doInBackground: httpResponse: "+httpResponse);
				if (httpResponse >= HttpURLConnection.HTTP_OK
						&& httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
					Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
					jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
					scanner.close();
				} else {
					Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
					jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
					scanner.close();
				}

			} catch (Throwable t) {
				t.printStackTrace();
			}

			return jsonResponse;
		}

		@Override
		protected void onPostExecute(String s) {
			if(httpResponse == 204) {
				Toast.makeText(CommentsActivity.this, "El comentario ha sido enviado.", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(CommentsActivity.this, "Hubo un error al enviar el comentario.", Toast.LENGTH_LONG).show();
			}
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
			);
			comentarioText.getText().clear();
			ratingBar.setRating(0F);
			new WebService().execute(urlGet+wineName);
		}
	}
}
