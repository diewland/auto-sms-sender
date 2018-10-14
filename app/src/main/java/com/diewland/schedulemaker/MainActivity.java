package com.diewland.schedulemaker;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "diewland";

    // schedule variables
    private int max_times;
    private int interval;
    private long total_times;
    private CountDownTimer waitTimer;
    private long round_no = 0;

    // screen objects
    private EditText et_tel_no;
    private EditText et_text;
    private EditText et_interval;
    private Spinner sp_interval_unit;
    private EditText et_times;
    private CheckBox cb_forever;
    private Button btn_start;
    private Button btn_stop;
    private TextView tv_console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_tel_no = (EditText) findViewById(R.id.et_tel_no);
        et_text = (EditText) findViewById(R.id.et_text);
        et_interval = (EditText) findViewById(R.id.et_interval);
        sp_interval_unit = (Spinner) findViewById(R.id.sp_interval_unit);
        et_times = (EditText) findViewById(R.id.et_times);
        cb_forever = (CheckBox) findViewById(R.id.cb_forever);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        tv_console = (TextView) findViewById(R.id.tv_console);

        // set default config
        et_interval.setText("10");
        et_times.setText("6");

        // prepare app
        stop_timer();
        tv_console.setText("=====================\n" +
                           "For Long time usage:\n" +
                           "=====================\n" +
                           "1. Plug in power adapter / power bank\n" +
                           "2. Turn off display sleep");

        // bind forever checkbox
        cb_forever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    et_times.setText("99999");
                    et_times.setEnabled(false);
                }
                else {
                    et_times.setEnabled(true);
                }
            }
        });

        // bind start button
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // require fill all fields
                if(et_tel_no.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill Tel.No", Toast.LENGTH_LONG).show();
                    et_tel_no.requestFocus();
                    return;
                }
                else if(et_text.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill Text", Toast.LENGTH_LONG).show();
                    et_text.requestFocus();
                    return;
                }
                else if(et_interval.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill Interval", Toast.LENGTH_LONG).show();
                    et_interval.requestFocus();
                    return;
                }
                else if(et_times.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill Times", Toast.LENGTH_LONG).show();
                    et_times.requestFocus();
                    return;
                }

                // block screen
                et_tel_no.setEnabled(false);
                et_text.setEnabled(false);
                et_interval.setEnabled(false);
                sp_interval_unit.setEnabled(false);
                et_times.setEnabled(false);
                cb_forever.setEnabled(false);
                btn_start.setEnabled(false);
                btn_stop.setEnabled(true);
                reset_console();

                // calculate schedule variables
                max_times = Integer.parseInt(et_times.getText().toString());
                interval = Integer.parseInt(et_interval.getText().toString());
                String interval_unit = sp_interval_unit.getSelectedItem().toString();
                switch (interval_unit) {
                    case "Secs":
                        interval *= 1000; // milliseconds
                        break;
                    case "Mins":
                        interval *= 1000 * 60;
                        break;
                    case "Hrs":
                        interval *= 1000 * 60 * 24;
                        break;
                }
                total_times = (max_times + 1) * interval;

                // start timer
                waitTimer = new CountDownTimer(total_times, interval) {
                    public void onTick(long millisUntilFinished) {
                        round_no++;

                        // get tel_no, text
                        String tel_no = et_tel_no.getText().toString();
                        String text = et_text.getText().toString();

                        // execute task
                        MyTask.run(getApplicationContext(), round_no, tel_no, text);

                        // update log
                        prepend_console("=> " + tel_no + " " + text, false);
                        prepend_console("Round #" + round_no, true);

                        if(round_no == max_times){
                            stop_timer();
                        }
                    }
                    public void onFinish() {
                        prepend_console("onFinish", true);
                    }
                }.start();
            }
        });

        // bind stop button
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop_timer();
            }
        });
    }

    // TIMER FUNCTION
    private void stop_timer(){
        et_tel_no.setEnabled(true);
        et_text.setEnabled(true);
        et_interval.setEnabled(true);
        sp_interval_unit.setEnabled(true);
        et_times.setEnabled(true);
        cb_forever.setEnabled(true);
        btn_start.setEnabled(true);
        btn_stop.setEnabled(false);

        round_no = 0;

        if(waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
        }
    }

    // CONSOLE FUNCTIONS
    private void reset_console(){
        tv_console.setText("");
    }
    private void prepend_console(String new_text, Boolean show_dt){
        String all_text = "";
        // show datetime
        if(show_dt){
            Calendar c = Calendar.getInstance();
            String dt_patt = "YYYY-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(dt_patt);
            String d = sdf.format(c.getTime());
            all_text = d + " ";
        }

        String prev_text = tv_console.getText().toString();
        all_text += new_text + "\n" + prev_text;
        tv_console.setText(all_text);
    }

}
