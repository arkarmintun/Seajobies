package com.arkarmintun.seajobies.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by arkar on 5/3/16.
 */
public class Helper {

    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static ProgressDialog proDialog;

    public static void startLoading(Context context, String msg) {
        proDialog = new ProgressDialog(context);
        proDialog.setMessage(msg);
        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialog.setCancelable(true);
        proDialog.show();
    }

    public static void stopLoading() {
        proDialog.dismiss();
        proDialog = null;
    }
}
