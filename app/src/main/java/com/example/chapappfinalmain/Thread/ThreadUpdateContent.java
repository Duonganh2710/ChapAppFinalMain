package com.example.chapappfinalmain.Thread;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chapappfinalmain.model.Content;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class ThreadUpdateContent extends Thread{
    Content content;
    Uri dataImage;

    public ThreadUpdateContent(Content content, Uri dataImage) {
        this.content = content;
        this.dataImage = dataImage;
    }

    @Override
    public void run() {
        super.run();
        updataImage(dataImage);
    }

    private void updataImage(Uri dataImage) {
        String id = dataImage.getLastPathSegment() + "_" +System.currentTimeMillis();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference reference = storage.getInstance().getReference("Post").child(dataImage.getLastPathSegment());

        UploadTask task = reference.putFile(dataImage);
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        updateData(uri, content);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("TAG", e.getMessage());
            }
        });
    }

    private void updateData(Uri uri, Content content) {
        String id = String.valueOf(System.currentTimeMillis());
        Content dataContent = new Content(content.getIdUser(), content.getUserName(), content.getTime(),
                content.getCommentOfContent(), uri.toString(), content.getNumberLike(), content.getStaticContent(), content.getUrlAvatar());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");
        reference.child(id).setValue(dataContent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {

            }
        });
    }
}
