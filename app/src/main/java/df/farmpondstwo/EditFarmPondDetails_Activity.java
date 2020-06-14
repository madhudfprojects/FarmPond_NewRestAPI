package df.farmpondstwo;

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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import df.farmpondstwo.Models.Class_MachineDetails;


public class EditFarmPondDetails_Activity extends AppCompatActivity {


    Toolbar toolbar;
    ImageView add_newfarmpond_iv;

    EditText edit_ponddepth_et,edit_pondwidth_et,edit_pondheight_et,edit_landacres_et,edit_landgunta_et;
    TextView edit_ponddetails_farmername_et,submittedby_tv;
    ImageView edit_pond_image1_iv,edit_pond_image2_iv,edit_pond_image3_iv;
    ImageButton edit_removeimage1_ib,edit_removeimage2_ib,edit_removeimage3_ib;

    Button edit_ponddetails_submit_bt,edit_ponddetails_cancel_bt;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String str_base64imagestring = "";
    ArrayList<String> arraylist_image1_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image2_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image3_base64 = new ArrayList<>();

    ArrayList<String> arraylist_image1_ID_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image2_ID_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image3_ID_base64 = new ArrayList<>();

    String str_image1,str_image2,str_image3;
    String str_Is_image1,str_Is_image2,str_Is_image3;
    Bitmap mIcon11;

    // Class_farmponddetails class_farmponddetails_obj;
    String str_farmpondbaseimage_url,str_cancelclicked;
    // Class_farmponddetails_offline class_farmponddetails_offline_obj;

    SharedPreferences sharedpref_farmerid_Obj;
    String str_farmerID;
    public static final String sharedpreferenc_farmerid = "sharedpreference_farmer_id";
    public static final String Key_FarmerID = "farmer_id";
    String str_farmpondimageurl,str_farmpond_id,str_farmername,str_farmer_id,str_approvedstatus;

    ArrayList<Bitmap> arrayList_bitmap;

    TextView image_id1_tv,image_id2_tv,image_id3_tv;

    LinearLayout cancel_submit_ll;



    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";
    public static final String KeyValue_perdayamount = "KeyValue_perdayamount";

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;

    String str_employee_id,str_employee_mailid;

    String str_submitteddatetime, str_validation_for_completed;

    TextView edit_pondamount_tv,edit_pond_total_amount_tv;
    static TextView edit_pond_completeddate_tv,edit_pond_startddate_tv;
    EditText edit_pond_no_of_days_et;
    CheckBox edit_farmpond_completed_cb;
    Spinner selectmachineno_sp,edit_selectremarks_sp;
    String str_perdayamount;

    EditText edit_amountcollected_et;
    Class_MachineDetails[] class_machineDetails_array_obj;
    Class_MachineDetails class_machineDetails_obj;
    String str_machinecode;

    LinearLayout startdate_LL,completeddate_LL,nodays_LL,machineno_LL,farmpondcompleted_LL,notcompleted_text_LL,remarks_LL,amountcollected_LL;
    LinearLayout amount_LL;
    String str_image1present,str_image2present,str_image3present;

    Class_GPSTracker gpstracker_obj1,gpstracker_obj2,gpstracker_obj3;
    Double double_currentlatitude=0.0;
    Double double_currentlongitude=0.0;
    String str_latitude,str_longitude;
    TextView latitude_tv,longitude_tv;

    Date startDate,endDate;

    // Class_RemarksDetails[] class_remarksdetails_array_obj;
    //Class_RemarksDetails class_remarksdetails_obj;
    String str_remarksid;


    //Class_FarmerProfileOffline[] class_farmerprofileoffline_array_obj;
    //Class_FarmerProfileOffline class_farmerprofileoffline_obj;

    //Class_farmponddetails_offline[] class_farmponddetails_offline_array_obj,newfarmponddetails_offline_array_obj;


    LinearLayout farmpondlocationlabel_LL;
    LinearLayout dblatitude_LL,dblongitude_LL;
    TextView dblatitude_tv,dblongitude_tv;

    LinearLayout currentlatitude_LL,currentlongitude_LL;
    String str_currentlatitude,str_currentlongitude;

    LinearLayout fpondcompleted_ll;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    String str_location;

    LinearLayout amountcollected_lesser_LL;

    private boolean isGPS = false;
    ProgressDialog dialog_location;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfarmponddetails);




        toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
        // Set upon the actionbar
        setSupportActionBar(toolbar);
        // Now use actionbar methods to show navigation icon and title
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        str_farmpondbaseimage_url = Class_URL.URL_farmpondbaselink.toString().trim();
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        add_newfarmpond_iv=(ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
        title.setText("Edit FarmPond Details");
        getSupportActionBar().setTitle("");
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorwhite), PorterDuff.Mode.SRC_ATOP);
        add_newfarmpond_iv.setVisibility(View.GONE);


        sharedpref_farmerid_Obj=getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_farmerID = sharedpref_farmerid_Obj.getString(Key_FarmerID, "").trim();


        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_employee_id=sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();


        str_employee_mailid=sharedpreferencebook_usercredential_Obj.getString(KeyValue_employee_mailid, "").trim();

        str_perdayamount=sharedpreferencebook_usercredential_Obj.getString(KeyValue_perdayamount, "").trim();


        //fetch the details previous activity
       /* Intent intent = getIntent();
        String jsonString = intent.getStringExtra("jsonObject");
      //  Log.e("intent",jsonString);
       // class_farmponddetails_obj = new Gson().fromJson(jsonString, Class_farmponddetails.class);

        class_farmponddetails_offline_obj= new Gson().fromJson(jsonString, Class_farmponddetails_offline.class);
*/


        /*String str_farmername =class_farmponddetails_obj.getFarmer_Name().toString() ;
        str_farmpond_id=class_farmponddetails_obj.getFarmpond_Id().toString();*/
//fetch the details previous activity

        //fetch the details previous activity

        str_farmer_id="";
        Intent intent = getIntent();
        str_farmername = intent.getStringExtra("farmer_name");
        str_farmpond_id = intent.getStringExtra("farmpond_id");//tempfarmpond1573025107214
        str_farmer_id = intent.getStringExtra("farmer_id");
        str_approvedstatus=intent.getStringExtra("ApprovedStatus");//i.putExtra("ApprovedStatus", str_approved);

        Log.e("pondid",str_farmpond_id);
        Log.e("farmerid",str_farmer_id);



        //fetch the details previous activity






        /*String str_farmername =class_farmponddetails_offline_obj.getFarmer_Name();
        str_farmpond_id=class_farmponddetails_offline_obj.getFarmpond_Id();*/



        edit_ponddetails_farmername_et=(TextView)findViewById(R.id.edit_ponddetails_farmername_et);
        edit_pondwidth_et=(EditText)findViewById(R.id.edit_pondwidth_et);
        edit_pondheight_et=(EditText)findViewById(R.id.edit_pondheight_et);
        edit_ponddepth_et=(EditText)findViewById(R.id.edit_ponddepth_et);
        submittedby_tv=(TextView)findViewById(R.id.submittedby_tv);

        edit_landacres_et=(EditText)findViewById(R.id.edit_landacres_et);
        edit_landgunta_et=(EditText)findViewById(R.id.edit_landgunta_et);

        image_id1_tv=(TextView)findViewById(R.id.image_id1_tv);
        image_id2_tv=(TextView)findViewById(R.id.image_id2_tv);
        image_id3_tv=(TextView)findViewById(R.id.image_id3_tv);



        edit_pond_image1_iv=(ImageView)findViewById(R.id.edit_pond_image1_iv);
        edit_pond_image2_iv=(ImageView)findViewById(R.id.edit_pond_image2_iv);
        edit_pond_image3_iv=(ImageView)findViewById(R.id.edit_pond_image3_iv);


        longitude_tv=(TextView)findViewById(R.id.longitude_tv);
        latitude_tv=(TextView)findViewById(R.id.latitude_tv);



        edit_removeimage1_ib=(ImageButton) findViewById(R.id.edit_removeimage1_ib);
        edit_removeimage2_ib=(ImageButton) findViewById(R.id.edit_removeimage2_ib);
        edit_removeimage3_ib=(ImageButton) findViewById(R.id.edit_removeimage3_ib);

        edit_ponddetails_submit_bt=(Button)findViewById(R.id.edit_ponddetails_submit_bt);
        edit_ponddetails_cancel_bt=(Button)findViewById(R.id.edit_ponddetails_cancel_bt);

        cancel_submit_ll=(LinearLayout)findViewById(R.id.cancel_submit_ll);

        edit_pond_startddate_tv=(TextView)findViewById(R.id.edit_pond_startddate_tv);
        edit_pond_completeddate_tv=(TextView)findViewById(R.id.edit_pond_completeddate_tv);

        // edit_pondamount_tv=(TextView)findViewById(R.id.edit_pondamount_tv);
        edit_pond_total_amount_tv=(TextView)findViewById(R.id.edit_pond_total_amount_tv);
        edit_pond_no_of_days_et=(EditText) findViewById(R.id.edit_pond_no_of_days_et);
        edit_farmpond_completed_cb=(CheckBox)findViewById(R.id.edit_farmpond_completed_cb);
        selectmachineno_sp=(Spinner)findViewById(R.id.selectmachineno_sp);
        edit_selectremarks_sp=(Spinner)findViewById(R.id.edit_selectremarks_sp);
        edit_amountcollected_et=findViewById(R.id.edit_amountcollected_et);

        amount_LL=(LinearLayout)findViewById(R.id.amount_LL);
        startdate_LL=(LinearLayout)findViewById(R.id.startdate_LL);
        completeddate_LL=(LinearLayout)findViewById(R.id.completeddate_LL);
        nodays_LL=(LinearLayout)findViewById(R.id.nodays_LL);
        machineno_LL=(LinearLayout)findViewById(R.id.machineno_LL);
        farmpondcompleted_LL=(LinearLayout)findViewById(R.id.farmpondcompleted_LL);
        notcompleted_text_LL=(LinearLayout)findViewById(R.id.notcompleted_text_LL);
        remarks_LL=findViewById(R.id.remarks_LL);
        amountcollected_LL=(LinearLayout)findViewById(R.id.amountcollected_LL);


        farmpondlocationlabel_LL=(LinearLayout)findViewById(R.id.farmpondlocationlabel_LL);
        dblatitude_LL=(LinearLayout)findViewById(R.id.dblatitude_LL);
        dblongitude_LL=(LinearLayout)findViewById(R.id.dblongitude_LL);
        dblatitude_tv=(TextView)findViewById(R.id.dblatitude_tv);
        dblongitude_tv=(TextView)findViewById(R.id.dblongitude_tv);

        currentlatitude_LL=(LinearLayout)findViewById(R.id.currentlatitude_LL);
        currentlongitude_LL=(LinearLayout)findViewById(R.id.currentlongitude_LL);
        fpondcompleted_ll=(LinearLayout)findViewById(R.id.fpondcompleted_ll);

        amountcollected_lesser_LL=(LinearLayout)findViewById(R.id.amountcollected_lesser_LL);


        farmpondlocationlabel_LL.setVisibility(View.GONE);
        dblatitude_LL.setVisibility(View.GONE);
        dblongitude_LL.setVisibility(View.GONE);
        currentlatitude_LL.setVisibility(View.GONE);
        currentlongitude_LL.setVisibility(View.GONE);
        amountcollected_lesser_LL.setVisibility(View.GONE);

        //edit_pondamount_tv.setText("X "+str_perdayamount+" =");



        str_image1=str_image2=str_image3="false";
        str_Is_image1=str_Is_image2=str_Is_image3="false";//for conversion to byte64 string
        str_cancelclicked="false";
        edit_ponddetails_farmername_et.setText(str_farmername);
        submittedby_tv.setText(str_employee_mailid);
        str_location="no";




        if(str_approvedstatus.isEmpty()||str_approvedstatus.equalsIgnoreCase("no")||
                str_approvedstatus.equalsIgnoreCase("0")||
                str_approvedstatus.equalsIgnoreCase("Pending")||
                str_approvedstatus.equalsIgnoreCase("Rejected")||
                str_approvedstatus.equalsIgnoreCase("Deleted")
        ){

        }else{
            if(str_approvedstatus.equalsIgnoreCase("Approved"))
            { cancel_submit_ll.setVisibility(View.GONE);
                fpondcompleted_ll.setVisibility(View.VISIBLE);
            }
            else{ }
        }








        //date

        Calendar c = Calendar.getInstance();
        //System.out.println("Current time => "+c.getTime());

        //  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Log.e("date",formattedDate);

        str_submitteddatetime=formattedDate;
        str_validation_for_completed="no";


        //date


        str_latitude="";str_longitude="";

        gpstracker_obj2 = new Class_GPSTracker(EditFarmPondDetails_Activity.this);
        if(gpstracker_obj2.canGetLocation())
        {
            double_currentlatitude = gpstracker_obj2.getLatitude();
            double_currentlongitude = gpstracker_obj2.getLongitude();


           /* str_latitude =Double.toString(double_currentlatitude);
            str_longitude =Double.toString(double_currentlongitude);


            latitude_tv.setText(str_latitude);
            longitude_tv.setText(str_longitude);

            Log.e("lat",str_latitude);
            Log.e("long",str_longitude);*/

            String str_lattest=Double.toString(double_currentlatitude);
            String str_longtest=Double.toString(double_currentlongitude);




            if(str_lattest.equals("0.0")||str_longtest.equals("0.0"))
            {
                alertdialog_refresh_latandlong();
            }


        }else
        {
            gpstracker_obj2.showSettingsAlert();
        }


        /*arraylist_image1_base64.add("0");
        arraylist_image2_base64.add("0");
        arraylist_image3_base64.add("0");*/

        arraylist_image1_base64.add("noimage1");
        arraylist_image2_base64.add("noimage2");
        arraylist_image3_base64.add("noimage3");


        str_image1present=str_image2present=str_image3present="no";








    }// end of Oncreate();



    public void alertdialog_refresh_latandlong() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(EditFarmPondDetails_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Click Ok to fetch latitude and Longitude");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

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







}

