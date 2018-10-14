package com.diewland.schedulemaker;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {

    // request permissions dialog
    // https://stackoverflow.com/a/37184243/466693
    public static void requestPermissions(Activity that){
        int request_code = 101;
        int sms = ContextCompat.checkSelfPermission(that, Manifest.permission.SEND_SMS);
        //int phone_state = ContextCompat.checkSelfPermission(that, Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        //if (phone_state != PackageManager.PERMISSION_GRANTED) {
        //    listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        //}
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(that,listPermissionsNeeded.toArray
                (new String[listPermissionsNeeded.size()]), request_code);
        }
    }

}
