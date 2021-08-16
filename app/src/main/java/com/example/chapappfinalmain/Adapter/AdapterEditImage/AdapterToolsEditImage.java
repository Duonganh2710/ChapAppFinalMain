package com.example.chapappfinalmain.Adapter.AdapterEditImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chapappfinalmain.Enum.ToolType;
import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.EditImageModel.ToolModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterToolsEditImage extends RecyclerView.Adapter<AdapterToolsEditImage.ViewHolder> {

    private List<ToolModel> toolList = new ArrayList<>();
    private OnItemSelected onItemSelected;

    public interface OnItemSelected{
        void onToolSelected(ToolType toolType);
    }

    public AdapterToolsEditImage(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
        toolList.add(new ToolModel("Brush", R.drawable.ic_brush, ToolType.BRUSH));
        toolList.add(new ToolModel("Text", R.drawable.ic_text, ToolType.TEXT));
        toolList.add(new ToolModel("Eraser", R.drawable.ic_eraser, ToolType.ERASER));
        toolList.add(new ToolModel("Filter", R.drawable.ic_photo_filter, ToolType.FILTER));

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_tool, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ToolModel toolModel = toolList.get(position);
        holder.imgToolIcon.setImageResource(toolModel.getToolIcon());
        holder.txtToolName.setText(toolModel.getToolName());
    }

    @Override
    public int getItemCount() {
        return toolList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgToolIcon;
        TextView txtToolName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgToolIcon = itemView.findViewById(R.id.imgToolIcon);
            txtToolName = itemView.findViewById(R.id.txtTool);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSelected.onToolSelected(toolList.get(getLayoutPosition()).getToolType());
                }
            });
        }
    }
}
