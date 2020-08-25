package df.farmponds;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Service;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import df.farmponds.Models.AddFarmerRequest;
import df.farmponds.Models.AddFarmerResList;
import df.farmponds.Models.AddFarmerResponse;
import df.farmponds.Models.Class_FarmerProfileOffline;
import df.farmponds.Models.Class_MachineDetails;
import df.farmponds.Models.Class_farmponddetails;
import df.farmponds.Models.DefaultResponse;
import df.farmponds.Models.ErrorUtils;
import df.farmponds.Models.PondImage;
import df.farmponds.remote.Class_ApiUtils;
import df.farmponds.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class EditFarmPondDetails_Activity extends AppCompatActivity {


    Toolbar toolbar;
    ImageView add_newfarmpond_iv;

    EditText edit_ponddepth_et, edit_pondwidth_et, edit_pondheight_et, edit_landacres_et, edit_landgunta_et;
    TextView edit_ponddetails_farmername_et, submittedby_tv;
    ImageView edit_pond_image1_iv, edit_pond_image2_iv, edit_pond_image3_iv;
    ImageButton edit_removeimage1_ib, edit_removeimage2_ib, edit_removeimage3_ib;

    Button edit_ponddetails_submit_bt, edit_ponddetails_cancel_bt;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String str_base64imagestring = "";
    ArrayList<String> arraylist_image1_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image2_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image3_base64 = new ArrayList<>();

    ArrayList<String> arraylist_image1_ID_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image2_ID_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image3_ID_base64 = new ArrayList<>();

    String str_image1, str_image2, str_image3;
    String str_Is_image1, str_Is_image2, str_Is_image3;
    Bitmap mIcon11;

    // Class_farmponddetails class_farmponddetails_obj;
    String str_farmpondbaseimage_url, str_cancelclicked;
    Class_farmponddetails_offline class_farmponddetails_offline_obj;
    Class_addfarmponddetails_ToFromServer2 class_addfarmponddetails_tofromserver2_obj;


    SharedPreferences sharedpref_farmerid_Obj;
    String str_farmerID;
    public static final String sharedpreferenc_farmerid = "sharedpreference_farmer_id";
    public static final String Key_FarmerID = "farmer_id";
    String str_farmpondimageurl, str_farmpond_id, str_farmername, str_farmer_id, str_approvedstatus;

    ArrayList<Bitmap> arrayList_bitmap;

    TextView image_id1_tv, image_id2_tv, image_id3_tv;

    LinearLayout cancel_submit_ll;

    String str_year;
    public static final String sharedpreferenc_selectedspinner = "sharedpreferenc_selectedspinner";
    public static final String Key_sel_yearsp = "sel_yearsp";
    public static final String Key_sel_statesp = "sel_statesp";
    public static final String Key_sel_districtsp = "sel_districtsp";
    public static final String Key_sel_taluksp = "sel_taluksp";
    public static final String Key_sel_villagesp = "sel_villagesp";
    public static final String Key_sel_grampanchayatsp = "sel_grampanchayatsp";
    SharedPreferences sharedpref_spinner_Obj;



    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";
    public static final String KeyValue_perdayamount = "KeyValue_perdayamount";

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;

    String str_employee_id, str_employee_mailid;

    String str_submitteddatetime, str_validation_for_completed;

    TextView edit_pondamount_tv, edit_pond_total_amount_tv;
    static TextView edit_pond_completeddate_tv, edit_pond_startddate_tv;
    EditText edit_pond_no_of_days_et;
    CheckBox edit_farmpond_completed_cb;
    Spinner selectmachineno_sp, edit_selectremarks_sp;
    String str_perdayamount;

    EditText edit_amountcollected_et;
    Class_MachineDetails[] class_machineDetails_array_obj;
    Class_MachineDetails class_machineDetails_obj;
    String str_machinecode;

    LinearLayout startdate_LL, completeddate_LL, nodays_LL, machineno_LL, farmpondcompleted_LL, notcompleted_text_LL, remarks_LL, amountcollected_LL;
    LinearLayout amount_LL;
    String str_image1present, str_image2present, str_image3present;

    Class_GPSTracker gpstracker_obj1, gpstracker_obj2, gpstracker_obj3;
    Double double_currentlatitude = 0.0;
    Double double_currentlongitude = 0.0;
    String str_latitude, str_longitude;
    TextView latitude_tv, longitude_tv;

    Date startDate, endDate;

    Class_RemarksDetails[] class_remarksdetails_array_obj;
    Class_RemarksDetails class_remarksdetails_obj;
    String str_remarksid;


    //Class_FarmerProfileOffline[] class_farmerprofileoffline_array_obj;
    //Class_FarmerProfileOffline class_farmerprofileoffline_obj;

    //Class_farmponddetails_offline[] class_farmponddetails_offline_array_obj,newfarmponddetails_offline_array_obj;

    Class_farmponddetails_offline[] class_farmponddetails_offline_array_obj;

    LinearLayout farmpondlocationlabel_LL;
    LinearLayout dblatitude_LL, dblongitude_LL;
    TextView dblatitude_tv, dblongitude_tv;

    LinearLayout currentlatitude_LL, currentlongitude_LL;
    String str_currentlatitude, str_currentlongitude;

    LinearLayout fpondcompleted_ll;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    String str_location;

    LinearLayout amountcollected_lesser_LL;


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


    AppLocationService appLocationService;
    double latitude, longitude;
    String lat_str, log_str;


    //To Upload to server
    Class_FarmerProfileOffline[] class_farmerprofileoffline_array_obj;
    //Interface_userservice userService1;

    Class_farmponddetails[] class_farmponddetails_offline_array_objRest, newfarmponddetails_offline_array_objRest;
    //To Upload to server

    int int_k;

    Class_addfarmponddetails_ToFromServer1[] editedpond_response;
    int int_er;

    int int_j,int_newfarmercount;
    Class_addfarmponddetails_ToFromServer1[] newpond_response;



    /*private boolean isGPS = false;
    ProgressDialog dialog_location;*/
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfarmponddetails);


        toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
        // Set upon the actionbar
        setSupportActionBar(toolbar);
        // Now use actionbar methods to show navigation icon and title
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        str_farmpondbaseimage_url = Class_URL.URL_farmpondbaselink.toString().trim();
        TextView title = (TextView) toolbar.findViewById(R.id.title_name);
        add_newfarmpond_iv = (ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
        title.setText("Edit FarmPond Details");
        getSupportActionBar().setTitle("");
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorwhite), PorterDuff.Mode.SRC_ATOP);
        add_newfarmpond_iv.setVisibility(View.GONE);


        sharedpref_farmerid_Obj = getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_farmerID = sharedpref_farmerid_Obj.getString(Key_FarmerID, "").trim();


        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_employee_id = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();


        str_employee_mailid = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employee_mailid, "").trim();

        str_perdayamount = sharedpreferencebook_usercredential_Obj.getString(KeyValue_perdayamount, "").trim();
        str_perdayamount="2000";


       sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        str_year=sharedpref_spinner_Obj.getString(Key_sel_yearsp,"").trim();

        /*String str_farmername =class_farmponddetails_obj.getFarmer_Name().toString() ;
        str_farmpond_id=class_farmponddetails_obj.getFarmpond_Id().toString();*/
//fetch the details previous activity

        //fetch the details previous activity

        str_farmer_id = "";
        Intent intent = getIntent();
        str_farmername = intent.getStringExtra("farmer_name");
        str_farmpond_id = intent.getStringExtra("farmpond_id");//tempfarmpond1573025107214
        str_farmer_id = intent.getStringExtra("farmer_id");
        str_approvedstatus = intent.getStringExtra("ApprovedStatus");//i.putExtra("ApprovedStatus", str_approved);

        Log.e("pondid", str_farmpond_id);
        Log.e("farmerid", str_farmer_id);
        Log.e("approved", str_approvedstatus);


        //fetch the details previous activity






        /*String str_farmername =class_farmponddetails_offline_obj.getFarmer_Name();
        str_farmpond_id=class_farmponddetails_offline_obj.getFarmpond_Id();*/


        edit_ponddetails_farmername_et = (TextView) findViewById(R.id.edit_ponddetails_farmername_et);
        edit_pondwidth_et = (EditText) findViewById(R.id.edit_pondwidth_et);
        edit_pondheight_et = (EditText) findViewById(R.id.edit_pondheight_et);
        edit_ponddepth_et = (EditText) findViewById(R.id.edit_ponddepth_et);
        submittedby_tv = (TextView) findViewById(R.id.submittedby_tv);

        edit_landacres_et = (EditText) findViewById(R.id.edit_landacres_et);
        edit_landgunta_et = (EditText) findViewById(R.id.edit_landgunta_et);

        image_id1_tv = (TextView) findViewById(R.id.image_id1_tv);
        image_id2_tv = (TextView) findViewById(R.id.image_id2_tv);
        image_id3_tv = (TextView) findViewById(R.id.image_id3_tv);


        edit_pond_image1_iv = (ImageView) findViewById(R.id.edit_pond_image1_iv);
        edit_pond_image2_iv = (ImageView) findViewById(R.id.edit_pond_image2_iv);
        edit_pond_image3_iv = (ImageView) findViewById(R.id.edit_pond_image3_iv);


        longitude_tv = (TextView) findViewById(R.id.longitude_tv);
        latitude_tv = (TextView) findViewById(R.id.latitude_tv);


        edit_removeimage1_ib = (ImageButton) findViewById(R.id.edit_removeimage1_ib);
        edit_removeimage2_ib = (ImageButton) findViewById(R.id.edit_removeimage2_ib);
        edit_removeimage3_ib = (ImageButton) findViewById(R.id.edit_removeimage3_ib);

        edit_ponddetails_submit_bt = (Button) findViewById(R.id.edit_ponddetails_submit_bt);
        edit_ponddetails_cancel_bt = (Button) findViewById(R.id.edit_ponddetails_cancel_bt);

        cancel_submit_ll = (LinearLayout) findViewById(R.id.cancel_submit_ll);

        edit_pond_startddate_tv = (TextView) findViewById(R.id.edit_pond_startddate_tv);
        edit_pond_completeddate_tv = (TextView) findViewById(R.id.edit_pond_completeddate_tv);

        // edit_pondamount_tv=(TextView)findViewById(R.id.edit_pondamount_tv);
        edit_pond_total_amount_tv = (TextView) findViewById(R.id.edit_pond_total_amount_tv);
        edit_pond_no_of_days_et = (EditText) findViewById(R.id.edit_pond_no_of_days_et);
        edit_farmpond_completed_cb = (CheckBox) findViewById(R.id.edit_farmpond_completed_cb);
        selectmachineno_sp = (Spinner) findViewById(R.id.selectmachineno_sp);
        edit_selectremarks_sp = (Spinner) findViewById(R.id.edit_selectremarks_sp);
        edit_amountcollected_et = findViewById(R.id.edit_amountcollected_et);

        amount_LL = (LinearLayout) findViewById(R.id.amount_LL);
        startdate_LL = (LinearLayout) findViewById(R.id.startdate_LL);
        completeddate_LL = (LinearLayout) findViewById(R.id.completeddate_LL);
        nodays_LL = (LinearLayout) findViewById(R.id.nodays_LL);
        machineno_LL = (LinearLayout) findViewById(R.id.machineno_LL);
        farmpondcompleted_LL = (LinearLayout) findViewById(R.id.farmpondcompleted_LL);
        notcompleted_text_LL = (LinearLayout) findViewById(R.id.notcompleted_text_LL);
        remarks_LL = findViewById(R.id.remarks_LL);
        amountcollected_LL = (LinearLayout) findViewById(R.id.amountcollected_LL);


        farmpondlocationlabel_LL = (LinearLayout) findViewById(R.id.farmpondlocationlabel_LL);
        dblatitude_LL = (LinearLayout) findViewById(R.id.dblatitude_LL);
        dblongitude_LL = (LinearLayout) findViewById(R.id.dblongitude_LL);
        dblatitude_tv = (TextView) findViewById(R.id.dblatitude_tv);
        dblongitude_tv = (TextView) findViewById(R.id.dblongitude_tv);

        currentlatitude_LL = (LinearLayout) findViewById(R.id.currentlatitude_LL);
        currentlongitude_LL = (LinearLayout) findViewById(R.id.currentlongitude_LL);
        fpondcompleted_ll = (LinearLayout) findViewById(R.id.fpondcompleted_ll);

        amountcollected_lesser_LL = (LinearLayout) findViewById(R.id.amountcollected_lesser_LL);


        startdate_LL.setVisibility(View.GONE);
        completeddate_LL.setVisibility(View.GONE);
        nodays_LL.setVisibility(View.GONE);
        machineno_LL.setVisibility(View.GONE);
        remarks_LL.setVisibility(View.GONE);
        amount_LL.setVisibility(View.GONE);
        amountcollected_LL.setVisibility(View.GONE);
        farmpondcompleted_LL.setVisibility(View.GONE);

        farmpondlocationlabel_LL.setVisibility(View.GONE);
        dblatitude_LL.setVisibility(View.GONE);
        dblongitude_LL.setVisibility(View.GONE);
        currentlatitude_LL.setVisibility(View.GONE);
        currentlongitude_LL.setVisibility(View.GONE);
        amountcollected_lesser_LL.setVisibility(View.GONE);

        //edit_pondamount_tv.setText("X "+str_perdayamount+" =");

        int_newfarmercount=0;
        str_image1 = str_image2 = str_image3 = "false";
        str_Is_image1 = str_Is_image2 = str_Is_image3 = "false";//for conversion to byte64 string
        str_cancelclicked = "false";
        edit_ponddetails_farmername_et.setText(str_farmername);
        submittedby_tv.setText(str_employee_mailid);
        str_location = "no";


        if (str_approvedstatus.isEmpty() || str_approvedstatus.equalsIgnoreCase("no") ||
                str_approvedstatus.equalsIgnoreCase("0") ||
                str_approvedstatus.equalsIgnoreCase("Pending") ||
                str_approvedstatus.equalsIgnoreCase("Rejected") ||
                str_approvedstatus.equalsIgnoreCase("Deleted")
        ) {

        } else {
            if (str_approvedstatus.equalsIgnoreCase("Approved")) {
                cancel_submit_ll.setVisibility(View.GONE);
                fpondcompleted_ll.setVisibility(View.VISIBLE);
            } else {
            }
        }


        //date

        Calendar c = Calendar.getInstance();
        //System.out.println("Current time => "+c.getTime());

        //  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Log.e("date", formattedDate);

        str_submitteddatetime = formattedDate;
        str_validation_for_completed = "no";


        //date


        str_latitude = "";
        str_longitude = "";

        gpstracker_obj2 = new Class_GPSTracker(EditFarmPondDetails_Activity.this);
        if (gpstracker_obj2.canGetLocation())
        {
            /*appLocationService = new AppLocationService(
                    EditFarmPondDetails_Activity.this);
            android.location.Location nwLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

            if (nwLocation != null) {
                double_currentlatitude = nwLocation.getLatitude();
                double_currentlongitude = nwLocation.getLongitude();
                lat_str = Double.toString(double_currentlatitude);
                log_str = Double.toString(double_currentlongitude);
                Log.e(TAG, "latitude" + lat_str);
                Log.e(TAG, "longitude" + log_str);
                Toast.makeText(EditFarmPondDetails_Activity.this, " Edit latitude=" + lat_str + " longitude=" + log_str, Toast.LENGTH_LONG).show();
                latitude_tv.setText(lat_str);
                longitude_tv.setText(log_str);


            } else {
                if (lat_str == null || log_str == null || lat_str.equals("0.0") || log_str.equals("0.0")) {
                   // alertdialog_refresh_latandlong();
                    latitude_tv.setText("0.0");
                    longitude_tv.setText("0.0");

                }
                Toast.makeText(EditFarmPondDetails_Activity.this, " after camera latitude=" + lat_str + " longitude=" + log_str, Toast.LENGTH_LONG).show();
            }

            String str_lattest = Double.toString(double_currentlatitude);
            String str_longtest = Double.toString(double_currentlongitude);


            if (str_lattest.equals("0.0") || str_longtest.equals("0.0")) {
               // alertdialog_refresh_latandlong();
            }
*/


            double_currentlatitude = gpstracker_obj2.getLatitude();
            double_currentlongitude = gpstracker_obj2.getLongitude();
            String str_lattest=Double.toString(double_currentlatitude);
            String str_longtest=Double.toString(double_currentlongitude);
            if(str_lattest.equals("0.0")||str_longtest.equals("0.0"))
            {
                alertdialog_refresh_latandlong();
            }

        } else {
            gpstracker_obj2.showSettingsAlert();
        }


        /*arraylist_image1_base64.add("0");
        arraylist_image2_base64.add("0");
        arraylist_image3_base64.add("0");*/

        arraylist_image1_base64.add("noimage1");
        arraylist_image2_base64.add("noimage2");
        arraylist_image3_base64.add("noimage3");


        str_image1present = str_image2present = str_image3present = "no";
        int_k=0;


        uploadfromDB_Machinelist();
        uploadfromDB_Remarkslist();

        Data_from_PondDetails_DB(str_farmpond_id);




        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds
        dialog_location = new ProgressDialog(EditFarmPondDetails_Activity.this);
        locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        Log.e("inside location", "inside location");
                        if (!isContinue)
                        {
                            Log.e("Flat",String.valueOf(location.getLatitude()));
                            Log.e("Flong",String.valueOf(location.getLongitude()));

                        } else {

                        }
                        if (!isContinue && mFusedLocationClient != null) {
                            // if (isContinue && mFusedLocationClient != null)
                            //{
                            try {
                                Thread.sleep(1 * 500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                            dialog_location.dismiss();

                            str_currentlatitude= String.valueOf(location.getLatitude());
                            str_currentlongitude= String.valueOf(location.getLongitude());

                            latitude_tv.setText(str_currentlatitude);
                            longitude_tv.setText(str_currentlongitude);
                            select_thirdimage();

                        }
                    } else {
                        Log.e("location", "null");
                        dialog_location.dismiss();
                    }
                }
            }
        };













        edit_pond_image1_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_image1 = "true";
                str_image2 = "false";
                str_image3 = "false";
                selectImage();
            }
        });
        edit_pond_image2_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_image2 = "true";

                str_image1 = "false";
                str_image3 = "false";
                selectImage();
            }
        });

        edit_pond_image3_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_image3 = "true";

                str_image1 = "false";
                str_image2 = "false";


                /*if (gps_enable())
                {
                    locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
                    isGPSON = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    getLocation();
                }*/

                if (gps_enable())
                {
                getLocation_oldphp(); }


            }
        });


        edit_removeimage1_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist_image1_base64.clear();
                arraylist_image1_base64.add("noimage1");

                str_image1present = "no";
                edit_removeimage1_ib.setVisibility(View.GONE);
                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                edit_pond_image1_iv.setImageDrawable(res);

            }
        });

        edit_removeimage2_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist_image2_base64.clear();
                arraylist_image2_base64.add("noimage2");

                str_image2present = "no";
                edit_removeimage2_ib.setVisibility(View.GONE);

                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                edit_pond_image2_iv.setImageDrawable(res);

            }
        });
        edit_removeimage3_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist_image3_base64.clear();
                arraylist_image3_base64.add("noimage3");
                edit_removeimage3_ib.setVisibility(View.GONE);

                str_location = "no";
                str_image3present = "no";
                str_validation_for_completed = "no";
                startdate_LL.setVisibility(View.GONE);
                completeddate_LL.setVisibility(View.GONE);
                nodays_LL.setVisibility(View.GONE);
                machineno_LL.setVisibility(View.GONE);
                farmpondcompleted_LL.setVisibility(View.GONE);
                remarks_LL.setVisibility(View.GONE);
                amount_LL.setVisibility(View.GONE);
                amountcollected_LL.setVisibility(View.GONE);

                notcompleted_text_LL.setVisibility(View.VISIBLE);

                farmpondlocationlabel_LL.setVisibility(View.GONE);
                currentlatitude_LL.setVisibility(View.GONE);
                currentlongitude_LL.setVisibility(View.GONE);
                dblatitude_LL.setVisibility(View.GONE);
                dblongitude_LL.setVisibility(View.GONE);
                str_latitude = "";
                str_longitude = "";


                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                edit_pond_image3_iv.setImageDrawable(res);
            }
        });


        edit_ponddetails_submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    // AsyncTask_submit_edited_farmponddetails();

                    cancel_submit_ll.setVisibility(View.GONE);
                    update_editedDetails_PondDetails_DB();
                }
            }
        });


        edit_ponddetails_cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_cancelclicked = "true";
                onBackPressed();
            }
        });


        selectmachineno_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                class_machineDetails_obj = (Class_MachineDetails) selectmachineno_sp.getSelectedItem();
                // str_machinecode = class_machineDetails_obj.getMachine_Code().toString();
                str_machinecode = class_machineDetails_obj.getMachine_ID().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edit_selectremarks_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                class_remarksdetails_obj = (Class_RemarksDetails) edit_selectremarks_sp.getSelectedItem();
                str_remarksid = class_remarksdetails_obj.getRemarks_ID().toString();
                // Toast.makeText(getApplicationContext(),"re :"+str_remarksid,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





       /* edit_farmpond_completed_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(edit_farmpond_completed_cb.isChecked())
                {
                    edit_farmpond_completed_cb.setChecked(true);

                    str_validation_for_completed="yes";
                    startdate_LL.setVisibility(View.VISIBLE);
                    completeddate_LL.setVisibility(View.VISIBLE);
                    nodays_LL.setVisibility(View.VISIBLE);
                    machineno_LL.setVisibility(View.VISIBLE);
                    // Toast.makeText(getApplicationContext(),"checked",Toast.LENGTH_SHORT).show();
                }
                else if(!edit_farmpond_completed_cb.isChecked())
                {
                    edit_farmpond_completed_cb.setChecked(false);
                    str_validation_for_completed="no";
                    startdate_LL.setVisibility(View.GONE);
                    completeddate_LL.setVisibility(View.GONE);
                    nodays_LL.setVisibility(View.GONE);
                    machineno_LL.setVisibility(View.GONE);
                    //Toast.makeText(getApplicationContext(),"unchecked",Toast.LENGTH_SHORT).show();
                }

            }
        });*/


        edit_pond_startddate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment dFragment_startdate = new DatePickerFragment_startdate();
                // Show the date picker dialog fragment
                dFragment_startdate.show(getFragmentManager(), "Date Picker");

            }
        });


        edit_pond_completeddate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* DialogFragment fromdateFragment = new DatePickerFragmentFromDate();
                fromdateFragment.show(getFragmentManager(), "Date Picker");*/

                DialogFragment dFragment = new DatePickerFragment_todate();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");


            }
        });


        edit_pond_no_of_days_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                /*Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();
                add_newpond_total_amount_tv.setText(s.toString());*/

                if (s.toString().isEmpty()) {
                    edit_pond_total_amount_tv.setText("");
                    // add_newpond_no_of_days_et.setText("");
                } else {

                    if (s.toString().equalsIgnoreCase(".")) {

                    } else {
                        /*int int_amount = Integer.parseInt(s.toString());
                        int int_per_day_amount = Integer.parseInt(str_perdayamount);
                        int_amount = int_amount * int_per_day_amount;

                        String str_totalamount = String.valueOf(int_amount);
                        edit_pond_total_amount_tv.setText(str_totalamount);*/


                        Log.e("watcher", s.toString());

                        Pattern p = Pattern.compile("[.!?]");
                        Matcher matcher = p.matcher(s.toString());
                        int count1 = 0;
                        while (matcher.find()) {
                            count1++;
                        }

                        if (count1 > 1) {
                            edit_pond_total_amount_tv.setText("0");
                        } else {


                            double deci_amount = Double.parseDouble((s.toString()));
                            long long_per_day_amount = Long.parseLong(str_perdayamount);
                            deci_amount = deci_amount * long_per_day_amount;
                            int int_amount = (int) Math.round(deci_amount);
                            String str_totalamount = String.valueOf(int_amount);
                            edit_pond_total_amount_tv.setText(str_totalamount);

                        }

                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {

                }
            }
        });


        edit_amountcollected_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    amountcollected_lesser_LL.setVisibility(View.GONE);
                } else {

                    String str_amt = edit_amountcollected_et.getText().toString().trim();
                    //int int_amtcollected = Integer.parseInt(str_amt);

                    Pattern p = Pattern.compile("[.!?]");
                    Matcher matcher = p.matcher(str_amt.toString());
                    int count1 = 0;
                    while (matcher.find()) {
                        count1++;
                    }

                    if (count1 > 1) {

                    } else {


                        char first_char = edit_amountcollected_et.getText().toString().trim().charAt(0);
                        String str_first_char = String.valueOf(first_char);

                        if (str_first_char.equalsIgnoreCase(".")) {
                            amountcollected_lesser_LL.setVisibility(View.VISIBLE);
                        } else {

                            double deci_amount = Double.parseDouble(edit_amountcollected_et.getText().toString().trim());

                            int int_amtcollected = (int) Math.round(deci_amount);
                            Log.e("amt", String.valueOf(int_amtcollected));

                            int int_totalamt = Integer.parseInt(edit_pond_total_amount_tv.getText().toString());


                            if (int_amtcollected < int_totalamt) {
                                amountcollected_lesser_LL.setVisibility(View.VISIBLE);
                                // Log.e("adderror", "error");
                            } else {
                                amountcollected_lesser_LL.setVisibility(View.GONE);
                            }

                        }

                    }


                }//main else

            }//main

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }// end of Oncreate();


    public void alertdialog_refresh_latandlong() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(EditFarmPondDetails_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Click Ok to fetch latitude and Longitude");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Intent i = new Intent(EditFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
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


    public void uploadfromDB_Machinelist() {


        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineIDDB VARCHAR);");
        Cursor cursor = db1.rawQuery("SELECT DISTINCT * FROM MachineDetails_fromServerRest", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        class_machineDetails_array_obj = new Class_MachineDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_MachineDetails innerObj_class_machinelist = new Class_MachineDetails();
                innerObj_class_machinelist.setMachine_Name(cursor.getString(cursor.getColumnIndex("MachineNameDB")));
                // innerObj_class_machinelist.setMachine_Code(cursor.getString(cursor.getColumnIndex("MachineCodeDDB")));
                innerObj_class_machinelist.setMachine_ID(cursor.getString(cursor.getColumnIndex("MachineIDDB")));

                class_machineDetails_array_obj[i] = innerObj_class_machinelist;
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, class_machineDetails_array_obj);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            selectmachineno_sp.setAdapter(dataAdapter);

            // selectmachineno_sp.setSelection(getIndex(selectmachineno_sp, "03"));

            /*if(x>sel_yearsp) {
                yearlist_SP.setSelection(sel_yearsp);
            }*/
        }

    }


    public void search_Machinelist(String str_machineID) {
        Log.e("InsideMachine", "Machinelist");

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineIDDB VARCHAR);");

        // Cursor cursor = db1.rawQuery("SELECT DISTINCT * FROM MachineDetails_fromServer WHERE MachineIDDB='" + str_machineID + "'", null);

        Cursor cursor = db1.rawQuery("SELECT DISTINCT * FROM MachineDetails_fromServerRest", null);
        int x = cursor.getCount();

        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        class_machineDetails_array_obj = new Class_MachineDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_MachineDetails innerObj_class_machinelist = new Class_MachineDetails();
                innerObj_class_machinelist.setMachine_Name(cursor.getString(cursor.getColumnIndex("MachineNameDB")));
                // innerObj_class_machinelist.setMachine_Code(cursor.getString(cursor.getColumnIndex("MachineCodeDDB")));
                innerObj_class_machinelist.setMachine_ID(cursor.getString(cursor.getColumnIndex("MachineIDDB")));

                class_machineDetails_array_obj[i] = innerObj_class_machinelist;
                i++;
            } while (cursor.moveToNext());
        }//if ends

        db1.close();


        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineIDDB VARCHAR);");

        Cursor cursor1 = db2.rawQuery("SELECT DISTINCT * FROM MachineDetails_fromServerRest WHERE MachineIDDB='" + str_machineID + "'", null);
        //Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM MachineDetails_fromServer", null);
        int x1 = cursor1.getCount();
        int i1 = 0;
        Class_MachineDetails class_machineDetails_obj3 = new Class_MachineDetails();
        if (cursor1.moveToFirst()) {

            do {
                Class_MachineDetails innerObj_class_machinelist = new Class_MachineDetails();
                innerObj_class_machinelist.setMachine_Name(cursor1.getString(cursor.getColumnIndex("MachineNameDB")));
                // innerObj_class_machinelist.setMachine_Code(cursor1.getString(cursor.getColumnIndex("MachineCodeDDB")));
                innerObj_class_machinelist.setMachine_ID(cursor1.getString(cursor.getColumnIndex("MachineIDDB")));

                class_machineDetails_obj3 = innerObj_class_machinelist;
                i1++;

            } while (cursor1.moveToNext());
        }//if ends
        db2.close();

        String str_comparevalue;
        //   if(class_machineDetails_obj3.getMachine_Code().isEmpty()||class_machineDetails_obj3.equals(null))
        if (class_machineDetails_obj3.getMachine_ID() == null || class_machineDetails_obj3.getMachine_ID().isEmpty()) {
            str_comparevalue = "Nocode";
            Log.e("comparevalue", str_comparevalue);
        } else {
            str_comparevalue = class_machineDetails_obj3.getMachine_ID().toString();
            Log.e("comparevalueelse", str_comparevalue);
        }

        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, class_machineDetails_array_obj);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            selectmachineno_sp.setAdapter(dataAdapter);

            selectmachineno_sp.setSelection(getIndex(selectmachineno_sp, str_comparevalue));
        }
    }


    public void uploadfromDB_Remarkslist() {


        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS RemarksDetails_fromServer(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,RemarksIDDB VARCHAR,RemarksNameDB VARCHAR);");
        Cursor cursor = db1.rawQuery("SELECT DISTINCT * FROM RemarksDetails_fromServer", null);

        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        class_remarksdetails_array_obj = new Class_RemarksDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_RemarksDetails innerObj_class_remarkslist = new Class_RemarksDetails();

                innerObj_class_remarkslist.setRemarks_ID(cursor.getString(cursor.getColumnIndex("RemarksIDDB")));
                innerObj_class_remarkslist.setRemarks_Name(cursor.getString(cursor.getColumnIndex("RemarksNameDB")));
                //

                class_remarksdetails_array_obj[i] = innerObj_class_remarkslist;
                i++;

            } while (cursor.moveToNext());
        }//if ends
        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, class_remarksdetails_array_obj);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            edit_selectremarks_sp.setAdapter(dataAdapter);
            /*if(x>sel_yearsp) {
                yearlist_SP.setSelection(sel_yearsp);
            }*/
        }
    }


    private int getIndex_remarks(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {

            //  Log.e("spinner",spinner.getItemAtPosition(i).toString());
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }


    //private method of your class
    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }


    public void Data_from_PondDetails_DB(String str_farmerpondID) {

        Log.e("editfarmerID", str_farmerpondID);
        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        //FarmPond_db
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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM FarmPondDetails_fromServerRest WHERE FPondidDB='" + str_farmerpondID + "'", null);
        int x = cursor1.getCount();

        Log.e("Editfarmerid", String.valueOf(x));
        Log.e("Editfarmpond_count", String.valueOf(x));


        int i = 0;
        class_farmponddetails_offline_obj = new Class_farmponddetails_offline();
        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    Class_farmponddetails_offline innerObj_Class_farmponddetails_offline = new Class_farmponddetails_offline();

                    innerObj_Class_farmponddetails_offline.setfarmer_id(cursor1.getString(cursor1.getColumnIndex("FIDDB")));
                    innerObj_Class_farmponddetails_offline.setFarmer_Name(cursor1.getString(cursor1.getColumnIndex("FNameDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_Id(cursor1.getString(cursor1.getColumnIndex("FPondidDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_Width(cursor1.getString(cursor1.getColumnIndex("WidthDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_Height(cursor1.getString(cursor1.getColumnIndex("HeightDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_Depth(cursor1.getString(cursor1.getColumnIndex("DepthDB")));

                    innerObj_Class_farmponddetails_offline.setImage1_ID(cursor1.getString(cursor1.getColumnIndex("Imageid1DB")));
                    innerObj_Class_farmponddetails_offline.setImage1_Base64(cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")));

                    innerObj_Class_farmponddetails_offline.setImage2_ID(cursor1.getString(cursor1.getColumnIndex("Imageid2DB")));
                    innerObj_Class_farmponddetails_offline.setImage2_Base64(cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")));

                    innerObj_Class_farmponddetails_offline.setImage3_ID(cursor1.getString(cursor1.getColumnIndex("Imageid3DB")));
                    innerObj_Class_farmponddetails_offline.setImage3_Base64(cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")));

                    innerObj_Class_farmponddetails_offline.setUploadedStatus(cursor1.getString(cursor1.getColumnIndex("UploadedStatus")));


                    innerObj_Class_farmponddetails_offline.setEmployeeID(str_employee_id);


                    Log.e("TotalDaysDB", cursor1.getString(cursor1.getColumnIndex("TotalDaysDB")));

                    innerObj_Class_farmponddetails_offline.setSubmittedDateTime(cursor1.getString(cursor1.getColumnIndex("SubmittedDateDB")));


                    innerObj_Class_farmponddetails_offline.setTotal_no_days(cursor1.getString(cursor1.getColumnIndex("TotalDaysDB")));
                    innerObj_Class_farmponddetails_offline.setConstructedDate(cursor1.getString(cursor1.getColumnIndex("ConstructedDateDB")));
                    innerObj_Class_farmponddetails_offline.setPondCost(cursor1.getString(cursor1.getColumnIndex("PondCostDB")));
                    innerObj_Class_farmponddetails_offline.setMachineCode(cursor1.getString(cursor1.getColumnIndex("McodeDB")));

                    innerObj_Class_farmponddetails_offline.setStartDate(cursor1.getString(cursor1.getColumnIndex("StartDateDB")));

                    Log.e("EndDateDB", cursor1.getString(cursor1.getColumnIndex("ConstructedDateDB")));
                    Log.e("StartDateDB", cursor1.getString(cursor1.getColumnIndex("StartDateDB")));


                    innerObj_Class_farmponddetails_offline.setFarmpond_remarks(cursor1.getString(cursor1.getColumnIndex("FPondRemarksDB")));

                    if(cursor1.getString(cursor1.getColumnIndex("FPondAmtTakenDB")).equals(null) ||
                            cursor1.getString(cursor1.getColumnIndex("FPondAmtTakenDB")).equalsIgnoreCase("null") )
                    {innerObj_Class_farmponddetails_offline.setFarmpond_amttaken("0"); }
                    else
                    {innerObj_Class_farmponddetails_offline.setFarmpond_amttaken(cursor1.getString(cursor1.getColumnIndex("FPondAmtTakenDB"))); }


                    innerObj_Class_farmponddetails_offline.setFarmpond_status(cursor1.getString(cursor1.getColumnIndex("FPondStatusDB")));
                    // "FPondRemarksDB VARCHAR,FPondAmtTakenDB VARCHAR,FPondStatusDB VARCHAR,"


                    // "FPondAcresDB VARCHAR,FPondGuntaDB VARCHAR,FPondCropBeforeDB VARCHAR,FPondCropAfterDB VARCHAR," +
                    innerObj_Class_farmponddetails_offline.setFarmpond_acres(cursor1.getString(cursor1.getColumnIndex("FPondAcresDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_gunta(cursor1.getString(cursor1.getColumnIndex("FPondGuntaDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_crop_before_pond(cursor1.getString(cursor1.getColumnIndex("FPondCropBeforeDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_crop_after_pond(cursor1.getString(cursor1.getColumnIndex("FPondCropAfterDB")));


                    innerObj_Class_farmponddetails_offline.setFarmpond_Latitude(cursor1.getString(cursor1.getColumnIndex("FPondLatitudeDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_Longitude(cursor1.getString(cursor1.getColumnIndex("FPondLongitudeDB")));
                    //"FPondLatitudeDB VARCHAR,FPondLongitudeDB VARCHAR," +

                    // innerObj_Class_farmponddetails_offline.setMachineCode(cursor1.getString(cursor1.getColumnIndex("FPondCodeDB")));
                    class_farmponddetails_offline_obj = innerObj_Class_farmponddetails_offline;
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }

        db1.close();

        if (x > 0) {
            if (class_farmponddetails_offline_obj != null) {
                DisplayData_Data_from_PondDetails_DB();

            } else {
                Log.e("onPostExecute", "class_farmponddetails_array_obj == null");
            }

        }


    }// end of Data_from_PondDetails_DB


    public void DisplayData_Data_from_PondDetails_DB() {


        edit_ponddetails_farmername_et.setText(class_farmponddetails_offline_obj.getFarmer_Name());
        edit_pondwidth_et.setText(class_farmponddetails_offline_obj.getFarmpond_Width());
        edit_pondheight_et.setText(class_farmponddetails_offline_obj.getFarmpond_Height());
        edit_ponddepth_et.setText(class_farmponddetails_offline_obj.getFarmpond_Depth());

        edit_landacres_et.setText(class_farmponddetails_offline_obj.getFarmpond_acres());
        edit_landgunta_et.setText(class_farmponddetails_offline_obj.getFarmpond_gunta());


        Log.e("status", class_farmponddetails_offline_obj.getFarmpond_status());

        if (class_farmponddetails_offline_obj.getFarmpond_status().trim().isEmpty() ||
                class_farmponddetails_offline_obj.getFarmpond_status().equals(null)) {
            notcompleted_text_LL.setVisibility(View.VISIBLE);
        } else {
           /* if(class_farmponddetails_offline_obj.getFarmpond_status().trim().equalsIgnoreCase("0")||
                    class_farmponddetails_offline_obj.getFarmpond_status().trim().equalsIgnoreCase("3"))
*/
            if (class_farmponddetails_offline_obj.getFarmpond_status().trim().equalsIgnoreCase("0")) {
                notcompleted_text_LL.setVisibility(View.GONE);
            } else {
                notcompleted_text_LL.setVisibility(View.GONE);

            }
        }


        image_id1_tv.setText(class_farmponddetails_offline_obj.getImage1_ID());
        image_id2_tv.setText(class_farmponddetails_offline_obj.getImage2_ID());
        image_id3_tv.setText(class_farmponddetails_offline_obj.getImage3_ID());

        String str_base64images1 = class_farmponddetails_offline_obj.getImage1_Base64();
        String str_base64images2 = class_farmponddetails_offline_obj.getImage2_Base64();
        String str_base64images3 = class_farmponddetails_offline_obj.getImage3_Base64();

      //  Log.e("str_base64images3", class_farmponddetails_offline_obj.getImage3_Base64());


        arraylist_image1_base64.clear();
        arraylist_image2_base64.clear();
        arraylist_image3_base64.clear();

        Class_base64toBitmapConversion class_base64toBitmapConversion_obj = new Class_base64toBitmapConversion();

        arrayList_bitmap = class_base64toBitmapConversion_obj.base64tobitmap(class_farmponddetails_offline_obj);

        // edit_pond_image1_iv.setImageBitmap(arrayList_bitmap.get(0));
        if (str_base64images1.equalsIgnoreCase("noimage1") ||
                str_base64images1.equalsIgnoreCase("0")) {
            arraylist_image1_base64.add("noimage1");

        } else {
            arraylist_image1_base64.add(str_base64images1);
            edit_pond_image1_iv.setImageBitmap(arrayList_bitmap.get(0));
        }


        if (str_base64images2.equalsIgnoreCase("noimage2") ||
                str_base64images2.equalsIgnoreCase("0")) {
            arraylist_image2_base64.add("noimage2");

        } else {
            arraylist_image2_base64.add(str_base64images2);
            edit_pond_image2_iv.setImageBitmap(arrayList_bitmap.get(1));
            str_image2present = "yes";
        }


        if (str_base64images3.equalsIgnoreCase("noimage3") ||
                str_base64images3.equalsIgnoreCase("0")) {

            Log.e("str_base64images3inside", class_farmponddetails_offline_obj.getImage3_Base64());
            str_validation_for_completed = "no";
            startdate_LL.setVisibility(View.GONE);
            completeddate_LL.setVisibility(View.GONE);
            nodays_LL.setVisibility(View.GONE);
            machineno_LL.setVisibility(View.GONE);
            farmpondcompleted_LL.setVisibility(View.GONE);
            remarks_LL.setVisibility(View.GONE);
            amount_LL.setVisibility(View.GONE);
            amountcollected_LL.setVisibility(View.GONE);

            farmpondlocationlabel_LL.setVisibility(View.GONE);
            dblatitude_LL.setVisibility(View.GONE);
            dblongitude_LL.setVisibility(View.GONE);


        } else
            {
                str_image3present = "yes";
            arraylist_image3_base64.add(str_base64images3);
            edit_pond_image3_iv.setImageBitmap(arrayList_bitmap.get(2));

            farmpondlocationlabel_LL.setVisibility(View.VISIBLE);
            dblatitude_LL.setVisibility(View.VISIBLE);
            dblongitude_LL.setVisibility(View.VISIBLE);

            dblatitude_tv.setText(class_farmponddetails_offline_obj.getFarmpond_Latitude());
            dblongitude_tv.setText(class_farmponddetails_offline_obj.getFarmpond_Longitude());

            str_latitude = class_farmponddetails_offline_obj.getFarmpond_Latitude();
            str_longitude = class_farmponddetails_offline_obj.getFarmpond_Longitude();

        }


        Log.e("daysDB", class_farmponddetails_offline_obj.getTotal_no_days().toString());

        if (class_farmponddetails_offline_obj.getTotal_no_days().equalsIgnoreCase("0")) {
            edit_pond_completeddate_tv.setText(str_submitteddatetime);
        }
        if (class_farmponddetails_offline_obj.getStartDate().equalsIgnoreCase("0")) {
            edit_pond_startddate_tv.setText(str_submitteddatetime);
        }


        Log.e("no_days", class_farmponddetails_offline_obj.getTotal_no_days());
        if (class_farmponddetails_offline_obj.getTotal_no_days().equalsIgnoreCase("0")
                || str_base64images3.equalsIgnoreCase("noimage3")) {


            edit_farmpond_completed_cb.setChecked(false);
            startdate_LL.setVisibility(View.GONE);
            completeddate_LL.setVisibility(View.GONE);
            nodays_LL.setVisibility(View.GONE);
            machineno_LL.setVisibility(View.GONE);
            remarks_LL.setVisibility(View.GONE);
            amount_LL.setVisibility(View.GONE);
            amountcollected_LL.setVisibility(View.GONE);
        } else {

            edit_farmpond_completed_cb.setChecked(true);
            startdate_LL.setVisibility(View.VISIBLE);
            completeddate_LL.setVisibility(View.VISIBLE);
            nodays_LL.setVisibility(View.VISIBLE);
            machineno_LL.setVisibility(View.VISIBLE);
            remarks_LL.setVisibility(View.VISIBLE);
            amount_LL.setVisibility(View.GONE);
            amountcollected_LL.setVisibility(View.VISIBLE);

            str_validation_for_completed = "yes";


            if (class_farmponddetails_offline_obj.getStartDate().equalsIgnoreCase("0")) {
                edit_pond_startddate_tv.setText(str_submitteddatetime);
            } else {
                edit_pond_startddate_tv.setText(class_farmponddetails_offline_obj.getStartDate().toString());
            }
            //s.substring(0, s.length() - 2)

            if (class_farmponddetails_offline_obj.getConstructedDate().toString().trim().length() > 12) {
                String s = class_farmponddetails_offline_obj.getConstructedDate();
                s = s.substring(0, s.length() - 8);
                edit_pond_completeddate_tv.setText(s.trim());
            } else {
                edit_pond_completeddate_tv.setText(class_farmponddetails_offline_obj.getConstructedDate().toString());
            }
            // edit_pond_completeddate_tv.setText(class_farmponddetails_offline_obj.getConstructedDate().toString());
            edit_pond_no_of_days_et.setText(class_farmponddetails_offline_obj.getTotal_no_days().toString());
            edit_pond_total_amount_tv.setText(class_farmponddetails_offline_obj.getPondCost());

            edit_amountcollected_et.setText(class_farmponddetails_offline_obj.getFarmpond_amttaken());

            Log.e("remarksID", class_farmponddetails_offline_obj.getFarmpond_remarks());

            search_Remarkslist(class_farmponddetails_offline_obj.getFarmpond_remarks());
            search_Machinelist(class_farmponddetails_offline_obj.getMachineCode());
        }

    }//


    public void search_Remarkslist(String str_remarksID) {

        Log.e("remarks", str_remarksID);
        if (str_remarksID.equalsIgnoreCase("0")) {
            // str_remarksID="4";
            str_remarksID = "100";
        }

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS RemarksDetails_fromServer(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,RemarksIDDB VARCHAR,RemarksNameDB VARCHAR);");
        Cursor cursor = db1.rawQuery("SELECT DISTINCT * FROM RemarksDetails_fromServer", null);
        int x = cursor.getCount();
        int i = 0;
        class_remarksdetails_array_obj = new Class_RemarksDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_RemarksDetails innerObj_class_remarkslist = new Class_RemarksDetails();
                innerObj_class_remarkslist.setRemarks_ID(cursor.getString(cursor.getColumnIndex("RemarksIDDB")));
                innerObj_class_remarkslist.setRemarks_Name(cursor.getString(cursor.getColumnIndex("RemarksNameDB")));


                class_remarksdetails_array_obj[i] = innerObj_class_remarkslist;
                i++;

            } while (cursor.moveToNext());
        }//if ends
        db1.close();


        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        Cursor cursor2 = db2.rawQuery("SELECT DISTINCT * FROM RemarksDetails_fromServer WHERE RemarksIDDB='" + str_remarksID + "'", null);
        int x2 = cursor2.getCount();
        Log.d("remarksScount", Integer.toString(x2));


        Class_RemarksDetails class_remarksDetails_obj3 = new Class_RemarksDetails();
        if (cursor2.moveToFirst()) {

            do {
                Class_RemarksDetails innerObj_class_remarkslist = new Class_RemarksDetails();

                innerObj_class_remarkslist.setRemarks_ID(cursor2.getString(cursor.getColumnIndex("RemarksIDDB")));
                innerObj_class_remarkslist.setRemarks_Name(cursor2.getString(cursor.getColumnIndex("RemarksNameDB")));
                //

                class_remarksDetails_obj3 = innerObj_class_remarkslist;


            } while (cursor2.moveToNext());
        }//if ends
        db2.close();


        String str_comparevalueR;

        if (class_remarksDetails_obj3.equals(null) || class_remarksDetails_obj3.getRemarks_ID().isEmpty()) {
            str_comparevalueR = "NoRemarks";
            Log.e("comparevalue", str_comparevalueR);
        } else {
            str_comparevalueR = class_remarksDetails_obj3.getRemarks_Name().toString();
            Log.e("comparevalueRe", str_comparevalueR);
        }


        if (x2 > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, class_remarksdetails_array_obj);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            edit_selectremarks_sp.setAdapter(dataAdapter);

            edit_selectremarks_sp.setSelection(getIndex_remarks(edit_selectremarks_sp, str_comparevalueR));
        }
    }


    private void selectImage() {
        final CharSequence[] items;

        if (str_image3.equalsIgnoreCase("true")) {
            List<String> listItems1 = new ArrayList<String>();
            listItems1.add("Take Photo");
            listItems1.add("Cancel");

            items = listItems1.toArray(new CharSequence[listItems1.size()]);
        } else {
            List<String> listItems2 = new ArrayList<String>();
            listItems2.add("Take Photo");
            listItems2.add("Choose from Library");
            listItems2.add("Cancel");

            items = listItems2.toArray(new CharSequence[listItems2.size()]);

        }





       /* final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};*/
        /* final CharSequence[] items = {"Take Photo","Cancel"};*/

        AlertDialog.Builder builder = new AlertDialog.Builder(EditFarmPondDetails_Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Class_utility.checkPermission(EditFarmPondDetails_Activity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Class_utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    private void galleryIntent() {
        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);*/

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == 2) {


                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = getResizedBitmap1(thumbnail, 400);
                Log.w(" gallery.", picturePath + "");
                BitMapToString1(thumbnail);

                /*add_newpond_image1_iv.setImageBitmap(thumbnail);
                BitMapToString1(thumbnail);*/

            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }


    //selection of gallery on hold
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                BitMapToString(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //add_newpond_image1_iv.setImageBitmap(bm);
       /* if(isInternetPresent){
            SaveFarmerImage();
        }else{

            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

        }*/

    }

    private void onCaptureImageResult(Intent data) {
        Bitmap bitmap_thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap_thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (str_image1.equals("true")) {
            str_image1 = "false";
            arraylist_image1_base64.clear();
            edit_pond_image1_iv.setImageBitmap(bitmap_thumbnail);
            edit_removeimage1_ib.setVisibility(View.VISIBLE);

            str_image1present = "yes";

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

            arraylist_image1_base64.clear();
            arraylist_image1_base64.add(str_base64imagestring);
            // BitMapToString(thumbnail);
        }
        if (str_image2.equals("true")) {
            arraylist_image2_base64.clear();
            edit_pond_image2_iv.setImageBitmap(bitmap_thumbnail);
            edit_removeimage2_ib.setVisibility(View.VISIBLE);
            str_image2 = "false";
            str_image2present = "yes";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

            arraylist_image2_base64.clear();
            arraylist_image2_base64.add(str_base64imagestring);
            //BitMapToString(thumbnail);
        }
        if (str_image3.equals("true")) {
            arraylist_image3_base64.clear();
            edit_pond_image3_iv.setImageBitmap(bitmap_thumbnail);
            edit_removeimage3_ib.setVisibility(View.VISIBLE);

            str_location = "yes";
            str_image3present = "yes";
            edit_farmpond_completed_cb.setChecked(true);

            notcompleted_text_LL.setVisibility(View.GONE);

            str_validation_for_completed = "yes";
            startdate_LL.setVisibility(View.VISIBLE);
            completeddate_LL.setVisibility(View.VISIBLE);
            nodays_LL.setVisibility(View.VISIBLE);
            machineno_LL.setVisibility(View.VISIBLE);
            farmpondcompleted_LL.setVisibility(View.VISIBLE);
            remarks_LL.setVisibility(View.VISIBLE);
            amount_LL.setVisibility(View.GONE);
            amountcollected_LL.setVisibility(View.VISIBLE);
            notcompleted_text_LL.setVisibility(View.GONE);

            dblatitude_LL.setVisibility(View.GONE);
            dblongitude_LL.setVisibility(View.GONE);

            farmpondlocationlabel_LL.setVisibility(View.VISIBLE);
            currentlatitude_LL.setVisibility(View.VISIBLE);
            currentlongitude_LL.setVisibility(View.VISIBLE);


            str_image3 = "false";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image3_base64.clear();
            arraylist_image3_base64.add(str_base64imagestring);
            // BitMapToString(bitmap_thumbnail);
        }
    }


    public Bitmap getResizedBitmap1(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

        return str_base64imagestring;
    }


    public void BitMapToString1(Bitmap userImage1) {


        if (str_image1.equalsIgnoreCase("true")) {
            str_image1 = "false";
            edit_pond_image1_iv.setImageBitmap(userImage1);
            edit_removeimage1_ib.setVisibility(View.VISIBLE);

            str_image1present = "yes";


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            userImage1.compress(Bitmap.CompressFormat.PNG, 90, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image1_base64.clear();
            arraylist_image1_base64.add(str_base64imagestring);
            str_base64imagestring = "";
        }

        if (str_image2.equalsIgnoreCase("true")) {
            str_image2 = "false";
            edit_pond_image2_iv.setImageBitmap(userImage1);
            edit_removeimage2_ib.setVisibility(View.VISIBLE);
            str_image2present = "yes";

            str_base64imagestring = "";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            userImage1.compress(Bitmap.CompressFormat.PNG, 90, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image2_base64.clear();
            arraylist_image2_base64.add(str_base64imagestring);
            str_base64imagestring = "";


        }


        if (str_image3.equalsIgnoreCase("true")) {
            str_image3 = "false";
            edit_pond_image3_iv.setImageBitmap(userImage1);
            edit_removeimage3_ib.setVisibility(View.VISIBLE);


            str_validation_for_completed = "yes";
            startdate_LL.setVisibility(View.VISIBLE);
            completeddate_LL.setVisibility(View.VISIBLE);
            nodays_LL.setVisibility(View.VISIBLE);
            machineno_LL.setVisibility(View.VISIBLE);
            farmpondcompleted_LL.setVisibility(View.VISIBLE);
            remarks_LL.setVisibility(View.VISIBLE);
            amount_LL.setVisibility(View.GONE);
            amountcollected_LL.setVisibility(View.VISIBLE);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            userImage1.compress(Bitmap.CompressFormat.PNG, 90, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image3_base64.clear();
            arraylist_image3_base64.add(str_base64imagestring);
            str_base64imagestring = "";

        }


        // return str_base64imagestring;
    }
//images

    //date
    @SuppressLint("ValidFragment")
    public static class DatePickerFragment_startdate extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);

            // Create a TextView programmatically.
            TextView tv = new TextView(getActivity());

            // Create a TextView programmatically
            android.widget.RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT, // Width of TextView
                    ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
            tv.setLayoutParams(lp);
            tv.setPadding(10, 10, 10, 10);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv.setText("This is a custom title.");
            tv.setTextColor(Color.parseColor("#ff0000"));
            tv.setBackgroundColor(Color.parseColor("#FFD2DAA7"));

            // Set the newly created TextView as a custom tile of DatePickerDialog
            //dpd.setCustomTitle(tv);

            // Or you can simply set a tile for DatePickerDialog
    /*
        setTitle(CharSequence title)
            Set the title text for this dialog's window.
    */
            // dpd.setTitle("Choose the Date"); // Uncomment this line to activate it

            // Return the DatePickerDialog
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the chosen date
            populateSetDate(year, month + 1, day);
        }

        public void populateSetDate(int year, int month, int day) {
            //mEdit = (EditText)findViewById(R.id.editText1);
            //mEdit.setText(month+"/"+day+"/"+year);

            //dateMDY_string=month+"/"+day+"/"+year;
            // String Dates = year + "-" +(month<10?("0"+month):(month)) + "-" + (day<10?("0"+day):(day));

            String Dates = (day < 10 ? ("0" + day) : (day)) + "-" + (month < 10 ? ("0" + month) : (month)) + "-" + year;

            String dateMDY_string = Dates;
            edit_pond_startddate_tv.setText(Dates);

            //dateMDY_string=year+"-"+month+"-"+day;

            //Toast.makeText(getApplicationContext(), x, 1000).show();
// tv_fromdate.setText(month+"/"+day+"/"+year);
            //btn.setText(year+"-"+month+"-"+day);
        }
    }// end of datepickerclass


    @SuppressLint("ValidFragment")
    public static class DatePickerFragment_todate extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);

            // Create a TextView programmatically.
            TextView tv = new TextView(getActivity());

            // Create a TextView programmatically
            android.widget.RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT, // Width of TextView
                    ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
            tv.setLayoutParams(lp);
            tv.setPadding(10, 10, 10, 10);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv.setText("This is a custom title.");
            tv.setTextColor(Color.parseColor("#ff0000"));
            tv.setBackgroundColor(Color.parseColor("#FFD2DAA7"));

            // Set the newly created TextView as a custom tile of DatePickerDialog
            //dpd.setCustomTitle(tv);

            // Or you can simply set a tile for DatePickerDialog
    /*
        setTitle(CharSequence title)
            Set the title text for this dialog's window.
    */
            // dpd.setTitle("Choose the Date"); // Uncomment this line to activate it

            // Return the DatePickerDialog
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the chosen date
            populateSetDate(year, month + 1, day);
        }

        public void populateSetDate(int year, int month, int day) {
            //mEdit = (EditText)findViewById(R.id.editText1);
            //mEdit.setText(month+"/"+day+"/"+year);

            //dateMDY_string=month+"/"+day+"/"+year;
            // String Dates = year + "-" +(month<10?("0"+month):(month)) + "-" + (day<10?("0"+day):(day));

            String Dates = (day < 10 ? ("0" + day) : (day)) + "-" + (month < 10 ? ("0" + month) : (month)) + "-" + year;

            String dateMDY_string = Dates;
            edit_pond_completeddate_tv.setText(Dates);

            //dateMDY_string=year+"-"+month+"-"+day;

            //Toast.makeText(getApplicationContext(), x, 1000).show();
// tv_fromdate.setText(month+"/"+day+"/"+year);

            //btn.setText(year+"-"+month+"-"+day);

        }
    }// end of datepickerclass

//date


//validation

    public boolean validation() {
        boolean b_pond_width1, b_pond_width2, b_pond_height1, b_pond_height2, b_pond_depth1, b_pond_depth2, b_pondimages,
        b_pondimages2, b_pond_completed_date, b_no_days, b_datevalidation, b_enteramount, b_reason;

        b_pond_width1 = b_pond_width2 = b_pond_height1 = b_pond_height2 = b_pond_depth1 = b_pond_depth2 = b_pondimages
       = b_pondimages2  = b_pond_completed_date = b_no_days = b_datevalidation = b_enteramount = b_reason = true;
        ;


        if (edit_pondwidth_et.getText().toString().length() == 0) {
            edit_pondwidth_et.setError("Empty not allowed");
            edit_pondwidth_et.requestFocus();
            b_pond_width1 = false;
        }

        if (edit_pondwidth_et.getText().toString().length() <= 1) {
            edit_pondwidth_et.setError("Enter Valid Width");
            edit_pondwidth_et.requestFocus();
            b_pond_width2 = false;
        }

        if (edit_pondheight_et.getText().toString().length() == 0) {
            edit_pondheight_et.setError("Empty not allowed");
            edit_pondheight_et.requestFocus();
            b_pond_height1 = false;
        }
        if (edit_pondheight_et.getText().toString().length() <= 1) {
            edit_pondheight_et.setError("Enter Valid Height");
            edit_pondheight_et.requestFocus();
            b_pond_height2 = false;
        }


        if (edit_ponddepth_et.getText().toString().length() == 0) {
            edit_ponddepth_et.setError("Empty not allowed");
            edit_ponddepth_et.requestFocus();
            b_pond_depth1 = false;
        }

        if (edit_ponddepth_et.getText().toString().length() <= 1) {
            edit_ponddepth_et.setError("Enter Valid Depth");
            edit_ponddepth_et.requestFocus();
            b_pond_depth2 = false;
        }

        // if (arraylist_image1_base64.size() == 0 || arraylist_image2_base64.size() == 0 || arraylist_image3_base64.size() == 0)
            /*if (arraylist_image1_base64.size() == 0 ||
                    arraylist_image2_base64.size() == 0 ||
                    arraylist_image3_base64.size() == 0)*/
           /* if(arraylist_image1_base64.get(0).equalsIgnoreCase("noimage1") ||
                    arraylist_image2_base64.get(0).equalsIgnoreCase("noimage2"))*/

        Log.e("edit_str_image1present",str_image1present);
           Log.e("edit_str_image2present",str_image2present);
        Log.e("edit_str_image3present",str_image3present);

        if (arraylist_image1_base64.get(0).equalsIgnoreCase("noimage1")) {
            Toast.makeText(getApplication(), "Add the 1st Pond Image", Toast.LENGTH_LONG).show();
            Log.e("inside", "if statement");
            b_pondimages = false;
        }

            if ((str_image2present.equalsIgnoreCase("no") &&
                    str_image3present.equalsIgnoreCase("yes"))) {
                Toast.makeText(getApplication(), "Add the 2nd Pond Image", Toast.LENGTH_LONG).show();
                b_pondimages2 = false;
            }


        if (str_validation_for_completed.equalsIgnoreCase("yes")) {
            // b_add_completed_date,b_no_days
            if (edit_pond_completeddate_tv.getText().toString().equalsIgnoreCase("Click-for_calendar") ||
                    edit_pond_completeddate_tv.getText().toString().equalsIgnoreCase("0")) {
                Toast.makeText(getApplication(), "Add the completed Date", Toast.LENGTH_LONG).show();
                b_pond_completed_date = false;
            }
            //Log.e("length", String.valueOf(add_newpond_no_of_days_et.getText().toString().trim().length()));
            if (edit_pond_no_of_days_et.getText().toString().trim().length() <= 0) {
                Toast.makeText(getApplication(), "Add days", Toast.LENGTH_LONG).show();
                edit_pond_no_of_days_et.setError("Enter the Days");
                edit_pond_no_of_days_et.requestFocus();
                b_no_days = false;
            }

            // }


           /* if(edit_pond_total_amount_tv.getText().toString().trim().length()>0)
            {
                if(edit_pond_total_amount_tv.getText().toString().equalsIgnoreCase("0"))
                {
                    Toast.makeText(getApplication(),"Enter No of days",Toast.LENGTH_LONG).show();
                    *//*edit_pond_total_amount_tv.setError("Enter the Days");
                    edit_pond_total_amount_tv.requestFocus();*//*
                    b_no_days=false;
                }
            }*/

            if (edit_amountcollected_et.getText().toString().trim().length() == 0) {
                edit_amountcollected_et.setError("Enter Amount");
                b_enteramount = false;
            }

            /*if(edit_amountcollected_et.getText().toString().trim().length()>0)
            {
                int x= Integer.parseInt(edit_amountcollected_et.getText().toString().trim());
                if(x<=0) {
                    edit_amountcollected_et.setError("Enter Valid Amount");
                    b_enteramount = false;
                }
            }*/


            if (str_remarksid.equalsIgnoreCase("100")) {
                Toast.makeText(getApplicationContext(), "Select the reason", Toast.LENGTH_SHORT).show();
                b_reason = false;
            }


            if (edit_pond_no_of_days_et.getText().toString().equalsIgnoreCase(".") ||
                    edit_pond_no_of_days_et.getText().toString().equalsIgnoreCase("0")) {
                Toast.makeText(getApplication(), "Add days", Toast.LENGTH_LONG).show();
                edit_pond_no_of_days_et.setError("Enter the Days");
                edit_pond_no_of_days_et.requestFocus();
                b_no_days = false;
            }


            if (edit_pond_no_of_days_et.getText().toString().trim().length() >= 0) {
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    startDate = myFormat.parse(edit_pond_startddate_tv.getText().toString());   // initialize start date
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    endDate = myFormat.parse(edit_pond_completeddate_tv.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long long_days = endDate.getTime() - startDate.getTime();
                long long_diffdays = TimeUnit.DAYS.convert(long_days, TimeUnit.MILLISECONDS);
                long_diffdays = long_diffdays + 1;
                Log.e("days", String.valueOf(long_diffdays));


                long long_entereddays;

                if (edit_pond_no_of_days_et.getText().toString().equalsIgnoreCase(".")
                        || edit_pond_total_amount_tv.getText().toString().equalsIgnoreCase("0")) {
                } else {


                    if (edit_pond_no_of_days_et.getText().toString().trim().length() <= 0) {
                        long_entereddays = 0;
                    } else {

                        String x = edit_pond_no_of_days_et.getText().toString().trim();
                        long_entereddays = (long) Double.parseDouble(x);
                        ;
                        //long_entereddays = Long.parseLong(edit_pond_no_of_days_et.getText().toString().trim());
                    }


                    if (long_diffdays >= long_entereddays) {
                    } else {
                        // edit_pond_no_of_days_et.setError("No of Days is more than Completed Date");
                        Toast.makeText(getApplication(), "No of Days is more than Completed Date", Toast.LENGTH_LONG).show();
                        b_no_days = false;
                    }

                }

            }


            if ((edit_pond_startddate_tv.getText().toString().trim().length() != 0) &&
                    (edit_pond_completeddate_tv.getText().toString().trim().length() != 0)) {
        /*if(date1.compareTo(date2)<0){ //0 comes when two date are same,
            //1 comes when date1 is higher then date2
            //-1 comes when date1 is lower then date2 }*/

                SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy");  //2017-06-22


                String str_ddmmyyyy_startdate = edit_pond_startddate_tv.getText().toString().trim();
                String str_ddmmyyyy_completedate = edit_pond_completeddate_tv.getText().toString().trim();


                try {
                    Date fromdate = mdyFormat.parse(str_ddmmyyyy_startdate);
                    Date todate = mdyFormat.parse(str_ddmmyyyy_completedate);

                    if (fromdate.compareTo(todate) <= 0) {
                        b_datevalidation = true;
                    } else {
                        Toast.makeText(getApplicationContext(), "Kindly enter valid date", Toast.LENGTH_SHORT).show();
                        b_datevalidation = false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }//end of try catch

            }

        }


        return (b_pond_width1 && b_pond_width2 && b_pond_height1 && b_pond_height2 && b_pond_depth1 && b_pond_depth2 && b_pondimages
              &&b_pondimages2  && b_pond_completed_date && b_no_days && b_datevalidation && b_enteramount && b_reason);
    }


//validation


    public void update_editedDetails_PondDetails_DB() {


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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR);");


        String str_completeddate, str_nodays, str_pondcost, str_mcode, str_startdate,
                str_farmpond_remarks, str_farmpond_amttaken, str_farmpondstatus;


   /* Log.e("aftereditDate",edit_pond_completeddate_tv.getText().toString());
    Log.e("aftereditdays",edit_pond_no_of_days_et.getText().toString());
    Log.e("aftereditamount",edit_pond_total_amount_tv.getText().toString());
    Log.e("aftereditmachine",str_machinecode);*/

        if (str_validation_for_completed.equalsIgnoreCase("yes")) {
            str_completeddate = edit_pond_completeddate_tv.getText().toString();
            str_nodays = edit_pond_no_of_days_et.getText().toString();

            str_startdate = edit_pond_startddate_tv.getText().toString();
            str_pondcost = edit_pond_total_amount_tv.getText().toString();
            str_mcode = str_machinecode;

            str_farmpond_remarks = str_remarksid;
            //str_farmpond_amttaken=add_newpond_amountcollected_et.getText().toString();

            str_farmpond_amttaken = edit_amountcollected_et.getText().toString();
            str_farmpondstatus = "6";

        } else {
            str_startdate = "0";
            str_completeddate = "0";
            str_nodays = "0";


            str_pondcost = "0";
            str_mcode = "0";
            str_farmpond_remarks = "4";
            str_farmpond_amttaken = "0";
            str_farmpondstatus = "3";
        }


        try {

//String selectQuery = "UPDATE Table_Name SET uploadstatus = '0' WHERE id = "+update[i]+";";

            Log.e("farmpondID", str_farmpond_id);
            ContentValues cv = new ContentValues();
    /*cv.put("FIDDB","Bob");
    cv.put("FNameDB","19");*/

            Log.e("Imageid1DB", image_id1_tv.getText().toString());
            Log.e("Imageid2DB", image_id2_tv.getText().toString());
            Log.e("Imageid3DB", image_id3_tv.getText().toString());


            if (arraylist_image1_base64.isEmpty()) {
                arraylist_image1_base64.add("noimage1");
                Log.e("editupdate","noimage1");
            }
            if (arraylist_image2_base64.isEmpty()) {
                arraylist_image2_base64.add("noimage2");
                Log.e("editupdate","noimage2");
            }
            if (arraylist_image3_base64.isEmpty()) {
                arraylist_image3_base64.add("noimage3");
                Log.e("editupdate","noimage3");
            }


        /*arraylist_image1_base64.add("noimage1");
        arraylist_image2_base64.add("noimage2");
        arraylist_image3_base64.add("noimage3");*/


            //EmployeeIDDB
            cv.put("WidthDB", edit_pondwidth_et.getText().toString());
            cv.put("HeightDB", edit_pondheight_et.getText().toString());
            cv.put("DepthDB", edit_ponddepth_et.getText().toString());


            // "FPondAcresDB VARCHAR,FPondGuntaDB VARCHAR,FPondCropBeforeDB VARCHAR,FPondCropAfterDB VARCHAR," +
            cv.put("FPondAcresDB", edit_landacres_et.getText().toString());
            cv.put("FPondGuntaDB", edit_landgunta_et.getText().toString());
            cv.put("FPondCropBeforeDB", " ");
            cv.put("FPondCropAfterDB", " ");

            cv.put("Imageid1DB", image_id1_tv.getText().toString());
            cv.put("Image1Base64DB", arraylist_image1_base64.get(0));

            cv.put("Imageid2DB", image_id2_tv.getText().toString());
            cv.put("Image2Base64DB", arraylist_image2_base64.get(0));

            cv.put("Imageid3DB", image_id3_tv.getText().toString());
            cv.put("Image3Base64DB", arraylist_image3_base64.get(0));

            cv.put("EmployeeIDDB", str_employee_id);


        /*if(str_location.equalsIgnoreCase("yes")) {
            cv.put("LatitudeDB", str_latitude);
            cv.put("LongitudeDB", str_longitude);
        }else{
            cv.put("LatitudeDB", "0.0");
            cv.put("LongitudeDB", "0.0");
        }*/

            Log.e("updateLatitude", str_latitude);
            Log.e("updateLongitude", str_longitude);

            cv.put("LatitudeDB", str_latitude);
            cv.put("LongitudeDB", str_longitude); // vijaylat

            cv.put("FPondLatitudeDB", str_latitude);
            cv.put("FPondLongitudeDB", str_longitude);

            cv.put("StartDateDB", str_startdate);
            cv.put("SubmittedDateDB", str_submitteddatetime);
            cv.put("TotalDaysDB", str_nodays);
            cv.put("ConstructedDateDB", str_completeddate);
            cv.put("PondCostDB", str_pondcost);
            cv.put("FPondAmtTakenDB", str_pondcost);

            cv.put("McodeDB", str_mcode);
            cv.put("FPondRemarksDB", str_farmpond_remarks);
            cv.put("FPondAmtTakenDB", str_farmpond_amttaken);
            cv.put("FPondStatusDB", "3");


            if (str_farmpond_id.contains("tempfarmpond")) {
                cv.put("UploadedStatus", 2);
            } else {
                cv.put("UploadedStatus", 1);
            }



            db1.update("FarmPondDetails_fromServerRest", cv, "FPondidDB = ?", new String[]{str_farmpond_id});

    /*Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM FarmPondDetails_fromServer WHERE FPondidDB='" + str_farmerpondID + "'", null);
    int x = cursor1.getCount();*/

            db1.close();


       /* Toast.makeText(getApplicationContext(), "Edition Updated Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
        startActivity(intent);
        finish();*/

            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent)
            {
                 fetch_DB_farmerprofile_offline_data();

               //fetch_DB_edited_offline_data();


            } else {
                Toast.makeText(getApplicationContext(), "Edition Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
                startActivity(intent);
                finish();
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
        }


    }


    //offlinedata verfication


    public void fetch_DB_farmerprofile_offline_data()
    {
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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatusFarmerprofile='" + 9 + "'", null);
        int x = cursor1.getCount();

        Log.e("addnew_farmercount", String.valueOf(x));


        int i = 0;
        class_farmerprofileoffline_array_obj = new Class_FarmerProfileOffline[x];
        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    Class_FarmerProfileOffline innerObj_Class_farmerprofileoffline = new Class_FarmerProfileOffline();
                    innerObj_Class_farmerprofileoffline.setStr_farmerID(cursor1.getString(cursor1.getColumnIndex("FIDDB")));

                    innerObj_Class_farmerprofileoffline.setStr_tempfarmerid(cursor1.getString(cursor1.getColumnIndex("TempFIDDB")));
                    innerObj_Class_farmerprofileoffline.setStr_fname(cursor1.getString(cursor1.getColumnIndex("FNameDB")));

                    innerObj_Class_farmerprofileoffline.setStr_mname(cursor1.getString(cursor1.getColumnIndex("FMNameDB")));
                    innerObj_Class_farmerprofileoffline.setStr_lname(cursor1.getString(cursor1.getColumnIndex("FLNameDB")));
                    innerObj_Class_farmerprofileoffline.setStr_yearid(cursor1.getString(cursor1.getColumnIndex("FYearIDDB")));
                    innerObj_Class_farmerprofileoffline.setStr_stateid(cursor1.getString(cursor1.getColumnIndex("FStateIDDB")));
                    innerObj_Class_farmerprofileoffline.setStr_districtid(cursor1.getString(cursor1.getColumnIndex("FDistrictIDDB")));
                    innerObj_Class_farmerprofileoffline.setStr_talukid(cursor1.getString(cursor1.getColumnIndex("FTalukIDDB")));
                    innerObj_Class_farmerprofileoffline.setStr_panchayatid(cursor1.getString(cursor1.getColumnIndex("FPanchayatIDDB")));

                    innerObj_Class_farmerprofileoffline.setStr_villageid(cursor1.getString(cursor1.getColumnIndex("FVillageIDDB")));
                    innerObj_Class_farmerprofileoffline.setStr_age(cursor1.getString(cursor1.getColumnIndex("FageDB")));
                    innerObj_Class_farmerprofileoffline.setStr_phonenumber(cursor1.getString(cursor1.getColumnIndex("FphonenumberDB")));
                    innerObj_Class_farmerprofileoffline.setStr_familymembers(cursor1.getString(cursor1.getColumnIndex("FfamilymemberDB")));
                    innerObj_Class_farmerprofileoffline.setStr_annualincome(cursor1.getString(cursor1.getColumnIndex("FAnnualIncomeDB")));
                    innerObj_Class_farmerprofileoffline.setStr_idprooftype(cursor1.getString(cursor1.getColumnIndex("FidprooftypeDB")));
                    innerObj_Class_farmerprofileoffline.setStr_idproofno(cursor1.getString(cursor1.getColumnIndex("FidproofnoDB")));
                    innerObj_Class_farmerprofileoffline.setStr_farmerimage(cursor1.getString(cursor1.getColumnIndex("FphotoDB")));
                    innerObj_Class_farmerprofileoffline.setStr_farmerprofile_UploadStatus(cursor1.getString(cursor1.getColumnIndex("UploadedStatusFarmerprofile")));
                    innerObj_Class_farmerprofileoffline.setStr_farmerGender(cursor1.getString(cursor1.getColumnIndex("Farmer_Gender")));

                    //Log.e("employeeid",cursor1.getString(cursor1.getColumnIndex("EmployeeIDDB")));
                    innerObj_Class_farmerprofileoffline.setStr_employeeid(cursor1.getString(cursor1.getColumnIndex("EmployeeIDDB")));

                    innerObj_Class_farmerprofileoffline.setStr_submittedDateTime(cursor1.getString(cursor1.getColumnIndex("SubmittedDateDB")));
                    class_farmerprofileoffline_array_obj[i] = innerObj_Class_farmerprofileoffline;
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }


        db1.close();

        Log.e("length", String.valueOf(class_farmerprofileoffline_array_obj.length));

        int_newfarmercount=0;
        for (int j = 0; j < class_farmerprofileoffline_array_obj.length; j++) {
            AddFarmerDetails(j);
        }
        if (x == 0) {
            fetch_DB_newfarmpond_offline_data();
        }

    }


    private void AddFarmerDetails(final int j) {
        Interface_userservice userService1;
        userService1 = Class_ApiUtils.getUserService();
        String FarmerID=class_farmerprofileoffline_array_obj[j].getStr_farmerID();
        if(FarmerID.startsWith("temp")){
            Log.e("tag","FarmerID temp=="+FarmerID);
            FarmerID="0";
        }
        AddFarmerRequest request = new AddFarmerRequest();
        request.setFarmerID(FarmerID);
        request.setStateID(class_farmerprofileoffline_array_obj[j].getStr_stateid());
        request.setDistrictID(class_farmerprofileoffline_array_obj[j].getStr_districtid());
        request.setTalukaID(class_farmerprofileoffline_array_obj[j].getStr_talukid());
        request.setPanchayatID(class_farmerprofileoffline_array_obj[j].getStr_panchayatid());
        request.setVillageID(class_farmerprofileoffline_array_obj[j].getStr_villageid());
        request.setFarmerFirstName(class_farmerprofileoffline_array_obj[j].getStr_fname());
        request.setFarmerMiddleName(class_farmerprofileoffline_array_obj[j].getStr_mname());
        request.setFarmerLastName(class_farmerprofileoffline_array_obj[j].getStr_lname());
        request.setFarmerMobile(class_farmerprofileoffline_array_obj[j].getStr_phonenumber());
        request.setFarmerIDType(class_farmerprofileoffline_array_obj[j].getStr_idprooftype());
        request.setFarmerIDNumber(class_farmerprofileoffline_array_obj[j].getStr_idproofno());
        request.setFarmerPhoto(class_farmerprofileoffline_array_obj[j].getStr_farmerimage());
        request.setFarmerAge(class_farmerprofileoffline_array_obj[j].getStr_age());
        request.setFarmerIncome(class_farmerprofileoffline_array_obj[j].getStr_annualincome());
        request.setFarmerFamily(class_farmerprofileoffline_array_obj[j].getStr_familymembers());
        request.setSubmittedDate(class_farmerprofileoffline_array_obj[j].getStr_submittedDateTime());
        request.setMobileTempID(class_farmerprofileoffline_array_obj[j].getStr_tempfarmerid());
        request.setCreatedBy(str_employee_id);
        request.setFarmer_Gender(class_farmerprofileoffline_array_obj[j].getStr_farmerGender());

        Log.e("tag", "FarmerFirstName==" + class_farmerprofileoffline_array_obj[j].getStr_fname());
        Log.e("tag", "FarmerID==" + class_farmerprofileoffline_array_obj[j].getStr_farmerID());


        Call<AddFarmerResponse> call = userService1.AddFarmer(request);

        Log.e("TAG", "Request 33: " + new Gson().toJson(request));
        Log.e("TAG", "Request: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(EditFarmPondDetails_Activity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<AddFarmerResponse>() {
            @Override
            public void onResponse(Call<AddFarmerResponse> call, Response<AddFarmerResponse> response) {
                System.out.println("response" + response.body().toString());

                  /*  Log.e("response",response.toString());
                    Log.e("TAG", "response 33: "+new Gson().toJson(response) );
                    Log.e("response body", String.valueOf(response.body()));*/
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
                   /* Log.e("response new:",error1.getMsg());
                    Log.e("response new status:", String.valueOf(error1.getstatus()));*/
                // Log.e("response",Gson.fromJson(response.toString(),AddFarmer_Activity1.class));

                if (response.isSuccessful()) {
                    AddFarmerResList addFarmerResList = response.body().getLst();
                    Log.e("tag", "addFarmerResList NAme=" + addFarmerResList.getFarmerFirstName());

                    String str_response_farmer_id = addFarmerResList.getFarmerID();
                    String str_response_farmercode = addFarmerResList.getFarmerCode();
                    String str_response_tempId = addFarmerResList.getMobileTempID();
                    Log.e("tag", "getMobileTempID=" + addFarmerResList.getMobileTempID());
                    Log.e("tag", "getFarmerCode=" + addFarmerResList.getFarmerCode());
                    Log.e("tag", "getFarmerID=" + addFarmerResList.getFarmerID());

                    SQLiteDatabase db1 = getApplication().openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

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
                            "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR);");


                    ContentValues cv = new ContentValues();
                    cv.put("FIDDB", str_response_farmer_id);
                /*cv.put("employee_id",str_response_image_id3);
                // employee_id*/
                    cv.put("UploadedStatusFarmerprofile", 10);

                    db1.update("FarmPondDetails_fromServerRest", cv, "TempFIDDB = ?", new String[]{str_response_tempId});
                    db1.close();

                    SQLiteDatabase db_viewfarmerlist = getApplication().openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);


                    db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                            "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                            "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                            "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                            "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                            "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                            "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");


                    ContentValues cv_farmelistupdate = new ContentValues();
                    cv_farmelistupdate.put("DispFarmerTable_Farmer_Code", str_response_farmercode);
                    cv_farmelistupdate.put("DispFarmerTable_FarmerID", str_response_farmer_id);


                    db_viewfarmerlist.update("ViewFarmerListRest", cv_farmelistupdate, "DispFarmerTable_Farmer_Code = ?", new String[]{str_response_tempId});
                    db_viewfarmerlist.close();
                    progressDoalog.dismiss();

                    if(int_newfarmercount>0){int_newfarmercount++;}
                    else{ int_newfarmercount++; }

                    if(int_newfarmercount==class_farmerprofileoffline_array_obj.length)
                    {
                        fetch_DB_newfarmpond_offline_data();
                    }


                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(EditFarmPondDetails_Activity.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(EditFarmPondDetails_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }


    public void fetch_DB_newfarmpond_offline_data() {


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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatus='" + 2 + "'", null);
        int x = cursor1.getCount();


        Log.e("newuploadstatus_count", String.valueOf(x));

        int i = 0;

        newfarmponddetails_offline_array_objRest = new Class_farmponddetails[x];
        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    Class_farmponddetails innerObj_Class_farmponddetails = new Class_farmponddetails();
                    innerObj_Class_farmponddetails.setFarmerID(cursor1.getString(cursor1.getColumnIndex("FIDDB")));
                    innerObj_Class_farmponddetails.setFarmerFirstName(cursor1.getString(cursor1.getColumnIndex("FNameDB")));
                    innerObj_Class_farmponddetails.setPondID(cursor1.getString(cursor1.getColumnIndex("FPondidDB")));
                    innerObj_Class_farmponddetails.setPondWidth(cursor1.getString(cursor1.getColumnIndex("WidthDB")));
                    //   innerObj_Class_farmponddetails.setPondDepth(cursor1.getString(cursor1.getColumnIndex("HeightDB")));
                    innerObj_Class_farmponddetails.setPondDepth(cursor1.getString(cursor1.getColumnIndex("DepthDB")));
                    innerObj_Class_farmponddetails.setAcademicID(cursor1.getString(cursor1.getColumnIndex("FYearIDDB")));
                    innerObj_Class_farmponddetails.setPondLandAcre(cursor1.getString(cursor1.getColumnIndex("FPondAcresDB")));
                    innerObj_Class_farmponddetails.setPondLandGunta(cursor1.getString(cursor1.getColumnIndex("FPondGuntaDB")));
                    innerObj_Class_farmponddetails.setPondLatitude(cursor1.getString(cursor1.getColumnIndex("LatitudeDB")));
                    innerObj_Class_farmponddetails.setPondLongitude(cursor1.getString(cursor1.getColumnIndex("LongitudeDB")));
                    // innerObj_Class_farmponddetails.setImage1_ID(cursor1.getString(cursor1.getColumnIndex("Imageid1DB")));

                    if (cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")).equalsIgnoreCase("0") ||
                            cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")).equalsIgnoreCase("noimage1")) {
                        innerObj_Class_farmponddetails.setPondImage1("");
                    } else {
                        innerObj_Class_farmponddetails.setPondImage1(cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")));
                    }

                    innerObj_Class_farmponddetails.setPondImage2(cursor1.getString(cursor1.getColumnIndex("Imageid2DB")));
                    if (cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")).equalsIgnoreCase("0") ||
                            cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")).equalsIgnoreCase("noimage2")) {
                        innerObj_Class_farmponddetails.setPondImage2("");
                    } else {

                        innerObj_Class_farmponddetails.setPondImage2(cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")));
                    }

                    innerObj_Class_farmponddetails.setPondImage3(cursor1.getString(cursor1.getColumnIndex("Imageid3DB")));
                    if (cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")).equalsIgnoreCase("0") ||
                            cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")).equalsIgnoreCase("noimage3")) {
                        innerObj_Class_farmponddetails.setPondImage3("");
                    } else {

                        innerObj_Class_farmponddetails.setPondImage3(cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")));
                    }

                    innerObj_Class_farmponddetails.setCreatedBy(cursor1.getString(cursor1.getColumnIndex("EmployeeIDDB")));

                    Log.e("employeeid", cursor1.getString(cursor1.getColumnIndex("EmployeeIDDB")));


                    Log.e("submitteddate", cursor1.getString(cursor1.getColumnIndex("SubmittedDateDB")));

                    innerObj_Class_farmponddetails.setSubmittedDate(cursor1.getString(cursor1.getColumnIndex("SubmittedDateDB")));


                    innerObj_Class_farmponddetails.setPondEnd(cursor1.getString(cursor1.getColumnIndex("ConstructedDateDB")));
                    innerObj_Class_farmponddetails.setPondDays(cursor1.getString(cursor1.getColumnIndex("TotalDaysDB")));
                    innerObj_Class_farmponddetails.setPondCost(cursor1.getString(cursor1.getColumnIndex("PondCostDB")));
                    innerObj_Class_farmponddetails.setMachineID(cursor1.getString(cursor1.getColumnIndex("McodeDB")));

                    innerObj_Class_farmponddetails.setPondStatus(cursor1.getString(cursor1.getColumnIndex("FPondStatusDB")));

                    innerObj_Class_farmponddetails.setPondStart(cursor1.getString(cursor1.getColumnIndex("StartDateDB")));

                    innerObj_Class_farmponddetails.setApprovalRemarks(cursor1.getString(cursor1.getColumnIndex("FPondRemarksDB")));
                    innerObj_Class_farmponddetails.setPondCollectedAmount(cursor1.getString(cursor1.getColumnIndex("FPondAmtTakenDB")));
                    //"FPondRemarksDB VARCHAR,FPondAmtTakenDB VARCHAR,FPondStatusDB VARCHAR," +

                    innerObj_Class_farmponddetails.setPondTempID(cursor1.getString(cursor1.getColumnIndex("TempFIDDB")));

                    innerObj_Class_farmponddetails.setPondCode(cursor1.getString(cursor1.getColumnIndex("FPondCodeDB")));

                    Log.e("class_farmpondcode", cursor1.getString(cursor1.getColumnIndex("FPondCodeDB")));
                    Log.e("class_farmpondID", cursor1.getString(cursor1.getColumnIndex("FPondidDB")));

                    newfarmponddetails_offline_array_objRest[i] = innerObj_Class_farmponddetails;
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }


        db1.close();

        Log.e("newlength", String.valueOf(newfarmponddetails_offline_array_objRest.length));

        newpond_response= new Class_addfarmponddetails_ToFromServer1[newfarmponddetails_offline_array_objRest.length];
        int_j=0;
        for (int k = 0; k < newfarmponddetails_offline_array_objRest.length; k++)
        {


            if (x > 0)
            {
                //  AsyncTask_submit_New_farmponddetails(k);
                Add_New_farmponddetails(k);
            }
        }
        if(x==0)
        {
            fetch_DB_edited_offline_data();
        }

    }




    private void Add_New_farmponddetails(int k)
    {


        final ProgressDialog newpondadded_progressDoalog;
        newpondadded_progressDoalog = new ProgressDialog(EditFarmPondDetails_Activity.this);
        newpondadded_progressDoalog.setMessage("Uploading Farmponds....");
        newpondadded_progressDoalog.setTitle("Please wait....");
        newpondadded_progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        newpondadded_progressDoalog.show();


        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();


        Class_addfarmponddetails_ToFromServer2 request = new Class_addfarmponddetails_ToFromServer2();

        String str_latitude, str_longitude;

        if (newfarmponddetails_offline_array_objRest[k].getPondImage3().equals("noimage3") ||
                newfarmponddetails_offline_array_objRest[k].getPondImage3().equals("0")) {
            str_latitude = "";
            str_longitude = "";
        } else {

            str_latitude = newfarmponddetails_offline_array_objRest[k].getPondLatitude();
            str_longitude = newfarmponddetails_offline_array_objRest[k].getPondLongitude();

        }

        Log.e("latitude", str_latitude);
        Log.e("Longitude", str_longitude);


        request.setPond_ID("0");// for new pond "0" need to be sent
        request.setFarmer_ID(newfarmponddetails_offline_array_objRest[k].getFarmerID());
        //request.setAcademic_ID(newfarmponddetails_offline_array_objRest[k].getAcademicID());
        request.setAcademic_ID(str_year);
        request.setMachine_ID(newfarmponddetails_offline_array_objRest[k].getMachineID());
        request.setPond_Latitude(str_latitude);
        request.setPond_Longitude(str_longitude);

        request.setPond_Length(newfarmponddetails_offline_array_objRest[k].getPondLength());
        request.setPond_Width(newfarmponddetails_offline_array_objRest[k].getPondWidth());
        request.setPond_Depth(newfarmponddetails_offline_array_objRest[k].getPondDepth());

        request.setPond_Start(newfarmponddetails_offline_array_objRest[k].getPondStart());
        request.setPond_End(newfarmponddetails_offline_array_objRest[k].getPondEnd());
        request.setPond_Days(newfarmponddetails_offline_array_objRest[k].getPondDays());
        request.setPond_Cost(newfarmponddetails_offline_array_objRest[k].getPondCost());
        request.setPond_Collected_Amount(newfarmponddetails_offline_array_objRest[k].getPondCollectedAmount());
        request.setPond_Status(newfarmponddetails_offline_array_objRest[k].getPondStatus());
        request.setPond_remarks(newfarmponddetails_offline_array_objRest[k].getPond_remarks());

       /* request.setPond_Image_1(newfarmponddetails_offline_array_objRest[k].getPondImage1());
        request.setPond_Image_2(newfarmponddetails_offline_array_objRest[k].getPondImage2());
        request.setPond_Image_3(newfarmponddetails_offline_array_objRest[k].getPondImage3());*/



        int x=0;
       /* if(str_image1present.equalsIgnoreCase("yes"))
        { x++;
            if(str_image2present.equalsIgnoreCase("yes"))
            {  x++; if(str_image3present.equalsIgnoreCase("yes"))
            {x++;}
            }
        }*/



        if(newfarmponddetails_offline_array_objRest[k].getPondImage1().equalsIgnoreCase("noimage1")
        ||newfarmponddetails_offline_array_objRest[k].getPondImage1().equalsIgnoreCase("0"))
        {}
        else{
            x++;
            if(newfarmponddetails_offline_array_objRest[k].getPondImage2().equalsIgnoreCase("noimage2")
                    ||newfarmponddetails_offline_array_objRest[k].getPondImage2().equalsIgnoreCase("0"))
            { }
            else{
                x++;
                if(newfarmponddetails_offline_array_objRest[k].getPondImage3().equalsIgnoreCase("noimage3")
                        ||newfarmponddetails_offline_array_objRest[k].getPondImage3().equalsIgnoreCase("0"))
                { }
                else{
                    x++;}
                 }
        }

        Log.e("pondlength", String.valueOf(x));

        PondImage[] pondimage_arrayobj = new PondImage[x];

        for(int j=0;j<x;j++)
        {
            PondImage pondimage_innerobj = new PondImage();
            pondimage_innerobj.setImageID("0");
            pondimage_innerobj.setPondID("0");
            pondimage_innerobj.setImageData1("");
            if(j==0)
            {pondimage_innerobj.setImageData(newfarmponddetails_offline_array_objRest[k].getPondImage1());
                pondimage_innerobj.setImageType("1");
            }
            if(j==1)
            {pondimage_innerobj.setImageData(newfarmponddetails_offline_array_objRest[k].getPondImage2());
                pondimage_innerobj.setImageType("2");}
            if(j==2)
            {pondimage_innerobj.setImageData(newfarmponddetails_offline_array_objRest[k].getPondImage3());
                pondimage_innerobj.setImageType("3");}
            pondimage_innerobj.setImageLink("");

            pondimage_innerobj.setImageStatus("0");
            pondimage_arrayobj[j]=pondimage_innerobj;
        }

        List list = Arrays.asList(pondimage_arrayobj);
        request.setPondImage(list);


        request.setSubmitted_Date(newfarmponddetails_offline_array_objRest[k].getSubmittedDate());
        request.setCreated_By(newfarmponddetails_offline_array_objRest[k].getCreatedBy());
        request.setPond_Temp_ID(newfarmponddetails_offline_array_objRest[k].getPondCode());
        request.setPond_Land_Gunta(newfarmponddetails_offline_array_objRest[k].getPondLandGunta());
        request.setPond_Land_Acre(newfarmponddetails_offline_array_objRest[k].getPondLandAcre());

        int_k=k;
        Log.e("kvalue", String.valueOf(k));
        Log.e("addrequest",request.toString());

        {
            retrofit2.Call call = userService.Post_ActionFarmerPondData(request);


           // Log.e("addrequest", new Gson().toJson(request));

            call.enqueue(new Callback<Class_addfarmponddetails_ToFromServer1>()
            {
                @Override
                public void onResponse(retrofit2.Call<Class_addfarmponddetails_ToFromServer1> call, Response<Class_addfarmponddetails_ToFromServer1> response) {


                    Log.e("response", response.toString());
                    //  Log.e("TAG", "response 33: " + new Gson().toJson(response));
                    Log.e("response_body", String.valueOf(response.body()));

                    if (response.isSuccessful())
                    {
                        //  progressDoalog.dismiss();


                        Class_addfarmponddetails_ToFromServer1 class_addfarmponddetailsresponse = response.body();

                        if (class_addfarmponddetailsresponse.getStatus().equalsIgnoreCase("true"))
                        {

                            if(int_j>0){}
                            else{ int_j=0; }
                            try {
                                newpond_response[int_j] = class_addfarmponddetailsresponse;
                                int_j++;
                            }
                            catch(Exception e)
                            {
                                Log.e("excep",e.toString());
                            }

                            Log.e("addpondresponse", class_addfarmponddetailsresponse.getStatus().toString());
                            Log.e("responsecost", class_addfarmponddetailsresponse.getLst2().get(0).getPond_Cost());
                            Log.e("responsepondID", class_addfarmponddetailsresponse.getLst2().get(0).getPondImage().get(0).getImageID());
                            Log.e("respTemppondID", class_addfarmponddetailsresponse.getLst2().get(0).getPond_Temp_ID());

                            //DB_update_newpond_response();


                            /*Gson gson = new Gson();
                            String jsonStr = gson.toJson(class_addfarmponddetailsresponse);
                            Log.e("Jsonstr",jsonStr.toString());*/

                            newpondadded_progressDoalog.dismiss();

                            if(int_j==newfarmponddetails_offline_array_objRest.length)
                            {
                                DB_update_newpond_response(int_j);
                            }





                        } else if (class_addfarmponddetailsresponse.getStatus().equals("false")) {
                            newpondadded_progressDoalog.dismiss();
                            Toast.makeText(EditFarmPondDetails_Activity.this, class_addfarmponddetailsresponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        newpondadded_progressDoalog.dismiss();
                        DefaultResponse error = ErrorUtils.parseError(response);
                        Log.e("addponderror", error.getMsg());

                        Toast.makeText(EditFarmPondDetails_Activity.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(EditFarmPondDetails_Activity.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("response_error", t.getMessage().toString());
                }
            });

        }

    }




    public void DB_update_newpond_response(int numberofresponse)
    {
        try {
            Log.e("lengthofobject", String.valueOf(numberofresponse));
            // Log.e("length", String.valueOf(newpond_response.length));
            Log.e("newpondcost", newpond_response[0].getLst2().get(0).getPond_Cost());
            Log.e("newpondImageID", newpond_response[0].getLst2().get(0).getPondImage().get(0).getImageID());
            Log.e("newpondTempID", newpond_response[0].getLst2().get(0).getPond_Temp_ID());

            int tg=newpond_response[0].getLst2().get(0).getPondImage().size();
            Log.e("pondsize",String.valueOf(tg));

        }
        catch(Exception e)
        { Log.e("DB",e.toString());  }

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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR);");


        for(int i=0;i<numberofresponse;i++)
        {

           /* Log.e("newpondcost", newpond_response[0].getLst2().get(0).getPond_Cost());
            Log.e("newpondImageID", newpond_response[0].getLst2().get(0).getPondImage().get(0).getImageID());
            Log.e("newpondTempID", newpond_response[0].getLst2().get(0).getPond_Temp_ID());
*/
            String TemppondID_response=newpond_response[i].getLst2().get(0).getPond_Temp_ID();

            ContentValues cv = new ContentValues();
            cv.put("FPondidDB",newpond_response[i].getLst2().get(0).getPond_ID());

            int int_pondimagesize=newpond_response[i].getLst2().get(0).getPondImage().size();

            Log.e("newpondTempID", newpond_response[i].getLst2().get(0).getPond_Temp_ID());
            Log.e("FPondidDB", newpond_response[i].getLst2().get(0).getPond_ID());
            Log.e("newpondsize", String.valueOf(int_pondimagesize));



            for(int k=0;k<int_pondimagesize;k++)
            {
                if(k==0)
                {
                    cv.put("Imageid1DB",newpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                    Log.e("Imageid1DB", newpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                    cv.put("Imageid2DB","0");
                    cv.put("Imageid3DB","0");
                }else{
                    if(k==1)
                    {
                        cv.put("Imageid2DB",newpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                        Log.e("Imageid2DB", newpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                        cv.put("Imageid3DB","0");
                    }else{
                        if(k==2)
                        {
                            cv.put("Imageid3DB",newpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                            Log.e("Imageid3DB", newpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                        }
                    }
                }

            }

            // Log.e("farmpondcode",newpond_response[i].getLst2().get(0).getPondCode());

            cv.put("FPondCodeDB",newpond_response[i].getLst2().get(0).getPond_ID());
            //  cv.put("FPondStatusDB","3");

            cv.put("UploadedStatus",3);

            db1.update("FarmPondDetails_fromServerRest", cv, "FPondidDB = ?", new String[]{TemppondID_response});
        }
        db1.close();

        fetch_DB_edited_offline_data();
    }











    public void fetch_DB_edited_offline_data()
    {

        Class_DBHandler dbhandler_obj=new Class_DBHandler(getApplicationContext());

        if(dbhandler_obj.get_DB_edited_offline_data_count()>0)
        {
            int x=dbhandler_obj.get_DB_edited_offline_data_count();
           class_farmponddetails_offline_array_obj = new Class_farmponddetails_offline[x];


            class_farmponddetails_offline_array_obj= dbhandler_obj.get_DB_edited_offline_data();

            editedpond_response= new Class_addfarmponddetails_ToFromServer1[class_farmponddetails_offline_array_obj.length];
            int_er=0;

            for (int k = 0; k < class_farmponddetails_offline_array_obj.length; k++)
            {

                AsyncTask_submit_edited_farmponddetails(k);
            }
        }
    }


    private void AsyncTask_submit_edited_farmponddetails(int k)
    {

        final ProgressDialog pondsedited_progressDoalog;
        pondsedited_progressDoalog = new ProgressDialog(EditFarmPondDetails_Activity.this);
        pondsedited_progressDoalog.setMessage("Uploading edited Farmponds....");
        pondsedited_progressDoalog.setTitle("Please wait....");
        pondsedited_progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pondsedited_progressDoalog.show();


        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Class_addfarmponddetails_ToFromServer2 request = new Class_addfarmponddetails_ToFromServer2();

        try {



            String str_latitude, str_longitude;


            Log.e("pondID", class_farmponddetails_offline_array_obj[k].getFarmpond_Id());
            Log.e("farmerID", class_farmponddetails_offline_array_obj[k].getfarmer_id());

            if (class_farmponddetails_offline_array_obj[k].getImage3_Base64().equals("noimage3") ||
                    class_farmponddetails_offline_array_obj[k].getImage3_Base64().equals("0")) {
                str_latitude = "";
                str_longitude = "";
            } else {

                str_latitude = class_farmponddetails_offline_array_obj[k].getLatitude();
                str_longitude = class_farmponddetails_offline_array_obj[k].getLongitude();

            }

            Log.e("latitude", str_latitude);
            Log.e("Longitude", str_longitude);


            request.setPond_ID(class_farmponddetails_offline_array_obj[k].getFarmpond_Id());
            request.setFarmer_ID(class_farmponddetails_offline_array_obj[k].getfarmer_id());
            //request.setAcademic_ID(newfarmponddetails_offline_array_objRest[k].getAcademicID());
            request.setAcademic_ID(str_year);
            request.setMachine_ID(class_farmponddetails_offline_array_obj[k].getMachineCode());
            request.setPond_Latitude(str_latitude);
            request.setPond_Longitude(str_longitude);

            request.setPond_Length(class_farmponddetails_offline_array_obj[k].getFarmpond_Height());
            request.setPond_Width(class_farmponddetails_offline_array_obj[k].getFarmpond_Width());
            request.setPond_Depth(class_farmponddetails_offline_array_obj[k].getFarmpond_Depth());

            request.setPond_Start(class_farmponddetails_offline_array_obj[k].getStartDate());
            request.setPond_End(class_farmponddetails_offline_array_obj[k].getConstructedDate());
            request.setPond_Days(class_farmponddetails_offline_array_obj[k].getTotal_no_days());
            request.setPond_Cost(class_farmponddetails_offline_array_obj[k].getPondCost());
            request.setPond_Collected_Amount(class_farmponddetails_offline_array_obj[k].getFarmpond_amttaken());

       /* request.setPond_Image_1(newfarmponddetails_offline_array_objRest[k].getPondImage1());
        request.setPond_Image_2(newfarmponddetails_offline_array_objRest[k].getPondImage2());
        request.setPond_Image_3(newfarmponddetails_offline_array_objRest[k].getPondImage3());*/


            int x = 0;
            if (str_image1present.equalsIgnoreCase("yes")) {
                x++;
                if (str_image2present.equalsIgnoreCase("yes")) {
                    x++;
                    if (str_image3present.equalsIgnoreCase("yes")) {
                        x++;
                    }
                }
            }

            Log.e("pondlength", String.valueOf(x));

            PondImage[] pondimage_arrayobj = new PondImage[x];

            for (int j = 0; j < x; j++) {
                PondImage pondimage_innerobj = new PondImage();
                pondimage_innerobj.setImageID("0");
                pondimage_innerobj.setPondID("0");
                pondimage_innerobj.setImageData1("");
                if (j == 0) {
                    pondimage_innerobj.setImageData(class_farmponddetails_offline_array_obj[k].getImage1_Base64());
                    if (str_image1present.equalsIgnoreCase("yes")) {
                        pondimage_innerobj.setImageType("1");
                    } else {
                        if (str_image2present.equalsIgnoreCase("yes")) {
                            pondimage_innerobj.setImageType("2");
                        } else {
                            if (str_image3present.equalsIgnoreCase("yes")) {
                                pondimage_innerobj.setImageType("3");
                            }
                        }
                    }


                }
                if (j == 1) {
                    pondimage_innerobj.setImageData(class_farmponddetails_offline_array_obj[k].getImage2_Base64());
                    if (str_image2present.equalsIgnoreCase("yes")) {
                        pondimage_innerobj.setImageType("2");
                    } else {
                        if (str_image3present.equalsIgnoreCase("yes")) {
                            pondimage_innerobj.setImageType("3");
                        }
                    }
                }


                if (j == 2) {
                    pondimage_innerobj.setImageData(class_farmponddetails_offline_array_obj[k].getImage3_Base64());
                    pondimage_innerobj.setImageType("3");
                }

                pondimage_innerobj.setImageLink("");
                pondimage_innerobj.setImageStatus("0");
                pondimage_arrayobj[j] = pondimage_innerobj;
            }

            List list = Arrays.asList(pondimage_arrayobj);
            request.setPondImage(list);

            request.setSubmitted_Date(class_farmponddetails_offline_array_obj[k].getSubmittedDateTime());
            request.setCreated_By(class_farmponddetails_offline_array_obj[k].getEmployeeID());
            request.setPond_Temp_ID(class_farmponddetails_offline_array_obj[k].getFarmpond_Id());
            request.setPond_Land_Gunta(class_farmponddetails_offline_array_obj[k].getFarmpond_gunta());
            request.setPond_Land_Acre(class_farmponddetails_offline_array_obj[k].getFarmpond_acres());
            request.setPond_remarks(class_farmponddetails_offline_array_obj[k].getFarmpond_remarks());

            int_k = k;
            Log.e("kvalue", String.valueOf(k));
            Log.e("Editrequest", request.toString());

        }
        catch(Exception e){

            Log.e("editexception",e.toString());

    }

        {
            retrofit2.Call call = userService.Post_ActionFarmerPondData(request);

            Log.e("editrequest", request.toString());

            call.enqueue(new Callback<Class_addfarmponddetails_ToFromServer1>()
            {
                @Override
                public void onResponse(retrofit2.Call<Class_addfarmponddetails_ToFromServer1> call, Response<Class_addfarmponddetails_ToFromServer1> response) {


                    Log.e("response", response.toString());
                    //  Log.e("TAG", "response 33: " + new Gson().toJson(response));
                    Log.e("response_body", String.valueOf(response.body()));

                    if (response.isSuccessful())
                    {
                        pondsedited_progressDoalog.dismiss();


                        Class_addfarmponddetails_ToFromServer1 editfarmpondresponse_obj = response.body();
                        Log.e("Editpondresp", editfarmpondresponse_obj.getStatus());


                        if (editfarmpondresponse_obj.getStatus().equalsIgnoreCase("true"))
                        {

                            if(int_er>0){}
                            else{ int_er=0; }
                            try {
                                editedpond_response[int_er] = editfarmpondresponse_obj;
                                int_er++;
                            }
                            catch(Exception e)
                            {
                                Log.e("Editrespexcep",e.toString());
                            }

                            /*Gson gson = new Gson();
                            String jsonStr = gson.toJson(class_addfarmponddetailsresponse);
                            Log.e("Jsonstr",jsonStr.toString());*/

                            if(int_er==class_farmponddetails_offline_array_obj.length)
                            {
                                DB_update_editedpond_response(int_er);
                            }

                        } else if (editfarmpondresponse_obj.getStatus().equals("false")) {
                                 pondsedited_progressDoalog.dismiss();
                            Toast.makeText(EditFarmPondDetails_Activity.this, editfarmpondresponse_obj.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    } else {
                          pondsedited_progressDoalog.dismiss();
                        DefaultResponse error = ErrorUtils.parseError(response);
                        Log.e("Editpondresp", error.getMsg());
                        Toast.makeText(EditFarmPondDetails_Activity.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(EditFarmPondDetails_Activity.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Editresp_error", t.getMessage().toString());
                }
            });

        }

    }




    public void DB_update_editedpond_response(int numberofresponse)
    {
        try {
            Log.e("lengthofobject", String.valueOf(numberofresponse));
            // Log.e("length", String.valueOf(newpond_response.length));
            Log.e("newpondcost", editedpond_response[0].getLst2().get(0).getPond_Cost());
            Log.e("newpondImageID", editedpond_response[0].getLst2().get(0).getPondImage().get(0).getImageID());
            Log.e("newpondTempID", editedpond_response[0].getLst2().get(0).getPond_Temp_ID());

            int tg=editedpond_response[0].getLst2().get(0).getPondImage().size();
            Log.e("pondsize",String.valueOf(tg));

        }
        catch(Exception e)
        { Log.e("DB",e.toString());  }

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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR);");


        for(int i=0;i<numberofresponse;i++)
        {

           /* Log.e("newpondcost", newpond_response[0].getLst2().get(0).getPond_Cost());
            Log.e("newpondImageID", newpond_response[0].getLst2().get(0).getPondImage().get(0).getImageID());
            Log.e("newpondTempID", newpond_response[0].getLst2().get(0).getPond_Temp_ID());
*/
            String pondID_response=editedpond_response[i].getLst2().get(0).getPond_ID();




            ContentValues cv = new ContentValues();
            cv.put("FPondidDB",editedpond_response[i].getLst2().get(0).getPond_ID());

            int int_pondimagesize=editedpond_response[i].getLst2().get(0).getPondImage().size();

            Log.e("newpondTempID", editedpond_response[i].getLst2().get(0).getPond_Temp_ID());
            Log.e("FPondidDB", editedpond_response[i].getLst2().get(0).getPond_ID());
            Log.e("newpondsize", String.valueOf(int_pondimagesize));



            for(int k=0;k<int_pondimagesize;k++)
            {
                if(k==0)
                {
                    cv.put("Imageid1DB",editedpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                    Log.e("Imageid1DB", editedpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                    cv.put("Imageid2DB","0");
                    cv.put("Imageid3DB","0");
                }else{
                    if(k==1)
                    {
                        cv.put("Imageid2DB",editedpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                        Log.e("Imageid2DB", editedpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                        cv.put("Imageid3DB","0");
                    }else{
                        if(k==2)
                        {
                            cv.put("Imageid3DB",editedpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                            Log.e("Imageid3DB", editedpond_response[i].getLst2().get(0).getPondImage().get(k).getImageID());
                        }
                    }
                }

            }


            cv.put("FPondCodeDB",editedpond_response[i].getLst2().get(0).getPond_ID());
            cv.put("FPondStatusDB","3");

            cv.put("UploadedStatus",0); //0 means uploaded 1 means not uploaded

            db1.update("FarmPondDetails_fromServerRest", cv, "FPondidDB = ?", new String[]{pondID_response});


        }

        db1.close();


        Toast.makeText(getApplicationContext(), "Updated FarmPond changes", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
        startActivity(intent);
        finish();


    }










        //offlinedata verfication




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
                 Log.e("Cangetlocation", String.valueOf(canGetLocation));
                Log.e("isGPSON", String.valueOf(isGPSON));
                if (isGPSON)
                {
                    // from GPS
                    //Log.d(TAG, "GPS on");

                    dialog_location = new ProgressDialog(EditFarmPondDetails_Activity.this);
                    dialog_location.setMessage("Please wait location fetching...");
                    dialog_location.setCanceledOnTouchOutside(false);
                    dialog_location.show();

                    appLocationService = new AppLocationService( EditFarmPondDetails_Activity.this);
                    android.location.Location nwLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);


                    if (nwLocation != null)
                    {
                        Double latitude = nwLocation.getLatitude();
                        Double longitude = nwLocation.getLongitude();
                        str_latitude=Double.toString(latitude);
                        str_longitude=Double.toString(longitude);
                        Log.e(TAG,"latitude"+str_latitude);
                        Log.e(TAG,"longitude"+str_longitude);
                        Toast.makeText(EditFarmPondDetails_Activity.this," after camera latitude="+str_latitude+" longitude="+str_longitude,Toast.LENGTH_LONG).show();
                        latitude_tv.setText(str_latitude);
                        longitude_tv.setText(str_longitude);

                        try {
                            Thread.sleep(1 * 500);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }

                        dialog_location.dismiss();

                        selectImage();
                    }
                    else{
                        dialog_location.dismiss();
                        Toast.makeText(EditFarmPondDetails_Activity.this," else after camera latitude="+str_latitude+" longitude="+str_longitude,Toast.LENGTH_LONG).show();

                    }

                  /*  for(int i=0;i<=50;i++)
                    {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                        if(locationManager!=null)
                        {}else{i--;}

                    }
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

                            Log.e("camEditlat",String.valueOf(loc.getLatitude()));
                            Log.e("camEditlong",String.valueOf(loc.getLongitude()));

                            str_latitude=String.valueOf(loc.getLatitude());
                            str_longitude=String.valueOf(loc.getLongitude());
                            latitude_tv.setText(str_latitude);
                            longitude_tv.setText(str_longitude);

                            dialog_location.dismiss();

                            selectImage();

                        }
                    }
                    else{
                        dialog_location.dismiss();
                    }*/
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





    //location


    public void select_thirdimage()
    {
        {
            gpstracker_obj3 = new Class_GPSTracker(EditFarmPondDetails_Activity.this);
            if (gpstracker_obj3.canGetLocation())
            {
                double_currentlatitude = gpstracker_obj3.getLatitude();
                double_currentlongitude = gpstracker_obj3.getLongitude();

                if (str_currentlatitude.equals("0.0") || str_currentlongitude.equals("0.0")) {
                    alertdialog_refresh_latandlong();
                } else {
                    selectImage();
                }
            } else {
                gpstracker_obj3.showSettingsAlert();
            }
        }
    }









    private void getLocation_oldphp()
    {
        if (ActivityCompat.checkSelfPermission(EditFarmPondDetails_Activity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(EditFarmPondDetails_Activity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditFarmPondDetails_Activity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.LOCATION_REQUEST);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(EditFarmPondDetails_Activity.this, new OnSuccessListener<Location>() {
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
                            if (ActivityCompat.checkSelfPermission(EditFarmPondDetails_Activity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditFarmPondDetails_Activity.this,
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
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














    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(EditFarmPondDetails_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Are you sure want to go back");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(EditFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
           /* Intent i = new Intent(EditFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
            startActivity(i);
             finish();*/
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}// end of class

