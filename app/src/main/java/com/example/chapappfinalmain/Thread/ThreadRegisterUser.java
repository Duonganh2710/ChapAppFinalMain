package com.example.chapappfinalmain.Thread;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chapappfinalmain.Database.DataUser;
import com.example.chapappfinalmain.Enum.DataOfUser;
import com.example.chapappfinalmain.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ThreadRegisterUser extends Thread{
    private final String TAG = "THREAD_REGISTER";

    private String email, password, userName, phone, userId;
    private String urlAvatar;
    private byte[] data;
    private Context context;
    private DataUser dataUser;


    DatabaseReference reference;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public ThreadRegisterUser(Context context, byte[] data, String userName, String password, String userId, String email, String phone) {
        this.data = data;
        this.userName = userName;
        this.password = password;
        this.userId = userId;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public void run() {
        uploadImageAvatar(data);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    private void saveDataUser(String phone , String userName, String email, String password, String uid, String urlAvatar) {
        final User user = new User(userName, email, password, uid, urlAvatar, phone);
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }else {
                }
            }
        });
    }
    private void uploadImageAvatar(byte[] data){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference reference = storage.getInstance().getReference("Avatar").child(userId);

        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        urlAvatar = uri.toString();
                        saveDataUser(phone, userName, email, password, userId, urlAvatar);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getMessage());
            }
        });

    }
}
