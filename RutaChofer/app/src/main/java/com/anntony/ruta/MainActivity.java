package com.anntony.ruta;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewDebug;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AbsRuntimePermission implements LocationListener , View.OnClickListener {

    LocationManager locationManager;
    private TextView textView;
    private boolean flag;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        textView = findViewById(R.id.txtPos);
        flag  = true;
        requestAppPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                R.id.mng, 10);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    @Override
    public void onLocationChanged(Location location) {
        textView.setText("mi posicion: " + location.getLatitude() + " " + location.getLongitude());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
            Log.e("entro", "");
            if (flag) {
                textView.setText("Encendido");
                // handler.postDelayed(runnable, 1000);
                startService(new Intent(getApplicationContext(), MiServicio.class));
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1)
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                flag = false;
            } else {
                //handler.removeCallbacks(runnable);
                stopService(new Intent(getApplicationContext(), MiServicio.class));
                locationManager.removeUpdates(this);
                flag = true;
                textView.setText("Está apagado");
            }
        }

    }
}
