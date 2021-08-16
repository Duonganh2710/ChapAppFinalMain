package com.example.chapappfinalmain.Base;

import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.jar.Manifest;

public abstract class BaseFragment extends Fragment {
    private static final int READ_WRITE_STORAGE = 52;
    public boolean requestPermission(String requestPermission){
        if(ContextCompat.checkSelfPermission(getContext(), requestPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{requestPermission}, READ_WRITE_STORAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == READ_WRITE_STORAGE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Read external permission granted!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getContext(), "Read external permission!", Toast.LENGTH_LONG).show();
            }
        }

    }
}
