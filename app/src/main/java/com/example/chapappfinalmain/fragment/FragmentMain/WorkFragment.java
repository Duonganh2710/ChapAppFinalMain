package com.example.chapappfinalmain.fragment.FragmentMain;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.chapappfinalmain.Activity.MainApp.CreateContentActivity;

import com.example.chapappfinalmain.Adapter.AdapterWork;

import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.Content;
import com.example.chapappfinalmain.model.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;


import de.hdodenhof.circleimageview.CircleImageView;



public class WorkFragment extends Fragment implements View.OnClickListener, AdapterWork.ClickLikeAndComment {
    CircleImageView imgAvatar;
    LinearLayout linearLayout;
    FloatingActionButton butAddContent;
    TextView txtUserName;
    RecyclerView rcyWork;

    ArrayList<Content> listContent = new ArrayList<>();
    AdapterWork adapterWork;
    User user;

    public static WorkFragment getInstance(User user) {
        WorkFragment fragment = new WorkFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object_user", user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        init(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = (User) getArguments().get("Object_user");

        Glide.with(view).load(user.getImgUri()).into(imgAvatar);
        txtUserName.setText(user.getUserName());

        rcyWork.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterWork = new AdapterWork(listContent, getContext(), this);
        rcyWork.setAdapter(adapterWork);

        getDataWork();

        butAddContent.setOnClickListener(this);
    }

    private void getDataWork() {
        DatabaseReference dataContent = FirebaseDatabase.getInstance().getReference("Post");
        dataContent.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Content content = snapshot.getValue(Content.class);
                listContent.add(content);
                Collections.reverse(listContent);
                adapterWork.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Content content = snapshot.getValue(Content.class);
                listContent.set(getNumberOfItem(content.getIdContent()), content);
                adapterWork.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Content content = snapshot.getValue(Content.class);
                listContent.remove(getNumberOfItem(content.getIdContent()));
                adapterWork.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });

    }

    private void init(View view) {
        imgAvatar =  view.findViewById(R.id.img_avatar_work);
        linearLayout = view.findViewById(R.id.layout_click);
        butAddContent = view.findViewById(R.id.but_add_content);
        txtUserName = view.findViewById(R.id.txt_user_name);
        rcyWork = view.findViewById(R.id.rcy_work);
    }
    private int getNumberOfItem (String idContent) {
        int numberItem = 0;
        for(int i = 0; i <= listContent.size() - 1; i++){
            if(listContent.get(i).getIdContent().equals(idContent)) {
                numberItem =  i;
                break;
            }
        }
        return numberItem;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_add_content:{
                Intent intent = new Intent(getActivity(), CreateContentActivity.class);
                startActivity(intent);
                break;
            }
        }
    }


    @Override
    public void OnClickLiKeContent(String idContent, boolean isLiked) {
        FirebaseDatabase dataLike = FirebaseDatabase.getInstance();
        if(!isLiked){
            dataLike.getReference().child("Like").
                    child(idContent).
                    child(user.getUserId()).
                    setValue(true);
        }else {
            dataLike.getReference().child("Like").
                    child(idContent).
                    child(user.getUserId()).
                    removeValue();
        }
    }

    @Override
    public void OnClickComment(String idContent) {

    }
}