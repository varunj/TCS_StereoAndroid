package io.github.varunj.tcs_stereoandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegSurfaceView;
import com.github.niqdev.mjpeg.MjpegView;

/**
 * Created by Varun on 13-12-2016.
 */

public class StereoActivity extends AppCompatActivity{

    private static final String IDENT_URL_IP_LEFT = "io.github.varunj.tcs_stereoandroid.urlipleft";
    private static final String IDENT_URL_IP_RIGHT = "iio.github.varunj.tcs_stereoandroid.urlipright";
    private static final String IDENT_URL_PATH = "io.github.varunj.tcs_stereoandroid.urlpath";
    private static String URL_LEFT= "";
    private static String URL_RIGHT = "";
    static final int STEREO_REQUEST = 1;
    private static final int TIMEOUT = 5;

    MjpegView home_imageLeft;
    MjpegView home_imageRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stereo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            URL_LEFT = "http://" + extras.getString(IDENT_URL_IP_LEFT) + extras.getString(IDENT_URL_PATH);
            URL_RIGHT = "http://" + extras.getString(IDENT_URL_IP_RIGHT) + extras.getString(IDENT_URL_PATH);
        }

        home_imageLeft = (MjpegSurfaceView) findViewById(R.id.home_imageLeft);
        home_imageRight = (MjpegSurfaceView) findViewById(R.id.home_imageRight);
    }

    private void loadFeeds() {
        Mjpeg.newInstance()
                .open(URL_LEFT, TIMEOUT)
                .subscribe(
                        inputStream -> {
                            home_imageLeft.setSource(inputStream);
                            home_imageLeft.setDisplayMode(DisplayMode.BEST_FIT); //DisplayMode.FULLSCREEN?
                            home_imageLeft.showFps(true);
                        },
                        throwable -> {
                            Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                        });
        Mjpeg.newInstance()
                .open(URL_RIGHT, TIMEOUT)
                .subscribe(
                        inputStream -> {
                            home_imageRight.setSource(inputStream);
                            home_imageRight.setDisplayMode(DisplayMode.BEST_FIT); //DisplayMode.FULLSCREEN?
                            home_imageRight.showFps(true);
                        },
                        throwable -> {
                            Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFeeds();
    }

    @Override
    protected void onPause() {
        super.onPause();
        home_imageLeft.stopPlayback();
        home_imageRight.stopPlayback();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        final View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        setResult(Activity.RESULT_OK, intent);
        startActivityForResult(intent, STEREO_REQUEST);
        finish();
    }
}
