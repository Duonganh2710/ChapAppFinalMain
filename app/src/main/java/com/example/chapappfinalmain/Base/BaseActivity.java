package com.example.chapappfinalmain.Base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.chapappfinalmain.Database.DataUser;
import com.example.chapappfinalmain.Enum.DataOfUser;
import com.example.chapappfinalmain.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity{
    public static final int READ_WRITE_STORAGE = 52;

    public ProgressDialog progressDialog;

    public boolean requestPermission(String permission){
        boolean isGranted = ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
        if(!isGranted) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, READ_WRITE_STORAGE);
        }
        return isGranted;
    }
    public void isPermissionGranted(boolean isGranted, String permission) {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        switch (requestCode) {
            case READ_WRITE_STORAGE:{
                isPermissionGranted(grantResults[0] == PackageManager.PERMISSION_GRANTED, permissions[0]);
                break;
            }
        }
    }

    public void saveDatabaseUser(User user, Context context){
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(String.valueOf(DataOfUser.UserName), user.getUserName());
        editor.putString(String.valueOf(DataOfUser.Password), user.getPassword());
        editor.putString(String.valueOf(DataOfUser.UserId), user.getUserId());
        editor.putString(String.valueOf(DataOfUser.Email), user.getEmail());
        editor.putString(String.valueOf(DataOfUser.PhoneNumber), user.getPhoneNumber());
        editor.putString(String.valueOf(DataOfUser.ImageUrl), user.getImgUri());
        editor.apply();
    }
    public void saveObjectUser(Context context, String preferenceFileName, String keyNumber, Object object){
        SharedPreferences sharedPreferences = getSharedPreferences(preferenceFileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        editor.putString(keyNumber, serializedObject);
        editor.apply();
    }
    public static User getSavedUser(Context context, String preferenceFileName, String keyNumber, Class<User> user){
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, MODE_PRIVATE);
        if(sharedPreferences.contains(keyNumber)) {
            Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(keyNumber, ""), user);
        }
        return null;
    }

    public void showDialog(@NonNull String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public void hideDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
