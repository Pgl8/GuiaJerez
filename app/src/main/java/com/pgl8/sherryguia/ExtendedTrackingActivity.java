package com.pgl8.sherryguia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgl8.sherryguia.models.Vino;
import com.squareup.picasso.Picasso;
import com.wikitude.WikitudeSDK;
import com.wikitude.WikitudeSDKStartupConfiguration;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.common.tracking.RecognizedTarget;
import com.wikitude.rendering.ExternalRendering;
import com.wikitude.tracker.ClientTracker;
import com.wikitude.tracker.ClientTrackerEventListener;
import com.wikitude.tracker.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ExtendedTrackingActivity extends AppCompatActivity implements ClientTrackerEventListener, ExternalRendering {

	private static final String TAG = "ExtendedTracking";

	private WikitudeSDK _wikitudeSDK;
	private CustomSurfaceView _view;
	private Driver _driver;
	private GLRendererExtendedTracking _glRenderer;
	private ProgressBar progressBar;
	private TextView wineName;
	private Button button1;
	private Button button2;
	private Button button3;
	public String accountName;
	public String accountPhoto;
	private final String url = "http://92.222.216.247:8080/conexiondb/demo/vinoService/vino/";
	private String urlAction = "http://92.222.216.247:8080/conexiondb/demo/vinoService/accion/";
	private Vino vino;
	private String tiendaUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_wikitudeSDK = new WikitudeSDK(this);
		WikitudeSDKStartupConfiguration startupConfiguration = new WikitudeSDKStartupConfiguration(WikitudeSDKConstants.WIKITUDE_SDK_KEY, CameraSettings.CameraPosition.BACK, CameraSettings.CameraFocusMode.CONTINUOUS);
		_wikitudeSDK.onCreate(getApplicationContext(), this, startupConfiguration);
		ClientTracker tracker = _wikitudeSDK.getTrackerManager().create2dClientTracker("file:///android_asset/tracker3.wtc");
		tracker.registerTrackerEventListener(this);

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		wineName = (TextView) findViewById(R.id.wineName);
		button1 = (Button) findViewById(R.id.button);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);

		Intent intent = getIntent();
		accountName = intent.getStringExtra("accountName");
		accountPhoto = intent.getStringExtra("accountPhoto");


	}

	@Override
	protected void onResume() {
		super.onResume();
		_wikitudeSDK.onResume();
		_view.onResume();
		_driver.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		_wikitudeSDK.onPause();
		_view.onPause();
		_driver.stop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		_wikitudeSDK.onDestroy();
	}

	@Override
	public void onRenderExtensionCreated(final RenderExtension renderExtension_) {
		_glRenderer = new GLRendererExtendedTracking(renderExtension_);
		_view = new CustomSurfaceView(getApplicationContext(), _glRenderer);
		_driver = new Driver(_view, 30);

		FrameLayout viewHolder = new FrameLayout(getApplicationContext());
		setContentView(viewHolder);

		viewHolder.addView(_view);

		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		RelativeLayout trackingQualityIndicator = (RelativeLayout) inflater.inflate(R.layout.activity_extended_tracking, null);
		viewHolder.addView(trackingQualityIndicator);
	}

	@Override
	public void onErrorLoading(final ClientTracker clientTracker_, final String errorMessage_) {
		Log.v(TAG, "onErrorLoading: " + errorMessage_);
	}

	@Override
	public void onTrackerFinishedLoading(final ClientTracker clientTracker_, final String trackerFilePath_) {

	}

	@Override
	public void onTargetRecognized(final Tracker tracker_, final String targetName_) {
		Log.d("onTargetRecognized", targetName_);

		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ExtendedTrackingActivity.this, DetailsActivity.class);
				intent.putExtra("wineName", targetName_);
				intent.putExtra("accountName", accountName);
				intent.putExtra("accountPhoto", accountPhoto);
				startActivity(intent);
			}
		});

		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent1 = new Intent(ExtendedTrackingActivity.this, CommentsActivity.class);
				intent1.putExtra("accountName", accountName);
				intent1.putExtra("accountPhoto", accountPhoto);
                intent1.putExtra("wineName", targetName_);
				startActivity(intent1);
			}
		});

		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Mandamos acción
				sendActionJSON(accountName, (String) wineName.getText(), "getShopURL");
				new WebService().execute(url+targetName_);

			}
		});
	}

	@Override
	public void onTracking(final Tracker tracker_, final RecognizedTarget recognizedTarget_) {
		_glRenderer.setCurrentlyRecognizedTarget(recognizedTarget_);
	}

	@Override
	public void onTargetLost(final Tracker tracker_, final String targetName_) {
		_glRenderer.setCurrentlyRecognizedTarget(null);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				wineName.setVisibility(View.INVISIBLE);
				//progressBar.setVisibility(View.INVISIBLE);
				button1.setVisibility(View.INVISIBLE);
				button2.setVisibility(View.INVISIBLE);
				button3.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public void onExtendedTrackingQualityUpdate(final Tracker tracker_, final String targetName_, final int oldTrackingQuality_, final int newTrackingQuality_) {
		Log.d(TAG, "onExtendedTrackingQualityUpdateOld: " + oldTrackingQuality_);
		Log.d(TAG, "onExtendedTrackingQualityUpdateNew: " + newTrackingQuality_);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				assert progressBar != null;
				progressBar.setMax(3);

				switch (newTrackingQuality_) {
					case -1:

						progressBar.setProgress(1);
						break;

					case 0:

						progressBar.setProgress(2);
						break;

					default:

						progressBar.setProgress(3);

				}

				wineName.setVisibility(View.VISIBLE);
				wineName.setText(targetName_);
				//progressBar.setVisibility(View.VISIBLE);
				button1.setVisibility(View.VISIBLE);
				button2.setVisibility(View.VISIBLE);
				button3.setVisibility(View.VISIBLE);
			}
		});
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

	private class WebService extends AsyncTask<String, Void, String> {

		private ProgressDialog mDialog = new ProgressDialog(ExtendedTrackingActivity.this);
		private String message = null;
		private String jsonResponse = null;

		@Override
		protected void onPreExecute() {

			mDialog.setMessage("Cargando...");
			mDialog.show();
		}

		@Override
		protected String doInBackground(String... strings) {

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

			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			vino = gson.fromJson(jsonResponse, Vino.class);
			tiendaUrl = vino.getUrl();

			return jsonResponse;
		}

		@Override
		protected void onPostExecute(String jsonResponse) {

			if(tiendaUrl != null) {
				mDialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tiendaUrl));
				startActivity(intent);
			}

			if(jsonResponse.isEmpty())
				Toast.makeText(ExtendedTrackingActivity.this, "Hubo un problema de conexión.", Toast.LENGTH_LONG).show();

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
