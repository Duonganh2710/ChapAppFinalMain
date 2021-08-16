package com.example.chapappfinalmain.Activity.MainApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chapappfinalmain.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private final static String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private Context loginContext = LoginActivity.this;
    private String email = null, password = null;
    private int maxOfClick = 0;

    Button submitButton;
    EditText extEmail, extPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        mAuth = FirebaseAuth.getInstance();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = extEmail.getText().toString();
                password = extPassword.getText().toString();
                if(testDataIntoEditText()) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "Sign In Success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                toChatActivity();

                                onStop();
                            }else {
                                Log.d(TAG, "Sign In fail");
                                Log.d(TAG, task.getException().getMessage());
                                maxOfClick++;
                                if(maxOfClick == 5) {
                                    dialogLostLogin();
                                }
                            }
                        }
                    });
                }
            }
        });

    }

    private void dialogLostLogin() {
        maxOfClick = 0;
        AlertDialog.Builder dialogLoginLost = new AlertDialog.Builder(loginContext);
        dialogLoginLost.setTitle(R.string.dialog_title_lost_password);
        dialogLoginLost.setMessage(R.string.dialog_message_lost_password);
        dialogLoginLost.setCancelable(false);
        dialogLoginLost.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogLoginLost.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = dialogLoginLost.create();
        alertDialog.show();
    }

    private void toChatActivity() {
        Intent intent = new Intent(loginContext, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            reload();
        }
    }

    private void init(){
        submitButton = findViewById(R.id.submitbutton);
        extEmail = findViewById(R.id.ext_email);
        extPassword = findViewById(R.id.ext_password);
    }

    private boolean testDataIntoEditText(){
        if(password.length() <= 9) {
            Toast.makeText(loginContext, R.string.lack_of_character, Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(loginContext, R.string.invalid_email, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void reload() {
        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(loginContext,"Reload successful!", Toast.LENGTH_SHORT).show();
                }else {
                    Log.e(TAG, "reload", task.getException());
                    Toast.makeText(loginContext, "Failed to reload user.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

