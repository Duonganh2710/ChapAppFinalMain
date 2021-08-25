package com.example.chapappfinalmain.fragment.FragmentMain;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.chapappfinalmain.Activity.MainApp.ChatActivity;
import com.example.chapappfinalmain.Activity.MainApp.CreateContentActivity;
import com.example.chapappfinalmain.Activity.MainApp.ProfileActivity;
import com.example.chapappfinalmain.Adapter.AdapterFriend;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.Chat;
import com.example.chapappfinalmain.model.User;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatFragment extends Fragment implements AdapterFriend.IOnclickData, View.OnClickListener {
    private SearchView searchView;
    private RecyclerView listFriend;
    private CircleImageView imgSetting;

    private AdapterFriend adapterFriend;
    private AdapterFriend.IOnclickData iOnclickData = this;

    private User user;

    public static ChatFragment getInstance(User user) {
        ChatFragment fragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object_user", user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        user = (User) getArguments().get("Object_user");
        imgSetting.setOnClickListener(this);
        getDataFriend(user);
    }

    private void getDataFriend(User userData) {
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_list_chat);
        listFriend.setLayoutAnimation(layoutAnimationController);

        if(userData == null) {
            Log.d("TAG", "null");
        }else {
            final List<User> userList = new ArrayList<>();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userList.clear();
                    for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                        User user = dataSnapshot1.getValue(User.class);
                        if(!user.getUserId().equals(userData.getUserId())) {
                            userList.add(user);
                        }
                    }
                    listFriend.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterFriend = new AdapterFriend(getContext(), userList, iOnclickData);
                    listFriend.setAdapter(adapterFriend);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public void sendData(User friendData) {
        if(friendData != null){
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra("FriendData", friendData);
            intent.putExtra("UserData", user);
            getActivity().startActivity(intent);
            Log.e("TAG", user.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_setting:{
                showBottomSheetSetting();
                break;
            }
        }
    }

    private void showBottomSheetSetting() {
        BottomSheetDialog dialogSetting = new BottomSheetDialog(getContext());
        dialogSetting.setContentView(R.layout.dialog_setting);
        LinearLayout layoutSetting = dialogSetting.findViewById(R.id.layout_setting);
        LinearLayout layoutContent = dialogSetting.findViewById(R.id.layout_content);
        LinearLayout layoutProfile = dialogSetting.findViewById(R.id.layout_profile);
        LinearLayout layoutLogout = dialogSetting.findViewById(R.id.layout_logout);
        
        layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToOtherActivity(ProfileActivity.class);
            }
        });
        layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToOtherActivity(CreateContentActivity.class);
            }
        });
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogLogout();
            }
        });
        
        dialogSetting.show();
    }

    private void openDialogLogout() {
    }

    private void moveToOtherActivity(Class className){
        Intent intent = new Intent(getContext(), className);
        getContext().startActivity(intent);
    }

    private void init(View view){
        //     searchView = view.findViewById(R.id.search_bar_list_friend);
        listFriend = view.findViewById(R.id.list_user);
        imgSetting = view.findViewById(R.id.img_setting);
    }
}