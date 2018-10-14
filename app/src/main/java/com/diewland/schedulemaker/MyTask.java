package com.diewland.schedulemaker;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class MyTask {

    private static String TAG = MainActivity.TAG;

    // https://stackoverflow.com/a/26311288/466693
    private static void sendSMS(Context ctx, String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(ctx, "Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(ctx, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public static void run(Context ctx, long round_no, String tel_no, String text){

        // 1. send sms
        sendSMS(ctx, tel_no, text);

        // 2. print log
        String my_text = "Round " + round_no + ": " + tel_no + " " + text;
        Log.d(TAG, my_text);

    }

}
