package df.farmpondstwo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import df.farmpondstwo.Models.Class_farmponddetails;

public class EachFarmPondDetails_Activity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView add_newfarmpond_iv;

    private ListView farmpondlist_listview;

    Class_farmponddetails[] class_farmponddetails_array_obj;

    Class_GPSTracker gpstracker_obj, gpstracker_obj2;
    Double double_currentlatitude = 0.0;
    Double double_currentlongitude = 0.0;
    String str_latitude, str_longitude;
    String str_gps_yes;
    Class_GPSTracker2  gpstracker_obj3;


    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;

    private boolean isGPS = false;
    private boolean canGetLocation = true;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    boolean isGPSON = false;
    LocationManager locationManager;
    Location loc;
    ProgressDialog dialog_location;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_farm_pond_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
        // Set upon the actionbar
        setSupportActionBar(toolbar);
        // Now use actionbar methods to show navigation icon and title
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       // str_farmpondbaseimage_url = Class_URL.URL_farmpondbaselink.toString().trim();

        TextView title = (TextView) toolbar.findViewById(R.id.title_name);
        add_newfarmpond_iv = (ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
        title.setText("FarmPond Details");
        getSupportActionBar().setTitle("");

        str_gps_yes = "no";
        farmpondlist_listview = (ListView) findViewById(R.id.farmpondlist_listview);



        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        dialog_location = new ProgressDialog(EachFarmPondDetails_Activity.this);

        add_newfarmpond_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {



                if (gps_enable())
                {
                    locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
                    isGPSON = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        if (permissionsToRequest.size() > 0)
                        {
                            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                                    ALL_PERMISSIONS_RESULT);
                          //  Log.d(TAG, "Permission requests");
                            canGetLocation = false;
                        }
                    }

                  //  getLocation();

                    getLocation2();

                }



                    gpstracker_obj = new Class_GPSTracker(EachFarmPondDetails_Activity.this);


                if(1>=3)
                {
                if (gpstracker_obj.canGetLocation()) {
                    double_currentlatitude = gpstracker_obj.getLatitude();
                    double_currentlongitude = gpstracker_obj.getLongitude();


                    str_latitude = Double.toString(double_currentlatitude);
                    str_longitude = Double.toString(double_currentlongitude);


                    Log.e("gpslat", str_latitude);
                    Log.e("gpslong", str_longitude);

                    Intent intent_addfarmpondactivity = new Intent(EachFarmPondDetails_Activity.this, AddFarmPondActivity.class);
                    /*intent_addfarmpondactivity.putExtra("farmername", str_farmername);
                    intent_addfarmpondactivity.putExtra("farmer_id", str_farmer_id);*/
                    startActivity(intent_addfarmpondactivity);
                    finish();


                } else {
                    gpstracker_obj.showSettingsAlert();
                }

            }


            }
        });



    }//end Of create























    private class Holder {
        TextView holder_farmername;
        TextView holder_pondwidth;
        TextView holder_pondheight;
        TextView holder_ponddepth;
        TextView holder_farmer_id;
        TextView holder_farmpond_id;
        ImageView holder_farmpond_image1;
        ImageView holder_farmpond_image2;
        ImageView holder_farmpond_image3;
        ImageView holder_editfarmerponddetails;
    }


    public class CustomAdapter extends BaseAdapter {


        public CustomAdapter() {

            super();
            Log.d("Inside CustomAdapter()", "Inside CustomAdapter()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(class_farmponddetails_array_obj.length);
            System.out.println("class_farmponddetails_array_obj.length" + x);
            return class_farmponddetails_array_obj.length;
        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            System.out.println("getItem position" + x);
            Log.d("getItem position", "x");
            return class_farmponddetails_array_obj[position];
        }


        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);
            System.out.println("getItemId position" + x);
            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Holder holder;

            Log.d("CustomAdapter", "position: " + position);

            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_row_item_farmponddetails, parent, false);


                //


                holder.holder_farmername = (TextView) convertView.findViewById(R.id.farmername_tv);
                holder.holder_pondwidth = (TextView) convertView.findViewById(R.id.pond_width);
                holder.holder_pondheight = (TextView) convertView.findViewById(R.id.pond_height);
                holder.holder_ponddepth = (TextView) convertView.findViewById(R.id.pond_depth);

                holder.holder_farmpond_image1 = (ImageView) convertView.findViewById(R.id.farmpondimage1_iv);
                holder.holder_farmpond_image2 = (ImageView) convertView.findViewById(R.id.farmpondimage2_iv);
                holder.holder_farmpond_image3 = (ImageView) convertView.findViewById(R.id.farmpondimage3_iv);
                holder.holder_farmer_id = (TextView) convertView.findViewById(R.id.farmer_id_tv);
                holder.holder_farmpond_id = (TextView) convertView.findViewById(R.id.farmpond_id_tv);
                holder.holder_editfarmerponddetails = (ImageView) convertView.findViewById(R.id.editfarmerponddetails_iv);

                Log.d("Inside If convertView", "Inside If convertView");

                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
                Log.d("Inside else convertView", "Inside else convertView");
            }

            final Class_farmponddetails farmponddetails_obj = (Class_farmponddetails) getItem(position);


            if (farmponddetails_obj != null) {
                //holder.holder_farmername.setText(farmponddetails_obj.getFarmer_Name());
                holder.holder_farmername.setText("Add Farmername");
                holder.holder_pondwidth.setText(farmponddetails_obj.getPondWidth());
                holder.holder_pondheight.setText(farmponddetails_obj.getPondLength());
                holder.holder_ponddepth.setText(farmponddetails_obj.getPondDepth());

                holder.holder_editfarmerponddetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        gpstracker_obj2 = new Class_GPSTracker(EachFarmPondDetails_Activity.this);


                        if (gpstracker_obj2.canGetLocation()) {
                            double_currentlatitude = gpstracker_obj2.getLatitude();
                            double_currentlongitude = gpstracker_obj2.getLongitude();


                            str_latitude = Double.toString(double_currentlatitude);
                            str_longitude = Double.toString(double_currentlongitude);


                            Log.e("lat", str_latitude);
                            Log.e("long", str_longitude);


                            str_gps_yes = "yes";
                            Log.e("editstring", str_gps_yes);
                            // edit_farmponddetails_alertdialog(farmponddetails_obj);


                        } else {
                            Log.e("editstring", str_gps_yes);
                            str_gps_yes = "no";
                            gpstracker_obj2.showSettingsAlert();
                        }


                        if (str_gps_yes.equalsIgnoreCase("yes"))
                        {
                           // edit_farmponddetails_alertdialog(farmponddetails_obj);
                        }



                    }
                });

                /*if (farmponddetails_obj.getClass_farmpondimages_obj().size() > 0)
                {
                    for (int j = 0; j < farmponddetails_obj.getClass_farmpondimages_obj().size(); j++) {

                        Log.e("imageurl", str_farmpondbaseimage_url + farmponddetails_obj.getClass_farmpondimages_obj().get(j).getImage_url().toString());

                        String str_farmpondimageurl = str_farmpondbaseimage_url + farmponddetails_obj.getClass_farmpondimages_obj().get(j).getImage_url().toString();

                        if (j == 0) {
                            Picasso.get()
                                    .load(str_farmpondimageurl)
                                    .into(holder.holder_farmpond_image1, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                        }
                                    });
                        }
                        if (j == 1) {
                            Picasso.get()
                                    .load(str_farmpondimageurl)
                                    .into(holder.holder_farmpond_image2, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                        }
                                    });
                        }
                        if (j == 2) {
                            Picasso.get()
                                    .load(str_farmpondimageurl)
                                    .into(holder.holder_farmpond_image3, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                        }
                                    });
                        }
                    } // end for 1
                }// end if 2*/


            }// end if 1

            return convertView;

        }//End of custom getView
    }//End of CustomAdapter










    //location


    public boolean gps_enable() {
        isGPS = false;
        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        if (isGPS) {
            return true;
        } else {
            return false;
        }
    }




    private void getLocation()
    {

        try {
            if (canGetLocation)
            {
               // Log.d(TAG, "Can get location");
                if (isGPSON)
                {
                    // from GPS
                    //Log.d(TAG, "GPS on");

                    dialog_location.setMessage("Please wait location fetching...");
                    dialog_location.setCanceledOnTouchOutside(false);
                    dialog_location.show();

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);


                    if (locationManager != null)
                    {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                        {
                            try {
                                Thread.sleep(1 * 500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Log.e("lat",String.valueOf(loc.getLatitude()));
                            Log.e("long",String.valueOf(loc.getLongitude()));

                            dialog_location.dismiss();

                            Intent intent_addfarmpondactivity = new Intent(EachFarmPondDetails_Activity.this, AddFarmPondActivity.class);
                            startActivity(intent_addfarmpondactivity);
                            finish();




                        }
                    }
                    else{
                        dialog_location.dismiss();
                    }
                }
                /*else if (isNetwork)
                {
                    // from Network Provider
                    Log.d(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null)
                    {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }
                } */
                /*else
                {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }*/
            } else
                {
                //Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    private void getLocation2()
    {
        try {
            if (canGetLocation)
            {
                // Log.d(TAG, "Can get location");
                if (isGPSON)
                {
                    // from GPS
                    //Log.d(TAG, "GPS on");

                    dialog_location.setMessage("Please wait location fetching...");
                    dialog_location.setCanceledOnTouchOutside(false);
                    dialog_location.show();


                    gpstracker_obj3 = new Class_GPSTracker2(EachFarmPondDetails_Activity.this);

                    Location gpsLocation = gpstracker_obj3
                            .getLocation(LocationManager.GPS_PROVIDER);


                    if (gpsLocation != null)
                    {

                        double_currentlatitude = gpsLocation.getLatitude();
                        double_currentlongitude = gpsLocation.getLongitude();

                        Log.e("lat",String.valueOf(double_currentlatitude));
                        Log.e("long",String.valueOf(double_currentlongitude));
                    } else {
                        //showSettingsAlert("GPS");
                    }

                    try {
                        Thread.sleep(1 * 100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    dialog_location.dismiss();

                    Intent intent_addfarmpondactivity = new Intent(EachFarmPondDetails_Activity.this, AddFarmPondActivity.class);
                    startActivity(intent_addfarmpondactivity);
                    finish();


                }

            } else
            {
                //Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }



    }


    //location




    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
               // Log.d(TAG, "onRequestPermissionsResult");
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                       /* if (shouldShowRequestPermissionRationale(permissionsRejected.get(0)))
                        {
                            showMessageOKCancel("These permissions are mandatory for the
                                    application. Please allow access.",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(permissionsRejected.toArray(
                                                new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                    }
                                }
                            });
                            return;
                        }*/

                    }
                } else {
                   // Log.d(TAG, "No rejected permissions.");
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }





}
