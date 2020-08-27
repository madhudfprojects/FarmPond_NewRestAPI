package df.farmponds;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class EachFarmPondDetails_Activity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView add_newfarmpond_iv;

    private ListView farmpondlist_listview;

  //  Class_farmponddetails[] class_farmponddetails_array_obj;
    Class_farmponddetails_offline[] class_farmponddetails_array_obj;


    //listview
    byte[] imageBytes;
    Bitmap bmp_decodedImage1, bmp_decodedImage2, bmp_decodedImage3;
    byte[] image_bytearray;
    Class_farmponddetails_offline farmponddetails_obj;
  //Class_farmponddetails farmponddetails_obj;

    //listview

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



    SharedPreferences sharedpref_farmerid_Obj;
    String str_farmerID, str_farmername, str_farmerID_notification;
    public static final String sharedpreferenc_farmerid = "sharedpreference_farmer_id";
    public static final String Key_FarmerID = "farmer_id";
    public static final String Key_FarmerName = "farmer_name";


    String sel_yearsp, sel_statesp, sel_districtsp, sel_taluksp, sel_villagesp, sel_grampanchayatsp;

    public static final String sharedpreferenc_selectedspinner = "sharedpreferenc_selectedspinner";
    public static final String Key_sel_yearsp = "sel_yearsp";
    public static final String Key_sel_statesp = "sel_statesp";
    public static final String Key_sel_districtsp = "sel_districtsp";
    public static final String Key_sel_taluksp = "sel_taluksp";
    public static final String Key_sel_villagesp = "sel_villagesp";
    public static final String Key_sel_grampanchayatsp = "sel_grampanchayatsp";
    SharedPreferences sharedpref_spinner_Obj;
    AppLocationService appLocationService;
    double latitude,longitude;
    String lat_str,log_str;



   /* private boolean isGPS = false;
    ProgressDialog dialog_location;*/
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;


    String str_approved;

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

        sharedpref_farmerid_Obj = getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_farmerID = sharedpref_farmerid_Obj.getString(Key_FarmerID, "").trim();


        sharedpref_farmerid_Obj = getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_farmername = sharedpref_farmerid_Obj.getString(Key_FarmerName, "").trim();

        sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        sel_yearsp = sharedpref_spinner_Obj.getString(Key_sel_yearsp, "").trim();
        sel_statesp = sharedpref_spinner_Obj.getString(Key_sel_statesp, "").trim();
        sel_districtsp = sharedpref_spinner_Obj.getString(Key_sel_districtsp, "").trim();
        sel_taluksp = sharedpref_spinner_Obj.getString(Key_sel_taluksp, "").trim();
        sel_villagesp = sharedpref_spinner_Obj.getString(Key_sel_villagesp, "").trim();
        sel_grampanchayatsp = sharedpref_spinner_Obj.getString(Key_sel_grampanchayatsp, "").trim();



        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        Intent intent = getIntent();
        if (isInternetPresent) {
            //AsyncTask_fetch_farmponddetails();


            if ((intent.equals(null)))
            {
                Data_from_PondDetails_DB(str_farmerID);
            } else {
                str_farmerID_notification = intent.getStringExtra("farmer_ID");//farmer_ID
                if (str_farmerID_notification == null || str_farmerID_notification.isEmpty()) {
                    Log.e("null", "null");
                    Data_from_PondDetails_DB(str_farmerID);

                } else {
                    Log.e("FCMfarmer_ID", str_farmerID_notification);
                    Data_from_PondDetails_DB(str_farmerID_notification);
                }

            }

        }else {

            str_farmerID_notification = intent.getStringExtra("farmer_ID");//farmer_ID
            if (str_farmerID_notification == null || str_farmerID_notification.isEmpty()) {
                Log.e("null", "null");
                Data_from_PondDetails_DB(str_farmerID);

            } else {
                Log.e("FCMfarmer_ID", str_farmerID_notification);
                Data_from_PondDetails_DB(str_farmerID_notification);
            }

        }









/*// commented on 11Aug2020
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

            }
        });
// commented on 11Aug2020*/













        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds
        dialog_location = new ProgressDialog(EachFarmPondDetails_Activity.this);


        locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations())
                {
                    if (location != null)
                    {
                        Log.e("inside location", "inside location");
                        //if (!isContinue)
                        if (!isContinue)
                        {
                            Log.e("lat",String.valueOf(location.getLatitude()));
                            Log.e("long",String.valueOf(location.getLongitude()));

                        } else {

                        }
                        if (!isContinue && mFusedLocationClient != null)
                        {
                            // if (isContinue && mFusedLocationClient != null)
                            //{
                            try {
                                Thread.sleep(1 * 500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                            dialog_location.dismiss();
                            call_addfarmpondActivity();
                        }
                    } else {
                        Log.e("location", "null");
                        dialog_location.dismiss();
                    }
                }
            }
        };



        add_newfarmpond_iv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (gps_enable())
                {
                    isContinue = false;
                    getLocation_oldphp();

                }

            }
        });




    }//end Of create













    public void Data_from_PondDetails_DB(String str_farmerID)
    {

        Log.e("farmerID", str_farmerID);
        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR,TempFIDDB VARCHAR," +
                "FNameDB VARCHAR,FMNameDB VARCHAR,FLNameDB VARCHAR,FYearIDDB VARCHAR,FStateIDDB VARCHAR,FDistrictIDDB VARCHAR," +
                "FTalukIDDB VARCHAR,FPanchayatIDDB VARCHAR,FVillageIDDB VARCHAR,FageDB VARCHAR,FphonenumberDB VARCHAR," +
                "FAnnualIncomeDB VARCHAR,FfamilymemberDB VARCHAR,FidprooftypeDB VARCHAR,FidproofnoDB VARCHAR,FphotoDB VARCHAR,FPondidDB VARCHAR,WidthDB VARCHAR," +
                "HeightDB VARCHAR,DepthDB VARCHAR,LatitudeDB VARCHAR,LongitudeDB VARCHAR,Imageid1DB VARCHAR,Image1Base64DB VARCHAR," +
                "Imageid2DB VARCHAR,Image2Base64DB VARCHAR,Imageid3DB VARCHAR,Image3Base64DB VARCHAR,EmployeeIDDB VARCHAR,SubmittedDateDB VARCHAR," +
                "TotalDaysDB VARCHAR,StartDateDB VARCHAR,ConstructedDateDB VARCHAR,PondCostDB VARCHAR,McodeDB VARCHAR,FPondCodeDB VARCHAR," +
                "FPondRemarksDB VARCHAR,FPondAmtTakenDB VARCHAR,FPondStatusDB VARCHAR," +
                "FPondApprovalStatusDB VARCHAR,FPondApprovalRemarksDB VARCHAR,FPondApprovedbyDB VARCHAR,FPondApprovedDateDB VARCHAR,FPondDonorDB VARCHAR," +
                "FPondLatitudeDB VARCHAR,FPondLongitudeDB VARCHAR," +
                "FPondAcresDB VARCHAR,FPondGuntaDB VARCHAR,FPondCropBeforeDB VARCHAR,FPondCropAfterDB VARCHAR," +
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR," +
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR,finalfarmpondcodeDB VARCHAR);");


        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM FarmPondDetails_fromServer WHERE FIDDB='" + str_farmerID + "'", null);
        //Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM FarmPondDetails_fromServer WHERE FIDDB='" + str_farmerID + "' AND FphonenumberDB='" + "empty" + "'", null);

        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM FarmPondDetails_fromServerRest WHERE FIDDB='" + str_farmerID + "' AND FAnnualIncomeDB='" + "empty" + "'", null);

        //  Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerList  WHERE DispFarmerTable_YearID='" + str_yearid + "' AND DispFarmerTable_StateID='" + str_stateid + "' AND DispFarmerTable_DistrictID='" + str_distid + "'  AND DispFarmerTable_TalukID='" + str_talukid + "' AND DispFarmerTable_VillageID='" + str_villageid + "' AND DispFarmerTable_GrampanchayatID='" + str_panchayatid + "'", null);
        int x = cursor1.getCount();

        if (x == 0) {
            cursor1 = db1.rawQuery("SELECT DISTINCT * FROM FarmPondDetails_fromServerRest WHERE FIDDB='" + str_farmerID + "'", null);
            x = cursor1.getCount();
            Log.e("inside","inside");

        }


        // int x=0;
        Log.e("Eachfarmpond_count", String.valueOf(x));


        int i = 0;
        // Class_farmponddetails_offline[] class_farmponddetails_array_obj;
        class_farmponddetails_array_obj = new Class_farmponddetails_offline[x];
        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    Class_farmponddetails_offline innerObj_class_farmpondetails = new Class_farmponddetails_offline();

                    innerObj_class_farmpondetails.setfarmer_id(cursor1.getString(cursor1.getColumnIndex("FIDDB")));

                    //"FNameDB VARCHAR,FMNameDB VARCHAR,FLNameDB VARCHAR,
                    innerObj_class_farmpondetails.setFarmer_Name(cursor1.getString(cursor1.getColumnIndex("FNameDB")));
                    innerObj_class_farmpondetails.setFarmer_Name(cursor1.getString(cursor1.getColumnIndex("FMNameDB")));
                    innerObj_class_farmpondetails.setFarmer_Name(cursor1.getString(cursor1.getColumnIndex("FLNameDB")));
                   // innerObj_class_farmpondetails.setFarmer_First_Name(cursor1.getString(cursor1.getColumnIndex("FNameDB")));
                    innerObj_class_farmpondetails.setFarmpond_Id(cursor1.getString(cursor1.getColumnIndex("FPondidDB")));
                    innerObj_class_farmpondetails.setFarmpond_Width(cursor1.getString(cursor1.getColumnIndex("WidthDB")));
                    innerObj_class_farmpondetails.setFarmpond_Height(cursor1.getString(cursor1.getColumnIndex("HeightDB")));
                    innerObj_class_farmpondetails.setFarmpond_Depth(cursor1.getString(cursor1.getColumnIndex("DepthDB")));

                    //innerObj_class_farmpondetails.setImage1_ID(cursor1.getString(cursor1.getColumnIndex("Imageid1DB")));
                    innerObj_class_farmpondetails.setImage1_Base64(cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")));

                   // innerObj_class_farmpondetails.setImage2_ID(cursor1.getString(cursor1.getColumnIndex("Imageid2DB")));
                    innerObj_class_farmpondetails.setImage2_Base64(cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")));

                    //innerObj_class_farmpondetails.setImage3_ID(cursor1.getString(cursor1.getColumnIndex("Imageid3DB")));
                    innerObj_class_farmpondetails.setImage3_Base64(cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")));

                  // innerObj_class_farmpondetails.setuploadstatus(cursor1.getString(cursor1.getColumnIndex("UploadedStatus")));

                    innerObj_class_farmpondetails.setFarmpondCode(cursor1.getString(cursor1.getColumnIndex("FPondCodeDB")));

                    innerObj_class_farmpondetails.setFarmpond_ApprovalStatus(cursor1.getString(cursor1.getColumnIndex("FPondApprovalStatusDB")));
                    innerObj_class_farmpondetails.setFarmpond_ApprovalRemarks(cursor1.getString(cursor1.getColumnIndex("FPondApprovalRemarksDB")));
                    innerObj_class_farmpondetails.setFarmpond_Approvedby(cursor1.getString(cursor1.getColumnIndex("FPondApprovedbyDB")));
                   // innerObj_class_farmpondetails.setFarmpond_ApprovedDate(cursor1.getString(cursor1.getColumnIndex("FPondApprovedDateDB")));
                    innerObj_class_farmpondetails.setFarmpond_Donor(cursor1.getString(cursor1.getColumnIndex("FPondDonorDB")));

                    innerObj_class_farmpondetails.setFinal_farmpond_code(cursor1.getString(cursor1.getColumnIndex("finalfarmpondcodeDB")));

                    Log.e("farmpondcode", cursor1.getString(cursor1.getColumnIndex("FPondCodeDB")));

                    class_farmponddetails_array_obj[i] = innerObj_class_farmpondetails;
                    i++;
                } while (cursor1.moveToNext());
            }//if ends


        }


        db1.close();

        if (x > 0) {
            if (class_farmponddetails_array_obj != null)
            {
                CustomAdapter_forOffline adapter = new CustomAdapter_forOffline();
                farmpondlist_listview.setAdapter(adapter);

                int y = class_farmponddetails_array_obj.length;

                Log.e("length adapter", "" + y);
            } else {
                Log.d("length adapter", "zero");
            }

        }


    }













    private class Holder_offline {
        TextView holder_farmername;
        TextView holder_pondwidth;
        TextView holder_pondheight;
        TextView holder_ponddepth;
        TextView holder_farmer_id;
        TextView holder_farmpond_id;
        TextView holder_final_farmpondcode;
        ImageView holder_farmpond_image1;
        ImageView holder_farmpond_image2;
        ImageView holder_farmpond_image3;
        ImageView holder_editfarmerponddetails;
        LinearLayout holder_listview_ll;
        LinearLayout holder_notsubmittedtoserver_ll;


        LinearLayout holder_approvalstatus_ll;
        LinearLayout holder_approvalremarks_ll;
        LinearLayout holder_approvedby_ll;
        LinearLayout holder_donorname_ll;

        TextView holder_farmpond_approvalstatus_tv;
        TextView holder_farmpond_approvalremarks_tv;
        TextView holder_farmpond_approvedby_tv;
        TextView holder_farmpond_donorname_tv;
    }



    public class CustomAdapter_forOffline extends BaseAdapter
    {


        public CustomAdapter_forOffline() {

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
            Log.d("getItem position", "x");
            return class_farmponddetails_array_obj[position];
        }


        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);

            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView1, ViewGroup parent) {

            final Holder_offline holder;

            Log.d("CustomAdapter", "position: " + position);

            if (convertView1 == null)
            {
                holder = new Holder_offline();
                convertView1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_row_item_farmponddetails_new, parent, false);


                //


                holder.holder_listview_ll = (LinearLayout) convertView1.findViewById(R.id.listview_ll);
                holder.holder_farmername = (TextView) convertView1.findViewById(R.id.farmername_tv);
                holder.holder_pondheight = (TextView) convertView1.findViewById(R.id.pond_height);
                holder.holder_pondwidth = (TextView) convertView1.findViewById(R.id.pond_width);
                holder.holder_ponddepth = (TextView) convertView1.findViewById(R.id.pond_depth);


                holder.holder_farmpond_image1 = (ImageView) convertView1.findViewById(R.id.farmpondimage1_iv);
                holder.holder_farmpond_image2 = (ImageView) convertView1.findViewById(R.id.farmpondimage2_iv);
                holder.holder_farmpond_image3 = (ImageView) convertView1.findViewById(R.id.farmpondimage3_iv);
                holder.holder_farmer_id = (TextView) convertView1.findViewById(R.id.farmer_id_tv);
                holder.holder_farmpond_id = (TextView) convertView1.findViewById(R.id.farmpond_id_tv);
                holder.holder_final_farmpondcode = (TextView) convertView1.findViewById(R.id.final_farmpondcode_tv);

                holder.holder_editfarmerponddetails = (ImageView) convertView1.findViewById(R.id.editfarmerponddetails_iv);
                holder.holder_notsubmittedtoserver_ll = (LinearLayout) convertView1.findViewById(R.id.notsubmittedtoserver_ll);

                holder.holder_approvalstatus_ll = (LinearLayout) convertView1.findViewById(R.id.approvalstatus_ll);
                holder.holder_approvalremarks_ll = (LinearLayout) convertView1.findViewById(R.id.approvalremarks_ll);
                holder.holder_approvedby_ll = (LinearLayout) convertView1.findViewById(R.id.approvedby_ll);
                holder.holder_donorname_ll = (LinearLayout) convertView1.findViewById(R.id.donorname_ll);


                // android:id="@+id/approvalstatus_ll"
                //android:id="@+id/approvalremarks_ll"
                //android:id="@+id/approvedby_ll"
                //android:id="@+id/donorname_ll"


                holder.holder_farmpond_approvalstatus_tv = (TextView) convertView1.findViewById(R.id.farmpond_approvalstatus_tv);
                holder.holder_farmpond_approvalremarks_tv = (TextView) convertView1.findViewById(R.id.farmpond_approvalremarks_tv);
                holder.holder_farmpond_approvedby_tv = (TextView) convertView1.findViewById(R.id.farmpond_approvedby_tv);
                holder.holder_farmpond_donorname_tv = (TextView) convertView1.findViewById(R.id.farmpond_donorname_tv);


                Log.d("Inside If convertView1", "Inside If convertView1");

                convertView1.setTag(holder);

            } else {
                holder = (Holder_offline) convertView1.getTag();
                Log.d("else convertView1", "else convertView1");
            }

            // Class_farmponddetails_offline farmponddetails_obj;
            farmponddetails_obj = (Class_farmponddetails_offline) getItem(position);


            if (farmponddetails_obj != null)
            {
                if (!(farmponddetails_obj.getFarmpond_Width().equalsIgnoreCase("empty")))
                {


                    /*holder.holder_farmername.setText(farmponddetails_obj.getFarmer_Name()+" "+
                            farmponddetails_obj.getFarmer_MName()+" "+farmponddetails_obj.getFarmer_LName());*/
                    holder.holder_farmername.setText(str_farmername);
                    holder.holder_pondwidth.setText(farmponddetails_obj.getFarmpond_Width());
                    holder.holder_pondheight.setText(farmponddetails_obj.getFarmpond_Height());
                    holder.holder_ponddepth.setText(farmponddetails_obj.getFarmpond_Depth());

                    holder.holder_farmpond_id.setText(farmponddetails_obj.getFarmpondCode());
                    holder.holder_final_farmpondcode.setText(farmponddetails_obj.getFinal_farmpond_code());

                    if (farmponddetails_obj.getFarmpondCode().contains("temp")) {
                        holder.holder_notsubmittedtoserver_ll.setVisibility(View.VISIBLE);
                    } else {
                        holder.holder_notsubmittedtoserver_ll.setVisibility(View.GONE);
                    }


                  /*  Log.e("approvalstatus", farmponddetails_obj.getFarmpond_ApprovalStatus());
                    Log.e("approvalremarks", farmponddetails_obj.getFarmpond_ApprovalRemarks());
                    Log.e("approvedby", farmponddetails_obj.getFarmpond_Approvedby());
                    Log.e("donorname", farmponddetails_obj.getFarmpond_Donor());
*/


                  //if (farmponddetails_obj.getFarmpond_ApprovalStatus().equalsIgnoreCase("null")



                    if (farmponddetails_obj.getFarmpond_ApprovalStatus()==null||
                            farmponddetails_obj.getFarmpond_ApprovalStatus().isEmpty() ||
                            farmponddetails_obj.getFarmpond_ApprovalStatus().equalsIgnoreCase("no") ||
                            farmponddetails_obj.getFarmpond_ApprovalStatus().equalsIgnoreCase("0")) {
                        holder.holder_approvalstatus_ll.setVisibility(View.GONE);
                        str_approved="pending";
                        Log.e("approvalstatus","'null");

                    } else {
                        if(farmponddetails_obj.getFarmpond_ApprovalStatus().equalsIgnoreCase("null"))
                        { holder.holder_approvalstatus_ll.setVisibility(View.GONE);
                            str_approved="pending";}
                        else {

                            holder.holder_approvalstatus_ll.setVisibility(View.VISIBLE);
                            if (farmponddetails_obj.getFarmpond_ApprovalStatus() == null ||
                                    farmponddetails_obj.getFarmpond_ApprovalStatus().equalsIgnoreCase("Pending") ||
                                    farmponddetails_obj.getFarmpond_ApprovalStatus().equalsIgnoreCase("Rejected") ||
                                    farmponddetails_obj.getFarmpond_ApprovalStatus().equalsIgnoreCase("Deleted")
                            ) {
                                    str_approved=farmponddetails_obj.getFarmpond_ApprovalStatus().trim();
                                holder.holder_farmpond_approvalstatus_tv.setTextColor(getResources().getColor(R.color.color_red));
                                holder.holder_farmpond_approvalstatus_tv.setText(farmponddetails_obj.getFarmpond_ApprovalStatus());
                            } else {

                                holder.holder_farmpond_approvalstatus_tv.setTextColor(getResources().getColor(R.color.dark_green));
                                holder.holder_farmpond_approvalstatus_tv.setText(farmponddetails_obj.getFarmpond_ApprovalStatus());
                            }
                        }
                    }


                  /*  if ( farmponddetails_obj.getFarmpond_ApprovalRemarks()==null||
                            farmponddetails_obj.getFarmpond_ApprovalRemarks().isEmpty() ||
                            farmponddetails_obj.getFarmpond_ApprovalRemarks().equalsIgnoreCase("no") ||
                            farmponddetails_obj.getFarmpond_ApprovalRemarks().equalsIgnoreCase("0")) {
                        holder.holder_approvalremarks_ll.setVisibility(View.GONE);
                    } else {
                        if(farmponddetails_obj.getFarmpond_ApprovalRemarks().equalsIgnoreCase("null"))
                        {}
                        else {
                            holder.holder_approvalremarks_ll.setVisibility(View.VISIBLE);
                            holder.holder_farmpond_approvalremarks_tv.setText(farmponddetails_obj.getFarmpond_ApprovalRemarks());
                        }
                    }

                    if ( farmponddetails_obj.getFarmpond_Approvedby()==null||
                            farmponddetails_obj.getFarmpond_Approvedby().isEmpty() ||
                            farmponddetails_obj.getFarmpond_Approvedby().equalsIgnoreCase("no") ||
                            farmponddetails_obj.getFarmpond_Approvedby().equalsIgnoreCase("0")) {
                        holder.holder_approvedby_ll.setVisibility(View.GONE);
                    } else {
                        if(farmponddetails_obj.getFarmpond_Approvedby().equalsIgnoreCase("null"))
                        {}
                        else{
                        holder.holder_approvedby_ll.setVisibility(View.VISIBLE);
                        holder.holder_farmpond_approvedby_tv.setText(farmponddetails_obj.getFarmpond_Approvedby());}
                    }

                    if (farmponddetails_obj.getFarmpond_Donor()==null||
                            farmponddetails_obj.getFarmpond_Donor().isEmpty() ||
                            farmponddetails_obj.getFarmpond_Donor().equalsIgnoreCase("no") ||
                            farmponddetails_obj.getFarmpond_Donor().equalsIgnoreCase("0")
                    ) {
                        holder.holder_donorname_ll.setVisibility(View.GONE);
                    } else {
                        if(farmponddetails_obj.getFarmpond_Donor().equalsIgnoreCase("null"))
                        {}
                        else {
                            holder.holder_donorname_ll.setVisibility(View.VISIBLE);
                            holder.holder_farmpond_donorname_tv.setText(farmponddetails_obj.getFarmpond_Donor());
                        }
                    }*/



                    holder.holder_farmer_id.setText(farmponddetails_obj.getfarmer_id());

                    final String str_farmpond_id = farmponddetails_obj.getFarmpond_Id();
                    final String str_farmer_id = farmponddetails_obj.getfarmer_id();
                    final String str_farmername = farmponddetails_obj.getFarmer_Name();

                    //final String final_str_approved = farmponddetails_obj.getFarmpond_ApprovalStatus();
                   // final String final_str_approved = str_approved;

                    final String final_str_approved = holder.holder_farmpond_approvalstatus_tv.getText().toString();






                    holder.holder_editfarmerponddetails.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                         /*   if (gps_enable())
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
                            }
                            try {
                                if (canGetLocation)
                                {
                                    // Log.d(TAG, "Can get location");
                                    if (isGPSON)
                                    {
                                        ;

                                        dialog_location.setMessage("Please wait location fetching...");
                                        dialog_location.setCanceledOnTouchOutside(false);
                                        dialog_location.show();

                                        appLocationService = new AppLocationService(
                                                EachFarmPondDetails_Activity.this);
                                        android.location.Location nwLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
                                        str_gps_yes = "yes";

                                        if (nwLocation != null)
                                        {
                                            latitude = nwLocation.getLatitude();
                                            longitude = nwLocation.getLongitude();
                                            lat_str=Double.toString(latitude);
                                            log_str=Double.toString(longitude);
                                            Log.e(TAG,"latitude"+lat_str);
                                            Log.e(TAG,"longitude"+log_str);
                                            Toast.makeText(EachFarmPondDetails_Activity.this," before latitude="+lat_str+" longitude="+log_str,Toast.LENGTH_LONG).show();
                                            str_latitude =Double.toString(latitude);
                                            str_longitude =Double.toString(longitude);

                                        }
                                        else{
                                            Toast.makeText(EachFarmPondDetails_Activity.this," before latitude="+lat_str+" longitude="+log_str,Toast.LENGTH_LONG).show();
                                        }

                                        try {
                                            Thread.sleep(1 * 100);

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        dialog_location.dismiss();


                                    }

                                } else
                                {
                                    str_gps_yes = "no";
                                    //Log.d(TAG, "Can't get location");
                                }
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            }

                            Log.e("tag","str_gps_yes="+str_gps_yes);
                            if (str_gps_yes.equalsIgnoreCase("yes"))
                            {

                                edit_farmponddetails_alertdialog_offline(str_farmername, str_farmer_id, str_farmpond_id, final_str_approved);
                            }
*/

                            // edit_farmponddetails_alertdialog(farmponddetails_obj);

                            //   edit_farmponddetails_alertdialog_offline(str_farmername, str_farmer_id, str_farmpond_id);




                            gpstracker_obj2 = new Class_GPSTracker(EachFarmPondDetails_Activity.this);

                            if (gps_enable())
                            {


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


                                if (str_gps_yes.equalsIgnoreCase("yes")) {
                                    edit_farmponddetails_alertdialog_offline(str_farmername, str_farmer_id, str_farmpond_id, final_str_approved);
                                }

                            }
                        }
                    });


                    //Log.e("image",farmponddetails_obj.getImage1_Base64());

                    String str_image1_Base64 = farmponddetails_obj.getImage1_Base64();
                    String str_image2_Base64 = farmponddetails_obj.getImage2_Base64();
                    String str_image3_Base64 = farmponddetails_obj.getImage3_Base64();


                    // Log.e("image offline", str_image1_Base64);


                    if (str_image1_Base64.equalsIgnoreCase("noimage1") ||
                            str_image1_Base64.equalsIgnoreCase("empty") ||
                            str_image1_Base64.equalsIgnoreCase("0"))//str_base64image1="noimage1" str_imageid1="0",empty
                    {
                        Log.e("no image", str_image1_Base64.toString());

                        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.no_image);
                        holder.holder_farmpond_image1.setImageBitmap(bitmap);
                    } else {

                        //  Log.e("Eachimage1",str_image1_Base64);

                        imageBytes = Base64.decode(str_image1_Base64, Base64.DEFAULT);
                        bmp_decodedImage1 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        holder.holder_farmpond_image1.setImageBitmap(bmp_decodedImage1);
                    }


                    if (str_image2_Base64.equalsIgnoreCase("noimage2") ||
                            str_image2_Base64.equalsIgnoreCase("empty") ||
                            str_image2_Base64.equalsIgnoreCase("0"))//str_base64image2="noimage2" str_imageid2="0"
                    {
                        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.no_image);
                        holder.holder_farmpond_image2.setImageBitmap(bitmap);
                    } else {


                        imageBytes = Base64.decode(str_image2_Base64, Base64.DEFAULT);
                        bmp_decodedImage2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        holder.holder_farmpond_image2.setImageBitmap(bmp_decodedImage2);
                    }


                    if (str_image3_Base64.equalsIgnoreCase("noimage3") ||
                            str_image3_Base64.equalsIgnoreCase("empty") ||
                            str_image3_Base64.equalsIgnoreCase("0"))//str_base64image3="noimage3" str_imageid3="0";
                    {
                        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.no_image);
                        holder.holder_farmpond_image3.setImageBitmap(bitmap);
                    } else {
                        imageBytes = Base64.decode(str_image3_Base64, Base64.DEFAULT);
                        bmp_decodedImage3 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        holder.holder_farmpond_image3.setImageBitmap(bmp_decodedImage3);
                    }
                } else {

                    holder.holder_listview_ll.setVisibility(View.GONE);
                }

            }// end if 1

            return convertView1;

        }//End of custom getView
    }//End of CustomAdapter







    public void edit_farmponddetails_alertdialog_offline(final String str_farmername, final String str_farmer_id,
                                                         final String str_farmpond_id, final String strs_approved)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(EachFarmPondDetails_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Are you sure want to Edit Details");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                Log.e("edit",strs_approved);
                Intent i = new Intent(EachFarmPondDetails_Activity.this, EditFarmPondDetails_Activity.class);
                i.putExtra("farmer_name", str_farmername);
                i.putExtra("farmpond_id", str_farmpond_id);
                i.putExtra("farmer_id", str_farmer_id);
                i.putExtra("ApprovedStatus", strs_approved);
                startActivity(i);
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

    }

















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



                    appLocationService = new AppLocationService(
                            EachFarmPondDetails_Activity.this);
                    android.location.Location nwLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                    if (nwLocation != null)
                    {
                        latitude = nwLocation.getLatitude();
                        longitude = nwLocation.getLongitude();
                        lat_str=Double.toString(latitude);
                        log_str=Double.toString(longitude);
                        Log.e(TAG,"latitude"+lat_str);
                        Log.e(TAG,"longitude"+log_str);
                        Toast.makeText(EachFarmPondDetails_Activity.this," before latitude="+lat_str+" longitude="+log_str,Toast.LENGTH_LONG).show();
                        str_latitude =Double.toString(latitude);
                        str_longitude =Double.toString(longitude);

                    }else {
                        if(str_latitude==null||str_longitude==null||str_latitude.equals("0.0")||str_longitude.equals("0.0"))
                        {
                           // alertdialog_refresh_latandlong();
                            str_latitude="0.0";str_longitude="0.0";


                        }
                        Toast.makeText(EachFarmPondDetails_Activity.this, " latitude=" + str_latitude + " longitude=" + str_longitude, Toast.LENGTH_LONG).show();
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

    public void alertdialog_refresh_latandlong() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(EachFarmPondDetails_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Click Ok to fetch latitude and Longitude");

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Intent i = new Intent(EachFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
                startActivity(i);
                finish();
            }
        });

        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                //  alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();
    }



   /* private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
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

                    }
                } else {
                   // Log.d(TAG, "No rejected permissions.");
                    canGetLocation = true;
                   // getLocation();
                    appLocationService = new AppLocationService(
                            EachFarmPondDetails_Activity.this);
                    android.location.Location nwLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                    if (nwLocation != null)
                    {
                        latitude = nwLocation.getLatitude();
                        longitude = nwLocation.getLongitude();
                        lat_str=Double.toString(latitude);
                        log_str=Double.toString(longitude);
                        Log.e(TAG,"latitude"+lat_str);
                        Log.e(TAG,"longitude"+log_str);
                        Toast.makeText(this," before latitude="+lat_str+" longitude="+log_str,Toast.LENGTH_LONG).show();
                        str_latitude =Double.toString(latitude);
                        str_longitude =Double.toString(longitude);

                        Intent intent_addfarmpondactivity = new Intent(EachFarmPondDetails_Activity.this, AddFarmPondActivity.class);
                        startActivity(intent_addfarmpondactivity);
                        finish();
                    }else{
                      //  gpstracker_obj.showSettingsAlert();
                    }

                }
                break;
        }
    }*/











    private void getLocation_oldphp()
    {
        if (ActivityCompat.checkSelfPermission(EachFarmPondDetails_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(EachFarmPondDetails_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(EachFarmPondDetails_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.LOCATION_REQUEST);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(EachFarmPondDetails_Activity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        location = null;
                        if (location != null) {
                            /*wayLatitude = location.getLatitude();
                            wayLongitude = location.getLongitude();*/

                        } else {
                            dialog_location.setMessage("Please wait location fetching...");
                            dialog_location.setCanceledOnTouchOutside(false);
                            dialog_location.show();
                            Log.e("else part", "else part");
                            if (ActivityCompat.checkSelfPermission(EachFarmPondDetails_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EachFarmPondDetails_Activity.this,
                                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            Log.e("else end", "else end");
                        }
                    }
                });
            }
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (isContinue) {
                        if (ActivityCompat.checkSelfPermission(EachFarmPondDetails_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                                (EachFarmPondDetails_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    } else {

                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EachFarmPondDetails_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mFusedLocationClient.getLastLocation().addOnSuccessListener(EachFarmPondDetails_Activity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                /*wayLatitude = location.getLatitude();
                                wayLongitude = location.getLongitude();
                                txtLocation.setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));*/
                                } else {
                                    if (ActivityCompat.checkSelfPermission(EachFarmPondDetails_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                                            (EachFarmPondDetails_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                                    {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }



    public void call_addfarmpondActivity()
    {
                Intent intent_addfarmpondactivity = new Intent(EachFarmPondDetails_Activity.this, AddFarmPondActivity.class);
                startActivity(intent_addfarmpondactivity);
                finish();
    }







    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i=new Intent(EachFarmPondDetails_Activity.this,Activity_ViewFarmers.class);
     /*   i.putExtra("value_constant","1");
        i.putExtra("sel_yearsp",sel_yearsp);
        i.putExtra("sel_statesp",sel_statesp);
        i.putExtra("sel_districtsp",sel_districtsp);
        i.putExtra("sel_taluksp",sel_taluksp);
        i.putExtra("sel_villagesp",sel_villagesp);
        i.putExtra("sel_grampanchayatsp",sel_grampanchayatsp);*/
       /* SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
        myprefs_spinner.putString(Key_sel_yearsp, sel_yearsp);
        myprefs_spinner.putString(Key_sel_statesp, sel_statesp);
        myprefs_spinner.putString(Key_sel_districtsp, sel_districtsp);
        myprefs_spinner.putString(Key_sel_taluksp, sel_taluksp);
        myprefs_spinner.putString(Key_sel_villagesp, sel_villagesp);
        myprefs_spinner.putString(Key_sel_grampanchayatsp, sel_grampanchayatsp);

        myprefs_spinner.apply();*/
        startActivity(i);
        finish();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EachFarmPondDetails_Activity.this, Activity_ViewFarmers.class);
            //i.putExtra("value_constant","1");
            startActivity(i);
            finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


}
