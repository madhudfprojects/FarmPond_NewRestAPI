package df.farmpondstwo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentValues;
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
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import df.farmpondstwo.Models.Class_MachineDetails;
import df.farmpondstwo.remote.Class_ApiUtils;
import df.farmpondstwo.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AddFarmPondActivity extends AppCompatActivity
{

/*    Class_GPSTracker gpstracker_obj1,gpstracker_obj2;
    Double double_currentlatitude=0.0;
    Double double_currentlongitude=0.0;*/
    String str_latitude,str_longitude;


    TextView latitude_tv,longitude_tv;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    Toolbar toolbar;

    double latitude,longitude;
    String lat_str,log_str;
    AppLocationService appLocationService;

    ImageView add_newfarmpond_iv;

    EditText add_newpond_width_et,add_newpond_height_et,add_newpond_depth_et,add_landacres_et,add_landgunta_et;
    TextView add_newpond_farmername_et;
    ImageView add_newpond_image3_iv;
    public static ImageView add_newpond_image1_iv,add_newpond_image2_iv;
    ImageButton removeimage1_ib,removeimage2_ib,removeimage3_ib;
    Button add_ponddetails_submit_bt,add_ponddetails_cancel_bt;
    TextView submittedby_tv;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String str_base64imagestring = "";
    ArrayList<String> arraylist_image1_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image2_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image3_base64 = new ArrayList<>();

    String str_image1,str_image2,str_image3,str_cancelclicked;

    String str_image1present,str_image2present,str_image3present;

    // static Class_alert_msg obj_class_alert_msg;

    LinearLayout cancel_submit_addnew_ll;

    SharedPreferences sharedpref_farmerid_Obj;
    String str_farmerID,str_farmername;
    public static final String sharedpreferenc_farmerid = "sharedpreference_farmer_id";
    public static final String Key_FarmerID = "farmer_id";
    public static final String Key_FarmerName = "farmer_name";



    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";
    public static final String KeyValue_perdayamount = "KeyValue_perdayamount";


    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;

    String str_employee_id,str_submitter_mailid,str_perday_amount;
    String str_currentDateandTime,str_submitteddatetime;
    CheckBox new_farmpond_completed_cb;


    static TextView  add_newpond_startdate_tv,add_newpond_completeddate_tv;
    TextView add_newpond_total_amount_tv;
    EditText add_newpond_amountcollected_et;
    EditText add_newpond_no_of_days_et;
    Spinner selectmachineno_sp,selectremarks_sp;

    LinearLayout startdate_LL,completeddate_LL,nodays_LL,machineno_LL,amount_LL,farmpondcompleted_LL,amountcollected_LL,remarks_LL;
    LinearLayout notcompleted_text_LL;
    String str_validation_for_completed;

    Class_MachineDetails[] class_machineDetails_array_obj;
    Class_MachineDetails class_machineDetails_obj;
    String str_machinecode;



    Class_RemarksDetails[] class_remarksdetails_array_obj;
    Class_RemarksDetails class_remarksdetails_obj;
    String str_remarksid;

    String str_farmpondcount;
    Date startDate,endDate;

    // Class_FarmerProfileOffline[] class_farmerprofileoffline_array_obj;

    Class_addfarmponddetails_ToFromServer2[] class_addfarmponddetails_toFromServer2_array_obj;

    String str_DBerror,str_DBerror_check;

    Boolean digitalcamerabuttonpressed = false;
    String mCurrentPhotoPath = "";
    Bitmap bitmap;
    String path;
    byte[] signimageinbytesArray = {0};
    String str_img = "";


    //Class_farmponddetails_offline[] class_farmponddetails_offline_array_obj,newfarmponddetails_offline_array_obj;

    LinearLayout farmpondLocationlabel_LL,farmpondLocationlatitude_LL,farmpondLocationlongitude_LL;

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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm_pond);


        toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
        // Set upon the actionbar
        setSupportActionBar(toolbar);
        // Now use actionbar methods to show navigation icon and title
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Set upon the actionbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        add_newfarmpond_iv=(ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
        title.setText("Add FarmPond Details");
        getSupportActionBar().setTitle("");
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorwhite), PorterDuff.Mode.SRC_ATOP);
        add_newfarmpond_iv.setVisibility(View.GONE);



        sharedpref_farmerid_Obj=getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_farmerID = sharedpref_farmerid_Obj.getString(Key_FarmerID, "").trim();


        sharedpref_farmerid_Obj=getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_farmername = sharedpref_farmerid_Obj.getString(Key_FarmerName, "").trim();



        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_employee_id=sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();


        //   submittedby_tv.setText((SaveSharedPreference.getUsermailID(AddFarmPondDetails_Activity.this)));
        str_submitter_mailid=sharedpreferencebook_usercredential_Obj.getString(KeyValue_employee_mailid, "").trim();

        str_perday_amount=sharedpreferencebook_usercredential_Obj.getString(KeyValue_perdayamount, "").trim();

        Log.e("farmername",str_farmername);
        Log.e("farmerid",str_farmerID);









        str_DBerror_check="no";
        str_remarksid="100";


        add_newpond_farmername_et=(TextView)findViewById(R.id.add_newpond_farmername_et);
        add_newpond_width_et=(EditText)findViewById(R.id.add_newpond_width_et);
        add_newpond_height_et=(EditText)findViewById(R.id.add_newpond_height_et);
        add_newpond_depth_et=(EditText)findViewById(R.id.add_newpond_depth_et);

        add_landacres_et=(EditText)findViewById(R.id.add_landacres_et);
        add_landgunta_et=(EditText)findViewById(R.id.add_landgunta_et);
        submittedby_tv=(TextView)findViewById(R.id.submittedby_tv);


        add_newpond_image1_iv=(ImageView)findViewById(R.id.add_newpond_image1_iv);
        add_newpond_image2_iv=(ImageView)findViewById(R.id.add_newpond_image2_iv);
        add_newpond_image3_iv=(ImageView)findViewById(R.id.add_newpond_image3_iv);


        removeimage1_ib=(ImageButton) findViewById(R.id.removeimage1_ib);
        removeimage2_ib=(ImageButton) findViewById(R.id.removeimage2_ib);
        removeimage3_ib=(ImageButton) findViewById(R.id.removeimage3_ib);

        add_ponddetails_submit_bt=(Button)findViewById(R.id.add_ponddetails_submit_bt);
        add_ponddetails_cancel_bt=(Button)findViewById(R.id.add_ponddetails_cancel_bt);

        cancel_submit_addnew_ll=(LinearLayout)findViewById(R.id.cancel_submit_addnew_ll);

        longitude_tv=(TextView)findViewById(R.id.longitude_tv);
        latitude_tv=(TextView)findViewById(R.id.latitude_tv);

        new_farmpond_completed_cb=(CheckBox)findViewById(R.id.new_farmpond_completed_cb);
        add_newpond_completeddate_tv=(TextView)findViewById(R.id.add_newpond_completeddate_tv);
        add_newpond_no_of_days_et=(EditText)findViewById(R.id.add_newpond_no_of_days_et);
        add_newpond_total_amount_tv=(TextView)findViewById(R.id.add_newpond_total_amount_tv);
        add_newpond_amountcollected_et=findViewById(R.id.add_newpond_amountcollected_et);
        selectmachineno_sp=(Spinner)findViewById(R.id.selectmachineno_sp);
        selectremarks_sp=(Spinner)findViewById(R.id.selectremarks_sp);

        add_newpond_startdate_tv=(TextView)findViewById(R.id.add_newpond_startdate_tv);

        startdate_LL=(LinearLayout)findViewById(R.id.startdate_LL);
        completeddate_LL=(LinearLayout)findViewById(R.id.completeddate_LL);
        nodays_LL=(LinearLayout)findViewById(R.id.nodays_LL);
        machineno_LL=(LinearLayout)findViewById(R.id.machineno_LL);
        remarks_LL=(LinearLayout)findViewById(R.id.remarks_LL);
        amount_LL=(LinearLayout)findViewById(R.id.amount_LL);
        amountcollected_LL=findViewById(R.id.amountcollected_LL);
        farmpondcompleted_LL=(LinearLayout)findViewById(R.id.farmpondcompleted_LL);
        notcompleted_text_LL=(LinearLayout)findViewById(R.id.notcompleted_text_LL);


        farmpondLocationlabel_LL=(LinearLayout)findViewById(R.id.farmpondLocationlabel_LL);
        farmpondLocationlatitude_LL=(LinearLayout)findViewById(R.id.farmpondLocationlatitude_LL);
        farmpondLocationlongitude_LL=(LinearLayout)findViewById(R.id.farmpondLocationlongitude_LL);
        amountcollected_lesser_LL=(LinearLayout)findViewById(R.id.amountcollected_lesser_LL);

        startdate_LL.setVisibility(View.GONE);
        completeddate_LL.setVisibility(View.GONE);
        nodays_LL.setVisibility(View.GONE);
        machineno_LL.setVisibility(View.GONE);
        remarks_LL.setVisibility(View.GONE);
        amount_LL.setVisibility(View.GONE);
        amountcollected_LL.setVisibility(View.GONE);
        farmpondcompleted_LL.setVisibility(View.GONE);

        farmpondLocationlabel_LL.setVisibility(View.GONE);
        farmpondLocationlatitude_LL.setVisibility(View.GONE);
        farmpondLocationlongitude_LL.setVisibility(View.GONE);
        amountcollected_lesser_LL.setVisibility(View.GONE);


        // new_farmpond_completed_cb.setChecked(false);
        str_validation_for_completed="no";
        str_machinecode="nocode";

        str_image1=str_image2=str_image3="false";
        str_cancelclicked="false";
        add_newpond_farmername_et.setText(str_farmername);
        submittedby_tv.setText(str_submitter_mailid);



        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Log.e("date",formattedDate);

        str_submitteddatetime=formattedDate;

        add_newpond_startdate_tv.setText(str_submitteddatetime);
        add_newpond_completeddate_tv.setText(str_submitteddatetime);

        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            startDate =   myFormat.parse(add_newpond_startdate_tv.getText().toString());   // initialize start date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endDate   = myFormat.parse(add_newpond_startdate_tv.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long duration  = endDate.getTime() - startDate.getTime();
        Log.e("days", String.valueOf(duration));



        // System.out.println("Days: " + int_days);


        appLocationService = new AppLocationService(
                AddFarmPondActivity.this);
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

        }



     /*   gpstracker_obj2 = new Class_GPSTracker(AddFarmPondActivity.this);
        if(gpstracker_obj2.canGetLocation())
        {
            double_currentlatitude = gpstracker_obj2.getLatitude();
            double_currentlongitude = gpstracker_obj2.getLongitude();
            str_latitude =Double.toString(double_currentlatitude);
            str_longitude =Double.toString(double_currentlongitude);

            *//*latitude_tv.setText(str_latitude);
            longitude_tv.setText(str_longitude);*//*

            Log.e("lat",str_latitude);
            Log.e("long",str_longitude);

            if(str_latitude.equals("0.0")||str_longitude.equals("0.0"))
            {
                // alertdialog_refresh_latandlong();
            }


        }else
        {
            //gpstracker_obj2.showSettingsAlert();
        }
*/
        //newfarmpond_count();
        //checkthecount();



        arraylist_image1_base64.add("noimage1");
        arraylist_image2_base64.add("noimage2");
        arraylist_image3_base64.add("noimage3");


        str_image1present=str_image2present=str_image3present="no";

        uploadfromDB_Machinelist();
       uploadfromDB_Remarkslist();

        DB_ViewFarmerlist_pondcount(str_farmerID);



        add_ponddetails_submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

               // doLogin();

                if(1<2)
                {
                    if (validation())
                    {
                        cancel_submit_addnew_ll.setVisibility(View.GONE);

                       /* gpstracker_obj1 = new Class_GPSTracker(AddFarmPondActivity.this);
                        if (gpstracker_obj1.canGetLocation()) {


                            double_currentlatitude = gpstracker_obj1.getLatitude();
                            double_currentlongitude = gpstracker_obj1.getLongitude();

                            str_latitude = Double.toString(double_currentlatitude);
                            str_longitude = Double.toString(double_currentlongitude);


                            latitude_tv.setText(str_latitude);
                            longitude_tv.setText(str_longitude);


                            internetDectector = new Class_InternetDectector(getApplicationContext());
                            isInternetPresent = internetDectector.isConnectingToInternet();

                        } else {
                            gpstracker_obj1.showSettingsAlert();
                        }*/
                        AsyncCallWS_Insert_into_DB task = new AsyncCallWS_Insert_into_DB(AddFarmPondActivity.this);
                        task.execute();
                    }

                }
            }
        });





        add_ponddetails_cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_cancelclicked="true";
                onBackPressed();
            }
        });


        add_newpond_image1_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str_image1="true";
                str_image2="false";
                str_image3="false";
                selectImage();
                // selectImage_fromgallery();

            }
        });
        add_newpond_image2_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str_image2="true";

                str_image1="false";
                str_image3="false";
                selectImage();
            }
        });

        add_newpond_image3_iv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                str_image1="false";
                str_image2="false";

                str_image3="true";

             //   if (gps_enable())
               // {
                    locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
                    isGPSON = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


                    //getLocation();
                    appLocationService = new AppLocationService( AddFarmPondActivity.this);
                    android.location.Location nwLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                    if (nwLocation != null)
                    {
                        latitude = nwLocation.getLatitude();
                        longitude = nwLocation.getLongitude();
                        lat_str=Double.toString(latitude);
                        log_str=Double.toString(longitude);
                        Log.e(TAG,"latitude"+lat_str);
                        Log.e(TAG,"longitude"+log_str);
                        Toast.makeText(AddFarmPondActivity.this," after camera latitude="+lat_str+" longitude="+log_str,Toast.LENGTH_LONG).show();
                        str_latitude =Double.toString(latitude);
                        str_longitude =Double.toString(longitude);
                        latitude_tv.setText(str_latitude);
                        longitude_tv.setText(str_longitude);
                    }
               // }


                selectImage();
            }
        });

        removeimage1_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                arraylist_image1_base64.clear();
                arraylist_image1_base64.add("noimage1");
                removeimage1_ib.setVisibility(View.GONE);

                str_image1present="no";
                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                add_newpond_image1_iv.setImageDrawable(res);

            }
        });

        removeimage2_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist_image2_base64.clear();
                arraylist_image2_base64.add("noimage2");
                removeimage2_ib.setVisibility(View.GONE);

                str_image2present="no";

                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                add_newpond_image2_iv.setImageDrawable(res);

               /* arraylist_image3_base64.clear();
                arraylist_image3_base64.add("noimage3");
                removeimage3_ib.setVisibility(View.GONE);
                add_newpond_image3_iv.setVisibility(View.GONE);*/
            }
        });
        removeimage3_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist_image3_base64.clear();
                arraylist_image3_base64.add("noimage3");
                removeimage3_ib.setVisibility(View.GONE);

                str_image3present="no";
                notcompleted_text_LL.setVisibility(View.VISIBLE);

                str_validation_for_completed="no";
                startdate_LL.setVisibility(View.GONE);
                completeddate_LL.setVisibility(View.GONE);
                nodays_LL.setVisibility(View.GONE);
                machineno_LL.setVisibility(View.GONE);
                remarks_LL.setVisibility(View.GONE);
                amount_LL.setVisibility(View.GONE);
                amountcollected_LL.setVisibility(View.GONE);
                farmpondcompleted_LL.setVisibility(View.GONE);

                farmpondLocationlabel_LL.setVisibility(View.GONE);
                farmpondLocationlatitude_LL.setVisibility(View.GONE);
                farmpondLocationlongitude_LL.setVisibility(View.GONE);




                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                add_newpond_image3_iv.setImageDrawable(res);

            }
        });




        selectmachineno_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                class_machineDetails_obj = (Class_MachineDetails) selectmachineno_sp.getSelectedItem();
                //str_machinecode = class_machineDetails_obj.getMachine_Code().toString();
                str_machinecode = class_machineDetails_obj.getMachine_ID().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectremarks_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                class_remarksdetails_obj=(Class_RemarksDetails)selectremarks_sp.getSelectedItem();
                str_remarksid=class_remarksdetails_obj.getRemarks_ID().toString();
               Toast.makeText(getApplicationContext(),""+str_remarksid,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });









    }//end of oncreate()






    public boolean validation()
    {
        boolean b_pond_width1,b_pond_width2,b_pond_height1,b_pond_height2,b_pond_depth1,b_pond_depth2,b_pondimages,
                b_add_completed_date,b_no_days,b_machine_no,b_datevalidation,b_enteramount,b_reason;

        b_pond_width1=b_pond_width2=b_pond_height1=b_pond_height2=b_pond_depth1=b_pond_depth2=b_pondimages=
                b_add_completed_date=b_no_days=b_datevalidation=b_enteramount=b_reason=true;



        long long_diffdays,long_days;

        Double long_entereddays;

        long_days=0;
        long_diffdays=1; long_entereddays=2.0;


        if (add_newpond_width_et.getText().toString().length() == 0) {
            add_newpond_width_et.setError("Empty not allowed");
            add_newpond_width_et.requestFocus();
            b_pond_width1=false;
        }


        if (add_newpond_width_et.getText().toString().length() <= 1||(Integer.parseInt(add_newpond_width_et.getText().toString())<0)) {
            add_newpond_width_et.setError("Enter Valid Width");
            add_newpond_width_et.requestFocus();
            b_pond_width2=false;
        }

        if (add_newpond_height_et.getText().toString().length() == 0) {
            add_newpond_height_et.setError("Empty not allowed");
            add_newpond_height_et.requestFocus();
            b_pond_height1=false;
        }
        if (add_newpond_height_et.getText().toString().length() <= 1||(Integer.parseInt(add_newpond_height_et.getText().toString())<0)) {
            add_newpond_height_et.setError("Enter Valid Length");
            add_newpond_height_et.requestFocus();
            b_pond_height2=false;
        }


        if (add_newpond_depth_et.getText().toString().length() == 0) {
            add_newpond_depth_et.setError("Empty not allowed");
            add_newpond_depth_et.requestFocus();
            b_pond_depth1=false;
        }

        if (add_newpond_depth_et.getText().toString().length() <= 1||(Integer.parseInt(add_newpond_depth_et.getText().toString())<0)) {
            add_newpond_depth_et.setError("Enter Valid Depth");
            add_newpond_depth_et.requestFocus();
            b_pond_depth2=false;
        }

        // if(arraylist_image1_base64.size()==0||arraylist_image2_base64.size()==0||arraylist_image3_base64.size()==0)


        // if(arraylist_image1_base64.size()==0||arraylist_image2_base64.size()==0)

       /* Log.e("string1",arraylist_image1_base64.get(0).toString());
        Log.e("string2",arraylist_image2_base64.get(0).toString());
*/
        String str_array_image1=arraylist_image1_base64.get(0).toString();
        String str_array_image2=arraylist_image2_base64.get(0).toString();

                /*if(str_array_image1.equalsIgnoreCase("noimage1")||
                        str_array_image2.equalsIgnoreCase("noimage2"))*/

        //Log.e("validimage1",str_array_image1.toString());

        if(str_array_image1.trim().equalsIgnoreCase("noimage1"))
        {
            Toast.makeText(getApplication(),"Add the 1st Pond Image",Toast.LENGTH_LONG).show();
            Log.e("inside","if statement");
            b_pondimages=false;
        }
        else
        {
            if((str_image2present.equalsIgnoreCase("no")&&
                    str_image3present.equalsIgnoreCase("yes")))
            {
                Toast.makeText(getApplication(),"Add the 2nd Pond Image",Toast.LENGTH_LONG).show();
                b_pondimages=false;
            }
        }



        if(str_validation_for_completed.equalsIgnoreCase("yes"))
        {
            // b_add_completed_date,b_no_days
            if(add_newpond_completeddate_tv.getText().toString().equalsIgnoreCase("Click-for_calendar"))
            {
                Toast.makeText(getApplication(),"Add the completed Date",Toast.LENGTH_LONG).show();
                b_add_completed_date=false;
            }
            //Log.e("length", String.valueOf(add_newpond_no_of_days_et.getText().toString().trim().length()));


            if(add_newpond_total_amount_tv.getText().toString().trim().length()>0)
            {
                if(add_newpond_total_amount_tv.getText().toString().equalsIgnoreCase("0"))
                {
                    Toast.makeText(getApplication(),"Add days",Toast.LENGTH_LONG).show();
                    add_newpond_no_of_days_et.setError("Enter the Days");
                    add_newpond_no_of_days_et.requestFocus();
                    b_no_days=false;
                }
            }

            if(add_newpond_amountcollected_et.getText().toString().trim().length()==0)
            {
                add_newpond_amountcollected_et.setError("Enter Amount");
                b_enteramount=false;
            }

           /* if(add_newpond_amountcollected_et.getText().toString().trim().length()>0)
            {
                int x= Integer.parseInt(add_newpond_amountcollected_et.getText().toString().trim());
                if(x<=0) {
                    add_newpond_amountcollected_et.setError("Enter Valid Amount");
                    b_enteramount = false;
                }
            }*/


           /* if(str_remarksid.equalsIgnoreCase("100"))
            {
                Toast.makeText(getApplicationContext(), "Select the reason", Toast.LENGTH_SHORT).show();
                b_reason=false;
            }*/




            if(add_newpond_no_of_days_et.getText().toString().equalsIgnoreCase("."))
            {
                Toast.makeText(getApplication(),"Add days",Toast.LENGTH_LONG).show();
                add_newpond_no_of_days_et.setError("Enter the Days");
                add_newpond_no_of_days_et.requestFocus();
                b_no_days=false;
            }

            if(add_newpond_no_of_days_et.getText().toString().trim().length()<=0)
            {
                Toast.makeText(getApplication(),"Add days",Toast.LENGTH_LONG).show();
                add_newpond_no_of_days_et.setError("Enter the Days");
                add_newpond_no_of_days_et.requestFocus();
                b_no_days=false;
            }

            if(add_newpond_no_of_days_et.getText().toString().trim().length()>=0)
            {
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    startDate =   myFormat.parse(add_newpond_startdate_tv.getText().toString());   // initialize start date
                    Log.e("startdate", String.valueOf(startDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    endDate   = myFormat.parse(add_newpond_completeddate_tv.getText().toString());
                    Log.e("enddate", String.valueOf(endDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long_days  = endDate.getTime() - startDate.getTime();

                // System.out.println ("Days: " + TimeUnit.DAYS.convert(long_days, TimeUnit.MILLISECONDS));
                //Log.e("longdays", String.valueOf(long_days));
                long_diffdays= TimeUnit.DAYS.convert(long_days, TimeUnit.MILLISECONDS);
                long_diffdays=long_diffdays+1;
                Log.e("diffdays", String.valueOf(long_diffdays));





                if(add_newpond_no_of_days_et.getText().toString().equalsIgnoreCase(".")
                        ||add_newpond_total_amount_tv.getText().toString().equalsIgnoreCase("0")) {
                }
                else {

                    if (add_newpond_no_of_days_et.getText().toString().trim().length() <= 0) {
                        long_entereddays = 0.0;
                    } else {


                        String x=add_newpond_no_of_days_et.getText().toString().trim();

                       /*NumberFormat nf = NumberFormat.getInstance();
                       Number number=0;
                       try {
                           number = nf.parse(x);
                       } catch (ParseException e) {
                           e.printStackTrace();
                       }

                       double deci_amount = (double) number;

                       long_entereddays =  Long.parseLong(String.valueOf(deci_amount));*/

                        Log.e("noofdays",x);
                        //long_entereddays =  (long)Double.parseDouble(x);
                        long_entereddays =  Double.parseDouble(x);
                    }



                    double d1 = long_diffdays*10;
                    double d2 = long_entereddays*10;

                    long_diffdays=long_diffdays*10;
                    long_entereddays=long_entereddays*10;

                    Log.e("longd", String.valueOf(d1));
                    Log.e("longE", String.valueOf(d2));

                    int int_diffdays= (int) ( long_diffdays);
                    // int int_entereddays= (int) (long_entereddays);


                    int int_entereddays= (int) d2;


               /* if(int_diffdays>=int_entereddays)
                {
                    Log.e("i1","1st is greater");
                }
                else{
                    Log.e("i1","2nd is greater");

                }

            Log.e("diff", String.valueOf(int_diffdays));
                   Log.e("enter", String.valueOf(int_entereddays));*/


                    // if (long_diffdays >= long_entereddays)

                    if (int_diffdays >= int_entereddays)
                    {
                    } else {
                        //add_newpond_no_of_days_et.setError("No of Days is more than Completed Date");
                        Toast.makeText(getApplication(), "No of Days is more than Completed Date", Toast.LENGTH_LONG).show();
                        b_no_days = false;
                    }
                }
            }




        }



        //add_newpond_completeddate_tv

        if ((add_newpond_startdate_tv.getText().toString().trim().length() != 0) &&
                (add_newpond_completeddate_tv.getText().toString().trim().length() != 0))
        {
        /*if(date1.compareTo(date2)<0){ //0 comes when two date are same,
            //1 comes when date1 is higher then date2
            //-1 comes when date1 is lower then date2 }*/

            SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy");  //2017-06-22


            String str_ddmmyyyy_startdate=add_newpond_startdate_tv.getText().toString().trim();
            String str_ddmmyyyy_completedate=add_newpond_completeddate_tv.getText().toString().trim();


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













        return ( b_pond_width1&&b_pond_width2&&b_pond_height1&&b_pond_height2&&b_pond_depth1&&b_pond_depth2&&b_pondimages
                &&b_add_completed_date&&b_no_days&&b_datevalidation&&b_enteramount&&b_reason);
    }






















    public void alertdialog_refresh_latandlong() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(AddFarmPondActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Click Ok to fetch latitude and Longitude");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                Intent i = new Intent(AddFarmPondActivity.this, EachFarmPondDetails_Activity.class);
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




    private void selectImage()
    {
        // CharSequence[] items;

       /* if(str_image3.equalsIgnoreCase("true"))
        {
            final CharSequence[] items = {"Take Photo", "Choose from Library",
                    "Cancel"};
        }
        else{
            final CharSequence[] items = {"Take Photo","Cancel"};
        }*/

        final CharSequence[] charSequenceItems;

        List<String> listItems = new ArrayList<String>();

        listItems.add("Item1");
        listItems.add("Item2");
        listItems.add("Item3");
        charSequenceItems = listItems.toArray(new CharSequence[listItems.size()]);


        final CharSequence[] items;

        if(str_image3.equalsIgnoreCase("true"))
        {
            List<String> listItems1 = new ArrayList<String>();
            listItems1.add("Take Photo");
            listItems1.add("Cancel");

            items = listItems1.toArray(new CharSequence[listItems1.size()]);
        }
        else
        {
            List<String> listItems2 = new ArrayList<String>();
            listItems2.add("Take Photo");
            listItems2.add("Choose from Library");
            listItems2.add("Cancel");

            items = listItems2.toArray(new CharSequence[listItems2.size()]);

        }





       /* final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};*/

        /* final CharSequence[] items = {"Take Photo","Cancel"};*/

        AlertDialog.Builder builder = new AlertDialog.Builder(AddFarmPondActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Class_utility.checkPermission(AddFarmPondActivity.this);
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


    private void galleryIntent()
    {
       /* Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);*/


        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == SELECT_FILE) {

                onSelectFromGalleryResult(data);
            }
            else if(requestCode == 2)
            {


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
                BitMapToString(thumbnail);

                /*add_newpond_image1_iv.setImageBitmap(thumbnail);
                BitMapToString1(thumbnail);*/




            }

            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data)
    {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                BitMapToString(bm);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // add_newpond_image1_iv.setImageBitmap(bm);

       /* if(isInternetPresent){
            SaveFarmerImage();
        }else{

            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

        }*/

    }
    private void onCaptureImageResult(Intent data)
    {
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


        if(str_image1.equals("true"))
        {
            add_newpond_image1_iv.setImageBitmap(bitmap_thumbnail);
            removeimage1_ib.setVisibility(View.VISIBLE);
            str_image1="false";
            str_image1present="yes";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

            arraylist_image1_base64.clear();
            arraylist_image1_base64.add(str_base64imagestring);
            // BitMapToString(thumbnail);
        }
        if(str_image2.equals("true"))
        {
            add_newpond_image2_iv.setImageBitmap(bitmap_thumbnail);
            removeimage2_ib.setVisibility(View.VISIBLE);
            str_image2="false";
            str_image2present="yes";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

            arraylist_image2_base64.clear();
            arraylist_image2_base64.add(str_base64imagestring);
            add_newpond_image3_iv.setVisibility(View.VISIBLE);
            //BitMapToString(thumbnail);
        }
        if(str_image3.equals("true"))
        {
            add_newpond_image3_iv.setImageBitmap(bitmap_thumbnail);
            removeimage3_ib.setVisibility(View.VISIBLE);
            farmpondcompleted_LL.setVisibility(View.VISIBLE);

            str_image3present="yes";
            notcompleted_text_LL.setVisibility(View.GONE);
            str_validation_for_completed="yes";
            startdate_LL.setVisibility(View.VISIBLE);
            completeddate_LL.setVisibility(View.VISIBLE);
            nodays_LL.setVisibility(View.VISIBLE);
            machineno_LL.setVisibility(View.VISIBLE);
            remarks_LL.setVisibility(View.VISIBLE);
            amount_LL.setVisibility(View.GONE);
            amountcollected_LL.setVisibility(View.VISIBLE);
            farmpondcompleted_LL.setVisibility(View.VISIBLE);

            farmpondLocationlabel_LL.setVisibility(View.VISIBLE);
            farmpondLocationlatitude_LL.setVisibility(View.VISIBLE);
            farmpondLocationlongitude_LL.setVisibility(View.VISIBLE);



            str_image3="false";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image3_base64.clear();
            arraylist_image3_base64.add(str_base64imagestring);
            // BitMapToString(bitmap_thumbnail);
        }

//
        /*if(isInternetPresent){
            SaveFarmerImage();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
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




    public void BitMapToString(Bitmap userImage1)
    {


        if(str_image1.equalsIgnoreCase("true"))
        {
            str_image1="false";
            add_newpond_image1_iv.setImageBitmap(userImage1);
            removeimage1_ib.setVisibility(View.VISIBLE);

            str_image1present="yes";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            userImage1.compress(Bitmap.CompressFormat.PNG, 90, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image1_base64.clear();
            arraylist_image1_base64.add(str_base64imagestring);
            str_base64imagestring="";
        }

        if(str_image2.equalsIgnoreCase("true"))
        {
            str_image2="false";
            add_newpond_image2_iv.setImageBitmap(userImage1);
            removeimage2_ib.setVisibility(View.VISIBLE);
            str_image2present="yes";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            userImage1.compress(Bitmap.CompressFormat.PNG, 90, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image2_base64.clear();
            arraylist_image2_base64.add(str_base64imagestring);
            str_base64imagestring="";
            /*add_newpond_image3_iv.setVisibility(View.VISIBLE);*/


        }


        if(str_image3.equalsIgnoreCase("true"))
        {
            str_image3="false";
            add_newpond_image3_iv.setImageBitmap(userImage1);
            removeimage3_ib.setVisibility(View.VISIBLE);

            str_image3present="yes";
            notcompleted_text_LL.setVisibility(View.VISIBLE);

            str_validation_for_completed="yes";
            startdate_LL.setVisibility(View.VISIBLE);
            completeddate_LL.setVisibility(View.VISIBLE);
            nodays_LL.setVisibility(View.VISIBLE);
            machineno_LL.setVisibility(View.VISIBLE);
            remarks_LL.setVisibility(View.VISIBLE);
            amount_LL.setVisibility(View.GONE);
            amountcollected_LL.setVisibility(View.VISIBLE);
            farmpondcompleted_LL.setVisibility(View.VISIBLE);



            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            userImage1.compress(Bitmap.CompressFormat.PNG, 90, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image3_base64.clear();
            arraylist_image3_base64.add(str_base64imagestring);
            str_base64imagestring="";

        }



        // return str_base64imagestring;
    }








    private class AsyncCallWS_Insert_into_DB extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("DFFarmPond", "doInBackground");


            insert_newfarmpond_into_FarmPondDetails_fromServer_table();


            return null;
        }

        public AsyncCallWS_Insert_into_DB(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();




            if(str_DBerror_check.equalsIgnoreCase("no"))
            {

                DB_ViewFarmerlist_updatepondcount(str_farmerID);

               /* Toast.makeText(getApplicationContext(), "New FarmPond has been added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
                startActivity(intent);
                finish();*/




                /*internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if(isInternetPresent)
                {

                    fetch_DB_farmerprofile_offline_data();
                }else
               {
                   Toast.makeText(getApplicationContext(), "New FarmPond has been added", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(AddFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
                   startActivity(intent);
                   finish();
                }*/

            }else{

                Toast.makeText(getApplicationContext(),"Contact Technology Team: "+str_DBerror,Toast.LENGTH_LONG).show();
            }






        }//end of OnPostExecute

    }// end Async task




    public void insert_newfarmpond_into_FarmPondDetails_fromServer_table()
    {


        try
        {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df_farmcode = new SimpleDateFormat("ddMMyyyy");
            String str_farmcodeadd = df_farmcode.format(c.getTime());
            Log.e("date",str_farmcodeadd);


            //Log.e("name", String.valueOf(System.currentTimeMillis()));
            String str_farmpond_id="tempfarmpond"+String.valueOf(System.currentTimeMillis());
            String str_farmpond_code=str_farmpond_id+str_farmcodeadd;

            String str_farmername_db=str_farmername.toString();
            String str_farmerid_db=str_farmerID.toString();

            Log.e("dbfarmername",str_farmername_db);
            Log.e("dbfarmerid",str_farmerid_db);

            String str_width=add_newpond_width_et.getText().toString();
            String str_height=add_newpond_height_et.getText().toString();
            String str_depth=add_newpond_depth_et.getText().toString();
            String str_latitude=latitude_tv.getText().toString();
            String str_longitude=longitude_tv.getText().toString();

            String str_acres=add_landacres_et.getText().toString();
            String  str_gunta=add_landgunta_et.getText().toString();
            String  str_crop_beforepond="";
            String  str_crop_afterpond="";


            String str_imageid1="0";
            String str_imageid2="0";
            String str_imageid3="0";


            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            Log.e("currentDateandTime",currentDateandTime);

            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


            str_currentDateandTime= currentDate+currentTime;
            Log.e("str_currentDateandTime",str_currentDateandTime);
            //Note: UploadedStatus is 2 for not uploaded and 3 for after uploaded

        /*add_newpond_width_et=(EditText)findViewById(R.id.add_newpond_width_et);
        add_newpond_height_et=(EditText)findViewById(R.id.add_newpond_height_et);
        add_newpond_depth_et=(EditText)findViewById(R.id.add_newpond_depth_et);*/

            String str_completeddate,str_nodays,str_pondcost,str_mcode,str_startdate,
                    str_farmpond_remarks,str_farmpond_amttaken,str_farmpondstatus, str_enteredpondcost;

            if(str_validation_for_completed.equalsIgnoreCase("yes"))
            {
                str_startdate=add_newpond_startdate_tv.getText().toString();
                str_completeddate=add_newpond_completeddate_tv.getText().toString();
                str_nodays=add_newpond_no_of_days_et.getText().toString();

                str_pondcost= add_newpond_total_amount_tv.getText().toString();
                str_enteredpondcost=add_newpond_amountcollected_et.getText().toString();
                str_mcode=str_machinecode;
                str_farmpond_remarks=str_remarksid;
                str_farmpond_amttaken=add_newpond_amountcollected_et.getText().toString();
                str_farmpondstatus="3";

            }
            else
            {
                str_startdate="0";
                str_completeddate="0";
                str_nodays="0";

                str_pondcost= "0";
                str_mcode="0";
                str_farmpond_remarks="0";
                str_farmpond_amttaken="0";
                str_farmpondstatus="3";
            }






            SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);





            /*
            db1.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServer(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR,TempFIDDB VARCHAR," +
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
                    "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");
                    */


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
                    "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");


            String str_farmerMName,str_farmerLName,str_yearID,str_stateID,str_districtID,str_talukID,str_panchayatID,str_villageID,
                    str_farmerage,str_Fphonenumber,str_FannualIncome,str_Ffamilymember,str_FIDprooftype,str_FIDproofno,str_Fphoto;

            str_farmerMName=str_farmerLName=str_yearID=str_stateID=str_districtID=str_talukID=str_panchayatID=str_villageID=
                    str_farmerage=str_Fphonenumber=str_FannualIncome=str_Ffamilymember=str_FIDprooftype=str_FIDproofno=str_Fphoto="empty";

            String str_approvalstatus,str_approvalremarks,str_approvedby,str_approveddate,str_donorname;
            str_approvalstatus="no";
            str_approvalremarks="no";
            str_approvedby="no";
            str_approveddate="no";
            str_donorname="no";








            String SQLiteQuery = "INSERT INTO FarmPondDetails_fromServerRest (FIDDB,TempFIDDB,FNameDB,FMNameDB,FLNameDB,FYearIDDB,FStateIDDB,FDistrictIDDB," +
                    "FTalukIDDB,FPanchayatIDDB,FVillageIDDB,FageDB,FphonenumberDB,FfamilymemberDB,FAnnualIncomeDB,FidprooftypeDB,FidproofnoDB,FphotoDB,FPondidDB," +
                    "WidthDB,HeightDB,DepthDB,LatitudeDB,LongitudeDB,Imageid1DB,Image1Base64DB,Imageid2DB,Image2Base64DB," +
                    "Imageid3DB,Image3Base64DB,EmployeeIDDB,SubmittedDateDB,TotalDaysDB,StartDateDB,ConstructedDateDB,PondCostDB,McodeDB," +
                    "FPondCodeDB,FPondRemarksDB,FPondAmtTakenDB,FPondStatusDB," +
                    "FPondApprovalStatusDB,FPondApprovalRemarksDB,FPondApprovedbyDB,FPondApprovedDateDB,FPondDonorDB," +
                    "FPondLatitudeDB,FPondLongitudeDB,FPondAcresDB,FPondGuntaDB,FPondCropBeforeDB,FPondCropAfterDB," +
                    "UploadedStatusFarmerprofile,UploadedStatus)" +
                    " VALUES ('"+str_farmerid_db+"','"+str_farmerid_db+"','"+str_farmername_db+"','"+str_farmerMName+"','"+str_farmerLName+"','"+str_yearID+"'," +
                    "'"+str_stateID+"','"+str_districtID+"','"+str_talukID+"','"+str_panchayatID+"','"+str_villageID+"','"+str_farmerage+"'," +
                    "'"+str_Fphonenumber+"','"+str_FannualIncome+"','"+str_Ffamilymember+"','"+str_FIDprooftype+"','"+str_FIDproofno+"','"+str_Fphoto+"'," +
                    "'"+str_farmpond_id+"','"+str_width+"'," +
                    "'"+str_height+"','"+str_depth+"','"+str_latitude+"','"+str_longitude+"','"+str_imageid1+"','"+arraylist_image1_base64.get(0)+"'," +
                    "'"+str_imageid2+"','"+arraylist_image2_base64.get(0)+"','"+str_imageid3+"','"+arraylist_image3_base64.get(0)+"'," +
                    "'"+str_employee_id+"','"+str_submitteddatetime+"','"+str_nodays+"','"+str_startdate+"','"+str_completeddate+"'," +
                    "'"+str_pondcost+"','"+str_mcode+"','"+str_farmpond_code+"'," +
                    "'"+str_farmpond_remarks+"','"+str_farmpond_amttaken+"','"+str_farmpondstatus+"'," +
                    "'"+str_approvalstatus+"','"+str_approvalremarks+"','"+str_approvedby+"','"+str_approveddate+"','"+str_donorname+"'," +
                    "'"+str_latitude+"','"+str_longitude+"','"+str_acres+"','"+str_gunta+"','"+str_crop_beforepond+"','"+str_crop_afterpond+"'," +
                    "'"+0+"','"+2+"');";




            db1.execSQL(SQLiteQuery);
            db1.close();


            //  DB_ViewFarmerlist_updatepondcount(str_farmerID);


            // DB_ViewFarmerlist_updatepondcount(str_farmerID);



       /* Toast.makeText(getApplicationContext(),"New FarmPond has been added",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddFarmPondDetails_Activity.this,EachFarmPondDetails_Activity.class);
        startActivity(intent);
        finish();*/
        }
        catch(Exception e)
        {
            Log.e("Insert_Error", e.getMessage());
            str_DBerror_check="yes";
            str_DBerror=e.getMessage();
            // Toast.makeText(getApplicationContext(),"error contact Tech Team"+e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }//end






    //Machinelist

    public void uploadfromDB_Machinelist()
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineIDDB VARCHAR);");
        Cursor cursor = db1.rawQuery("SELECT DISTINCT * FROM MachineDetails_fromServerRest", null);
        int x = cursor.getCount();
        Log.e("Machinecount", Integer.toString(x));


        int i = 0;
        class_machineDetails_array_obj = new Class_MachineDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_MachineDetails innerObj_class_machinelist = new Class_MachineDetails();

                innerObj_class_machinelist.setMachine_Name(cursor.getString(cursor.getColumnIndex("MachineNameDB")));
                //innerObj_class_machinelist.setMachine_Code(cursor.getString(cursor.getColumnIndex("MachineCodeDDB")));
                innerObj_class_machinelist.setMachine_ID(cursor.getString(cursor.getColumnIndex("MachineIDDB")));
                //


                Log.e("machine",innerObj_class_machinelist.getMachine_ID());
                class_machineDetails_array_obj[i] = innerObj_class_machinelist;
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, class_machineDetails_array_obj);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            selectmachineno_sp.setAdapter(dataAdapter);
            /*if(x>sel_yearsp) {
                yearlist_SP.setSelection(sel_yearsp);
            }*/
        }

    }





    public void uploadfromDB_Remarkslist()
    {

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
            selectremarks_sp.setAdapter(dataAdapter);
            /*if(x>sel_yearsp) {
                yearlist_SP.setSelection(sel_yearsp);
            }*/
        }
    }













    public void DB_ViewFarmerlist_pondcount(String str_farmerID)
    {

        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR);");


        Cursor cursor1 = db_viewfarmerlist.rawQuery("SELECT * FROM ViewFarmerListRest WHERE DispFarmerTable_FarmerID='" + str_farmerID + "'", null);
        int x = cursor1.getCount();




        //Log.e("pondcount", String.valueOf(x));
        //str_farmpondcount=String.valueOf(x);





        if(x>0)
        {
            if (cursor1.moveToFirst())
            {
                do {

                    str_farmpondcount=cursor1.getString(cursor1.getColumnIndex("Farmpondcount"));
                    Log.e("pondcount", str_farmpondcount);

                } while (cursor1.moveToNext());
            }//if ends

        }else
        {
            str_farmpondcount="0";
        }




    }





    //Machinelist






    public void DB_ViewFarmerlist_updatepondcount(String str_farmerID)
    {

        int int_farmpondcount= Integer.parseInt(str_farmpondcount);
        int_farmpondcount=int_farmpondcount+1;
        String x= String.valueOf(int_farmpondcount);

        /*
        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerList(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR,LocalFarmerImg BLOB,Farmpondcount VARCHAR);");

*/
        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");


        ContentValues cv = new ContentValues();
        cv.put("Farmpondcount",String.valueOf(int_farmpondcount));


        db_viewfarmerlist.update("ViewFarmerListRest", cv, "DispFarmerTable_FarmerID = ?", new String[]{str_farmerID});
        db_viewfarmerlist.close();


        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if(isInternetPresent)
        {

            //fetch_DB_farmerprofile_offline_data();

            Toast.makeText(getApplicationContext(), "New FarmPond has been added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddFarmPondActivity.this, EachFarmPondDetails_Activity.class);
            startActivity(intent);
            finish();
        }else
        {
            Toast.makeText(getApplicationContext(), "New FarmPond has been added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddFarmPondActivity.this, EachFarmPondDetails_Activity.class);
            startActivity(intent);
            finish();
        }

    }

















    private void doLogin()
    {


        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();


        Class_addfarmponddetails_ToFromServer2 request=new Class_addfarmponddetails_ToFromServer2();
        request.setPond_ID("0");
        request.setFarmer_ID("9276");
        request.setAcademic_ID("2020");
        request.setMachine_ID("2");
        request.setPond_Latitude("15.5876");
        request.setPond_Longitude("75.7857");
        request.setPond_Length("40");
        request.setPond_Width("30");
        request.setPond_Depth("60");
        request.setPond_Start("20-05-2020");
        request.setPond_End("20-05-2020");
        request.setPond_Days("1");
        request.setPond_Cost("2000");
        // request.setPond_Image_1("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAARuAAAEbgHQo7JoAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAGxQTFRF////AP//KK6GJK2JJK+JJq2HJa6HJa+JJa6IJa6IJa6IJq6IKK+JKK+KK7CLLrGNM7OPNLOQNbSRObWTPLeVQLiWRLmZRrqaVsCjY8WqacetgNC5htK9j9XCltjGndrJruHTtuTXueXZvufcQhO/KQAAAAp0Uk5TAAEmcH+As7Xm9myQZpsAAAB3SURBVBhXZY9HEgMhEAMFLLPQzjnH/f8ffcCmtkzfNFXSSJIkHy0li14FFzIA5OAkyfVUeicpMCJIPle1XZG9YtXLYQOd7Kfn7wNgSjBbAJPnESApwW7Yw+NMORiwfp2ul2K0Ejq9375J3fgtkH1brK3ejPub/wG/CwjxA06BTgAAAABJRU5ErkJggg==");
        request.setPond_Image_2("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAARuAAAEbgHQo7JoAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAGxQTFRF////AP//KK6GJK2JJK+JJq2HJa6HJa+JJa6IJa6IJa6IJq6IKK+JKK+KK7CLLrGNM7OPNLOQNbSRObWTPLeVQLiWRLmZRrqaVsCjY8WqacetgNC5htK9j9XCltjGndrJruHTtuTXueXZvufcQhO/KQAAAAp0Uk5TAAEmcH+As7Xm9myQZpsAAAB3SURBVBhXZY9HEgMhEAMFLLPQzjnH/f8ffcCmtkzfNFXSSJIkHy0li14FFzIA5OAkyfVUeicpMCJIPle1XZG9YtXLYQOd7Kfn7wNgSjBbAJPnESApwW7Yw+NMORiwfp2ul2K0Ejq9375J3fgtkH1brK3ejPub/wG/CwjxA06BTgAAAABJRU5ErkJggg==");
        request.setPond_Image_3("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAARuAAAEbgHQo7JoAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAGxQTFRF////AP//KK6GJK2JJK+JJq2HJa6HJa+JJa6IJa6IJa6IJq6IKK+JKK+KK7CLLrGNM7OPNLOQNbSRObWTPLeVQLiWRLmZRrqaVsCjY8WqacetgNC5htK9j9XCltjGndrJruHTtuTXueXZvufcQhO/KQAAAAp0Uk5TAAEmcH+As7Xm9myQZpsAAAB3SURBVBhXZY9HEgMhEAMFLLPQzjnH/f8ffcCmtkzfNFXSSJIkHy0li14FFzIA5OAkyfVUeicpMCJIPle1XZG9YtXLYQOd7Kfn7wNgSjBbAJPnESApwW7Yw+NMORiwfp2ul2K0Ejq9375J3fgtkH1brK3ejPub/wG/CwjxA06BTgAAAABJRU5ErkJggg==");
        request.setSubmitted_Date("20-05-2020");
        request.setCreated_By("6");
        request.setPond_Temp_ID("TempFarmpond29052020125670");
        request.setPond_Land_Gunta("20");
        request.setPond_Land_Acre("20");

        retrofit2.Call call = userService.Post_ActionFarmerPondData(request);




        call.enqueue(new Callback<Class_addfarmponddetails_ToFromServer1>()
        {
            @Override
            public void onResponse(retrofit2.Call<Class_addfarmponddetails_ToFromServer1> call, Response<Class_addfarmponddetails_ToFromServer1> response)
            {

               /* Class_farmponddetails_Response user_object= new Class_farmponddetails_Response();
                user_object = (Class_farmponddetails_Response) response.body();*/

                /*Class_farmponddetails_ToServer  user_object1= new Class_farmponddetails_ToServer();
                user_object1 = (Class_farmponddetails_ToServer) response.body();
*/

              /*  Class_addfarmponddetails_ToFromServer1  user_object1= new Class_addfarmponddetails_ToFromServer1();
                user_object1 = (Class_addfarmponddetails_ToFromServer1) response.body();*/

                Class_addfarmponddetails_ToFromServer1  user_object1=response.body();


                Log.e("response",user_object1.getStatus().toString());
                Log.e("Addpondresponse",response.body().toString());

                Log.e("response",user_object1.getLst2().getPond_Cost());


                // Log.e("Addpond_count", String.valueOf(user_object1.getClass_addfarmponddetails_toFromServer2_obj().size()));

                //Toast.makeText(AddFarmPondActivity.this, ""+user_object.getStatus().toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(AddFarmPondActivity.this, ""+user_object1.getClass_addfarmponddetails_toFromServer2_obj().get(0).getMachine_ID().toString(), Toast.LENGTH_SHORT).show();


                //user_object1.getClass_addfarmponddetails_toFromServer2_obj().size();

                //            Class_LoginResponse user_object= new Class_LoginResponse();
                //              user_object = (Class_LoginResponse) response.body();

                // Log.e("response",user_object.getStatus().toString());

                //  Toast.makeText(AddFarmPondActivity.this, ""+user_object.getStatus().toString(), Toast.LENGTH_LONG).show();

//                Toast.makeText(AddFarmPondActivity.this, ""+user_object.getMessage().toString(), Toast.LENGTH_LONG).show();

              /*  if(response.isSuccessful())
                {
                    user_object = (User) response.body();

                    Toast.makeText(MainActivity.this, ""+user_object.getEmail().toString(), Toast.LENGTH_SHORT).show();
*/
                //  Toast.makeText(MainActivity.this, "CAMU Token:"+user_object.getPass().toString(), Toast.LENGTH_SHORT).show();

                // ResObj resObj = response.body();

                // }
            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
                Toast.makeText(AddFarmPondActivity.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("response_error",t.getMessage().toString());
            }
        });
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


                    for(int i=0;i<=50;i++)
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

                            Log.e("cameralat",String.valueOf(loc.getLatitude()));
                            Log.e("cameralong",String.valueOf(loc.getLongitude()));

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


    //location





    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(AddFarmPondActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Are you sure want to go back");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(AddFarmPondActivity.this, EachFarmPondDetails_Activity.class);
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




}//end of class
