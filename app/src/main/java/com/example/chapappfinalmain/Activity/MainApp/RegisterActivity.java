package com.example.chapappfinalmain.Activity.MainApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.chapappfinalmain.Database.DataUser;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.Thread.ThreadRegisterUser;
import com.example.chapappfinalmain.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private final static String TAG = "RegisterActivity";
    private final Context registerContext = RegisterActivity.this;
    private final int CAMERA_REQUEST = 1;
    private final int STORAGE_REQUEST = 2;

    private EditText extUserName, extEmail, extPassword,extPhoneNumber;
    private Button butRegister;
    private TextView txtToLogin;
    private CircleImageView imgAvatar;
    private LottieAnimationView animationView;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mReference;

    private String email, password, userName, phone;
    private boolean addAvatar = false;
    private String urlAvatar = "";

    private User user;
    private DataUser dataUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        mAuth = FirebaseAuth.getInstance();
        butRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = extUserName.getText().toString();
                email = extEmail.getText().toString();
                password = extPassword.getText().toString();
                phone = extPhoneNumber.getText().toString();
                if(textDataInfoExt(userName, email, password, phone)) {
                    if(addAvatar){
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Log.d(TAG, "Create user success");
                                    currentUser = mAuth.getCurrentUser();

                                    upLoadImageAndSaveUser();
                                    toChatActivity();
                                }else {
                                    Log.d(TAG, "FAIL: "  + task.getException().getMessage());
                                    Toast.makeText(registerContext, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(registerContext, "You have not avatar",Toast.LENGTH_SHORT).show();
                        dialogAddAvatar();
                    }

                }
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddAvatar();
            }
        });

        txtToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginActivity();
            }
        });
        
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reloadCurrentUser();
        }
    }
    private void reloadCurrentUser() {
        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(registerContext,"Reload successful!", Toast.LENGTH_SHORT).show();
                }else {
                    Log.e(TAG, "reload", task.getException());
                    Toast.makeText(registerContext, "Failed to reload user.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean textDataInfoExt(String userName, String email, String password, String phone) {
        if(password.length() <= 9 || userName.length() <= 9) {
            Toast.makeText(registerContext, R.string.lack_of_character, Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(registerContext, R.string.invalid_email, Toast.LENGTH_LONG).show();
            return false;
        }
        if(phone.length() >= 12){
            Toast.makeText(registerContext, R.string.phone_error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void dialogAddAvatar() {
        AlertDialog.Builder dialogAddAvatar = new AlertDialog.Builder(registerContext);
        dialogAddAvatar.setTitle("Avatar");
        dialogAddAvatar.setMessage("You must have avatar");
        dialogAddAvatar.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }).setNegativeButton("Data Storage", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), STORAGE_REQUEST);
            }
        });
        dialogAddAvatar.setCancelable(false);

        AlertDialog alertDialog = dialogAddAvatar.create();
        alertDialog.show();

        addAvatar = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        animationView.setVisibility(View.GONE);
        imgAvatar.setVisibility(View.VISIBLE);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST: {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imgAvatar.setImageBitmap(photo);
                    break;
                }
                case STORAGE_REQUEST: {
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgAvatar.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void toLoginActivity() {
        Intent intent = new Intent(registerContext, LoginActivity.class);
        startActivity(intent);
        finish();
        onStop();
    }
    private void toChatActivity() {
        Intent intent = new Intent(registerContext, MainActivity.class);
        startActivity(intent);
        finish();
        onStop();
    }

    private void upLoadImageAndSaveUser() {
        imgAvatar.setDrawingCacheEnabled(true);
        imgAvatar.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        ThreadRegisterUser threadRegisterUser = new ThreadRegisterUser(getApplicationContext(),data, userName, password,
                currentUser.getUid(),email, phone);
        threadRegisterUser.start();
    }

    private void init(){
        extUserName = findViewById(R.id.ext_user_register);
        extEmail = findViewById(R.id.ext_email_register);
        extPassword = findViewById(R.id.ext_password_register);
        extPhoneNumber = findViewById(R.id.ext_phone_register);
        butRegister = findViewById(R.id.but_register);
        txtToLogin = findViewById(R.id.txt_to_login);
        imgAvatar = findViewById(R.id.img_avatar);
        animationView = findViewById(R.id.animation);
    }
}