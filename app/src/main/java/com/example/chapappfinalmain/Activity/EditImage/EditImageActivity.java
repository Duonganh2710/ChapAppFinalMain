package com.example.chapappfinalmain.Activity.EditImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.chapappfinalmain.Activity.MainApp.CreateContentActivity;
import com.example.chapappfinalmain.Activity.MainApp.MainActivity;
import com.example.chapappfinalmain.Adapter.AdapterEditImage.AdapterToolsEditImage;
import com.example.chapappfinalmain.Adapter.AdapterEditImage.AdpaterFilter;
import com.example.chapappfinalmain.Base.BaseActivity;
import com.example.chapappfinalmain.Enum.ToolType;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.fragment.FragmentToolEditImage.FragmentToolBrush;
import com.example.chapappfinalmain.fragment.FragmentToolEditImage.FragmentToolText;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import de.hdodenhof.circleimageview.CircleImageView;
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import ja.burhanrashid52.photoeditor.SaveSettings;
import ja.burhanrashid52.photoeditor.TextStyleBuilder;
import ja.burhanrashid52.photoeditor.ViewType;

import static android.net.Uri.fromFile;

public class EditImageActivity extends BaseActivity implements OnPhotoEditorListener,
        View.OnClickListener,
        FragmentToolBrush.Properties ,
        FragmentToolText.TextEditorListener,
        AdpaterFilter.OnFilterListener {
    CircleImageView imgBack, imgCheck;
    PhotoEditorView vEditImg;
    ImageView imgBrush, imgEraser, imgText, imgFilter;
    RecyclerView rcyListFilters;
    LinearLayout layoutTool, layoutFilter;
    CircleImageView imgCloseFilter;

    FragmentToolBrush fragmentToolBrush;
    AdpaterFilter adpaterFilter;

    PhotoEditor photoEditor;
    Uri urImage;
    Boolean openFilter = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        init();

        urImage = getUri();

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), urImage);
            vEditImg.getSource().setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        adpaterFilter = new AdpaterFilter(this);

        photoEditor = new PhotoEditor.Builder(getApplicationContext(), vEditImg).setPinchTextScalable(true).build();
        photoEditor.setOnPhotoEditorListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcyListFilters.setLayoutManager(linearLayoutManager);
        rcyListFilters.setAdapter(adpaterFilter);

        fragmentToolBrush = new FragmentToolBrush(this);
        imgBack.setOnClickListener(this);
        imgCheck.setOnClickListener(this);
        imgBrush.setOnClickListener(this);
        imgText.setOnClickListener(this);
        imgEraser.setOnClickListener(this);
        imgFilter.setOnClickListener(this);
        imgCloseFilter.setOnClickListener(this);
    }

    private Uri getUri() {
        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("URI_IMAGE");
        return uri;
    }

    private void backActivity(@Nullable String photo){
        if(photo == null){
            photoEditor.clearAllViews();
            onBackPressed();
            finish();
        }else {
            photoEditor.clearAllViews();
            Intent intent = new Intent(EditImageActivity.this, CreateContentActivity.class);
            intent.putExtra("PATH_IMAGE", photo);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_back:{
               backActivity(null);
               break;
            }
            case R.id.but_check:{
                savaImage();
                break;
            }
            case R.id.but_brush:{
                photoEditor.setBrushDrawingMode(true);
                fragmentToolBrush.show(getSupportFragmentManager(), fragmentToolBrush.getTag());
                break;
            }
            case R.id.img_close_filter:{
                showFilter(!openFilter);
                break;
            }
            case R.id.but_text:{
                FragmentToolText fragmentToolText = FragmentToolText.show(this);
                fragmentToolText.setOnTextEditorListener(new FragmentToolText.TextEditorListener() {
                    @Override
                    public void onDone(String textComment, int colorCode) {
                        TextStyleBuilder styleBuilder = new TextStyleBuilder();
                        styleBuilder.withTextColor(colorCode);

                        photoEditor.addText(textComment, styleBuilder);

                    }
                });
                break;
            }
            case R.id.but_eraser:{
                photoEditor.brushEraser();
                break;
            }
            case R.id.but_filter:{
                showFilter(openFilter);
                break;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void savaImage() {
        if(requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showDialog("Handling photo");
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + ""
                    + System.currentTimeMillis() + ".png");
            try {
                file.createNewFile();

                SaveSettings saveSettings = new SaveSettings.Builder().
                        setClearViewsEnabled(true).
                        setTransparencyEnabled(true).
                        build();
                photoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        hideDialog();
                        backActivity(imagePath);
                        Log.e("FAIL123", imagePath);
                        vEditImg.getSource().setImageURI(Uri.fromFile(new File(imagePath)));
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        hideDialog();
                        Log.e("FAIL", exception.getMessage());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showFilter(boolean b) {
        if (b == true) {
            layoutFilter.setVisibility(View.VISIBLE);
            layoutTool.setVisibility(View.INVISIBLE);
            TranslateAnimation animation = new TranslateAnimation(
                    0, 0,
                    layoutFilter.getHeight(), 0
            );
            animation.setDuration(300);
            animation.setFillAfter(true);
            layoutFilter.startAnimation(animation);

        }else {
            TranslateAnimation animation = new TranslateAnimation(
                    0, 0,
                    0, layoutFilter.getHeight()
            );
            animation.setDuration(300);
            animation.setFillAfter(true);
            layoutFilter.startAnimation(animation);
            layoutFilter.setVisibility(View.INVISIBLE);
            layoutTool.setVisibility(View.VISIBLE);

        }
    }


    private void init(){
        imgBack = (CircleImageView) findViewById(R.id.but_back);
        imgCheck = (CircleImageView) findViewById(R.id.but_check);
        vEditImg = findViewById(R.id.photo_edit_view);
        imgBrush = findViewById(R.id.but_brush);
        imgEraser = findViewById(R.id.but_eraser);
        imgText = findViewById(R.id.but_text);
        imgFilter = findViewById(R.id.but_filter);
        rcyListFilters = findViewById(R.id.rcy_filter);
        layoutTool = findViewById(R.id.layout_tool);
        layoutFilter = findViewById(R.id.layout_filter);
        imgCloseFilter = findViewById(R.id.img_close_filter);
    }

    @Override
    public void onColorChanged(int colorCode) {
        photoEditor.setBrushColor(colorCode);
    }

    @Override
    public void onOpacityChanged(int opacity) {
        photoEditor.setOpacity(opacity);
    }

    @Override
    public void onBrushSizeChanged(int brushSize) {
        photoEditor.setBrushSize(brushSize);
    }

    @Override
    public void onEditTextChangeListener(View rootView, String text, int colorCode) {

    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onDone(String textComment, int colorCode) {

    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        photoEditor.setFilterEffect(photoFilter);
    }
}