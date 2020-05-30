package df.farmpondstwo;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.Date;

public class AddFarmPondActivity extends AppCompatActivity
{

    Class_GPSTracker gpstracker_obj1,gpstracker_obj2;
    Double double_currentlatitude=0.0;
    Double double_currentlongitude=0.0;
    String str_latitude,str_longitude;


    TextView latitude_tv,longitude_tv;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    Toolbar toolbar;


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

   /* Class_MachineDetails[] class_machineDetails_array_obj;
    Class_MachineDetails class_machineDetails_obj;
    String str_machinecode;

    Class_RemarksDetails[] class_remarksdetails_array_obj;
    Class_RemarksDetails class_remarksdetails_obj;
    String str_remarksid;

    String str_farmpondcount;
    Date startDate,endDate;

    Class_FarmerProfileOffline[] class_farmerprofileoffline_array_obj;*/

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
    ProgressDialog dialog_location;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm_pond);

        // Set upon the actionbar
        setSupportActionBar(toolbar);
        // Now use actionbar methods to show navigation icon and title
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        add_newfarmpond_iv=(ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
        title.setText("Add FarmPond Details");
        getSupportActionBar().setTitle("");
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorwhite), PorterDuff.Mode.SRC_ATOP);
        add_newfarmpond_iv.setVisibility(View.GONE);





    }
}
