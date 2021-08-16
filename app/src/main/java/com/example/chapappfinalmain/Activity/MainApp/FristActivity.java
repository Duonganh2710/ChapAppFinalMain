package com.example.chapappfinalmain.Activity.MainApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.chapappfinalmain.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FristActivity extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    Button butLogin, butRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist);

        butLogin = findViewById(R.id.but_login);
        butRegister = findViewById(R.id.but_register);
        lottieAnimationView = findViewById(R.id.animation);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            toNextActivity(MainActivity.class);
        }

        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextActivity(LoginActivity.class);
            }
        });

        butRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextActivity(RegisterActivity.class);
            }
        });
    }
    private void toNextActivity(Class nextActivity){
        Intent intent = new Intent(getApplicationContext(), nextActivity);
        startActivity(intent);
        finishAffinity();
    }
}