package com.finedust.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.annotation.NonNull;

/**
 * Created by CH on 2017-09-02.
 */

public class CheckConnectivity {
    private static final String SETTINGS_PACKAGE = "com.android.settings";
    private static final String SETTINGS_CLASS_DATA_USAGE_SETTINGS = "com.android.settings.Settings$DataUsageSummaryActivity";

    /*  Network Connectivity */
    public static boolean checkNetworkConnection(@NonNull Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }

    public static void showNetworkDisabledAlert(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("앱을 사용하기 위해서 네트워크가 필요합니다.")
                .setCancelable(true)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent networkEnableOptions = new Intent(Intent.ACTION_MAIN, null);
                        networkEnableOptions.setComponent(new ComponentName(SETTINGS_PACKAGE, SETTINGS_CLASS_DATA_USAGE_SETTINGS));
                        activity.startActivity(networkEnableOptions);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create().show();

    }

    /*  GpsEnabled */
    public static void checkGpsEnabled(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            createGpsDisabledAlert(activity);

    }

    private static void createGpsDisabledAlert(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("위치정보 사용권한이 필요")
                .setMessage("정확한 위치를 찾기 위해 GPS 기능이 필요합니다.")
                .setCancelable(false)
                .setPositiveButton("GPS ON",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent gpsEnableOptions = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                activity.startActivity(gpsEnableOptions);
                            }
                        })
                .setNegativeButton("GPS OFF",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create().show();
    }


}
