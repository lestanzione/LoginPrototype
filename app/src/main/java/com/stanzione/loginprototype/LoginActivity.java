package com.stanzione.loginprototype;

import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;

    private Button loginSignUpButton;
    private TextView titleTextView;

    private CardView signUpCardView;
    private EditText signUpUsernameEditText;
    private EditText signUpEmailEditText;
    private EditText signUpPasswordEditText;
    private Button signUpButton;

    private SignUpRequest signUpRequest;

    private boolean isSignUpRunning = false;

    Animation animFadeIn;
    Animation animFadeOut;
    Animation animExplode;

    private enum LoginState {
        STATE_LOGO,
        STATE_SIGNIN,
        STATE_SIGNUP
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        animExplode = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.explode);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        loginSignUpButton = (Button) findViewById(R.id.loginSignUpButton);
        titleTextView = (TextView) findViewById(R.id.spreadTitle);

        signUpCardView = (CardView) findViewById(R.id.signUpCardView);
        signUpUsernameEditText = (EditText) findViewById(R.id.signUpUsernameEditText);
        signUpEmailEditText = (EditText) findViewById(R.id.signUpEmailEditText);
        signUpPasswordEditText = (EditText) findViewById(R.id.signUpPasswordEditText);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        loginSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUp();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSignUp();
            }
        });

    }

    private void showSignUp(){

        if(signUpRequest != null) {
            signUpRequest.cancel(true);
        }

        titleTextView.startAnimation(animExplode);

        titleTextView.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                signUpCardView.startAnimation(animFadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void doSignUp(){

        if(!isUsernameValid()){
            Snackbar.make(coordinatorLayout, "Username must have at least 5 characters", Snackbar.LENGTH_SHORT).show();
        }
        else if(!isEmailValid()){
            Snackbar.make(coordinatorLayout, "Enter a valid email", Snackbar.LENGTH_SHORT).show();
        }
        else if(!isPasswordValid()){
            Snackbar.make(coordinatorLayout, "Enter a password", Snackbar.LENGTH_SHORT).show();
        }
        else{
            signUpRequest = new SignUpRequest();
            signUpRequest.execute(new String[]{getUsername(), getEmail(), getPassword()});
        }

    }

    private boolean isUsernameValid(){
        return getUsername().length() >= 5;
    }

    private boolean isEmailValid(){
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }

    private boolean isPasswordValid(){
        return !getPassword().isEmpty();
    }

    private String getUsername(){
        return signUpUsernameEditText.getText().toString().trim();
    }

    private String getEmail(){
        return signUpEmailEditText.getText().toString().trim();
    }

    private String getPassword(){
        return signUpPasswordEditText.getText().toString().trim();
    }

    private class SignUpRequest extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... values) {

            String username = values[0];
            String email = values[1];
            String password = values[2];

            //acting as a 10 second server communication

            try{
                for(int i=0; i<10; i++){
                    Log.d("TAG", "i = " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //acting as a already registered user
            if(username.equalsIgnoreCase("lstanzione")){
                return false;
            }

            return true;

        }

        @Override
        protected void onPostExecute(Boolean signUpResult) {

            if(signUpResult){
                Snackbar.make(coordinatorLayout, "Account created successfully!", Snackbar.LENGTH_SHORT).show();
            }
            else{
                Snackbar.make(coordinatorLayout, "This username already exists", Snackbar.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {

            Snackbar.make(coordinatorLayout, "Sign Up cancelled!", Snackbar.LENGTH_SHORT).show();

        }
    }

}
