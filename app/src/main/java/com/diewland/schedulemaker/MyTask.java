package com.diewland.schedulemaker;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

public class MyTask {

    private static String TAG = MainActivity.TAG;

    public static void run(Context ctx, long round_no, String tel_no, String text){

        // 1. send sms
        // https://stackoverflow.com/a/26311288/466693
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(tel_no, null, text, null, null);

        // 2. print log
        String my_text = "Round " + round_no + ": " + tel_no + " " + text;
        Log.d(TAG, my_text);

    }

}
