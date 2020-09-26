package df.farmponds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Cluster_MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    Marker myMarker;
    String str_latitude,str_longitude;
    Double double_currentlatitude=0.0;
    Double double_currentlongitude=0.0;

    Button cluster_ok_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.clustermap);
        mapFragment.getMapAsync(this);


        cluster_ok_bt=(Button)findViewById(R.id.cluster_ok_bt);

        Intent intent = getIntent();
        str_latitude = intent.getStringExtra("latitude");
        str_longitude=intent.getStringExtra("longitude");

        double_currentlatitude = Double.valueOf(str_latitude);
        double_currentlongitude = Double.valueOf(str_longitude);



        cluster_ok_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng Currentlocation = new LatLng(double_currentlatitude, double_currentlongitude);

        myMarker = googleMap.addMarker(new MarkerOptions()
                .position(Currentlocation)
                .title("You are here"));



        mMap.moveCamera(CameraUpdateFactory.newLatLng(Currentlocation));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //float zoomLevel = 16.0f; //This goes up to 21
        float zoomLevel = 20.0f; //This goes up to 21
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Currentlocation, zoomLevel));


    }




    @Override
    public void onBackPressed()
    {

        Toast.makeText(getApplicationContext(),"Kindly Click on Ok Button",Toast.LENGTH_LONG).show();
    }

}
