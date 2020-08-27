package df.farmponds;

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
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import df.farmponds.Models.AddFarmerRequest;
import df.farmponds.Models.AddFarmerResList;
import df.farmponds.Models.AddFarmerResponse;
import df.farmponds.Models.AutoSyncVersion;
import df.farmponds.Models.AutoSyncVersionList;
import df.farmponds.Models.Class_FarmerProfileOffline;
import df.farmponds.Models.Class_farmponddetails;
import df.farmponds.Models.DefaultResponse;
import df.farmponds.Models.ErrorUtils;
import df.farmponds.Models.Farmer;
import df.farmponds.Models.GetAppVersion;
import df.farmponds.Models.GetAppVersionList;
import df.farmponds.Models.PondImage;
import df.farmponds.remote.Class_ApiUtils;
import df.farmponds.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Activity_MarketingHomeScreen extends AppCompatActivity {

    //For titlebar
    Toolbar toolbar;
    //For titlebar


    //Sharedpreference to store valid offical mailID
    public static final String sharedpreferenc_mailbook = "sharedpreference_mailbook";
    public static final String NameKey_mailID = "namekey_validmailID";
    SharedPreferences sharedpref_validMailID_Obj;
    //Sharedpreference to store valid offical mailID


  //  Class_GPSTracker gps_object;

    ImageView viewfarmer_ib, college_addmission_history_IB;

    Class_InternetDectector internetDectector,internetDectector1;
    Boolean isInternetPresent = false;
    Boolean isInternetPresent1 = false;

    int int_insidecursorcount, int_cousorcount;
    String str_fetch_image, str_fetch_studentname, str_fetch_gender, str_fetch_birthdate, str_fetch_education, str_fetch_marks, str_fetch_mobileno, str_fetch_fathername, str_fetch_mothername, str_fetch_aadharno, str_fetch_studentstatus, str_fetch_admissionfee, str_fetch_createddate, str_fetch_createdby, str_fetch_receiptno;
    int int_fetch_sandboxid, int_fetch_academicid, int_fetch_clusterid, int_fetch_instituteid, int_fetch_schoolid, int_fetch_levelid;
    Boolean RegisterResponse_marketingscreen;

    TextView Notuploadedcount_edited_tv,Notuploadedcount_new_tv,farmerprofile_count_tv;
    int OfflineStudentTable_count;
    Cursor cursor;
    SQLiteDatabase db;
    int i = 0;
    ImageButton notupload_ib;

    public static final String sharedpreferenc_selectedspinner = "sharedpreferenc_selectedspinner";
    public static final String Key_sel_yearsp = "sel_yearsp";
    public static final String Key_sel_statesp = "sel_statesp";
    public static final String Key_sel_districtsp = "sel_districtsp";
    public static final String Key_sel_taluksp = "sel_taluksp";
    public static final String Key_sel_villagesp = "sel_villagesp";
    public static final String Key_sel_grampanchayatsp = "sel_grampanchayatsp";
    SharedPreferences sharedpref_spinner_Obj;

    String localimg_FarmerID;
    byte[] localimg_byte;

   // Class_Offline_Newfarmponddetails[] offline_newfarmponddetails_arrayObj;
    //int int_offlinecount;

    String str_VersionStatus;

    Class_farmponddetails[] class_farmerandpond_details_array_obj;

    Class_farmponddetails_offline[] class_farmponddetails_offline_array_obj,newfarmponddetails_offline_array_obj;

    Class_farmponddetails[] class_farmponddetails_offline_array_objRest,newfarmponddetails_offline_array_objRest;

    Class_FarmerProfileOffline[] class_farmerprofileoffline_array_obj;
    Class_FarmerProfileOffline class_farmerprofileoffline_obj;



    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_employee_id;
    private String versioncode;
    Interface_userservice userService1;
    Farmer[] class_farmerlistdetails_arrayObj;

    int int_newfarmercount,int_j;
    Class_addfarmponddetails_ToFromServer1[] newpond_response;

    int int_k;

    Class_addfarmponddetails_ToFromServer1[] editedpond_response;
    int int_er;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__farmer_home_screen);


        toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Farmer View");
        getSupportActionBar().setTitle("");

        userService1 = Class_ApiUtils.getUserService();

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        sharedpref_validMailID_Obj = getSharedPreferences(sharedpreferenc_mailbook, Context.MODE_PRIVATE);
        sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);


        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_employee_id=sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

Log.e("tag","str_employee_id="+str_employee_id);


        viewfarmer_ib = (ImageView) findViewById(R.id.viewFarmer_IB);

        Notuploadedcount_edited_tv = (TextView) findViewById(R.id.Notuploadedcount_edited_tv);
        Notuploadedcount_new_tv=  (TextView) findViewById(R.id.Notuploadedcount_new_tv);
        farmerprofile_count_tv=(TextView) findViewById(R.id.farmerprofile_count_tv);

        notupload_ib = (ImageButton) findViewById(R.id.notupload_IB);

        try {
            versioncode = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int_k=0;
        if (isInternetPresent)
        {

        //working
         fetch_DB_farmerprofile_offline_data();

          /* fetch_DB_edited_offline_data();*/
           // fetch_DB_Edited_farmerprofile_offline_data();
        }
        else
        {
            //working

            fetch_DB_farmerprofile_offline_data_count();
            fetch_DB_edited_offline_data_count();
            fetch_DB_New_pond_count();
            Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_SHORT).show();
        }


        viewfarmer_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();
                Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_ViewFarmers.class);
                SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
                myprefs_spinner.putString(Key_sel_yearsp, "0");
                myprefs_spinner.putString(Key_sel_statesp, "0");
                myprefs_spinner.putString(Key_sel_districtsp, "0");
                myprefs_spinner.putString(Key_sel_taluksp, "0");
                myprefs_spinner.putString(Key_sel_villagesp, "0");
                myprefs_spinner.putString(Key_sel_grampanchayatsp, "0");

                myprefs_spinner.apply();
                startActivity(i);


                if (isInternetPresent) {


                } else {
                    Toast.makeText(getApplicationContext(), "Connect to Internet", Toast.LENGTH_LONG).show();
                }


            }
        });

        notupload_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                internetDectector1 = new Class_InternetDectector(getApplicationContext());
                isInternetPresent1 = internetDectector1.isConnectingToInternet();

                if (isInternetPresent1)
                {
                    fetch_DB_farmerprofile_offline_data();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Connect to Internet", Toast.LENGTH_LONG).show();
                }

            }
        });

    }// end of Oncreate()


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            // Toast.makeText(CalenderActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {

                SaveSharedPreference.setUserName(getApplicationContext(), "");

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("Key_Logout", "yes");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (id == android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_HomeScreen.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }








    // Sync the data
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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR,finalfarmpondcodeDB VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatusFarmerprofile='" + 9 + "'", null);
        int x = cursor1.getCount();

        Log.e("new farmercount", String.valueOf(x));

        int i = 0;
        class_farmerprofileoffline_array_obj= new Class_FarmerProfileOffline[x];
        if(x>0)
        {
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

        for(int j=0;j<class_farmerprofileoffline_array_obj.length;j++)
        {

            //AsyncTask_submit_farmerprofiledetails(j);
            Log.e("tag","AddFarmerId =="+class_farmerprofileoffline_array_obj[j].getStr_farmerID());
            AddFarmerDetails(j);
        }

        if(x==0)
        {
            fetch_DB_newfarmpond_offline_data();
        }
        Log.e("new_farmpond","new_farmpond");

        if(2>1)
        {
            fetch_DB_New_pond_count();
        }


    }




    private void AddFarmerDetails(final int j){
        AddFarmerRequest request = new AddFarmerRequest();
       // request.setFarmerID(class_farmerprofileoffline_array_obj[j].getStr_farmerID());
        request.setFarmerID("0");
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

        Log.e("tag","FarmerFirstName=="+class_farmerprofileoffline_array_obj[j].getStr_fname());
        Log.e("tag","FarmerID=="+class_farmerprofileoffline_array_obj[j].getStr_farmerID());

        Call<AddFarmerResponse> call = userService1.AddFarmer(request);
        Log.e("TAG", "Request 33: "+new Gson().toJson(request) );
        Log.e("TAG", "Request: "+request.toString() );

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_MarketingHomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<AddFarmerResponse>()
        {
            @Override
            public void onResponse(Call<AddFarmerResponse> call, Response<AddFarmerResponse> response)
            {
                System.out.println("response"+response.body().toString());

                  /*  Log.e("response",response.toString());
                    Log.e("TAG", "response 33: "+new Gson().toJson(response) );
                    Log.e("response body", String.valueOf(response.body()));*/
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
                   /* Log.e("response new:",error1.getMsg());
                    Log.e("response new status:", String.valueOf(error1.getstatus()));*/
                // Log.e("response",Gson.fromJson(response.toString(),AddFarmer_Activity1.class));

                if(response.isSuccessful())
                {
                    AddFarmerResList addFarmerResList = response.body().getLst();
                    Log.e("tag","addFarmerResList NAme="+addFarmerResList.getFarmerFirstName());

                    String str_response_farmer_id=addFarmerResList.getFarmerID();
                    String str_response_farmercode=addFarmerResList.getFarmerCode();
                    String str_response_tempId=addFarmerResList.getMobileTempID();
                    Log.e("tag","getMobileTempID="+addFarmerResList.getMobileTempID());
                    Log.e("tag","getFarmerCode="+addFarmerResList.getFarmerCode());

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
                            "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR,finalfarmpondcodeDB VARCHAR);");



                    ContentValues cv = new ContentValues();
                    cv.put("FIDDB",str_response_farmer_id);
                /*cv.put("employee_id",str_response_image_id3);
                // employee_id*/
                    cv.put("UploadedStatusFarmerprofile",10);

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
                    cv_farmelistupdate.put("DispFarmerTable_Farmer_Code",str_response_farmercode);
                    cv_farmelistupdate.put("DispFarmerTable_FarmerID",str_response_farmer_id);


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

                    Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.e("TAG", "onFailure: "+t.toString() );

                Log.e("tag","Error:"+t.getMessage());
                Toast.makeText(Activity_MarketingHomeScreen.this, "WS:Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }


    public void fetch_DB_newfarmpond_offline_data()
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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR,finalfarmpondcodeDB VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatus='" + 2 + "'", null);
        int x = cursor1.getCount();


        // Notuploadedcount_new_tv.setText("NewPond:"+String.valueOf(x));
        Notuploadedcount_new_tv.setText( String.valueOf(x));

        Log.e("newuploadstatus_count", String.valueOf(x));

        int i = 0;

        newfarmponddetails_offline_array_objRest = new Class_farmponddetails[x];
        if(x>0)
        {
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

                    if(cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")).equalsIgnoreCase("0") ||
                            cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")).equalsIgnoreCase("noimage1") )
                    {
                        innerObj_Class_farmponddetails.setPondImage1("");
                    }else{
                        innerObj_Class_farmponddetails.setPondImage1(cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")));}

                    innerObj_Class_farmponddetails.setPondImage2(cursor1.getString(cursor1.getColumnIndex("Imageid2DB")));
                    if(cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")).equalsIgnoreCase("0") ||
                            cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")).equalsIgnoreCase("noimage2") )
                    {
                        innerObj_Class_farmponddetails.setPondImage2("");
                    }else{

                        innerObj_Class_farmponddetails.setPondImage2(cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")));}

                    innerObj_Class_farmponddetails.setPondImage3(cursor1.getString(cursor1.getColumnIndex("Imageid3DB")));
                    if(cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")).equalsIgnoreCase("0") ||
                            cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")).equalsIgnoreCase("noimage3") )
                    {
                        innerObj_Class_farmponddetails.setPondImage3("");
                    }else {

                        innerObj_Class_farmponddetails.setPondImage3(cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")));
                    }

                    innerObj_Class_farmponddetails.setCreatedBy(cursor1.getString(cursor1.getColumnIndex("EmployeeIDDB")));

                    Log.e("employeeid",cursor1.getString(cursor1.getColumnIndex("EmployeeIDDB")));


                    Log.e("submitteddate",cursor1.getString(cursor1.getColumnIndex("SubmittedDateDB")));

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

                    Log.e("class_farmpondcode",cursor1.getString(cursor1.getColumnIndex("FPondCodeDB")));
                    Log.e("class_farmpondID",cursor1.getString(cursor1.getColumnIndex("FPondidDB")));

                    newfarmponddetails_offline_array_objRest[i] = innerObj_Class_farmponddetails;
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }


        db1.close();

        Log.e("newlength", String.valueOf(newfarmponddetails_offline_array_objRest.length));

        newpond_response= new Class_addfarmponddetails_ToFromServer1[newfarmponddetails_offline_array_objRest.length];
        int_j=0;

        for(int k=0;k<newfarmponddetails_offline_array_objRest.length;k++)
        {
            if(x>0) {
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
        newpondadded_progressDoalog = new ProgressDialog(Activity_MarketingHomeScreen.this);
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
        request.setAcademic_ID("2020");
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


        int x=0;

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
                            Toast.makeText(Activity_MarketingHomeScreen.this, class_addfarmponddetailsresponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        newpondadded_progressDoalog.dismiss();
                        DefaultResponse error = ErrorUtils.parseError(response);
                        Log.e("addponderror", error.getMsg());

                        Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(Activity_MarketingHomeScreen.this, "WS:error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR,finalfarmpondcodeDB VARCHAR);");


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
            cv.put("finalfarmpondcodeDB",newpond_response[i].getLst2().get(0).getPondCode());
            //  cv.put("FPondStatusDB","3");

            cv.put("UploadedStatus",3);

            db1.update("FarmPondDetails_fromServerRest", cv, "FPondidDB = ?", new String[]{TemppondID_response});
        }
        db1.close();


        fetch_DB_edited_offline_data();
    }


    public void fetch_DB_edited_offline_data()
    {

        fetch_DB_New_pond_count();
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
        else
            {
                fetch_DB_farmerprofile_offline_data_count();
                fetch_DB_New_pond_count();
                fetch_DB_edited_offline_data_count();

                fetch_DB_Edited_farmerprofile_offline_data();
        }
    }


    private void AsyncTask_submit_edited_farmponddetails(int k)
    {

        final ProgressDialog pondsedited_progressDoalog;
        pondsedited_progressDoalog = new ProgressDialog(Activity_MarketingHomeScreen.this);
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
            request.setAcademic_ID("2020");
            request.setMachine_ID(class_farmponddetails_offline_array_obj[k].getMachineCode());
            request.setPond_Latitude(str_latitude);
            request.setPond_Longitude(str_longitude);

            Log.e("height",class_farmponddetails_offline_array_obj[k].getFarmpond_Height());
            Log.e("Width",class_farmponddetails_offline_array_obj[k].getFarmpond_Width());
            Log.e("Depth",class_farmponddetails_offline_array_obj[k].getFarmpond_Depth());

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




            if(class_farmponddetails_offline_array_obj[k].getImage1_Base64().equalsIgnoreCase("noimage1")
                    ||class_farmponddetails_offline_array_obj[k].getImage1_Base64().equalsIgnoreCase("0"))
            {}
            else{
                x++;
                if(class_farmponddetails_offline_array_obj[k].getImage2_Base64().equalsIgnoreCase("noimage2")
                        ||class_farmponddetails_offline_array_obj[k].getImage2_Base64().equalsIgnoreCase("0"))
                { }
                else{
                    x++;
                    if(class_farmponddetails_offline_array_obj[k].getImage3_Base64().equalsIgnoreCase("noimage3")
                            ||class_farmponddetails_offline_array_obj[k].getImage3_Base64().equalsIgnoreCase("0"))
                    { }
                    else{
                        x++;}
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
                        pondimage_innerobj.setImageType("1");
                    }
                if (j == 1)
                {
                    pondimage_innerobj.setImageData(class_farmponddetails_offline_array_obj[k].getImage2_Base64());
                        pondimage_innerobj.setImageType("2");
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
            request.setPond_Status(class_farmponddetails_offline_array_obj[k].getFarmpond_status());

            int_k = k;
            Log.e("kvalue", String.valueOf(k));
            Log.e("Editrequest", request.toString());

            Log.e("Editrequest",new Gson().toJson(request));

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
                            Toast.makeText(Activity_MarketingHomeScreen.this, editfarmpondresponse_obj.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        pondsedited_progressDoalog.dismiss();
                        DefaultResponse error = ErrorUtils.parseError(response);
                        Log.e("Editpondresp", error.getMsg());
                        Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(Activity_MarketingHomeScreen.this, "WS:error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR,finalfarmpondcodeDB VARCHAR);");


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



            //

            cv.put("WidthDB",editedpond_response[i].getLst2().get(0).getPond_Width());
            cv.put("HeightDB",editedpond_response[i].getLst2().get(0).getPond_Length());
            cv.put("DepthDB",editedpond_response[i].getLst2().get(0).getPond_Depth());
            cv.put("TotalDaysDB",editedpond_response[i].getLst2().get(0).getPond_Days());
            cv.put("StartDateDB",editedpond_response[i].getLst2().get(0).getPond_Start());
            cv.put("ConstructedDateDB",editedpond_response[i].getLst2().get(0).getPond_End());
            cv.put("PondCostDB",editedpond_response[i].getLst2().get(0).getPond_Cost());
            cv.put("McodeDB",editedpond_response[i].getLst2().get(0).getMachine_ID());
            cv.put("FPondRemarksDB",editedpond_response[i].getLst2().get(0).getPond_remarks());
            cv.put("FPondAmtTakenDB",editedpond_response[i].getLst2().get(0).getPond_Collected_Amount());
            cv.put("FPondStatusDB",editedpond_response[i].getLst2().get(0).getPond_Status());


            //


            cv.put("FPondCodeDB",editedpond_response[i].getLst2().get(0).getPond_ID());
            cv.put("finalfarmpondcodeDB",editedpond_response[i].getLst2().get(0).getPondCode());
            cv.put("FPondStatusDB","3");

            cv.put("UploadedStatus",0); //0 means uploaded 1 means not uploaded

            db1.update("FarmPondDetails_fromServerRest", cv, "FPondidDB = ?", new String[]{pondID_response});
        }
        db1.close();


        fetch_DB_farmerprofile_offline_data_count();
        fetch_DB_New_pond_count();
        fetch_DB_edited_offline_data_count();

        fetch_DB_Edited_farmerprofile_offline_data();

    }




    //Sync the data


















    public void fetch_DB_farmerprofile_offline_data_count()
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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR,finalfarmpondcodeDB VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatusFarmerprofile='" + 9 + "'", null);
        int x = cursor1.getCount();

        Log.e("profilecount", String.valueOf(x));


       // farmerprofile_count_tv.setText("Farmer Profile: " + String.valueOf(x));
        farmerprofile_count_tv.setText(String.valueOf(x));

    }

    public void fetch_DB_edited_offline_data_count()
    {

        Class_DBHandler dbhandler_obj=new Class_DBHandler(getApplicationContext());
        int int_editcount=dbhandler_obj.get_DB_edited_offline_data_count();
        Notuploadedcount_edited_tv.setText(String.valueOf(int_editcount));

        Log.e("uploadstatus_count", String.valueOf(Notuploadedcount_edited_tv));

    }

    public void fetch_DB_New_pond_count()
    {

        Class_DBHandler dbhandler_obj=new Class_DBHandler(getApplicationContext());

        int int_offlinefarmpond_count=dbhandler_obj.get_DB_newfarmpond_offline_data_count();

        Notuploadedcount_new_tv.setText( String.valueOf(int_offlinefarmpond_count));
        Log.e("newpondoffline_count", String.valueOf(int_offlinefarmpond_count));

    }

    private void GetAppVersionCheck(){
//        Map<String,String> params = new HashMap<String, String>();
//
//        params.put("User_ID","90");// for dynamic

        retrofit2.Call call = userService1.getAppVersion();
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_MarketingHomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<GetAppVersion>()
        {
            @Override
            public void onResponse(Call<GetAppVersion> call, Response<GetAppVersion> response)
            {
                Log.e("response",response.toString());
                Log.e("TAG", "response: "+new Gson().toJson(response) );
                Log.e("response body", String.valueOf(response.body()));

                if(response.isSuccessful())
                {
             //        progressDoalog.dismiss();
                    GetAppVersion  class_loginresponse = response.body();
                    Log.e("tag","res=="+class_loginresponse.toString());
                    if(class_loginresponse.getStatus()) {


                        List<GetAppVersionList> getAppVersionList=response.body().getlistVersion();
                        String str_releaseVer=getAppVersionList.get(0).getAppVersion();

                        int int_versioncode= Integer.parseInt(versioncode);
                        int int_releaseVer= Integer.parseInt(str_releaseVer);

                        Log.e("tag","str_releaseVer="+str_releaseVer);
                        if(int_releaseVer>int_versioncode)
                        {
                            //call pop
                            Log.e("popup","popup");
                            //alerts();
                            alerts_dialog_playstoreupdate();
                        }
                        else{

                        }
                        progressDoalog.dismiss();
                    }else{
                        progressDoalog.dismiss();
                        Toast.makeText(Activity_MarketingHomeScreen.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.e("TAG", "onFailure: "+t.toString() );

                Log.e("tag","Error:"+t.getMessage());
                Toast.makeText(Activity_MarketingHomeScreen.this, "WS:Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void GetAutoSyncVersion() {
//        Map<String,String> params = new HashMap<String, String>();
//
//        params.put("User_ID","90");// for dynamic

        retrofit2.Call call = userService1.getAutoSyncVersion(str_employee_id);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_MarketingHomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<AutoSyncVersion>() {
            @Override
            public void onResponse(Call<AutoSyncVersion> call, Response<AutoSyncVersion> response) {
                Log.e("tag","response AutoSyncVersion="+response.toString());
                Log.e("TAG", "response AutoSyncVersion: " + new Gson().toJson(response));
                Log.e("response body", String.valueOf(response.body()));

                if (response.isSuccessful()) {
                    //  progressDoalog.dismiss();
                    AutoSyncVersion class_loginresponse = response.body();
                    Log.e("tag", "res==" + class_loginresponse.toString());
                    str_VersionStatus = String.valueOf(class_loginresponse.getStatus());

                    if (class_loginresponse.getStatus()) {

                        List<AutoSyncVersionList> addFarmerResList = response.body().getListVersion();
                        Log.e("tag", "getUserSync =" + addFarmerResList.get(0).getUserSync());
                        Log.e("tag", "getUserID =" + addFarmerResList.get(0).getUserID());

                      //  str_VersionStatus = String.valueOf(class_loginresponse.getStatus());
                        alerts_dialog_AutoSyncVersion();
                        progressDoalog.dismiss();

                    } else {
                        progressDoalog.dismiss();

                        Log.e("tag", "class_loginresponse.getMessage() =" + class_loginresponse.getMessage());

                        //  Toast.makeText(Activity_MarketingHomeScreen.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                   // Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_MarketingHomeScreen.this, "WS:Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }




    public  void alerts_dialog_playstoreupdate()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_MarketingHomeScreen.this);
        dialog.setCancelable(false);
        dialog.setTitle("DF Agri");
        dialog.setMessage("Kindly update from playstore");

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Intent	intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=df.farmponds"));
                startActivity(intent);
            }
        });


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();

    }

    public  void alerts_dialog_AutoSyncVersion()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_MarketingHomeScreen.this);
        dialog.setCancelable(false);
        dialog.setTitle("DF Agri");
        dialog.setMessage("Kindly Re-Sync your data");

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                if (isInternetPresent)
                {

                    //working
                    //fetch_DB_farmerprofile_offline_data();

                    /* fetch_DB_edited_offline_data();*/
                   // fetch_DB_Edited_farmerprofile_offline_data();
                }

                Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_ViewFarmers.class);
                i.putExtra("VersionStatus", String.valueOf(str_VersionStatus));
                SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
                myprefs_spinner.putString(Key_sel_yearsp, "0");
                myprefs_spinner.putString(Key_sel_statesp, "0");
                myprefs_spinner.putString(Key_sel_districtsp, "0");
                myprefs_spinner.putString(Key_sel_taluksp, "0");
                myprefs_spinner.putString(Key_sel_villagesp, "0");
                myprefs_spinner.putString(Key_sel_grampanchayatsp, "0");

                myprefs_spinner.apply();
                startActivity(i);
            }
        });


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();

    }
    public void fetch_DB_Edited_farmerprofile_offline_data()
    {


        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");



        Cursor cursor1 = db_viewfarmerlist.rawQuery("SELECT * FROM ViewFarmerListRest WHERE UploadedStatusFarmerprofile_DB='" + 9 + "'", null);
        int x = cursor1.getCount();

        Log.e("profile_count", String.valueOf(x));



        int i = 0;


        class_farmerlistdetails_arrayObj=new Farmer[x];
        if(x>0)
        {
            if (cursor1.moveToFirst()) {

                do {

                    Farmer innerObj_class_farmerlistDetails=new Farmer();

                    //
                    innerObj_class_farmerlistDetails.setAcademic_ID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_YearID")));
                    innerObj_class_farmerlistDetails.setStateID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_StateID")));
                    innerObj_class_farmerlistDetails.setDistrictID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_DistrictID")));
                    innerObj_class_farmerlistDetails.setTalukaID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_TalukID")));
                    innerObj_class_farmerlistDetails.setVillageID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_VillageID")));
                    innerObj_class_farmerlistDetails.setPanchayatID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_GrampanchayatID")));
                    innerObj_class_farmerlistDetails.setFarmerID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerID")));
                    innerObj_class_farmerlistDetails.setFarmer_Code(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code")));
                    innerObj_class_farmerlistDetails.setFarmerFirstName(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName")));
                    innerObj_class_farmerlistDetails.setFarmerMiddleName(cursor1.getString(cursor1.getColumnIndex("FarmerMName_DB")));
                    innerObj_class_farmerlistDetails.setFarmerMiddleName(cursor1.getString(cursor1.getColumnIndex("FarmerLName_DB")));
                    innerObj_class_farmerlistDetails.setFarmerAge(cursor1.getString(cursor1.getColumnIndex("Farmerage_DB")));
                    innerObj_class_farmerlistDetails.setFarmerMobile(cursor1.getString(cursor1.getColumnIndex("Farmercellno_DB")));
                    innerObj_class_farmerlistDetails.setFarmerIncome(cursor1.getString(cursor1.getColumnIndex("FIncome_DB")));
                    innerObj_class_farmerlistDetails.setFarmerFamily(cursor1.getString(cursor1.getColumnIndex("Ffamilymember_DB")));
                    innerObj_class_farmerlistDetails.setFarmerIDType(cursor1.getString(cursor1.getColumnIndex("FIDprooftype_DB")));
                    innerObj_class_farmerlistDetails.setFarmerIDNumber(cursor1.getString(cursor1.getColumnIndex("FIDProofNo_DB")));
                    innerObj_class_farmerlistDetails.setStr_base64(cursor1.getString(cursor1.getColumnIndex("FarmerImageB64str_DB")));
                    innerObj_class_farmerlistDetails.setSubmittedDate(cursor1.getString(cursor1.getColumnIndex("Submitted_Date")));
                    innerObj_class_farmerlistDetails.setFarmer_Gender(cursor1.getString(cursor1.getColumnIndex("Farmer_Gender")));

                    class_farmerlistdetails_arrayObj[i] = innerObj_class_farmerlistDetails;
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }


        db_viewfarmerlist.close();

        Log.e("length", String.valueOf(class_farmerlistdetails_arrayObj.length));

        for(int j=0;j<class_farmerlistdetails_arrayObj.length;j++)
        {
            Log.e("tag","FarmerFirstName=="+class_farmerlistdetails_arrayObj[j].getFarmerFirstName());
          //  EditFarmerDetails(j);
            String FarmerID=class_farmerlistdetails_arrayObj[j].getFarmerID();
            String stateid=class_farmerlistdetails_arrayObj[j].getStateID();
            String districtid=class_farmerlistdetails_arrayObj[j].getDistrictID();
            String talukid=class_farmerlistdetails_arrayObj[j].getTalukaID();
            String panchayatid=class_farmerlistdetails_arrayObj[j].getPanchayatID();
            String villageid=class_farmerlistdetails_arrayObj[j].getVillageID();
            String fname=class_farmerlistdetails_arrayObj[j].getFarmerFirstName();
            String mname=class_farmerlistdetails_arrayObj[j].getFarmerMiddleName();
            String lname=class_farmerlistdetails_arrayObj[j].getFarmerLastName();
            String phonenumber=class_farmerlistdetails_arrayObj[j].getFarmerMobile();
            String idprooftyp=class_farmerlistdetails_arrayObj[j].getFarmerIDType();
            String idproofno=class_farmerlistdetails_arrayObj[j].getFarmerIDNumber();
            String farmerimage=class_farmerlistdetails_arrayObj[j].getFarmerPhoto();
            String age=class_farmerlistdetails_arrayObj[j].getFarmerAge();
            String annualincome=class_farmerlistdetails_arrayObj[j].getFarmerIncome();
            String familymembers=class_farmerlistdetails_arrayObj[j].getFarmerFamily();
            String submittedDateTime=class_farmerlistdetails_arrayObj[j].getSubmittedDate();
            String tempfarmerid=class_farmerlistdetails_arrayObj[j].getMobileTempID();
            String empId=class_farmerlistdetails_arrayObj[j].getCreatedBy();
            String Farmer_Gender=class_farmerlistdetails_arrayObj[j].getFarmer_Gender();

            EditFarmerDetails(FarmerID,stateid,districtid,talukid,panchayatid,villageid,fname,mname,lname,phonenumber,idprooftyp,idproofno,farmerimage,age,annualincome,familymembers,submittedDateTime,tempfarmerid,empId,Farmer_Gender);


        }


        if(x==0)
        {
            GetAutoSyncVersion();
            GetAppVersionCheck();
          //  AsyncTask_fetch_appversioncheck();
        }



    }

    /*private void EditFarmerDetails(final int j){
        AddFarmerRequest request = new AddFarmerRequest();
        request.setFarmerID(class_farmerprofileoffline_array_obj[j].getStr_farmerID());
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
        request.setCreatedBy("40");*/

    private void EditFarmerDetails(String FarmerID,String stateid,String districtid,String talukid,String panchayatid,String villageid,
                                  String fname,String mname,String lname,String phonenumber,String idprooftyp,String idproofno,String farmerimage,String age,String annualincome,String familymembers,String submittedDateTime,String tempfarmerid,String empId,String Farmer_Gender)
    {
        if(FarmerID.startsWith("temp")){
            Log.e("tag","FarmerID temp=="+FarmerID);
            FarmerID="0";
        }

        AddFarmerRequest request = new AddFarmerRequest();
        request.setFarmerID(FarmerID);
        request.setStateID(stateid);
        request.setDistrictID(districtid);
        request.setTalukaID(talukid);
        request.setPanchayatID(panchayatid);
        request.setVillageID(villageid);
        request.setFarmerFirstName(fname);
        request.setFarmerMiddleName(mname);
        request.setFarmerLastName(lname);
        request.setFarmerMobile(phonenumber);
        request.setFarmerIDType(idprooftyp);
        request.setFarmerIDNumber(idproofno);
        request.setFarmerPhoto(farmerimage);
        request.setFarmerAge(age);
        request.setFarmerIncome(annualincome);
        request.setFarmerFamily(familymembers);
        request.setSubmittedDate(submittedDateTime);
        request.setMobileTempID(tempfarmerid);
        request.setCreatedBy(str_employee_id);
        request.setFarmer_Gender(Farmer_Gender);

        //  Log.e("tag","FarmerFirstName=="+class_farmerprofileoffline_array_obj[j].getStr_fname());
      //  Log.e("tag","FarmerID=="+class_farmerprofileoffline_array_obj[j].getStr_farmerID());

        Call<AddFarmerResponse> call = userService1.AddFarmer(request);
        Log.e("TAG", "Request 33: "+new Gson().toJson(request) );
        Log.e("TAG", "Request edit: "+request.toString() );

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_MarketingHomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<AddFarmerResponse>()
        {
            @Override
            public void onResponse(Call<AddFarmerResponse> call, Response<AddFarmerResponse> response)
            {
           //     System.out.println("response "+response.body().toString());

                  /*  Log.e("response",response.toString());
                    Log.e("TAG", "response 33: "+new Gson().toJson(response) );
                    Log.e("response body", String.valueOf(response.body()));*/
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
                   /* Log.e("response new:",error1.getMsg());
                    Log.e("response new status:", String.valueOf(error1.getstatus()));*/
                // Log.e("response",Gson.fromJson(response.toString(),AddFarmer_Activity1.class));

                if(response.isSuccessful())
                {
                    AddFarmerResList addFarmerResList = response.body().getLst();
                    Log.e("tag","editFarmerResList NAme="+addFarmerResList.getFarmerFirstName());

                   /* String str_response_farmer_id=addFarmerResList.getFarmerID();
                    String str_response_farmercode=addFarmerResList.getFarmerCode();
                    String str_response_tempId=addFarmerResList.getMobileTempID();
                    Log.e("tag","getMobileTempID="+addFarmerResList.getMobileTempID());
                    Log.e("tag","getFarmerCode="+addFarmerResList.getFarmerCode());*/
                    String str_response_farmer_id = addFarmerResList.getFarmerID();
                    String str_response_farmercode = addFarmerResList.getFarmerCode();
                    String str_response_tempId = addFarmerResList.getMobileTempID();
                    Log.e("tag", "getMobileTempID=" + addFarmerResList.getMobileTempID());
                    Log.e("tag", "getFarmerCode=" + addFarmerResList.getFarmerCode());
                    Log.e("tag", "getFarmerID=" + addFarmerResList.getFarmerID());

                    SQLiteDatabase db_viewfarmerlist = getApplication().openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

                    db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                            "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                            "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                            "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                            "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                            "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                            "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");


                    ContentValues cv = new ContentValues();
                    cv.put("UploadedStatusFarmerprofile_DB",10);

                    db_viewfarmerlist.update("ViewFarmerListRest", cv, "DispFarmerTable_FarmerID = ?", new String[]{str_response_farmer_id});
                    db_viewfarmerlist.close();
                    progressDoalog.dismiss();
                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
                Log.e("TAG", "onFailure: "+t.toString() );

                Log.e("tag","Error:"+t.getMessage());
                Toast.makeText(Activity_MarketingHomeScreen.this, "WS:Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }





}// end of class
