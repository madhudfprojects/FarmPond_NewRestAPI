package df.farmpondstwo;

import android.annotation.SuppressLint;
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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import df.farmpondstwo.Models.AdminEmpTotalPondCountList;
import df.farmpondstwo.Models.AdminEmpoyeeTotalPondCount;
import df.farmpondstwo.Models.Class_MachineDetails;
import df.farmpondstwo.Models.Class_farmponddetails;
import df.farmpondstwo.Models.DefaultResponse;
import df.farmpondstwo.Models.District;
import df.farmpondstwo.Models.ErrorUtils;
import df.farmpondstwo.Models.Farmer;
import df.farmpondstwo.Models.Location_Data;
import df.farmpondstwo.Models.Location_DataList;
import df.farmpondstwo.Models.Panchayat;
import df.farmpondstwo.Models.State;
import df.farmpondstwo.Models.Taluka;
import df.farmpondstwo.Models.UserData;
import df.farmpondstwo.Models.UserDataList;
import df.farmpondstwo.Models.ValidateSyncRequest;
import df.farmpondstwo.Models.ValidateSyncResponse;
import df.farmpondstwo.Models.Village;
import df.farmpondstwo.Models.Year;
import df.farmpondstwo.remote.Class_ApiUtils;
import df.farmpondstwo.remote.Interface_userservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.android.volley.Response;

public class Activity_ViewFarmers extends AppCompatActivity {

    public static final String sharedpreferenc_studentid = "studentid_edit";
    public static final String key_studentid = "str_studentID_edit";
    public static final String sharedpreferenc_studentid_pay = "studentid";
    public static final String key_studentid_pay = "str_studentID";


    public static final String sharedpreferenc_camera = "cameraflag";
    public static final String key_flagcamera = "flag_camera";

    UserDataList[] userDataLists;
    UserDataList class_userDatalist = new UserDataList();

    Interface_userservice userService1;
    public static String compressedfilepaths = "";
    static Boolean digitalcamerabuttonpressed = false;
    static byte[] signimageinbytesArray = {0};
    public static Context context;
    ListView farmer_listview;
    EditText searchStudent_et;
    Button search_BT;
    String str_loginuserID;
    Boolean isInternetPresent = false;
    Boolean isInternetPresent2 = false;
    Boolean isInternetPresent3 = false;
    Class_InternetDectector internetDectector, internetDectector2, internetDectector3;
    String str_searchStudent_et;
    int count1;
    /*  Class_StudentListDetails Objclass_studentListDetails;
      Class_StudentListDetails[] Arrayclass_studentListDetails;*/
    LinearLayout studentlist_LL, Nostudentlist_LL, application_LL, mainstuName_LL;
    ImageButton details_show_ib;
    // CustomAdapter adapter;
    Spinner yearlist_SP, statelist_SP, districtlist_SP, taluklist_SP, villagelist_SP, grampanchayatlist_SP;
    /*  Class_SandBoxDetails[] arrayObj_Class_sandboxDetails, arrayObj_Class_sandboxDetails2;
      Class_SandBoxDetails obj_Class_sandboxDetails;
      Class_academicDetails[] arrayObj_Class_academicDetails, arrayObj_Class_academicDetails2;
      Class_academicDetails obj_Class_academicDetails;
      Class_ClusterDetails[] arrayObj_Class_clusterDetails, arrayObj_Class_clusterDetails2;
      Class_ClusterDetails obj_Class_clusterDetails;
      Class_InsituteDetails[] arrayObj_Class_InstituteDetails, arrayObj_Class_InstituteDetails2;
      Class_InsituteDetails obj_Class_instituteDetails;
      Class_LevelDetails[] arrayObj_Class_LevelDetails, arrayObj_Class_LevelDetails2;
      Class_LevelDetails obj_Class_levelDetails;
      Class_StudentStatus[] arrayObj_Class_StudentStatus, arrayObj_Class_StudentStatus2;
      Class_StudentStatus Obj_Class_StudentStatus;
      Class_ViewStudentData[] arrayObj_Class_ViewStudentData, arrayObj_Class_ViewStudentData2;
      Class_ViewStudentData Obj_Class_ViewStudentData;*/
    String selected_studentstatus = "", sp_strsand_ID, selected_sandboxName,
            sp_straca_ID, selected_academicname, sp_strClust_ID, selected_clusterName,
            sp_strInst_ID, selected_instituteName, sp_strLev_ID, selected_levelName;
    LinearLayout spinnerlayout_ll;
    ImageButton search_ib, downarrow_ib, uparrow_ib;
    TextView viewspinner_tv;
    TableLayout tableLayout;
    // SoapPrimitive response_soapobj_studentStatus;
    boolean issubmitclickedOnAlertDialog = false;
    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_stuid_Obj;
    SharedPreferences sharedpref_stuid_pay_Obj;
    String str_studentid_sharedpreference, str_studentid_pay_sharedpreference;
    ////////////////////////////////ViewFarmer modifications sept 16th2019
    AutoCompleteTextView searchautofill;
    InputMethodManager imm;
    String[] FarmerNameArray;
    int intval_searchflag = 0, search_pos;
    String str_selected;
    LinearLayout mainviewfarmerlist_ll, farmernameandcodelist_ll, farmerimagelayout_ll, nofarmerRecords_ll;
    Toolbar toolbar;
    // ShowSearchView showSearchView;
    EditText search_et;
    FarmerListViewAdapter farmerListViewAdapter;
    ViewHolder holder;
    Year[] arrayObj_Class_yearDetails2;
    Year Obj_Class_yearDetails;
    State[] arrayObj_Class_stateDetails2;
    State Obj_Class_stateDetails;
    District[] arrayObj_Class_DistrictListDetails2;
    District Obj_Class_DistrictDetails;
    Taluka[] arrayObj_Class_TalukListDetails2;
    Taluka Obj_Class_TalukDetails;
    Village[] arrayObj_Class_VillageListDetails2;
    Village Obj_Class_VillageListDetails;
    Panchayat[] arrayObj_Class_GrampanchayatListDetails2;
    Panchayat Obj_Class_GramanchayatDetails;
    Farmer[] arrayObj_Class_FarmerListDetails2, class_farmerlistdetails_arrayobj2;
    Farmer Obj_Class_FarmerListDetails;
    String selected_year, sp_stryear_ID, sp_strstate_ID, selected_district, selected_stateName, sp_strdistrict_ID, sp_strdistrict_state_ID, sp_strTaluk_ID, selected_taluk, sp_strVillage_ID, selected_village, sp_strgrampanchayat_ID, selected_grampanchayat;
    String sp_strstate_ID_new = "";
    String mCurrentPhotoPath = "", str_flagforcamera;
    Bitmap bitmap;
    String str_img = "";
    SharedPreferences sharedpref_camera_Obj;
    Uri imageUri = null;
    Bitmap scaledBitmap = null;
    // HttpHandler sh;
    String str_status_msg;
    String[] StrArray_farmerimage, StrArray_farmerlist_panchayatid, StrArray_farmerlist_villageid, StrArray_farmerlist_talukid,
            StrArray_farmerlist_distid, StrArray_farmerlist_stateid, StrArray_farmerlist_yearid, StrArray_farmerlist_farmerid,
            StrArray_year_ID, StrArray_year, strArray_farmername, StrArray_farmercode, strArray_stateid, strArray_statename,
            strArray_districtid, strArray_districtname, strArray_selecteddistname, strArray_Dist_stateid, strArray_taluk_distid,
            strArray_talukid, strArray_talukname, strArray_village_talukid, strArray_villageid, strArray_villagename,
            strArray_village_panchayatid, strArray_panchayat_talukid, strArray_panchayatid, strArray_panchayatname,
            strArray_farmpondcount, strArray_farmerBase64, strArray_farmerMname, strArray_farmerLname, strArray_farmerage,
            strArray_farmercellnumber, strArray_farmerincome, strArray_farmerfamilymember, strArray_farmerIDprooftype,
            strArray_farmerIDproofnumber;
    List<State> stateList;
    List<District> districtList;
    List<Taluka> talukList;
    List<Village> villageList;
    List<Panchayat> panchayatList;
    List<Farmer> farmerList;
    List<Year> yearList;
    private ArrayList<Farmer> originalViewFarmerList = null;
    private ArrayList<Farmer> ViewFarmerList_arraylist;


    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String str_selected_farmerID, str_selected_farmerID_forimagesaving, str_selected_farmerName;

    public static final String sharedpreferenc_farmerid = "sharedpreference_farmer_id";
    public static final String Key_FarmerID = "farmer_id";
    public static final String Key_FarmerName = "farmer_name";
    SharedPreferences sharedpref_farmerid_Obj;

    String pondImage1, pondImageId1, pondImageType1;
    String pondImage2, pondImageId2, pondImageType2;
    String pondImage3, pondImageId3, pondImageType3;


    private ImageLoadingListener imageListener;
    public DisplayImageOptions displayoption;
    ImageLoader imgLoader = ImageLoader.getInstance();

    int sel_yearsp = 0, sel_statesp = 0, sel_districtsp = 0, sel_taluksp = 0, sel_villagesp = 0, sel_grampanchayatsp = 0;

    public static final String sharedpreferenc_selectedspinner = "sharedpreferenc_selectedspinner";
    public static final String Key_sel_yearsp = "sel_yearsp";
    public static final String Key_sel_statesp = "sel_statesp";
    public static final String Key_sel_districtsp = "sel_districtsp";
    public static final String Key_sel_taluksp = "sel_taluksp";
    public static final String Key_sel_villagesp = "sel_villagesp";
    public static final String Key_sel_grampanchayatsp = "sel_grampanchayatsp";
    SharedPreferences sharedpref_spinner_Obj;

    byte[] b;

    byte[] imageBytes_list;


    Class_farmponddetails[] class_farmerandpond_details_array_obj;

    Class_MachineDetails[] class_machinedetails_array_obj;
    Class_RemarksDetails[] class_remarksdetails_array_obj;
    String StateCount="0",DistrictCount="0",TalukaCount="0",PanchayatCount="0",VillageCount="0",YearCount="0",MachineCount="0",MachineCostCount="0",Farmer_Count="0",Pond_Count="0",Sync_ID="";

    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";
    public static final String Key_syncId = "Sync_ID";
    SharedPreferences sharedpreferencebook_usercredential_Obj;

    String str_farmerID, str_employee_id;


    FloatingActionButton addfarmerdetails_fab;


    ProgressDialog pdLoading_fetch_farmponddetails, pdLoading_fetch_machinedetails, pdLoading_fetch_remarksdetails;
    int int_jsonarraymachinelength, int_jsonarrayremarkslength;

    String str_total_no_days, str_constructed_date, str_submitted_date, str_farmpond_cost, str_machine_code,
            str_farmpondcode, str_startdate, str_farmpond_remarks, str_farmpond_amtcollected, str_farmpond_status,
            str_approvalstatus, str_approvalremarks, str_approvedby, str_approveddate, str_donorname, str_latitude, str_longitude,
            str_acres, str_gunta, str_crop_beforepond, str_crop_afterpond;


    String str_return;
    String str_imageurltobase64_farmerimage;

    Class_farmerimageBase64[] class_farmerimageBase64_array;
    Location_DataList[] location_dataLists;
    Location_DataList class_location_dataList = new Location_DataList();


    public static final String MyPREFERENCE_SyncId = "MyPref_SyncId" ;
    public static final String SyncId = "SyncId";
    SharedPreferences shared_syncId;
    int k;
    private String versioncode;
    String VersionStatus="false";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_farmers);

        userService1 = Class_ApiUtils.getUserService();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Farmer List");
        getSupportActionBar().setTitle("");
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Farmer List");*/
        initImageLoader(getApplicationContext());
        displayoption = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.profileimg)
                .showStubImage(R.drawable.profileimg)
                .showImageForEmptyUri(R.drawable.profileimg).cacheInMemory()
                .cacheOnDisc().build();

        imageListener = new ImageDisplayListener();


        context = getApplicationContext();
        //sh = new HttpHandler();
        stateList = new ArrayList<>();
        districtList = new ArrayList<>();
        talukList = new ArrayList<>();
        villageList = new ArrayList<>();
        panchayatList = new ArrayList<>();
        farmerList = new ArrayList<>();
        yearList = new ArrayList<>();

        ViewFarmerList_arraylist = new ArrayList<Farmer>();

        farmerListViewAdapter = new FarmerListViewAdapter(Activity_ViewFarmers.this, ViewFarmerList_arraylist);

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

//        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs.getString("login_userid", "nothing");

//        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        shared_syncId = getSharedPreferences(MyPREFERENCE_SyncId, Context.MODE_PRIVATE);

        sharedpref_farmerid_Obj = getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_selected_farmerID = sharedpref_farmerid_Obj.getString(Key_FarmerID, "").trim();

        sharedpref_farmerid_Obj = getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_selected_farmerName = sharedpref_farmerid_Obj.getString(Key_FarmerName, "").trim();


        sharedpref_stuid_Obj = getSharedPreferences(sharedpreferenc_studentid, Context.MODE_PRIVATE);
        str_studentid_sharedpreference = sharedpref_stuid_Obj.getString(key_studentid, "").trim();


        sharedpref_stuid_pay_Obj = getSharedPreferences(sharedpreferenc_studentid_pay, Context.MODE_PRIVATE);
        str_studentid_pay_sharedpreference = sharedpref_stuid_pay_Obj.getString(key_studentid_pay, "").trim();

        sharedpref_camera_Obj = getSharedPreferences(sharedpreferenc_camera, Context.MODE_PRIVATE);
        str_flagforcamera = sharedpref_camera_Obj.getString(key_flagcamera, "").trim();


        sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        sel_yearsp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_yearsp, "").trim());
        sel_statesp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_statesp, "").trim());
        sel_districtsp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_districtsp, "").trim());
        sel_taluksp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_taluksp, "").trim());
        sel_villagesp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_villagesp, "").trim());
        sel_grampanchayatsp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_grampanchayatsp, "").trim());

        Log.e("tag", "sel_districtsp=" + sel_districtsp + "sel_statesp=" + sel_statesp);


        sharedpref_farmerid_Obj = getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_farmerID = sharedpref_farmerid_Obj.getString(Key_FarmerID, "").trim();

        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_employee_id = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        Log.e("tag", "str_employee_id=" + str_employee_id);
        str_imageurltobase64_farmerimage = "empty";



       /* Intent intent = getIntent();
        String str_value_constant=intent.getStringExtra("value_constant");
        if(str_value_constant.equalsIgnoreCase("1")){
            sel_yearsp=Integer.parseInt(intent.getStringExtra("sel_yearsp"));
            sel_statesp=Integer.parseInt(intent.getStringExtra("sel_statesp"));
            sel_districtsp=Integer.parseInt(intent.getStringExtra("sel_districtsp"));
            sel_taluksp=Integer.parseInt(intent.getStringExtra("sel_taluksp"));
            sel_villagesp=Integer.parseInt(intent.getStringExtra("sel_villagesp"));
            sel_grampanchayatsp=Integer.parseInt(intent.getStringExtra("sel_grampanchayatsp"));
        }*/

        //   searchautofill = (AutoCompleteTextView) findViewById(R.id.Search);
        //  searchautofill.setInputType(InputType.TYPE_NULL);
//        Typeface typefacereg = Typeface.createFromAsset(getAssets(), "fonts/laouiregular.ttf");
//        searchautofill.setTypeface(typefacereg);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout_stulist);

        farmer_listview = (ListView) findViewById(R.id.farmer_LISTVIEW);
        yearlist_SP = (Spinner) findViewById(R.id.yearlist_farmer_SP);
        statelist_SP = (Spinner) findViewById(R.id.statelist_farmer_SP);

        districtlist_SP = (Spinner) findViewById(R.id.districtlist_farmer_SP);
        taluklist_SP = (Spinner) findViewById(R.id.taluklist_farmer_SP);
        villagelist_SP = (Spinner) findViewById(R.id.villagelist_farmer_SP);
        grampanchayatlist_SP = (Spinner) findViewById(R.id.grampanchayatlist_farmer_SP);
        spinnerlayout_ll = (LinearLayout) findViewById(R.id.spinnerlayout_LL);
        viewspinner_tv = (TextView) findViewById(R.id.viewspinner_TV);

        downarrow_ib = (ImageButton) findViewById(R.id.downarrow_IB);
        uparrow_ib = (ImageButton) findViewById(R.id.uparrow_IB);

        search_et = (EditText) findViewById(R.id.search_et);
        str_return = "no";

        try {
            versioncode = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            VersionStatus = extras.getString("VersionStatus");
            Log.e("tag","VersionStatus="+VersionStatus);

        }


        search_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewspinner_tv.setText("Show");
                spinnerlayout_ll.setVisibility(View.GONE);
                downarrow_ib.setVisibility(View.GONE);
                uparrow_ib.setVisibility(View.VISIBLE);
                String text = search_et.getText().toString().toLowerCase(Locale.getDefault());
                farmerListViewAdapter.filter(text, originalViewFarmerList);

            }
        });


        uparrow_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewspinner_tv.setText("Hide");
                spinnerlayout_ll.setVisibility(View.VISIBLE);
                downarrow_ib.setVisibility(View.VISIBLE);
                uparrow_ib.setVisibility(View.GONE);

            }
        });

        downarrow_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewspinner_tv.setText("Show");
                spinnerlayout_ll.setVisibility(View.GONE);
                downarrow_ib.setVisibility(View.GONE);
                uparrow_ib.setVisibility(View.VISIBLE);

            }
        });
   /*     @SuppressLint("ResourceType")
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_in);
        animation1.setDuration(1000);
        spinnerlayout_ll.setAnimation(animation1);
        spinnerlayout_ll.animate();
        animation1.start();*/


//        if (isInternetPresent) {
//            deleteYearTable_B4insertion();
//            deleteStateTable_B4insertion();
//            deleteDistrictTable_B4insertion();
//            deleteTalukTable_B4insertion();
//            deleteVillageTable_B4insertion();
//            deleteGrampanchayatTable_B4insertion();
//            deleteViewFarmerlistTable_B4insertion();
//
//
//            GetDropdownValues();
//
//        } else {
//            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
//        }
//
//        if (isInternetPresent) {
//
//            GetFarmerDetails();
//        } else {
//            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
//
//        }

        yearlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_yearDetails = (Year) yearlist_SP.getSelectedItem();
                sp_stryear_ID = Obj_Class_yearDetails.getAcademic_ID().toString();
                selected_year = yearlist_SP.getSelectedItem().toString();
                int sel_yearsp_new = yearlist_SP.getSelectedItemPosition();
                //  farmerListViewAdapter.notifyDataSetChanged();

                if (sel_yearsp_new != sel_yearsp) {
                    sel_yearsp = sel_yearsp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
                    statelist_SP.setSelection(0);
                    districtlist_SP.setSelection(0);
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        statelist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Obj_Class_stateDetails = (State) statelist_SP.getSelectedItem();
                sp_strstate_ID = Obj_Class_stateDetails.getStateID().toString();
                selected_stateName = statelist_SP.getSelectedItem().toString();
                int sel_statesp_new = statelist_SP.getSelectedItemPosition();


                Class_SaveSharedPreference.setPREF_stateposition(Activity_ViewFarmers.this, position);
                Update_districtid_spinner(sp_strstate_ID);
                if (sel_statesp_new != sel_statesp) {
                    sel_statesp = sel_statesp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
                    districtlist_SP.setSelection(0);
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        districtlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_DistrictDetails = (District) districtlist_SP.getSelectedItem();
                sp_strdistrict_ID = Obj_Class_DistrictDetails.getDistrictID();
                sp_strdistrict_state_ID = Obj_Class_DistrictDetails.getStateID();
                selected_district = districtlist_SP.getSelectedItem().toString();
                int sel_districtsp_new = districtlist_SP.getSelectedItemPosition();
                // Log.e("selected_district", " : " + selected_district);
//                Log.i("sp_strdistrict_state_ID", " : " + sp_strdistrict_state_ID);
                Log.e("sp_strdistrict_ID", " : " + sp_strdistrict_ID);

                Class_SaveSharedPreference.setPREF_districtposition(Activity_ViewFarmers.this, position);
                Update_TalukId_spinner(sp_strdistrict_ID);

                if (sel_districtsp_new != sel_districtsp) {
                    sel_districtsp = sel_districtsp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        taluklist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_TalukDetails = (Taluka) taluklist_SP.getSelectedItem();
                sp_strTaluk_ID = Obj_Class_TalukDetails.getTalukaID();
                selected_taluk = taluklist_SP.getSelectedItem().toString();
                int sel_taluksp_new = taluklist_SP.getSelectedItemPosition();

                Class_SaveSharedPreference.setPREF_talukposition(Activity_ViewFarmers.this, position);
                Update_GramPanchayatID_spinner(sp_strTaluk_ID);
                //Update_VillageId_spinner(sp_strgrampanchayat_ID);
                if (sel_taluksp_new != sel_taluksp) {
                    sel_taluksp = sel_taluksp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();

                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        grampanchayatlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_GramanchayatDetails = (Panchayat) grampanchayatlist_SP.getSelectedItem();
                sp_strgrampanchayat_ID = Obj_Class_GramanchayatDetails.getPanchayatID().toString();
                selected_grampanchayat = Obj_Class_GramanchayatDetails.getPanchayatName().toString();
                int sel_grampanchayatsp_new = grampanchayatlist_SP.getSelectedItemPosition();


                Log.e("sp_strpachayat_ID", sp_strgrampanchayat_ID);


                Class_SaveSharedPreference.setPREF_gramanchayatposition(Activity_ViewFarmers.this, position);

                Update_VillageId_spinner(sp_strgrampanchayat_ID, sp_strTaluk_ID);

                if (sel_grampanchayatsp_new != sel_grampanchayatsp) {
                    sel_grampanchayatsp = sel_grampanchayatsp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        villagelist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_VillageListDetails = (Village) villagelist_SP.getSelectedItem();
                sp_strVillage_ID = Obj_Class_VillageListDetails.getVillageID();
                selected_village = villagelist_SP.getSelectedItem().toString();

                int sel_villagesp_new = villagelist_SP.getSelectedItemPosition();

                Class_SaveSharedPreference.setPREF_villageposition(Activity_ViewFarmers.this, position);
                if (sel_villagesp_new != sel_villagesp) {
                    sel_villagesp = sel_villagesp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();

                    // grampanchayatlist_SP.setSelection(0);
                }


                Log.e("yearselected", yearlist_SP.getSelectedItem().toString());


                if (statelist_SP.getSelectedItem().toString().equalsIgnoreCase("Select")
                        && districtlist_SP.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();

                } else {
                    Log.e("yearid", sp_stryear_ID + "-" + sp_strstate_ID + "-" + sp_strdistrict_ID + "-" + sp_strTaluk_ID + "-" + sp_strVillage_ID + "-" + sp_strgrampanchayat_ID);

                    Update_ids_farmerlist_listview(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strgrampanchayat_ID);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        farmer_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                str_selected_farmerID = ViewFarmerList_arraylist.get(position).getFarmerID();
                str_selected_farmerName = ViewFarmerList_arraylist.get(position).getFarmerFirstName();

                Log.e("str_selected_farmerID", str_selected_farmerID);
                Intent i = new Intent(Activity_ViewFarmers.this, EachFarmPondDetails_Activity.class);
                i.putExtra("sel_yearsp", String.valueOf(sel_yearsp));
                i.putExtra("sel_statesp", String.valueOf(sel_statesp));
                i.putExtra("sel_districtsp", String.valueOf(sel_districtsp));
                i.putExtra("sel_taluksp", String.valueOf(sel_taluksp));
                i.putExtra("sel_villagesp", String.valueOf(sel_villagesp));
                i.putExtra("sel_grampanchayatsp", String.valueOf(sel_grampanchayatsp));

                SharedPreferences.Editor myprefs_farmerid = sharedpref_farmerid_Obj.edit();
                myprefs_farmerid.putString(Key_FarmerID, str_selected_farmerID);
                myprefs_farmerid.putString(Key_FarmerName, str_selected_farmerName);
                myprefs_farmerid.apply();

                SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
                myprefs_spinner.putString(Key_sel_yearsp, String.valueOf(sel_yearsp));
                myprefs_spinner.putString(Key_sel_statesp, String.valueOf(sel_statesp));
                myprefs_spinner.putString(Key_sel_districtsp, String.valueOf(sel_districtsp));
                myprefs_spinner.putString(Key_sel_taluksp, String.valueOf(sel_taluksp));
                myprefs_spinner.putString(Key_sel_villagesp, String.valueOf(sel_villagesp));
                myprefs_spinner.putString(Key_sel_grampanchayatsp, String.valueOf(sel_grampanchayatsp));

                myprefs_spinner.apply();


                i.putExtra("farmer_name", ViewFarmerList_arraylist.get(position).getFarmerFirstName());
                i.putExtra("farmer_id", ViewFarmerList_arraylist.get(position).getFarmerID());

                startActivity(i);
                // finish();

            }
        });

        if(VersionStatus.equalsIgnoreCase("true")){
            internetDectector2 = new Class_InternetDectector(getApplicationContext());
            isInternetPresent2 = internetDectector2.isConnectingToInternet();
            if (isInternetPresent2) {

                //GetDropdownValues();
                deleteStateRestTable_B4insertion();
                deleteDistrictRestTable_B4insertion();
                deleteTalukRestTable_B4insertion();
                deleteGrampanchayatRestTable_B4insertion();
                deleteVillageRestTable_B4insertion();
                deleteYearRestTable_B4insertion();
                deleteMachineRestTable_B4insertion();
                deleteViewFarmerlistTable_B4insertion();
                delete_FarmPondDetails_fromServer_B4insertion();

                DBCreate_RemarksDetails();
                ViewFarmerlistdetailsRestTable_B4insertion();
                FarmpondRest_detailsTable_B4insertion();

                GetDropdownValuesRestData();
                GetFarmer_PondValuesRestData();

                String Sync_IDNew = shared_syncId.getString(SyncId, "");
                Log.e("tag","Sync_IDNew="+Sync_IDNew);
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }else{
            internetDectector2 = new Class_InternetDectector(getApplicationContext());
            isInternetPresent2 = internetDectector2.isConnectingToInternet();
            if (isInternetPresent2) {
                CountCheckList();
                UserDataCountCheckList();
                stateListRest_dbCount();

            }
        }


        uploadfromDB_Yearlist();
        uploadfromDB_Statelist();
        uploadfromDB_Districtlist();
        uploadfromDB_Taluklist();
        uploadfromDB_Villagelist();
        uploadfromDB_Grampanchayatlist();
        //uploadfromDB_Farmerlist();


        addfarmerdetails_fab = (FloatingActionButton) findViewById(R.id.addfarmerdetails_fab);
        addfarmerdetails_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
                // Intent i = new Intent(getApplicationContext(),AddFarmer_Activity.class);
                Intent i = new Intent(getApplicationContext(), AddFarmer_Activity1.class);
                startActivity(i);
                finish();
            }
        });


    }//oncreate


    public static void initImageLoader(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.profileimg)
                .showImageOnFail(R.drawable.profileimg)
                .resetViewBeforeLoading().cacheOnDisc()
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(options)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();

        ImageLoader.getInstance().init(config);
    }


    //////////////////////////////23May2020/////////////////////////////////

    public void DBCreate_CountDetailsRest_insert_2SQLiteDB(String str_sCount, String str_dCount, String str_tCount, String str_pCount,String str_vCount,String yearCount,String machineCount,String mCostCount,String str_Sync_Id) {
        SQLiteDatabase db_locationCount = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_locationCount.execSQL("CREATE TABLE IF NOT EXISTS LocationCountListRest(StateCount VARCHAR,DistrictCount VARCHAR,TalukaCount VARCHAR,PanchayatCount VARCHAR,VillageCount VARCHAR,YearCount VARCHAR,MachineCount VARCHAR,MachineCostCount VARCHAR,Farmer_Count VARCHAR,Pond_Count VARCHAR,Sync_ID VARCHAR);");

        String SQLiteQuery = "INSERT INTO LocationCountListRest (StateCount,DistrictCount,TalukaCount,PanchayatCount,VillageCount,YearCount,MachineCount,MachineCostCount,Sync_ID)" +
                " VALUES ('" + str_sCount + "','" + str_dCount +"','"+ str_tCount + "','" + str_pCount +"','"+str_vCount + "','" + yearCount + "','" + machineCount + "','" + mCostCount + "','" + str_Sync_Id +"');";
        db_locationCount.execSQL(SQLiteQuery);

        Log.e("str_sCount DB", str_sCount);
        Log.e("str_dCount DB", str_dCount);
        Log.e("str_tCount DB", str_tCount);
        Log.e("str_Sync_Id DB", str_Sync_Id);
        db_locationCount.close();
    }
    public void delete_CountDetailsRestTable_B4insertion() {

        SQLiteDatabase db_locationCount = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_locationCount.execSQL("CREATE TABLE IF NOT EXISTS LocationCountListRest(StateCount VARCHAR,DistrictCount VARCHAR,TalukaCount VARCHAR,PanchayatCount VARCHAR,VillageCount VARCHAR,YearCount VARCHAR,MachineCount VARCHAR,MachineCostCount VARCHAR,Farmer_Count VARCHAR,Pond_Count VARCHAR,Sync_ID VARCHAR);");
        Cursor cursor = db_locationCount.rawQuery("SELECT * FROM LocationCountListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_locationCount.delete("LocationCountListRest", null, null);

        }
        db_locationCount.close();
    }
    public void DBCreate_UserDataCountListRest_insert_2SQLiteDB(String str_FarmerCount, String str_PondCount) {
        SQLiteDatabase db_userdataCount = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_userdataCount.execSQL("CREATE TABLE IF NOT EXISTS UserDataCountListRest(Farmer_Count VARCHAR,Pond_Count VARCHAR);");

        String SQLiteQuery = "INSERT INTO UserDataCountListRest (Farmer_Count,Pond_Count)" +
                " VALUES ('" + str_FarmerCount + "','" + str_PondCount + "');";
        db_userdataCount.execSQL(SQLiteQuery);

        Log.e("str_FarmerCount DB", str_FarmerCount);
        Log.e("str_PondCount DB", str_PondCount);

        db_userdataCount.close();
    }
    public void delete_UserDataCountListRestTable_B4insertion() {

        SQLiteDatabase db_userdataCount = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_userdataCount.execSQL("CREATE TABLE IF NOT EXISTS UserDataCountListRest(Farmer_Count VARCHAR,Pond_Count VARCHAR);");
        Cursor cursor = db_userdataCount.rawQuery("SELECT * FROM UserDataCountListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_userdataCount.delete("UserDataCountListRest", null, null);

        }
        db_userdataCount.close();
    }

    public void DBCreate_StatedetailsRest_insert_2SQLiteDB(String str_stateID, String str_statename, String str_yearid, int i) {
        SQLiteDatabase db_statelist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        //  db_statelist.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR);");
        db_statelist.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");


        if (i == 0) {
            String SQLiteQuery = "INSERT INTO StateListRest (StateID,StateName)" +
                    " VALUES ('" + "0" + "','" + "Select" + "');";
            db_statelist.execSQL(SQLiteQuery);
        }

        String SQLiteQuery = "INSERT INTO StateListRest (StateID,StateName)" +
                " VALUES ('" + str_stateID + "','" + str_statename + "');";
        db_statelist.execSQL(SQLiteQuery);

        Log.e("str_stateID DB", str_stateID);
        Log.e("str_statename DB", str_statename);
        Log.e("str_stateyearid DB", str_yearid);
        db_statelist.close();
    }

    public void deleteStateRestTable_B4insertion() {

        SQLiteDatabase db_statelist_delete = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_statelist_delete.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor = db_statelist_delete.rawQuery("SELECT * FROM StateListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_statelist_delete.delete("StateListRest", null, null);

        }
        db_statelist_delete.close();
    }

    public void DBCreate_DistrictdetailsRest_insert_2SQLiteDB(String str_districtID, String str_districtname, String str_yearid, String str_stateid, int i) {
        SQLiteDatabase db_districtlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_districtlist.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");


        if (i == 0) {
            String SQLiteQuery = "INSERT INTO DistrictListRest (DistrictID, DistrictName,Distr_yearid,Distr_Stateid)" +
                    " VALUES ('" + "0" + "','" + "Select" + "','" + "0" + "','" + "0" + "');";
            db_districtlist.execSQL(SQLiteQuery);
        }
        String SQLiteQuery = "INSERT INTO DistrictListRest (DistrictID, DistrictName,Distr_yearid,Distr_Stateid)" +
                " VALUES ('" + str_districtID + "','" + str_districtname + "','" + str_yearid + "','" + str_stateid + "');";
        db_districtlist.execSQL(SQLiteQuery);

//        Log.e("str_districtID DB", str_districtID);
        Log.e("str_districtname DB", str_districtname);
//        Log.e("str_yearid DB", str_yearid);
//        Log.e("str_stateid DB", str_stateid);
        db_districtlist.close();
    }

    public void deleteDistrictRestTable_B4insertion() {

        SQLiteDatabase db_districtlist_delete = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_districtlist_delete.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db_districtlist_delete.rawQuery("SELECT * FROM DistrictListRest", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_districtlist_delete.delete("DistrictListRest", null, null);

        }
        db_districtlist_delete.close();
    }

    public void DBCreate_TalukdetailsRest_insert_2SQLiteDB(String str_talukID, String str_talukname, String str_districtid, int i) {

        SQLiteDatabase db_taluklist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_taluklist.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");


        if (i == 0) {
            String SQLiteQuery = "INSERT INTO TalukListRest (TalukID, TalukName,Taluk_districtid)" +
                    " VALUES ('" + "0" + "','" + "Select" + "','" + "0" + "');";
            db_taluklist.execSQL(SQLiteQuery);
        }

        String SQLiteQuery = "INSERT INTO TalukListRest (TalukID, TalukName,Taluk_districtid)" +
                " VALUES ('" + str_talukID + "','" + str_talukname + "','" + str_districtid + "');";
        db_taluklist.execSQL(SQLiteQuery);

//        Log.e("str_talukID DB", str_talukID);
        Log.e("str_talukname DB", str_talukname);
//        Log.e("str_districtid DB", str_districtid);
        db_taluklist.close();
    }

    public void deleteTalukRestTable_B4insertion() {

        SQLiteDatabase db_taluklist_delete = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_taluklist_delete.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db_taluklist_delete.rawQuery("SELECT * FROM TalukListRest", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_taluklist_delete.delete("TalukListRest", null, null);

        }
        db_taluklist_delete.close();
    }

    public void DBCreate_VillagedetailsRest_insert_2SQLiteDB(String str_villageID, String str_village, String str_talukid, String str_village_panchayatid, int i) {
        SQLiteDatabase db_villagelist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_villagelist.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,PanchayatID VARCHAR);");


        if (i == 0) {
            String SQLiteQuery = "INSERT INTO VillageListRest (VillageID, Village,TalukID,PanchayatID)" +
                    " VALUES ('" + "0" + "','" + "Select" + "','" + "0" + "','" + "0" + "');";
            db_villagelist.execSQL(SQLiteQuery);
        }

        String SQLiteQuery = "INSERT INTO VillageListRest (VillageID, Village,TalukID,PanchayatID)" +
                " VALUES ('" + str_villageID + "','" + str_village + "','" + str_talukid + "','" + str_village_panchayatid + "');";
        db_villagelist.execSQL(SQLiteQuery);

//        Log.e("str_villageID DB", str_villageID);
//        Log.e("str_village DB", str_village);
//        Log.e("str_talukid DB", str_talukid);
        db_villagelist.close();
    }

    public void deleteVillageRestTable_B4insertion() {

        SQLiteDatabase db_villagelist_delete = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        // db_villagelist_delete.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_villagelist_delete.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,PanchayatID VARCHAR);");
        Cursor cursor1 = db_villagelist_delete.rawQuery("SELECT * FROM VillageListRest", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_villagelist_delete.delete("VillageListRest", null, null);

        }
        db_villagelist_delete.close();
    }

    public void DBCreate_GrampanchayatdetailsRest_insert_2SQLiteDB(String str_grampanchayatID, String str_grampanchayat, String str_distid, int i) {

        SQLiteDatabase db_panchayatlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_panchayatlist.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatListRest(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");


        if (i == 0) {
            String SQLiteQuery = "INSERT INTO GrampanchayatListRest (GramanchayatID, Gramanchayat,Panchayat_DistID)" +
                    " VALUES ('" + "0" + "','" + "Select" + "','" + "0" + "');";
            db_panchayatlist.execSQL(SQLiteQuery);
        }

        String SQLiteQuery = "INSERT INTO GrampanchayatListRest (GramanchayatID, Gramanchayat,Panchayat_DistID)" +
                " VALUES ('" + str_grampanchayatID + "','" + str_grampanchayat + "','" + str_distid + "');";
        db_panchayatlist.execSQL(SQLiteQuery);
        // }
//        Log.e("str_panchayatID DB", str_grampanchayatID);
        //  Log.e("str_panchayat DB", str_grampanchayat);
//        Log.e("str_distid DB", str_distid);
        db_panchayatlist.close();
    }

    public void deleteGrampanchayatRestTable_B4insertion() {

        SQLiteDatabase db_grampanchayatlist_delete = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_grampanchayatlist_delete.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatListRest(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
        Cursor cursor1 = db_grampanchayatlist_delete.rawQuery("SELECT * FROM GrampanchayatListRest", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_grampanchayatlist_delete.delete("GrampanchayatListRest", null, null);

        }
        db_grampanchayatlist_delete.close();
    }

    public void DBCreate_YeardetailsRest_insert_2SQLiteDB(String str_yearID, String str_yearname, int i) {
        SQLiteDatabase db_yearlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR);");

        String SQLiteQuery = "INSERT INTO YearListRest (YearID, YearName)" +
                " VALUES ('" + str_yearID + "','" + str_yearname + "');";
        db_yearlist.execSQL(SQLiteQuery);

        Log.e("str_yearname DB", str_yearname);
        db_yearlist.close();
    }

    public void deleteYearRestTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM YearListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("YearListRest", null, null);

        }
        db_yearlist_delete.close();
    }

    public void DBCreate_MachineDetailsRest(String str_machinename, String str_machineid) {
        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineIDDB VARCHAR);");

        String SQLiteQuery = "INSERT INTO MachineDetails_fromServerRest (MachineNameDB,MachineIDDB)" +
                " VALUES ('" + str_machinename + "','" + str_machineid + "');";

        db1.execSQL(SQLiteQuery);
        db1.close();

    }

    public void deleteMachineRestTable_B4insertion() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineIDDB VARCHAR);");


        Cursor cursor = db1.rawQuery("SELECT * FROM MachineDetails_fromServerRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db1.delete("MachineDetails_fromServerRest", null, null);

        }
        db1.close();
    }

    public void ViewFarmerlistdetailsRestTable_B4insertion() {

        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");

        Cursor cursor = db_viewfarmerlist.rawQuery("SELECT * FROM ViewFarmerListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_viewfarmerlist.delete("ViewFarmerListRest", null, null);

        }
        db_viewfarmerlist.close();
    }

    public void FarmpondRest_detailsTable_B4insertion() {

        SQLiteDatabase db_viewfarmpondlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmpondlist.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetailsRest(MTempId INTEGER PRIMARY KEY,pondID VARCHAR,farmerID VARCHAR," +
                "academicID VARCHAR,machineID VARCHAR,pondCode VARCHAR," +
                "pondLatitude VARCHAR,pondLongitude VARCHAR,pondLength VARCHAR," +
                "pondWidth VARCHAR,pondDepth VARCHAR,pondStart VARCHAR,pondEnd VARCHAR," +
                "pondDays VARCHAR,pondCost VARCHAR,pondImage1 VARCHAR,pondImage2 VARCHAR,pondImage3 VARCHAR,pondStatus VARCHAR," +
                "submittedDate VARCHAR,submittedBy VARCHAR," +
                "createdDate VARCHAR,createdBy VARCHAR,pondTempID VARCHAR,responseOutput VARCHAR,createdUser VARCHAR,submittedUser VARCHAR);");

        Cursor cursor = db_viewfarmpondlist.rawQuery("SELECT * FROM FarmPondDetailsRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_viewfarmpondlist.delete("FarmPondDetailsRest", null, null);

        }
        db_viewfarmpondlist.close();
    }

    public void DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(String str_yearID, String str_stateID, String str_districtID,
                                                                String str_talukid, String str_villageid, String str_grampanchayatid,
                                                                String str_farmerid, String str_farmercode, String str_farmername,
                                                                String str_farmerimage, String farmpondcount, String str_imagebase64,
                                                                String str_mname, String str_lname, String str_age, String str_cellno,
                                                                String str_income, String str_member, String str_idprooftype, String str_idproofno, String Submitted_Date, String Created_By, String Created_Date, String Created_User, String ResponseOutput, String Response_Action) {


        String str_UploadedStatusFarmerprofile = "10";  //uploaded

        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");

        String SQLiteQuery = "INSERT INTO ViewFarmerListRest (DispFarmerTable_YearID,DispFarmerTable_StateID, DispFarmerTable_DistrictID," +
                "DispFarmerTable_TalukID,DispFarmerTable_VillageID,DispFarmerTable_GrampanchayatID,DispFarmerTable_FarmerID," +
                "DispFarmerTable_Farmer_Code,DispFarmerTable_FarmerName,FarmerMName_DB,FarmerLName_DB,Farmerage_DB," +
                "Farmercellno_DB,FIncome_DB,Ffamilymember_DB,FIDprooftype_DB,FIDProofNo_DB,UploadedStatusFarmerprofile_DB,FarmerImageB64str_DB,DispFarmerTable_FarmerImage," +
                "Farmpondcount,Submitted_Date,Created_By,Created_Date,Created_User,Response,Response_Action)" +
                " VALUES ('" + str_yearID + "','" + str_stateID + "','" + str_districtID + "','" + str_talukid + "','" + str_villageid + "','"
                + str_grampanchayatid + "','" + str_farmerid + "','" + str_farmercode + "','" + str_farmername + "','" + str_mname + "'," +
                "'" + str_lname + "','" + str_age + "','" + str_cellno + "','" + str_income + "','" + str_member + "','" + str_idprooftype + "','" + str_idproofno + "'," +
                "'" + str_UploadedStatusFarmerprofile + "','" + str_imagebase64 + "','" + str_farmerimage + "','" + farmpondcount + "','"
                + Submitted_Date + "','" + Created_By + "','" + Created_Date + "','" + Created_User + "','" + ResponseOutput + "','" + Response_Action + "');";

        db_viewfarmerlist.execSQL(SQLiteQuery);


//        Log.e("str_yearID DB", str_yearID);
//        Log.e("str_stateID DB", str_stateID);
//        Log.e("str_districtID DB", str_districtID);
//        Log.e("str_talukid DB", str_talukid);
//        Log.e("str_villageid DB", str_villageid);
//        Log.e("str_grampanchayatid DB", str_grampanchayatid);
//        Log.e("str_farmerid DB", str_farmerid);
        Log.e("str_farmername DB", str_farmername);
//        Log.e("str_farmerimage DB", str_farmerimage);
        db_viewfarmerlist.close();
    }

    public void deleteViewFarmerlistTable_B4insertion() {

        SQLiteDatabase db_viewfarmerlist_delete = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmerlist_delete.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");


        Cursor cursor = db_viewfarmerlist_delete.rawQuery("SELECT * FROM ViewFarmerListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_viewfarmerlist_delete.delete("ViewFarmerListRest", null, null);

        }
        db_viewfarmerlist_delete.close();
    }

    public void DBCreate_FarmpondsRest_details_2SQLiteDB(String str_farmerid, String str_farmername, String str_farmpond_id, String str_width,
                                                         String str_height, String str_depth, String str_image1, String str_base64image1, String str_pondImageId1, String pondImageType1,
                                                         String str_image2, String str_base64image2, String str_pondImageId2, String pondImageType2, String str_image3,
                                                         String str_base64image3, String str_pondImageId3, String pondImageType3, String str_total_days, String str_constr_date,
                                                         String str_submited_date, String str_pond_cost,
                                                         String str_mcode, String str_fpondcode, String str_startdate, String str_farmpond_remarks,
                                                         String str_farmpond_amtcollected, String str_farmpond_status, String str_farmerMName,
                                                         String str_farmerLName, String str_Fphonenumber, String str_FIDprooftype,
                                                         String str_FIDproofno, String str_approvalstatus, String str_approvalremarks,
                                                         String str_approvedby, String str_approveddate, String str_donorname,
                                                         String str_latitude, String str_longitude,
                                                         String str_acres, String str_gunta, String str_crop_beforepond, String str_crop_afterpond) {
        if (str_farmpond_remarks == null) {
            str_farmpond_remarks = "100";
        } else {
            if (str_farmpond_remarks.toString().trim().length() == 0) {
                str_farmpond_remarks = "100";
            } else {
                if (str_farmpond_remarks.equalsIgnoreCase("NoRemarks")) {
                    str_farmpond_remarks = "100";
                } else {
                }
            }
        }


        if (str_acres == null) {
            str_acres = "0";
        }
        if (str_gunta == null) {
            str_gunta = "0";
        }


        if (str_constr_date == null) {
            str_constr_date = "nodate";
        }
        if (str_constr_date.equalsIgnoreCase("00-00-0000 00:00:00")) {
            str_constr_date = "nodate";
        }
        if (str_submited_date == null) {
            str_submited_date = "nodate";
        }
        if (str_submited_date.equalsIgnoreCase("00-00-0000 00:00:00")) {
            str_submited_date = "nodate";
        }


       /* Log.e("DB_imageid1",str_imageid1);
        Log.e("DB_imageid2",str_imageid2);
        Log.e("DB_imageid3",str_imageid3);*/


        String str_yearID, str_stateID, str_districtID, str_talukID, str_panchayatID, str_villageID,
                str_farmerage, str_FannualIncome, str_Ffamilymember, str_Fphoto, str_empID, str_tempfid;

        str_yearID = str_stateID = str_districtID = str_talukID = str_panchayatID = str_villageID =
                str_farmerage = str_FannualIncome = str_Ffamilymember = str_Fphoto = str_empID = str_tempfid = "empty";

        Log.e("tag", "str_farmerid" + str_farmerid);
        // String str_tempfarmpond_id="temppondidX";

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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR);");


        //    Log.e("remarks",str_farmpond_remarks);
        //  Log.e("amt",str_farmpond_amtcollected);
        //  Log.e("status",str_farmpond_status);
        Log.e("tag", "pond FIDDB str_farmerid=" + str_farmerid);


        String SQLiteQuery = "INSERT INTO FarmPondDetails_fromServerRest(FIDDB,TempFIDDB,FNameDB,FMNameDB,FLNameDB,FYearIDDB,FStateIDDB,FDistrictIDDB," +
                "FTalukIDDB,FPanchayatIDDB,FVillageIDDB,FageDB,FphonenumberDB,FfamilymemberDB,FAnnualIncomeDB,FidprooftypeDB,FidproofnoDB,FphotoDB,FPondidDB," +
                "WidthDB,HeightDB,DepthDB,LatitudeDB,LongitudeDB,Imageid1DB,Image1Base64DB,Imageid2DB,Image2Base64DB," +
                "Imageid3DB,Image3Base64DB,EmployeeIDDB,SubmittedDateDB,TotalDaysDB,StartDateDB,ConstructedDateDB,PondCostDB,McodeDB," +
                "FPondCodeDB,FPondRemarksDB,FPondAmtTakenDB,FPondStatusDB," +
                "FPondApprovalStatusDB,FPondApprovalRemarksDB,FPondApprovedbyDB,FPondApprovedDateDB,FPondDonorDB," +
                "FPondLatitudeDB,FPondLongitudeDB," +
                "FPondAcresDB,FPondGuntaDB,FPondCropBeforeDB,FPondCropAfterDB," +
                "UploadedStatusFarmerprofile,UploadedStatus," +
                "newpondImageId1,pondImageType1,newpondImageId2,pondImageType2,newpondImageId3,pondImageType3)" +
                " VALUES ('" + str_farmerid + "','" + str_tempfid + "','" + str_farmername + "','" + str_farmerMName + "','" + str_farmerLName + "','" + str_yearID + "'," +
                "'" + str_stateID + "','" + str_districtID + "','" + str_talukID + "','" + str_panchayatID + "','" + str_villageID + "','" + str_farmerage + "'," +
                "'" + str_Fphonenumber + "','" + str_FannualIncome + "','" + str_Ffamilymember + "','" + str_FIDprooftype + "','" + str_FIDproofno + "','" + str_Fphoto + "'," +
                "'" + str_farmpond_id + "','" + str_width + "'," +
                "'" + str_height + "','" + str_depth + "','" + 0 + "','" + 0 + "','" + str_image1 + "','" + str_base64image1 + "'," +
                "'" + str_image2 + "','" + str_base64image2 + "','" + str_image3 + "','" + str_base64image3 + "','" + str_empID + "','" + str_submited_date + "'," +
                "'" + str_total_days + "','" + str_startdate + "','" + str_constr_date + "','" + str_pond_cost + "','" + str_mcode + "','" + str_fpondcode + "'," +
                "'" + str_farmpond_remarks + "','" + str_farmpond_amtcollected + "','" + str_farmpond_status + "'," +
                "'" + str_approvalstatus + "','" + str_approvalremarks + "','" + str_approvedby + "','" + str_approveddate + "','" + str_donorname + "'," +
                "'" + str_latitude + "','" + str_longitude + "','" + str_acres + "','" + str_gunta + "','" + str_crop_beforepond + "','" + str_crop_afterpond + "','" + 0 + "','" + 0 + "','" + str_pondImageId1 + "','" + pondImageType1 + "','" + str_pondImageId2 + "','" + pondImageType2 + "','" + str_pondImageId3 + "','" + pondImageType3 + "');";

        db1.execSQL(SQLiteQuery);
        db1.close();

    }

    public void delete_FarmPondDetails_fromServer_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

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


        Cursor cursor = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db1.delete("FarmPondDetails_fromServerRest", null, null);
        }
        db1.close();


    }

    public void DBCreate_FarmpondRest_details_2SQLiteDB(String pondID, String farmerID, String academicID, String machineID, String pondCode, String pondLatitude, String pondLongitude, String pondLength, String pondWidth,
                                                        String pondDepth, String pondStart, String pondEnd, String pondDays, String pondCost, String pondImage1, String pondImage2, String pondImage3, String pondStatus, String submittedDate, String submittedBy, String createdDate,
                                                        String createdBy, String pondTempID, String responseOutput, String createdUser, String submittedUser) {

        SQLiteDatabase db_viewfarmpondlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_viewfarmpondlist.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetailsRest(MTempId INTEGER PRIMARY KEY,pondID VARCHAR,farmerID VARCHAR," +
                "academicID VARCHAR,machineID VARCHAR,pondCode VARCHAR," +
                "pondLatitude VARCHAR,pondLongitude VARCHAR,pondLength VARCHAR," +
                "pondWidth VARCHAR,pondDepth VARCHAR,pondStart VARCHAR,pondEnd VARCHAR," +
                "pondDays VARCHAR,pondCost VARCHAR,pondImage1 VARCHAR,pondImage2 VARCHAR,pondImage3 VARCHAR,pondStatus VARCHAR," +
                "submittedDate VARCHAR,submittedBy VARCHAR," +
                "createdDate VARCHAR,createdBy VARCHAR,pondTempID VARCHAR,responseOutput VARCHAR,createdUser VARCHAR,submittedUser VARCHAR);");

        String SQLiteQuery = "INSERT INTO FarmPondDetailsRest (pondID,farmerID, academicID," +
                "machineID,pondCode,pondLatitude,pondLongitude," +
                "pondLength,pondWidth,pondDepth,pondStart,pondEnd," +
                "pondDays,pondCost,pondImage1,pondImage2,pondImage3,pondStatus,submittedDate,submittedBy," +
                "createdDate,createdBy,pondTempID,responseOutput,createdUser,submittedUser)" +
                " VALUES ('" + pondID + "','" + farmerID + "','" + academicID + "','" + machineID + "','" + pondCode + "','"
                + pondLatitude + "','" + pondLongitude + "','" + pondLength + "','" + pondWidth + "','" + pondDepth + "'," +
                "'" + pondStart + "','" + pondEnd + "','" + pondDays + "','" + pondCost + "','" + pondImage1 + "','" + pondImage2 + "','" + pondImage3 + "'," +
                "'" + pondStatus + "','" + submittedDate + "','" + submittedBy + "','" + createdDate + "','"
                + createdBy + "','" + pondTempID + "','" + responseOutput + "','" + createdUser + "','" + submittedUser + "');";

        db_viewfarmpondlist.execSQL(SQLiteQuery);

    }

    /////////////////////////////////////////////////////////////////////////////////////
//---------------------------------------------------------------------------------
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
                Log.e("tag", "innerObj_Class_yearList" + innerObj_Class_yearList);

                Log.e("tag", "arrayObj_Class_yearDetails2" + arrayObj_Class_yearDetails2[i]);
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_year.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_yearDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            yearlist_SP.setAdapter(dataAdapter);
            if (x > sel_yearsp) {
                yearlist_SP.setSelection(sel_yearsp);
            }
        }

    }

    public void uploadfromDB_Statelist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor Statecount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new State[x];
        if (cursor1.moveToFirst()) {

            do {
                State innerObj_Class_SandboxList = new State();
                innerObj_Class_SandboxList.setStateID(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_SandboxList.setStateName(cursor1.getString(cursor1.getColumnIndex("StateName")));
                //   innerObj_Class_SandboxList.s(cursor1.getString(cursor1.getColumnIndex("state_yearid")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            statelist_SP.setAdapter(dataAdapter);
            if (x > sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }
        }

    }

    public void uploadfromDB_Districtlist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor Districtcount", Integer.toString(x));

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
            districtlist_SP.setAdapter(dataAdapter);
            if (x > sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }
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
            districtlist_SP.setAdapter(dataAdapter);
            if (x > sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }
        }

    }

    public void uploadfromDB_Taluklist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukListRest", null);

        // Cursor cursor2 = db1.rawQuery("select T.taluka_id,V.village_id,V.village_name from master_state S, master_district D, master_taluka T, master_village V, master_panchayat P where S.state_id=D.state_id AND D.district_id=T.district_id AND T.taluka_id=V.taluk_id AND T.district_id=P.district_id AND (S.state_id in (1,12,25))",null);
        //  Cursor cursor1 = db1.rawQuery("select T.TalukID,T.TalukName,T.Taluk_districtid from DistrictList D, TalukList T where D.DistrictID=T.Taluk_districtid",null);

        int x = cursor1.getCount();
        Log.d("cursor Talukcount", Integer.toString(x));

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
            taluklist_SP.setAdapter(dataAdapter);
            if (x > sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }
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
            taluklist_SP.setAdapter(dataAdapter);
            if (x > sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }
        }

      /*  if(x==0)
        {
            arrayObj_Class_TalukListDetails2 = new Taluka[1];
            Taluka innerObj_Class_talukList = new Taluka();
            innerObj_Class_talukList.setTalukaID("2000");
            innerObj_Class_talukList.setTalukaName("No Records");
            innerObj_Class_talukList.setDistrictID("2000");


            arrayObj_Class_TalukListDetails2[0] = innerObj_Class_talukList;

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            taluklist_SP.setAdapter(dataAdapter);
        }
*/
    }

    public void uploadfromDB_Villagelist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        // db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,PanchayatID VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM VillageListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor Villagecount", Integer.toString(x));

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
            villagelist_SP.setAdapter(dataAdapter);
            if (x > sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }
        }


    }

    public void Update_VillageId_spinner(String str_panchayatID, String str_talukid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        //  db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db1.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,PanchayatID VARCHAR);");
        //Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageListRest WHERE TalukID='" + str_talukid + "'AND PanchayatID='" + str_panchayatID + "'", null);
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
                innerObj_Class_villageList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("PanchayatID")));

                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                // Log.e("village_name", cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            villagelist_SP.setAdapter(dataAdapter);
            if (x > sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }

        }


      /*  if(x==0)
        {
            arrayObj_Class_VillageListDetails2 = new Village[1];
            Village innerObj_Class_villageList = new Village();
            innerObj_Class_villageList.setVillageID("2000");
            innerObj_Class_villageList.setVillageName("No Records");
            innerObj_Class_villageList.setTalukaID("2000");


            arrayObj_Class_VillageListDetails2[0] = innerObj_Class_villageList;
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            villagelist_SP.setAdapter(dataAdapter);

        }
*/
    }

    public void uploadfromDB_Grampanchayatlist() {

        SQLiteDatabase db_grampanchayat = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_grampanchayat.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatListRest(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
        Cursor cursor1 = db_grampanchayat.rawQuery("SELECT DISTINCT * FROM GrampanchayatListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor Panchayathcount", Integer.toString(x));

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
            grampanchayatlist_SP.setAdapter(dataAdapter);
            if (x > sel_grampanchayatsp) {
                grampanchayatlist_SP.setSelection(sel_grampanchayatsp);
            }

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
            grampanchayatlist_SP.setAdapter(dataAdapter);
            if (x > sel_grampanchayatsp) {
                grampanchayatlist_SP.setSelection(sel_grampanchayatsp);
            }
        }


       /* if(x==0)
        {
            arrayObj_Class_GrampanchayatListDetails2 = new Panchayat[1];
            Panchayat innerObj_Class_panchayatList = new Panchayat();
            innerObj_Class_panchayatList.setPanchayatID("2000");
            innerObj_Class_panchayatList.setPanchayatName("No Records");
            innerObj_Class_panchayatList.setTalukaID("2000");


            arrayObj_Class_GrampanchayatListDetails2[0] = innerObj_Class_panchayatList;
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_GrampanchayatListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            grampanchayatlist_SP.setAdapter(dataAdapter);

        }*/

    }

    public void Update_ids_farmerlist_listview(String str_yearid, String str_stateid, String str_distid, String str_talukid, String str_villageid, String str_panchayatid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");


        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerListRest  WHERE DispFarmerTable_YearID='" + str_yearid + "' AND DispFarmerTable_StateID='" + str_stateid + "' AND DispFarmerTable_DistrictID='" + str_distid + "'  AND DispFarmerTable_TalukID='" + str_talukid + "' AND DispFarmerTable_VillageID='" + str_villageid + "' AND DispFarmerTable_GrampanchayatID='" + str_panchayatid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerListRest WHERE DispFarmerTable_YearID='" + str_yearid + "' AND DispFarmerTable_VillageID='" + str_villageid + "'", null);


        int x = cursor1.getCount();
        Log.d("cursor Farmercount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_FarmerListDetails2 = new Farmer[x];
        // originalViewFarmerList.clear();
        ViewFarmerList_arraylist.clear();

        if (cursor1.moveToFirst()) {

            do {
                Farmer innerObj_Class_SandboxList = new Farmer();
                innerObj_Class_SandboxList.setAcademic_ID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_YearID")));
                innerObj_Class_SandboxList.setStateID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_StateID")));
                innerObj_Class_SandboxList.setDistrictID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_DistrictID")));
                innerObj_Class_SandboxList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_TalukID")));
                innerObj_Class_SandboxList.setVillageID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_VillageID")));
                innerObj_Class_SandboxList.setPanchayatID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_GrampanchayatID")));
                innerObj_Class_SandboxList.setFarmerID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerID")));//DispFarmerTable_Farmer_Code VARCHAR
                innerObj_Class_SandboxList.setFarmer_Code(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code")));//DispFarmerTable_Farmer_Code VARCHAR
                innerObj_Class_SandboxList.setFarmerFirstName(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName")));
                // innerObj_Class_SandboxList.setLocalfarmerimage(cursor1.getBlob(cursor1.getColumnIndex("LocalFarmerImg")));


                // innerObj_Class_SandboxList.setStr_base64(cursor1.getString(cursor1.getColumnIndex("FarmerImageB64str_DB")));

                innerObj_Class_SandboxList.setFarmerMiddleName(cursor1.getString(cursor1.getColumnIndex("FarmerMName_DB")));
                innerObj_Class_SandboxList.setFarmerLastName(cursor1.getString(cursor1.getColumnIndex("FarmerLName_DB")));
                innerObj_Class_SandboxList.setFarmerAge(cursor1.getString(cursor1.getColumnIndex("Farmerage_DB")));
                innerObj_Class_SandboxList.setFarmerMobile(cursor1.getString(cursor1.getColumnIndex("Farmercellno_DB")));
                innerObj_Class_SandboxList.setFarmerIncome(cursor1.getString(cursor1.getColumnIndex("FIncome_DB")));
                innerObj_Class_SandboxList.setFarmerFamily(cursor1.getString(cursor1.getColumnIndex("Ffamilymember_DB")));
                innerObj_Class_SandboxList.setFarmerIDType(cursor1.getString(cursor1.getColumnIndex("FIDprooftype_DB")));
                innerObj_Class_SandboxList.setFarmerIDNumber(cursor1.getString(cursor1.getColumnIndex("FIDProofNo_DB")));


                String str_FarmerName = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName"));
                String str_Farmercode = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code"));
                String str_FarmerImage = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage"));
                String str_farmerpondcount = cursor1.getString(cursor1.getColumnIndex("Farmpondcount"));
                byte[] str_LocalImg = cursor1.getBlob(cursor1.getColumnIndex("LocalFarmerImg"));

                Log.e("tag", "str_LocalImg oo=" + str_LocalImg);

                arrayObj_Class_FarmerListDetails2[i] = innerObj_Class_SandboxList;
                Farmer item;

                //item = new Class_FarmerListDetails(str_Farmercode, str_FarmerName);
                item = new Farmer(
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_YearID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_StateID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_DistrictID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_TalukID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_VillageID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_GrampanchayatID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerID")),
                        /*cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code")),*/
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage")),
                        cursor1.getString(cursor1.getColumnIndex("FarmerImageB64str_DB")),

                        cursor1.getString(cursor1.getColumnIndex("FarmerMName_DB")),
                        cursor1.getString(cursor1.getColumnIndex("FarmerLName_DB")),
                        cursor1.getString(cursor1.getColumnIndex("Farmerage_DB")),
                        cursor1.getString(cursor1.getColumnIndex("Farmercellno_DB")),
                        cursor1.getString(cursor1.getColumnIndex("FIncome_DB")),
                        cursor1.getString(cursor1.getColumnIndex("Ffamilymember_DB")),
                        cursor1.getString(cursor1.getColumnIndex("FIDprooftype_DB")),
                        cursor1.getString(cursor1.getColumnIndex("FIDProofNo_DB")),

                        cursor1.getString(cursor1.getColumnIndex("Farmpondcount")),
                        cursor1.getBlob(cursor1.getColumnIndex("LocalFarmerImg"))
                );//farmer_image


                ViewFarmerList_arraylist.add(item);
                Log.e("str_FarmerName2id", str_FarmerName);
                Log.e("str_FarmerImage", str_FarmerImage);

                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

//            originalViewFarmerList.clear();
//           // ViewFarmerList_arraylist.clear();
            originalViewFarmerList = new ArrayList<Farmer>();
            originalViewFarmerList.addAll(ViewFarmerList_arraylist);

            if (ViewFarmerList_arraylist != null) {
                farmerListViewAdapter.notifyDataSetChanged();
                farmer_listview.setAdapter(farmerListViewAdapter);

            }
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_FarmerListDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            farmer_listview.setAdapter(dataAdapter);


//            CustomAdapter_new dataAdapter=new CustomAdapter_new();
//            farmer_listview.setAdapter(dataAdapter);

        }

    }

    private void GetDropdownValuesRestData() {

        Call<Location_Data> call = userService1.getLocationData(str_employee_id);
        // Call<Location_Data> call = userService1.getLocationData("38");
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_ViewFarmers.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<Location_Data>() {
            @Override
            public void onResponse(Call<Location_Data> call, Response<Location_Data> response) {
                //   Log.e("Entered resp", response.message());
                //     Log.e("Entered resp", response.body().getMessage());

                if (response.isSuccessful()) {
                    Location_Data class_locaitonData = response.body();
                    Log.e("response.body", response.body().getLst().toString());
                    if (class_locaitonData.getStatus().equals(true)) {
                        List<Location_DataList> yearlist = response.body().getLst();
                        Log.e("programlist.size()", String.valueOf(yearlist.size()));

                        location_dataLists = new Location_DataList[yearlist.size()];
                        // Toast.makeText(getContext(), "" + class_monthCounts.getMessage(), Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < location_dataLists.length; i++) {


                            Log.e("status", String.valueOf(class_locaitonData.getStatus()));
                           /* Log.e("msg", class_loginresponse.getMessage());
                            Log.e("list", class_loginresponse.getList().get(i).getId());
                            Log.e("list", class_loginresponse.getList().get(i).getProgramCode());
                            Log.e("size", String.valueOf(class_loginresponse.getList().size()));*/

                            class_location_dataList.setState((class_locaitonData.getLst().get(i).getState()));
                            class_location_dataList.setDistrict(class_locaitonData.getLst().get(i).getDistrict());
                            class_location_dataList.setTaluka(class_locaitonData.getLst().get(i).getTaluka());
                            class_location_dataList.setPanchayat(class_locaitonData.getLst().get(i).getPanchayat());
                            class_location_dataList.setVillage(class_locaitonData.getLst().get(i).getVillage());
                            class_location_dataList.setYear(class_locaitonData.getLst().get(i).getYear());

                            class_location_dataList.setCount(class_locaitonData.getLst().get(i).getCount());

                            int sizeCount = class_locaitonData.getLst().get(i).getCount().size();
                            for (int j = 0; j < sizeCount; j++) {
                                Log.e("tag", "PanchayatCount ==" + class_locaitonData.getLst().get(i).getCount().get(j).getPanchayatCount());

                                Log.e("tag", "VillageCount==" + class_locaitonData.getLst().get(i).getCount().get(j).getVillageCount());
                                String SCount = class_locaitonData.getLst().get(i).getCount().get(j).getStateCount();
                                String DCount = class_locaitonData.getLst().get(i).getCount().get(j).getDistrictCount();
                                String TCount = class_locaitonData.getLst().get(i).getCount().get(j).getTalukaCount();
                                String PCount = class_locaitonData.getLst().get(i).getCount().get(j).getPanchayatCount();
                                String VCount = class_locaitonData.getLst().get(i).getCount().get(j).getVillageCount();
                                String YCount = class_locaitonData.getLst().get(i).getCount().get(j).getYearCount();
                                String MachineCount = class_locaitonData.getLst().get(i).getCount().get(j).getMachineCount();
                                String MachineCostCount = class_locaitonData.getLst().get(i).getCount().get(j).getMachineCostCount();
                                String Sync_ID = class_locaitonData.getLst().get(i).getCount().get(j).getSync_ID();
                                SharedPreferences.Editor editor = shared_syncId.edit();
                                editor.putString(SyncId, Sync_ID);
                                editor.commit();
                                SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
                                myprefs_spinner.putString(Key_syncId, Sync_ID);
                                myprefs_spinner.apply();
                                DBCreate_CountDetailsRest_insert_2SQLiteDB(SCount,DCount,TCount,PCount,VCount,YCount,MachineCount,MachineCostCount,Sync_ID);
                               /* String StateName = class_locaitonData.getLst().get(i).getState().get(j).getStateName();
                                String StateId = class_locaitonData.getLst().get(i).getState().get(j).getStateID();
                                DBCreate_StatedetailsRest_insert_2SQLiteDB(StateId, StateName, StateId, j);*/
                            }

                            int sizeState = class_locaitonData.getLst().get(i).getState().size();
                            for (int j = 0; j < sizeState; j++) {
                                Log.e("tag", "state name==" + class_locaitonData.getLst().get(i).getState().get(j).getStateName());
                                String StateName = class_locaitonData.getLst().get(i).getState().get(j).getStateName();
                                String StateId = class_locaitonData.getLst().get(i).getState().get(j).getStateID();
                                DBCreate_StatedetailsRest_insert_2SQLiteDB(StateId, StateName, StateId, j);
                            }
                            int sizeDistrict = class_locaitonData.getLst().get(i).getDistrict().size();
                            for (int j = 0; j < sizeDistrict; j++) {
                                Log.e("tag", "District name==" + class_locaitonData.getLst().get(i).getDistrict().get(j).getDistrictName());
                                String DistrictName = class_locaitonData.getLst().get(i).getDistrict().get(j).getDistrictName();
                                String DistrictId = class_locaitonData.getLst().get(i).getDistrict().get(j).getDistrictID();
                                String DistrictStateId = class_locaitonData.getLst().get(i).getDistrict().get(j).getStateID();
                                DBCreate_DistrictdetailsRest_insert_2SQLiteDB(DistrictId, DistrictName, "Y1", DistrictStateId, j);
                            }
                            Log.e("tag", "size==" + class_locaitonData.getLst().get(i).getTaluka().size());
                            int sizeTaluka = class_locaitonData.getLst().get(i).getTaluka().size();
                            for (int j = 0; j < sizeTaluka; j++) {
                                Log.e("tag", "Taluka name==" + class_locaitonData.getLst().get(i).getTaluka().get(j).getTalukaName());
                                String TalukaName = class_locaitonData.getLst().get(i).getTaluka().get(j).getTalukaName();
                                String TalukaId = class_locaitonData.getLst().get(i).getTaluka().get(j).getTalukaID();
                                String TalukaDistrictId = class_locaitonData.getLst().get(i).getTaluka().get(j).getDistrictID();
                                DBCreate_TalukdetailsRest_insert_2SQLiteDB(TalukaId, TalukaName, TalukaDistrictId, j);
                            }
                            int sizePanchayat = class_locaitonData.getLst().get(i).getPanchayat().size();
                            for (int j = 0; j < sizePanchayat; j++) {
                                Log.e("tag", "Panchayat name==" + class_locaitonData.getLst().get(i).getPanchayat().get(j).getPanchayatName());
                                String PanchayatName = class_locaitonData.getLst().get(i).getPanchayat().get(j).getPanchayatName();
                                String PanchayatId = class_locaitonData.getLst().get(i).getPanchayat().get(j).getPanchayatID();
                                String PanchayatTalukaId = class_locaitonData.getLst().get(i).getPanchayat().get(j).getTalukaID();
                                DBCreate_GrampanchayatdetailsRest_insert_2SQLiteDB(PanchayatId, PanchayatName, PanchayatTalukaId, j);
                            }
                            int sizeVillage = class_locaitonData.getLst().get(i).getVillage().size();
                            for (int j = 0; j < sizeVillage; j++) {
                                Log.e("tag", "Village name==" + class_locaitonData.getLst().get(i).getVillage().get(j).getVillageName());
                                String VillageName = class_locaitonData.getLst().get(i).getVillage().get(j).getVillageName();
                                String VillageId = class_locaitonData.getLst().get(i).getVillage().get(j).getVillageID();
                                String VillagePanchayatId = class_locaitonData.getLst().get(i).getVillage().get(j).getPanchayatID();
                                String VillageTalukId = class_locaitonData.getLst().get(i).getVillage().get(j).getTalukaID();
                                DBCreate_VillagedetailsRest_insert_2SQLiteDB(VillageId, VillageName, VillageTalukId, VillagePanchayatId, j);
                            }
                            int sizeYear = class_locaitonData.getLst().get(i).getYear().size();
                            for (int j = 0; j < sizeYear; j++) {
                                Log.e("tag", "Year name==" + class_locaitonData.getLst().get(i).getYear().get(j).getAcademic_Name());
                                String YearName = class_locaitonData.getLst().get(i).getYear().get(j).getAcademic_Name();
                                String YearId = class_locaitonData.getLst().get(i).getYear().get(j).getAcademic_ID();
                                DBCreate_YeardetailsRest_insert_2SQLiteDB(YearId, YearName, j);
                            }
                            if (class_locaitonData.getLst().get(i).getClassMachineDetails() != null) {
                                int sizeMachine = class_locaitonData.getLst().get(i).getClassMachineDetails().size();
                                for (int j = 0; j < sizeMachine; j++) {
                                    Log.e("tag", "Machine name==" + class_locaitonData.getLst().get(i).getClassMachineDetails().get(j).getMachine_Name());
                                    String MachineName = class_locaitonData.getLst().get(i).getClassMachineDetails().get(j).getMachine_Name();
                                    String MachineId = class_locaitonData.getLst().get(i).getClassMachineDetails().get(j).getMachine_ID();
                                    DBCreate_MachineDetailsRest(MachineName, MachineId);
                                }
                            }
                        }

                        uploadfromDB_Yearlist();
                        uploadfromDB_Statelist();
                        uploadfromDB_Districtlist();
                        uploadfromDB_Taluklist();
                        uploadfromDB_Villagelist();
                        uploadfromDB_Grampanchayatlist();
                        // progressDoalog.dismiss();
                    } else {
                        Toast.makeText(Activity_ViewFarmers.this, class_locaitonData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDoalog.dismiss();
                    Log.e("tag","working");
                } else {
                    progressDoalog.dismiss();
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("error message", error.getMsg());

                    Toast.makeText(Activity_ViewFarmers.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Toast.makeText(Activity_ViewFarmers.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }

    private void GetFarmer_PondValuesRestData() {

        Call<UserData> call = userService1.getUserData(str_employee_id);
        //  Call<UserData> call = userService1.getUserData("38");

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_ViewFarmers.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                //   Log.e("Entered resp", response.message());
                //     Log.e("Entered resp", response.body().getMessage());
                Log.e("TAG", "response userdata: " + new Gson().toJson(response));
                Log.e("response body userdata", String.valueOf(response.body()));
                AddFarmerDetailsNew();
                if (response.isSuccessful()) {
                    UserData class_userData = response.body();
                    //   Log.e("response.body", response.body().getLst().toString());
                    if (class_userData.getStatus().equals(true)) {
                        if(class_userData.getMessage().equalsIgnoreCase("Success")) {
                            List<UserDataList> yearlist = response.body().getLst();
                            Log.e("programlist.size()", String.valueOf(yearlist.size()));

                            userDataLists = new UserDataList[yearlist.size()];
                            //   Log.e("tag","Pond Id=="+class_userData.getLst().get(0).getPond().get(0).getPondID());

                            for (int i = 0; i < userDataLists.length; i++) {

                                String str_farmpondbaseimage_url, str_base64image = null, str_base64image1 = null, str_base64image2 = null, str_base64image3 = null;

                                String str_imageid1, str_imageid2, str_imageid3;
                                str_base64image1 = "noimage1";
                                str_base64image2 = "noimage2";
                                str_base64image3 = "noimage3";
                                str_imageid1 = "0";
                                str_imageid2 = "0";
                                str_imageid3 = "0";
                                str_farmpond_remarks = "NoRemarks";


                                str_farmpondbaseimage_url = Class_URL.URL_farmpondbaselink.toString().trim();

                                Log.e("status", String.valueOf(class_userData.getStatus()));
                                // Log.e("tag","farmer name=="+class_userData.getLst().get(0).getFarmer().get(0).getFarmerFirstName());

                                class_userDatalist.setFarmer((class_userData.getLst().get(i).getFarmer()));
                                class_userDatalist.setPond(class_userData.getLst().get(i).getPond());

                                int sizeFarmer = 0;
                                if (class_userData.getLst().get(i).getFarmer() != null) {
                                    sizeFarmer = class_userData.getLst().get(i).getFarmer().size();
                                }
                                int sizePond = 0;
                                if (class_userData.getLst().get(i).getPond() != null) {
                                    sizePond = class_userData.getLst().get(i).getPond().size();
                                }

                                int sizeCount = 0;
                                if (class_userData.getLst().get(i).getUserdatacount() != null) {
                                    sizeCount = class_userData.getLst().get(i).getUserdatacount().size();
                                }

                                for (int j = 0; j < sizeCount; j++) {
                                    String Farmer_Count = class_userData.getLst().get(i).getUserdatacount().get(j).getFarmer_Count();
                                    String Pond_Count = class_userData.getLst().get(i).getUserdatacount().get(j).getPond_Count();

                                    DBCreate_UserDataCountListRest_insert_2SQLiteDB(Farmer_Count, Pond_Count);
                                }

                                for (int j = 0; j < sizeFarmer; j++) {
                                    Log.e("tag", "Farmer name==" + class_userData.getLst().get(i).getFarmer().get(j).getFarmerFirstName());
                                    //String FarmerFirstName = class_userData.getLst().get(i).getFarmer().get(j).getFarmerFirstName();
                                    // String FarmerID = class_userData.getLst().get(i).getFarmer().get(j).getFarmerID();
                                    String farmerID = class_userData.getLst().get(i).getFarmer().get(j).getFarmerID();
                                    String stateID = class_userData.getLst().get(i).getFarmer().get(j).getStateID();
                                    String districtID = class_userData.getLst().get(i).getFarmer().get(j).getDistrictID();
                                    String talukaID = class_userData.getLst().get(i).getFarmer().get(j).getTalukaID();
                                    String panchayatID = class_userData.getLst().get(i).getFarmer().get(j).getPanchayatID();
                                    String villageID = class_userData.getLst().get(i).getFarmer().get(j).getVillageID();
                                    String farmerFirstName = class_userData.getLst().get(i).getFarmer().get(j).getFarmerFirstName();
                                    String farmerMiddleName = class_userData.getLst().get(i).getFarmer().get(j).getFarmerMiddleName();
                                    String farmerLastName = class_userData.getLst().get(i).getFarmer().get(j).getFarmerLastName();
                                    String farmerMobile = class_userData.getLst().get(i).getFarmer().get(j).getFarmerMobile();
                                    String farmerIDType = class_userData.getLst().get(i).getFarmer().get(j).getFarmerIDType();
                                    String farmerIDNumber = class_userData.getLst().get(i).getFarmer().get(j).getFarmerIDNumber();
                                    String str_imageurl = class_userData.getLst().get(i).getFarmer().get(j).getFarmerPhoto();
                                    String farmerAge = class_userData.getLst().get(i).getFarmer().get(j).getFarmerAge();
                                    String farmerIncome = class_userData.getLst().get(i).getFarmer().get(j).getFarmerIncome();
                                    String farmerFamily = class_userData.getLst().get(i).getFarmer().get(j).getFarmerFamily();
                                    String submittedDate = class_userData.getLst().get(i).getFarmer().get(j).getSubmittedDate();
                                    String createdBy = class_userData.getLst().get(i).getFarmer().get(j).getCreatedBy();
                                    String createdDate = class_userData.getLst().get(i).getFarmer().get(j).getCreatedDate();
                                    String mobileTempID = class_userData.getLst().get(i).getFarmer().get(j).getMobileTempID();
                                    String createdUser = class_userData.getLst().get(i).getFarmer().get(j).getCreatedUser();
                                    String responseoutput = class_userData.getLst().get(i).getFarmer().get(j).getResponse();
                                    String responseAction = class_userData.getLst().get(i).getFarmer().get(j).getResponseAction();
                                    String farmerCode = class_userData.getLst().get(i).getFarmer().get(j).getFarmer_Code();
                                    String farmpondcount = class_userData.getLst().get(i).getFarmer().get(j).getFarmPond_Count();
                                    String yearID = class_userData.getLst().get(i).getFarmer().get(j).getAcademic_ID();

                                    Log.e("tag", "str_imageurl=" + str_imageurl);


                            /*    InputStream inputstream_obj = null;
                                if(str_imageurl!=null||!str_imageurl.equals("")) {
                                    try {
                                        inputstream_obj = new URL(str_imageurl).openStream();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                    byte[] b = baos.toByteArray();
                                    String str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

                                    str_farmerbase64 = str_base64image;
                                }else{
                                    str_farmerbase64=null;
                                }*/
                                    String str_farmerbase64 = null;
                                    class_farmerlistdetails_arrayobj2 = new Farmer[sizeFarmer];
                                    if (str_imageurl == null || str_imageurl.equals("")) {
                                    } else {
                                        //  str_imageurl=class_farmerlistdetails_arrayobj2[i].getFarmerPhoto();

                                        String str_farmpondimageurl = str_imageurl;

                                        InputStream inputstream_obj = null;
                                        try {
                                            if (android.os.Build.VERSION.SDK_INT > 9) {
                                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                StrictMode.setThreadPolicy(policy);
                                                inputstream_obj = new URL(str_farmpondimageurl).openStream();

                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                        byte[] b = baos.toByteArray();
                                        str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

                                        Log.e("tag", "byteArray img=" + b);
                                        str_imageurltobase64_farmerimage = str_base64image;

                                        str_farmerbase64 = str_base64image;
                                    }

                                    //   Log.e("submitdate", submittedDate);
                                    DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(yearID, stateID, districtID, talukaID, villageID, panchayatID, farmerID, farmerCode, farmerFirstName, str_imageurl, farmpondcount, str_farmerbase64, farmerMiddleName, farmerLastName, farmerAge, farmerMobile, farmerIncome, farmerFamily, farmerIDType, farmerIDNumber, submittedDate, createdBy, createdDate, createdUser, responseoutput, responseAction);
                                }

                                Log.e("tag", "sizePond=" + sizePond);
                                for (int j = 0; j < sizePond; j++) {
                                    String pondID = class_userData.getLst().get(i).getPond().get(j).getPondID();
                                    String farmerID = class_userData.getLst().get(i).getPond().get(j).getFarmerID();
                                    String academicID = class_userData.getLst().get(i).getPond().get(j).getAcademicID();
                                    String machineID = class_userData.getLst().get(i).getPond().get(j).getMachineID();
                                   // String pondCode = class_userData.getLst().get(i).getPond().get(j).getPondCode();

                                    String pondCode = class_userData.getLst().get(i).getPond().get(j).getPondID();
                                    
                                    String pondLatitude = class_userData.getLst().get(i).getPond().get(j).getPondLatitude();
                                    String pondLongitude = class_userData.getLst().get(i).getPond().get(j).getPondLongitude();
                                    String pondLength = class_userData.getLst().get(i).getPond().get(j).getPondLength();
                                    String pondWidth = class_userData.getLst().get(i).getPond().get(j).getPondWidth();
                                    String pondDepth = class_userData.getLst().get(i).getPond().get(j).getPondDepth();
                                    String pondStart = class_userData.getLst().get(i).getPond().get(j).getPondStart();
                                    String pondEnd = class_userData.getLst().get(i).getPond().get(j).getPondEnd();
                                    String pondDays = class_userData.getLst().get(i).getPond().get(j).getPondDays();
                                    String pondCost = class_userData.getLst().get(i).getPond().get(j).getPondCost();

                                    String pondStatus = class_userData.getLst().get(i).getPond().get(j).getPondStatus();
                                    String submittedDate = class_userData.getLst().get(i).getPond().get(j).getSubmittedDate();
                                    String submittedBy = class_userData.getLst().get(i).getPond().get(j).getSubmittedBy();
                                    String createdDate = class_userData.getLst().get(i).getPond().get(j).getCreatedDate();
                                    String createdBy = class_userData.getLst().get(i).getPond().get(j).getCreatedBy();
                                    String pondTempID = class_userData.getLst().get(i).getPond().get(j).getPondTempID();
                                    String responseOutput = class_userData.getLst().get(i).getPond().get(j).getResponse();
                                    String createdUser = class_userData.getLst().get(i).getPond().get(j).getCreatedUser();
                                    String submittedUser = class_userData.getLst().get(i).getPond().get(j).getSubmittedUser();
                                    String farmer_First_Name = class_userData.getLst().get(i).getPond().get(j).getFarmerFirstName();
                                    String farmer_Middle_Name = class_userData.getLst().get(i).getPond().get(j).getFarmerMiddleName();
                                    String farmer_Last_Name = class_userData.getLst().get(i).getPond().get(j).getSubmittedUser();

                                    String Pond_Land_Acre = class_userData.getLst().get(i).getPond().get(j).getPondLandAcre();
                                    String Pond_Land_Gunta = class_userData.getLst().get(i).getPond().get(j).getPondLandGunta();
                                    String Approval_Status = class_userData.getLst().get(i).getPond().get(j).getApprovalStatus();
                                    String Response_Action = class_userData.getLst().get(i).getPond().get(j).getResponseAction();
                                    String Approval_Remarks = class_userData.getLst().get(i).getPond().get(j).getApprovalRemarks();
                                    String Approval_By = class_userData.getLst().get(i).getPond().get(j).getApprovalBy();
                                    String Approval_User = class_userData.getLst().get(i).getPond().get(j).getApprovalUser();
                                    String Farmer_ID_Type = class_userData.getLst().get(i).getPond().get(j).getFarmerIDType();
                                    String Farmer_ID_Number = class_userData.getLst().get(i).getPond().get(j).getFarmerIDNumber();
                                    String Donor_Name = class_userData.getLst().get(i).getPond().get(j).getDonorName();
                                    String Crop_Before = class_userData.getLst().get(i).getPond().get(j).getCropBefore();
                                    String Crop_After = class_userData.getLst().get(i).getPond().get(j).getCropAfter();
                                    String CollectedAmount = class_userData.getLst().get(i).getPond().get(j).getPondCollectedAmount();

                                    // PondImage Array Method
                                    int pondImageSize = class_userData.getLst().get(i).getPond().get(j).getPondImage().size();
                                    for (int k = 0; k < pondImageSize; k++) {
                                        String pondImageType = class_userData.getLst().get(i).getPond().get(j).getPondImage().get(k).getImageType();
                                        if(pondImageType!=null) {
                                            if (pondImageType.equalsIgnoreCase("1")) {
                                                pondImage1 = class_userData.getLst().get(i).getPond().get(j).getPondImage().get(k).getImageLink();
                                                pondImageId1 = class_userData.getLst().get(i).getPond().get(j).getPondImage().get(k).getImageID();
                                                pondImageType1 = class_userData.getLst().get(i).getPond().get(j).getPondImage().get(k).getImageType();
                                            }
                                            if (pondImageType.equalsIgnoreCase("2")) {
                                                pondImage2 = class_userData.getLst().get(i).getPond().get(j).getPondImage().get(k).getImageLink();
                                                pondImageId2 = class_userData.getLst().get(i).getPond().get(j).getPondImage().get(k).getImageID();
                                                pondImageType2 = class_userData.getLst().get(i).getPond().get(j).getPondImage().get(k).getImageType();
                                                Log.e("tag", "pondImageId2==" + pondImageId2);
                                                Log.e("tag", "pondImage2==" + pondImage2);
                                            }
                                            if (pondImageType.equalsIgnoreCase("3")) {
                                                pondImage3 = class_userData.getLst().get(i).getPond().get(j).getPondImage().get(k).getImageLink();
                                                pondImageId3 = class_userData.getLst().get(i).getPond().get(j).getPondImage().get(k).getImageID();
                                                pondImageType3 = class_userData.getLst().get(i).getPond().get(j).getPondImage().get(k).getImageType();
                                            }

                                            Log.e("tag", "pondImageId==" + pondImageId1);
                                            Log.e("tag", "pondImage1==" + pondImage1);
                                        }

                                    }

                                    //   String pondImage1,pondImage2,pondImage3;
                                    //  String str_imageurl = class_userData.getLst().get(i).getPond().get(j).getPondImage1();

                                    // String str_farmpondimageurl = str_imageurl;
                                    // String str_imageid1 = class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).
                                    // getClass_farmpondimages_obj().get(k).getImage_ID();

                                    //---------------------------------------------------------------------
                                    String str_imageurl = pondImage1;
                                    Log.e("tag", "str_imageurl==" + str_imageurl);

                                    if (str_imageurl == null || str_imageurl.equals("")) {
                                    } else {
                                        //  str_imageurl=class_farmerlistdetails_arrayobj2[i].getFarmerPhoto();

                                        String str_farmpondimageurl = str_imageurl;

                                        InputStream inputstream_obj = null;
                                        try {
                                            if (android.os.Build.VERSION.SDK_INT > 9) {
                                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                StrictMode.setThreadPolicy(policy);
                                                inputstream_obj = new URL(str_farmpondimageurl).openStream();

                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                        byte[] b = baos.toByteArray();
                                        str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

                                        Log.e("tag", "byteArray img=" + b);
                                        str_imageurltobase64_farmerimage = str_base64image;

                                        str_base64image1 = str_base64image;
                                    }

                                    String str_imageurl2 = pondImage2;
                                    Log.e("tag", "str_imageurl2==" + str_imageurl2);
                                    if (str_imageurl2 == null || str_imageurl2.equals("")) {
                                    } else {
                                        //  str_imageurl=class_farmerlistdetails_arrayobj2[i].getFarmerPhoto();

                                        String str_farmpondimageurl = str_imageurl2;

                                        InputStream inputstream_obj = null;
                                        try {
                                            if (android.os.Build.VERSION.SDK_INT > 9) {
                                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                StrictMode.setThreadPolicy(policy);
                                                inputstream_obj = new URL(str_farmpondimageurl).openStream();

                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                        byte[] b = baos.toByteArray();
                                        str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

                                        Log.e("tag", "byteArray img=" + b);
                                        str_imageurltobase64_farmerimage = str_base64image;

                                        str_base64image2 = str_base64image;
                                    }
                                    String str_imageurl3 = pondImage3;
                                    Log.e("tag", "str_imageurl3==" + str_imageurl3);
                                    if (str_imageurl3 == null || str_imageurl3.equals("")) {
                                    } else {
                                        //  str_imageurl=class_farmerlistdetails_arrayobj2[i].getFarmerPhoto();

                                        String str_farmpondimageurl = str_imageurl3;

                                        InputStream inputstream_obj = null;
                                        try {
                                            if (android.os.Build.VERSION.SDK_INT > 9) {
                                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                StrictMode.setThreadPolicy(policy);
                                                inputstream_obj = new URL(str_farmpondimageurl).openStream();

                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                        byte[] b = baos.toByteArray();
                                        str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

                                        Log.e("tag", "byteArray img=" + b);
                                        str_imageurltobase64_farmerimage = str_base64image;

                                        str_base64image3 = str_base64image;
                                    }
                                    //-----------------------------------Temp commented becouse of no image in url--------
//-----------------------------url need to change------------------------

                                    //   String str_imageurl = class_userData.getLst().get(i).getPond().get(j).getPondImage1();
/*
                                String str_imageurl=pondImage1;
                                String str_farmpondimageurl =  str_imageurl;
                                Log.e("url1", str_farmpondimageurl);
                                Log.e("url1", "str_farmpondimageurl=" + str_farmpondimageurl);

                                if (str_imageurl == null) {
                                    str_base64image1="empty";
                                } else {

                                    InputStream inputstream_obj = null;
                                    try {
                                        if (android.os.Build.VERSION.SDK_INT > 9) {
                                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                            StrictMode.setThreadPolicy(policy);
                                            inputstream_obj = new URL(str_farmpondimageurl).openStream();

                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                   /* InputStream inputstream_obj = null;
                                    try {
                                        inputstream_obj = new URL(str_farmpondimageurl).openStream();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }*/
  /*                                  Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                    byte[] b = baos.toByteArray();
                                    Log.e("str_img byte array..", String.valueOf(b));
                                    str_base64image1 = Base64.encodeToString(b, Base64.DEFAULT);
                                }
                               // String str_imageurl2 = class_userData.getLst().get(i).getPond().get(j).getPondImage2();
                                String str_imageurl2=pondImage2;
                                String str_farmpondimageurl2 = str_imageurl2;
                                Log.e("url1", str_farmpondimageurl2);
                                Log.e("url1", "str_farmpondimageurl2=" + str_farmpondimageurl2);

                                if (str_imageurl2 == null) {
 str_base64image2="empty";
                                } else {


                                    InputStream inputstream_obj2 = null;
                                    try {
                                        if (android.os.Build.VERSION.SDK_INT > 9) {
                                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                            StrictMode.setThreadPolicy(policy);
                                            inputstream_obj2 = new URL(str_farmpondimageurl2).openStream();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Bitmap mIcon2 = BitmapFactory.decodeStream(inputstream_obj2);
                                    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                                    mIcon2.compress(Bitmap.CompressFormat.PNG, 60, baos2);
                                    byte[] b2 = baos2.toByteArray();
                                    Log.e("str_img byte array..", String.valueOf(b2));
                                    str_base64image2 = Base64.encodeToString(b2, Base64.DEFAULT);
                                }
                               // String str_imageurl3 = class_userData.getLst().get(i).getPond().get(j).getPondImage3();
                                String str_imageurl3 =pondImage3;
                                String str_farmpondimageurl3 = str_imageurl3;
                                Log.e("url1", str_farmpondimageurl3);
                                Log.e("url1", "str_farmpondimageurl3=" + str_farmpondimageurl3);

                                if (str_imageurl3 == null) {
 str_base64image3="empty";
                                } else {


                                    InputStream inputstream_obj3 = null;
                                    try {
                                        if (android.os.Build.VERSION.SDK_INT > 9) {
                                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                            StrictMode.setThreadPolicy(policy);
                                            inputstream_obj3 = new URL(str_farmpondimageurl3).openStream();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Bitmap mIcon3 = BitmapFactory.decodeStream(inputstream_obj3);
                                    ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
                                    mIcon3.compress(Bitmap.CompressFormat.PNG, 60, baos3);
                                    byte[] b3 = baos3.toByteArray();
                                    Log.e("str_img byte array..", String.valueOf(b3));
                                    str_base64image3 = Base64.encodeToString(b3, Base64.DEFAULT);
                                }

   */
                                    //------------------------------------------------------------------------------------------------------------------

                              /*  str_base64image1="iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAARuAAAEbgHQo7JoAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAGxQTFRF////AP//KK6GJK2JJK+JJq2HJa6HJa+JJa6IJa6IJa6IJq6IKK+JKK+KK7CLLrGNM7OPNLOQNbSRObWTPLeVQLiWRLmZRrqaVsCjY8WqacetgNC5htK9j9XCltjGndrJruHTtuTXueXZvufcQhO/KQAAAAp0Uk5TAAEmcH+As7Xm9myQZpsAAAB3SURBVBhXZY9HEgMhEAMFLLPQzjnH/f8ffcCmtkzfNFXSSJIkHy0li14FFzIA5OAkyfVUeicpMCJIPle1XZG9YtXLYQOd7Kfn7wNgSjBbAJPnESApwW7Yw+NMORiwfp2ul2K0Ejq9375J3fgtkH1brK3ejPub/wG/CwjxA06BTgAAAABJRU5ErkJggg==";
                                str_base64image2="iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAARuAAAEbgHQo7JoAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAGxQTFRF////AP//KK6GJK2JJK+JJq2HJa6HJa+JJa6IJa6IJa6IJq6IKK+JKK+KK7CLLrGNM7OPNLOQNbSRObWTPLeVQLiWRLmZRrqaVsCjY8WqacetgNC5htK9j9XCltjGndrJruHTtuTXueXZvufcQhO/KQAAAAp0Uk5TAAEmcH+As7Xm9myQZpsAAAB3SURBVBhXZY9HEgMhEAMFLLPQzjnH/f8ffcCmtkzfNFXSSJIkHy0li14FFzIA5OAkyfVUeicpMCJIPle1XZG9YtXLYQOd7Kfn7wNgSjBbAJPnESApwW7Yw+NMORiwfp2ul2K0Ejq9375J3fgtkH1brK3ejPub/wG/CwjxA06BTgAAAABJRU5ErkJggg==";
                                str_base64image3="iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAARuAAAEbgHQo7JoAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAGxQTFRF////AP//KK6GJK2JJK+JJq2HJa6HJa+JJa6IJa6IJa6IJq6IKK+JKK+KK7CLLrGNM7OPNLOQNbSRObWTPLeVQLiWRLmZRrqaVsCjY8WqacetgNC5htK9j9XCltjGndrJruHTtuTXueXZvufcQhO/KQAAAAp0Uk5TAAEmcH+As7Xm9myQZpsAAAB3SURBVBhXZY9HEgMhEAMFLLPQzjnH/f8ffcCmtkzfNFXSSJIkHy0li14FFzIA5OAkyfVUeicpMCJIPle1XZG9YtXLYQOd7Kfn7wNgSjBbAJPnESApwW7Yw+NMORiwfp2ul2K0Ejq9375J3fgtkH1brK3ejPub/wG/CwjxA06BTgAAAABJRU5ErkJggg==";
*/

                                    //pondImage1="0"; pondImage2="0"; pondImage3="0";
                                    str_approveddate = "0";
                                    DBCreate_FarmpondsRest_details_2SQLiteDB(farmerID, farmer_First_Name, pondID, pondWidth, pondLength,
                                            pondDepth, pondImageId1, str_base64image1, pondImage1, pondImageType1, pondImageId2, str_base64image2, pondImage2, pondImageType2, pondImageId3, str_base64image3, pondImage3, pondImageType3,
                                            pondDays, createdDate, pondEnd, pondCost, machineID,
                                            pondCode, pondStart, str_farmpond_remarks, CollectedAmount, pondStatus,
                                            farmer_Middle_Name, farmer_Last_Name, "", Farmer_ID_Type, Farmer_ID_Number, Approval_Status,
                                            Approval_Remarks, Approval_By, str_approveddate, Donor_Name, pondLatitude, pondLongitude,
                                            Pond_Land_Acre, Pond_Land_Gunta, Crop_Before, Crop_After);
                                }
                            }

                            //  uploadfromDB_Farmerlist();
                        }
                        else{
                            Toast.makeText(Activity_ViewFarmers.this, class_userData.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDoalog.dismiss();
                    } else {
                        progressDoalog.dismiss();

                        Toast.makeText(Activity_ViewFarmers.this, class_userData.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDoalog.dismiss();

                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("error message", error.getMsg());

                    // Toast.makeText(Activity_ViewFarmers.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(Activity_ViewFarmers.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }

    private void AddFarmerDetailsNew() {

        Log.e("tag","Location_Response2="+class_location_dataList.getResponse());
        Log.e("tag","UserData_Response2="+class_userDatalist.getResponse());
        String locationData = null,userData = null;
        if(class_location_dataList.getResponse()!=null) {
            if (class_location_dataList.getResponse().equalsIgnoreCase("Success")) {
                locationData = "Success";
            }else{
                locationData = "Error";
            }
        }
        if(class_userDatalist.getResponse()!=null) {

            if (class_userDatalist.getResponse().equalsIgnoreCase("Success")) {
                userData = "Success";
            }
            else{
                userData = "Error";
            }
        }
        Log.e("tag","Location_Response3="+locationData);
        Log.e("tag","UserData_Response3="+userData);

        String Sync_IDNew = shared_syncId.getString(SyncId, "");
        Log.e("tag","Sync_IDNew="+Sync_IDNew);

        sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        String Sync_IDNew1 = sharedpref_spinner_Obj.getString(Key_syncId, "").trim();
        Log.e("tag","Sync_IDNew1="+Sync_IDNew1);

        ValidateSyncRequest request = new ValidateSyncRequest();
        request.setSyncID(Sync_IDNew);
        request.setSyncVersion(versioncode);
        request.setSyncStatus("Success");
        /*if(locationData==null||locationData.equalsIgnoreCase("Success")){
            if(userData==null||userData.equalsIgnoreCase("Success")) {
                request.setSyncStatus("Success");
            }else{
                request.setSyncStatus("Error");
            }
        }else {
            request.setSyncStatus("Error");
        }*/


        Call<ValidateSyncResponse> call = userService1.Post_ValidateSync(request);

        Log.e("TAG", "Post_ValidateSync Request: " + new Gson().toJson(call.request()));
        Log.e("TAG", "Request Post_ValidateSync: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_ViewFarmers.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        call.enqueue(new Callback<ValidateSyncResponse>() {
            @Override
            public void onResponse(Call<ValidateSyncResponse> call, Response<ValidateSyncResponse> response) {
                Log.e("response", response.toString());
                Log.e("TAG", "ValidateSyncResponse : " + new Gson().toJson(response));
                Log.e("tag","ValidateSyncResponse body"+ String.valueOf(response.body()));
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
                   /* Log.e("response new:",error1.getMsg());
                    Log.e("response new status:", String.valueOf(error1.getstatus()));*/
                // Log.e("response",Gson.fromJson(response.toString(),AddFarmer_Activity1.class));

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    ValidateSyncResponse class_loginresponse = response.body();
                    Log.e("tag", "res==" + class_loginresponse.toString());
                    if (class_loginresponse.getStatus().equals("true")) {

                    } else if (class_loginresponse.getStatus().equals("false")) {
                        //  progressDoalog.dismiss();
                        Toast.makeText(Activity_ViewFarmers.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    // Toast.makeText(Activity_ViewFarmers.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_ViewFarmers.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }



//------------------------------------------------------
/////////////----------------------Auto Sync code for 1st time --------------------------------///////////

   /* public void stateListRest_dbCount(){
        SQLiteDatabase db_statelist_delete = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_statelist_delete.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor = db_statelist_delete.rawQuery("SELECT * FROM StateListRest", null);
        int x = cursor.getCount();

        if(x==0){
            GetDropdownValuesRestData();
        }
    }*/

    public void stateListRest_dbCount(){
        SQLiteDatabase db_statelist = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_statelist.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor = db_statelist.rawQuery("SELECT * FROM StateListRest", null);
        int State_x = cursor.getCount();
        Log.e("cursor State_xcount", Integer.toString(State_x));

        if(State_x==0){
            GetDropdownValuesRestData();
            GetFarmer_PondValuesRestData();
            DBCreate_RemarksDetails();
            // AddFarmerDetailsNew();
        }else{
            SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
            db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
            Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictListRest", null);
            int District_x = cursor1.getCount();
            Log.e("cursor Talukcount", Integer.toString(District_x));

            SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
            db2.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
            Cursor cursor2 = db2.rawQuery("SELECT DISTINCT * FROM TalukListRest", null);
            int Taluk_x = cursor2.getCount();
            Log.e("cursor Talukcount", Integer.toString(Taluk_x));

            SQLiteDatabase db_village = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
            db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,PanchayatID VARCHAR);");
            Cursor cursor3 = db_village.rawQuery("SELECT DISTINCT * FROM VillageListRest", null);
            int village_x = cursor3.getCount();
            Log.e("cursor village_xcount", Integer.toString(village_x));

            SQLiteDatabase db_grampanchayat = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
            db_grampanchayat.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatListRest(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
            Cursor cursor4 = db_grampanchayat.rawQuery("SELECT DISTINCT * FROM GrampanchayatListRest", null);
            int panchayat_x = cursor4.getCount();
            Log.d("cursor panchayatcount", Integer.toString(panchayat_x));

            SQLiteDatabase db_year = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
            db_year.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR);");
            Cursor cursor5 = db_year.rawQuery("SELECT DISTINCT * FROM YearListRest", null);
            int Year_x = cursor5.getCount();
            Log.d("cursor Yearcount", Integer.toString(Year_x));

            SQLiteDatabase db6 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
            db6.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineIDDB VARCHAR);");
            Cursor cursor6 = db1.rawQuery("SELECT * FROM MachineDetails_fromServerRest", null);
            int Machine_x = cursor6.getCount();
            Log.d("cursor Machinecount", Integer.toString(Machine_x));

            SQLiteDatabase db_farmer = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

            db_farmer.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                    "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                    "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                    "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                    "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                    "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                    "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");


            // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerListRest  WHERE DispFarmerTable_YearID='" + str_yearid + "' AND DispFarmerTable_StateID='" + str_stateid + "' AND DispFarmerTable_DistrictID='" + str_distid + "'  AND DispFarmerTable_TalukID='" + str_talukid + "' AND DispFarmerTable_VillageID='" + str_villageid + "' AND DispFarmerTable_GrampanchayatID='" + str_panchayatid + "'", null);
            Cursor cursor7 = db_farmer.rawQuery("SELECT DISTINCT * FROM ViewFarmerListRest", null);

            int farmer_x = cursor7.getCount();
            Log.e("tag","farmer_x="+farmer_x);
            cursor7.close();

            State_x=State_x-1;
            District_x=District_x-1;
            Taluk_x=Taluk_x-1;
            village_x=village_x-1;
            panchayat_x=panchayat_x-1;

            if(State_x==Integer.valueOf(StateCount)&&District_x==Integer.valueOf(DistrictCount)&&Taluk_x==Integer.valueOf(TalukaCount)&&village_x==Integer.valueOf(VillageCount)&&panchayat_x==Integer.valueOf(PanchayatCount)&&Year_x==Integer.valueOf(YearCount)&&Machine_x==Integer.valueOf(MachineCount)){

            }else{
                delete_CountDetailsRestTable_B4insertion();
                deleteStateRestTable_B4insertion();
                deleteDistrictRestTable_B4insertion();
                deleteTalukRestTable_B4insertion();
                deleteGrampanchayatRestTable_B4insertion();
                deleteVillageRestTable_B4insertion();
                deleteYearRestTable_B4insertion();
                deleteMachineRestTable_B4insertion();
                deleteRemarksTable_B4insertion();
                DBCreate_RemarksDetails();
                //  ViewFarmerlistdetailsRestTable_B4insertion();

                GetDropdownValuesRestData();

            }
            if(farmer_x==Integer.valueOf(Farmer_Count)){

            }else if(farmer_x<Integer.valueOf(Farmer_Count)){
                deleteViewFarmerlistTable_B4insertion();
                delete_FarmPondDetails_fromServer_B4insertion();
                GetFarmer_PondValuesRestData();
                FarmpondRest_detailsTable_B4insertion();
            }
            //AddFarmerDetailsNew();
        }
    }
    public void CountCheckList(){
        SQLiteDatabase db_locationCount = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_locationCount.execSQL("CREATE TABLE IF NOT EXISTS LocationCountListRest(StateCount VARCHAR,DistrictCount VARCHAR,TalukaCount VARCHAR,PanchayatCount VARCHAR,VillageCount VARCHAR,YearCount VARCHAR,MachineCount VARCHAR,MachineCostCount VARCHAR,Farmer_Count VARCHAR,Pond_Count VARCHAR,Sync_ID VARCHAR);");
        Cursor cursor1 = db_locationCount.rawQuery("SELECT DISTINCT * FROM LocationCountListRest", null);

        int x = cursor1.getCount();
        Log.d("cursor countlist", Integer.toString(x));

        int i = 0;
        if (cursor1.moveToFirst()) {

            do {
                StateCount=cursor1.getString(cursor1.getColumnIndex("StateCount"));
                DistrictCount=cursor1.getString(cursor1.getColumnIndex("DistrictCount"));
                TalukaCount=cursor1.getString(cursor1.getColumnIndex("TalukaCount"));
                PanchayatCount=cursor1.getString(cursor1.getColumnIndex("PanchayatCount"));
                VillageCount=cursor1.getString(cursor1.getColumnIndex("VillageCount"));
                YearCount=cursor1.getString(cursor1.getColumnIndex("YearCount"));
                MachineCount=cursor1.getString(cursor1.getColumnIndex("MachineCount"));
                MachineCostCount=cursor1.getString(cursor1.getColumnIndex("MachineCostCount"));
                //  Farmer_Count=cursor1.getString(cursor1.getColumnIndex("Farmer_Count"));
                //Pond_Count=cursor1.getString(cursor1.getColumnIndex("Pond_Count"));
                Sync_ID=cursor1.getString(cursor1.getColumnIndex("Sync_ID"));
                // arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
            Log.e("tag","StateCount="+StateCount);
            Log.e("tag","DistrictCount="+DistrictCount);
            Log.e("tag","VillageCount="+VillageCount);
            Log.e("tag","Sync_ID="+Sync_ID);
            SharedPreferences.Editor editor = shared_syncId.edit();
            editor.putString(SyncId, Sync_ID);
            editor.commit();
            SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
            myprefs_spinner.putString(Key_syncId, Sync_ID);
            myprefs_spinner.apply();
        }//if ends

    }
    public void UserDataCountCheckList(){
        SQLiteDatabase db_userdataCount = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_userdataCount.execSQL("CREATE TABLE IF NOT EXISTS UserDataCountListRest(Farmer_Count VARCHAR,Pond_Count VARCHAR);");
        Cursor cursor1 = db_userdataCount.rawQuery("SELECT DISTINCT * FROM UserDataCountListRest", null);

        int x = cursor1.getCount();
        Log.d("tag","cursor userdatacountlist"+ Integer.toString(x));

        int i = 0;
        if (cursor1.moveToFirst()) {

            do {
                Farmer_Count=cursor1.getString(cursor1.getColumnIndex("Farmer_Count"));
                Pond_Count=cursor1.getString(cursor1.getColumnIndex("Pond_Count"));
                i++;
            } while (cursor1.moveToNext());
            Log.e("tag","Farmer_Count="+Farmer_Count);
            Log.e("tag","DiPond_Count="+Pond_Count);

            if(Farmer_Count==null||Farmer_Count.equalsIgnoreCase("")){
                Farmer_Count="0";
            }
            if(Pond_Count==null||Pond_Count.equalsIgnoreCase("")){
                Pond_Count="0";
            }
        }//if ends

    }

    public void ViewFarmerListRest_dbcount(){
        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");


        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerListRest  WHERE DispFarmerTable_YearID='" + str_yearid + "' AND DispFarmerTable_StateID='" + str_stateid + "' AND DispFarmerTable_DistrictID='" + str_distid + "'  AND DispFarmerTable_TalukID='" + str_talukid + "' AND DispFarmerTable_VillageID='" + str_villageid + "' AND DispFarmerTable_GrampanchayatID='" + str_panchayatid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerListRest", null);

        int x = cursor1.getCount();
        cursor1.close();
        //   SQLiteDatabase db = openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

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
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR," +
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR);");


        Cursor cursor = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest", null);
        int x2 = cursor.getCount();

        cursor.close();*/

        Log.d("tag","cursor Farmercount auto sync="+ Integer.toString(x));
        if(x==0){
            GetFarmer_PondValuesRestData();
        }
    }
/////////////////////////////sept18th2019//////////////////////////////////

    public void alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_ViewFarmers.this);
        builder1.setMessage("No Data Found");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(Activity_ViewFarmers.this, Activity_FarmerHomeScreen.class);
                        startActivity(i);
                        finish();

                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_register, menu);
        menu.findItem(R.id.Sync)
                .setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:

                Intent i = new Intent(Activity_ViewFarmers.this, Activity_MarketingHomeScreen.class);
                startActivity(i);
                finish();
                break;

            case R.id.Sync:


                //if(Async_Alertdialog().equalsIgnoreCase("yes"))
            {

                internetDectector2 = new Class_InternetDectector(getApplicationContext());
                isInternetPresent2 = internetDectector2.isConnectingToInternet();
                if (isInternetPresent2) {

                    //GetDropdownValues();
                    deleteStateRestTable_B4insertion();
                    deleteDistrictRestTable_B4insertion();
                    deleteTalukRestTable_B4insertion();
                    deleteGrampanchayatRestTable_B4insertion();
                    deleteVillageRestTable_B4insertion();
                    deleteYearRestTable_B4insertion();
                    deleteMachineRestTable_B4insertion();
                    deleteViewFarmerlistTable_B4insertion();
                    delete_FarmPondDetails_fromServer_B4insertion();

                    DBCreate_RemarksDetails();
                    ViewFarmerlistdetailsRestTable_B4insertion();
                    FarmpondRest_detailsTable_B4insertion();

                    GetDropdownValuesRestData();
                    GetFarmer_PondValuesRestData();
                    //      NormalLoginNew();
                    //GetFarmerDetails();


                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


                if (isInternetPresent2) {
                    //    GetFarmerDetails();


                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                }

                if (isInternetPresent2) {


                    //   AsyncTask_fetch_farmponddetails();

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                }

                internetDectector2 = new Class_InternetDectector(getApplicationContext());
                isInternetPresent2 = internetDectector2.isConnectingToInternet();
                if (isInternetPresent2) {
                    //   AsyncTask_fetch_MachineDetails();
                } else {

                }

                if (isInternetPresent2) {
                    // AsyncTask_fetch_construction_remarks();
                }

            }

            break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_ViewFarmers.this, Activity_MarketingHomeScreen.class);
        startActivity(i);
        finish();

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//
//            if (resultCode == RESULT_OK) {
//
//                /*********** Load Captured Image And Data Start ****************/
//
//                String imageId = convertImageUriToFile(imageUri, ViewFarmers.this);
//
//
//                //  Create and excecute AsyncTask to load capture image
//
//                new LoadImagesFromSDCard().execute("" + imageId);
//
//                /*********** Load Captured Image And Data End ****************/
//
//
//            } else if (resultCode == RESULT_CANCELED) {
//
//                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
//            } else {
//
//                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
//         Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//       by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//       you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//       max Height and width values of the compressed image is taken as 816x612

         /*float maxHeight = 816.0f;
         float maxWidth = 612.0f;*/


        float maxHeight = 216.0f;
        float maxWidth = 212.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//       width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//       setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//       inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//       this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//           load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//       check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        compressedfilepaths = filename;

        try {
            out = new FileOutputStream(filename);

//           write the compressed bitmap at the destination specified by filename.
            //scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //    if (resultCode == RESULT_OK) {
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//
//
//                Log.d("CameraDemo", "Pic saved");
//                Uri imageUri = Uri.parse(mCurrentPhotoPath);
//                if (imageUri.getPath() != null) {
//                    File file = new File(imageUri.getPath());
//
//                    try {
//                        InputStream ims = new FileInputStream(file);
//                        bitmap = BitmapFactory.decodeStream(ims);
//                        rotatemethod(file.toString());
//                        BitMapToString(bitmap);
//
//                        holder.farmerimage_iv.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        return;
//                    }
//
//
//                } else if (resultCode == RESULT_CANCELED) {
//
//                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        } else if (requestCode == 2) {
//            if (resultCode == RESULT_OK) {
//
//                Uri selectedImage = data.getData();
//                String[] filePath = {MediaStore.Images.Media.DATA};
//                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String picturePath = c.getString(columnIndex);
//                c.close();
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//                thumbnail = getResizedBitmap(thumbnail, 400);
//                Log.w(" gallery.", picturePath + "");
//                holder.farmerimage_iv.setImageBitmap(thumbnail);
//                BitMapToString(thumbnail);
//            }
//        }
//
//    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "DetSkillsSign/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        // String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        String uriSting = (file.getAbsolutePath() + "/" + "f" + System.currentTimeMillis() + ".png");
        return uriSting;

    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
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

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        b = baos.toByteArray();
        str_img = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("str_img..", str_img);
        return str_img;
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

            // holder.farmerimage_iv.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*private void GetDropdownValuesRestData(){

     *//* String str_token = "Bearer " + str_AUTH_token;
            Log.e("str_token", str_token);
            Log.e("str_AUTH_token", str_AUTH_token);*//*
     *//*    User_Id request = new User_Id();
        request.setUser_ID("40");*//*

        Call<Location_Data> call = userService1.getLocationData("40");


            call.enqueue(new Callback<Location_Data>() {
                @Override
                public void onResponse(Call<Location_Data> call, Response<Location_Data> response) {
                 //   Log.e("Entered resp", response.message());
               //     Log.e("Entered resp", response.body().getMessage());

                    if (response.isSuccessful()) {
                        Location_Data class_locaitonData = response.body();
                        Log.e("response.body", response.body().getLst().toString());
                        if (class_locaitonData.getStatus().equals(true)) {
                            List<Location_DataList> yearlist = response.body().getLst();
                            Log.e("programlist.size()", String.valueOf(yearlist.size()));

                            location_dataLists = new Location_DataList[yearlist.size()];
                            // Toast.makeText(getContext(), "" + class_monthCounts.getMessage(), Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < location_dataLists.length; i++) {


                                Log.e("status", String.valueOf(class_locaitonData.getStatus()));
                           *//* Log.e("msg", class_loginresponse.getMessage());
                            Log.e("list", class_loginresponse.getList().get(i).getId());
                            Log.e("list", class_loginresponse.getList().get(i).getProgramCode());
                            Log.e("size", String.valueOf(class_loginresponse.getList().size()));*//*

                                class_location_dataList.setState((class_locaitonData.getLst().get(i).getState()));
                                class_location_dataList.setDistrict(class_locaitonData.getLst().get(i).getDistrict());
                                class_location_dataList.setPanchayat(class_locaitonData.getLst().get(i).getPanchayat());
                                class_location_dataList.setVillage(class_locaitonData.getLst().get(i).getVillage());

                                for(int j=0;j<class_locaitonData.getLst().get(i).getState().size();j++){
                                    Log.e("tag","state name=="+class_locaitonData.getLst().get(j).getState().get(j).getStateName());
                                    String StateName = class_locaitonData.getLst().get(j).getState().get(j).getStateName();
                                    String StateId = class_locaitonData.getLst().get(j).getState().get(j).getStateID();
                                    DBCreate_StatedetailsRest_insert_2SQLiteDB(StateId,StateName,StateId,j);
                                }
                            }

                        } else {
                            Toast.makeText(Activity_ViewFarmers.this, class_locaitonData.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Log.e("Entered resp else", "");
                        DefaultResponse error = ErrorUtils.parseError(response);
                        // … and use it to show error information

                        // … or just log the issue like we’re doing :)
                        Log.e("error message", error.getMsg());

                        Toast.makeText(Activity_ViewFarmers.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(Activity_ViewFarmers.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });// end of call

    }*/
    private void NormalLoginNew() {

        // Call call = userService1.getLocationDataNew(str_employee_id);
        Call call = userService1.getLocationDataNew("101");

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                Toast.makeText(Activity_ViewFarmers.this, "" + response.toString(), Toast.LENGTH_SHORT).show();

                // Log.e("response",response.body().toString());
                Location_Data user_object = new Location_Data();
                user_object = (Location_Data) response.body();
                // String x=response.body().toString();

              /*  try{
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String y=jsonObject.getString("Status").toString();
                    Toast.makeText(getApplicationContext(),"S:"+y,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error ponds",e.toString());
                }*/


                //     Log.e("response",user_object.getStatus().toString());

                //  Toast.makeText(Activity_ViewFarmers.this, ""+user_object.getStatus().toString(), Toast.LENGTH_LONG).show();

                Toast.makeText(Activity_ViewFarmers.this, "stateName=" + user_object.getLst().get(0).getState().get(0).getStateName(), Toast.LENGTH_LONG).show();

               /* SaveSharedPreference.setUserMailID(Activity_ViewFarmers.this,user_object.getLst().get(0).getUserID());

                editor_obj = sharedpreferencebook_usercredential_Obj.edit();

                editor_obj.putString(KeyValue_User_ID, user_object.getLst().get(0).getUserID());
                editor_obj.putString(KeyValue_User_Name, user_object.getLst().get(0).getUserName());
                editor_obj.putString(KeyValue_User_Email, user_object.getLst().get(0).getUserEmail());
                editor_obj.putString(KeyValue_User_Role, user_object.getLst().get(0).getUserRole());
                editor_obj.putString(KeyValue_User_State, user_object.getLst().get(0).getUserState());
                editor_obj.putString(KeyValue_User_State_Amount, user_object.getLst().get(0).getUserStateAmount());
                editor_obj.putString(KeyValue_User_Desigation, user_object.getLst().get(0).getUserDesigation());
                editor_obj.putString(KeyValue_Response, user_object.getLst().get(0).getResponse());


                editor_obj.commit();
*/
                Log.e("tag", "stateName==" + user_object.getLst().get(0).getState().get(0).getStateName());

                //editor_obj.putString(KeyValue_employeeid, "78");
                //editor_obj.putString(KeyValue_employeeid, "66");
              /*  editor_obj.putString(KeyValue_employeeid, user_object.getLst().get(0).getUserID());
                editor_obj.putString(KeyValue_employeename, user_object.getLst().get(0).getUserName());
                editor_obj.putString(KeyValue_employee_mailid, user_object.getLst().get(0).getUserEmail());
                editor_obj.putString(KeyValue_employeecategory, user_object.getLst().get(0).getUserRole());
                editor_obj.putString(KeyValue_employeesandbox, user_object.getLst().get(0).getUserState());
                editor_obj.putString(KeyValue_perdayamount, user_object.getLst().get(0).getUserStateAmount());



                editor_obj.commit();


               *//* Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);*//*

                Intent intent = new Intent(MainActivity.this,Activity_HomeScreen.class);
                startActivity(intent);*/
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("tag", "error" + t.getMessage());
                Toast.makeText(Activity_ViewFarmers.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
  /*  private void GetFarmer_PondValuesRestData(){

        Call<UserData> call = userService1.getUserData("40");


        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                //   Log.e("Entered resp", response.message());
                //     Log.e("Entered resp", response.body().getMessage());

                if (response.isSuccessful()) {
                    UserData class_locaitonData = response.body();
                    Log.e("response.body", response.body().getLst().toString());
                    if (class_locaitonData.getStatus().equals(true)) {
                        List<UserDataList> yearlist = response.body().getLst();
                        Log.e("programlist.size()", String.valueOf(yearlist.size()));

                        location_dataLists = new Location_DataList[yearlist.size()];
                        // Toast.makeText(getContext(), "" + class_monthCounts.getMessage(), Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < location_dataLists.length; i++) {


                            Log.e("status", String.valueOf(class_locaitonData.getStatus()));
                           *//* Log.e("msg", class_loginresponse.getMessage());
                            Log.e("list", class_loginresponse.getList().get(i).getId());
                            Log.e("list", class_loginresponse.getList().get(i).getProgramCode());
                            Log.e("size", String.valueOf(class_loginresponse.getList().size()));*//*


     *//* class_location_dataList.setState((class_locaitonData.getLst().get(i).getState()));
                            class_location_dataList.setDistrict(class_locaitonData.getLst().get(i).getDistrict());
                            class_location_dataList.setPanchayat(class_locaitonData.getLst().get(i).getPanchayat());
                            class_location_dataList.setVillage(class_locaitonData.getLst().get(i).getVillage());*//*
                            Log.e("tag","farmer name=="+class_locaitonData.getLst().get(0).getFarmer().get(0).getFarmerFirstName());
                            //   location_dataLists[i] = class_locaitonData;
                            //  Log.e("spinner month", location_dataLists[i].getState().get(i).getStateName());
                            //Log.e("spinner getTotalIdeas", String.valueOf(arraymonthlist[i].getTotalIdeas()));

                            // classMonthCountLists_arr.add(new Class_MonthCountList(class_monthCountList.getId(),class_monthCountList.getMonthName(),class_monthCountList.getMonthCode(),class_monthCountList.getTotalIdeas()));
                            //CreateCardViewProgrammatically();

                        }
                    } else {
                        Toast.makeText(Activity_ViewFarmers.this, class_locaitonData.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("error message", error.getMsg());

                    Toast.makeText(Activity_ViewFarmers.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(Activity_ViewFarmers.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }
*/
   /* private void GetDropdownValues()
    {
        final ProgressDialog dialog = new ProgressDialog(Activity_ViewFarmers.this, R.style.AppCompatAlertDialogStyle);


        //getting the progressbar
        // final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        //  progressBar.setVisibility(View.VISIBLE);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String str_JSON_LocationURL = Class_URL.JSON_LocationURL.toString().trim();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_JSON_LocationURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);
//                        dialog.dismiss();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray stateArray = obj.getJSONArray("States");
                            // Log.e("state", String.valueOf(stateArray));

                            JSONArray districtArray = obj.getJSONArray("District");
                            JSONArray talukArray = obj.getJSONArray("Taluks");
                            JSONArray panchayatArray = obj.getJSONArray("Panchayat");//Panchayat
                            JSONArray villageArray = obj.getJSONArray("Village");

                            JSONArray yearArray = obj.getJSONArray("Year");
                            // Log.e("talukArray",Arrays.toString(new JSONArray[]{talukArray}));
                            // Log.e("stateArray",Arrays.toString(new JSONArray[]{stateArray}));
                            //Log.e("districtArray",Arrays.toString(new JSONArray[]{districtArray}));
                            // Log.e("villageArray",Arrays.toString(new JSONArray[]{villageArray}));
                            // Log.e("panchayatArray",Arrays.toString(new JSONArray[]{panchayatArray}));
                            // Log.e("yearArray",Arrays.toString(new JSONArray[]{yearArray}));

                            //Log.e("villageArray", String.valueOf(villageArray.length()));
                            //now looping through all the elements of the json array,29811
                            strArray_statename = new String[stateArray.length()];
                            strArray_stateid = new String[stateArray.length()];
                            strArray_districtname = new String[districtArray.length()];
                            strArray_districtid = new String[districtArray.length()];
                            strArray_Dist_stateid = new String[districtArray.length()];
                            strArray_selecteddistname = new String[districtArray.length()];
                            strArray_talukid = new String[talukArray.length()];
                            strArray_talukname = new String[talukArray.length()];
                            strArray_taluk_distid = new String[talukArray.length()];

                            strArray_panchayatid = new String[panchayatArray.length()];
                            strArray_panchayatname = new String[panchayatArray.length()];
                            strArray_panchayat_talukid = new String[panchayatArray.length()];

                            strArray_villageid = new String[villageArray.length()];
                            strArray_village_talukid = new String[villageArray.length()];
                            strArray_villagename = new String[villageArray.length()];
                            strArray_village_panchayatid=new String[villageArray.length()];

                            StrArray_year = new String[yearArray.length()];
                            StrArray_year_ID = new String[yearArray.length()];


                            arrayObj_Class_stateDetails2 = new Class_StateListDetails[stateArray.length()];
                            arrayObj_Class_DistrictListDetails2 = new Class_DistrictListDetails[districtArray.length()];
                            arrayObj_Class_TalukListDetails2 = new Class_TalukListDetails[talukArray.length()];

                            arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[panchayatArray.length()];
                            arrayObj_Class_VillageListDetails2 = new Class_VillageListDetails[villageArray.length()];
                            arrayObj_Class_yearDetails2 = new Class_YearListDetails[yearArray.length()];


                            for (int i = 0; i < yearArray.length(); i++)
                            {

                                //getting the json object of the particular index inside the array
                                JSONObject yearObject = yearArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object

                                Class_YearListDetails class_yearListDetails = new Class_YearListDetails(yearObject.getString("year_id"), yearObject.getString("year"));
                                StrArray_year[i] = yearObject.getString("year");
                                StrArray_year_ID[i] = yearObject.getString("year_id");
                                class_yearListDetails.setYear(StrArray_year[i]);
                                class_yearListDetails.setYearID(StrArray_year_ID[i]);
                                arrayObj_Class_yearDetails2[i] = class_yearListDetails;
                                // Log.e("StrArray_year",StrArray_year[i]);

                                yearList.add(class_yearListDetails);

                                DBCreate_Yeardetails_insert_2SQLiteDB(StrArray_year_ID[i], StrArray_year[i],i);

                            }

                            for (int i = 0; i < stateArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject StateObject = stateArray.getJSONObject(i);
                                //JSONObject yearObject = yearArray.getJSONObject(i);
                                //creating a hero object and giving them the values from json object
                                Class_StateListDetails class_stateListDetails = new Class_StateListDetails(StateObject.getString("state_id"), StateObject.getString("state_name"));
                                strArray_statename[i] = StateObject.getString("state_name");
                                strArray_stateid[i] = StateObject.getString("state_id");

                                //StrArray_year_ID[i] = yearObject.getString("year_id");

                                class_stateListDetails.setState_name(strArray_statename[i]);
                                class_stateListDetails.setState_id(strArray_stateid[i]);
                                //class_stateListDetails.setYear_id(StrArray_year_ID[i]);
                                arrayObj_Class_stateDetails2[i] = class_stateListDetails;
                                //adding the hero to herolist
                                // Log.e("strArray_statename..",strArray_statename[i]);
                                stateList.add(class_stateListDetails);
                                DBCreate_Statedetails_insert_2SQLiteDB(strArray_stateid[i], strArray_statename[i], strArray_stateid[i],i);

                            }
                            for (int i = 0; i < districtArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject districtObject = districtArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Class_DistrictListDetails class_districtListDetails = new Class_DistrictListDetails(districtObject.getString("district_id"), districtObject.getString("district_name"), districtObject.getString("st_id"));
                                strArray_districtname[i] = districtObject.getString("district_name");
                                strArray_districtid[i] = districtObject.getString("district_id");
                                strArray_Dist_stateid[i] = districtObject.getString("st_id");
                                class_districtListDetails.setDistrict_id(strArray_districtid[i]);
                                class_districtListDetails.setDistrict_name(strArray_districtname[i]);
                                class_districtListDetails.setState_id(strArray_Dist_stateid[i]);
                                arrayObj_Class_DistrictListDetails2[i] = class_districtListDetails;

                                //adding the hero to herolist
                                // Log.e("strArray_districtname..",strArray_districtname[i]);
                                districtList.add(class_districtListDetails);

                                DBCreate_Districtdetails_insert_2SQLiteDB(strArray_districtid[i], strArray_districtname[i], "Y1", strArray_Dist_stateid[i],i);

                            }

                            for (int i = 0; i < talukArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject talukObject = talukArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Class_TalukListDetails class_talukListDetails = new Class_TalukListDetails(talukObject.getString("taluka_id"), talukObject.getString("taluk_name"), talukObject.getString("district_id"));
                                strArray_talukname[i] = talukObject.getString("taluk_name");
                                strArray_talukid[i] = talukObject.getString("taluka_id");//taluka_id,taluk_id
                                strArray_taluk_distid[i] = talukObject.getString("district_id");//district_id,dist_id
                                arrayObj_Class_TalukListDetails2[i] = class_talukListDetails;

                                //adding the hero to herolist
                                //Log.e("strArray_talukid..",strArray_talukid[i]);
                                //Log.e("strArray_taluk_distid..",strArray_taluk_distid[i]);
                                //Log.e("strArray_talukid..",strArray_talukid[i]);
                                talukList.add(class_talukListDetails);

                                DBCreate_Talukdetails_insert_2SQLiteDB(strArray_talukid[i], strArray_talukname[i], strArray_taluk_distid[i],i);

                            }

                            //  for (int i = 0; i < 70; i++) {
                            for (int i = 0; i < villageArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject villageObject = villageArray.getJSONObject(i);

                         ////"village_id":"604936","taluk_id":"5500","panchayat_id":"216105","village_name":"AMARAPURA"},
                                Class_VillageListDetails class_villageListDetails = new Class_VillageListDetails(villageObject.getString("village_id"),
                                        villageObject.getString("village_name"), villageObject.getString("taluk_id"),villageObject.getString("panchayat_id"));
                                strArray_villagename[i] = villageObject.getString("village_name");
                                strArray_villageid[i] = villageObject.getString("village_id");
                                strArray_village_talukid[i] = villageObject.getString("taluk_id");
                                strArray_village_panchayatid[i]=villageObject.getString("panchayat_id");
                                arrayObj_Class_VillageListDetails2[i] = class_villageListDetails;

                                //adding the hero to herolist
                                //  Log.e("str_villae_talukid..",strArray_village_talukid[i]);
                                villageList.add(class_villageListDetails);
                                DBCreate_Villagedetails_insert_2SQLiteDB(strArray_villageid[i], strArray_villagename[i], strArray_village_talukid[i],strArray_village_panchayatid[i],i);


                            }

                            for (int i = 0; i < panchayatArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject panchayatObject = panchayatArray.getJSONObject(i);


                                //[{"panchayat_id":"216191","Taluka_ID":"5498","panchayat_name":"114.DANAPURA"}


                                Class_GrampanchayatListDetails class_grampanchayatListDetails = new Class_GrampanchayatListDetails(panchayatObject.getString("panchayat_id"), panchayatObject.getString("panchayat_name"), panchayatObject.getString("Taluka_ID"));
                                strArray_panchayatname[i] = panchayatObject.getString("panchayat_name");
                                strArray_panchayatid[i] = panchayatObject.getString("panchayat_id");
                                strArray_panchayat_talukid[i] = panchayatObject.getString("Taluka_ID");
                                arrayObj_Class_GrampanchayatListDetails2[i] = class_grampanchayatListDetails;

                                //adding the hero to herolist
                                // Log.e("strArrhayatname",strArray_panchayatname[i]);
                                panchayatList.add(class_grampanchayatListDetails);

                                DBCreate_Grampanchayatdetails_insert_2SQLiteDB(strArray_panchayatid[i], strArray_panchayatname[i], strArray_panchayat_talukid[i],i);

                            }

                            uploadfromDB_Yearlist();
                            uploadfromDB_Statelist();
                            uploadfromDB_Districtlist();
                            uploadfromDB_Taluklist();
                            uploadfromDB_Villagelist();
                            uploadfromDB_Grampanchayatlist();

//                            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
//                            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            statelist_SP.setAdapter(dataAdapter);
//                            ArrayAdapter dataAdapter_dist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_DistrictListDetails2);
//                            dataAdapter_dist.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            districtlist_SP.setAdapter(dataAdapter_dist);
//                            ArrayAdapter dataAdapter_taluk= new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
//                            dataAdapter_taluk.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            taluklist_SP.setAdapter(dataAdapter_taluk);
//                            ArrayAdapter dataAdapter_village= new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
//                            dataAdapter_village.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            villagelist_SP.setAdapter(dataAdapter_village);
//                            ArrayAdapter dataAdapter_panchayat= new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_GrampanchayatListDetails2);
//                            dataAdapter_panchayat.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            grampanchayatlist_SP.setAdapter(dataAdapter_panchayat);


//                            for(int i=0;i<strArray_Dist_stateid.length;i++) {
//                                if (sp_strstate_ID.equals(strArray_Dist_stateid[i])) {
//
//                                    strArray_selecteddistname[i]=strArray_districtname[i];
////                                    ArrayAdapter dataAdapter_dist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, strArray_districtname);
////                                    dataAdapter_dist.setDropDownViewResource(R.layout.spinnercenterstyle);
////                                    districtlist_SP.setAdapter(dataAdapter_dist);
//                                }
//
//                                   ArrayAdapter dataAdapter_dist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, strArray_selecteddistname);
//                                   dataAdapter_dist.setDropDownViewResource(R.layout.spinnercenterstyle);
//                                   districtlist_SP.setAdapter(dataAdapter_dist);
//
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.e("errormsg_catch", e.getMessage());
                        }

                        dialog.dismiss();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        // error.printStackTrace();
                       // Log.e("errormsg", error.getMessage().toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

        @Override
        protected Map<String,String> getParams()
        {
            Map<String,String> params = new HashMap<String, String>();
            params.put("employee_id",str_employee_id);
            return params;
        }

    };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(9000,
                                                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }*/

  /*  public void GetFarmerDetails()
    {
        final ProgressDialog dialog = new ProgressDialog(Activity_ViewFarmers.this, R.style.AppCompatAlertDialogStyle);


        //getting the progressbar
        // final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        //  progressBar.setVisibility(View.VISIBLE);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String str_JSON_FarmerDetailsURL = Class_URL.URL_FarmerDetails.toString().trim();


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_JSON_FarmerDetailsURL,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);
                        dialog.dismiss();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray farmerArray = obj.getJSONArray("Farmers");


                            //Log.e("farmerarraylength",String.valueOf(farmerArray.length()));
                            //Log.e("farmerArray",Arrays.toString(new JSONArray[]{farmerArray}));
                            //now looping through all the elements of the json array
                            strArray_farmername = new String[farmerArray.length()];
                            StrArray_farmercode = new String[farmerArray.length()];
                            StrArray_farmerlist_farmerid = new String[farmerArray.length()];
                            StrArray_farmerlist_yearid = new String[farmerArray.length()];
                            StrArray_farmerlist_stateid = new String[farmerArray.length()];
                            StrArray_farmerlist_distid = new String[farmerArray.length()];
                            StrArray_farmerlist_talukid = new String[farmerArray.length()];
                            StrArray_farmerlist_villageid = new String[farmerArray.length()];
                            StrArray_farmerlist_panchayatid = new String[farmerArray.length()];
                            StrArray_farmerimage= new String[farmerArray.length()];
                            strArray_farmpondcount=new String[farmerArray.length()];
                            strArray_farmerBase64=new String[farmerArray.length()];

                            strArray_farmerMname=new String[farmerArray.length()];
                            strArray_farmerLname=new String[farmerArray.length()];
                            strArray_farmerage=new String[farmerArray.length()];
                            strArray_farmercellnumber=new String[farmerArray.length()];
                             strArray_farmerincome=new String[farmerArray.length()];
                            strArray_farmerfamilymember=new String[farmerArray.length()];
                            strArray_farmerIDprooftype=new String[farmerArray.length()];
                            strArray_farmerIDproofnumber=new String[farmerArray.length()];



                            arrayObj_Class_FarmerListDetails2 = new Class_FarmerListDetails[farmerArray.length()];

                            class_farmerlistdetails_arrayobj2=new Class_FarmerListDetails[farmerArray.length()];


                           *//* for (int i = 0; i < farmerArray.length(); i++)
                            {

                            }*//*


                            int k=0;
                            class_farmerimageBase64_array=new Class_farmerimageBase64[farmerArray.length()];
                            *//*for ( k = 0; k < farmerArray.length(); k++)
                            {

                                JSONObject farmerObject = farmerArray.getJSONObject(k);
                                AsyncCallWS_imageurltobase64_farmerimage task = new AsyncCallWS_imageurltobase64_farmerimage(Activity_ViewFarmers.this);
                                task.execute(farmerObject.getString("farmer_image"));

                            }*//*

                          //  if(k==farmerArray.length())
                            {

                                for (int m = 0; m < farmerArray.length(); m++) {
                                    // Log.e("fimage",class_farmerimageBase64_array[m].getStr_farmerimagebase64().toString());
                                    Log.e("inside", "for loop");
                                }

                            }


                            for (int i = 0; i < farmerArray.length(); i++)
                            {

                              //  Log.e("farmerpic",StrArray_farmerimage[i].toString());

                                //getting the json object of the particular index inside the array
                                JSONObject farmerObject = farmerArray.getJSONObject(i);

                                // Log.e("farmername.",farmerObject.getString("farmer_name"));

                                //creating a hero object and giving them the values from json object
                                Class_FarmerListDetails class_farmerListDetails = new Class_FarmerListDetails(
                                        farmerObject.getString("year_id"),
                                        farmerObject.getString("st_id"),
                                        farmerObject.getString("dist_id"),
                                        farmerObject.getString("tlk_id"),
                                        farmerObject.getString("village_id"),
                                        farmerObject.getString("panchayat_id"),
                                        farmerObject.getString("farmer_id"),
                                       *//* farmerObject.getString("farmer_code"),//farmer_code*//*
                                        farmerObject.getString("farmer_name"),
                                        farmerObject.getString("farmer_image"),
                                        farmerObject.getString("farmer_image"),

                                        farmerObject.getString("Middle_name"),
                                        farmerObject.getString("Last_name"),
                                        farmerObject.getString("age"),
                                        farmerObject.getString("contact"),
                                        farmerObject.getString("Annual_income"),
                                        farmerObject.getString("Family_members"),
                                        farmerObject.getString("Id_proof_type"),
                                        farmerObject.getString("ID_proof"),

                                        farmerObject.getString("farmpond_count"),
                                        null);//farmer_image


                                class_farmerlistdetails_arrayobj2[i]=class_farmerListDetails;
*//*

                                Log.e("N_yearid",class_farmerlistdetails_arrayobj2[i].getYearid());
                                Log.e("N_stateid",class_farmerlistdetails_arrayobj2[i].getStateid());
                                Log.e("N_district",class_farmerlistdetails_arrayobj2[i].getDistrictid());
                                Log.e("N_taluk",class_farmerlistdetails_arrayobj2[i].getTalukid());
                                Log.e("N_villageid",class_farmerlistdetails_arrayobj2[i].getVillageid());
                                Log.e("N_panchayathid",class_farmerlistdetails_arrayobj2[i].getGrampanchayatid());
                                Log.e("N_farmerid",class_farmerlistdetails_arrayobj2[i].getFarmerid());
                                Log.e("N_farmername",class_farmerlistdetails_arrayobj2[i].getFarmername());
                                Log.e("N_farmerimage",class_farmerlistdetails_arrayobj2[i].getFarmerimage());
                                Log.e("N_farmerpondcount",class_farmerlistdetails_arrayobj2[i].getFarmpondcount());
                                Log.e("N_farmerimage64",class_farmerlistdetails_arrayobj2[i].getStr_base64());
*//*



                                strArray_farmername[i] = farmerObject.getString("farmer_name");//farmer_name
                                StrArray_farmercode[i] = farmerObject.getString("farmer_code");
                                StrArray_farmerlist_farmerid[i] = farmerObject.getString("farmer_id");
                                StrArray_farmerlist_yearid[i] = farmerObject.getString("year_id");
                                StrArray_farmerlist_stateid[i] = farmerObject.getString("st_id");
                                StrArray_farmerlist_distid[i] = farmerObject.getString("dist_id");
                                StrArray_farmerlist_talukid[i] = farmerObject.getString("tlk_id");
                                StrArray_farmerlist_villageid[i] = farmerObject.getString("village_id");
                                StrArray_farmerlist_panchayatid[i] = farmerObject.getString("panchayat_id");
                                strArray_farmpondcount[i] = farmerObject.getString("farmpond_count");
                                StrArray_farmerimage[i] = farmerObject.getString("farmer_image");

//                                Log.e("farmer_id.", farmerObject.getString("farmer_id"));
//                                Log.e("year_id.", farmerObject.getString("year_id"));
//                                Log.e("st_id.", farmerObject.getString("st_id"));
//                                Log.e("dist_id.", farmerObject.getString("dist_id"));
//                                Log.e("tlk_id.", farmerObject.getString("tlk_id"));
//                                Log.e("panchayat_id.", farmerObject.getString("panchayat_id"));
//                                Log.e("village_id.", farmerObject.getString("village_id"));
//                                Log.e("farmer_code.", farmerObject.getString("farmer_code"));
                               // Log.e("farmer_image.", farmerObject.getString("farmer_image"));

                                *//*
                                [{"st_id":"25","dist_id":"635","tlk_id":"5663","village_id":"626707",
                                "panchayat_id":"18","farmer_id":"1","farmer_name":"Surender Reddy",
                                "farmer_code":"TS25TEMP282","year_id":"1","year":"2017","farmer_image":"null"},
                                 *//*
                                class_farmerListDetails.setFarmername(strArray_farmername[i]);
                              //  class_farmerListDetails.setFarmercode(StrArray_farmercode[i]);
                                class_farmerListDetails.setFarmerid(StrArray_farmerlist_farmerid[i]);
                                class_farmerListDetails.setFarmerimage(StrArray_farmerimage[i]);
                                arrayObj_Class_FarmerListDetails2[i] = class_farmerListDetails;


                              *//*  for (int k = 0; k < 1; k++)
                                {
                                    AsyncCallWS_imageurltobase64_farmerimage task = new AsyncCallWS_imageurltobase64_farmerimage(Activity_ViewFarmers.this);
                                    task.execute(farmerObject.getString("farmer_image"));

                                }*//*


                                //task.execute();
                               //String image_value= String.valueOf(task.execute(farmerObject.getString("farmer_image")));

                              // Log.e("image_value",str_imageurltobase64_farmerimage);

                               // strArray_farmerBase64[i]=str_imageurltobase64_farmerimage;

                                        //strArray_farmerBase64[i]= String.valueOf(task.execute(farmerObject.getString("farmer_image")));


                                Class_FarmerListDetails item;
                                item = new Class_FarmerListDetails(farmerObject.getString("year_id"),
                                        farmerObject.getString("st_id"),
                                        farmerObject.getString("dist_id"),
                                        farmerObject.getString("tlk_id"),
                                        farmerObject.getString("village_id"),
                                        farmerObject.getString("panchayat_id"),
                                        farmerObject.getString("farmer_id"),
                                       *//* farmerObject.getString("farmer_code"),*//*
                                        farmerObject.getString("farmer_name"),
                                        farmerObject.getString("farmer_image"),
                                        farmerObject.getString("farmer_image"),

                                        farmerObject.getString("Middle_name"),
                                        farmerObject.getString("Last_name"),
                                        farmerObject.getString("age"),
                                        farmerObject.getString("contact"),
                                        farmerObject.getString("Annual_income"),
                                        farmerObject.getString("Family_members"),
                                        farmerObject.getString("Id_proof_type"),
                                        farmerObject.getString("ID_proof"),


                                        farmerObject.getString("farmpond_count"),
                                       null);//farmer_image

                                //item = new Class_FarmerListDetails(strArray_farmername[i], StrArray_farmercode[i]);
                                ViewFarmerList_arraylist.add(item);


                                farmerList.add(class_farmerListDetails);


*//*
                                DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(StrArray_farmerlist_yearid[i], StrArray_farmerlist_stateid[i],
                                        StrArray_farmerlist_distid[i], StrArray_farmerlist_talukid[i], StrArray_farmerlist_villageid[i],
                                        StrArray_farmerlist_panchayatid[i], StrArray_farmerlist_farmerid[i], StrArray_farmercode[i],
                                        strArray_farmername[i], StrArray_farmerimage[i],strArray_farmpondcount[i],
                                        strArray_farmerBase64[i]);*//*
                            }



                            AsyncCallWS_imageurltobase64_farmerimage2 task2 = new AsyncCallWS_imageurltobase64_farmerimage2(Activity_ViewFarmers.this);
                            task2.execute();


                         //   uploadfromDB_Farmerlist();




//                            originalViewFarmerList = new ArrayList<Class_FarmerListDetails>();
//                            originalViewFarmerList.addAll(ViewFarmerList_arraylist);
//
//                            farmerListViewAdapter.notifyDataSetChanged();
//                            farmer_listview.setAdapter(farmerListViewAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("error", e.toString());
                        }





                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Log.e("error", error.toString());
                        // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>();
                params.put("employee_id",str_employee_id);
                return params;
            }

        };




        stringRequest.setRetryPolicy(new DefaultRetryPolicy(9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }*/

    public void Update_ImageLocaly() {
        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
     /*   db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,DispFarmerTable_FarmerImage VARCHAR,LocalFarmerImg BLOB);");
*/
        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");

    /*String SQLiteQuery = "UPDATE ViewFarmerList SET DispFarmerTable_FarmerImage = "+str_img+" WHERE DispFarmerTable_FarmerID = "+ str_selected_farmerID_forimagesaving;

    db_viewfarmerlist.execSQL(SQLiteQuery);*/
        Log.e("tag", "byte b=" + b);
        ContentValues cv = new ContentValues();
        cv.put("LocalFarmerImg", b);
        db_viewfarmerlist.update("ViewFarmerListRest", cv, "DispFarmerTable_FarmerID=" + str_selected_farmerID_forimagesaving, null);

        db_viewfarmerlist.close();
        Update_ids_farmerlist_listview(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strgrampanchayat_ID);

    }

    public static void SaveFarmerImage(final String FarmerId, final String FarmerImg, final Context context1) {

        final ProgressDialog dialog = new ProgressDialog(context1, R.style.AppCompatAlertDialogStyle);


        //getting the progressbar
        // final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        //  progressBar.setVisibility(View.VISIBLE);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        String str_JSON_FarmerImageURL = Class_URL.JSON_FarmerImageURL.toString().trim();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_JSON_FarmerImageURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);
                        dialog.dismiss();

                        parse_farmpondresponse(context1, response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Log.e("error", error.toString());
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context1, "No Internet", Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


//                params.put("farmer_id","1");
            /*params.put("Farmer_ID",str_selected_farmerID_forimagesaving);//str_selected_farmerID_forimagesaving,str_selected_farmerID_forimagesaving
            params.put("image_link",str_img);*/
                params.put("Farmer_ID", FarmerId);//str_selected_farmerID_forimagesaving,str_selected_farmerID_forimagesaving
                params.put("image_link", FarmerImg);
                return params;
            }

        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context1);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public static void parse_farmpondresponse(Context context1, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("statusMessage").equalsIgnoreCase("success")) {

                Toast.makeText(context1, "Image updated successfully to verify click on refesh button", Toast.LENGTH_LONG).show();
            }
            if (jsonObject.getString("statusMessage").equalsIgnoreCase("Farmer_ID Empty")) {

                Toast.makeText(context1, "Sorry,could not save image", Toast.LENGTH_LONG).show();
            }
            if (jsonObject.getString("statusMessage").equalsIgnoreCase("failed")) {

                Toast.makeText(context1, "Sorry,could not save image", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }

    }

    private class Holder {

        ///////////////////////sept16th2019
        TextView FarmerNamelabel_tv;
        TextView FarmerName_tv;
        TextView FarmerCodelabel_tv;
        TextView farmercode_tv;
        ImageView farmerimage_iv;
        Button farmpond_bt;

        ///////////////////////sept16th2019

    }


    public static class ViewHolder {


        public ImageView farmerimage_iv;
        ///////////////////////sept16th2019
        TextView FarmerNamelabel_tv;
        TextView FarmerName_tv;
        TextView FarmerIDlabel_tv;
        TextView farmercode_tv;
        Button farmpond_bt;
        TextView farmerpondcount_tv;
        //j TextView status_tv;

        ///////////////////////sept16th2019

    }

    public class FarmerListViewAdapter extends BaseAdapter {


        public ArrayList<Farmer> projList;
        Activity activity;
        private ArrayList<Farmer> mDisplayedValues = null;

        public FarmerListViewAdapter(Activity activity, ArrayList<Farmer> projList) {
            super();
            this.activity = activity;
            this.projList = projList;
            this.mDisplayedValues = projList;
        }

        @Override
        public int getCount() {
            //return projList.size();
            Log.e("size", String.valueOf(mDisplayedValues.size()));
            return mDisplayedValues.size();

        }

        @Override
        public Farmer getItem(int position) {

            //return projList.get(position);
            return mDisplayedValues.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

//            ViewHolder holder;
            LayoutInflater inflater = activity.getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.child_farmerlist_item, null);
                holder = new ViewHolder();

                holder.FarmerNamelabel_tv = (TextView) convertView.findViewById(R.id.FarmerNamelabel_tv);
                holder.FarmerName_tv = (TextView) convertView.findViewById(R.id.FarmerName_tv);
                holder.FarmerIDlabel_tv = (TextView) convertView.findViewById(R.id.FarmerIDlabel_tv);
                holder.farmercode_tv = (TextView) convertView.findViewById(R.id.farmercode_tv);
                holder.farmpond_bt = (Button) convertView.findViewById(R.id.farmpond_bt);
                holder.farmerimage_iv = (ImageView) convertView.findViewById(R.id.farmerimage_iv);
                holder.farmerpondcount_tv = (TextView) convertView.findViewById(R.id.pondcount_tv);
                // holder.status_tv=(TextView)convertView.findViewById(R.id.status_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Farmer Obj_Class_farmerlistdetails = (Farmer) getItem(position);

//
            if (Obj_Class_farmerlistdetails != null) {
                Log.e("listfarmername", Obj_Class_farmerlistdetails.getFarmerFirstName());
                Log.e("listfarmerid", Obj_Class_farmerlistdetails.getFarmerID());

                /*if(!(Obj_Class_farmerlistdetails.getFarmername().equalsIgnoreCase("name")||
                        !(Obj_Class_farmerlistdetails.getFarmercode().equalsIgnoreCase("farmer_code"))))*/
                {

                    holder.FarmerName_tv.setText(Obj_Class_farmerlistdetails.getFarmerFirstName());
                    holder.farmercode_tv.setText(Obj_Class_farmerlistdetails.getFarmerID());

                    holder.farmerimage_iv.setTag(position);
                    String str_fetched_imgfile = Class_URL.URL_farmerimageurl + Obj_Class_farmerlistdetails.getFarmerPhoto();
                    Log.e("Obj_ClFarmerimage()", Obj_Class_farmerlistdetails.getFarmerPhoto());
                    Log.e("getLocalfarmerimage()", String.valueOf(Obj_Class_farmerlistdetails.getLocalfarmerimage()));


//                    holder.status_tv.setText(Obj_Class_farmerlistdetails.getResponse());
                    holder.farmerpondcount_tv.setText(Obj_Class_farmerlistdetails.getFarmPond_Count());
                    /*if(Obj_Class_farmerlistdetails.getFarmpondcount().isEmpty()
                            ||Obj_Class_farmerlistdetails.getFarmpondcount().equals(null))*/
                        /*if(Obj_Class_farmerlistdetails.getFarmpondcount().equals(null))
                    {
                        holder.farmerpondcount_tv.setText("0");
                    }else{
                        holder.farmerpondcount_tv.setText(Obj_Class_farmerlistdetails.getFarmpondcount());
                    }*/

               /* if(Obj_Class_farmerlistdetails.getFarmerimage()!=null) {
                    imgLoader.displayImage(str_fetched_imgfile, holder.farmerimage_iv, displayoption, imageListener);
                }*/

                    if (Obj_Class_farmerlistdetails.getStr_base64() != null) {


                        // Log.e("imagelist_if",Obj_Class_farmerlistdetails.getStr_base64().toString());
                        if (Obj_Class_farmerlistdetails.getStr_base64().equalsIgnoreCase("null")) {
                        } else {
                            imageBytes_list = Base64.decode(Obj_Class_farmerlistdetails.getStr_base64(), Base64.DEFAULT);
                            Bitmap bmp_decodedImage1 = BitmapFactory.decodeByteArray(imageBytes_list, 0, imageBytes_list.length);
                            holder.farmerimage_iv.setImageBitmap(bmp_decodedImage1);
                        }
                    } else {
                        Log.e("imagelist_else", Obj_Class_farmerlistdetails.getStr_base64().toString());
                    }


                    if (Obj_Class_farmerlistdetails.getLocalfarmerimage() != null) {

                        if (Obj_Class_farmerlistdetails.getLocalfarmerimage().toString().contains(".jpeg")
                                || Obj_Class_farmerlistdetails.getLocalfarmerimage().toString().contains(".jpg")
                                || Obj_Class_farmerlistdetails.getLocalfarmerimage().toString().contains(".png")) {
                           /* byte[] str_imggg = Obj_Class_farmerlistdetails.getLocalfarmerimage();
                            Bitmap bmp = BitmapFactory.decodeByteArray(str_imggg, 0, str_imggg.length);
                            holder.farmerimage_iv.setImageBitmap(bmp);*/
                        }

                        // }

                    } else {
                        //    if (!Obj_Class_farmerlistdetails.getFarmerimage().equalsIgnoreCase("image/profileimg.png")) {


                        if (Obj_Class_farmerlistdetails.getFarmerPhoto() != null) {
                            if (Obj_Class_farmerlistdetails.getFarmerPhoto().toString().contains(".jpeg")
                                    || Obj_Class_farmerlistdetails.getFarmerPhoto().toString().contains(".jpg")
                                    || Obj_Class_farmerlistdetails.getFarmerPhoto().toString().contains(".png")) {

                                // imgLoader.displayImage(str_fetched_imgfile, holder.farmerimage_iv, displayoption, imageListener);
                            }
                        } else {
                            Log.e("farmerImage", "farmerimage null");
                        }
                    }

                    holder.farmerimage_iv.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(View v) {

                            int pos = (Integer) v.getTag();
                            Farmer Obj_Class_farmerlistdetails = (Farmer) getItem(pos);
                            Log.e("farmerimg", String.valueOf(pos));
                            // displayalert();
                            str_selected_farmerID_forimagesaving = Obj_Class_farmerlistdetails.getFarmerID();
                            Log.e("img_farmerid_onclick", str_selected_farmerID_forimagesaving);

                            Log.e("farmerID", Obj_Class_farmerlistdetails.getFarmerID());

                            Intent i = new Intent(getApplicationContext(), AddFarmer_Activity1.class);
                            i.putExtra("farmerID", Obj_Class_farmerlistdetails.getFarmerID());
                            startActivity(i);

                            //selectImage();
                        }
                    });

                }

            }
            /*else
            {

            }*/
            // }


            return convertView;
        }


        //////////////////////////////////////7thoct///////////////////////////////////


        private void selectImage() {
            final CharSequence[] items = {"Take Photo", "Choose from Library",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ViewFarmers.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result = Class_utility.checkPermission(Activity_ViewFarmers.this);
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


        //////////////////////////////////////7thoct///////////////////////////////////////

        public void filter(String charText, ArrayList<Farmer> projectList) {
            charText = charText.toLowerCase(Locale.getDefault());
            this.mDisplayedValues.clear();

            if (charText != null) {
                if (projectList != null) {
                    if (charText.isEmpty() || charText.length() == 0) {
                        this.mDisplayedValues.addAll(projectList);
                    } else {
                        for (Farmer wp : projectList) {
                            if (wp.getFarmerFirstName().toLowerCase(Locale.getDefault()).contains(charText.toLowerCase())) {
                                this.mDisplayedValues.add(wp);
                            }
                        }
                    }
                    notifyDataSetChanged();

                    //FarmerListViewAdapter.updateList(mDisplayedValues);
                }
            }
        }


//    public void updateList(ArrayList<Class_FarmerListDetails> list){
//        projList = list;
//        notifyDataSetChanged();
//    }

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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
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
        }
        //  holder.farmerimage_iv.setImageBitmap(bm);
        Update_ImageLocaly();
        if (isInternetPresent) {
            SaveFarmerImage(str_selected_farmerID_forimagesaving, str_img, this);
        } else {

            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
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
        //   holder.farmerimage_iv.setImageBitmap(thumbnail);
        BitMapToString(thumbnail);
        Update_ImageLocaly();

        if (isInternetPresent) {
            SaveFarmerImage(str_selected_farmerID_forimagesaving, str_img, this);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }








   /* private void AsyncTask_fetch_farmponddetails()
    {

       // final ProgressDialog pdLoading_fetch_farmponddetails = new ProgressDialog(Activity_ViewFarmers.this);

        pdLoading_fetch_farmponddetails = new ProgressDialog(Activity_ViewFarmers.this);
        pdLoading_fetch_farmponddetails.setMessage("\tLoading...");
        pdLoading_fetch_farmponddetails.setCancelable(false);
        pdLoading_fetch_farmponddetails.show();



        String str_fetchfarmponddetails_url = Class_URL.URL_farmer_with_pond_details_emp.toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {


                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                       //Log.e("volley_eachpondresponse",response);

                       parse_each_farmpondresponse(response);

                        *//*pdLoading.dismiss();

                        AsyncCallWS_imageurltobase64 task = new AsyncCallWS_imageurltobase64(Activity_ViewFarmers.this);
                        task.execute();*//*

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading_fetch_farmponddetails.dismiss();
                        Log.e("volley_eachpondresponse",error.toString());

                        Toast.makeText(Activity_ViewFarmers.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                } )
        {
            @Override
            protected Map<String,String> getParams()
            {





               *//* Drawable d = null; // the drawable (Captain Obvious, to the rescue!!!)
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmapdata = stream.toByteArray();*//*





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

            }
        };




        stringRequest.setRetryPolicy(new DefaultRetryPolicy(9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/


  /*  public void parse_each_farmpondresponse(String response)
    {

        try
        {
            JSONObject jsonObject = new JSONObject(response);

            //"StatusMessage": "Success"
            if (jsonObject.getString("StatusMessage").equalsIgnoreCase("Success"))
            {
                Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();

                JSONArray response_jsonarray = jsonObject.getJSONArray("Farmers");
                Log.e("pondlength", String.valueOf(response_jsonarray.length()));

                int int_jsonarraylength=response_jsonarray.length();

                class_farmerandpond_details_array_obj = new Class_FarmerAndPond_Details[int_jsonarraylength];

                for(int i=0;i<int_jsonarraylength;i++)
                {
                    Class_FarmerAndPond_Details class_farmerandpond_details_innerobj = new Gson().fromJson(String.valueOf(response_jsonarray.get(i).toString()), Class_FarmerAndPond_Details.class);

                    class_farmerandpond_details_array_obj[i]=class_farmerandpond_details_innerobj;



                    Log.e("new name", class_farmerandpond_details_innerobj.getFarmer_Name().toString());
                    //Log.e("new depth", class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().get(0).getFarmpond_Depth().toString());

                 //   Log.e("pond id", class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().get(0).getFarmpond_ID());

                    Log.e("value of i",String.valueOf(i));

                   Log.e( "farmpondsize",String.valueOf(class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().size()));

                   if(class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().size()>0)
                   {

                       if (class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().get(0).getClass_farmpondimages_obj().size() > 0)
                       {
                           Log.e("image_id", class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().get(0).getClass_farmpondimages_obj().get(0).getImage_ID().toString());

                           //Log.e("pond size",String.valueOf(class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().get(i).getClass_farmpondimages_obj().size()));

                           //Log.e("new id", class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().get(i).getClass_farmpondimages_obj().get(0).getImage_ID().toString());

                           //Log.e("new image", class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().get(i).getClass_farmpondimages_obj().get(0).getImage_url().toString());
                       } else {
                           Log.e("no_image", String.valueOf(i));
                           Log.e("pond_id", class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().get(0).getFarmpond_ID());
                       }

                   }
                   else
                   {
                       Log.e("no_farmerpond","no_farmerpond");
                   }
                    // Log.e("depth", class_farmponddetails_innerobj.getFarmpond_Depth().toString());
                    // Log.e("name", class_farmponddetails_innerobj.getClass_farmpondimages_obj().get(0).getImage_ID().toString());
                    //Log.e("name", String.valueOf(class_farmponddetails_innerobj.getClass_farmpondimages_obj().size()));
                    //Log.e("name", String.valueOf(class_farmponddetails_innerobj.getClass_farmpondimages_obj().size()));

                    //Incase of Offline
                   *//* if(class_farmponddetails_innerobj.getClass_farmpondimages_obj().size()>0)
                    {
                        for(int j=0;j<class_farmponddetails_innerobj.getClass_farmpondimages_obj().size();j++)
                        {

                        }

                    }*//*
                    //Incase of Offline


                }

                Log.e("length", String.valueOf(class_farmerandpond_details_array_obj.length));

               *//* for(int i=0;i<class_farmerandpond_details_array_obj.length;i++)
                {

                    if(class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(0).getClass_farmpondimages_obj().size()>0)
                    {


                            Log.e("size images", String.valueOf(class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(0).getClass_farmpondimages_obj().size()));


                    }

                    else{

                    }


                }*//*

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error ponds",e.toString());
        }



        pdLoading_fetch_farmponddetails.dismiss();

        AsyncCallWS_imageurltobase64 task = new AsyncCallWS_imageurltobase64(Activity_ViewFarmers.this);
        task.execute();


    }//parse_each_farmpondresponse
*/







  /*  private class AsyncCallWS_imageurltobase64 extends AsyncTask<String, Void, Void> {
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


            urltobase64();


            return null;
        }

        public AsyncCallWS_imageurltobase64(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();



//          DBCount();






        }//end of OnPostExecute

    }// end Async task

*/


  /*  public void urltobase64()
    {

        try
        {


            String str_length= String.valueOf(class_farmerandpond_details_array_obj.length);
            Log.e("pondimagelength",str_length);

            for (int i = 0; i < class_farmerandpond_details_array_obj.length; i++)
            {

                String str_farmerid, str_farmername, str_farmpond_id, str_width, str_height, str_depth, str_imageid, str_imageurl, str_farmpondbaseimage_url;

                String str_base64image1,str_base64image2,str_base64image3;
                String str_imageid1,str_imageid2,str_imageid3;

                str_base64image1="noimage1";str_base64image2="noimage2";str_base64image3="noimage3";
                str_imageid1="0";str_imageid2="0";str_imageid3="0";

                    Bitmap bmp_pondimages;
                str_farmpondbaseimage_url = Class_URL.URL_farmpondbaselink.toString().trim();


                str_farmerid = class_farmerandpond_details_array_obj[i].getFarmerID();
                str_farmername = class_farmerandpond_details_array_obj[i].getFarmer_First_Name();


                String str_farmerMName,str_farmerLName,str_farmerage,str_Fphonenumber,str_FannualIncome,
                        str_Ffamilymember,str_FIDprooftype,str_FIDproofno,str_Fphoto,str_empID,str_tempfid;

                str_farmerMName=class_farmerandpond_details_array_obj[i].getFarmer_Middle_Name();
                str_farmerLName=class_farmerandpond_details_array_obj[i].getFarmer_Last_Name();
             *//*   str_Fphonenumber=class_farmerandpond_details_array_obj[i].getFarmer_cellno();
                str_FIDprooftype=class_farmerandpond_details_array_obj[i].getFarmer_idprooftype();
                str_FIDproofno=class_farmerandpond_details_array_obj[i].getFarmer_idproofnumber();*//*

                Log.e("Tmname",str_farmerMName);
                Log.e("Tlname",str_farmerLName);
              *//*  Log.e("Tphone",str_Fphonenumber);
                Log.e("Tidtype",str_FIDprooftype);
                Log.e("Tidproofno",str_FIDproofno);*//*





                //class_farmerandpond_details_innerobj.getClass_farmponddetails_obj().size()
                if (class_farmerandpond_details_array_obj.length > 0)
                {
                    Log.e("Farmpond Size", String.valueOf(class_farmerandpond_details_array_obj.length));


*//*
                    for (int j = 0; j < class_farmerandpond_details_array_obj[i].getFarmerID().length(); j++)
                    {*//*
                     //   Log.e("insize images", String.valueOf(class_farmerandpond_details_array_obj[i].get(j).getClass_farmpondimages_obj().size()));


                        str_total_no_days=class_farmerandpond_details_array_obj[i].getPondDays();
                        str_constructed_date=class_farmerandpond_details_array_obj[i].getCreatedDate();
                        str_submitted_date=class_farmerandpond_details_array_obj[i].getSubmittedDate();
                        str_farmpond_cost=class_farmerandpond_details_array_obj[i].getPondCost();

                        //str_farmpond_remarks=String.valueOf(class_farmerandpond_details_array_obj[i].getFarmpond_remarksID());
                       // str_farmpond_amtcollected=String.valueOf(class_farmerandpond_details_array_obj[i].getFarmpond_amtcollected());
                       // str_farmpond_status=String.valueOf(class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_completionstatus());



                    *//*    str_approvalstatus=class_farmerandpond_details_array_obj[i].getFarmpond_Approvalstatus();
                        str_approvalremarks=class_farmerandpond_details_array_obj[i].getFarmpond_Approvalremarks();
                       str_approvedby=class_farmerandpond_details_array_obj[i].get(j).getFarmpond_Approvedby();
                       str_approveddate=class_farmerandpond_details_array_obj[i].getFarmpond_ApprovedDate();
                       str_donorname=class_farmerandpond_details_array_obj[i].getFarmpond_Donorname();*//*


                        str_acres=class_farmerandpond_details_array_obj[i].getFarmpond_acres();
                        str_gunta=class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_gunta();
                        str_crop_beforepond=class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_crop_before_pond();
                        str_crop_afterpond=class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_crop_after_pond();

                        str_latitude=class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_Latitude();
                        str_longitude=class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_Longitude();

                       int x=1;
                        String y= String.valueOf(x);

                       if(class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj()
                                .get(j).getMachine_code().isEmpty())
                        {
                            str_machine_code="NoMachineCode";
                        }
                        else {

                            str_machine_code=class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj()
                                    .get(j).getMachine_code();

                        }

                        if(class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_Code().isEmpty()
                        ||class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_Code()==null)
                        {
                            Log.e("fpcode", "NoPondCode");
                            str_farmpondcode="NoPondCode";
                        }
                        else {

                            Log.e("fpcode", class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_Code());
                            str_farmpondcode=class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_Code();

                        }


                        str_startdate=class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_StartDate().toString();


                      *//*  Log.e("days",class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getPond_constructed_days());
                        Log.e("date",class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getPond_constructed_date());
                        Log.e("submitteddate",class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getSubmitted_date());
                        Log.e("cost",class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getPond_construction_cost());
                        //Log.e("mcode",class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getMachine_code());

                        if(class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_Code().isEmpty())
                        {
                            Log.e("fpcode", "nocode");
                        }
                        else {

                            Log.e("fpcode", class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).getFarmpond_Code());
                        }
*//*

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


                                if(k==0)
                                {

                                    str_imageid1 = class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).
                                            getClass_farmpondimages_obj().get(k).getImage_ID();
                                    str_imageurl = class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).
                                            getClass_farmpondimages_obj().get(k).getImage_url();

                                    String str_farmpondimageurl = str_farmpondbaseimage_url + str_imageurl;
                                    Log.e("url1", str_farmpondimageurl);

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
                                    str_base64image1 = Base64.encodeToString(b, Base64.DEFAULT);

                                }

                                if(k==1)
                                {
                                    str_imageid2 = class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).
                                            getClass_farmpondimages_obj().get(k).getImage_ID();
                                    str_imageurl = class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).
                                            getClass_farmpondimages_obj().get(k).getImage_url();

                                    String str_farmpondimageurl = str_farmpondbaseimage_url + str_imageurl;
                                    Log.e("url2", str_farmpondimageurl);

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
                                    str_base64image2 = Base64.encodeToString(b, Base64.DEFAULT);
                                }

                                if(k==2)
                                {
                                    str_imageid3 = class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).
                                            getClass_farmpondimages_obj().get(k).getImage_ID();
                                    str_imageurl = class_farmerandpond_details_array_obj[i].getClass_farmponddetails_obj().get(j).
                                            getClass_farmpondimages_obj().get(k).getImage_url();

                                    String str_farmpondimageurl = str_farmpondbaseimage_url + str_imageurl;
                                    Log.e("url3", str_farmpondimageurl);

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
                                    str_base64image3 = Base64.encodeToString(b, Base64.DEFAULT);


                                }

                                *//*DBCreate_Farmpondsedetails_2SQLiteDB(str_farmerid, str_farmername,str_farmpond_id,str_width,str_height,
                                        str_depth,str_imageid,str_base64image1 );*//*

                        //    }//for 3


                        }// if 2
                       *//* else
                        {
                            DBCreate_Farmpondsedetails_2SQLiteDB(str_farmerid, str_farmername,str_farmpond_id,str_width,str_height,
                                    str_depth,"0","empty");
                        }*//*









     *//*  DBCreate_Farmpondsedetails_2SQLiteDB(str_farmerid, str_farmername,str_farmpond_id,str_width,str_height,
                                str_depth,str_imageid1,str_base64image1,str_imageid2,str_base64image2,str_imageid3,str_base64image3,
                                str_total_no_days,str_constructed_date,str_submitted_date,str_farmpond_cost,str_machine_code,
                                str_farmpondcode,str_startdate,str_farmpond_remarks,str_farmpond_amtcollected,str_farmpond_status);
*//*




     *//*  DBCreate_Farmpondsedetails_2SQLiteDB(str_farmerid, str_farmername,str_farmpond_id,str_width,str_height,
                                str_depth,str_imageid1,str_base64image1,str_imageid2,str_base64image2,str_imageid3,str_base64image3,
                                str_total_no_days,str_constructed_date,str_submitted_date,str_farmpond_cost,str_machine_code,
                                str_farmpondcode,str_startdate,str_farmpond_remarks,str_farmpond_amtcollected,str_farmpond_status,
                                str_farmerMName,str_farmerLName,str_Fphonenumber,str_FIDprooftype,str_FIDproofno);*//*


                        DBCreate_Farmpondsedetails_2SQLiteDB(str_farmerid, str_farmername,str_farmpond_id,str_width,str_height,
                                str_depth,str_imageid1,str_base64image1,str_imageid2,str_base64image2,str_imageid3,str_base64image3,
                                str_total_no_days,str_constructed_date,str_submitted_date,str_farmpond_cost,str_machine_code,
                                str_farmpondcode,str_startdate,str_farmpond_remarks,str_farmpond_amtcollected,str_farmpond_status,
                                str_farmerMName,str_farmerLName,str_Fphonenumber,str_FIDprooftype,str_FIDproofno,str_approvalstatus,
                                str_approvalremarks,str_approvedby,str_approveddate,str_donorname,str_latitude,str_longitude,
                                str_acres,str_gunta,str_crop_beforepond,str_crop_afterpond);




                        str_base64image1="noimage1";str_base64image2="noimage2";str_base64image3="noimage3";
                        str_imageid1="0";str_imageid2="0";str_imageid3="0";

                    }// for 2

                }//if 1
            }//for 1

        }//try 1
        catch(Exception e)
        {
            Log.e("pondimage_Error", e.getMessage());
            e.printStackTrace();
        }
    }*///end of urltobase64







   /* public void  DBCreate_Farmpondsedetails_2SQLiteDB(String str_farmerid, String str_farmername,String str_farmpond_id,String str_width,
                                                      String str_height,String str_depth,String str_imageid1,String str_base64image1,
                                                      String str_imageid2,String str_base64image2,String str_imageid3,String str_base64image3,
                                                      String str_total_days,String str_constr_date,String str_submited_date,String str_pond_cost,
                                                      String str_mcode,String str_fpondcode,String str_startdate,String str_farmpond_remarks,
                                                      String str_farmpond_amtcollected,String str_farmpond_status,String str_farmerMName,
                                                      String str_farmerLName,String str_Fphonenumber,String str_FIDprooftype,
                                                      String str_FIDproofno,String str_approvalstatus,String str_approvalremarks,
                                                      String str_approvedby,String str_approveddate,String str_donorname,
                                                      String str_latitude,String str_longitude,
                                                      String str_acres,String str_gunta,String str_crop_beforepond,String str_crop_afterpond)

    {






       *//* "constructed_date": "00-00-0000 00:00:00",
            "submitted_date": "00-00-0000 00:00:00",
            "farmpond_constrction_cost": "0",
            "machine_code": "",
            "fp_code": "",*//*









     *//*"st_id": "29",
            "dist_id": "528",
            "tlk_id": "5499",
            "village_id": "604790",
            "panchayat_id": "216281",
            "registered_date_id": "3",
            "Id_proof_type": null,
            "ID_proof": "0",
            "year": "2019",*//*



        if(str_constr_date.equalsIgnoreCase("00-00-0000 00:00:00"))
        {
            str_constr_date="nodate";
        }
        if(str_submited_date.equalsIgnoreCase("00-00-0000 00:00:00"))
        {
            str_submited_date="nodate";
        }


        Log.e("DB_imageid1",str_imageid1);
        Log.e("DB_imageid2",str_imageid2);
        Log.e("DB_imageid3",str_imageid3);


        String str_yearID,str_stateID,str_districtID,str_talukID,str_panchayatID,str_villageID,
               str_farmerage,str_FannualIncome,str_Ffamilymember,str_Fphoto,str_empID,str_tempfid;


        str_yearID=str_stateID=str_districtID=str_talukID=str_panchayatID=str_villageID=
                str_farmerage=str_FannualIncome=str_Ffamilymember=str_Fphoto=str_empID=str_tempfid="empty";





        Log.e("Dbfarmerid",str_farmerid);
       // String str_tempfarmpond_id="temppondidX";

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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR);");




        ////str_approvalstatus,str_approvalremarks,str_approvedby,str_approveddate,str_donorname;

        //FPondRemarksDB,FPondAmtTakenDB,FPondStatusDB,

     //   "Imageid3DB,Image3Base64DB,EmployeeIDDB,SubmittedDateDB,UploadedStatusFarmerprofile,UploadedStatus)" +

       // str_submited_date

        Log.e("remarks",str_farmpond_remarks);
        Log.e("amt",str_farmpond_amtcollected);
        Log.e("status",str_farmpond_status);




// "FPondApprovalStatusDB VARCHAR,FPondApprovalRemarksDB VARCHAR,FPondApprovedbyDB VARCHAR,FPondApprovedDateDB VARCHAR,FPondDonorDB VARCHAR," +


   //FPondApprovalStatusDB,FPondApprovalRemarksDB,FPondApprovedbyDB,FPondApprovedDateDB,FPondDonorDB,

     //String str_approvalstatus,String str_approvalremarks,
        //                                                      String str_approvedby,String str_approveddate,String str_donorname

        //"FPondLatitudeDB VARCHAR,FPondLongitudeDB VARCHAR," +

        // "FPondAcresDB VARCHAR,FPondGuntaDB VARCHAR,FPondCropBeforeDB VARCHAR,FPondCropAfterDB VARCHAR," +

        String SQLiteQuery = "INSERT INTO FarmPondDetails_fromServerRest(FIDDB,TempFIDDB,FNameDB,FMNameDB,FLNameDB,FYearIDDB,FStateIDDB,FDistrictIDDB," +
                "FTalukIDDB,FPanchayatIDDB,FVillageIDDB,FageDB,FphonenumberDB,FfamilymemberDB,FAnnualIncomeDB,FidprooftypeDB,FidproofnoDB,FphotoDB,FPondidDB," +
                "WidthDB,HeightDB,DepthDB,LatitudeDB,LongitudeDB,Imageid1DB,Image1Base64DB,Imageid2DB,Image2Base64DB," +
                "Imageid3DB,Image3Base64DB,EmployeeIDDB,SubmittedDateDB,TotalDaysDB,StartDateDB,ConstructedDateDB,PondCostDB,McodeDB," +
                "FPondCodeDB,FPondRemarksDB,FPondAmtTakenDB,FPondStatusDB," +
                "FPondApprovalStatusDB,FPondApprovalRemarksDB,FPondApprovedbyDB,FPondApprovedDateDB,FPondDonorDB," +
                "FPondLatitudeDB,FPondLongitudeDB," +
                "FPondAcresDB,FPondGuntaDB,FPondCropBeforeDB,FPondCropAfterDB," +
                "UploadedStatusFarmerprofile,UploadedStatus)" +
                " VALUES ('"+str_farmerid+"','"+str_tempfid+"','"+str_farmername+"','"+str_farmerMName+"','"+str_farmerLName+"','"+str_yearID+"'," +
                "'"+str_stateID+"','"+str_districtID+"','"+str_talukID+"','"+str_panchayatID+"','"+str_villageID+"','"+str_farmerage+"'," +
                "'"+str_Fphonenumber+"','"+str_FannualIncome+"','"+str_Ffamilymember+"','"+str_FIDprooftype+"','"+str_FIDproofno+"','"+str_Fphoto+"'," +
                "'"+str_farmpond_id+"','"+str_width+"'," +
                "'"+str_height+"','"+str_depth+"','"+0+"','"+0+"','"+str_imageid1+"','"+str_base64image1+"'," +
                "'"+str_imageid2+"','"+str_base64image2+"','"+str_imageid3+"','"+str_base64image3+"','"+str_empID+"','"+str_submited_date+"'," +
                "'"+str_total_days+"','"+str_startdate+"','"+str_constr_date+"','"+str_pond_cost+"','"+str_mcode+"','"+str_fpondcode+"'," +
                "'"+str_farmpond_remarks+"','"+str_farmpond_amtcollected+"','"+str_farmpond_status+"'," +
                "'"+str_approvalstatus+"','"+str_approvalremarks+"','"+str_approvedby+"','"+str_approveddate+"','"+str_donorname+"'," +
                "'"+str_latitude+"','"+str_longitude+"','"+str_acres+"','"+str_gunta+"','"+str_crop_beforepond+"','"+str_crop_afterpond+"','"+0+"','"+0+"');";


Log.e("tag","pond FIDDB="+str_farmerid);

        // String str_acres,String str_gunta,String str_crop_beforepond,String str_crop_afterpond)

        db1.execSQL(SQLiteQuery);
        db1.close();






       *//* String SQLiteQuery = "INSERT INTO FarmPondDetails_fromServer(FIDDB,TempFIDDB,FNameDB,FMNameDB,FLNameDB,FYearIDDB,FStateIDDB,FDistrictIDDB," +
                "FTalukIDDB,FPanchayatIDDB,FVillageIDDB,FageDB,FphonenumberDB,FfamilymemberDB,FAnnualIncomeDB,FidprooftypeDB,FidproofnoDB,FphotoDB,FPondidDB," +
                "WidthDB,HeightDB,DepthDB,LatitudeDB,LongitudeDB,Imageid1DB,Image1Base64DB,Imageid2DB,Image2Base64DB," +
                "Imageid3DB,Image3Base64DB,EmployeeIDDB,SubmittedDateDB,TotalDaysDB,StartDateDB,ConstructedDateDB,PondCostDB,McodeDB,FPondCodeDB,FPondRemarksDB,FPondAmtTakenDB,FPondStatusDB,UploadedStatusFarmerprofile,UploadedStatus)" +
                " VALUES ('"+str_farmerid+"','"+str_tempfid+"','"+str_farmername+"','"+str_farmerMName+"','"+str_farmerLName+"','"+str_yearID+"'," +
                "'"+str_stateID+"','"+str_districtID+"','"+str_talukID+"','"+str_panchayatID+"','"+str_villageID+"','"+str_farmerage+"'," +
                "'"+str_Fphonenumber+"','"+str_FannualIncome+"','"+str_Ffamilymember+"','"+str_FIDprooftype+"','"+str_FIDproofno+"','"+str_Fphoto+"'," +
                "'"+str_farmpond_id+"','"+str_width+"'," +
                "'"+str_height+"','"+str_depth+"','"+0+"','"+0+"','"+str_imageid1+"','"+str_base64image1+"'," +
                "'"+str_imageid2+"','"+str_base64image2+"','"+str_imageid3+"','"+str_base64image3+"','"+str_empID+"','"+str_submited_date+"'," +
                "'"+str_total_days+"','"+str_startdate+"','"+str_constr_date+"','"+str_pond_cost+"','"+str_mcode+"','"+str_fpondcode+"'," +
                "'"+str_farmpond_remarks+"','"+str_farmpond_amtcollected+"','"+str_farmpond_status+"','"+0+"','"+0+"');";*//*


// String str_total_days,String str_constr_date,String str_submited_date,String str_pond_cost,
//                                                      String str_mcode,String str_fpondcode)

        *//*String SQLiteQuery = "INSERT INTO FarmPondDetails_fromServer (FIDDB,FNameDB,FPondidDB,WidthDB,HeightDB,DepthDB,LatitudeDB,LongitudeDB,Imageid1DB,Image1Base64DB,Imageid2DB,Image2Base64DB,Imageid3DB,Image3Base64DB,EmployeeIDDB,UploadedStatus)" +
                " VALUES ('"+str_farmerid+"','"+str_farmername+"','"+str_farmpond_id+"','"+str_width+"'," +
                "'"+str_height+"','"+str_depth+"','"+0+"','"+0+"','"+str_imageid1+"','"+str_base64image1+"'," +
                "'"+str_imageid2+"','"+str_base64image2+"','"+str_imageid3+"','"+str_base64image3+"','"+str_farmerID+"','"+0+"');";
        db1.execSQL(SQLiteQuery);
        db1.close();*//*
    }*/





    /*public void DBCount()
    {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);


        db1.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR," +
                "FNameDB VARCHAR,FMNameDB VARCHAR,FLNameDB VARCHAR,FYearIDDB VARCHAR,FStateIDDB VARCHAR,FDistrictIDDB VARCHAR," +
                "FTalukIDDB VARCHAR,FPanchayatIDDB VARCHAR,FVillageIDDB VARCHAR,FageDB VARCHAR,FphonenumberDB VARCHAR," +
                "FfamilymemberDB VARCHAR,FidprooftypeDB VARCHAR,FidproofnoDB VARCHAR,FphotoDB VARCHAR,FPondidDB VARCHAR,WidthDB VARCHAR," +
                "HeightDB VARCHAR,DepthDB VARCHAR,LatitudeDB VARCHAR,LongitudeDB VARCHAR,Imageid1DB VARCHAR,Image1Base64DB VARCHAR," +
                "Imageid2DB VARCHAR,Image2Base64DB VARCHAR,Imageid3DB VARCHAR,Image3Base64DB VARCHAR,EmployeeIDDB VARCHAR," +
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR);");



        Cursor cursor = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest", null);
        int x = cursor.getCount();
        Log.e("farmpond_count", String.valueOf(x));

        db1.close();

    }*/

    public void deleteMachineTable_B4insertion() {


        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineCodeDDB VARCHAR,MachineIDDB VARCHAR);");


        Cursor cursor = db1.rawQuery("SELECT * FROM MachineDetails_fromServerRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db1.delete("MachineDetails_fromServerRest", null, null);

        }
        db1.close();
    }


    public void deleteRemarksTable_B4insertion() {


        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS RemarksDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,RemarksIDDB VARCHAR,RemarksNameDB VARCHAR);");
        Cursor cursor = db1.rawQuery("SELECT * FROM RemarksDetails_fromServerRest", null);
        int x = cursor.getCount();
        if (x > 0) {
            db1.delete("RemarksDetails_fromServerRest", null, null);

        }
        db1.close();
    }


    public void DBCreate_Farmpondsedetails_2SQLiteDB3(String str_width_4rdb, String str_height_4rdb, String str_depth_4rdb) {

       /* SQLiteDatabase db1 = this.openOrCreateDatabase("PondDetails_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServer(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FID VARCHAR,WidthDB VARCHAR," +
                "HeightDB VARCHAR,DepthDB VARCHAR,LatDB VARCHAR,LongDB VARCHAR,FarmPondImage1 BLOB,UploadedStatus VARCHAR);");

        String SQLiteQuery = "INSERT INTO FarmPondDetails_fromServer (FID,WidthDB,HeightDB,DepthDB,LatDB,LongDB,FarmPondImage1,UploadedStatus)" +
                " VALUES ('"+str_farmer_id+"','"+str_width_4rdb+"','"+str_height_4rdb+"','"+str_depth_4rdb+"'," +
                "'"+str_latitude+"','"+str_longitude+"','"+arraylist_image1_base64.get(0)+"','"+1+"');";
        db1.execSQL(SQLiteQuery);
        db1.close();*/
    }






   /* private void AsyncTask_fetch_MachineDetails()
    {

        // final ProgressDialog pdLoading_fetch_farmponddetails = new ProgressDialog(Activity_ViewFarmers.this);

        pdLoading_fetch_machinedetails = new ProgressDialog(Activity_ViewFarmers.this);
        pdLoading_fetch_machinedetails.setMessage("\tLoading...");
        pdLoading_fetch_machinedetails.setCancelable(false);
        pdLoading_fetch_machinedetails.show();



        String str_fetchfarmponddetails_url = Class_URL.URL_fetch_machinedetails.toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {


                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.e("volley_machineresponse",response);

                        parse_each_machineresponse(response);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading_fetch_machinedetails.dismiss();
                        Log.e("volley_machineresponse",error.toString());

                        Toast.makeText(Activity_ViewFarmers.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                } )
        {
            *//*@Override
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

            }*//*
        };




        stringRequest.setRetryPolicy(new DefaultRetryPolicy(9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/


   /* public void parse_each_machineresponse(String response)
    {

        try {

          //  Log.e("machine_length", String.valueOf(response.length()));


            JSONArray jsonArray = new JSONArray(response);

            Log.e("machine_length", String.valueOf(jsonArray.length()));

            int_jsonarraymachinelength=jsonArray.length();


            class_machinedetails_array_obj = new Class_MachineDetails[int_jsonarraymachinelength];

            for(int i=0;i<int_jsonarraymachinelength;i++)
            {

                JSONObject jresponse = jsonArray.getJSONObject(i);

               *//* Class_MachineDetails class_machinedetails_innerobj =
                        new Gson().fromJson(String.valueOf(jsonArray.getJSONObject(i).toString()), Class_MachineDetails.class);*//*

                Class_MachineDetails class_machinedetails_innerobj =
                        new Gson().fromJson((jsonArray.getJSONObject(i).toString()), Class_MachineDetails.class);

                class_machinedetails_array_obj[i]=class_machinedetails_innerobj;

               *//* Log.e("machineclass",class_machinedetails_innerobj.getMachine_Code().toString()); //machine_code
                Log.e("machine_name",jresponse.getString("name")); //machine_code
                Log.e("machine_code",jresponse.getString("machine_code"));*//*
            }

            DBCreate_MachineDetails();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error_machine",e.toString());
        }



        pdLoading_fetch_machinedetails.dismiss();



    }*///parse_machine_response







   /* public void  DBCreate_MachineDetails()
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("MachineDetails_DB", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServer(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineCodeDDB VARCHAR,MachineIDDB VARCHAR);");

        for(int i=0;i<int_jsonarraymachinelength;i++)
        {
            String str_machinename=class_machinedetails_array_obj[i].getMachine_name().toString().trim();
            String str_machinecode=class_machinedetails_array_obj[i].getMachine_Code().toString().trim();
            String str_machineid=class_machinedetails_array_obj[i].getMachine_id().toString().trim();
            String SQLiteQuery = "INSERT INTO MachineDetails_fromServer (MachineNameDB,MachineCodeDDB,MachineIDDB)" +
                    " VALUES ('"+str_machinename+"','"+str_machinecode+"','"+str_machineid+"');";
            db1.execSQL(SQLiteQuery);
        }
        db1.close();

    }*/








    /*private void AsyncTask_fetch_construction_remarks()
    {

        // final ProgressDialog pdLoading_fetch_farmponddetails = new ProgressDialog(Activity_ViewFarmers.this);

        pdLoading_fetch_remarksdetails = new ProgressDialog(Activity_ViewFarmers.this);
        pdLoading_fetch_remarksdetails.setMessage("\tLoading...");
        pdLoading_fetch_remarksdetails.setCancelable(false);
        pdLoading_fetch_remarksdetails.show();



        String str_fetchfarmponddetails_url = Class_URL.URL_fetch_remarksdetails.toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {


                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        //Log.e("volley_remarks",response);

                        parse_each_construction_remarks(response);


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading_fetch_remarksdetails.dismiss();
                        Log.e("volley_remarks",error.toString());

                        Toast.makeText(Activity_ViewFarmers.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                } )
        {
            *//*@Override
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

            }*//*
        };




        stringRequest.setRetryPolicy(new DefaultRetryPolicy(9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void parse_each_construction_remarks(String response)
    {

        try {

            //  Log.e("machine_length", String.valueOf(response.length()));


            JSONArray jsonArray = new JSONArray(response);

            Log.e("remarks_length", String.valueOf(jsonArray.length()));

            int_jsonarrayremarkslength=jsonArray.length();


            class_remarksdetails_array_obj = new Class_RemarksDetails[int_jsonarrayremarkslength];

            for(int i=0;i<int_jsonarrayremarkslength;i++)
            {

                JSONObject jresponse = jsonArray.getJSONObject(i);
               // Log.e("jresponse",jresponse.toString());

               *//* Class_RemarksDetails class_remarksdetails_innerobj =
                        new Gson().fromJson(String.valueOf(jsonArray.getJSONObject(i).toString()), Class_RemarksDetails.class);
*//*
                Class_RemarksDetails class_remarksdetails_innerobj =
                        new Gson().fromJson((jsonArray.getJSONObject(i).toString()), Class_RemarksDetails.class);

                class_remarksdetails_array_obj[i]=class_remarksdetails_innerobj;

              //  Log.e("remarksname",class_remarksdetails_innerobj.getRemarks_Name()); //machine_code
                Log.e("remarks_id",jresponse.getString("Option_ID"));
                Log.e("remarks_name",jresponse.getString("Option_Value"));
            }

            DBCreate_RemarksDetails();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error_remarks",e.toString());
        }



        pdLoading_fetch_remarksdetails.dismiss();



    }//parse_machine_response
*/



 /*   public void  DBCreate_RemarksDetails()
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("RemarksDetails_DB", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS RemarksDetails_fromServer(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,RemarksIDDB VARCHAR,RemarksNameDB VARCHAR);");

        for(int i=0;i<int_jsonarrayremarkslength;i++)
        {
            String str_remarksid=class_remarksdetails_array_obj[i].getRemarks_ID().trim();
            String str_remarksname=class_remarksdetails_array_obj[i].getRemarks_Name().trim();
            String SQLiteQuery = "INSERT INTO RemarksDetails_fromServer (RemarksIDDB,RemarksNameDB)" +
                    " VALUES ('"+str_remarksid+"','"+str_remarksname+"');";
            db1.execSQL(SQLiteQuery);
        }
        db1.close();

    }*/


    private class AsyncCallWS_imageurltobase64_farmerimage extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        Context context;

        String str_base64;

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
        protected String doInBackground(String... params) {
            Log.i("DFFarmPond", "doInBackground");


            String x = params[0];
            Log.e("x", x);

            str_base64 = urltobase64_farmerimage(x);

            str_imageurltobase64_farmerimage = str_base64;
            //Log.e("str_farmer", str_base64);

            //  strArray_farmerBase64[k]=str_base64;
            return str_base64;


            //return null;
        }

        public AsyncCallWS_imageurltobase64_farmerimage(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();


            //  return str_base64;
//          DBCount();


        }//end of OnPostExecute

    }// end Async task


    public String urltobase64_farmerimage(String str_imageurl) {

        String str_farmpondbaseimage_url, str_base64image = null;
        try {


            Class_farmerimageBase64 class_farmerimageBase64_innerObj = new Class_farmerimageBase64();
            str_farmpondbaseimage_url = Class_URL.URL_farmpondbaselink.toString().trim();


            String str_farmpondimageurl = str_farmpondbaseimage_url + str_imageurl;


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
            str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

            str_imageurltobase64_farmerimage = str_base64image;
            class_farmerimageBase64_innerObj.setStr_farmerimagebase64(str_base64image);


            // class_farmerimageBase64_array[k]=class_farmerimageBase64_innerObj;

            // Log.e("insideimage",class_farmerimageBase64_array[0].getStr_farmerimagebase64().toString());


        }//try 1
        catch (Exception e) {
            Log.e("Farmerimage_Error", e.getMessage());
            e.printStackTrace();
        }
        return str_base64image;

    }//end of urltobase64










    /*private class AsyncCallWS_imageurltobase64_farmerimage2 extends AsyncTask<String, Void, Void> {
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


            urltobase64_farmerimage2();


            return null;
        }

        public AsyncCallWS_imageurltobase64_farmerimage2(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();


            //uploadfromDB_Farmerlist();


//          DBCount();






        }//end of OnPostExecute

    }// end Async task
*/





   /* public void urltobase64_farmerimage2()
    {

        try
        {


            String str_length= String.valueOf(class_farmerlistdetails_arrayobj2.length);
            Log.e("farmerprofilelength",str_length);
            String str_farmpondbaseimage_url,str_base64image = null;

            String str_imageurl = null;

            String str_yearid,str_stateid,str_distid,str_talukid,str_villageid,str_panchayataid,str_farmerid,
                    str_farmercode,str_farmername,str_farmerimageurl,str_farmpondcount,str_farmerbase64;




            String str_fMname,str_fLname,str_fage,str_fcellno,str_fincome,str_fmember,str_idtype,str_idproofno;

            for (int i = 0; i < class_farmerlistdetails_arrayobj2.length; i++)
            {

                str_farmpondbaseimage_url = Class_URL.URL_farmpondbaselink.toString().trim();

                       str_yearid=class_farmerlistdetails_arrayobj2[i].getYearid();
                       str_stateid=class_farmerlistdetails_arrayobj2[i].getStateid();
                       str_distid=class_farmerlistdetails_arrayobj2[i].getDistrictid();
                       str_talukid=class_farmerlistdetails_arrayobj2[i].getTalukid();
                       str_villageid=class_farmerlistdetails_arrayobj2[i].getVillageid();
                       str_panchayataid=class_farmerlistdetails_arrayobj2[i].getGrampanchayatid();
                      str_farmerid=class_farmerlistdetails_arrayobj2[i].getFarmerid();
                       str_farmercode=class_farmerlistdetails_arrayobj2[i].getFarmerid();
                       str_farmername=class_farmerlistdetails_arrayobj2[i].getFarmername();
                        str_farmerimageurl=class_farmerlistdetails_arrayobj2[i].getFarmerimage();
                        str_farmpondcount=class_farmerlistdetails_arrayobj2[i].getFarmpondcount();
                       str_fMname=class_farmerlistdetails_arrayobj2[i].getStr_farmerMname();
                       str_fLname=class_farmerlistdetails_arrayobj2[i].getStr_farmerLname();
                       str_fage=class_farmerlistdetails_arrayobj2[i].getStr_farmeage();
                       str_fcellno=class_farmerlistdetails_arrayobj2[i].getStr_farmercellno();
                       str_fincome=class_farmerlistdetails_arrayobj2[i].getStr_farmerannualincome();
                       str_fmember=class_farmerlistdetails_arrayobj2[i].getStr_farmerfamilymember();
                       str_idtype=class_farmerlistdetails_arrayobj2[i].getStr_farmeridprooftype();
                       str_idproofno=class_farmerlistdetails_arrayobj2[i].getStr_farmeridproofnumber();

                       //str_farmerbase64=class_farmerlistdetails_arrayobj2[i].









               str_imageurl=class_farmerlistdetails_arrayobj2[i].getFarmerimage();

                String str_farmpondimageurl = str_farmpondbaseimage_url + str_imageurl;

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
                str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

                str_imageurltobase64_farmerimage=str_base64image;

                str_farmerbase64=str_base64image;



                DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(str_yearid, str_stateid,
                        str_distid, str_talukid, str_villageid,
                        str_panchayataid, str_farmerid, str_farmercode,
                        str_farmername, str_farmerimageurl,str_farmpondcount,
                        str_farmerbase64, str_fMname,str_fLname,str_fage,str_fcellno,str_fincome,
                        str_fmember,str_idtype,str_idproofno);

            }//for 1

          //  uploadfromDB_Farmerlist();


        }//try 1
        catch(Exception e)
        {
            Log.e("farmerimage_Error2", e.getMessage());
            e.printStackTrace();
        }


    }
*/


    public void DBCreate_RemarksDetails() {

        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS RemarksDetails_fromServer(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,RemarksIDDB VARCHAR,RemarksNameDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM RemarksDetails_fromServer", null);
        int x = cursor.getCount();
        if (x > 0) {
            db2.delete("RemarksDetails_fromServer", null, null);
        }
        db2.close();


        try {

            //  Log.e("machine_length", String.valueOf(response.length()));

            String response = "[\n" +
                    "    {\n" +
                    "        \"Option_ID\": \"4\",\n" +
                    "        \"Option_Value\": \"NoRemarks\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"Option_ID\": \"3\",\n" +
                    "        \"Option_Value\": \"Day/Night Work\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"Option_ID\": \"2\",\n" +
                    "        \"Option_Value\": \"Operator Not Available\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"Option_ID\": \"1\",\n" +
                    "        \"Option_Value\": \"Machine Failure\"\n" +
                    "    }\n" +
                    "]";
            JSONArray jsonArray = new JSONArray(response);

            Log.e("remarks_length", String.valueOf(jsonArray.length()));

            int_jsonarrayremarkslength = jsonArray.length();


            class_remarksdetails_array_obj = new Class_RemarksDetails[int_jsonarrayremarkslength];

            for (int i = 0; i < int_jsonarrayremarkslength; i++) {

                JSONObject jresponse = jsonArray.getJSONObject(i);
                // Log.e("jresponse",jresponse.toString());

               /* Class_RemarksDetails class_remarksdetails_innerobj =
                        new Gson().fromJson(String.valueOf(jsonArray.getJSONObject(i).toString()), Class_RemarksDetails.class);
*/
                Class_RemarksDetails class_remarksdetails_innerobj =
                        new Gson().fromJson((jsonArray.getJSONObject(i).toString()), Class_RemarksDetails.class);

                class_remarksdetails_array_obj[i] = class_remarksdetails_innerobj;

                //  Log.e("remarksname",class_remarksdetails_innerobj.getRemarks_Name()); //machine_code
                Log.e("remarks_id", jresponse.getString("Option_ID"));
                Log.e("remarks_name", jresponse.getString("Option_Value"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error_remarks", e.toString());
        }

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS RemarksDetails_fromServer(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,RemarksIDDB VARCHAR,RemarksNameDB VARCHAR);");

        for (int i = 0; i < int_jsonarrayremarkslength; i++) {

            if (i == 0) {
                String SQLiteQuery = "INSERT INTO RemarksDetails_fromServer (RemarksIDDB,RemarksNameDB)" +
                        " VALUES ('" + "100" + "','" + "Select" + "');";
                db1.execSQL(SQLiteQuery);
            }


            String str_remarksid = class_remarksdetails_array_obj[i].getRemarks_ID().trim();
            String str_remarksname = class_remarksdetails_array_obj[i].getRemarks_Name().trim();
            String SQLiteQuery = "INSERT INTO RemarksDetails_fromServer (RemarksIDDB,RemarksNameDB)" +
                    " VALUES ('" + str_remarksid + "','" + str_remarksname + "');";
            db1.execSQL(SQLiteQuery);
        }
        db1.close();

    }


    public String Async_Alertdialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_ViewFarmers.this);
        dialog.setCancelable(false);
        dialog.setTitle("DF Agri");
        dialog.setMessage("Are you sure want to Sync the data");


        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                    /*Intent i = new Intent(EditProfileActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();*/
                str_return = "yes";

            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                        str_return = "no";
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

        //alert.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
        if (str_return.equalsIgnoreCase("yes")) {
            return "yes";
        } else {
            return "no";
        }


    }


}// end of  class

