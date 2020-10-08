package df.farmponds;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import df.farmponds.Models.Class_getdemo_Response;
import df.farmponds.Models.Class_getdemo_resplist;
import df.farmponds.Models.Class_gethelp_Response;
import df.farmponds.Models.Class_gethelp_resplist;
import df.farmponds.Models.DefaultResponse;
import df.farmponds.Models.ErrorUtils;
import df.farmponds.Models.Class_gethelp_Response;
import df.farmponds.Models.Location_Data;
import df.farmponds.Models.Location_DataList;
import df.farmponds.Models.NormalLogin_Response;
import df.farmponds.remote.Class_ApiUtils;
import df.farmponds.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_HomeScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    TextView dislay_UserName_tv;
    ImageView displ_Userimg_iv;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    String str_Googlelogin_Username, str_Googlelogin_UserImg, str_loginuserID;
    String str_flag;

    //commit

    //  SharedPreferences sharedpref_username_Obj;
    // SharedPreferences sharedpref_userimage_Obj;
    SharedPreferences sharedpref_loginuserid_Obj;
    // SharedPreferences sharedpref_flag_Obj;
    Toolbar toolbar;

    //Added by shivaleela
    public static final String sharedpreferenc_username = "googlelogin_name";
    public static final String Key_username = "name_googlelogin";
    SharedPreferences sharedpref_userimage_Obj;
    public static final String sharedpreferenc_userimage = "googlelogin_img";
    public static final String key_userimage = "profileimg_googlelogin";
    SharedPreferences sharedpref_username_Obj;

    SharedPreferences sharedpref_flag_Obj;
    public static final String sharedpreferenc_flag = "flag_sharedpreference";
    public static final String key_flag = "flag";


    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    String str_employeecategory;
    RelativeLayout admin_view_rl;
    LinearLayout admin_view_ll;
    ImageView admin_view_iv;


    TelephonyManager tm1 = null;
    String myVersion, deviceBRAND, deviceHARDWARE, devicePRODUCT, deviceUSER, deviceModelName, deviceId, tmDevice, tmSerial, androidId, simOperatorName, sdkver, mobileNumber;
    int sdkVersion, Measuredwidth = 0, Measuredheight = 0, update_flage = 0;
    AsyncTask<Void, Void, Void> mRegisterTask;
    String regId = "dfagriXZ", str_userid;
    private String versioncode;

    private boolean isGPS = false;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Farmer View");
        getSupportActionBar().setTitle("");

        //admin_view_rl=(RelativeLayout)findViewById(R.id.admin_view_rl);
        admin_view_ll = (LinearLayout) findViewById(R.id.admin_view_ll);

        admin_view_iv = (ImageView) findViewById(R.id.admin_view_iv);

        sharedpref_username_Obj = getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_Googlelogin_Username = sharedpref_username_Obj.getString(Key_username, "").trim();

        sharedpref_userimage_Obj = getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_Googlelogin_UserImg = sharedpref_userimage_Obj.getString(key_userimage, "").trim();

        sharedpref_flag_Obj = getSharedPreferences(sharedpreferenc_flag, Context.MODE_PRIVATE);
        str_flag = sharedpref_flag_Obj.getString(key_flag, "").trim();

        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_employeecategory = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeecategory, "").trim();

        str_userid=sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();
        admin_view_ll.setVisibility(View.VISIBLE);

       // Log.e("Fuserid",str_userid);

       /* if(str_employeecategory.equalsIgnoreCase("Marketing"))
        {
            admin_view_ll.setVisibility(View.VISIBLE);
        }*/


        try {
            versioncode = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



      /*  int xy=db.getCount();
        Log.e("xy", String.valueOf(xy));*/




        admin_view_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //siddarath coffee day

                Intent i = new Intent(Activity_HomeScreen.this, Admin_Total_FarmpondDetails_Activity.class);
                startActivity(i);
                finish();
            }
        });

        if (!str_flag.equals("1")) {
            if (SaveSharedPreference.getUserName(Activity_HomeScreen.this).length() == 0) {
//                Intent i = new Intent(Activity_HomeScreen.this, NormalLogin.class);
//                startActivity(i);
//                finish();
//                // call Login Activity
            } else {
                str_Googlelogin_Username=SaveSharedPreference.getUserName(Activity_HomeScreen.this);
                // Stay at the current activity.
            }

        } else {

            if (SaveSharedPreference.getUserName(Activity_HomeScreen.this).length() == 0) {
                Intent i = new Intent(Activity_HomeScreen.this, MainActivity.class);
                startActivity(i);
                finish();
                // call Login Activity
            } else {
                // Stay at the current activity.
            }
        }


        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if(isInternetPresent)
        {
            Add_setGCM1();
        }




       /* LinearLayout homepagelayout_LL = (LinearLayout) findViewById(R.id.homepagelayout_ll);
        homepagelayout_LL.setVisibility(LinearLayout.VISIBLE);
        @SuppressLint("ResourceType") Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation3.setDuration(100);
        homepagelayout_LL.setAnimation(animation3);
        homepagelayout_LL.animate();
        animation3.start();*/


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        dislay_UserName_tv = (TextView) findViewById(R.id.dislay_UserName_TV);
        displ_Userimg_iv = (ImageView) findViewById(R.id.displ_Userimg_IV);
        // dislay_UserName_tv.setText(str_Googlelogin_Username);

        if (str_flag.equals("1")) {
            dislay_UserName_tv.setText("");
        } else {
            dislay_UserName_tv.setText(str_Googlelogin_Username);
            try {
                Glide.with(this).load(str_Googlelogin_UserImg).into(displ_Userimg_iv);
            } catch (NullPointerException e) {
                // Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // RelativeLayout stuRegistration_relativeLayout = (RelativeLayout) findViewById(R.id.student_registration_RL);
                LinearLayout stuRegistration_relativeLayout = (LinearLayout) findViewById(R.id.student_registration_RL);
                stuRegistration_relativeLayout.setVisibility(LinearLayout.VISIBLE);
               /* @SuppressLint("ResourceType") Animation stuRegistration_animation = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.translate);
                stuRegistration_animation.setDuration(500);
                stuRegistration_relativeLayout.setAnimation(stuRegistration_animation);
                stuRegistration_relativeLayout.setAnimation(stuRegistration_animation);
                stuRegistration_relativeLayout.animate();
                stuRegistration_animation.start();*/

                stuRegistration_relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(Activity_HomeScreen.this, Activity_MarketingHomeScreen.class);
                        startActivity(i);
                        //  overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
                    }
                });


            }
        }, 100);








    }


    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            dislay_UserName_tv.setText(account.getDisplayName());
            try {
                Glide.with(this).load(account.getPhotoUrl()).into(displ_Userimg_iv);
            } catch (NullPointerException e) {
                // Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }

        } else {
            //gotoMainActivity();
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }














    private void Add_setGCM1()
    {
        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        tm1 = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        String NetworkType;
        simOperatorName = tm1.getSimOperatorName();
        Log.e("Operator", "" + simOperatorName);
        NetworkType = "GPRS";


        int simSpeed = tm1.getNetworkType();
        if (simSpeed == 1)
            NetworkType = "Gprs";
        else if (simSpeed == 4)
            NetworkType = "Edge";
        else if (simSpeed == 8)
            NetworkType = "HSDPA";
        else if (simSpeed == 13)
            NetworkType = "LTE";
        else if (simSpeed == 3)
            NetworkType = "UMTS";
        else
            NetworkType = "Unknown";

        Log.e("SIM_INTERNET_SPEED", "" + NetworkType);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        tmDevice = "" + tm1.getDeviceId();
        Log.e("DeviceIMEI", "" + tmDevice);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mobileNumber = "" + tm1.getLine1Number();
        Log.e("getLine1Number value", "" + mobileNumber);

        String mobileNumber1 = "" + tm1.getPhoneType();
        Log.e("getPhoneType value", "" + mobileNumber1);
        tmSerial = "" + tm1.getSimSerialNumber();
        //  Log.v("GSM devices Serial Number[simcard] ", "" + tmSerial);
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        Log.e("androidId CDMA devices", "" + androidId);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        deviceId = deviceUuid.toString();
        //  Log.v("deviceIdUUID universally unique identifier", "" + deviceId);


        deviceModelName = Build.MODEL;
        Log.v("Model Name", "" + deviceModelName);
        deviceUSER = Build.USER;
        Log.v("Name USER", "" + deviceUSER);
        devicePRODUCT = Build.PRODUCT;
        Log.v("PRODUCT", "" + devicePRODUCT);
        deviceHARDWARE = Build.HARDWARE;
        Log.v("HARDWARE", "" + deviceHARDWARE);
        deviceBRAND = Build.BRAND;
        Log.v("BRAND", "" + deviceBRAND);
        myVersion = Build.VERSION.RELEASE;
        Log.v("VERSION.RELEASE", "" + myVersion);
        sdkVersion = Build.VERSION.SDK_INT;
        Log.v("VERSION.SDK_INT", "" + sdkVersion);
        sdkver = Integer.toString(sdkVersion);
        // Get display details

        Measuredwidth = 0;
        Measuredheight = 0;
        Point size = new Point();
        WindowManager w = getWindowManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //   w.getDefaultDisplay().getSize(size);
            Measuredwidth = w.getDefaultDisplay().getWidth();//size.x;
            Measuredheight = w.getDefaultDisplay().getHeight();//size.y;
        } else {
            Display d = w.getDefaultDisplay();
            Measuredwidth = d.getWidth();
            Measuredheight = d.getHeight();
        }

        Log.e("SCREEN_Width", "" + Measuredwidth);
        Log.e("SCREEN_Height", "" + Measuredheight);


        regId = FirebaseInstanceId.getInstance().getToken();



        Log.e("regId_DeviceID", "" + regId);

        Class_devicedetails request = new Class_devicedetails();
        request.setUser_ID(str_userid);
        request.setDeviceId(regId);
        request.setOSVersion(myVersion);
        request.setManufacturer(deviceBRAND);
        request.setModelNo(deviceModelName);
          request.setSDKVersion(sdkver);
         request.setDeviceSrlNo(tmDevice);
          request.setServiceProvider(simOperatorName);
          request.setSIMSrlNo(tmSerial);
         request.setDeviceWidth(String.valueOf(Measuredwidth));
         request.setDeviceHeight(String.valueOf(Measuredheight));
         request.setAppVersion(versioncode);



        {
            retrofit2.Call call = userService.Post_ActionDeviceDetails(request);

            call.enqueue(new Callback<Class_devicedetails>()
            {
                @Override
                public void onResponse(retrofit2.Call<Class_devicedetails> call, Response<Class_devicedetails> response) {


                    Log.e("response", response.toString());
                    Log.e("response_body", String.valueOf(response.body()));

                    if (response.isSuccessful())
                    {
                        //  progressDoalog.dismiss();


                        Class_devicedetails class_addfarmponddetailsresponse = response.body();

                        if (class_addfarmponddetailsresponse.getStatus().equals("true"))
                        {
                            Log.e("devicedetails", "devicedetails_Added");

                            gethelp();

                        } else if (class_addfarmponddetailsresponse.getStatus().equals("false")) {
                            //     progressDoalog.dismiss();
                            Toast.makeText(Activity_HomeScreen.this, class_addfarmponddetailsresponse.getMessage(), Toast.LENGTH_SHORT).show();
                            gethelp();
                        }
                    } else {
                        //   progressDoalog.dismiss();
                        DefaultResponse error = ErrorUtils.parseError(response);
                        Log.e("devicedetailserror", error.getMsg());
                        Toast.makeText(Activity_HomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                        gethelp();


                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(Activity_HomeScreen.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("response_error", t.getMessage().toString());
                }
            });

        }
    }




    public void gethelp()
    {
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if(isInternetPresent)
        {
            gethelp_api();
            //getdemo();
        }
    }




    private void gethelp_api()
    {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Fetching Helpdetails....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Call<Class_gethelp_Response> call = userService.GetHelp(str_userid);


        call.enqueue(new Callback<Class_gethelp_Response>() {
            @Override
            public void onResponse(Call<Class_gethelp_Response> call, Response<Class_gethelp_Response> response) {

                // Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                Log.e("response_gethelp", "response_gethelp: " + new Gson().toJson(response));

               /* Class_gethelp_Response gethelp_response_obj = new Class_gethelp_Response();
                gethelp_response_obj = (Class_gethelp_Response) response.body();*/



                if(response.isSuccessful())
                {
                    DBCreate_Helpdetails();
                    Class_gethelp_Response gethelp_response_obj = response.body();
                    Log.e("response.body", response.body().getLst().toString());


                    if (gethelp_response_obj.getStatus().equals(true))
                    {

                        List<Class_gethelp_resplist> helplist = response.body().getLst();
                        Log.e("length", String.valueOf(helplist.size()));
                        int int_helpcount=helplist.size();

                        for(int i=0;i<int_helpcount;i++)
                        {
                            Log.e("title",helplist.get(i).getTitle().toString());

                            String str_title=helplist.get(i).getTitle().toString();
                            String str_content=helplist.get(i).getContent().toString();
                            DBCreate_HelpDetails_insert_2sqliteDB(str_title,str_content);
                        }


                       // Data_from_HelpDetails_table();

                        //helplist.get(0).
                        progressDoalog.dismiss();

                        getdemo();
                    }
                   // Log.e("response.body", response.body().size);

                }



            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDoalog.dismiss();
                Log.e("WS", "error" + t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, "WS:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



    public void DBCreate_HelpDetails_insert_2sqliteDB(String title,String content)
    {
        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");

        ContentValues cv = new ContentValues();
        cv.put("TitleDB", title);
        cv.put("ContentDB", content);
        db2.insert("HelpDetails_table", null, cv);
        db2.close();

        Log.e("insert","DBCreate_HelpDetails_insert_2sqliteDB");

    }


    public void DBCreate_Helpdetails() {

        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM HelpDetails_table", null);
        int x = cursor.getCount();
        if (x > 0) {
            db2.delete("HelpDetails_table", null, null);
        }
        db2.close();
    }




    public void Data_from_HelpDetails_table()
    {
        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM HelpDetails_table", null);
        int x = cursor.getCount();

        Log.e("helpcount", String.valueOf(x));

    }




    public void getdemo()
    {
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if(isInternetPresent)
        {
            getdemo_api();
        }
    }



    private void getdemo_api()
    {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Fetching Demodetails....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Call<Class_getdemo_Response> call = userService.GetDemo(str_userid);


        call.enqueue(new Callback<Class_getdemo_Response>() {
            @Override
            public void onResponse(Call<Class_getdemo_Response> call, Response<Class_getdemo_Response> response) {
                Log.e("response_gethelp", "response_gethelp: " + new Gson().toJson(response));

               /* Class_gethelp_Response gethelp_response_obj = new Class_gethelp_Response();
                gethelp_response_obj = (Class_gethelp_Response) response.body();*/



                if(response.isSuccessful())
                {
                    DBCreate_Demodetails();
                    Class_getdemo_Response getdemo_response_obj = response.body();
                    Log.e("response.body", response.body().getLst().toString());


                    if (getdemo_response_obj.getStatus().equals(true))
                    {

                        List<Class_getdemo_resplist> demolist = response.body().getLst();
                        Log.e("length", String.valueOf(demolist.size()));
                        int int_helpcount=demolist.size();

                        for(int i=0;i<int_helpcount;i++)
                        {
                            Log.e("language",demolist.get(i).getLanguage_Name().toString());

                            String str_languagename=demolist.get(i).getLanguage_Name().toString();
                            String str_languagelink=demolist.get(i).getLanguage_Link().toString();
                            DBCreate_DemoDetails_insert_2sqliteDB(str_languagename,str_languagelink);
                        }

                        //Data_from_HelpDetails_table();

                        //helplist.get(0).
                        progressDoalog.dismiss();
                    }
                    // Log.e("response.body", response.body().size);

                }



            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDoalog.dismiss();
                Log.e("WS", "error" + t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, "WS:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



    public void DBCreate_Demodetails() {

        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS DemoDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,LanguageDB VARCHAR,LinkDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM DemoDetails_table", null);
        int x = cursor.getCount();
        if (x > 0) {
            db2.delete("DemoDetails_table", null, null);
        }
        db2.close();
    }




    public void DBCreate_DemoDetails_insert_2sqliteDB(String str_languagename,String str_languagelink)
    {
        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS DemoDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,LanguageDB VARCHAR,LinkDB VARCHAR);");

        ContentValues cv = new ContentValues();
        cv.put("LanguageDB", str_languagename);
        cv.put("LinkDB", str_languagelink);
        db2.insert("DemoDetails_table", null, cv);
        db2.close();

        Log.e("insert","DBCreate_DemoDetails_insert_2sqliteDB");

    }






        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
            menu.findItem(R.id.filedfacilater).setTitle("Cluster Head");
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        if(id==R.id.contactus)
        {

            Intent i = new Intent(Activity_HomeScreen.this, ContactUs_Activity.class);
            startActivity(i);
            finish();
            return true;
        }
        if(id==R.id.filedfacilater)
        {

            Intent i = new Intent(Activity_HomeScreen.this, ClusterHomeActivity.class);
            startActivity(i);
            finish();
            return true;
        }




        if (id == R.id.logout)
        {
            // Toast.makeText(CalenderActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent)
            {

//                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                SharedPreferences.Editor editor = settings.edit();
//                editor.remove("login_userEmail");


                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_HomeScreen.this);
                dialog.setCancelable(false);
                dialog.setTitle(R.string.alert);
                dialog.setMessage("Are you sure want to Logout?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {

                        SaveSharedPreference.setUserName(Activity_HomeScreen.this, "");

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("Key_Logout", "yes");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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






//                Intent i = new Intent(getApplicationContext(), NormalLogin.class);
//                i.putExtra("logout_key1", "yes");
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);

                //}
            } else {
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (id == android.R.id.home)
        {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Activity_HomeScreen.this, Activity_HomeScreen.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
