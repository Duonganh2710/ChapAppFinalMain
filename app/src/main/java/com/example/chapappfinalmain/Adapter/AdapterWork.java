package com.example.chapappfinalmain.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.Content;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterWork extends RecyclerView.Adapter<AdapterWork.ViewHolder> {
    List<Content> listContent;
    Context context;

    public AdapterWork(List<Content> listContent, Context context) {
        this.listContent = listContent;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Content content = listContent.get(position);
        Glide.with(context).load(content.getUrlAvatar()).into(holder.imgAvatar);
        holder.prgLoadPhoto.setVisibility(View.VISIBLE);
        if(content.getUrlImage() == "null" ){
            holder.imgMain.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(content.getUrlImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.prgLoadPhoto.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.prgLoadPhoto.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.imgMain);
        }

        holder.txtUserName.setText(content.getUserName());
        holder.txtTime.setText(content.getTime());
        holder.txtContent.setText(content.getCommentOfContent());

    }

    @Override
    public int getItemCount() {
        if(listContent.size() > 0) {
            return listContent.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgAvatar;
        TextView txtUserName, txtTime, txtContent;
        RoundedImageView imgMain;
        LinearLayout layoutLike, layoutDownload;
        ProgressBar prgLoadPhoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar_work);
            txtUserName = itemView.findViewById(R.id.txt_user_name_work);
            txtTime = itemView.findViewById(R.id.txt_time_work);
            txtContent = itemView.findViewById(R.id.txt_content_work);
            imgMain = itemView.findViewById(R.id.img_main_work);
            layoutLike = itemView.findViewById(R.id.layout_like);
            layoutDownload = itemView.findViewById(R.id.layout_download);
            prgLoadPhoto = itemView.findViewById(R.id.prg_load_photo_work);
        }
    }
}
