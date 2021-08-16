package com.example.chapappfinalmain.fragment.FragmentProliteWork;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chapappfinalmain.Activity.EditImage.EditImageActivity;
import com.example.chapappfinalmain.Activity.MainApp.CreateContentActivity;
import com.example.chapappfinalmain.Adapter.AdpaterImagePhone;
import com.example.chapappfinalmain.Base.BaseFragment;
import com.example.chapappfinalmain.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;


public class PrivateFragment extends BaseFragment implements AdpaterImagePhone.SelectedImageListener {
    RecyclerView rcyImgPhone;
    AdpaterImagePhone adpaterImagePhone;

    ArrayList<String> listPaths = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_private, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        if(requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
            loadImage();
        }else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void loadImage() {
        listPaths = listPathImages(getContext());
        adpaterImagePhone = new AdpaterImagePhone(getContext(), listPaths, this);

        rcyImgPhone.setHasFixedSize(true);
        rcyImgPhone.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rcyImgPhone.setAdapter(adpaterImagePhone);
    }

    private void init(View view) {
        rcyImgPhone = view.findViewById(R.id.rcy_img_private);
    }

    @Override
    public void imageListener(String path, View view) {
        Uri uri = Uri.fromFile(new File(path));
        if(uri != null){
            Intent intent = new Intent(getContext(), EditImageActivity.class);
            intent.putExtra("URI_IMAGE", uri);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) getContext(), view.findViewById(R.id.img_photo_in_mobile), "photoEdit");
            startActivity(intent, optionsCompat.toBundle());
        }

    }
    private ArrayList<String> listPathImages(Context context){
        ArrayList<String> listPathImages = new ArrayList<>();
        Uri uri;
        Cursor cursor;
        int column_index_data;
        String ablosutePathOfImage;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderBy = MediaStore.Video.Media.DATE_TAKEN;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        cursor  =  context.getContentResolver().query(uri, projection, null, null, orderBy+" DESC" );
        column_index_data = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()){
            ablosutePathOfImage = cursor.getString(column_index_data);
            listPathImages.add(ablosutePathOfImage);
        }
        return listPathImages;
    }
}