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
import android.widget.SearchView;

import com.example.chapappfinalmain.Activity.MainApp.ChatActivity;
import com.example.chapappfinalmain.Activity.MainApp.CreateContentActivity;
import com.example.chapappfinalmain.Adapter.AdapterFriend;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatFragment extends Fragment implements AdapterFriend.IOnclickData {
    private SearchView searchView;
    private RecyclerView listFriend;

    private AdapterFriend adapterFriend;
    private AdapterFriend.IOnclickData iOnclickData = this;

    private User user;
    private View viewLayout;
    private ArrayList<String> idChat;

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
        viewLayout = view;
        user = (User) getArguments().get("Object_user");

        idChat = new ArrayList<>();

        getDataFriend(user);


    }

    private String getAllIdChat(User user, User friend) {
        String id;
        String userID = user.getUserId();

        String idChat = userID + friend.getUserId();
        char[] arr = idChat.toCharArray();
        Arrays.sort(arr);
        id = new String(arr);
        return id;
    }

    private void getDataFriend(User userData) {
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_list_chat);

        if(userData == null) {
            Log.d("TAG", "null");
        }else {
            final List<User> userList = new ArrayList<>();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userList.clear();
                    idChat.clear();
                    for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                        User user = dataSnapshot1.getValue(User.class);
                        if(!user.getUserId().equals(userData.getUserId())) {
                            userList.add(user);
                            idChat.add(getAllIdChat(userData, user));
                        }
                    }

                    listFriend.setLayoutAnimation(layoutAnimationController);
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

    private void init(View view){
  //     searchView = view.findViewById(R.id.search_bar_list_friend);
       listFriend = view.findViewById(R.id.list_user);
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

}