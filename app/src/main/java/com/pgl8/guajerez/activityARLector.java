package com.pgl8.guajerez;

import android.content.Intent;
import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.metaio.sdk.ARViewActivity;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.Camera;
import com.metaio.sdk.jni.IGeometry;
import com.metaio.sdk.jni.IMetaioSDKAndroid;
import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.TrackingValues;
import com.metaio.sdk.jni.TrackingValuesVector;
import com.metaio.tools.io.AssetsManager;

import java.io.File;
import java.security.Policy;


public class activityARLector extends ARViewActivity {

    private Button button1;
    /**
     * Currently loaded tracking configuration file
     */
    File trackingConfigFile;

    private MetaioSDKCallbackHandler mCallbackHandler;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCallbackHandler = new MetaioSDKCallbackHandler();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mCallbackHandler.delete();
        mCallbackHandler = null;
    }

    @Override
    protected int getGUILayout()
    {
        return R.layout.activity_arlector;
    }

    @Override
    protected IMetaioSDKCallback getMetaioSDKCallbackHandler() {
        return mCallbackHandler;
    }

    @Override
    protected void loadContents(){
        try{
            // Load the desired tracking configuration
            trackingConfigFile = AssetsManager.getAssetPathAsFile(getApplicationContext(),
                    "tracking/TrackingData_MarkerlessFast.xml");
            MetaioDebug.log("Tracking Config path = "+trackingConfigFile);

            boolean result = metaioSDK.setTrackingConfiguration(trackingConfigFile);
            MetaioDebug.log("Picture Marker tracking data loaded: " + result);

            // Load all the geometries. First - Model
            /*final File metaioManModel = AssetsManager.getAssetPathAsFile(getApplicationContext(), "TutorialTrackingSamples/Assets/metaioman.md2");
            if (metaioManModel != null)
            {
                mMetaioMan = metaioSDK.createGeometry(metaioManModel);
                if (mMetaioMan != null)
                {
                    // Set geometry properties
                    mMetaioMan.setScale(4f);
                    MetaioDebug.log("Loaded geometry "+metaioManModel);
                }
                else
                    MetaioDebug.log(Log.ERROR, "Error loading geometry: "+metaioManModel);
            }*/


        }
        catch (Exception e){
            MetaioDebug.log(Log.ERROR, "Error loading contents!");
            MetaioDebug.printStackTrace(Log.ERROR, e);
        }
    }

    @Override
    protected void onGeometryTouched(IGeometry geometry) {

    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        //super.onSurfaceChanged(width, height);
        android.hardware.Camera camera = IMetaioSDKAndroid.getCamera(this);
        android.hardware.Camera.Parameters param = camera.getParameters();
        param.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(param);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_arlector, menu);
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
    }*/

    final class MetaioSDKCallbackHandler extends IMetaioSDKCallback{

        @Override
        public void onSDKReady() {
            // show GUI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mGUIView.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void onTrackingEvent(TrackingValuesVector trackingValues)
        {
            super.onTrackingEvent(trackingValues);
            for(int i=0; i<trackingValues.size(); i++){
                final TrackingValues v = trackingValues.get(i);
                if (v.isTrackingState()){
                    MetaioDebug.log("I AM TRACKING");
                    final String raw_data = v.getAdditionalValues();
                    MetaioDebug.log("I HAVE ADDITIONAL VALUES");
                    if(raw_data != null) {
                        MetaioDebug.log("Marker Scanned");
                        MetaioDebug.log("Marker ID: "+ v.getCosName());
                        //button1 = (Button) findViewById(R.id.button1);
                        //button1.setVisibility(View.VISIBLE);
                        //button1.setClickable(true);
                        //Animation shake = AnimationUtils.loadAnimation(getBaseContext(), R.anim.cycle);
                        //button1.startAnimation(shake);
                        /*runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MetaioDebug.log("Marker Scanned");
                                button1.setVisibility(View.VISIBLE);
                                button1.setClickable(true);
                                Animation shake = AnimationUtils.loadAnimation(getBaseContext(), R.anim.cycle);
                                button1.startAnimation(shake);
                                final int position = 7;
                                try {
                                    Intent intent = new Intent(getBaseContext(), activityInfoVinos.class);
                                    intent.putExtra("posicion", position);
                                    startActivity(intent);
                                }catch (final Exception ex){
                                    MetaioDebug.log("Exception in runnable action");
                                }
                            }
                        });*/
                    }
                }
            }
        }

    }
}
