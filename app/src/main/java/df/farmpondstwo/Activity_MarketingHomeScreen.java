package df.farmpondstwo;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import df.farmpondstwo.Models.AddFarmerRequest;
import df.farmpondstwo.Models.AddFarmerResList;
import df.farmpondstwo.Models.AddFarmerResponse;
import df.farmpondstwo.Models.Class_FarmerProfileOffline;
import df.farmpondstwo.Models.Class_farmponddetails;
import df.farmpondstwo.Models.DefaultResponse;
import df.farmpondstwo.Models.ErrorUtils;
import df.farmpondstwo.Models.Farmer;
import df.farmpondstwo.Models.GetAppVersion;
import df.farmpondstwo.Models.GetAppVersionList;
import df.farmpondstwo.remote.Class_ApiUtils;
import df.farmpondstwo.remote.Interface_userservice;
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

        if (isInternetPresent)
        {

        //working
         //fetch_DB_farmerprofile_offline_data();

          /* fetch_DB_edited_offline_data();
            fetch_DB_Edited_farmerprofile_offline_data();*/
        }
        else
        {
            //working

            /*fetch_DB_farmerprofile_offline_data_count();
            fetch_DB_edited_offline_data_count1();
            fetch_DB_New_pond_count();
*/
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


                   /* fetch_DB_farmerprofile_offline_data();
                    fetch_DB_edited_offline_data();*/

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



    /*
                       // Log.e("insize images", String.valueOf(class_farmerandpond_details_array_obj.size()));



                        str_farmpond_id=class_farmerandpond_details_array_obj[i].
                                getClass_farmponddetails_obj().get(j).getFarmpond_ID();
                        str_width=class_farmerandpond_details_array_obj[i].
                                getClass_farmponddetails_obj().get(j).getFarmpond_Width();
                        str_height=class_farmerandpond_details_array_obj[i].
                                getClass_farmponddetails_obj().get(j).getFarmpond_Height();
                        str_depth=class_farmerandpond_details_array_obj[i].
                                getClass_farmponddetails_obj().get(j).getFarmpond_Depth();

                        if (class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).
                                getClass_farmpondimages_obj().size() > 0)
                        {



                            for (int k = 0; k < class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().
                                    get(j).getClass_farmpondimages_obj().size(); k++)
                            {


                                str_imageid = class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).
                                        getClass_farmpondimages_obj().get(k).getImage_ID();
                                str_imageurl = class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).
                                        getClass_farmpondimages_obj().get(0).getImage_url();

                                String str_farmpondimageurl = str_farmpondbaseimage_url + str_imageurl;
                                Log.e("url", str_farmpondimageurl);

                                InputStream inputstream_obj = null;
                                try {
                                    inputstream_obj = new URL(str_farmpondimageurl).openStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                byte[] b = baos.toByteArray();
                                str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

                                DBCreate_Farmpondsedetails_2SQLiteDB(str_farmerid, str_farmername,str_farmpond_id,str_width,str_height,
                                        str_depth,str_imageid,str_base64imagestring );

                            }//for 3


                        }// if 2
                        else
                        {
                            DBCreate_Farmpondsedetails_2SQLiteDB(str_farmerid, str_farmername,str_farmpond_id,str_width,str_height,
                                    str_depth,"0","empty");
                        }

                    }// for 2

                }//if 1


            }

        }//try 1
            catch(Exception e)
            {
                Log.e("image Error", e.getMessage());
                e.printStackTrace();
            }
        }//*/

   public void  DBCreate_Farmpondsedetails_2SQLiteDB(String str_farmerid, String str_farmername,String str_farmpond_id,String str_width,
                                         String str_height,String str_depth,String str_imageid,String str_base64imagestring)
    {

        Log.e("dbFid",str_farmerid);
        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR," +
                "FNameDB VARCHAR,FMNameDB VARCHAR,FLNameDB VARCHAR,FYearIDDB VARCHAR,FStateIDDB VARCHAR,FDistrictIDDB VARCHAR," +
                "FTalukIDDB VARCHAR,FPanchayatIDDB VARCHAR,FVillageIDDB VARCHAR,FageDB VARCHAR,FphonenumberDB VARCHAR," +
                "FfamilymemberDB VARCHAR,FidproofnoIDDB VARCHAR,FphotoDB VARCHAR,FPondidDB VARCHAR,WidthDB VARCHAR," +
                "HeightDB VARCHAR,DepthDB VARCHAR,LatitudeDB VARCHAR,LongitudeDB VARCHAR,Imageid1DB VARCHAR,Image1Base64DB VARCHAR," +
                "Imageid2DB VARCHAR,Image2Base64DB VARCHAR,Imageid3DB VARCHAR,Image3Base64DB VARCHAR,EmployeeIDDB VARCHAR," +
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");

        String SQLiteQuery = "INSERT INTO FarmPondDetails_fromServerRest(FIDDB,FNameDB,FPondidDB,WidthDB,HeightDB,DepthDB,ImageidDB,ImageBase64DB,UploadedStatus)" +
                " VALUES ('"+str_farmerid+"','"+str_farmername+"','"+str_farmpond_id+"','"+str_width+"'," +
                "'"+str_height+"','"+str_depth+"','"+str_imageid+"','"+str_base64imagestring+"','"+str_employee_id+"','"+1+"');";
        db1.execSQL(SQLiteQuery);
        db1.close();
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
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");

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



        for(int j=0;j<class_farmerprofileoffline_array_obj.length;j++)
        {

            //AsyncTask_submit_farmerprofiledetails(j);
            Log.e("tag","Edit FarmerId =="+class_farmerprofileoffline_array_obj[j].getStr_farmerID());
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

    public void fetch_DB_edited_offline_data()
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
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatus='" + 1 + "'", null);
        int x = cursor1.getCount();


       // Notuploadedcount_edited_tv.setText("Pond Edited: "+String.valueOf(x));
        Notuploadedcount_edited_tv.setText(String.valueOf(x));


        Log.e("uploadstatus_count", String.valueOf(x));



        int i = 0;
        class_farmponddetails_offline_array_objRest = new Class_farmponddetails[x];
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

                    innerObj_Class_farmponddetails.setPondStatus(cursor1.getString(cursor1.getColumnIndex("UploadedStatus")));

                    innerObj_Class_farmponddetails.setPondStart(cursor1.getString(cursor1.getColumnIndex("StartDateDB")));

                    innerObj_Class_farmponddetails.setApprovalRemarks(cursor1.getString(cursor1.getColumnIndex("FPondRemarksDB")));
                    innerObj_Class_farmponddetails.setPondCollectedAmount(cursor1.getString(cursor1.getColumnIndex("FPondAmtTakenDB")));
                    //"FPondRemarksDB VARCHAR,FPondAmtTakenDB VARCHAR,FPondStatusDB VARCHAR," +

                    innerObj_Class_farmponddetails.setPondTempID(cursor1.getString(cursor1.getColumnIndex("TempFIDDB")));

                    innerObj_Class_farmponddetails.setPondCode(cursor1.getString(cursor1.getColumnIndex("FPondCodeDB")));

                    Log.e("class_farmpondcode",cursor1.getString(cursor1.getColumnIndex("FPondCodeDB")));
                    Log.e("class_farmpondID",cursor1.getString(cursor1.getColumnIndex("FPondidDB")));



                    class_farmponddetails_offline_array_objRest[i] = innerObj_Class_farmponddetails;
                    i++;


                    Log.e("editdays", cursor1.getString(cursor1.getColumnIndex("TotalDaysDB")));
                    Log.e("editconstructed", cursor1.getString(cursor1.getColumnIndex("ConstructedDateDB")));
                    Log.e("editpondcost", cursor1.getString(cursor1.getColumnIndex("PondCostDB")));
                    Log.e("editMcode", cursor1.getString(cursor1.getColumnIndex("McodeDB")));



                    Log.e("Farmerid", cursor1.getString(cursor1.getColumnIndex("FIDDB")));
                    Log.e("FarmerName", cursor1.getString(cursor1.getColumnIndex("FNameDB")));
                    Log.e("Imageid1DB", cursor1.getString(cursor1.getColumnIndex("Imageid1DB")));
                    Log.e("Imageid2DB", cursor1.getString(cursor1.getColumnIndex("Imageid2DB")));
                    Log.e("Imageid3DB", cursor1.getString(cursor1.getColumnIndex("Imageid3DB")));


                } while (cursor1.moveToNext());
            }//if ends

        }


        db1.close();

        Log.e("length", String.valueOf(class_farmponddetails_offline_array_objRest.length));



            for(int j=0;j<class_farmponddetails_offline_array_objRest.length;j++)
            {

               // Edit_farmponddetails(j);
           //    AsyncTask_submit_edited_farmponddetails(j);
            }


        if(x==0)
        {
        }




    }



    private void AddFarmerDetails(final int j){
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
        request.setCreatedBy(str_employee_id);
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
                progressDoalog.dismiss();
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
                            "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");



                    ContentValues cv = new ContentValues();
                    cv.put("FIDDB",str_response_farmer_id);
                /*cv.put("employee_id",str_response_image_id3);
                // employee_id*/
                    cv.put("UploadedStatusFarmerprofile",10);

                    db1.update("FarmPondDetails_fromServerRest", cv, "TempFIDDB = ?", new String[]{str_response_tempId});
                    db1.close();

                    SQLiteDatabase db_viewfarmerlist = getApplication().openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);


                    db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                            "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                            "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                            "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                            "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                            "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                            "LocalFarmerImg BLOB,Farmpondcount VARCHAR);");


                    ContentValues cv_farmelistupdate = new ContentValues();
                    cv_farmelistupdate.put("DispFarmerTable_Farmer_Code",str_response_farmercode);
                    cv_farmelistupdate.put("DispFarmerTable_FarmerID",str_response_farmer_id);


                    db_viewfarmerlist.update("ViewFarmerListRest", cv_farmelistupdate, "DispFarmerTable_Farmer_Code = ?", new String[]{str_response_tempId});
                    db_viewfarmerlist.close();

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
                Toast.makeText(Activity_MarketingHomeScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }


    private void AsyncTask_submit_edited_farmponddetails(final int j)
    {

        //{"image_id1":"4030","completion_status":6,"image_id2":"4031","image_id3":"4032","statusMessage":"Fail"}

        final ProgressDialog pdLoading = new ProgressDialog(Activity_MarketingHomeScreen.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String str_fetchfarmponddetails_url = Class_URL.URL_Edited_farmponddetails_offlineData.toString().trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        pdLoading.dismiss();

                        Log.e("volley_edited_response",response);//volley response: {"statusMessage":"success"}
                      parse_edited_uploaded_offlinedata_resp(response,class_farmponddetails_offline_array_obj[j].getFarmpond_Id());

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading.dismiss();
                        Toast.makeText(Activity_MarketingHomeScreen.this,"WS:"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {


                Map<String,String> params = new HashMap<String, String>();

                String str_imageID_1,str_imageID_2,str_imageID_3;
                String str_edit_lat,str_edit_long;

                str_edit_lat=str_edit_long="3.0";

                class_farmponddetails_offline_array_obj[j].getImage1_Base64();

                if(class_farmponddetails_offline_array_obj[j].getImage1_ID()!="0" ||
                        !(class_farmponddetails_offline_array_obj[j].getImage1_ID().equalsIgnoreCase("noimage1")))
                { str_imageID_1= class_farmponddetails_offline_array_obj[j].getImage1_ID();}
                else{ str_imageID_1="";}

                if(class_farmponddetails_offline_array_obj[j].getImage2_ID()!="0" ||
                        !(class_farmponddetails_offline_array_obj[j].getImage1_ID().equalsIgnoreCase("noimage2")))
                { str_imageID_2=class_farmponddetails_offline_array_obj[j].getImage2_ID();}
                else{ str_imageID_2="";}

                if(class_farmponddetails_offline_array_obj[j].getImage3_ID()!="0" ||
                        !(class_farmponddetails_offline_array_obj[j].getImage1_ID().equalsIgnoreCase("noimage3")))
                { str_imageID_3=class_farmponddetails_offline_array_obj[j].getImage3_ID();
                    str_edit_lat=class_farmponddetails_offline_array_obj[j].getLatitude();
                    str_edit_long=class_farmponddetails_offline_array_obj[j].getLongitude();
                }
                else{ str_imageID_3="";
                    str_edit_lat=str_edit_long="3.0";
                }

                Log.e("imageid1",str_imageID_1);
                Log.e("imageid2",str_imageID_2);
                Log.e("imageid3",str_imageID_3);


                try{


                    Log.e("employeeID param",class_farmponddetails_offline_array_obj[j].getEmployeeID());

                    Log.e("EditLat",class_farmponddetails_offline_array_obj[j].getLatitude());
                    Log.e("EditLong",class_farmponddetails_offline_array_obj[j].getLongitude());

                    params.put("Farmer_ID",class_farmponddetails_offline_array_obj[j].getfarmer_id());
                    params.put("Width",class_farmponddetails_offline_array_obj[j].getFarmpond_Width());
                    params.put("Height",class_farmponddetails_offline_array_obj[j].getFarmpond_Height());
                    params.put("Depth",class_farmponddetails_offline_array_obj[j].getFarmpond_Depth());

                    params.put("acres",class_farmponddetails_offline_array_obj[j].getFarmpond_acres());
                    params.put("gunta",class_farmponddetails_offline_array_obj[j].getFarmpond_gunta());
                    params.put("crop_before_farmpond"," ");
                    params.put("crop_after_farmpond"," ");

                    params.put("Latitude",class_farmponddetails_offline_array_obj[j].getLatitude());
                    params.put("Longitude",class_farmponddetails_offline_array_obj[j].getLongitude());

                    params.put("Farmpond_ID",class_farmponddetails_offline_array_obj[j].getFarmpond_Id());

                    params.put("image_id1",str_imageID_1);
                    params.put("image_id2",str_imageID_2);
                    params.put("image_id3",str_imageID_3);

                    params.put("image_link_1",class_farmponddetails_offline_array_obj[j].getImage1_Base64());
                    params.put("image_link_2",class_farmponddetails_offline_array_obj[j].getImage2_Base64());
                    params.put("image_link_3",class_farmponddetails_offline_array_obj[j].getImage3_Base64());

                    params.put("employee_id",class_farmponddetails_offline_array_obj[j].getEmployeeID());
                    params.put("submitted_date",class_farmponddetails_offline_array_obj[j].getSubmittedDateTime());

                    params.put("constructed_date",class_farmponddetails_offline_array_obj[j].getConstructedDate());
                    params.put("total_construction_days",class_farmponddetails_offline_array_obj[j].getTotal_no_days());
                    params.put("farmpond_constrction_cost",class_farmponddetails_offline_array_obj[j].getPondCost());
                    params.put("machine_code",class_farmponddetails_offline_array_obj[j].getMachineCode());
                    params.put("farmpond_startdate",class_farmponddetails_offline_array_obj[j].getStartDate());
                    params.put("Const_Remark_ID",class_farmponddetails_offline_array_obj[j].getFarmpond_remarks());
                    params.put("farmpond_const_amt_collected",class_farmponddetails_offline_array_obj[j].getFarmpond_amttaken());
                    //farmpond_const_amt_collected Const_Remark_ID

                    Log.e("startdate",class_farmponddetails_offline_array_obj[j].getStartDate().toString());

                }
                catch (Exception e)
                {
                    Log.e("upload error", e.getMessage());
                    e.printStackTrace();
                }

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("request",stringRequest.toString());
        requestQueue.add(stringRequest);
    }

    private void Edit_farmponddetails(int k)
    {

        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Class_addfarmponddetails_ToFromServer2 request=new Class_addfarmponddetails_ToFromServer2();
        String str_latitude,str_longitude;

        if(class_farmponddetails_offline_array_objRest[k].getPondImage3().equals("noimage3") ||
                class_farmponddetails_offline_array_objRest[k].getPondImage3().equals("0"))
        {
            str_latitude="";
            str_longitude="";
        }else{

            str_latitude= class_farmponddetails_offline_array_objRest[k].getPondLatitude();
            str_longitude= class_farmponddetails_offline_array_objRest[k].getPondLongitude();

        }

        Log.e("latitude",str_latitude);
        Log.e("Longitude",str_longitude);

        request.setPond_ID(class_farmponddetails_offline_array_objRest[k].getPondID());
        request.setFarmer_ID(class_farmponddetails_offline_array_objRest[k].getFarmerID());
        request.setAcademic_ID(class_farmponddetails_offline_array_objRest[k].getAcademicID());
        request.setMachine_ID(class_farmponddetails_offline_array_objRest[k].getMachineID());
        request.setPond_Latitude(str_latitude);
        request.setPond_Longitude(str_longitude);
        request.setPond_Length(class_farmponddetails_offline_array_objRest[k].getPondLength());
        request.setPond_Width(class_farmponddetails_offline_array_objRest[k].getPondWidth());
        request.setPond_Depth(class_farmponddetails_offline_array_objRest[k].getPondDepth());
        request.setPond_Start(class_farmponddetails_offline_array_objRest[k].getPondStart());
        request.setPond_End(class_farmponddetails_offline_array_objRest[k].getPondEnd());
        request.setPond_Days(class_farmponddetails_offline_array_objRest[k].getPondDays());
        request.setPond_Cost(class_farmponddetails_offline_array_objRest[k].getPondCost());
        request.setPond_Image_1(class_farmponddetails_offline_array_objRest[k].getPondImage1());
        request.setPond_Image_2(class_farmponddetails_offline_array_objRest[k].getPondImage2());
        request.setPond_Image_3(class_farmponddetails_offline_array_objRest[k].getPondImage3());
        request.setSubmitted_Date(class_farmponddetails_offline_array_objRest[k].getSubmittedDate());
        request.setCreated_By(class_farmponddetails_offline_array_objRest[k].getCreatedBy());
        request.setPond_Temp_ID(class_farmponddetails_offline_array_objRest[k].getPondTempID());
        request.setPond_Land_Gunta(class_farmponddetails_offline_array_objRest[k].getPondLandGunta());
        request.setPond_Land_Acre(class_farmponddetails_offline_array_objRest[k].getPondLandAcre());

        retrofit2.Call call = userService.Post_ActionFarmerPondData(request);




        call.enqueue(new Callback<Class_addfarmponddetails_ToFromServer1>()
        {
            @Override
            public void onResponse(retrofit2.Call<Class_addfarmponddetails_ToFromServer1> call, Response<Class_addfarmponddetails_ToFromServer1> response)
            {
                Class_addfarmponddetails_ToFromServer1  user_object1=response.body();


                Log.e("response",user_object1.getStatus().toString());
                Log.e("Addpondresponse",response.body().toString());

                Log.e("response",user_object1.getLst2().getPond_Cost());

                Log.e("response",response.toString());
                Log.e("TAG", "response 33: "+new Gson().toJson(response) );
                Log.e("response body", String.valueOf(response.body()));
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
                   /* Log.e("response new:",error1.getMsg());
                    Log.e("response new status:", String.valueOf(error1.getstatus()));*/
                // Log.e("response",Gson.fromJson(response.toString(),AddFarmer_Activity1.class));

                if(response.isSuccessful())
                {
                    //  progressDoalog.dismiss();
                    Class_addfarmponddetails_ToFromServer1  class_loginresponse = response.body();
                    Log.e("tag","res=="+class_loginresponse.toString());
                    if(class_loginresponse.getStatus().equals("true")) {

                        Class_addfarmponddetails_ToFromServer2 class_addfarmponddetails_toFromServer2 = response.body().getLst2();
                        Log.e("tag","class_editfarmponddetails_toFromServer2 farmerID="+class_addfarmponddetails_toFromServer2.getFarmer_ID());
                        Log.e("tag","class_edit farmponddetails_toFromServer2 PondID="+class_addfarmponddetails_toFromServer2.getPond_ID());

                    }else if(class_loginresponse.getStatus().equals("false")){
                        //     progressDoalog.dismiss();
                        Toast.makeText(Activity_MarketingHomeScreen.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    //   progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
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
                Toast.makeText(Activity_MarketingHomeScreen.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("response_error",t.getMessage().toString());
            }
        });
    }
    public void parse_edited_uploaded_offlinedata_resp(String response,String str_farmpond_id)
    {

        SQLiteDatabase db1=this.openOrCreateDatabase("PondDetails_DB", Context.MODE_PRIVATE, null);

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("statusMessage").equalsIgnoreCase("Success"))
            {



                String str_updated_imageId1,str_updated_imageId2,str_updated_imageId3,str_edit_completionStatus;
                str_edit_completionStatus="3";

                if(jsonObject.has("image_id1"))
                {
                    //str_updated_imageId1="0";
                    str_updated_imageId1= jsonObject.getString("image_id1");
                }
                else{
                    //str_updated_imageId1= jsonObject.getString("image_id1");
                    str_updated_imageId1="0";
                }

                if(jsonObject.has("image_id2"))
                {
                    str_updated_imageId2=jsonObject.getString("image_id2");
                }
                else{
                    str_updated_imageId2="0";
                }

                if(jsonObject.has("image_id3"))
                {
                    str_updated_imageId3=jsonObject.getString("image_id3");
                }
                else{
                    //str_updated_imageId3=jsonObject.getString("image_id3");
                    str_updated_imageId3="0";
                }

                if(jsonObject.has("completion_status"))
                {
                    str_edit_completionStatus=jsonObject.getString("completion_status");
                }



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



                if(str_edit_completionStatus.equalsIgnoreCase("novalue")||
                        str_edit_completionStatus.equalsIgnoreCase("0")){ str_edit_completionStatus="3"; }

                ContentValues cv = new ContentValues();
                cv.put("Imageid1DB",str_updated_imageId1);
                cv.put("Imageid2DB",str_updated_imageId2);
                cv.put("Imageid3DB",str_updated_imageId3);
                cv.put("FPondStatusDB",str_edit_completionStatus);
                //str_edit_completionStatus
                cv.put("UploadedStatus",0);

                db1.update("FarmPondDetails_fromServerRest", cv, "FPondidDB = ?", new String[]{str_farmpond_id});
                db1.close();

                fetch_DB_edited_offline_data_count1();
            }
            else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error",e.toString());
            db1.close();
        }

    }

    //Sync the data

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
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");

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

                    innerObj_Class_farmponddetails.setPondStatus(cursor1.getString(cursor1.getColumnIndex("UploadedStatus")));

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


        for(int k=0;k<newfarmponddetails_offline_array_objRest.length;k++)
        {


            if(x>0) {
              //  AsyncTask_submit_New_farmponddetails(k);
                Add_New_farmponddetails(k);
            }
        }

    }




  /*  private void AsyncTask_submit_New_farmponddetails(final int k)
    {

        final ProgressDialog pdLoading = new ProgressDialog(Activity_MarketingHomeScreen.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();



        String str_fetchfarmponddetails_url = Class_URL.URL_Add_farmponddetails_offlineData.toString().trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        pdLoading.dismiss();

                        Log.e("volley_newfarmpond_resp",response);//volley response: {"statusMessage":"success"}

                        int index = response.indexOf("{");
                        String Result = response.substring(index);

                     //   Log.e("result",Result);


                        parse_add_newfarmpond_offline_response(response,newfarmponddetails_offline_array_obj[k].getFarmpond_Id());

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading.dismiss();
                        Toast.makeText(Activity_MarketingHomeScreen.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>();

                String str_latitude,str_longitude;

                if(newfarmponddetails_offline_array_obj[k].getImage3_Base64().equalsIgnoreCase("noimage3") ||
                        newfarmponddetails_offline_array_obj[k].getImage3_Base64().equalsIgnoreCase("0"))
                {

                    str_latitude="";
                    str_longitude="";


                }else{

                    str_latitude= newfarmponddetails_offline_array_obj[k].getLatitude();
                    str_longitude= newfarmponddetails_offline_array_obj[k].getLongitude();

                }

                params.put("Farmer_ID",newfarmponddetails_offline_array_obj[k].getfarmer_id());
                params.put("Width",newfarmponddetails_offline_array_obj[k].getFarmpond_Width());
                params.put("Height",newfarmponddetails_offline_array_obj[k].getFarmpond_Height());
                params.put("Depth",newfarmponddetails_offline_array_obj[k].getFarmpond_Depth());

                *//*Log.e("latitude",newfarmponddetails_offline_array_obj[k].getLatitude());
                Log.e("Longitude",newfarmponddetails_offline_array_obj[k].getLongitude());*//*

                params.put("acres",newfarmponddetails_offline_array_obj[k].getFarmpond_acres());
                params.put("gunta",newfarmponddetails_offline_array_obj[k].getFarmpond_gunta());
                params.put("crop_before_farmpond","");
                params.put("crop_after_farmpond","");


                Log.e("latitude",str_latitude);
                Log.e("Longitude",str_longitude);

                *//*params.put("Latitude",newfarmponddetails_offline_array_obj[k].getLatitude());
                params.put("Longitude",newfarmponddetails_offline_array_obj[k].getLongitude());
*//*
                params.put("Latitude",str_latitude);
                params.put("Longitude",str_longitude);

                //str_latitude

                params.put("image_link_1",newfarmponddetails_offline_array_obj[k].getImage1_Base64());

                params.put("image_link_2",newfarmponddetails_offline_array_obj[k].getImage2_Base64().trim());
                params.put("image_link_3",newfarmponddetails_offline_array_obj[k].getImage3_Base64().trim());

                params.put("employee_id",newfarmponddetails_offline_array_obj[k].getEmployeeID());
                params.put("submitted_date",newfarmponddetails_offline_array_obj[k].getSubmittedDateTime());

                params.put("constructed_date",newfarmponddetails_offline_array_obj[k].getConstructedDate());
                params.put("total_construction_days",newfarmponddetails_offline_array_obj[k].getTotal_no_days());
                params.put("farmpond_constrction_cost",newfarmponddetails_offline_array_obj[k].getPondCost());
                params.put("machine_code",newfarmponddetails_offline_array_obj[k].getMachineCode());
                params.put("farmpond_startdate",newfarmponddetails_offline_array_obj[k].getStartDate());
                params.put("Const_Remark_ID",newfarmponddetails_offline_array_obj[k].getFarmpond_remarks());
                params.put("farmpond_const_amt_collected",newfarmponddetails_offline_array_obj[k].getFarmpond_amttaken());
                params.put("app_temp_fpcode",newfarmponddetails_offline_array_obj[k].getFarmpondCode());

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("request",stringRequest.toString());
        requestQueue.add(stringRequest);
    }*/

    private void Add_New_farmponddetails(int k)
    {


        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();


        Class_addfarmponddetails_ToFromServer2 request=new Class_addfarmponddetails_ToFromServer2();
        String str_latitude,str_longitude;

        if(newfarmponddetails_offline_array_objRest[k].getPondImage3().equals("noimage3") ||
                newfarmponddetails_offline_array_objRest[k].getPondImage3().equals("0"))
        {
            str_latitude="";
            str_longitude="";
        }else{

            str_latitude= newfarmponddetails_offline_array_objRest[k].getPondLatitude();
            str_longitude= newfarmponddetails_offline_array_objRest[k].getPondLongitude();

        }

        Log.e("latitude",str_latitude);
        Log.e("Longitude",str_longitude);

        request.setPond_ID(newfarmponddetails_offline_array_objRest[k].getPondID());
        request.setFarmer_ID(newfarmponddetails_offline_array_objRest[k].getFarmerID());
        request.setAcademic_ID(newfarmponddetails_offline_array_objRest[k].getAcademicID());
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
        request.setPond_Image_1(newfarmponddetails_offline_array_objRest[k].getPondImage1());
        request.setPond_Image_2(newfarmponddetails_offline_array_objRest[k].getPondImage2());
        request.setPond_Image_3(newfarmponddetails_offline_array_objRest[k].getPondImage3());
        request.setSubmitted_Date(newfarmponddetails_offline_array_objRest[k].getSubmittedDate());
        request.setCreated_By(newfarmponddetails_offline_array_objRest[k].getCreatedBy());
        request.setPond_Temp_ID(newfarmponddetails_offline_array_objRest[k].getPondTempID());
        request.setPond_Land_Gunta(newfarmponddetails_offline_array_objRest[k].getPondLandGunta());
        request.setPond_Land_Acre(newfarmponddetails_offline_array_objRest[k].getPondLandAcre());

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

                Log.e("response",response.toString());
                Log.e("TAG", "response 33: "+new Gson().toJson(response) );
                Log.e("response body", String.valueOf(response.body()));
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
                   /* Log.e("response new:",error1.getMsg());
                    Log.e("response new status:", String.valueOf(error1.getstatus()));*/
                // Log.e("response",Gson.fromJson(response.toString(),AddFarmer_Activity1.class));

                if(response.isSuccessful())
                {
                    //  progressDoalog.dismiss();
                    Class_addfarmponddetails_ToFromServer1  class_loginresponse = response.body();
                    Log.e("tag","res=="+class_loginresponse.toString());
                    if(class_loginresponse.getStatus().equals("true")) {

                        Class_addfarmponddetails_ToFromServer2 class_addfarmponddetails_toFromServer2 = response.body().getLst2();
                        Log.e("tag","class_addfarmponddetails_toFromServer2 farmerID="+class_addfarmponddetails_toFromServer2.getFarmer_ID());
                        Log.e("tag","class_addfarmponddetails_toFromServer2 PondID="+class_addfarmponddetails_toFromServer2.getPond_ID());

                    }else if(class_loginresponse.getStatus().equals("false")){
                        //     progressDoalog.dismiss();
                        Toast.makeText(Activity_MarketingHomeScreen.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    //   progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
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
                Toast.makeText(Activity_MarketingHomeScreen.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("response_error",t.getMessage().toString());
            }
        });
    }




    public void parse_add_newfarmpond_offline_response(String response,String str_farmpond_id)
    {
        try {

            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("statusMessage").equalsIgnoreCase("Success"))
            {

                String str_response_image_id3="0",str_response_image_id2="0",str_completionStatus="3";

                String str_response_farmpond_id=jsonObject.getString("farmpond_id");

                String str_response_image_id1=jsonObject.getString("image_id1");

                if(jsonObject.has("image_id2"))
                {
                 str_response_image_id2=jsonObject.getString("image_id2"); }


                if(jsonObject.has("image_id3"))
                {
                    str_response_image_id3=jsonObject.getString("image_id3");
                }
                if(jsonObject.has("completion_status"))
                {
                    str_completionStatus=jsonObject.getString("completion_status");
                }



                String str_response_farmpond_code=jsonObject.getString("farmpond_code");

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
                        "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");

                //{"farmpond_id":"1632","farmpond_code":"KA20-01027","image_id1":"1402","statusMessage":"success"}

                ContentValues cv = new ContentValues();
                cv.put("FPondidDB",str_response_farmpond_id);
                cv.put("Imageid1DB",str_response_image_id1);
                cv.put("Imageid2DB",str_response_image_id2);
                cv.put("Imageid3DB",str_response_image_id3);
                cv.put("FPondCodeDB",str_response_farmpond_code);
                cv.put("FPondStatusDB",str_completionStatus);


                /*cv.put("employee_id",str_response_image_id3);
                // employee_id*/
                cv.put("UploadedStatus",3);

                db1.update("FarmPondDetails_fromServerRest", cv, "FPondidDB = ?", new String[]{str_farmpond_id});
                db1.close();

                fetch_DB_New_pond_count();

            }
            else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error",e.toString());
        }

    }

    private void AsyncTask_submit_farmerprofiledetails(final int j)
    {

        final ProgressDialog pdLoading = new ProgressDialog(Activity_MarketingHomeScreen.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();





        String str_fetchfarmponddetails_url = Class_URL.URL_Add_farmerdetails.toString().trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        pdLoading.dismiss();

                        Log.e("volley profile response",response);//volley response: {"statusMessage":"success"}
                        parse_submitted_farmerprofiledetails_resp(response,class_farmerprofileoffline_array_obj[j].getStr_farmerID());

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading.dismiss();
                        Toast.makeText(Activity_MarketingHomeScreen.this,"WS:"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {


                Map<String,String> params = new HashMap<String, String>();




                try{


                    Log.e("employeeID param",class_farmerprofileoffline_array_obj[j].getStr_employeeid());

                    params.put("year_id",class_farmerprofileoffline_array_obj[j].getStr_yearid()); //
                    params.put("State",class_farmerprofileoffline_array_obj[j].getStr_stateid());
                    params.put("District",class_farmerprofileoffline_array_obj[j].getStr_districtid());
                    params.put("Taluk",class_farmerprofileoffline_array_obj[j].getStr_talukid());
                    params.put("Grampanchayat",class_farmerprofileoffline_array_obj[j].getStr_panchayatid());
                    params.put("Village",class_farmerprofileoffline_array_obj[j].getStr_villageid());
                    params.put("Firstname",class_farmerprofileoffline_array_obj[j].getStr_fname());
                    params.put("Middlename",class_farmerprofileoffline_array_obj[j].getStr_mname());
                    params.put("LastName",class_farmerprofileoffline_array_obj[j].getStr_lname());
                    params.put("Age",class_farmerprofileoffline_array_obj[j].getStr_age());
                    params.put("PhoneNumber",class_farmerprofileoffline_array_obj[j].getStr_phonenumber());
                    params.put("annual_income",class_farmerprofileoffline_array_obj[j].getStr_annualincome());
                    params.put("family_members",class_farmerprofileoffline_array_obj[j].getStr_familymembers());
                    params.put("Id_proof_type",class_farmerprofileoffline_array_obj[j].getStr_idprooftype());
                    params.put("id_proof_no",class_farmerprofileoffline_array_obj[j].getStr_idproofno());
                    params.put("image_link",class_farmerprofileoffline_array_obj[j].getStr_farmerimage());

                    params.put("employee_id",class_farmerprofileoffline_array_obj[j].getStr_employeeid());

                    params.put("submitted_date",class_farmerprofileoffline_array_obj[j].getStr_submittedDateTime());



                }
                catch (Exception e)
                {
                    Log.e("upload error", e.getMessage());
                    e.printStackTrace();
                }

                //Log.e("image", arraylist_image1_base64.get(0).toString());
                //  Log.e("Edit request", String.valueOf(params));
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("request",stringRequest.toString());
        requestQueue.add(stringRequest);
    }




    public void parse_submitted_farmerprofiledetails_resp(String response,String str_farmer_id)
    {

        try {
            JSONObject jsonObject = new JSONObject(response);


            if (jsonObject.getString("statusMessage").equalsIgnoreCase("Success"))
            {

                String str_response_farmer_id=jsonObject.getString("farmer_id");



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
                        "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");

                ContentValues cv = new ContentValues();
                cv.put("FIDDB",str_response_farmer_id);
                /*cv.put("employee_id",str_response_image_id3);
                // employee_id*/
                cv.put("UploadedStatusFarmerprofile",10);

                db1.update("FarmPondDetails_fromServerRest", cv, "TempFIDDB = ?", new String[]{str_farmer_id});
                db1.close();

                SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);


                db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                        "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                        "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                        "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                        "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                        "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                        "LocalFarmerImg BLOB,Farmpondcount VARCHAR);");


                ContentValues cv_farmelistupdate = new ContentValues();
                cv_farmelistupdate.put("DispFarmerTable_Farmer_Code",str_response_farmer_id);
                cv_farmelistupdate.put("DispFarmerTable_FarmerID",str_response_farmer_id);


                db_viewfarmerlist.update("ViewFarmerList", cv_farmelistupdate, "DispFarmerTable_Farmer_Code = ?", new String[]{str_farmer_id});
                db_viewfarmerlist.close();


                fetch_DB_farmerprofile_offline_data_count();
                fetch_DB_newfarmpond_offline_data();



            }
            else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error",e.toString());
        }

    }







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
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatusFarmerprofile='" + 9 + "'", null);
        int x = cursor1.getCount();

        Log.e("profilecount", String.valueOf(x));


       // farmerprofile_count_tv.setText("Farmer Profile: " + String.valueOf(x));
        farmerprofile_count_tv.setText(String.valueOf(x));

    }

    public void fetch_DB_edited_offline_data_count1()
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
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatus='" + 1 + "'", null);
        int x = cursor1.getCount();


      //  Notuploadedcount_edited_tv.setText("Edited:" + String.valueOf(x));
        Notuploadedcount_edited_tv.setText(String.valueOf(x));

        Log.e("uploadstatus_count", String.valueOf(x));

    }

    public void fetch_DB_New_pond_count()
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
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatus='" + 2 + "'", null);
        int x = cursor1.getCount();

       // Notuploadedcount_new_tv.setText("NewPond:"+String.valueOf(x));
        Notuploadedcount_new_tv.setText( String.valueOf(x));
        Log.e("newuploadstatus_count", String.valueOf(x));

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
                            alerts_dialog();
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
                Toast.makeText(Activity_MarketingHomeScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void AsyncTask_fetch_appversioncheck()
    {

        final ProgressDialog pdLoading_fetch_appversioncheck = new ProgressDialog(Activity_MarketingHomeScreen.this);

       // pdLoading_fetch_appversioncheck = new ProgressDialog(Activity_MarketingHomeScreen.this);
        pdLoading_fetch_appversioncheck.setMessage("\tLoading...");
        pdLoading_fetch_appversioncheck.setCancelable(false);
        pdLoading_fetch_appversioncheck.show();



        String str_fetchfarmponddetails_url = Class_URL.URL_appreleasedate.toString().trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {


                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.e("volley_releaseresponse",response);

                        parse_each_versionresponse(response);

                        pdLoading_fetch_appversioncheck.dismiss();

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading_fetch_appversioncheck.dismiss();
                        Log.e("volley_releaseresponse",error.toString());

                        Toast.makeText(Activity_MarketingHomeScreen.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                } )
        {
            /*@Override
            protected Map<String,String> getParams()
            {


                Map<String,String> params = new HashMap<String, String>();

                try{
                    params.put("employee_id",str_employee_id); //
                }
                catch (Exception e)
                {
                    Log.e("upload error", e.getMessage());
                    e.printStackTrace();
                }

                //Log.e("image", arraylist_image1_base64.get(0).toString());
                Log.e("Edit request", String.valueOf(params));
                return params;

            }*/
        };




        stringRequest.setRetryPolicy(new DefaultRetryPolicy(9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void parse_each_versionresponse(String response)
    {

        try {

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("StatusMessage").equalsIgnoreCase("Success"))
            {
                String str_releaseVer=jsonObject.getString("Release_version");

                int int_versioncode= Integer.parseInt(versioncode);
                int int_releaseVer= Integer.parseInt(str_releaseVer);

               if(int_releaseVer>int_versioncode)
               {
                   //call pop
                   Log.e("popup","popup");
                   //alerts();
                   alerts_dialog();
               }
               else{

               }


            }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error_machine", e.toString());
        }

    }//parse_machine_response

    public  void alerts_dialog()
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

    public void fetch_DB_Edited_farmerprofile_offline_data()
    {


        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");



        Cursor cursor1 = db_viewfarmerlist.rawQuery("SELECT * FROM ViewFarmerListRest WHERE UploadedStatusFarmerprofile_DB='" + 9 + "'", null);
        int x = cursor1.getCount();

        Log.e("edit_farmercount", String.valueOf(x));



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
            String empId="40";

              EditFarmerDetails(FarmerID,stateid,districtid,talukid,panchayatid,villageid,fname,mname,lname,phonenumber,idprooftyp,idproofno,farmerimage,age,annualincome,familymembers,submittedDateTime,tempfarmerid,empId);

            //  AsyncTask_submit_farmerEdited_profiledetails(j);
        }


        if(x==0)
        {
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
                                  String fname,String mname,String lname,String phonenumber,String idprooftyp,String idproofno,String farmerimage,String age,String annualincome,String familymembers,String submittedDateTime,String tempfarmerid,String empId)
    {

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

                    String str_response_farmer_id=addFarmerResList.getFarmerID();
                    String str_response_farmercode=addFarmerResList.getFarmerCode();
                    String str_response_tempId=addFarmerResList.getMobileTempID();
                    Log.e("tag","getMobileTempID="+addFarmerResList.getMobileTempID());
                    Log.e("tag","getFarmerCode="+addFarmerResList.getFarmerCode());

                    SQLiteDatabase db_viewfarmerlist = getApplication().openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

                    db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                            "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                            "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                            "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                            "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                            "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                            "LocalFarmerImg BLOB,Farmpondcount VARCHAR);");


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
                Toast.makeText(Activity_MarketingHomeScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }

    private void AsyncTask_submit_farmerEdited_profiledetails(final int j)
    {

        final ProgressDialog pdLoading = new ProgressDialog(Activity_MarketingHomeScreen.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String str_submit_editedfarmerdetails_url = Class_URL.URL_submit_edited_farmerdetails.trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_submit_editedfarmerdetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        pdLoading.dismiss();

                        Log.e("editprofile_response",response);//volley response: {"statusMessage":"success"}
                        parse_submitted_editedfarmerprofiledetails_resp(response,class_farmerlistdetails_arrayObj[j].getFarmerID());

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading.dismiss();
                        Toast.makeText(Activity_MarketingHomeScreen.this,"WS:"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {

                Map<String,String> params = new HashMap<String, String>();


                String str_submiteddate;

                try{

                    Class_CurrentDateDDMMYYYY date_obj= new Class_CurrentDateDDMMYYYY();
                    str_submiteddate=date_obj.currentdate();

                   // Log.e("employeeID param",class_farmerlistdetails_arrayObj[j].getStr_employeeid());

                   // params.put("year_id",class_farmerlistdetails_arrayObj[j].getStr_yearid()); //
                    params.put("State",class_farmerlistdetails_arrayObj[j].getStateID());
                    params.put("District",class_farmerlistdetails_arrayObj[j].getDistrictID());
                    params.put("Taluk",class_farmerlistdetails_arrayObj[j].getTalukaID());
                    params.put("Grampanchayat",class_farmerlistdetails_arrayObj[j].getPanchayatID());
                    params.put("Village",class_farmerlistdetails_arrayObj[j].getVillageID());
                    params.put("Firstname",class_farmerlistdetails_arrayObj[j].getFarmerFirstName());
                    params.put("Middlename",class_farmerlistdetails_arrayObj[j].getFarmerMiddleName());
                    params.put("LastName",class_farmerlistdetails_arrayObj[j].getFarmerLastName());
                    params.put("Age",class_farmerlistdetails_arrayObj[j].getFarmerAge());
                    params.put("PhoneNumber",class_farmerlistdetails_arrayObj[j].getFarmerMobile());
                    params.put("family_members",class_farmerlistdetails_arrayObj[j].getFarmerFamily());
                    params.put("annual_income",class_farmerlistdetails_arrayObj[j].getFarmerIncome());

                    params.put("image_link",class_farmerlistdetails_arrayObj[j].getStr_base64());
                    params.put("Id_proof_type",class_farmerlistdetails_arrayObj[j].getFarmerIDType());
                    params.put("id_proof_no",class_farmerlistdetails_arrayObj[j].getFarmerIDNumber());

                    params.put("employee_id",str_employee_id);
                    params.put("Farmer_ID",class_farmerlistdetails_arrayObj[j].getFarmerID());

                    params.put("submitted_date",str_submiteddate);

                }
                catch (Exception e)
                {
                    Log.e("upload error", e.getMessage());
                    e.printStackTrace();
                }

                //Log.e("image", arraylist_image1_base64.get(0).toString());
                //  Log.e("Edit request", String.valueOf(params));
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("request",stringRequest.toString());
        requestQueue.add(stringRequest);
    }

    public void parse_submitted_editedfarmerprofiledetails_resp(String response,String str_farmer_id)
    {

        //{"statusMessage":"Success"}
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("statusMessage").equalsIgnoreCase("Success"))
            {
                SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

                db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                        "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                        "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                        "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                        "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                        "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                        "LocalFarmerImg BLOB,Farmpondcount VARCHAR);");


                ContentValues cv = new ContentValues();
                cv.put("UploadedStatusFarmerprofile_DB",10);

                db_viewfarmerlist.update("ViewFarmerListRest", cv, "DispFarmerTable_FarmerID = ?", new String[]{str_farmer_id});
                db_viewfarmerlist.close();




            }
            else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("editerror",e.toString());
            Toast.makeText(Activity_MarketingHomeScreen.this,"editprofile:"+e.toString(),Toast.LENGTH_LONG).show();
        }


    }

}// end of class
