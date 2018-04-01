package com.manan.dev.ec2018app.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by yatindhingra on 11/03/18.
 */

public class MyNotificationResponse extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String eventId = intent.getStringExtra("eventId");
        String eventName = intent.getStringExtra("eventName");
        Integer uniqueId = intent.getIntExtra("uniqueId", 0);

        Intent serviceringtone = new Intent(context, MyNotificationService.class);
        serviceringtone.putExtra("eventId", eventId);
        serviceringtone.putExtra("eventName", eventName);
        serviceringtone.putExtra("uniqueId", uniqueId);
        context.startService(serviceringtone);
    }
}
