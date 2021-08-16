package com.example.chapappfinalmain.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AdpaterChat extends RecyclerView.Adapter<AdpaterChat.ViewHolder> {
    private final int TYPE_RIGHT = 0;
    private final int TYPE_LEFT = 123;

    private FirebaseUser senderUser = FirebaseAuth.getInstance().getCurrentUser();
    private Context context;
    private List<Chat> listChat;

    public AdpaterChat(Context context, List<Chat> listChat) {
        this.context = context;
        this.listChat = listChat;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right, parent, false);
                return new AdpaterChat.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left, parent, false);
            return new AdpaterChat.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = listChat.get(position);
        holder.txtChat.setText(chat.getMessage());

    }

    @Override
    public int getItemCount() {
        if(listChat != null){
            return listChat.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(listChat.get(position).getSenderId().equals(senderUser.getUid())) {
            return TYPE_RIGHT;
        }else {
            return TYPE_LEFT;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtChat;
        ConstraintLayout itemChat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtChat = itemView.findViewById(R.id.txt_chat);
            itemChat = itemView.findViewById(R.id.item_chat);
        }
    }
}
