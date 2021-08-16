package com.example.chapappfinalmain.fragment.FragmentToolEditImage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.chapappfinalmain.Adapter.AdapterEditImage.AdapterColorPicker;
import com.example.chapappfinalmain.R;

import org.jetbrains.annotations.NotNull;


public class FragmentToolText extends DialogFragment {
    public static final String TAG = FragmentToolText.class.getSimpleName();
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String EXTRA_COLOR_CODE = "extra_color_code";

    Integer mColorCode;

    AdapterColorPicker adapterColorPicker;
    TextEditorListener textEditorListener;
    InputMethodManager inputMethodManager;

    Button butDone;
    EditText extComment;
    RecyclerView rcyColors;

    public interface TextEditorListener{
        void onDone(String textComment, int colorCode);
    }

    public static FragmentToolText show(@NonNull AppCompatActivity appCompatActivity,
                                                @NonNull String inputText,
                                                @ColorInt int colorCode) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, colorCode);
        FragmentToolText fragment = new FragmentToolText();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }

    //Show dialog with default text input as empty and text color white
    public static FragmentToolText show(@NonNull AppCompatActivity appCompatActivity) {
        return show(appCompatActivity,
                "", ContextCompat.getColor(appCompatActivity, R.color.white));
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tool_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        adapterColorPicker = new AdapterColorPicker(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcyColors.setLayoutManager(manager);
        rcyColors.setAdapter(adapterColorPicker);

        adapterColorPicker.setOnColorPickerClickListener(new AdapterColorPicker.OnColorPickerClickListener() {
            @Override
            public void onColorPickerClickListener(int colorCode) {
                mColorCode = colorCode;
                extComment.setTextColor(colorCode);
            }
        });

        extComment.setText(getArguments().getString(EXTRA_INPUT_TEXT));
        mColorCode = getArguments().getInt(EXTRA_COLOR_CODE);
        extComment.setTextColor(getArguments().getInt(EXTRA_COLOR_CODE));
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        butDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dismiss();
                String text = extComment.getText().toString();
                if(!TextUtils.isEmpty(text) && textEditorListener != null){
                    textEditorListener.onDone(text, mColorCode);
                }
            }
        });

    }
    public void setOnTextEditorListener(TextEditorListener textEditorListener){
        this.textEditorListener = textEditorListener;
    }
    private void init(View view) {
        butDone = view.findViewById(R.id.but_done);
        extComment = view.findViewById(R.id.ext_comment_in_photo);
        rcyColors = view.findViewById(R.id.rcy_colors);
    }
}