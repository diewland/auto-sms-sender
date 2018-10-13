package com.diewland.schedulemaker;

import android.content.Context;
import android.util.Log;

public class MyTask {

    private static String TAG = MainActivity.TAG;

    public static void run(Context ctx, long round_no, String tel_no, String text){

        //
        // custom your task here
        //
        String my_text = "Round " + round_no + ": " + tel_no + " " + text;
        Log.d(TAG, my_text);

    }

}
