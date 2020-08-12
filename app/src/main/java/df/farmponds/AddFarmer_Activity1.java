package df.farmponds;

import android.app.Activity;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import df.farmponds.Models.AddFarmerRequest;
import df.farmponds.Models.AddFarmerRequestTest;
import df.farmponds.Models.AddFarmerResList;
import df.farmponds.Models.AddFarmerResponse;
import df.farmponds.Models.Class_FarmerProfileOffline;
import df.farmponds.Models.DefaultResponse;
import df.farmponds.Models.District;
import df.farmponds.Models.ErrorUtils;
import df.farmponds.Models.Farmer;
import df.farmponds.Models.Panchayat;
import df.farmponds.Models.State;
import df.farmponds.Models.Taluka;
import df.farmponds.Models.Village;
import df.farmponds.Models.Year;
import df.farmponds.remote.Class_ApiUtils;
import df.farmponds.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFarmer_Activity1 extends AppCompatActivity {


    Year[] arrayObj_Class_yearDetails, arrayObj_Class_yearDetails2;
    Year Obj_Class_yearDetails;
    State[] arrayObj_Class_stateDetails, arrayObj_Class_stateDetails2;
    State Obj_Class_stateDetails;
    District[] arrayObj_Class_DistrictListDetails, arrayObj_Class_DistrictListDetails2;
    District Obj_Class_DistrictDetails;
    Taluka[] arrayObj_Class_TalukListDetails, arrayObj_Class_TalukListDetails2;
    Taluka Obj_Class_TalukDetails;
    Village[] arrayObj_Class_VillageListDetails, arrayObj_Class_VillageListDetails2;
    Village Obj_Class_VillageListDetails;
    Panchayat[] arrayObj_Class_GrampanchayatListDetails, arrayObj_Class_GrampanchayatListDetails2;
    Panchayat Obj_Class_GramanchayatDetails;
    String selected_year, sp_stryear_ID, sp_strstate_ID, selected_district, selected_stateName, sp_strdistrict_ID, sp_strdistrict_state_ID, sp_strTaluk_ID, selected_taluk, sp_strVillage_ID, selected_village, sp_strgrampanchayat_ID, selected_grampanchayat;


    Spinner yearlist_farmers_sp, statelist_farmers_sp, districtlist_farmers_sp, taluklist_farmers_sp, grampanchayatlist_farmers_sp, villagelist_farmers_sp, selectidproof_sp;
    String[] ary_str_idproof = {"AdharaCard", "Driving License", "Ration Card", "Voter Id", "Not Available"};
    EditText farmerfirstname_et, farmerlastname_et, farmermiddlename_et, farmerage_et, farmercellno_et, farmerannualincome_et, familymember_et, farmeridno_et;

    String str_idproof_type, str_idproof_no;

    Button submit_farmerdetails_bt;

    ArrayList<String> arraylist_farmerimage_base64 = new ArrayList<>();

    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    String str_tempfarmerid;

    public static ImageView add_farmerimage_iv;
    ImageButton removefarmerimage_ib;
    Toolbar toolbar;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String str_base64imagestring = "";
    String str_image1;

    Interface_userservice userService1;

    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_employee_id;

    String str_farmerid, str_tempfid;

    Boolean digitalcamerabuttonpressed = false;
    String str_img = "";
    String mCurrentPhotoPath = "";
    Bitmap bitmap;
    String path;
    byte[] signimageinbytesArray = {0};

    String str_submitteddatetime;

    int statepos = 0, districtpos = 0, talukpos = 0, grampanchayatpos = 0, villagepos = 0;

    String str_editingfarmerprofile;
    String str_farmerID_foredit;

    // Class_farmprofiledetails class_farmprofiledetails_obj;
    Farmer class_farmerListDetails_obj;
    ArrayList<Bitmap> arrayList_bitmap_farmerimage;


    Class_FarmerProfileOffline[] class_farmerprofileoffline_array_obj;
    Class_FarmerProfileOffline class_farmerprofileoffline_obj;

    File file_img;
    File file_imageFile;
    public static String imageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farmer);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Farmer Details");
        getSupportActionBar().setTitle("");

        sp_stryear_ID = sp_strstate_ID = sp_strdistrict_ID = sp_strTaluk_ID = sp_strgrampanchayat_ID = sp_strVillage_ID = "-1";
        arraylist_farmerimage_base64.clear();
        str_image1 = "false";

        userService1 = Class_ApiUtils.getUserService();

        statepos = Class_SaveSharedPreference.getPREF_stateposition(AddFarmer_Activity1.this);
        districtpos = Class_SaveSharedPreference.getPREF_districtposition(AddFarmer_Activity1.this);
        talukpos = Class_SaveSharedPreference.getPREF_talukposition(AddFarmer_Activity1.this);
        grampanchayatpos = Class_SaveSharedPreference.getPREF_gramanchayatposition(AddFarmer_Activity1.this);
        villagepos = Class_SaveSharedPreference.getPREF_villageposition(AddFarmer_Activity1.this);

        yearlist_farmers_sp = (Spinner) findViewById(R.id.yearlist_farmers_sp);
        statelist_farmers_sp = (Spinner) findViewById(R.id.statelist_farmers_sp);
        districtlist_farmers_sp = (Spinner) findViewById(R.id.districtlist_farmers_sp);
        taluklist_farmers_sp = (Spinner) findViewById(R.id.taluklist_farmers_sp);
        grampanchayatlist_farmers_sp = (Spinner) findViewById(R.id.grampanchayatlist_farmers_sp);
        villagelist_farmers_sp = (Spinner) findViewById(R.id.villagelist_farmers_sp);
        selectidproof_sp = (Spinner) findViewById(R.id.selectidproof_sp);

        farmerfirstname_et = (EditText) findViewById(R.id.farmerfirstname_et);
        farmermiddlename_et = (EditText) findViewById(R.id.farmermiddlename_et);
        farmerlastname_et = (EditText) findViewById(R.id.farmerlastname_et);
        farmerage_et = (EditText) findViewById(R.id.farmerage_et);
        farmercellno_et = (EditText) findViewById(R.id.farmercellno_et);
        farmerannualincome_et = (EditText) findViewById(R.id.farmerannualincome_et);
        familymember_et = (EditText) findViewById(R.id.familymember_et);
        farmeridno_et = (EditText) findViewById(R.id.farmeridno_et);
        add_farmerimage_iv = (ImageView) findViewById(R.id.add_farmerimage_iv);

        removefarmerimage_ib = (ImageButton) findViewById(R.id.removefarmerimage_ib);
    /*    Button submit_farmerdetails_bt_temp=(Button)findViewById(R.id.submit_farmerdetails_bt_temp);

        submit_farmerdetails_bt_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FarmerID="0";
                String stateid="29";
                String districtid="528";
                String talukid="5500";
                String panchayatid="216105";
                String villageid="604936";
                String fname="MAdhu";
                String mname="k";
                String lname="k";
                String phonenumber="9876543210";

                String idprooftyp="9";
                String idproofno="9";
                String farmerimage="";
                String age="20";
                String annualincome="50000";
                String familymembers="6";
                String submittedDateTime="03-06-2020";
                String tempfarmerid="1";
                String empId="40";

                AddFarmerDetails(FarmerID,stateid,districtid,talukid,panchayatid,villageid,fname,mname,lname,phonenumber,idprooftyp,idproofno,farmerimage,age,annualincome,familymembers,submittedDateTime,tempfarmerid,empId);
             //   AddFarmerDetailsNew();
            }
        });*/

        submit_farmerdetails_bt = (Button) findViewById(R.id.submit_farmerdetails_bt);

        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_employee_id = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();


       /* ArrayAdapter<String> dataAdapter_idproof = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ary_str_idproof);
        dataAdapter_idproof.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectidproof_sp.setAdapter(dataAdapter_idproof);*/

        str_editingfarmerprofile = "no";

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ary_str_idproof);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        selectidproof_sp.setAdapter(aa);


        uploadfromDB_Yearlist();
       uploadfromDB_Statelist();
        uploadfromDB_Districtlist();
        uploadfromDB_Taluklist();
        uploadfromDB_Villagelist();
        uploadfromDB_Grampanchayatlist();

        //Toast.makeText(getApplicationContext(),"in Activity1",Toast.LENGTH_SHORT).show();

        selectidproof_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (selectidproof_sp.getSelectedItem().toString().equalsIgnoreCase("AdharaCard")) {
                    str_idproof_type = "1";
                }
                if (selectidproof_sp.getSelectedItem().toString().equalsIgnoreCase("Driving License")) {
                    str_idproof_type = "2";
                }
                if (selectidproof_sp.getSelectedItem().toString().equalsIgnoreCase("Ration Card")) {
                    str_idproof_type = "3";
                }
                if (selectidproof_sp.getSelectedItem().toString().equalsIgnoreCase("Voter Id")) {
                    str_idproof_type = "4";
                }

                if (selectidproof_sp.getSelectedItem().toString().equalsIgnoreCase("Not Available")) {
                    str_idproof_type = " ";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        yearlist_farmers_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_yearDetails = (Year) yearlist_farmers_sp.getSelectedItem();
                sp_stryear_ID = Obj_Class_yearDetails.getAcademic_ID().toString();
                selected_year = yearlist_farmers_sp.getSelectedItem().toString();
                int sel_yearsp_new = yearlist_farmers_sp.getSelectedItemPosition();

                //  farmerListViewAdapter.notifyDataSetChanged();

               /* if(sel_yearsp_new!=sel_yearsp)
                {
                    sel_yearsp=sel_yearsp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
                    statelist_SP.setSelection(0);
                    districtlist_SP.setSelection(0);
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        statelist_farmers_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Obj_Class_stateDetails = (State) statelist_farmers_sp.getSelectedItem();
                sp_strstate_ID = Obj_Class_stateDetails.getStateID().toString();
                selected_stateName = statelist_farmers_sp.getSelectedItem().toString();
                int sel_statesp_new = statelist_farmers_sp.getSelectedItemPosition();

                Update_districtid_spinner(sp_strstate_ID);
               /* if(sel_statesp_new!=sel_statesp)
                {
                    sel_statesp=sel_statesp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
                    districtlist_SP.setSelection(0);
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        districtlist_farmers_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_DistrictDetails = (District) districtlist_farmers_sp.getSelectedItem();
                sp_strdistrict_ID = Obj_Class_DistrictDetails.getDistrictID();
                sp_strdistrict_state_ID = Obj_Class_DistrictDetails.getStateID();
                selected_district = districtlist_farmers_sp.getSelectedItem().toString();
                int sel_districtsp_new = districtlist_farmers_sp.getSelectedItemPosition();
                // Log.e("selected_district", " : " + selected_district);
//                Log.i("sp_strdistrict_state_ID", " : " + sp_strdistrict_state_ID);
                Log.e("sp_strdistrict_ID", " : " + sp_strdistrict_ID);
                // Log.i("sp_strstate_ID", " : " + sp_strstate_ID);


                // Update_TalukId_spinner("5623");

                Update_TalukId_spinner(sp_strdistrict_ID);
                // Update_GramPanchayatID_spinner(sp_strdistrict_ID);

                /*if(sel_districtsp_new!=sel_districtsp) {
                    sel_districtsp=sel_districtsp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        taluklist_farmers_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_TalukDetails = (Taluka) taluklist_farmers_sp.getSelectedItem();
                sp_strTaluk_ID = Obj_Class_TalukDetails.getTalukaID();
                selected_taluk = taluklist_farmers_sp.getSelectedItem().toString();
                int sel_taluksp_new = taluklist_farmers_sp.getSelectedItemPosition();

                // Update_VillageId_spinner("5433");//5516,sp_strTaluk_ID
//                Log.i("selected_taluk", " : " + selected_taluk);
//
//                Log.e("sp_stryear_ID..", sp_stryear_ID);
//                Log.e("sp_strstate_ID..", sp_strstate_ID);
//                Log.e("sp_strdistrict_ID..", sp_strdistrict_ID);
                //Log.e("sp_strTaluk_ID..", sp_strTaluk_ID);

                // Update_VillageId_spinner(sp_strTaluk_ID);
                Update_GramPanchayatID_spinner(sp_strTaluk_ID);

               /* if(sel_taluksp_new!=sel_taluksp) {
                    sel_taluksp=sel_taluksp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();

                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        grampanchayatlist_farmers_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_GramanchayatDetails = (Panchayat) grampanchayatlist_farmers_sp.getSelectedItem();
                sp_strgrampanchayat_ID = Obj_Class_GramanchayatDetails.getPanchayatID().toString();
                selected_grampanchayat = Obj_Class_GramanchayatDetails.getPanchayatName().toString();
                int sel_grampanchayatsp_new = grampanchayatlist_farmers_sp.getSelectedItemPosition();


                Log.e("sp_strpachayat_ID", sp_strgrampanchayat_ID);

                Update_VillageId_spinner(sp_strgrampanchayat_ID);

                /*if(sel_grampanchayatsp_new!=sel_grampanchayatsp)
                {
                    sel_grampanchayatsp=sel_grampanchayatsp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        villagelist_farmers_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_VillageListDetails = (Village) villagelist_farmers_sp.getSelectedItem();
                sp_strVillage_ID = Obj_Class_VillageListDetails.getVillageID();
                selected_village = villagelist_farmers_sp.getSelectedItem().toString();

                int sel_villagesp_new = villagelist_farmers_sp.getSelectedItemPosition();

                /*if(sel_villagesp_new!=sel_villagesp) {
                    sel_villagesp=sel_villagesp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();

                    // grampanchayatlist_SP.setSelection(0);
                }
*/

                Log.e("yearselected", villagelist_farmers_sp.getSelectedItem().toString());


                //  Update_ids_farmerlist_listview(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strgrampanchayat_ID);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        str_farmerID_foredit = "0";
        Intent intent = getIntent();


        if ((intent.equals(null))) {
        } else {
            str_farmerID_foredit = intent.getStringExtra("farmerID");

            //if (categoryId !=null && !categoryId.isEmpty())
            // if (str_farmerID_foredit.equals(null)||str_farmerID_foredit.isEmpty()) {

            if (str_farmerID_foredit == null || str_farmerID_foredit.isEmpty()) {
                Log.e("null", "null");
            } else {
                Log.e("farmerid", str_farmerID_foredit);

                Data_from_FarmerDetails_DB(str_farmerID_foredit);
            }

        }


        submit_farmerdetails_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File imagefile;

                Log.e("YearID", sp_stryear_ID);
                Log.e("stateID", sp_strstate_ID);
                Log.e("districtID", sp_strdistrict_ID);
                Log.e("talukID", sp_strTaluk_ID);
                Log.e("grampanchayat", sp_strgrampanchayat_ID);
                Log.e("village", sp_strVillage_ID);


                str_tempfarmerid = "tempnewfarmerid" + String.valueOf(System.currentTimeMillis());


                if (digitalcamerabuttonpressed) {
                    imagefile = new File(CameraPhotoCapture.compressedfilepaths);
                    Bitmap myBitmap = BitmapFactory.decodeFile(imagefile.getAbsolutePath());


                    path = imagefile.getAbsolutePath();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    signimageinbytesArray = stream.toByteArray();
                    str_img = Base64.encodeToString(signimageinbytesArray, Base64.DEFAULT);
                    Log.e("byteArray", "byteArray" + Arrays.toString(signimageinbytesArray));
                    // Log.e("str_img  " , str_img);
                    digitalcamerabuttonpressed = false;

                    arraylist_farmerimage_base64.clear();
                    arraylist_farmerimage_base64.add(str_img);
                } else {
                    signimageinbytesArray = null;
                }


                if (validation()) {

                   /* internetDectector = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector.isConnectingToInternet();

                    if(isInternetPresent)
                    {

                        AsyncTask_Add_farmponddetails();


                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Check Internet Connection",Toast.LENGTH_SHORT).show();
                    }*/
//-----------------------------------------------------

                    if (str_editingfarmerprofile.equalsIgnoreCase("yes")) {
                        update_farmerdetails_into_ViewFarmerList();
                    } else {

                        int duplicatecount = checkduplicate_farmerdetails_into_ViewFarmerList();

                        if (duplicatecount > 0) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Already Farmer Details Present", Toast.LENGTH_LONG);
                            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                            toastMessage.setTextColor(Color.RED);
                            toast.show();
                        } else {
                            insert_farmerdetails_into_ViewFarmerList();
                        }

                    }

                }



               /* DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(sp_stryear_ID,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,sp_strVillage_ID, sp_strgrampanchayat_ID,
                        str_farmerid,"1",farmerfirstname_et, String str_farmerimage);*/

                // public void DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(String str_yearID, String str_stateID, String str_districtID, String str_talukid, String str_villageid, String str_grampanchayatid, String str_farmerid, String str_farmercode, String str_farmername, String str_farmerimage) {

            }
        });


        add_farmerimage_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_image1 = "true";
                //selectImage();
                selectImage1();
            }

        });


        removefarmerimage_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist_farmerimage_base64.clear();
                removefarmerimage_ib.setVisibility(View.GONE);
                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                add_farmerimage_iv.setImageDrawable(res);
            }
        });


    }// end of oncreate();


    // spinner insertion from DB

    public void uploadfromDB_Yearlist() {


        SQLiteDatabase db_year = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_year.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR);");
        Cursor cursor = db_year.rawQuery("SELECT DISTINCT * FROM YearListRest", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_yearDetails2 = new Year[x];
        if (cursor.moveToFirst()) {

            do {
                Year innerObj_Class_yearList = new Year();
                innerObj_Class_yearList.setAcademic_ID(cursor.getString(cursor.getColumnIndex("YearID")));
                innerObj_Class_yearList.setAcademic_Name(cursor.getString(cursor.getColumnIndex("YearName")));


                arrayObj_Class_yearDetails2[i] = innerObj_Class_yearList;
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_year.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_yearDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            yearlist_farmers_sp.setAdapter(dataAdapter);
            /*if(x>sel_yearsp) {
                yearlist_SP.setSelection(sel_yearsp);
            }*/
        }

    }

    public void uploadfromDB_Statelist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new State[x];
        if (cursor1.moveToFirst()) {

            do {
                State innerObj_Class_SandboxList = new State();
                innerObj_Class_SandboxList.setStateID(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_SandboxList.setStateName(cursor1.getString(cursor1.getColumnIndex("StateName")));
                innerObj_Class_SandboxList.setYearID(cursor1.getString(cursor1.getColumnIndex("state_yearid")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            statelist_farmers_sp.setAdapter(dataAdapter);
            statelist_farmers_sp.setSelection(statepos);
            /*if(x>sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }*/
        }

    }
  /*  public void uploadfromDB_Statelist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new State[x];
        if (cursor1.moveToFirst()) {

            do {
                State innerObj_Class_SandboxList = new State();
                innerObj_Class_SandboxList.setStateID(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_SandboxList.setStateName(cursor1.getString(cursor1.getColumnIndex("StateName")));
                innerObj_Class_SandboxList.setYearID(cursor1.getString(cursor1.getColumnIndex("state_yearid")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            statelist_farmers_sp.setAdapter(dataAdapter);
            statelist_farmers_sp.setSelection(statepos);
            *//*if(x>sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }*//*
        }

    }
*/
  /*  public void Update_stateid_spinner(String str_yearids) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
//        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList WHERE YearId='" + str_yearids + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList WHERE state_yearid='" + str_yearids + "'", null);


        int x = cursor1.getCount();
        Log.d("cursor Scount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new Class_StateListDetails[x];
        if (cursor1.moveToFirst())
        {

            do {
                Class_StateListDetails innerObj_Class_AcademicList = new Class_StateListDetails();
                innerObj_Class_AcademicList.setState_id(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_AcademicList.setState_name(cursor1.getString(cursor1.getColumnIndex("StateName")));
                innerObj_Class_AcademicList.setYear_id(cursor1.getString(cursor1.getColumnIndex("state_yearid")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            statelist_farmers_sp.setAdapter(dataAdapter);
            *//*if(x>sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }*//*
        }

    }
*/

    public void uploadfromDB_Districtlist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_DistrictListDetails2 = new District[x];
        if (cursor1.moveToFirst()) {

            do {
                District innerObj_Class_SandboxList = new District();
                innerObj_Class_SandboxList.setDistrictID(cursor1.getString(cursor1.getColumnIndex("DistrictID")));
                innerObj_Class_SandboxList.setDistrictName(cursor1.getString(cursor1.getColumnIndex("DistrictName")));
                //innerObj_Class_SandboxList.setYear_id(cursor1.getString(cursor1.getColumnIndex("Distr_yearid")));
                innerObj_Class_SandboxList.setStateID(cursor1.getString(cursor1.getColumnIndex("Distr_Stateid")));


                arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            districtlist_farmers_sp.setAdapter(dataAdapter);
            districtlist_farmers_sp.setSelection(districtpos);
            /*if(x>sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }*/
        }

    }

    public void Update_districtid_spinner(String str_stateid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictListRest WHERE Distr_Stateid='" + str_stateid + "'", null);
        //Cursor cursor1 = db1.rawQuery("select * from DistrictList D, TalukList T where D.DistrictID=T.Taluk_districtid AND D.Distr_Stateid='" + str_stateid + "'",null);

        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_DistrictListDetails2 = new District[x];
        if (cursor1.moveToFirst()) {

            do {
                District innerObj_Class_AcademicList = new District();
                innerObj_Class_AcademicList.setDistrictID(cursor1.getString(cursor1.getColumnIndex("DistrictID")));
                innerObj_Class_AcademicList.setDistrictName(cursor1.getString(cursor1.getColumnIndex("DistrictName")));
                //innerObj_Class_AcademicList.setYear_id(cursor1.getString(cursor1.getColumnIndex("Distr_yearid")));
                innerObj_Class_AcademicList.setStateID(cursor1.getString(cursor1.getColumnIndex("Distr_Stateid")));


                arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            districtlist_farmers_sp.setAdapter(dataAdapter);
            districtlist_farmers_sp.setSelection(districtpos);
            /*if(x>sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }*/
        }

    }

    public void uploadfromDB_Taluklist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukListRest", null);

        // Cursor cursor2 = db1.rawQuery("select T.taluka_id,V.village_id,V.village_name from master_state S, master_district D, master_taluka T, master_village V, master_panchayat P where S.state_id=D.state_id AND D.district_id=T.district_id AND T.taluka_id=V.taluk_id AND T.district_id=P.district_id AND (S.state_id in (1,12,25))",null);
        //  Cursor cursor1 = db1.rawQuery("select T.TalukID,T.TalukName,T.Taluk_districtid from DistrictList D, TalukList T where D.DistrictID=T.Taluk_districtid",null);

        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_TalukListDetails2 = new Taluka[x];
        if (cursor1.moveToFirst()) {

            do {
                Taluka innerObj_Class_SandboxList = new Taluka();
                innerObj_Class_SandboxList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                if (cursor1.getString(cursor1.getColumnIndex("TalukName")).isEmpty()) {
                    innerObj_Class_SandboxList.setTalukaName("Empty In DB");
                } else {
                    innerObj_Class_SandboxList.setTalukaName(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                }
                //innerObj_Class_SandboxList.setTaluk_name(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                innerObj_Class_SandboxList.setDistrictID(cursor1.getString(cursor1.getColumnIndex("Taluk_districtid")));


                arrayObj_Class_TalukListDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            taluklist_farmers_sp.setAdapter(dataAdapter);
            taluklist_farmers_sp.setSelection(talukpos);
            /*if(x>sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }*/
        }

    }


    public void Update_TalukId_spinner(String str_distid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukListRest WHERE Taluk_districtid='" + str_distid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Tcount", Integer.toString(x));

        int i = 0;

        arrayObj_Class_TalukListDetails2 = new Taluka[x];


        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    Taluka innerObj_Class_talukList = new Taluka();
                    innerObj_Class_talukList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                    innerObj_Class_talukList.setTalukaName(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                    innerObj_Class_talukList.setDistrictID(cursor1.getString(cursor1.getColumnIndex("Taluk_districtid")));


                    arrayObj_Class_TalukListDetails2[i] = innerObj_Class_talukList;
                    //Log.e("taluk_name",cursor1.getString(cursor1.getColumnIndex("TalukName")));
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }
        db1.close();


        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            taluklist_farmers_sp.setAdapter(dataAdapter);
            taluklist_farmers_sp.setSelection(talukpos);
            /*if(x>sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }*/
        }

        /*if(x==0)
        {
            arrayObj_Class_TalukListDetails2 = new Class_TalukListDetails[1];
            Class_TalukListDetails innerObj_Class_talukList = new Class_TalukListDetails();
            innerObj_Class_talukList.setTaluk_id("2000");
            innerObj_Class_talukList.setTaluk_name("No Records");
            innerObj_Class_talukList.setDistrict_id("2000");


            arrayObj_Class_TalukListDetails2[0] = innerObj_Class_talukList;

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            taluklist_farmers_sp.setAdapter(dataAdapter);
            taluklist_farmers_sp.setSelection(talukpos);
        }*/

    }

    public void uploadfromDB_Villagelist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        //db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,PanchayatID VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM VillageListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_VillageListDetails2 = new Village[x];
        if (cursor1.moveToFirst()) {

            do {
                Village innerObj_Class_villageList = new Village();
                innerObj_Class_villageList.setVillageID(cursor1.getString(cursor1.getColumnIndex("VillageID")));
                innerObj_Class_villageList.setVillageName(cursor1.getString(cursor1.getColumnIndex("Village")));
                innerObj_Class_villageList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                innerObj_Class_villageList.setPanchayatID(cursor1.getString(cursor1.getColumnIndex("PanchayatID")));

                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            villagelist_farmers_sp.setAdapter(dataAdapter);
            villagelist_farmers_sp.setSelection(villagepos);
            /*if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }*/
        }


    }

    public void Update_VillageId_spinner(String str_talukid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        //db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db1.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,PanchayatID VARCHAR);");
        //Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageListRest WHERE PanchayatID='" + str_talukid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Vcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_VillageListDetails2 = new Village[x];
        if (cursor1.moveToFirst()) {

            do {
                Village innerObj_Class_villageList = new Village();
                innerObj_Class_villageList.setVillageID(cursor1.getString(cursor1.getColumnIndex("VillageID")));
                innerObj_Class_villageList.setVillageName(cursor1.getString(cursor1.getColumnIndex("Village")));
                innerObj_Class_villageList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                innerObj_Class_villageList.setPanchayatID(cursor1.getString(cursor1.getColumnIndex("PanchayatID")));

                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                // Log.e("village_name", cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            villagelist_farmers_sp.setAdapter(dataAdapter);
            villagelist_farmers_sp.setSelection(villagepos);
            /*if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }*/

        }


        /*if(x==0)
        {
            arrayObj_Class_VillageListDetails2 = new Class_VillageListDetails[1];
            Class_VillageListDetails innerObj_Class_villageList = new Class_VillageListDetails();
            innerObj_Class_villageList.setVillage_id("2000");
            innerObj_Class_villageList.setVillage_name("No Records");
            innerObj_Class_villageList.setTaluk_id("2000");


            arrayObj_Class_VillageListDetails2[0] = innerObj_Class_villageList;
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            villagelist_farmers_sp.setAdapter(dataAdapter);
           // villagelist_farmers_sp.setSelection(villagepos);

        }*/

    }

    public void uploadfromDB_Grampanchayatlist() {

        SQLiteDatabase db_grampanchayat = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_grampanchayat.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatListRest(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
        Cursor cursor1 = db_grampanchayat.rawQuery("SELECT DISTINCT * FROM GrampanchayatListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_GrampanchayatListDetails2 = new Panchayat[x];
        if (cursor1.moveToFirst()) {

            do {
                Panchayat innerObj_Class_panchayatList = new Panchayat();
                innerObj_Class_panchayatList.setPanchayatID(cursor1.getString(cursor1.getColumnIndex("GramanchayatID")));
                innerObj_Class_panchayatList.setPanchayatName(cursor1.getString(cursor1.getColumnIndex("Gramanchayat")));
                innerObj_Class_panchayatList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("Panchayat_DistID")));


                arrayObj_Class_GrampanchayatListDetails2[i] = innerObj_Class_panchayatList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_grampanchayat.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_GrampanchayatListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            grampanchayatlist_farmers_sp.setAdapter(dataAdapter);
            grampanchayatlist_farmers_sp.setSelection(grampanchayatpos);
           /* if(x>sel_grampanchayatsp) {
                grampanchayatlist_SP.setSelection(sel_grampanchayatsp);
            }*/

        }

    }


    public void Update_GramPanchayatID_spinner(String str_ditrictid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatListRest(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM GrampanchayatListRest WHERE Panchayat_DistID='" + str_ditrictid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Gcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_GrampanchayatListDetails2 = new Panchayat[x];
        if (cursor1.moveToFirst()) {

            do {
                Panchayat innerObj_Class_panchayatList = new Panchayat();
                innerObj_Class_panchayatList.setPanchayatID(cursor1.getString(cursor1.getColumnIndex("GramanchayatID")));
                innerObj_Class_panchayatList.setPanchayatName(cursor1.getString(cursor1.getColumnIndex("Gramanchayat")));
                innerObj_Class_panchayatList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("Panchayat_DistID")));


                arrayObj_Class_GrampanchayatListDetails2[i] = innerObj_Class_panchayatList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_GrampanchayatListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            grampanchayatlist_farmers_sp.setAdapter(dataAdapter);
            grampanchayatlist_farmers_sp.setSelection(grampanchayatpos);
            /*if(x>sel_grampanchayatsp) {
                grampanchayatlist_SP.setSelection(sel_grampanchayatsp);
            }*/
        }

/*
        if(x==0)
        {
            arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[1];
            Class_GrampanchayatListDetails innerObj_Class_panchayatList = new Class_GrampanchayatListDetails();
            innerObj_Class_panchayatList.setGramanchayat_id("2000");
            innerObj_Class_panchayatList.setGramanchayat_name("No Records");
            innerObj_Class_panchayatList.setTalukid("2000");


            arrayObj_Class_GrampanchayatListDetails2[0] = innerObj_Class_panchayatList;
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_GrampanchayatListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            grampanchayatlist_farmers_sp.setAdapter(dataAdapter);

        }*/

    }

    // spinner insertion from DB

    //check for duplicate farmer details

    public int checkduplicate_farmerdetails_into_ViewFarmerList() {

        String str_Fname = farmerfirstname_et.getText().toString().trim();
        String str_Mname = farmermiddlename_et.getText().toString().trim();
        String str_Lname = farmerlastname_et.getText().toString().trim();
        String str_cellno = farmercellno_et.getText().toString().trim();

        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");

        // Cursor cursor1 = db_viewfarmerlist.rawQuery("SELECT DISTINCT * FROM ViewFarmerList  WHERE DispFarmerTable_FarmerName='" + str_yearid + "' AND FarmerMName_DB='" + str_stateid + "' AND FarmerLName_DB='" + str_distid + "'  AND Farmercellno_DB='" + str_talukid + "'", null);
        Cursor cursor1 = db_viewfarmerlist.rawQuery("SELECT * FROM ViewFarmerListRest  WHERE DispFarmerTable_FarmerName='" + str_Fname + "' COLLATE NOCASE AND " +
                "FarmerMName_DB='" + str_Mname + "' COLLATE NOCASE AND " +
                "FarmerLName_DB='" + str_Lname + "'COLLATE NOCASE AND " +
                "Farmercellno_DB='" + str_cellno + "'", null);

        //  Cursor cursor1 = db_viewfarmerlist.rawQuery("SELECT * FROM ViewFarmerList WHERE UploadedStatusFarmerprofile='" + 9 + "'", null);
        int x = cursor1.getCount();

        Log.e("duplicatecount", String.valueOf(x));

        //Toast.makeText(getApplicationContext(),"Already Farmer Details Present",Toast.LENGTH_LONG).show();
        /*Toast toast = Toast.makeText(getApplicationContext(),"Already Farmer Details Present",Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(Color.RED);
        toast.show();*/

        return x;

    }
    //check for duplicate farmer details


    //insert into new farmerdetails
    public void insert_farmerdetails_into_ViewFarmerList() {
        //  str_tempfid="tempfarmerID"+String.valueOf(System.currentTimeMillis());
        //str_tempfid=str_farmerid;
        str_tempfid = "tempfarmerID" + String.valueOf(System.currentTimeMillis() + "_" + str_employee_id);

        str_farmerid = str_tempfid; //"0";
        String str_farmerFname = farmerfirstname_et.getText().toString();
        String str_farmerimage = "empty";
        String str_farmpondcount = "0";


        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);



    /*db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerList(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
            "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
            "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
            "DispFarmerTable_FarmerName VARCHAR,FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR,
            LocalFarmerImg BLOB,Farmpondcount VARCHAR);");

*/

        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");



        String str_mname = farmermiddlename_et.getText().toString();
        String str_lname = farmerlastname_et.getText().toString();
        String str_age = farmerage_et.getText().toString();
        String str_cellno = farmercellno_et.getText().toString();
        String str_income = farmerannualincome_et.getText().toString();
        String str_member = familymember_et.getText().toString();
        String str_idprooftype = str_idproof_type;
        String str_idproofno = farmeridno_et.getText().toString();
        String str_Fphoto = arraylist_farmerimage_base64.get(0).toString();
        String str_UploadedStatusFarmerprofile = "10";


        String SQLiteQuery = "INSERT INTO ViewFarmerListRest (DispFarmerTable_YearID,DispFarmerTable_StateID, DispFarmerTable_DistrictID," +
                "DispFarmerTable_TalukID,DispFarmerTable_VillageID,DispFarmerTable_GrampanchayatID,DispFarmerTable_FarmerID," +
                "DispFarmerTable_Farmer_Code,DispFarmerTable_FarmerName,FarmerMName_DB,FarmerLName_DB,Farmerage_DB," +
                "Farmercellno_DB,FIncome_DB,Ffamilymember_DB,FIDprooftype_DB,FIDProofNo_DB,UploadedStatusFarmerprofile_DB,FarmerImageB64str_DB,DispFarmerTable_FarmerImage,Farmpondcount)" +
                " VALUES ('" + sp_stryear_ID + "','" + sp_strstate_ID + "','" + sp_strdistrict_ID + "','" + sp_strTaluk_ID + "','" + sp_strVillage_ID + "','"
                + sp_strgrampanchayat_ID + "','" + str_farmerid + "','" + str_farmerid + "','" + str_farmerFname + "','" + str_mname + "'," +
                "'" + str_lname + "','" + str_age + "','" + str_cellno + "','" + str_income + "','" + str_member + "','" + str_idprooftype + "','" + str_idproofno + "','" + str_UploadedStatusFarmerprofile + "','" + str_Fphoto + "','" + str_farmerimage + "','" + str_farmpondcount + "');";


        db_viewfarmerlist.execSQL(SQLiteQuery);
        db_viewfarmerlist.close();

        insert_farmerdetails_into_FarmPondDetails_fromServer_table();
    }


    public void insert_farmerdetails_into_FarmPondDetails_fromServer_table() {
        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

       /* db1.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR,TempFIDDB VARCHAR," +
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
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");*/

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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR);");



        String str_yearID, str_stateID, str_districtID, str_talukID, str_panchayatID, str_villageID, str_farmerFname, str_farmerMName,
                str_farmerLName, str_farmerage, str_Fphonenumber, str_FannualIncome, str_Ffamilymember, str_FIDprooftype,
                str_FIDproofno, str_Fphoto;

        String str_farmpond_id, str_width, str_height, str_depth, str_imageid1, str_base64image1, str_imageid2,
                str_base64image2, str_imageid3, str_base64image3, str_UploadedStatusFarmerprofile, str_startdate,
                str_farmpond_remarks, str_farmpond_amttaken, str_farmpondstatus;


        String str_approvalstatus, str_approvalremarks, str_approvedby, str_approveddate, str_donorname, str_latitude, str_longitude;

        String str_acres, str_gunta, str_crop_beforepond, str_crop_afterpond;
        str_farmpond_id = str_width = str_height = str_depth = str_imageid1 = str_base64image1 = str_imageid2 = str_base64image2 =
                str_imageid3 = str_base64image3 = "empty";


        String str_newpondImageId1,str_pondImageType1,str_newpondImageId2,str_pondImageType2,
        str_newpondImageId3,str_pondImageType3;
        str_newpondImageId1=str_pondImageType1=str_newpondImageId2=str_pondImageType2=
                str_newpondImageId3=str_pondImageType3="0";


        str_yearID = sp_stryear_ID;
        str_stateID = sp_strstate_ID;
        str_districtID = sp_strdistrict_ID;
        str_talukID = sp_strTaluk_ID;
        str_panchayatID = sp_strgrampanchayat_ID;
        str_villageID = sp_strVillage_ID;
        str_farmerFname = farmerfirstname_et.getText().toString();
        str_farmerMName = farmermiddlename_et.getText().toString();
        str_farmerLName = farmerlastname_et.getText().toString();
        str_farmerage = farmerage_et.getText().toString();
        str_Fphonenumber = farmercellno_et.getText().toString();
        str_FannualIncome = farmerannualincome_et.getText().toString();
        str_Ffamilymember = familymember_et.getText().toString();
        str_FIDprooftype = str_idproof_type;
        str_FIDproofno = farmeridno_et.getText().toString();
        str_Fphoto = arraylist_farmerimage_base64.get(0).toString();
        str_UploadedStatusFarmerprofile = "9";
        str_startdate = "0";
        str_farmpond_remarks = "4";
        str_farmpond_amttaken = "0";
        str_farmpondstatus = "3";
        str_approvalstatus = "no";
        str_approvalremarks = "no";
        str_approvedby = "no";
        str_approveddate = "no";
        str_donorname = "no";
        str_latitude = "no";
        str_longitude = "no";
        str_acres = "0";
        str_gunta = "0";
        str_crop_beforepond = "";
        str_crop_afterpond = "";



        Calendar c = Calendar.getInstance();
        //System.out.println("Current time => "+c.getTime());

        //  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");             //added by madhu

        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        str_submitteddatetime = formattedDate;
        Log.e("date", formattedDate);




        String SQLiteQuery = "INSERT INTO FarmPondDetails_fromServerRest (FIDDB,TempFIDDB,FNameDB,FMNameDB,FLNameDB,FYearIDDB,FStateIDDB,FDistrictIDDB," +
                "FTalukIDDB,FPanchayatIDDB,FVillageIDDB,FageDB,FphonenumberDB,FfamilymemberDB,FAnnualIncomeDB,FidprooftypeDB,FidproofnoDB,FphotoDB,FPondidDB," +
                "WidthDB,HeightDB,DepthDB,LatitudeDB,LongitudeDB,Imageid1DB,Image1Base64DB,Imageid2DB,Image2Base64DB," +
                "Imageid3DB,Image3Base64DB,EmployeeIDDB,SubmittedDateDB," +
                "TotalDaysDB,StartDateDB,ConstructedDateDB,PondCostDB,McodeDB,FPondCodeDB,FPondRemarksDB,FPondAmtTakenDB,FPondStatusDB," +
                "FPondApprovalStatusDB,FPondApprovalRemarksDB,FPondApprovedbyDB,FPondApprovedDateDB,FPondDonorDB," +
                "FPondLatitudeDB,FPondLongitudeDB," +
                "FPondAcresDB,FPondGuntaDB,FPondCropBeforeDB,FPondCropAfterDB," +
                "UploadedStatusFarmerprofile,UploadedStatus," +
                "newpondImageId1,pondImageType1,newpondImageId2,pondImageType2,newpondImageId3,pondImageType3)" +
                " VALUES ('" + str_farmerid + "','" + str_tempfid + "','" + str_farmerFname + "','" + str_farmerMName + "','" + str_farmerLName + "','" + str_yearID + "'," +
                "'" + str_stateID + "','" + str_districtID + "','" + str_talukID + "','" + str_panchayatID + "','" + str_villageID + "','" + str_farmerage + "'," +
                "'" + str_Fphonenumber + "','" + str_Ffamilymember + "','" + str_FannualIncome + "','" + str_FIDprooftype + "','" + str_FIDproofno + "','" + str_Fphoto + "'," +
                "'" + str_farmpond_id + "','" + str_width + "'," +
                "'" + str_height + "','" + str_depth + "','" + 0 + "','" + 0 + "','" + str_imageid1 + "','" + str_base64image1 + "'," +
                "'" + str_imageid2 + "','" + str_base64image2 + "','" + str_imageid3 + "','" + str_base64image3 + "','" + str_employee_id + "','" + str_submitteddatetime + "'," +
                "'" + 0 + "','" + str_startdate + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + str_farmpond_remarks + "','" + str_farmpond_amttaken + "','" + str_farmpondstatus + "'," +
                "'" + str_approvalstatus + "','" + str_approvalremarks + "','" + str_approvedby + "','" + str_approveddate + "','" + str_donorname + "'," +
                "'" + str_latitude + "','" + str_longitude + "','" + str_acres + "','" + str_gunta + "','" + str_crop_beforepond + "','" + str_crop_afterpond + "'," +
                "'" + str_UploadedStatusFarmerprofile + "','" + 0 + "'," +
                "'"+str_newpondImageId1+"','"+str_pondImageType1+"'," +
                "'"+str_newpondImageId2+"','"+str_pondImageType2+"'," +
                "'"+str_newpondImageId3+"','"+str_pondImageType3+"');";


        str_newpondImageId1=str_pondImageType1=str_newpondImageId2=str_pondImageType2=
                str_newpondImageId3=str_pondImageType3="0";

        db1.execSQL(SQLiteQuery);
        db1.close();

        //FPondRemarksDB,FPondAmtTakenDB,FPondStatusDB,


        ClearEditTextAfterDoneTask();

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent) {
            fetch_DB_farmerprofile_offline_data();
        }

    }

    public void ClearEditTextAfterDoneTask() {
        Toast.makeText(getApplicationContext(), "Farmer Data has been added", Toast.LENGTH_LONG).show();
        farmerfirstname_et.getText().clear();
        farmermiddlename_et.getText().clear();
        farmerlastname_et.getText().clear();
        farmerage_et.getText().clear();
        farmercellno_et.getText().clear();
        farmerannualincome_et.getText().clear();
        familymember_et.getText().clear();
        farmeridno_et.getText().clear();
        add_farmerimage_iv.setImageResource(R.drawable.add_farmpond_image);
        selectidproof_sp.setSelection(0);
    }


    public void DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(String str_yearID, String str_stateID, String str_districtID, String str_talukid, String str_villageid, String str_grampanchayatid, String str_farmerid, String str_farmercode, String str_farmername, String str_farmerimage) {
        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerList(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR,DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR,DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR,DispFarmerTable_FarmerName VARCHAR,DispFarmerTable_FarmerImage VARCHAR,LocalFarmerImg BLOB);");


        String SQLiteQuery = "INSERT INTO ViewFarmerList (DispFarmerTable_YearID,DispFarmerTable_StateID, DispFarmerTable_DistrictID,DispFarmerTable_TalukID,DispFarmerTable_VillageID,DispFarmerTable_GrampanchayatID,DispFarmerTable_FarmerID,DispFarmerTable_Farmer_Code,DispFarmerTable_FarmerName,DispFarmerTable_FarmerImage)" +
                " VALUES ('" + str_yearID + "','" + str_stateID + "','" + str_districtID + "','" + str_talukid + "','" + str_villageid + "','" + str_grampanchayatid + "','" + str_farmerid + "','" + str_farmercode + "','" + str_farmername + "','" + str_farmerimage + "');";
        db_viewfarmerlist.execSQL(SQLiteQuery);


//        Log.e("str_yearID DB", str_yearID);
//        Log.e("str_stateID DB", str_stateID);
//        Log.e("str_districtID DB", str_districtID);
//        Log.e("str_talukid DB", str_talukid);
//        Log.e("str_villageid DB", str_villageid);
//        Log.e("str_grampanchayatid DB", str_grampanchayatid);
//        Log.e("str_farmerid DB", str_farmerid);
//        Log.e("str_farmername DB", str_farmername);
//        Log.e("str_farmerimage DB", str_farmerimage);


        db_viewfarmerlist.close();
    }


    public boolean validation() {
        boolean b_year, b_state, b_district, b_taluk, b_grampanchayat, b_village, b_firstname, b_secondname, b_middlename,
                b_age, b_cellnumber, b_annualincome, b_familymembers, b_farmerimage, b_agecheck;

        b_year = b_state = b_district = b_taluk = b_grampanchayat = b_village = b_firstname = b_secondname =
                b_middlename = b_age = b_cellnumber = b_annualincome = b_familymembers = b_farmerimage = b_agecheck = true;

        Log.e("yearvalue", sp_stryear_ID);


        if (sp_stryear_ID.equalsIgnoreCase("-1") || sp_strstate_ID.equalsIgnoreCase("-1")
                || sp_strdistrict_ID.equalsIgnoreCase("-1") || sp_strTaluk_ID.equalsIgnoreCase("-1") ||
                sp_strgrampanchayat_ID.equalsIgnoreCase("-1") || sp_strVillage_ID.equalsIgnoreCase("-1")) {
            Toast.makeText(getApplicationContext(), "Select the dropdown", Toast.LENGTH_LONG).show();
            b_year = b_state = b_district = b_taluk = b_grampanchayat = b_village = false;
        }

        if (sp_stryear_ID.equalsIgnoreCase("0") || sp_strstate_ID.equalsIgnoreCase("0")
                || sp_strdistrict_ID.equalsIgnoreCase("0") || sp_strTaluk_ID.equalsIgnoreCase("0") ||
                sp_strgrampanchayat_ID.equalsIgnoreCase("-0") || sp_strVillage_ID.equalsIgnoreCase("0")) {
            Toast.makeText(getApplicationContext(), "Select the dropdown", Toast.LENGTH_LONG).show();
            b_year = b_state = b_district = b_taluk = b_grampanchayat = b_village = false;
        }

        if (farmerfirstname_et.getText().toString().length() == 0 || farmerfirstname_et.getText().toString().length() < 3) {
            farmerfirstname_et.setError("Enter Valid Name");
            farmerfirstname_et.requestFocus();
            b_firstname = false;
        }
        /*if(farmersecondname_et.getText().toString().length()==0)
        {
            farmersecondname_et.setError("Empty not allowed");
            farmersecondname_et.requestFocus();
            b_secondname=false;
        }


        if(farmermiddlename_et.getText().toString().length()==0)
        {
            farmermiddlename_et.setError("Empty not allowed");
            farmermiddlename_et.requestFocus();
            b_middlename=false;
        }

        if(farmermiddlename_et.getText().toString().length()==0)
        {
            farmermiddlename_et.setError("Empty not allowed");
            farmermiddlename_et.requestFocus();
            b_middlename=false;
        }*/

        if (farmerage_et.getText().toString().length() == 0) {
            farmerage_et.setError("Empty not allowed");
            farmerage_et.requestFocus();
            b_age = false;
        }


        //if(int_agecheck<=15)
        if (farmerage_et.getText().toString().trim().length() == 0) {
            farmerage_et.setError("Enter valid age");
            farmerage_et.requestFocus();
            b_agecheck = false;
        }

        if (farmerage_et.getText().toString().trim().length() > 0) {
            Integer int_agecheck = Integer.valueOf(farmerage_et.getText().toString());
            if (int_agecheck <= 15) {
                farmerage_et.setError("Age must be above 15");
                farmerage_et.requestFocus();
                b_agecheck = false;
            }
        }


        if (farmercellno_et.getText().toString().length() < 9) {
            farmercellno_et.setError("Enter vaild cellnumber");
            farmercellno_et.requestFocus();
            b_cellnumber = false;
        }


        //if(int_annualincome<50000)
        if (farmerannualincome_et.getText().toString().trim().length() == 0) {
            farmerannualincome_et.setError("Must be greater than 50,000");
            farmerannualincome_et.requestFocus();
            b_annualincome = false;
        } else {
            Integer int_annualincome = Integer.valueOf(farmerannualincome_et.getText().toString());
            if (int_annualincome < 50000) {
                farmerannualincome_et.setError("Must be greater than 50,000");
                farmerannualincome_et.requestFocus();
                b_annualincome = false;
            }
        }

        if (familymember_et.getText().toString().length() < 1 || familymember_et.getText().toString().length() > 30) {
            familymember_et.setError("Enter vaild familymember");
            familymember_et.requestFocus();
            b_familymembers = false;
        }

        if (arraylist_farmerimage_base64.size() == 0) {
            Toast.makeText(getApplication(), "Add the Farmer Images", Toast.LENGTH_LONG).show();
            b_farmerimage = false;
        }


        return (b_year && b_state && b_district && b_taluk && b_grampanchayat && b_village && b_firstname && b_secondname &&
                b_middlename && b_age && b_cellnumber && b_annualincome && b_familymembers && b_farmerimage && b_agecheck);

    }


    //volley asynctask

    private void AsyncTask_Add_farmponddetails() {

        final ProgressDialog pdLoading = new ProgressDialog(AddFarmer_Activity1.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();


        String str_fetchfarmponddetails_url = Class_URL.URL_Add_farmerdetails.toString().trim();

//        String str_fetchfarmponddetails_url = Class_URL.URL_Add_farmponddetails_offlineData.toString().trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pdLoading.dismiss();

                        Log.e("volley response", response);//volley response: {"statusMessage":"success"}
                        parse_newfarmer_details_response(response);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdLoading.dismiss();
                        Toast.makeText(AddFarmer_Activity1.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                /*String str_image1=arraylist_image1_base64.get(0).toString();
                String str_image2=arraylist_image2_base64.get(0).toString();
                String str_image3=arraylist_image3_base64.get(0).toString();*/


                params.put("year_id", sp_stryear_ID); //
                params.put("State", sp_strstate_ID);
                params.put("District", sp_strdistrict_ID);
                params.put("Taluk", sp_strTaluk_ID);
                params.put("Grampanchayat", sp_strgrampanchayat_ID);
                params.put("Village", sp_strVillage_ID);
                params.put("Firstname", farmerfirstname_et.getText().toString());
                params.put("Middlename", farmermiddlename_et.getText().toString());
                params.put("LastName", farmerlastname_et.getText().toString());
                params.put("Age", farmerage_et.getText().toString());
                params.put("PhoneNumber", farmercellno_et.getText().toString());
                params.put("annual_income", farmerannualincome_et.getText().toString());
                params.put("family_members", familymember_et.getText().toString());
                params.put("Id_proof_type", str_idproof_type);
                params.put("id_proof_no", farmeridno_et.getText().toString());
                params.put("image_link", arraylist_farmerimage_base64.get(0).toString());


                //Log.e("image", arraylist_image1_base64.get(0).toString());
                //Log.e("requesrt", String.valueOf(params));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("request", stringRequest.toString());
        requestQueue.add(stringRequest);
    }


    public void parse_newfarmer_details_response(String response) {
        //volley response: {"statusMessage":"success"}
        /*response:  {
            "farmer_id": "532",
                    "statusMessage": "success"
        }*/
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("statusMessage").equalsIgnoreCase("Success")) {

                Toast.makeText(getApplicationContext(), "Farmer Details Uploaded", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AddFarmer_Activity1.this, AddFarmer_Activity1.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplication(), jsonObject.getString("statusMessage").toString(), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }

    }

    //profile images


    private void selectImage1() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFarmer_Activity1.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intentPhotoCapture = new Intent(AddFarmer_Activity1.this, CameraPhotoCapture.class);

                    startActivity(intentPhotoCapture);

                   /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
*/
                    Toast.makeText(getApplicationContext(), "choosen  take photo ",
                            Toast.LENGTH_SHORT).show();
                    digitalcamerabuttonpressed = true;
/*
                    file_img=getOutputFromCamera();
                    imageFilePath = CommonUtils.getFilename();
                    File imageFile = new File(imageFilePath);
                    //  Uri imageFileUri = Uri.fromFile(imageFile); // convert path to Uri
                    Uri imageFileUri = FileProvider.getUriForFile(AddFarmer_Activity1.this, "com.mydomain.fileprovider", imageFile);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);   // set the image file name
                    startActivityForResult(intent, REQUEST_CAMERA);*/

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);


                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //    if (resultCode == RESULT_OK) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Log.e("tag", "mCurrentPhotoPath="+mCurrentPhotoPath);
               imageFilePath= CommonUtils.compressImage(mCurrentPhotoPath);
                Log.e("CameraDemo", "Pic saved");
                Uri imageUri = Uri.parse(imageFilePath);
                if (imageUri.getPath() != null) {
                    File file = new File(imageUri.getPath());

                    try {
                        InputStream ims = new FileInputStream(file);
                        bitmap = BitmapFactory.decodeStream(ims);
                        rotatemethod(file.toString());
                        Toast.makeText(getApplicationContext(), "In camera", Toast.LENGTH_SHORT).show();
                        BitMapToString1(bitmap);

                        add_farmerimage_iv.setImageBitmap(bitmap);


                    } catch (FileNotFoundException e) {
                        return;
                    }


                } else if (resultCode == RESULT_CANCELED) {

                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();

                }
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                imageFilePath= CommonUtils.compressImage(picturePath);

                Bitmap thumbnail = (BitmapFactory.decodeFile(imageFilePath));
                thumbnail = getResizedBitmap1(thumbnail, 400);
                Log.w(" gallery.", picturePath + "");
                add_farmerimage_iv.setImageBitmap(thumbnail);
                BitMapToString1(thumbnail);

            }
        }

    }


    public String BitMapToString1(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        str_img = Base64.encodeToString(b, Base64.DEFAULT);
          Log.e("tag","image size=="+String.valueOf(b.length));
//                if (str_img.equals("") || str_img.equals(null)) {
        arraylist_farmerimage_base64.clear();
        arraylist_farmerimage_base64.add(str_img);
        return str_img;
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


    public void rotatemethod(String path) {
        //       check the rotation of the image and display it properly
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                //matrix.postRotate(90);
                matrix.setRotate(90);
                Log.d("EXIF", "Exif90: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix,
                    true);

            add_farmerimage_iv.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //profile images



  /*  private void selectImage()
    {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
       // final CharSequence[] items = {"Take Photo","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddFarmer_Activity1.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Class_utility.checkPermission(AddFarmer_Activity1.this);
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
*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Class_utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library")) {
                        //galleryIntent();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
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

    private File getOutputFromCamera() {
        String timeStamp = new SimpleDateFormat(getString(R.string.txtdateformat), Locale.getDefault()).format(new Date());
        file_imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getString(R.string.txtFileDocumentUploadImg) + timeStamp + getString(R.string.txtFileExtension));
        try {
            file_imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Log.d("absolute path is",imageFile.getAbsolutePath());
        return file_imageFile;
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    //@Override
    public void onActivityResult2(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
           /* if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);*/

            if (requestCode == 1) {
                Log.e("inside", "inside");
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap thumbnail_bitmap = (BitmapFactory.decodeFile(picturePath));
                    thumbnail_bitmap = getResizedBitmap(thumbnail_bitmap, 400);
                    Log.w(" gallery.", picturePath + "");

                    add_farmerimage_iv.setImageBitmap(thumbnail_bitmap);
                    /*removefarmerimage_ib.setVisibility(View.VISIBLE);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumbnail_bitmap.compress(Bitmap.CompressFormat.PNG, 60, baos);
                    byte[] b = baos.toByteArray();
                    str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

                    arraylist_farmerimage_base64.add(str_base64imagestring);*/
                    // BitMapToString(thumbnail);

                    //BitMapToString(thumbnail_bitmap);
                }
            } else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


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


            if (str_image1.equals("true")) {
                add_farmerimage_iv.setImageBitmap(bm);
                removefarmerimage_ib.setVisibility(View.VISIBLE);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 60, baos);
                byte[] b = baos.toByteArray();
                str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

                arraylist_farmerimage_base64.add(str_base64imagestring);
                // BitMapToString(thumbnail);
            }


        }


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
            add_farmerimage_iv.setImageBitmap(bitmap_thumbnail);
            removefarmerimage_ib.setVisibility(View.VISIBLE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

            arraylist_farmerimage_base64.add(str_base64imagestring);
            // BitMapToString(thumbnail);
        }


//
        /*if(isInternetPresent){
            SaveFarmerImage();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
    }


    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

        return str_base64imagestring;
    }

//images


    public void onBackPressed() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(AddFarmer_Activity1.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Are you sure want to go back");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(AddFarmer_Activity1.this, Activity_ViewFarmers.class);
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


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
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


    public void Data_from_FarmerDetails_DB(String str_farmerid_toedit) {

        Log.e("inside", "display");
        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");


        Cursor cursor1 = db_viewfarmerlist.rawQuery("SELECT DISTINCT * FROM ViewFarmerListRest WHERE DispFarmerTable_FarmerID='" + str_farmerid_toedit + "'", null);
        int x = cursor1.getCount();


        Log.e("Editsearchcout", String.valueOf(x));
        int i = 0;
        // class_farmprofiledetails_obj = new Class_farmprofiledetails();

        class_farmerListDetails_obj = new Farmer();
        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {

                    Farmer innerObj_Class_farmerlist_offline = new Farmer();

                    innerObj_Class_farmerlist_offline.setFarmerID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerID")));
                    innerObj_Class_farmerlist_offline.setFarmerFirstName(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName")));
                    innerObj_Class_farmerlist_offline.setFarmerMiddleName(cursor1.getString(cursor1.getColumnIndex("FarmerMName_DB")));
                    innerObj_Class_farmerlist_offline.setFarmerLastName(cursor1.getString(cursor1.getColumnIndex("FarmerLName_DB")));
                    innerObj_Class_farmerlist_offline.setFarmerAge(cursor1.getString(cursor1.getColumnIndex("Farmerage_DB")));
                    innerObj_Class_farmerlist_offline.setFarmerMobile(cursor1.getString(cursor1.getColumnIndex("Farmercellno_DB")));
                    innerObj_Class_farmerlist_offline.setFarmerIncome(cursor1.getString(cursor1.getColumnIndex("FIncome_DB")));
                    innerObj_Class_farmerlist_offline.setFarmerFamily(cursor1.getString(cursor1.getColumnIndex("Ffamilymember_DB")));
                    innerObj_Class_farmerlist_offline.setFarmerIDType(cursor1.getString(cursor1.getColumnIndex("FIDprooftype_DB")));
                    innerObj_Class_farmerlist_offline.setFarmerIDNumber(cursor1.getString(cursor1.getColumnIndex("FIDProofNo_DB")));
                    innerObj_Class_farmerlist_offline.setStr_base64(cursor1.getString(cursor1.getColumnIndex("FarmerImageB64str_DB")));
                    // innerObj_Class_farmerlist_offline.set(cursor1.getString(cursor1.getColumnIndex("UploadedStatusFarmerprofile_DB")));

                    // Log.e("dbfarmerimage",cursor1.getString(cursor1.getColumnIndex("FarmerImageB64str_DB")));

                    class_farmerListDetails_obj = innerObj_Class_farmerlist_offline;

                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }

        db_viewfarmerlist.close();

        if (x > 0) {
            if (class_farmerListDetails_obj != null) {
                DisplayData_Data_from_ViewFarmerList_DB();

            } else {
                Log.e("onPostExecute", "class_farmerListDetails_obj == null");
            }

        }


    }


    public void DisplayData_Data_from_ViewFarmerList_DB() {

        str_editingfarmerprofile = "yes";

        farmerfirstname_et.setText(class_farmerListDetails_obj.getFarmerFirstName());
        farmermiddlename_et.setText(class_farmerListDetails_obj.getFarmerMiddleName());
        farmerlastname_et.setText(class_farmerListDetails_obj.getFarmerLastName());

        farmerage_et.setText(class_farmerListDetails_obj.getFarmerAge());
        farmercellno_et.setText(class_farmerListDetails_obj.getFarmerMobile());
        farmerannualincome_et.setText(class_farmerListDetails_obj.getFarmerIncome());
        familymember_et.setText(class_farmerListDetails_obj.getFarmerFamily());
        farmeridno_et.setText(class_farmerListDetails_obj.getFarmerIDNumber());

        Log.e("idprooftype", class_farmerListDetails_obj.getFarmerIDType());

        //Log.e("image",class_farmerListDetails_obj.getStr_base64());
        // Log.e("image",class_farmerListDetails_obj.getFarmerimage());


        Class_base64toBitmapConversionFarmerProfile class_base64toBitmapConversionFarmerProfile_obj = new Class_base64toBitmapConversionFarmerProfile();
        arrayList_bitmap_farmerimage = class_base64toBitmapConversionFarmerProfile_obj.base64tobitmap(class_farmerListDetails_obj);

        add_farmerimage_iv.setImageBitmap(arrayList_bitmap_farmerimage.get(0));
        arraylist_farmerimage_base64.clear();
        arraylist_farmerimage_base64.add(class_farmerListDetails_obj.getStr_base64());
        removefarmerimage_ib.setVisibility(View.VISIBLE);

        //if(!class_farmerListDetails_obj.getFarmerIDType().equalsIgnoreCase("")||class_farmerListDetails_obj.getFarmerIDType()!=null||!class_farmerListDetails_obj.getFarmerIDType().equalsIgnoreCase("null")) {
        if (class_farmerListDetails_obj.getFarmerIDType().equalsIgnoreCase("null")) {
        } else {
            int int_idprooftype = Integer.parseInt(class_farmerListDetails_obj.getFarmerIDType());
            if (int_idprooftype < 5) {
                int_idprooftype--;
            } else {
                int_idprooftype--;
            }
            selectidproof_sp.setSelection(int_idprooftype);

        }



       /* Class_base64toBitmapConversion class_base64toBitmapConversion_obj=new Class_base64toBitmapConversion();

        arrayList_bitmap_farmerimage=class_base64toBitmapConversion_obj.base64tobitmap(class_farmerListDetails_obj);*/


    }//


    public void update_farmerdetails_into_ViewFarmerList() {

        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");             //added by madhu
        String formattedDate = df.format(c.getTime());
        str_submitteddatetime = formattedDate;
        Log.e("date", formattedDate);


        try {

            Log.e("updateFID", str_farmerID_foredit);

            ContentValues cv = new ContentValues();
            cv.put("DispFarmerTable_YearID", sp_stryear_ID);
            cv.put("DispFarmerTable_StateID", sp_strstate_ID);
            cv.put("DispFarmerTable_DistrictID", sp_strdistrict_ID);
            cv.put("DispFarmerTable_TalukID", sp_strTaluk_ID);
            cv.put("DispFarmerTable_VillageID", sp_strVillage_ID);
            cv.put("DispFarmerTable_GrampanchayatID", sp_strgrampanchayat_ID);
            cv.put("DispFarmerTable_FarmerName", farmerfirstname_et.getText().toString());

            cv.put("FarmerMName_DB", farmermiddlename_et.getText().toString());
            cv.put("FarmerLName_DB", farmerlastname_et.getText().toString());

            cv.put("Farmerage_DB", farmerage_et.getText().toString());
            cv.put("Farmercellno_DB", farmercellno_et.getText().toString());
            cv.put("FIncome_DB", farmerannualincome_et.getText().toString());
            cv.put("Ffamilymember_DB", familymember_et.getText().toString());
            cv.put("FIDprooftype_DB", str_idproof_type);
            cv.put("FIDProofNo_DB", farmeridno_et.getText().toString());
            cv.put("UploadedStatusFarmerprofile_DB", "9");
            cv.put("FarmerImageB64str_DB", arraylist_farmerimage_base64.get(0).toString());
            cv.put("Submitted_Date", str_submitteddatetime);


            if (str_farmerID_foredit.contains("tempfarmpond")) {
                cv.put("UploadedStatusFarmerprofile_DB", "10");
            } else {
                cv.put("UploadedStatusFarmerprofile_DB", "9");
            }

            db_viewfarmerlist.update("ViewFarmerListRest", cv, "DispFarmerTable_FarmerID = ?", new String[]{str_farmerID_foredit});
            db_viewfarmerlist.close();


            Update_farmerdetails_into_FarmPondDetails_fromServer_table();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    public void Update_farmerdetails_into_FarmPondDetails_fromServer_table() {
        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

       /* db2.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR,TempFIDDB VARCHAR," +
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
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");*/
        db2.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR,TempFIDDB VARCHAR," +
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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR);");



        try {

            Log.e("updateFID", str_farmerID_foredit);

            ContentValues cv = new ContentValues();
            cv.put("FYearIDDB", sp_stryear_ID);
            cv.put("FStateIDDB", sp_strstate_ID);
            cv.put("FDistrictIDDB", sp_strdistrict_ID);
            cv.put("FTalukIDDB", sp_strTaluk_ID);
            cv.put("FPanchayatIDDB", sp_strgrampanchayat_ID);
            cv.put("FVillageIDDB", sp_strVillage_ID);
            cv.put("FNameDB", farmerfirstname_et.getText().toString());

            cv.put("FMNameDB", farmermiddlename_et.getText().toString());
            cv.put("FLNameDB", farmerlastname_et.getText().toString());

            cv.put("FageDB", farmerage_et.getText().toString());
            cv.put("FphonenumberDB", farmercellno_et.getText().toString());
            cv.put("FAnnualIncomeDB", farmerannualincome_et.getText().toString());
            cv.put("FfamilymemberDB", familymember_et.getText().toString());
            cv.put("FidprooftypeDB", str_idproof_type);
            cv.put("FidproofnoDB", farmeridno_et.getText().toString());
            cv.put("FphotoDB", arraylist_farmerimage_base64.get(0).toString());


            if (str_farmerID_foredit.contains("tempfarmpond")) {
                cv.put("UploadedStatusFarmerprofile", "9");
            } else {
                cv.put("UploadedStatusFarmerprofile", "10");
            }


            db2.update("FarmPondDetails_fromServerRest", cv, "FIDDB = ?", new String[]{str_farmerID_foredit});
            db2.close();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
        }


        Intent i = new Intent(AddFarmer_Activity1.this, Activity_ViewFarmers.class);
        startActivity(i);
        finish();


    }


//Online Sync


    public void fetch_DB_farmerprofile_offline_data() {
        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

       /* db1.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR,TempFIDDB VARCHAR," +
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
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");*/


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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR," +
                "pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR);");




        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatusFarmerprofile='" + 9 + "'", null);
        int x = cursor1.getCount();

        Log.e("new farmercount", String.valueOf(x));


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


        for (int j = 0; j < class_farmerprofileoffline_array_obj.length; j++) {
            AddFarmerDetails(j);
        }

    }


    private void AddFarmerDetailsNew() {

        AddFarmerRequestTest request = new AddFarmerRequestTest();
        request.setFarmerID("0");
        request.setVillageID("604936");
        request.setFarmerFirstName("Test madhu");
        request.setFarmerMobile("8907654321");
        request.setSubmittedDate("03-06-2020");
        request.setMobileTempID("1");
        request.setCreatedBy(str_employee_id);
        // Log.e("tag","submittedDateTime="+class_farmerprofileoffline_array_obj[j].getStr_submittedDateTime());
//        request.setFarmerID("0");
//        request.setVillageID("604790");
//        request.setFarmerFirstName("Madhu");
//        request.setFarmerMobile("8904674048");
//        request.setSubmittedDate("02-06-2020");
//        request.setMobileTempID("1");
//        request.setCreatedBy("40");

      /*  Log.e("tag","FarmerFirstName=="+class_farmerprofileoffline_array_obj[j].getStr_fname());
        Log.e("tag","FarmerID=="+class_farmerprofileoffline_array_obj[j].getStr_farmerID());
*/
        Call<AddFarmerResponse> call = userService1.AddFarmerNew(request);
        //   Call<AddFarmerRequest> call = userService1.AddFarmer(farmerId, stateId, districtId, talukaId, panchayatId, villageId, fname, mname, lname, phoneNum,idType,idPronum,farmerImg,age,annualIncome,familyMember,submittedData,tempFarmerID,empId);
        Log.e("TAG", "Request 33: " + new Gson().toJson(call.request()));
        Log.e("TAG", "Request: " + request.toString());
        /*final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(AddFarmer_Activity1.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();*/
        call.enqueue(new Callback<AddFarmerResponse>() {
            @Override
            public void onResponse(Call<AddFarmerResponse> call, Response<AddFarmerResponse> response) {
                Log.e("response", response.toString());
                Log.e("TAG", "response 33: " + new Gson().toJson(response));
                Log.e("response body", String.valueOf(response.body()));
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
                   /* Log.e("response new:",error1.getMsg());
                    Log.e("response new status:", String.valueOf(error1.getstatus()));*/
                // Log.e("response",Gson.fromJson(response.toString(),AddFarmer_Activity1.class));

                if (response.isSuccessful()) {
                    //  progressDoalog.dismiss();
                    AddFarmerResponse class_loginresponse = response.body();
                    Log.e("tag", "res==" + class_loginresponse.toString());
                    if (class_loginresponse.getStatus().equals("true")) {

                        AddFarmerResList addFarmerResList = response.body().getLst();
                        Log.e("tag", "addFarmerResList NAme=" + addFarmerResList.getFarmerFirstName());
                        Log.e("tag", "addFarmerResList farmerID=" + addFarmerResList.getFarmerID());

                    } else if (class_loginresponse.getStatus().equals("false")) {
                        //     progressDoalog.dismiss();
                        Toast.makeText(AddFarmer_Activity1.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    //   progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(AddFarmer_Activity1.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(AddFarmer_Activity1.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }

    private void AddFarmerDetails(final int j) {
        AddFarmerRequest request = new AddFarmerRequest();
      //  request.setFarmerID(class_farmerprofileoffline_array_obj[j].getStr_farmerID());
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
        request.setFarmerAge(class_farmerprofileoffline_array_obj[j].getStr_age());
        request.setFarmerIncome(class_farmerprofileoffline_array_obj[j].getStr_annualincome());
        request.setFarmerFamily(class_farmerprofileoffline_array_obj[j].getStr_familymembers());
        request.setSubmittedDate(class_farmerprofileoffline_array_obj[j].getStr_submittedDateTime());
        request.setMobileTempID(class_farmerprofileoffline_array_obj[j].getStr_tempfarmerid());
        request.setCreatedBy(str_employee_id);
        request.setFarmerPhoto(class_farmerprofileoffline_array_obj[j].getStr_farmerimage());
        Log.e("tag", "FarmerFirstName==" + class_farmerprofileoffline_array_obj[j].getStr_fname());
        Log.e("tag", "FarmerID==" + class_farmerprofileoffline_array_obj[j].getStr_farmerID());
        Log.e("tag", "getStr_familymembers==" + class_farmerprofileoffline_array_obj[j].getStr_familymembers());
        Log.e("tag", "getStr_annualincome==" + class_farmerprofileoffline_array_obj[j].getStr_annualincome());

        Call<AddFarmerResponse> call = userService1.AddFarmer(request);
        //Log.e("TAG", "Request 33: " + new Gson().toJson(request));
        Log.e("TAG", "Request: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(AddFarmer_Activity1.this);
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

                   /* db1.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR,TempFIDDB VARCHAR," +
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
                            "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");*/



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
                            "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR," +
                            "pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR);");



                    ContentValues cv = new ContentValues();
                    cv.put("FIDDB", str_response_farmer_id);
                /*cv.put("employee_id",str_response_image_id3);
                // employee_id*/
                    cv.put("UploadedStatusFarmerprofile", 10);

                    db1.update("FarmPondDetails_fromServerRest", cv, "TempFIDDB = ?", new String[]{str_response_tempId});
                    db1.close();

                    SQLiteDatabase db_viewfarmerlist = getApplication().openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);


                    db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                            "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                            "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                            "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                            "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                            "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                            "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");


                    ContentValues cv_farmelistupdate = new ContentValues();
                    cv_farmelistupdate.put("DispFarmerTable_Farmer_Code", str_response_farmercode);
                    cv_farmelistupdate.put("DispFarmerTable_FarmerID", str_response_farmer_id);


                    db_viewfarmerlist.update("ViewFarmerListRest", cv_farmelistupdate, "DispFarmerTable_Farmer_Code = ?", new String[]{str_response_tempId});
                    db_viewfarmerlist.close();
                    progressDoalog.dismiss();
                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(AddFarmer_Activity1.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(AddFarmer_Activity1.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }


//Online Sync


    public boolean onOptionsItemSelected(MenuItem item) {
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




    /*public void onBackPressed() {
        Intent i = new Intent(AddFarmer_Activity.this, Activity_MarketingHomeScreen.class);
        startActivity(i);
        finish();
    }*/


}// end of class
