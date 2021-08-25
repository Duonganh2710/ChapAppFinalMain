package com.example.chapappfinalmain.Activity.MainApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.chapappfinalmain.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBackSearch;
    EditText extSearch;
    RecyclerView rcyDataSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();

        rcyDataSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        imgBackSearch.setOnClickListener(this);
    }

    private void init() {
        imgBackSearch = findViewById(R.id.img_back_search);
        extSearch = findViewById(R.id.src_data);
        rcyDataSearch = findViewById(R.id.rcy_data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back_search:{
                onBackPressed();
                break;
            }
        }
    }
}