package com.treasurehunt.madcamp_week3;
import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;

import android.os.Looper;
import android.os.Message;
import android.os.Process;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GpsTrackingService extends Service {
    LocationManager manager;
    public double latitude = 0.0;
    public double longitude = 0.0;
    private IBinder mIBinder = new MyBinder();
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    String device_token;
    RetrofitClient retrofitClient = new RetrofitClient();


    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            System.out.println("gts handleMessage()");
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            startLocationService();

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

    class MyBinder extends Binder{
        GpsTrackingService getService(){
            return GpsTrackingService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("gts onBind()");
        return mIBinder;
    }

    @Override
    public void onCreate() {
        System.out.println("gts onCreate()");
        
        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive work doesn't disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

        //Read the token of the phone.
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("FirebaseSettingEx", "getInstanceId failed", task.getException());
                return;
            }
            device_token = task.getResult().getToken();
            System.out.println("token: " + device_token);
            System.out.println("token length: " + device_token.length());
        });

//        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("gts onStartCommand()");
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        System.out.println("gts onDestroy()");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("gts onUnbind()");
        return super.onUnbind(intent);
    }

    private void startLocationService(){
        System.out.println("start location service");
        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        long minTime = 100; // 갱신에 필요한 최소 시간 은 1초
        float minDistance = 0; // 위치 갱신이 필요한 최소 거리 m

        boolean isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        System.out.println("isGPSenabled : "+ isGPSEnabled);
        System.out.println("isNetwordenabled : "+ isNetworkEnabled);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Don't have permission", Toast.LENGTH_LONG).show();
            return;
        }

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTime,minDistance,mLocationListener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,minTime,minDistance, mLocationListener);
    }

    private void stopLocationService(){ //gps 업뎃 중단하기
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Don't have permission", Toast.LENGTH_LONG).show();

            return;
        }
        manager.removeUpdates(mLocationListener);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            System.out.println("latitude :" + latitude);
            System.out.println("longitude :" + longitude);

            DeviceLocation deviceLocation = new DeviceLocation(device_token, Double.toString(latitude), Double.toString(longitude));
            Call<String> call = retrofitClient.apiService.hunt(deviceLocation);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        System.out.println("hunt response successful");
                    }
                    else{
                        System.out.println("hunt response not successful");
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println("hunt response on failure");
                }

            });
//


            //stopLocationService();
        }

    };

}
