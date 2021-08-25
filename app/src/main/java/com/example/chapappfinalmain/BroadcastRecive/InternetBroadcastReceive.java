package com.example.chapappfinalmain.BroadcastRecive;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.chapappfinalmain.R;

import java.util.logging.Logger;

public class InternetBroadcastReceive extends BroadcastReceiver {
    private Dialog dialogDisconnection;
    public boolean isConnect = true;
    private boolean isShowDialog = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkAvaible(context)) {
            setConnect(true);
            if(isShowDialog()){
                dialogDisconnection.dismiss();
            }
        } else {
            openDialog(context);
            setConnect(false);
        }
    }

    private void openDialog(Context context) {
        dialogDisconnection = new Dialog(context);
        dialogDisconnection.setContentView(R.layout.dialog_disconnect);
        dialogDisconnection.setCancelable(true);
        dialogDisconnection.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialogDisconnection.getWindow().getAttributes().windowAnimations = R.anim.layout_animation_list_chat;
        dialogDisconnection.show();

        setShowDialog(true);
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }


    private boolean isShowDialog() {
        return isShowDialog;
    }

    private void setShowDialog(boolean showDialog) {
        isShowDialog = showDialog;
    }

    private boolean isNetworkAvaible(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }

}
