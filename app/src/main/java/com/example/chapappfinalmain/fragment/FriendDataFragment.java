package com.example.chapappfinalmain.fragment;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.fragment.FragmentMain.ChatFragment;
import com.example.chapappfinalmain.model.User;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;


public class FriendDataFragment extends DialogFragment implements View.OnClickListener {
    private CircleImageView imgClose, imgAvatarFriend;
    private TextView txtUserName;
    private LinearLayout layoutMessager, layoutCall, layoutProfile;
    private CardView cardProfile;

    private User friend;

    public static FriendDataFragment getInstance(User friendData){
        FriendDataFragment fragment = new FriendDataFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("FRIEND_DATA", friendData);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setWindowAnimations(R.style.dialog_slide_animation);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView(view);
        setupClickListeners(view);

        friend = (User) getArguments().get("FRIEND_DATA");

        Glide.with(getContext()).load(friend.getImgUri()).into(imgAvatarFriend);
        txtUserName.setText(friend.getUserName());
        imgClose.setOnClickListener(this);
        layoutMessager.setOnClickListener(this);
        layoutProfile.setOnClickListener(this);
        layoutCall.setOnClickListener(this);
    }

    private void setupClickListeners(View view) {
        switch (view.getId()){
            case R.id.img_close_dialog:{
                getDialog().dismiss();
                break;
            }
            case R.id.layout_profile:{
                cardProfile.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.layout_messager:{
                getDialog().dismiss();
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_close_dialog:{
                getDialog().dismiss();
            }
        }
    }

    private void setUpView(View view) {
        imgClose = view.findViewById(R.id.img_close_dialog);
        imgAvatarFriend = view.findViewById(R.id.img_avatar_friend);
        txtUserName = view.findViewById(R.id.txt_username_friend);
        layoutMessager = view.findViewById(R.id.layout_messager);
        layoutCall = view.findViewById(R.id.layout_call);
        layoutProfile = view.findViewById(R.id.layout_profile);
        cardProfile = view.findViewById(R.id.card_profile);
    }
}