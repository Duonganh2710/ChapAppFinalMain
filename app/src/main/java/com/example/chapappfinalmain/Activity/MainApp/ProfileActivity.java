package com.example.chapappfinalmain.Activity.MainApp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.chapappfinalmain.Base.BaseActivity;
import com.example.chapappfinalmain.Base.BaseFragment;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.makeramen.roundedimageview.RoundedImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    CircleImageView imgAvatar;
    TextView txtUserName, txtEmail, txtPhoneNumber, txtLocation;
    Button butEditProfile, butLogOut;
    RoundedImageView butBack;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        user = getSavedUser(getApplicationContext(), "USER_DATA", "USER_KEY", User.class);

        Glide.with(getApplicationContext()).load(user.getImgUri()).into(imgAvatar);
        txtUserName.setText(user.getUserName());
        txtPhoneNumber.setText(user.getPhoneNumber());
        txtEmail.setText(user.getEmail());
        txtLocation.setText("Bien Hoa");

        butEditProfile.setOnClickListener(this);
        butBack.setOnClickListener(this);
        butLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:{
                finish();
                onBackPressed();
                break;
            }
            case R.id.but_logout:{
                logoutUser();
                break;
            }
            case R.id.but_edit_porfile:{

                break;
            }
        }
    }

    private void logoutUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();

        Intent intent = new Intent(ProfileActivity.this, FristActivity.class);
        startActivity(intent);
        finish();
    }

    private void init(){
        imgAvatar = findViewById(R.id.img_avatar);
        txtUserName = findViewById(R.id.txt_user_name);
        txtEmail = findViewById(R.id.txt_email);
        txtPhoneNumber = findViewById(R.id.txt_phone);
        txtLocation = findViewById(R.id.txt_location);
        butEditProfile = findViewById(R.id.but_edit_porfile);
        butBack = findViewById(R.id.but_back);
        butLogOut = findViewById(R.id.but_logout);
    }


}