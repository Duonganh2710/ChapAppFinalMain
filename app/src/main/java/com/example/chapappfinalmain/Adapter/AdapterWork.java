package com.example.chapappfinalmain.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterWork extends RecyclerView.Adapter<AdapterWork.ViewHolder> {
    FirebaseUser user;

    List<Content> listContent;
    Context context;
    ClickLikeAndComment onClickLikeAndComment;

    public interface ClickLikeAndComment{
        void OnClickLiKeContent(String idContent, boolean isLiked);
        void OnClickComment(String idContent);
    }


    public AdapterWork(List<Content> listContent, Context context, ClickLikeAndComment onClickLikeAndComment) {
        this.listContent = listContent;
        this.context = context;
        this.onClickLikeAndComment = onClickLikeAndComment;
        this.user = FirebaseAuth.getInstance().getCurrentUser();
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
        isLiked(content.getIdContent(), holder.imgLike);
        numLike(content.getIdContent(), holder.txtLike);

        holder.txtUserName.setText(content.getUserName());
        holder.txtTime.setText(content.getTime());
        holder.txtContent.setText(content.getCommentOfContent());

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

        holder.layoutLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.imgLike.getTag().equals("Liked")){
                    onClickLikeAndComment.OnClickLiKeContent(content.getIdContent(), true);
                }else {
                    onClickLikeAndComment.OnClickLiKeContent(content.getIdContent(), false);
                    Log.e("TAG", holder.imgLike.getTag().toString());
                }
            }
        });

    }

    private void isLiked(String idContent, ImageView imgLike){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Like").
                child(idContent);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child(user.getUid()).exists()){
                    imgLike.setImageResource(R.drawable.ic_liked);
                    imgLike.setTag("Liked");
                }else {
                    imgLike.setImageResource(R.drawable.ic_like);
                    imgLike.setTag("Default");
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void numLike(String idContent, TextView txtNumLike){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Like").
                child(idContent);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                txtNumLike.setText(snapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
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
        ImageView imgLike;
        TextView txtUserName, txtTime, txtContent, txtLike;
        RoundedImageView imgMain;
        LinearLayout layoutLike, layoutDownload;
        ProgressBar prgLoadPhoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar_work);
            txtUserName = itemView.findViewById(R.id.txt_user_name_work);
            txtTime = itemView.findViewById(R.id.txt_time_work);
            txtContent = itemView.findViewById(R.id.txt_content_work);
            txtLike = itemView.findViewById(R.id.txt_number_like);
            imgMain = itemView.findViewById(R.id.img_main_work);
            layoutLike = itemView.findViewById(R.id.layout_like);
            layoutDownload = itemView.findViewById(R.id.layout_download);
            prgLoadPhoto = itemView.findViewById(R.id.prg_load_photo_work);
            imgLike = itemView.findViewById(R.id.img_like);

        }
    }
}
