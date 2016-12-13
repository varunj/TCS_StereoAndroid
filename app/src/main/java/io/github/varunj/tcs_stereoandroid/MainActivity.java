package io.github.varunj.tcs_stereoandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Varun on 13-12-2016.
 */

public class MainActivity extends AppCompatActivity {

    private EditText home_ip_left;
    private EditText home_ip_right;
    private EditText home_path;
    private Button home_button;
    private static final String IDENT_URL_IP_LEFT = "io.github.varunj.tcs_stereoandroid.urlipleft";
    private static final String IDENT_URL_IP_RIGHT = "iio.github.varunj.tcs_stereoandroid.urlipright";
    private static final String IDENT_URL_PATH = "io.github.varunj.tcs_stereoandroid.urlpath";
    static final int STEREO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get data
        home_ip_left = (EditText) findViewById(R.id.home_ip_left);
        home_ip_right = (EditText) findViewById(R.id.home_ip_right);
        home_path = (EditText) findViewById(R.id.home_path);

        //show stereo
        home_button = (Button) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!(home_ip_left.getText().toString().equals("") || home_ip_right.getText().toString().equals("")
                        || home_path.getText().toString().equals(""))) {
                    Intent intent = new Intent(getApplicationContext(), StereoActivity.class);
                    intent.putExtra(IDENT_URL_IP_LEFT, home_ip_left.getText().toString());
                    intent.putExtra(IDENT_URL_IP_RIGHT, home_ip_right.getText().toString());
                    intent.putExtra(IDENT_URL_PATH, home_path.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    startActivityForResult(intent, STEREO_REQUEST);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Enter Valid Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
