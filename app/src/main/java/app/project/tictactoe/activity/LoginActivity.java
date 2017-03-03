package app.project.tictactoe.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import app.project.tictactoe.R;
import app.project.tictactoe.Utils.TConst;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    private TextView textView = null;
    private SharedPreferences.Editor se = null;
    private String mob = "0000000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_PHONE_STATE}, 1);

        se = getSharedPreferences("cache", 0).edit();
        textView = (TextView) findViewById(R.id.txt_otp);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TConst.TWITTER_KEY, TConst.TWITTER_SECRET);
        Digits.Builder digitsBuilder = new Digits.Builder().withTheme(R.style.CustomDigitsTheme);
        Fabric.with(this, new TwitterCore(authConfig), digitsBuilder.build());

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {

                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
                se.putString("mob", mob.trim());
                se.commit();
                se = null;
                LoginActivity.this.finish();
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });


    }
}
