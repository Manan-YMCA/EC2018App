package com.manan.dev.ec2018app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    private static SensorManager sensorService;
    private Sensor sensor;
    private ImageView confettiImgView1, confettiImgView2, confettiImgView3, confettiImgView4;
    float current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        confettiImgView1 = (ImageView) findViewById(R.id.confetti1);
        confettiImgView2 = (ImageView) findViewById(R.id.confetti2);
        confettiImgView3 = (ImageView) findViewById(R.id.confetti3);
        confettiImgView4 = (ImageView) findViewById(R.id.confetti4);
        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (sensor != null) {
            sensorService.registerListener(mySensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            current = 0f;
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
        } else {
            Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(this, "ORIENTATION Sensor not found",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }
    private SensorEventListener mySensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // angle between the magnetic north direction
            // 0=North, 90=East, 180=South, 270=West
            final float azimuth = event.values[0];
            Log.d("hey",azimuth+"");

            if(current != azimuth) {

                confettiImgView1.animate().rotation(azimuth).setDuration(300);
                confettiImgView2.animate().rotation(180 - azimuth).setDuration(300);
                confettiImgView3.animate().rotation(azimuth).setDuration(300);
                confettiImgView4.animate().rotation(180 - azimuth).setDuration(300);
            }

            current = azimuth;

            //Toast.makeText(AboutActivity.this, String.valueOf(azimuth), Toast.LENGTH_SHORT).show();
            // use this data to translate confetti/bubbles

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensor != null) {
            sensorService.unregisterListener(mySensorEventListener);
        }
    }
}
