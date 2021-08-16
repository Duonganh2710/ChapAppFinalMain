package com.example.chapappfinalmain.Adapter.AdapterEditImage;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chapappfinalmain.Adapter.AdapterFriend;
import com.example.chapappfinalmain.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ja.burhanrashid52.photoeditor.PhotoFilter;

public class AdpaterFilter extends RecyclerView.Adapter<AdpaterFilter.ViewHolder> {
    private OnFilterListener onFilterListener;
    private List<Pair<String, PhotoFilter>> listFilters = new ArrayList<>();

    public interface OnFilterListener{
        void onFilterSelected(PhotoFilter photoFilter);
    }

    public AdpaterFilter(OnFilterListener onFilterListener) {
        this.onFilterListener = onFilterListener;
        setUpListFilters();
    }



    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Pair<String, PhotoFilter> filterPair = listFilters.get(position);
        Bitmap fromAsset = getBitmapFromAsset(holder.itemView.getContext(), filterPair.first);
        holder.imgFilterView.setImageBitmap(fromAsset);
        holder.txtFilterName.setText(filterPair.second.name().replace("_", " "));
    }

    @Override
    public int getItemCount() {
        return listFilters.size();
    }

    private Bitmap getBitmapFromAsset(Context context, String strName){
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
            return BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setUpListFilters() {
        listFilters.add(new Pair<>("filters/original.jpg", PhotoFilter.NONE));
        listFilters.add(new Pair<>("filters/auto_fix.png", PhotoFilter.AUTO_FIX));
        listFilters.add(new Pair<>("filters/brightness.png", PhotoFilter.BRIGHTNESS));
        listFilters.add(new Pair<>("filters/contrast.png", PhotoFilter.CONTRAST));
        listFilters.add(new Pair<>("filters/documentary.png", PhotoFilter.DOCUMENTARY));
        listFilters.add(new Pair<>("filters/dual_tone.png", PhotoFilter.DUE_TONE));
        listFilters.add(new Pair<>("filters/fill_light.png", PhotoFilter.FILL_LIGHT));
        listFilters.add(new Pair<>("filters/fish_eye.png", PhotoFilter.FISH_EYE));
        listFilters.add(new Pair<>("filters/grain.png", PhotoFilter.GRAIN));
        listFilters.add(new Pair<>("filters/gray_scale.png", PhotoFilter.GRAY_SCALE));
        listFilters.add(new Pair<>("filters/lomish.png", PhotoFilter.LOMISH));
        listFilters.add(new Pair<>("filters/negative.png", PhotoFilter.NEGATIVE));
        listFilters.add(new Pair<>("filters/posterize.png", PhotoFilter.POSTERIZE));
        listFilters.add(new Pair<>("filters/saturate.png", PhotoFilter.SATURATE));
        listFilters.add(new Pair<>("filters/sepia.png", PhotoFilter.SEPIA));
        listFilters.add(new Pair<>("filters/sharpen.png", PhotoFilter.SHARPEN));
        listFilters.add(new Pair<>("filters/temprature.png", PhotoFilter.TEMPERATURE));
        listFilters.add(new Pair<>("filters/tint.png", PhotoFilter.TINT));
        listFilters.add(new Pair<>("filters/vignette.png", PhotoFilter.VIGNETTE));
        listFilters.add(new Pair<>("filters/cross_process.png", PhotoFilter.CROSS_PROCESS));
        listFilters.add(new Pair<>("filters/b_n_w.png", PhotoFilter.BLACK_WHITE));
        listFilters.add(new Pair<>("filters/flip_horizental.png", PhotoFilter.FLIP_HORIZONTAL));
        listFilters.add(new Pair<>("filters/flip_vertical.png", PhotoFilter.FLIP_VERTICAL));
        listFilters.add(new Pair<>("filters/rotate.png", PhotoFilter.ROTATE));
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFilterView;
        TextView txtFilterName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgFilterView = itemView.findViewById(R.id.img_filter_view);
            txtFilterName = itemView.findViewById(R.id.txt_filter_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFilterListener.onFilterSelected(listFilters.get(getLayoutPosition()).second);
                }
            });
        }
    }
}
