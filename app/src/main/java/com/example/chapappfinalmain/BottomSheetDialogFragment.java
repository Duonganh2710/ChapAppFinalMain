package com.example.chapappfinalmain;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetDialogFragment extends AppCompatDialogFragment {
    public BottomSheetDialogFragment() {
    }
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new BottomSheetDialog(this.getContext(), this.getTheme());
    }
}
