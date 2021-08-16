package com.example.chapappfinalmain.Activity.MainApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chapappfinalmain.Adapter.AdpaterChat;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.Chat;
import com.example.chapappfinalmain.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{
    DatabaseReference reference;

    AdpaterChat adpaterChat;

    ImageButton butBack, butMore, butSendMessage;
    CircleImageView imgAvatar;
    TextView txtUserName;
    RecyclerView listItemMessage;
    EditText extMessage;

    String chatId;
    User friendData, userData;
    ArrayList<Chat> chatList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();

        getData();
        chatId = sortID(userData.getUserId(), friendData.getUserId());

        setLayout(friendData.getUserName(), friendData.getImgUri());
        loadListChat();

        butSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(extMessage == null) {
                    Toast.makeText(getApplicationContext(), "Message is null", Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(userData.getUserId(), friendData.getUserId(), extMessage.getText().toString());
                    txtUserName.setText("");
                }
            }
        });

    }
    private String sortID(String userID, String friendID){
        String id = userID + friendID;
        char[] arr = id.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }

    private void loadListChat() {
        listItemMessage.setHasFixedSize(true);
        listItemMessage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reference = FirebaseDatabase.getInstance().getReference("Chats").child(chatId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    Chat chat = dataSnapshot1.getValue(Chat.class);
                    chatList.add(chat);
                    Log.e("TAG", chat.toString());
                }
                adpaterChat = new AdpaterChat(getApplicationContext(), chatList);
                listItemMessage.setAdapter(adpaterChat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String uid, String userId, String toString) {
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("senderId", uid);
        hashMap.put("receiveId", userId);
        hashMap.put("message", toString);
        reference.child(chatId).push().setValue(hashMap);

    }

    private void setLayout(String friendName, String urlAvatarFriend) {
        txtUserName.setText(friendName);
        Glide.with(getApplicationContext()).load(urlAvatarFriend).into(imgAvatar);
    }

    private void getData() {
        Intent intent = getIntent();
        friendData = (User) intent.getSerializableExtra("FriendData");
        userData = (User) intent.getSerializableExtra("UserData");


    }

    private void init(){
        butBack = findViewById(R.id.but_back);
        butMore = findViewById(R.id.but_more);
        butSendMessage = findViewById(R.id.but_send_message);
        imgAvatar = findViewById(R.id.img_avatar_chat);
        txtUserName = findViewById(R.id.txt_user_chat);
        listItemMessage = findViewById(R.id.list_chat_item);
        extMessage = findViewById(R.id.ext_message);

        butBack.setOnClickListener(this);
        butMore.setOnClickListener(this);
        butSendMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:{
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.but_more:{
                break;
            }
            case R.id.but_send_message:{
                if(extMessage.getText().toString() == null){
                        Toast.makeText(getApplicationContext(), "Messager is null", Toast.LENGTH_SHORT).show();
                }else {

                }
                break;
            }
        }
    }
}