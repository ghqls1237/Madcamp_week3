package com.example.madcamp_week3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.Login;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.w3c.dom.Text;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public double latitude = 0.0;
    public double longitude = 0.0;
    LocationManager manager;
    public Button logout_btn;
    FirebaseAuth auth;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("main activity");
        super.onCreate(savedInstanceState);

        context = this;
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = null;
        try {
            adapter = new VPAdapter(getSupportFragmentManager());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        vp.setAdapter(adapter);

        //tab과 viewpager 연동시키는 과정
        TabLayout tab=findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
//                moveTaskToBack(false);
//                finish();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                ((Activity)context).finish();
            }
        });

        startLocationService();
    }

    private void startLocationService(){
        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        long minTime = 10; // 갱신에 필요한 최소 시간 은 1초
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


            //stopLocationService();
        }
        
    };

    private void logout(){
        auth.signOut();
        System.out.println("logout");
        System.out.println("current user: "+auth.getCurrentUser());
    }
}