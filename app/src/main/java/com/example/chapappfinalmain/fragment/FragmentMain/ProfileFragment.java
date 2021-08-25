package com.example.chapappfinalmain.fragment.FragmentMain;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chapappfinalmain.Activity.MainApp.FristActivity;
import com.example.chapappfinalmain.Activity.MainApp.ProfileActivity;
import com.example.chapappfinalmain.Adapter.AdapterPaperProfile;
import com.example.chapappfinalmain.Enum.DataOfUser;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Pair;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    CircleImageView imgAvataProfile;
    TextView txtUserNameProfile, txtEmailProfile, txtNumberProfile;
    TabLayout tabTitle;
    ViewPager paperActivity;
    CardView cardViewProfile;


    AdapterPaperProfile adapterPaperProfile;

    FirebaseAuth auth;

    public static ProfileFragment getInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object_user", user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        User user = (User) getArguments().get("Object_user");
        if(user == null) {
            Log.e("TAG123", "null");
        }else {
            setLayoutDataUser(user);
        }
        setLayoutWork();

        cardViewProfile.setOnClickListener(this);

    }

    private void setLayoutWork() {
        adapterPaperProfile = new AdapterPaperProfile(getChildFragmentManager());
        paperActivity.setAdapter(adapterPaperProfile);

        paperActivity.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabTitle));

        tabTitle.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                paperActivity.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setLayoutDataUser(User user) {
        Glide.with(getActivity()).load(user.getImgUri()).into(imgAvataProfile);
        txtUserNameProfile.setText(user.getUserName());
        txtNumberProfile.setText(user.getPhoneNumber());
        txtEmailProfile.setText(user.getEmail());
    }

    private void init(View view) {
        imgAvataProfile = view.findViewById(R.id.img_avatar_profile);
        txtUserNameProfile = view.findViewById(R.id.txt_username_profile);
        txtEmailProfile = view.findViewById(R.id.txt_email_profile);
        txtNumberProfile = view.findViewById(R.id.txt_phone_profile);
        tabTitle = (TabLayout) view.findViewById(R.id.tab_title);
        paperActivity = (ViewPager) view.findViewById(R.id.paper_activity);
        cardViewProfile = view.findViewById(R.id.card_view_profile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_view_profile:{
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                ActivityOptions options = ActivityOptions.
                        makeSceneTransitionAnimation((Activity) getContext(), imgAvataProfile, "IMAGE_AVATAR");
                getContext().startActivity(intent, options.toBundle());

                break;
            }
        }
    }
}