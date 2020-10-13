package df.farmponds;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
/*import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;*/
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Class_GPSTracker gpstracker_obj1;

    Double double_currentlatitude = 0.0;
    Double double_currentlongitude = 0.0;


    Toolbar toolbar;
    ImageView add_newfarmpond_iv;

    EditText locationsearchtext_tv;
    String location;
    Button search_bt, submit_bt, cancel_bt;

    Marker myMarker;

    String str_fromname, str_pondmarked, str_latlong;

    String str_lat, str_long;

    PlaceAutocompleteFragment searchautocomplete_fragment;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Places.initialize(getApplicationContext(), "YOUR_API_KEY");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchautocomplete_fragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.searchautocomplete_fragment);
        locationsearchtext_tv = (EditText) findViewById(R.id.locationsearchtext_tv);
        search_bt = (Button) findViewById(R.id.search_bt);
        submit_bt = (Button) findViewById(R.id.submit_bt);
        cancel_bt = (Button) findViewById(R.id.cancel_bt);





        Intent intent = getIntent();
        str_fromname = intent.getStringExtra("from");


        str_pondmarked = "no";

        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(locationsearchtext_tv.getText().toString().trim().length()<=0)
                {
                    Toast.makeText(getApplicationContext(), "Enter location", Toast.LENGTH_SHORT).show();
                }else {

                    location = locationsearchtext_tv.getText().toString();

                    List<Address> addressList = null;

                    if (location != null || !location.equals("")
                    ) {

                        Geocoder geocoder = new Geocoder(getApplicationContext());

                        try {
                            addressList = geocoder.getFromLocationName(location, 1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        try {
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            Toast.makeText(getApplicationContext(), address.getLatitude() + " " + address.getLongitude(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error: Enter correct city name ", Toast.LENGTH_LONG).show();
                        }


                    } else {

                        Toast.makeText(getApplicationContext(), "Enter location", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });

       // Places.initialize(getApplicationContext(), "YOUR_API_KEY");
       // Places.initialize(getApplicationContext(),"@string/API_KEY");

       /* searchautocomplete_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place)
            {
                Log.e("Maps", "Place selected: " + place.getName());
               // addMarker(place);
               *//* mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);*//*
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });*/










        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (str_pondmarked.equalsIgnoreCase("yes")) {
                    Toast.makeText(getApplicationContext(), "" + str_latlong, Toast.LENGTH_LONG).show();
                    Log.e("latlong", str_latlong);

                    String str_pondlatlong = str_latlong;
                    str_pondlatlong = str_pondlatlong.substring(10);

                    str_pondlatlong = str_pondlatlong.substring(0, str_pondlatlong.length() - 1);
                    Log.e("lat:", str_pondlatlong);

                    String[] parts = str_pondlatlong.split(",");
                    str_lat = parts[0];
                    str_long = parts[1];


                    AlertDialog.Builder dialog = new AlertDialog.Builder(MapsActivity.this);
                    dialog.setCancelable(false);
                    dialog.setTitle(R.string.alert);
                    dialog.setMessage("Is the marked Pond is correct");

                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            if (str_fromname.equalsIgnoreCase("addfarmpond")) {
                                AddFarmPondActivity.statlatitude_tv.setText(str_lat);
                                AddFarmPondActivity.statlongitude_tv.setText(str_long);
                                AddFarmPondActivity.statlocatedmap_status_tv.setText("Completed");
                                AddFarmPondActivity.statlocationnotconfirmedtext_tv.setVisibility(View.GONE);
                                AddFarmPondActivity.statlocation_confirmedtext_tv.setVisibility(View.VISIBLE);

                                finish();
                            }

                            if (str_fromname.equalsIgnoreCase("Editfarmpond")) {
                                EditFarmPondDetails_Activity.statlatitude_tv.setText(str_lat);
                                EditFarmPondDetails_Activity.statlongitude_tv.setText(str_long);
                                EditFarmPondDetails_Activity.statlocatedmap_status_tv.setText("Completed");
                                EditFarmPondDetails_Activity.statlocationnotconfirmedtext_tv.setVisibility(View.GONE);
                                EditFarmPondDetails_Activity.statlocation_confirmedtext_tv.setVisibility(View.VISIBLE);
                                finish();
                            }


                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Action for "Cancel".
                                    dialog.dismiss();
                                }
                            });

                    final AlertDialog alert = dialog.create();
                    alert.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                        }
                    });
                    alert.show();


                } else {
                    Toast.makeText(getApplicationContext(), "Kindly Mark the Pond in Map", Toast.LENGTH_LONG).show();
                }
            }
        });


        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MapsActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(R.string.alert);
                dialog.setMessage("Are you sure want to locate Pond later");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                   /* AddFarmPondActivity.statlatitude_tv.setText("0.0");
                    AddFarmPondActivity.statlongitude_tv.setText("0.0");*/

                        if (str_fromname.equalsIgnoreCase("addfarmpond")) {
                            AddFarmPondActivity.statlocationnotconfirmedtext_tv.setVisibility(View.VISIBLE);
                            AddFarmPondActivity.statlocatedmap_status_tv.setText("Pending");
                            AddFarmPondActivity.statlocation_confirmedtext_tv.setVisibility(View.GONE);
                        }

                        if (str_fromname.equalsIgnoreCase("Editfarmpond")) {
                            EditFarmPondDetails_Activity.statlocationnotconfirmedtext_tv.setVisibility(View.VISIBLE);
                            EditFarmPondDetails_Activity.statlocatedmap_status_tv.setText("Pending");
                            EditFarmPondDetails_Activity.statlocation_confirmedtext_tv.setVisibility(View.GONE);
                        }

                        finish();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Action for "Cancel".
                                dialog.dismiss();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                    }
                });
                alert.show();

                // finish();
            }
        });


    }// end of OnCreate()

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device.
     * This method will only be triggered once the user has installed
     Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(final GoogleMap googleMap) {





      /*  mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng TutorialsPoint = new LatLng(15.583767, 75.7622434);
        LatLng TutorialsPoint1 = new LatLng(15.383767, 75.122434);

        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title("maps.com"));



        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint1));*/

        gpstracker_obj1 = new Class_GPSTracker(MapsActivity.this);
        if (gpstracker_obj1.canGetLocation()) {
            double_currentlatitude = gpstracker_obj1.getLatitude();
            double_currentlongitude = gpstracker_obj1.getLongitude();

        }


        if (str_fromname.equalsIgnoreCase("addfarmpond")) {
            double_currentlatitude = Double.valueOf(AddFarmPondActivity.statlatitude_tv.getText().toString());
            double_currentlongitude = Double.valueOf(AddFarmPondActivity.statlongitude_tv.getText().toString());
        }

        if (str_fromname.equalsIgnoreCase("Editfarmpond")) {
            double_currentlatitude = Double.valueOf(EditFarmPondDetails_Activity.statlatitude_tv.getText().toString());
            double_currentlongitude = Double.valueOf(EditFarmPondDetails_Activity.statlongitude_tv.getText().toString());
        }


       /* mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng Currentlocation = new LatLng(double_currentlatitude, double_currentlongitude);

        mMap.addMarker(new
                MarkerOptions().position(Currentlocation).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Currentlocation));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //float zoomLevel = 16.0f; //This goes up to 21
        float zoomLevel = 20.0f; //This goes up to 21
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Currentlocation, zoomLevel));*/


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng Currentlocation = new LatLng(double_currentlatitude, double_currentlongitude);


        /*myMarker = googleMap.addMarker(new MarkerOptions()
                .position(Currentlocation)
                .title("You are here")
                .snippet(String.valueOf(Currentlocation)));*/


        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(double_currentlatitude, double_currentlongitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = (addresses.get(0).getAddressLine(0));
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String knownName = addresses.get(0).getFeatureName();

        Log.e("Address",address+"|City "+city+"|State "+state+"knownName"+knownName);

        myMarker = googleMap.addMarker(new MarkerOptions()
                .position(Currentlocation)
                .title("You are here"));



        mMap.moveCamera(CameraUpdateFactory.newLatLng(Currentlocation));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //float zoomLevel = 16.0f; //This goes up to 21
        float zoomLevel = 20.0f; //This goes up to 21
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Currentlocation, zoomLevel));






        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng)
            {
               /* googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Your marker title")
                        .snippet("Your marker snippet"));*/

               /* googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("You are here"));

                Toast.makeText(getApplicationContext(),""+String.valueOf(latLng),Toast.LENGTH_SHORT).show();*/

              //  myMarker=null;

                if (myMarker == null)
                {
                    Log.e("if","if");
                    str_latlong="";
                    // Marker was not set yet. Add marker:
                    myMarker = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("You are here")
                            .snippet(String.valueOf(latLng)));

                    str_pondmarked="yes";
                    str_latlong=String.valueOf(latLng);
                }
                else {
                    Log.e("else","else");

                    str_latlong="";
                    myMarker.remove();

                    myMarker = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("You are here")
                            .snippet(String.valueOf(latLng)));

                    // Marker already exists, just update it's position
                   /* myMarker.setPosition(latLng);
                    myMarker.setTitle("You are here");
                   myMarker.setSnippet(String.valueOf(latLng));*/

                    str_pondmarked="yes";
                    str_latlong=String.valueOf(latLng);
                   // Log.e("else",String.valueOf(latLng));
                    //lat/lng: (15.370835294151595,75.1237140968442)

                }



            }


        });


    }





    @Override
    public void onBackPressed()
    {

       Toast.makeText(getApplicationContext(),"Kindly Click on Cancel or Submit button",Toast.LENGTH_LONG).show();
    }




}