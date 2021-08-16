package com.example.chapappfinalmain.fragment.FragmentToolEditImage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.chapappfinalmain.Adapter.AdapterEditImage.AdapterColorPicker;
import com.example.chapappfinalmain.BottomSheetDialogFragment;
import com.example.chapappfinalmain.R;

import org.jetbrains.annotations.NotNull;

public class FragmentToolBrush extends BottomSheetDialogFragment implements SeekBar.OnSeekBarChangeListener{
    SeekBar sbBrush, sbOpacity;
    RecyclerView rcyColor;

    AdapterColorPicker adapterColorPicker;

    private Properties mProperties;

    public FragmentToolBrush() {
    }

    public FragmentToolBrush(Properties mProperties) {
        this.mProperties = mProperties;
    }

    public interface Properties{
        void onColorChanged(int colorCode);
        void onOpacityChanged(int opacity);
        void onBrushSizeChanged(int brushSize);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tool_brush, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        sbBrush = view.findViewById(R.id.sbSize);
        sbOpacity = view.findViewById(R.id.sbOpacity);
        rcyColor = view.findViewById(R.id.rcy_color);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcyColor.setLayoutManager(layoutManager);
        rcyColor.setHasFixedSize(true);
        adapterColorPicker = new AdapterColorPicker(getContext());
        adapterColorPicker.setOnColorPickerClickListener(new AdapterColorPicker.OnColorPickerClickListener() {
            @Override
            public void onColorPickerClickListener(int colorColor) {
                if(mProperties != null) {
                    dismiss();
                    mProperties.onColorChanged(colorColor);
                }
            }
        });
        rcyColor.setAdapter(adapterColorPicker);


        sbOpacity.setOnSeekBarChangeListener(this);
        sbBrush.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.sbOpacity:{
                if(mProperties != null) {
                    mProperties.onOpacityChanged(progress);
                }
                break;
            }
            case R.id.sbSize:{
                if(mProperties != null){
                    mProperties.onBrushSizeChanged(progress);
                }
                break;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}