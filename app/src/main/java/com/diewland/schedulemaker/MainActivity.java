package com.diewland.schedulemaker;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button btn_start;
    private Button btn_stop;
    private TextView tv_log;

    private CountDownTimer waitTimer;
    private long current_time = 0;

    // TODO config
    private int max_times    = 5;        // 5 seconds
    private int interval     = 1 * 1000; // 1 second
    private long total_times = max_times * interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_stop = (Button)findViewById(R.id.btn_stop);
        tv_log = (TextView)findViewById(R.id.tv_log);

        // TODO total_times = Long.MAX_VALUE; --> close to infinite

        // prepare ui
        btn_start.setEnabled(true);
        btn_stop.setEnabled(false);

        tv_log.setText("111111111111111111\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n" +
                       "222222222222212222\n");

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_start.setEnabled(false);
                btn_stop.setEnabled(true);

                waitTimer = new CountDownTimer(total_times, interval) {
                    public void onTick(long millisUntilFinished) {
                        current_time++;
                        custom_task(current_time, "Hello", "World");
                    }
                    public void onFinish() {
                        stop_timer();
                    }
                }.start();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop_timer();
            }
        });
    }

    // CUSTOM TASK

    private void custom_task(long current_time, String text1, String text2){
        String text = "<" + current_time + "> "+ text1 +" " + text2;
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    // TIMER FUNCTIONS

    private void stop_timer(){
        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
            current_time = 0;

            btn_start.setEnabled(true);
            btn_stop.setEnabled(false);
        }
    }

}
