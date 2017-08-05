package com.pgl8.sherryguia;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgl8.sherryguia.models.Vino;

import org.w3c.dom.Text;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DetailsActivity extends AppCompatActivity {
	// URL local, cambiar a servidor.
	private final String url = "http://192.168.1.34:8080/conexiondb/demo/vinoService/vino/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		String wine = extras.getString("wine");

		// Use AsyncTask execute Method To Prevent ANR Problem
		new WebService().execute(url+wine);

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
				Vino vino = gson.fromJson(jsonResponse, Vino.class);

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
			}
		}
	}
}
