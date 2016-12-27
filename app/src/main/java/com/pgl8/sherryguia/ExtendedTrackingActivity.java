package com.pgl8.sherryguia;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wikitude.WikitudeSDK;
import com.wikitude.WikitudeSDKStartupConfiguration;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.common.tracking.RecognizedTarget;
import com.wikitude.rendering.ExternalRendering;
import com.pgl8.sherryguia.CustomSurfaceView;
import com.pgl8.sherryguia.Driver;
import com.pgl8.sherryguia.GLRendererExtendedTracking;
import com.wikitude.tracker.ClientTracker;
import com.wikitude.tracker.ClientTrackerEventListener;
import com.wikitude.tracker.Tracker;

public class ExtendedTrackingActivity extends AppCompatActivity implements ClientTrackerEventListener, ExternalRendering {

	private static final String TAG = "ExtendedTracking";

	private WikitudeSDK _wikitudeSDK;
	private CustomSurfaceView _view;
	private Driver _driver;
	private GLRendererExtendedTracking _glRenderer;
	//private String _wikiKey = "qSlMsrJUjSkLnjnMiwIMdzmW4e/iJc08wkUZe6rcYgqhH629NlJ8g7AhC4iiphuSMymZd55w+S7hvNBV2tRW0tFbuk9MKo2arb8xEF07nLUR2dWmNt8DP4jY7B/Vp/eh2C8LVySj4HnZ2lQsdooS7jFRfUcAl6qlrg2SGKn3cRpTYWx0ZWRfX8x+Hdqn7XqIjYT9x4Pc71KtqAAO2iezhVR7QQNqj7R3nyQo4NZo2gOJ4iaap+OpgT9AKmSIUWfkE1NIaPUaX4w6LiC1EaDGA5nLhx6hVgL6zyJlOvYlsDfY8dWfYenDCoV5plVbvMhkYuCPACBsgPoPG+VMMz/1ic2ox/GdEwcivm1FB1Kv3X47v5kp7YJqFopiZ1jrWEDh14GIz5KZhz4W2VCzxAb8a3ZMK8A+62gav/kMSs3ZmKHoPMV/iVw9rV8QC6GPmir9340L7S2P/zQFj50pAY/4XwVuu7aLRnan8D66Wk8xLG9658JuwxTJd8ivTgLGB3kbJWWLmQ2R27e3bNxZ7Lw8aU3fTh+9VbE6Sm+BIWcZLCx0FqgbMe33unClepUQPqG1wXhP3UjQyfXY7Jm+hIbGPe06xM61O22qovBqATz62CX1C+2rtY19Sj47+sWAeogLRMBCUDgQkMQQjGAM2QXkh5yyLT3vt/FwScJusjDWtco=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_wikitudeSDK = new WikitudeSDK(this);
		WikitudeSDKStartupConfiguration startupConfiguration = new WikitudeSDKStartupConfiguration(WikitudeSDKConstants.WIKITUDE_SDK_KEY, CameraSettings.CameraPosition.BACK, CameraSettings.CameraFocusMode.CONTINUOUS);
		_wikitudeSDK.onCreate(getApplicationContext(), this, startupConfiguration);
		ClientTracker tracker = _wikitudeSDK.getTrackerManager().create2dClientTracker("file:///android_asset/tracker.wtc", new String[]{"*"});
		tracker.registerTrackerEventListener(this);
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
				EditText trackingQualityIndicator = (EditText) findViewById(R.id.tracking_quality_indicator);
				trackingQualityIndicator.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public void onExtendedTrackingQualityUpdate(final Tracker tracker_, final String targetName_, final int oldTrackingQuality_, final int newTrackingQuality_) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				EditText trackingQualityIndicator = (EditText) findViewById(R.id.tracking_quality_indicator);
				switch (newTrackingQuality_) {
					case -1:
						trackingQualityIndicator.setBackgroundColor(Color.parseColor("#FF3420"));
						trackingQualityIndicator.setText(R.string.tracking_quality_indicator_bad);
						break;
					case 0:
						trackingQualityIndicator.setBackgroundColor(Color.parseColor("#FFD900"));
						trackingQualityIndicator.setText(R.string.tracking_quality_indicator_average);
						break;
					default:
						trackingQualityIndicator.setBackgroundColor(Color.parseColor("#6BFF00"));
						trackingQualityIndicator.setText(R.string.tracking_quality_indicator_good);
				}
				trackingQualityIndicator.setVisibility(View.VISIBLE);
			}
		});
	}
}
