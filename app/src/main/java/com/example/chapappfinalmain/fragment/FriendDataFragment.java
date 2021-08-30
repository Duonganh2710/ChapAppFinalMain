package com.example.chapappfinalmain.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbDevice;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.fragment.FragmentMain.ChatFragment;
import com.example.chapappfinalmain.model.User;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;


public class FriendDataFragment extends DialogFragment implements View.OnClickListener {
    private static final int CALL_KEY = 1;

    private CircleImageView imgClose, imgAvatarFriend;
    private TextView txtUserName;
    private LinearLayout layoutMessager, layoutCall, layoutProfile;
    private CardView cardProfile;

    private User friend;
    private boolean showCardFrofile = false;

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

        friend = (User) getArguments().get("FRIEND_DATA");

        Glide.with(getContext()).load(friend.getImgUri()).into(imgAvatarFriend);
        txtUserName.setText(friend.getUserName());
        imgClose.setOnClickListener(this);
        layoutMessager.setOnClickListener(this);
        layoutProfile.setOnClickListener(this);
        layoutCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_close_dialog:{
                getDialog().dismiss();
                break;
            }
            case R.id.layout_profile:{
                showCardFrofile = !showCardFrofile;
                setVisibilityCardProfile(showCardFrofile);
                break;
            }
            case R.id.layout_messager:{
                getDialog().dismiss();
                break;
            }
            case R.id.layout_call:{
                callFriend(friend.getPhoneNumber());
                break;
            }
        }
    }

    private void callFriend(String phoneNumber) {
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_KEY);
        }else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CALL_KEY:{

                break;
            }
        }
    }

    private void setVisibilityCardProfile(boolean b){
        if (b){
            cardProfile.setVisibility(View.VISIBLE);

            TranslateAnimation animation = new TranslateAnimation(0, 0,
                    cardProfile.getHeight(), 0);
            animation.setDuration(500);
            animation.setFillAfter(true);
            cardProfile.setAnimation(animation);
        }else {
            TranslateAnimation animation = new TranslateAnimation(0, 0,
                    0, cardProfile.getHeight());
            animation.setDuration(500);
            animation.setFillAfter(true);
            cardProfile.setAnimation(animation);

            cardProfile.setVisibility(View.GONE);
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