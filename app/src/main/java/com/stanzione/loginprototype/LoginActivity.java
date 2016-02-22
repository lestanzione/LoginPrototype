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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;

    private Button loginSignInButton;
    private Button loginSignUpButton;
    private TextView titleTextView;

    //Sign In elements
    private RelativeLayout signInLayout;
    private EditText signInUsernameEditText;
    private EditText signInPasswordEditText;
    private Button signInButton;

    //Sign Up elements
    private CardView signUpCardView;
    private EditText signUpUsernameEditText;
    private EditText signUpEmailEditText;
    private EditText signUpPasswordEditText;
    private Button signUpButton;

    private LoginState currentState = LoginState.STATE_LOGO;

    private SignInRequest signInRequest;
    private SignUpRequest signUpRequest;

    private boolean isSignInRunning = false;
    private boolean isSignUpRunning = false;

    Animation animFadeIn;
    Animation animFadeOut;
    Animation animExplode;
    Animation animEnterFromLeft;
    Animation animEnterFromRight;
    Animation animExitToLeft;
    Animation animExitToRight;

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
        animEnterFromLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.enter_from_left);
        animEnterFromRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.enter_from_right);
        animExitToLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.exit_to_left);
        animExitToRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.exit_to_right);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        loginSignInButton = (Button) findViewById(R.id.loginButton);
        loginSignUpButton = (Button) findViewById(R.id.loginSignUpButton);
        titleTextView = (TextView) findViewById(R.id.spreadTitle);

        signInLayout = (RelativeLayout) findViewById(R.id.signInLayout);
        signInUsernameEditText = (EditText) findViewById(R.id.signInUsernameEditText);
        signInPasswordEditText = (EditText) findViewById(R.id.signInPasswordEditText);
        signInButton = (Button) findViewById(R.id.signInButton);

        signUpCardView = (CardView) findViewById(R.id.signUpCardView);
        signUpUsernameEditText = (EditText) findViewById(R.id.signUpUsernameEditText);
        signUpEmailEditText = (EditText) findViewById(R.id.signUpEmailEditText);
        signUpPasswordEditText = (EditText) findViewById(R.id.signUpPasswordEditText);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        loginSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignIn();
            }
        });

        loginSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUp();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSignIn();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSignUp();
            }
        });

    }

    private void showSignIn(){

        if(signUpRequest != null) {
            signUpRequest.cancel(true);
        }

        if(currentState.equals(LoginState.STATE_LOGO)) {

            currentState = LoginState.STATE_SIGNIN;

            titleTextView.startAnimation(animExplode);

            titleTextView.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    signInLayout.startAnimation(animFadeIn);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
        else if(currentState.equals(LoginState.STATE_SIGNUP)){

            currentState = LoginState.STATE_SIGNIN;

            signInLayout.startAnimation(animEnterFromLeft);
            signUpCardView.startAnimation(animExitToRight);

            signUpCardView.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    signUpCardView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }

    }

    private void showSignUp(){

        if(signInRequest != null) {
            signInRequest.cancel(true);
        }

        if(currentState.equals(LoginState.STATE_LOGO)) {

            currentState = LoginState.STATE_SIGNUP;

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
        else if(currentState.equals(LoginState.STATE_SIGNIN)){

            currentState = LoginState.STATE_SIGNUP;

            signInLayout.startAnimation(animExitToLeft);
            signUpCardView.startAnimation(animEnterFromRight);

            signInLayout.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    signInLayout.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }

    }

    private void doSignIn(){

        if(!isSignInUsernameValid()){
            Snackbar.make(coordinatorLayout, "Username must have at least 5 characters", Snackbar.LENGTH_SHORT).show();
        }
        else if(!isSignInPasswordValid()){
            Snackbar.make(coordinatorLayout, "Enter a password", Snackbar.LENGTH_SHORT).show();
        }
        else{
            signInRequest = new SignInRequest();
            signInRequest.execute(new String[]{getSignInUsername(), getSignInPassword()});
        }

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

    private boolean isSignInUsernameValid(){
        return getSignInUsername().length() >= 5;
    }

    private boolean isSignInPasswordValid(){
        return !getSignInPassword().isEmpty();
    }

    private String getSignInUsername(){
        return signInUsernameEditText.getText().toString().trim();
    }

    private String getSignInPassword(){
        return signInPasswordEditText.getText().toString().trim();
    }

    private class SignInRequest extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... values) {

            String username = values[0];
            String password = values[1];

            //acting as a 10 second server communication

            try{
                for(int i=0; i<10; i++){
                    Log.d("TAG", "i = " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //acting as a successfully login attempt
            if(username.equalsIgnoreCase("lstanzione")){
                return true;
            }

            return false;

        }

        @Override
        protected void onPostExecute(Boolean signInResult) {

            if(signInResult){
                Snackbar.make(coordinatorLayout, "Logged successfully!", Snackbar.LENGTH_SHORT).show();
            }
            else{
                Snackbar.make(coordinatorLayout, "Wrong username or password!", Snackbar.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onCancelled() {

            Snackbar.make(coordinatorLayout, "Sign In cancelled!", Snackbar.LENGTH_SHORT).show();

        }
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
