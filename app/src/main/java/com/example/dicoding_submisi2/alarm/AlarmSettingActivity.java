package com.example.dicoding_submisi2.alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dicoding_submisi2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmSettingActivity extends AppCompatActivity {
    Switch upcomingSwitch, dailySwitch;
    SharedPreferences sharedPreferences;

    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("onCreate", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);
        sharedPreferences = this.getSharedPreferences("com.example.myalarmmanager", Context.MODE_PRIVATE);
        upcomingSwitch = findViewById(R.id.upcomingSwitch);
        dailySwitch = findViewById(R.id.dailySwtich);
        alarmReceiver = new AlarmReceiver();
        upcomingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    sharedPreferences.edit().putString("upcoming", "On").apply();
                    String upcomingState =sharedPreferences.getString("upcoming","");
                    alarmReceiver.setRepeatingAlarm(AlarmSettingActivity.this, AlarmReceiver.TYPE_UPCOMING, "08:00", "Upcoming Alarm");
                    Toast.makeText(getBaseContext(),"upcoming state " +  upcomingState, Toast.LENGTH_SHORT).show();


                }
                else{
                    sharedPreferences.edit().putString("upcoming", "Off").apply();
                    String upcomingState =sharedPreferences.getString("upcoming","");
                    Toast.makeText(getBaseContext(), "upcoming state "+ upcomingState, Toast.LENGTH_SHORT).show();
                    alarmReceiver.cancelAlarm(AlarmSettingActivity.this, AlarmReceiver.TYPE_UPCOMING);

                }

            }
        });

        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    sharedPreferences.edit().putString("daily", "On").apply();
                    String dailyState =sharedPreferences.getString("daily","");
                    Toast.makeText(getBaseContext(), dailyState, Toast.LENGTH_SHORT).show();
                    alarmReceiver.setRepeatingAlarm(AlarmSettingActivity.this, AlarmReceiver.TYPE_REPEATING,
                            "07:00", "Daily Alarm");

                }
                else{
                    sharedPreferences.edit().putString("daily", "Off").apply();
                    String dailyState =sharedPreferences.getString("daily","");
                    Toast.makeText(getBaseContext(), dailyState, Toast.LENGTH_SHORT).show();
                    alarmReceiver.cancelAlarm(AlarmSettingActivity.this, AlarmReceiver.TYPE_REPEATING);
                }
            }
        });

    }

    public void switchState(){
        String dailyState = sharedPreferences.getString("daily", "");
        Log.i("dailyState", dailyState);
        if (dailyState.equals("On")){
            dailySwitch.setChecked(true);
            alarmReceiver.setRepeatingAlarm(AlarmSettingActivity.this, AlarmReceiver.TYPE_REPEATING,
                    "07:00", "Daily Alarm");
        }
        else{
            dailySwitch.setChecked(false);
            alarmReceiver.cancelAlarm(AlarmSettingActivity.this, AlarmReceiver.TYPE_REPEATING);

        }
        String upcomingState = sharedPreferences.getString("upcoming", "");
        Log.i("upcomingState", upcomingState);
        if (upcomingState.equals("On")){
            upcomingSwitch.setChecked(true);
            alarmReceiver.setRepeatingAlarm(AlarmSettingActivity.this, AlarmReceiver.TYPE_UPCOMING, "08:00", "Upcoming Alarm");


        }
        else{
            upcomingSwitch.setChecked(false);
            alarmReceiver.cancelAlarm(AlarmSettingActivity.this, AlarmReceiver.TYPE_UPCOMING);

        }

    }








}