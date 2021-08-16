package com.example.chapappfinalmain.Adapter.AdapterEditImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chapappfinalmain.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterColorPicker extends RecyclerView.Adapter<AdapterColorPicker.ViewHoler> {
    Context context;
    LayoutInflater inflater;
    List<Integer> colorPickerColors;
    OnColorPickerClickListener onColorPickerClickListener;

    public AdapterColorPicker(Context context, LayoutInflater inflater, List<Integer> colorPickerColors) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.colorPickerColors = colorPickerColors;
    }

    public AdapterColorPicker(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        colorPickerColors = getDefaultColors(context);
    }

    public interface OnColorPickerClickListener{
       void onColorPickerClickListener(int colorCode);
   }

    @NonNull
    @NotNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.color_picker_item_list, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHoler holder, int position) {
        holder.colorPickerView.setBackgroundColor(colorPickerColors.get(position));
    }

    @Override
    public int getItemCount() {
        return colorPickerColors.size();
    }

    public void setOnColorPickerClickListener(OnColorPickerClickListener onColorPickerClickListener){
        this.onColorPickerClickListener = onColorPickerClickListener;
    }

    public static List<Integer> getDefaultColors(Context context){
        ArrayList<Integer> colorPickerColors = new ArrayList<>();
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.black));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.sky_blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.violet_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.white));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_green_color_picker));
        return colorPickerColors;
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        View colorPickerView;
        public ViewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            colorPickerView = itemView.findViewById(R.id.color_picker_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onColorPickerClickListener != null) {
                        onColorPickerClickListener.onColorPickerClickListener(colorPickerColors.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
}
