package com.example.chapappfinalmain.Activity.MainApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.chapappfinalmain.Base.BaseActivity;
import com.example.chapappfinalmain.Base.BaseFragment;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    CircleImageView imgAvatar;
    ImageView imgEmailChange, imgPhoneNumberChange, imgchangeAvatar;
    TextView txtUserName, txtEmail, txtPhoneNumber, txtLocation;
    Button butEditProfile, butLogOut;
    RoundedImageView butBack;
    ConstraintLayout layoutEmail, layoutPhoneNumber, layoutEmailEdit, layoutPhoneNumberEdit;
    EditText extEmailEdit, extPhoneNumberEdit;

    private User user;

    private boolean changeData = false, changeEmail = false , changePhoneNumber = false;
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
        imgEmailChange.setOnClickListener(this);
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
                if(changeData) {
                    if(changeEmail){
                        String email = extEmailEdit.getText().toString();
                        updateEmail(email);
                    }else if(changePhoneNumber) {
                        String phoneNumber = extPhoneNumberEdit.getText().toString();
                        updateInDataBase(null, phoneNumber, user);
                    }
                }
                break;
            }
            case R.id.img_email_change:{
                showlayoutEdit(layoutEmail, layoutEmailEdit);
                changeData = true;
                changeEmail = true;
                break;
            }
            case R.id.img_phone_number_change:{
                showlayoutEdit(layoutPhoneNumber, layoutPhoneNumberEdit);
                changeData = true;
                changePhoneNumber = true;
                break;
            }
            case R.id.img_change_avatar:{
                showDialogChangeAvatar();
                break;
            }
        }
    }

    private void showDialogChangeAvatar() {
        AlertDialog.Builder dialogChangeAvatar = new AlertDialog.Builder(getApplicationContext());
        dialogChangeAvatar.setTitle("Avatar");
        dialogChangeAvatar.setMessage("Do you want change avatar?");
        dialogChangeAvatar.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            
            }
        });
        dialogChangeAvatar.setNegativeButton("No", null);
        dialogChangeAvatar.show();
    }

    private boolean removeAvatar(String urlAvatar){
        final boolean[] removedImgae = {false};
        FirebaseStorage storageImage = FirebaseStorage.getInstance();
        StorageReference reference = storageImage.getReferenceFromUrl(urlAvatar);
        reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                removedImgae[0] = true;
            }
        });
        return removedImgae[0];
    }

    private void updateEmail(String newEmail) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    auth.getCurrentUser().updateEmail(newEmail).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "update email success", Toast.LENGTH_SHORT).show();
                            updateInDataBase(newEmail, null, user);
                        }
                    });
                }
                else {
                    Toast.makeText(ProfileActivity.this, "update email failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateInDataBase(@Nullable String email, @Nullable String phoneNumber, User friendData) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        if(!email.isEmpty()){
            User userDataChange = new User(friendData.getUserName(), email, friendData.getPassword(), auth.getUid(), friendData.getImgUri(), friendData.getPhoneNumber());

            databaseReference.child(auth.getUid()).setValue(userDataChange).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {

                }
            });
        }else if(!phoneNumber.isEmpty()) {
            User userDataChange = new User(friendData.getUserName(), friendData.getEmail(), friendData.getPassword(), auth.getUid(), friendData.getImgUri(), phoneNumber);

            databaseReference.child(auth.getUid()).setValue(userDataChange).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {

                }
            });
        }else {
            User userDataChange = new User(friendData.getUserName(), email, friendData.getPassword(), auth.getUid(), friendData.getImgUri(), phoneNumber);

            databaseReference.child(auth.getUid()).setValue(userDataChange).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {

                }
            });
        }
    }

    private void showlayoutEdit(View layoutGone, View layoutVisible){
        layoutGone.setVisibility(View.INVISIBLE);

        TranslateAnimation animation = new TranslateAnimation(layoutEmailEdit.getWidth(), 0,
                0, 0);
        animation.setDuration(500);
        layoutVisible.setAnimation(animation);

        layoutVisible.setVisibility(View.VISIBLE);
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
        imgEmailChange = findViewById(R.id.img_email_change);
        imgPhoneNumberChange = findViewById(R.id.img_phone_number_edit);
        imgchangeAvatar = findViewById(R.id.img_change_avatar);

        txtUserName = findViewById(R.id.txt_user_name);
        txtEmail = findViewById(R.id.txt_email);
        txtPhoneNumber = findViewById(R.id.txt_phone);
        txtLocation = findViewById(R.id.txt_location);

        butEditProfile = findViewById(R.id.but_edit_porfile);
        butBack = findViewById(R.id.but_back);
        butLogOut = findViewById(R.id.but_logout);

        layoutEmail = findViewById(R.id.layout_email);
        layoutPhoneNumber = findViewById(R.id.layout_phone_number);
        layoutEmailEdit = findViewById(R.id.layout_email_edit);
        layoutPhoneNumberEdit = findViewById(R.id.layout_phone_number_edit);

        extEmailEdit = findViewById(R.id.ext_email_edit);
        extPhoneNumberEdit = findViewById(R.id.ext_phone_number_edit);
    }


}