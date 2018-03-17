package com.manan.dev.ec2018app.Utilities;

/**
 * Created by subham on 17/03/18.
**/
 import android.app.Application;

 public class MyApplication extends Application {

 private static MyApplication mInstance;

 @Override
 public void onCreate() {
 super.onCreate();

 mInstance = this;
 }

 public static synchronized MyApplication getInstance() {
 return mInstance;
 }

 public void setConnectivityListener(ConnectivityReciever.ConnectivityReceiverListener listener) {
 ConnectivityReciever.connectivityReceiverListener = listener;
 }
 }