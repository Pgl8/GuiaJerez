package com.pgl8.sherryguia;

import android.content.Intent;
import android.location.Location;
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

import com.wikitude.WikitudeSDK;
import com.wikitude.WikitudeSDKStartupConfiguration;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.common.tracking.RecognizedTarget;
import com.wikitude.rendering.ExternalRendering;
import com.wikitude.tracker.ClientTracker;
import com.wikitude.tracker.ClientTrackerEventListener;
import com.wikitude.tracker.Tracker;

public class ExtendedTrackingActivity extends AppCompatActivity implements ClientTrackerEventListener, ExternalRendering {

	private static final String TAG = "ExtendedTracking";

	private WikitudeSDK _wikitudeSDK;
	private CustomSurfaceView _view;
	private Driver _driver;
	private GLRendererExtendedTracking _glRenderer;
	private Location mLocation;
	private ProgressBar progressBar;
	private TextView wineName;
	private Button button1;
	private Button button2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_wikitudeSDK = new WikitudeSDK(this);
		WikitudeSDKStartupConfiguration startupConfiguration = new WikitudeSDKStartupConfiguration(WikitudeSDKConstants.WIKITUDE_SDK_KEY, CameraSettings.CameraPosition.BACK, CameraSettings.CameraFocusMode.CONTINUOUS);
		_wikitudeSDK.onCreate(getApplicationContext(), this, startupConfiguration);
		ClientTracker tracker = _wikitudeSDK.getTrackerManager().create2dClientTracker("file:///android_asset/tracker2.wtc");
		tracker.registerTrackerEventListener(this);

		GPSTracker gps = new GPSTracker(this);
		if(gps.canGetLocation()){
			mLocation = gps.loc;
			//double longitude = gps.getLongitude();
			//double latitude = gps.getLatitude();
			Toast.makeText(this, "Location: " + gps.getLatitude() + ", " + gps.getLongitude(), Toast.LENGTH_LONG).show();
			Log.d("Longitude: ", String.valueOf(gps.getLongitude()));
			Log.d("Latitude: ", String.valueOf(gps.getLatitude()));
		}

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		wineName = (TextView) findViewById(R.id.wineName);
		button1 = (Button) findViewById(R.id.button);
		button2 = (Button) findViewById(R.id.button2);

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
				intent.putExtra("wine", targetName_);
				startActivity(intent);
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
				progressBar.setVisibility(View.INVISIBLE);
				button1.setVisibility(View.INVISIBLE);
				button2.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public void onExtendedTrackingQualityUpdate(final Tracker tracker_, final String targetName_, final int oldTrackingQuality_, final int newTrackingQuality_) {
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
				progressBar.setVisibility(View.VISIBLE);
				button1.setVisibility(View.VISIBLE);
				button2.setVisibility(View.VISIBLE);
			}
		});
	}


}
