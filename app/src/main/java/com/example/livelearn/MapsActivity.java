package com.example.livelearn;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG;
    private static double[] latLng;
    private static String title;

    public static void setLatLng(double v, double v1, String thisTitle){
        latLng = new double[]{v, v1};
        title = thisTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng zhishanele = new LatLng(latLng[0], latLng[1]);

        mMap.addMarker(new MarkerOptions().position(zhishanele).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(zhishanele));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.getUiSettings().setCompassEnabled(true);       // 左上角的指南針，要兩指旋轉才會出現
        mMap.getUiSettings().setMapToolbarEnabled(true);    // 右下角的導覽及開啟 Google Map功能
        mMap.moveCamera(CameraUpdateFactory.newLatLng(zhishanele));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(50));

    }

    public static void setBtnGoogleMap(Context context, ImageButton btnGoogleMap, double v, double v1, String title){
        btnGoogleMap.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                MapsActivity.setLatLng(v, v1, title);
                Intent intent=new Intent();
                intent.setClass(context, MapsActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
