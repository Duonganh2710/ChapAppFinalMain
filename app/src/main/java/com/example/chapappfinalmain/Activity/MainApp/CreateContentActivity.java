package com.example.chapappfinalmain.Activity.MainApp;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.bumptech.glide.Glide;
import com.example.chapappfinalmain.Activity.EditImage.EditImageActivity;
import com.example.chapappfinalmain.Enum.DataOfUser;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.Thread.ThreadUpdateContent;
import com.example.chapappfinalmain.model.Content;
import com.example.chapappfinalmain.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateContentActivity extends AppCompatActivity implements View.OnClickListener {
    private final int CAMERA_REQUEST = 1;
    private final int STORAGE_REQUEST = 2;

    ImageButton imgBackMainActivity;
    Button butSendContent;
    ImageView imgAvatar;
    TextView txtUserName;
    LinearLayout layoutSelectImg;
    EditText extContent;
    ImageView imgImageContent;
    Button butEditPhoto;

    Uri UriImage;
    String dataPhoto;
    String userId , userName, userImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_content);
        init();

        getUserData();

        dataPhoto = getDataPhotoAfterEdit();
        if(dataPhoto != null){
            imgImageContent.setImageURI(Uri.fromFile(new File(dataPhoto)));
            setVisibility(true);
        }else {
            setVisibility(false);
        }

        txtUserName.setText(userName);
        Glide.with(getApplicationContext()).load(userImage).into(imgAvatar);

        imgBackMainActivity.setOnClickListener(this);
        butSendContent.setOnClickListener(this);
        imgImageContent.setOnClickListener(this);
        butEditPhoto.setOnClickListener(this);
    }

    private String getDataPhotoAfterEdit() {
        Intent intent = getIntent();
        return intent.getStringExtra("PATH_IMAGE");
    }

    private void setVisibility(boolean b) {
        if(b == true){
            butEditPhoto.setVisibility(View.VISIBLE);
        }else {
            butEditPhoto.setVisibility(View.INVISIBLE);
        }
    }

    private void sendContent(){
        if(extContent.length() == 0) {
            Toast.makeText(getApplicationContext(), "Content is empty", Toast.LENGTH_LONG).show();
        }else if(!imgImageContent.isShown() && extContent.length() > 0){
            Toast.makeText(getApplicationContext(), "Picture is empty", Toast.LENGTH_LONG).show();
        }else {
            sendContentWithImg(userId);

            Intent intent = new Intent(CreateContentActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), STORAGE_REQUEST);
    }

    private void sendContentWithImg(String userID) {
        String idContent = getIdContent();
        String time = getTime();
        String content = extContent.getText().toString();
        Content dataContent = new Content(idContent, userID, userName, time, content, userImage);

//        Uri file = Uri.fromFile(new File(pathImage));

        ThreadUpdateContent threadUpdateContent = new ThreadUpdateContent(dataContent, UriImage);
        threadUpdateContent.run();
    }
    public static String getRealPathFromURI(Context context, Uri uri) {
        String filePath = "";
        if (uri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            Log.e("DATA", filePath + "12233");
            Log.e("DATA1", uri.toString());
            return filePath;
        } else {
            // image pick from gallery
            return  getRealPathFromURI(context,uri);
        }

    }
    private String getIdContent(){
        return "Content" + System.currentTimeMillis();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == STORAGE_REQUEST) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imgImageContent.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UriImage = uri;
                setVisibility(true);
            }
        }
    }

    private void backMainActivity(){
        onBackPressed();
        finish();
    }
    private void getUserData() {
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        userId = preferences.getString(String.valueOf(DataOfUser.UserId), "1");
        userName = preferences.getString(String.valueOf(DataOfUser.UserName), "1");
        userImage = preferences.getString(String.valueOf(DataOfUser.ImageUrl), "1");
    }
//    private void getPathImageAfterEdit() {
//        Intent intent = getIntent();
//        pathImage = intent.getStringExtra("PATH_IMAGE");
//        if(pathImage != null){
//            imgImageContent.setImageURI(Uri.fromFile(new File(pathImage)));
//        }
//    }

    private String getTime() {
        String time = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        return time;
    }
    private void init() {
        imgBackMainActivity = findViewById(R.id.img_back);
        butSendContent = findViewById(R.id.but_send);
        imgAvatar = findViewById(R.id.img_avatar_create);
        txtUserName = findViewById(R.id.txt_user_name);
        layoutSelectImg = findViewById(R.id.layout_add_img);
        extContent = findViewById(R.id.ext_content);
        imgImageContent = findViewById(R.id.img_add_image);
        butEditPhoto = findViewById(R.id.but_open_edit_photo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:{
                backMainActivity();
                break;
            }
            case R.id.but_send:{
                sendContent();
                break;
            }
            case R.id.img_add_image:{
                getImage();
                break;
            }
            case R.id.but_open_edit_photo:{
                if(UriImage != null){
                    Intent intent = new Intent(CreateContentActivity.this, EditImageActivity.class);
                    intent.putExtra("URI_IMAGE", UriImage);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(CreateContentActivity.this, findViewById(R.id.img_add_image), "photoEdit");
                    startActivity(intent, optionsCompat.toBundle());
                    finish();
                }
                break;
            }
        }
    }
}
