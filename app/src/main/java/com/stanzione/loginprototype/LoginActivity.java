package com.stanzione.loginprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button signUpButton;
    private TextView titleTextView;
    private TextView signUpEmailTextView;

    Animation animFadeIn;
    Animation animFadeOut;
    Animation animExplode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        animExplode = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.explode);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        titleTextView = (TextView) findViewById(R.id.spreadTitle);
        signUpEmailTextView = (TextView) findViewById(R.id.signUpEmailEditText);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUp();
            }
        });

    }

    private void showSignUp(){

        titleTextView.startAnimation(animExplode);

        titleTextView.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                signUpEmailTextView.startAnimation(animFadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
