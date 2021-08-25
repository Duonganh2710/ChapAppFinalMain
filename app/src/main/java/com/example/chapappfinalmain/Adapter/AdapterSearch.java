package com.example.chapappfinalmain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chapappfinalmain.R;
import com.example.chapappfinalmain.model.Search;
import com.example.chapappfinalmain.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {
    private Context context;
    private List<Search> searchLists;
    private OnClickSearchData onClickSearchData;

    public interface OnClickSearchData{
        void listenerData(User user);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Search search = searchLists.get(position);

    }

    @Override
    public int getItemCount() {
        if(searchLists.size() > 0) {
            return searchLists.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgData;
        TextView txtTextData;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgData = (ImageView) itemView.findViewById(R.id.img_search_item);
            txtTextData = itemView.findViewById(R.id.txt_text_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSearchData.listenerData(searchLists.get(getLayoutPosition()).getUser());
                }
            });
        }
    }
}
