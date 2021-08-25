package com.example.chapappfinalmain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.User;

import java.util.List;

public class AdapterFriend extends RecyclerView.Adapter<AdapterFriend.ViewHolder> {
    private Context context;
    private List<User> listUser;
    private IOnclickData iOnclickData;
    public interface IOnclickData{
        void sendData(User friendData);
    }

    public AdapterFriend(Context context, List<User> listUser, IOnclickData iOnclickData) {
        this.context = context;
        this.listUser = listUser;
        this.iOnclickData = iOnclickData;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final User user = listUser.get(position);

        if(user.getImgUri() != "Default"){
            Glide.with(context).load(user.getImgUri()).into(holder.imgAvatar);
        }else {
            holder.imgAvatar.setImageResource(R.drawable.ic_avatar);
        }


    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtUserName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txt_user);
            imgAvatar = itemView.findViewById(R.id.img_item_avatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iOnclickData.sendData(listUser.get(getLayoutPosition()));
                }
            });
        }
    }
}
