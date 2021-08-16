package com.example.chapappfinalmain.BroadcastRecive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.logging.Logger;

public class InternetBroadcastReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            if (isNetworkAvaible(context)) {
                Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Disconnected", Toast.LENGTH_LONG).show();
            }
        Log.e("TAF", "null");
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
