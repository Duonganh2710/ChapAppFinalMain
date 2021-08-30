package com.example.chapappfinalmain.fragment.FragmentProliteWork;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.chapappfinalmain.Adapter.AdapterProfileImg;
import com.example.chapappfinalmain.Adapter.AdapterWork;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.Content;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.logging.Logger;

public class PublishFragment extends Fragment implements AdapterProfileImg.IOnclickData{
    FirebaseUser userCurrent;

    AdapterProfileImg.IOnclickData onclickData = this;

    ArrayList<Content> listContent;
    RecyclerView rcyImg;
    AdapterProfileImg adapterProfileImg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, container, false);
        init(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull final View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listContent = new ArrayList<>();
        userCurrent = FirebaseAuth.getInstance().getCurrentUser();

        rcyImg.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listContent.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()) {
                    Content content = dataSnapshot1.getValue(Content.class);
                    if(content.getIdUser().equals(userCurrent.getUid())){
                        listContent.add(content);
                    }
                }
                adapterProfileImg = new AdapterProfileImg(listContent, getContext(), onclickData);
                rcyImg.setAdapter(adapterProfileImg);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    private void init(View view) {
        rcyImg = view.findViewById(R.id.rcy_img_publish);
    }

    @Override
    public void sendData(Content content) {
        Log.e("TAG", content.getUrlImage());
    }
}