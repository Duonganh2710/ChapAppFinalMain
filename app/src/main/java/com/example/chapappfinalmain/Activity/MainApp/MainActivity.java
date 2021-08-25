package com.example.chapappfinalmain.Activity.MainApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.chapappfinalmain.Base.BaseActivity;
import com.example.chapappfinalmain.BroadcastRecive.InternetBroadcastReceive;
import com.example.chapappfinalmain.Enum.DataOfUser;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.fragment.FragmentMain.ChatFragment;
import com.example.chapappfinalmain.fragment.FragmentMain.ProfileFragment;
import com.example.chapappfinalmain.fragment.FragmentMain.WorkFragment;
import com.example.chapappfinalmain.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.DataInputStream;

public class MainActivity extends BaseActivity {
    ChipNavigationBar botNavMain ;
    User user;

    Dialog progressDialog;

    private InternetBroadcastReceive broadcastReceive = new InternetBroadcastReceive();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        internetManager();

        if(broadcastReceive.isConnect) {
            createProgressDialog();

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            final String userId = currentUser.getUid();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userData = snapshot.getValue(User.class);
                    user = userData;

                    progressDialog.dismiss();

                    if(user != null){
                        saveDatabaseUser(user, getApplicationContext());
                        saveObjectUser(getApplicationContext(), "USER_DATA", "USER_KEY", user);
                    }
                    progressDialog.dismiss();

                    ChatFragment(user);
                    botNavMain.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(int i) {
                            selectItemMenu(i);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void createProgressDialog() {
        progressDialog = new Dialog(MainActivity.this);
        progressDialog.setContentView(R.layout.get_data_dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        progressDialog.show();
    }
    private void internetManager(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceive, intentFilter);
    }

    private void selectItemMenu(int i) {
        switch (i) {
            case R.id.home:{
                selectFragment(ChatFragment.getInstance(user));
                break;
            }
            case R.id.work:{
                selectFragment(WorkFragment.getInstance(user));
                break;
            }
            case R.id.user:{
                selectFragment(ProfileFragment.getInstance(user));
                break;
            }
        }
    }

    private void init() {
        botNavMain = findViewById(R.id.bottom_navigation);
        user = new User();
    }

    private void ChatFragment(User dataUser) {
        botNavMain.setItemSelected(R.id.home, true);
        selectFragment(ChatFragment.getInstance(user));
    }

    private void selectFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.main_content, fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        registerReceiver(broadcastReceive, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceive);
    }
}