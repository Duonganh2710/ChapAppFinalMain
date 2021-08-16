package com.example.chapappfinalmain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.Content;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterProfileImg extends RecyclerView.Adapter<AdapterProfileImg.ViewHolder> {
    List<Content> listContent;
    IOnclickData iOnclickData;
    Context context;

    public interface IOnclickData{
       void sendData(Content content);
    }

    public AdapterProfileImg(List<Content> listContent, Context context, IOnclickData iOnclickData) {
        this.listContent = listContent;
        this.context = context;
        this.iOnclickData = iOnclickData;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_img, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        final Content content = listContent.get(position);
        Glide.with(context).load(content.getUrlImage()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnclickData.sendData(content);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listContent.size() == 0){
            return 0;
        }
        return listContent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView img;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_profile);
        }
    }
}
