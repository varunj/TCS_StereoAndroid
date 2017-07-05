package io.github.varunj.tcs_stereoimages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stereo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            URL_LEFT = "http://" + extras.getString(IDENT_URL_IP_LEFT) + extras.getString(IDENT_URL_PATH);
            URL_RIGHT = "http://" + extras.getString(IDENT_URL_IP_RIGHT) + extras.getString(IDENT_URL_PATH);
        }


        new Thread(new Runnable() {
            public void run() {
                final ImageView bmImage = (ImageView) findViewById(R.id.home_imageLeft);
                Bitmap mIcon11 = null;

                while (true) {
                try {
                    InputStream in = new java.net.URL(URL_LEFT).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                final Bitmap finalMIcon1 = mIcon11;
                bmImage.post(new Runnable() {
                    public void run() {
                        bmImage.setImageBitmap(finalMIcon1);
                    }
                });}
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                final ImageView bmImage = (ImageView) findViewById(R.id.home_imageRight);
                Bitmap mIcon11 = null;

                while (true) {
                try {
                    InputStream in = new java.net.URL(URL_RIGHT).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                final Bitmap finalMIcon1 = mIcon11;
                bmImage.post(new Runnable() {
                    public void run() {
                        bmImage.setImageBitmap(finalMIcon1);
                    }
                }); }
            }
        }).start();

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
