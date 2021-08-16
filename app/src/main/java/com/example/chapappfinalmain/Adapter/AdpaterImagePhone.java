package com.example.chapappfinalmain.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.chapappfinalmain.R;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdpaterImagePhone extends RecyclerView.Adapter<AdpaterImagePhone.ViewHolder> {
    private Context context;
    private List<String> pathImageLists;
    private SelectedImageListener imageListener;

    public AdpaterImagePhone(Context context, List<String> pathImageLists, SelectedImageListener imageListener) {
        this.context = context;
        this.pathImageLists = pathImageLists;
        this.imageListener = imageListener;
    }

    public interface SelectedImageListener{
        void imageListener(String path, View view);
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_in_mobile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String path = pathImageLists.get(position);
        holder.pbLoadImage.setVisibility(View.VISIBLE);
        Glide.with(context).load(path).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.pbLoadImage.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.pbLoadImage.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.imgImagePhone);
    }

    @Override
    public int getItemCount() {
        if(pathImageLists.size() == 0 ) {
            return 0;
        }
        return pathImageLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imgImagePhone;
        ProgressBar pbLoadImage;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgImagePhone = itemView.findViewById(R.id.img_photo_in_mobile);
            pbLoadImage = itemView.findViewById(R.id.pb_load_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageListener.imageListener(pathImageLists.get(getLayoutPosition()), imgImagePhone);
                }
            });
        }
    }
}
