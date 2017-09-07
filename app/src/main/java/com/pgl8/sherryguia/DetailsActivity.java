package com.pgl8.sherryguia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgl8.sherryguia.models.Vino;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DetailsActivity extends AppCompatActivity {
	private static final String TAG = "DetailsActivity";
	private final String url = "http://92.222.216.247:8080/conexiondb/demo/vinoService/vino/";
	private String urlAction = "http://92.222.216.247:8080/conexiondb/demo/vinoService/accion/";
	ImageView vinoImagen;
	private Vino vino;
	private String wineName;
	private String accountName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		vinoImagen = (ImageView) findViewById(R.id.vinoImage);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		wineName = extras.getString("wineName");
		accountName = extras.getString("accountName");

		// Use AsyncTask execute Method To Prevent ANR Problem
		new WebService().execute(url+wineName);

		// Mandamos acción
		sendActionJSON(accountName, extras.getString("wineName"), "getDetails");

	}

	public void sendActionJSON(String accountName, String wineName, String action){
		JSONObject json = new JSONObject();

		try{
			json.put("username", accountName);
			json.put("wine", wineName);
			json.put("action", action);

			if (json.length() > 0) {
				// Llamada a la clase para mandar el comentario
				new SendActionJsonData().execute(String.valueOf(json));
			}
		}catch (JSONException e){
			e.printStackTrace();
		}
	}

	private class WebService extends AsyncTask<String, Void, Void> {

		private ProgressDialog mDialog = new ProgressDialog(DetailsActivity.this);
		private String message = null;
		private String jsonResponse = null;
		TextView titulo = (TextView) findViewById(R.id.tituloText);
		TextView descripcion = (TextView) findViewById(R.id.descripcionText);
		TextView tipoUva = (TextView) findViewById(R.id.tipoUvaText);
		TextView vinedo = (TextView) findViewById(R.id.vinedoText);
		TextView graduacion = (TextView) findViewById(R.id.graduacionText);
		TextView tipoVino = (TextView) findViewById(R.id.tipoVinoText);
		TextView contenido = (TextView) findViewById(R.id.contenidoText);
		TextView elaboracion = (TextView) findViewById(R.id.elaboracionText);
		TextView notasCata = (TextView) findViewById(R.id.notaCataText);
		TextView consumo = (TextView) findViewById(R.id.consumoText);

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
				Toast.makeText(DetailsActivity.this, "Hubo un problema de conexión.", Toast.LENGTH_LONG).show();
			}else{
				Gson gson = new GsonBuilder().disableHtmlEscaping().create();
				vino = gson.fromJson(jsonResponse, Vino.class);

				titulo.setText(vino.getNombre());
				descripcion.setText(vino.getDescripcion());
				tipoUva.setText(vino.getTipoUva());
				vinedo.setText(vino.getVinedo());
				graduacion.setText(vino.getGraduacion());
				tipoVino.setText(vino.getTipo());
				contenido.setText(vino.getContenido());
				elaboracion.setText(vino.getElaboracion());
				notasCata.setText(vino.getNotaCata());
				consumo.setText(vino.getConsumo());
				Picasso.with(DetailsActivity.this).load(vino.getImagen()).resize(400, 800).into(vinoImagen);
			}
		}
	}

	private class SendActionJsonData extends AsyncTask<String, Void, String> {

		private String urlPost = urlAction;
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
				Log.d(TAG, "doInBackground: "+urlPost);
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
	}
}
