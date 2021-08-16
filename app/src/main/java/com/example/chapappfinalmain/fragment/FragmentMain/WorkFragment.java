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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


import de.hdodenhof.circleimageview.CircleImageView;



public class WorkFragment extends Fragment {
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

        getDataUser(user, view);
        getDataWork();

        butAddContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateContentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getDataWork() {
        rcyWork.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference dataContent = FirebaseDatabase.getInstance().getReference("Post");
        dataContent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listContent.clear();
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    Content content = dataSnapshot1.getValue(Content.class);
                    listContent.add(content);

                }
                Collections.reverse(listContent);
                adapterWork = new AdapterWork(listContent, getContext());
                rcyWork.setAdapter(adapterWork);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataUser(User user, View view) {
        Glide.with(view).load(user.getImgUri()).into(imgAvatar);
        txtUserName.setText(user.getUserName());
    }

    private void init(View view) {
        imgAvatar =  view.findViewById(R.id.img_avatar_work);
        linearLayout = view.findViewById(R.id.layout_click);
        butAddContent = view.findViewById(R.id.but_add_content);
        txtUserName = view.findViewById(R.id.txt_user_name);
        rcyWork = view.findViewById(R.id.rcy_work);
    }

}