package com.anntony.myapplication.Views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.anntony.myapplication.Fragment.NoticiasFragment;
import com.anntony.myapplication.R;
import com.anntony.myapplication.RuntimePermissions.AbsRuntimePermission;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class MapsActivity extends AbsRuntimePermission implements OnMapReadyCallback, LocationListener, View.OnClickListener
, NoticiasFragment.OnFragmentInteractionListener{

    private GoogleMap mMap;
    FirebaseDatabase database;
    DatabaseReference ref;
    boolean flag, flag_2;
    LocationManager locationListener;
    Location location;
    MarkerOptions marker, yoMarker;
    FloatingActionButton yo, camion, noticias;
    ValueEventListener valueEventListener;
    KmlLayer layer;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("punto");
        flag = true;
        flag_2 = true;
        locationListener = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        yo = findViewById(R.id.yo);
        camion = findViewById(R.id.camion);
        noticias = findViewById(R.id.noticias);
        noticias.setOnClickListener(this);
        yo.setOnClickListener(this);
        camion.setOnClickListener(this);


    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        fragment = new NoticiasFragment();
        requestAppPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 10, 10);
        try {
            layer = new KmlLayer(mMap, R.raw.ruta,getApplicationContext());
           // layer.addLayerToMap();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



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
        locationListener.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,
                10, this);
        LatLng sydney = new LatLng(26.50343799668589, -100.17156139305972);

        marker = new MarkerOptions()
                .position(sydney)
                .title("Ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_servicio_de_transporte_48));



        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Add a marker in Sydney and move the camera
        //mMap.addMarker(marker);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double lat = dataSnapshot.child("lat").getValue(double.class);
                double lng = dataSnapshot.child("log").getValue(double.class);
                LatLng latLng = new LatLng(lat, lng);
                marker.position(latLng);
                mMap.clear();
               /* try {
                    //layer.addLayerToMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }*/
                mMap.addMarker(marker);
                if(yoMarker != null)
                    mMap.addMarker(yoMarker);
                if(flag){

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                    flag = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(valueEventListener);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());


            yoMarker = new MarkerOptions()
                    .position(sydney)
                    .title("Yo")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_ubicaci_n_del_usuario_48));


        yoMarker.position(sydney);
        mMap.clear();
        /*try {
           // layer.addLayerToMap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }*/
        mMap.addMarker(marker);
        mMap.addMarker(yoMarker);
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

            case R.id.yo:
                if(yoMarker != null) {
                    Toast.makeText(getApplicationContext(), "Mi posición.", Toast.LENGTH_SHORT).show();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(yoMarker.getPosition(), 15.0f));
                }else{
                    Toast.makeText(getApplicationContext(), "Necesitas moverte un poco para obtener tu posición.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.camion:
                Toast.makeText(getApplicationContext(), "Camión.", Toast.LENGTH_SHORT).show();
                if(marker != null)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15.0f));
                break;

            case R.id.noticias:
                //Agregar fragment por defecto
                if(flag_2){
                    getSupportFragmentManager().beginTransaction().add(R.id.map, fragment).commit();
                    flag_2 = !flag_2;
                }else{
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    flag_2 = !flag_2;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ref.removeEventListener(valueEventListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
