package com.stanzione.loginprototype.login;

import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stanzione.loginprototype.R;
import com.stanzione.loginprototype.di.LoginApplication;

import javax.inject.Inject;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.loginButton)
    Button loginSignInButton;

    @BindView(R.id.loginSignUpButton)
    Button loginSignUpButton;

    @BindView(R.id.spreadTitle)
    TextView titleTextView;

    //Sign In elements
    @BindView(R.id.signInLayout)
    RelativeLayout signInLayout;

    @BindView(R.id.signInUsernameEditText)
    EditText signInUsernameEditText;

    @BindView(R.id.signInPasswordEditText)
    EditText signInPasswordEditText;

    @BindView(R.id.signInButton)
    Button signInButton;

    //Sign Up elements
    @BindView(R.id.signUpCardView)
    CardView signUpCardView;

    @BindView(R.id.signUpUsernameEditText)
    EditText signUpUsernameEditText;

    @BindView(R.id.signUpEmailEditText)
    EditText signUpEmailEditText;

    @BindView(R.id.signUpPasswordEditText)
    EditText signUpPasswordEditText;

    @BindView(R.id.signUpButton)
    Button signUpButton;

    @Inject
    LoginContract.Presenter loginPresenter;

    private LoginState currentState = LoginState.STATE_LOGO;

    private SignInRequest signInRequest;
    private SignUpRequest signUpRequest;

    private boolean isSignInRunning = false;
    private boolean isSignUpRunning = false;

    @BindAnim(R.anim.fade_in)
    Animation animFadeIn;
    @BindAnim(R.anim.fade_out)
    Animation animFadeOut;
    @BindAnim(R.anim.explode)
    Animation animExplode;
    @BindAnim(R.anim.enter_from_left)
    Animation animEnterFromLeft;
    @BindAnim(R.anim.enter_from_right)
    Animation animEnterFromRight;
    @BindAnim(R.anim.exit_to_left)
    Animation animExitToLeft;
    @BindAnim(R.anim.exit_to_right)
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

        ((LoginApplication) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter.attachView(this);
        loginPresenter.getAppUser();
    }

    @OnClick(R.id.loginButton)
    void showSignIn(){
        loginPresenter.signInButtonClicked();
    }

    @OnClick(R.id.loginSignUpButton)
    void showSignUp(){
        loginPresenter.signUpButtonClicked();
    }

    @OnClick(R.id.signInButton)
    void doSignIn(){
        loginPresenter.doLogin();
    }

    @OnClick(R.id.signUpButton)
    void doSignUp(){
        loginPresenter.doSignUp();
    }

    @Override
    public void showSignInDialog() {

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

    @Override
    public void showSignUpDialog() {

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

    @Override
    public String getSignInUsername() {
        return signInUsernameEditText.getText().toString().trim();
    }

    @Override
    public String getSignInPassword() {
        return signInPasswordEditText.getText().toString().trim();
    }

    @Override
    public String getSignUpUsername() {
        return signUpUsernameEditText.getText().toString().trim();
    }

    @Override
    public String getSignUpEmail() {
        return signUpEmailEditText.getText().toString().trim();
    }

    @Override
    public String getSignUpPassword() {
        return signUpPasswordEditText.getText().toString().trim();
    }

    @Override
    public void setSignInUsername(String signInUsername) {
        signInUsernameEditText.setText(signInUsername);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
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
