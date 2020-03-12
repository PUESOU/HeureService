package com.example.heureservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent heureeservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button serviceBtn = (Button)findViewById(R.id.serviceBtn);
        /**
        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, HeureService.class));
            }
        });
         /**/
        serviceBtn.setOnClickListener(this);

        findViewById(R.id.heureServiceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.this.isServiceRunning("com.example.heureservice.HeureService")) {
                    Toast.makeText(MainActivity.this, "Service en cours", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Service arrêté", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String intitule = ((Button)v).getText().toString();
        String btn1 = getResources().getString(R.string.btn1);
        String btn2 = getResources().getString(R.string.btn2);

        if(intitule.equals(btn1)) {
            heureeservice = new Intent(this, HeureService.class);
            startService(heureeservice);
            ((Button)v).setText(btn2);
        } else {
            stopService(heureeservice);
            heureeservice = null;
            ((Button)v).setText(btn1);
        }
    }

    private boolean isServiceRunning(String nomService) {
        ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);

        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if(nomService.equals(service.service.getClassName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(heureeservice != null) {
            stopService(heureeservice);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(heureeservice != null) {
            startService(heureeservice);
        }
    }
}
