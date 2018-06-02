package com.anntony.ruta;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Antonio Facundo on 08/02/2018.
 */

public class MiServicio extends Service  implements  LocationListener{

    LocationManager locationManager;
    Location location;
    Handler handler;
    Runnable runnable;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("punto");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000,
                10, this);

    }
    @Override
    public void onStart(Intent intent, int startId){


        handler = new Handler();
        final int delay = 1000; //milliseconds

        ref.child("lat").setValue(26);
        ref.child("log").setValue(-100);

        runnable = new Runnable(){
            public void run(){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getApplicationContext(),
                                    Manifest.permission.ACCESS_COARSE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {

                        location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                    }
                }

                if(location != null) {
                    ref.child("lat").setValue(location.getLatitude());
                    ref.child("log").setValue(location.getLongitude());
                }
                handler.postDelayed(this, delay);
            }
        };


       // handler.postDelayed(runnable, delay);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Termina", "");
        //handler.removeCallbacks(runnable);
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(Location location) {
        ref.child("lat").setValue(location.getLatitude());
        ref.child("log").setValue(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
