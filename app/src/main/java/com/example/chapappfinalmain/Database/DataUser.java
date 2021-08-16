package com.example.chapappfinalmain.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.chapappfinalmain.Enum.DataOfUser;
import com.example.chapappfinalmain.model.User;

public abstract class DataUser{

    public DataUser() {
    }

    public void saveDatabaseUser(User user, Context context){
        SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(String.valueOf(DataOfUser.UserName), user.getUserName());
        editor.putString(String.valueOf(DataOfUser.Password), user.getPassword());
        editor.putString(String.valueOf(DataOfUser.UserId), user.getUserId());
        editor.putString(String.valueOf(DataOfUser.Email), user.getEmail());
        editor.putString(String.valueOf(DataOfUser.PhoneNumber), user.getPhoneNumber());
        editor.putString("URL", user.getImgUri());
        editor.apply();
        if(user.getUserName().isEmpty()){
            Log.d("TAG", "NULL");
        }
        Log.d("TAG", user.getUserName());
    }
}
