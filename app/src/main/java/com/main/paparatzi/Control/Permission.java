package com.main.paparatzi.Control;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class Permission {


    private Context context;
    public static final int MANUALLY_PERMISSION_REQUEST_CODE = 111;
    public static final int PERMISSIONS_REGULAR_LOCATION_REQUEST_CODE = 112;
    public static final int PERMISSIONS_READ_STORAGE_AND_CAMERA_REQUEST_CODE = 113;
    public static final int PERMISSIONS_BACKGROUND_LOCATION_REQUEST_CODE = 114;
    public static final int PERMISSIONS_CAMERA_REQUEST_CODE = 115;

    public Permission(Context context) {
        this.context = context;
    }

    private boolean checkForBackgroundLocationPermission() {
        if ( ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public boolean checkForFineLocationPermission() {
        if ( ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    public boolean checkForCoarseLocationPermission() {
        if ( ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    public boolean checkForPermission(String permission){
        if ( ContextCompat.checkSelfPermission( context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    public void requestForCamera() {
        boolean isGranted = checkForPermission(Manifest.permission.CAMERA);
        if (!isGranted) {
            ActivityCompat.requestPermissions((Activity)context,
                    new String[]{ Manifest.permission.CAMERA},
                    PERMISSIONS_CAMERA_REQUEST_CODE);
            return;
        }
    }
    public void requestForStorage(){
        boolean isGranted = checkForPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (!isGranted) {
            ActivityCompat.requestPermissions((Activity)context,
                    new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                    PERMISSIONS_READ_STORAGE_AND_CAMERA_REQUEST_CODE);
            return;
        }
    }
    public void requestForLocation() {
        boolean per1 = checkForCoarseLocationPermission();
        boolean per2 = checkForFineLocationPermission();
        boolean per3 = android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.Q || checkForBackgroundLocationPermission();

        if (!per1  ||  !per2) {
            // if i can ask for permission
            requestFirstLocationPermission();
        } else if (!per3) {
            // if i can ask for permission
            requestSecondLocationPermission();
        } else {
            Log.d("pttt", "requestForLocation: ");
        }
    }
    private void requestSecondLocationPermission() {
        // Background location permissions
        ActivityCompat.requestPermissions((Activity)context,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_MEDIA_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                PERMISSIONS_BACKGROUND_LOCATION_REQUEST_CODE);
    }
    private void requestFirstLocationPermission() {
        // Regular location permissions
        ActivityCompat.requestPermissions((Activity)context,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSIONS_REGULAR_LOCATION_REQUEST_CODE);
    }

    public boolean requestPermissionWithRationaleCheck(String permission, int code){
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,permission)) {
            Log.d("pttt", "shouldShowRequestPermissionRationale = true");
            // Show user description for what we need the permission
            ActivityCompat.requestPermissions((Activity)context,
                    new String[]{permission},
                    code);
            return true;
        } else {
            return false;
        }
    }
    public void openPermissionSettingDialog() {
        String message = "We must your permission to CAMERA ,LOCATION and STORAGE.\nPlease add the missing permission in the settings screen.";
        AlertDialog alertDialog =
                new AlertDialog.Builder(((Activity)context))
                        .setMessage(message)
                        .setPositiveButton(((Activity)context).getString(android.R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", ((Activity)context).getPackageName(), null);
                                        intent.setData(uri);
                                        ((Activity)context).startActivityForResult(intent, MANUALLY_PERMISSION_REQUEST_CODE);
                                        dialog.cancel();
                                    }
                                }).show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
}
