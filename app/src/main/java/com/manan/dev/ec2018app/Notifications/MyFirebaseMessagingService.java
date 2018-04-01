package com.manan.dev.ec2018app.Notifications;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.manan.dev.ec2018app.R;

import java.util.Date;

/**
 * Created by yatindhingra on 21/03/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private int Unique_Integer_Number;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        System.out.println("tototototo");
        // There are two types of messages data messages and notification messages. Data messages are handled here in onMessageReceived whether the app is in the foreground or background. Data messages are the type traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app is in the foreground. When the app is in the background an automatically generated notification is displayed. *//*
        String notificationTitle = null, notificationBody = null;
        String dataTitle = null, dataMessage = null;


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            dataTitle = remoteMessage.getData().get("title");
            dataMessage = remoteMessage.getData().get("message");
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        //sendNotification(notificationTitle, notificationBody, dataTitle, dataMessage);

        sendNotification(remoteMessage, notificationTitle, notificationBody, dataTitle, dataMessage);

    }

    /**
     * //     * Create and show a simple notification containing the received FCM message.
     * //
     */
    private void sendNotification(RemoteMessage remoteMessage, String notificationTitle, String notificationBody, String dataTitle, String dataMessage) {

        System.out.println("qweqwrwr");
        Intent intent = new Intent(remoteMessage.getNotification().getClickAction());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Unique_Integer_Number = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notfication_icon)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Unique_Integer_Number /* ID of notification */, notificationBuilder.build());

    }
}
