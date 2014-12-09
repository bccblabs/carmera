package veme.cario.com.CARmera;

/**
 * Created by bski on 11/5/14.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * UserActivity which displays a registration screen to the user.
 */

public class WelcomeActivity extends Activity {

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Log in button click handler
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });

        // Sign up button click handler
        Button signUpButton = (Button) findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
            }
        });

        Button loginFacebook = (Button) findViewById(R.id.login_facebook_btn);
        loginFacebook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick();
            }
        });
        ParseUser current_user = ParseUser.getCurrentUser();
        if (current_user != null && ParseFacebookUtils.isLinked(current_user)) {
            startActivity(new Intent(WelcomeActivity.this, ProfileActivity.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    private void onLoginClick() {
        progressDialog = ProgressDialog.show(WelcomeActivity.this, "",
                "Loggin in using Facebook", true);
        List<String> permissions = Arrays.asList("public_profile",
                "user_friends", "user_location", "email");

        ParseFacebookUtils.logIn(permissions, this,new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                progressDialog.dismiss();
                if (parseUser == null) {
                    Toast toast = Toast.makeText(WelcomeActivity.this,
                            "Oops, can't loggin through facebook", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startActivity(new Intent(WelcomeActivity.this, ProfileActivity.class));
                }
            }

        });

    }
}