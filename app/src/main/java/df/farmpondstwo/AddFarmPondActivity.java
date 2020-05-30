package df.farmpondstwo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import java.util.List;

import df.farmpondstwo.Models.Class_MachineDetails;

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

    Class_MachineDetails[] class_machineDetails_array_obj;
    Class_MachineDetails class_machineDetails_obj;
    String str_machinecode;

    /*Class_RemarksDetails[] class_remarksdetails_array_obj;
    Class_RemarksDetails class_remarksdetails_obj;*/
    String str_remarksid;

    String str_farmpondcount;
    Date startDate,endDate;

   // Class_FarmerProfileOffline[] class_farmerprofileoffline_array_obj;

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










        str_DBerror_check="no";


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
        //submittedby_tv.setText(str_submitter_mailid);
        submittedby_tv.setText("Add Submitter MailID");


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






        gpstracker_obj2 = new Class_GPSTracker(AddFarmPondActivity.this);
        if(gpstracker_obj2.canGetLocation())
        {
            double_currentlatitude = gpstracker_obj2.getLatitude();
            double_currentlongitude = gpstracker_obj2.getLongitude();


            str_latitude =Double.toString(double_currentlatitude);
            str_longitude =Double.toString(double_currentlongitude);


            latitude_tv.setText(str_latitude);
            longitude_tv.setText(str_longitude);

            Log.e("lat",str_latitude);
            Log.e("long",str_longitude);

            if(str_latitude.equals("0.0")||str_longitude.equals("0.0"))
            {
                alertdialog_refresh_latandlong();
            }


        }else
        {
            gpstracker_obj2.showSettingsAlert();
        }

        //newfarmpond_count();
        //checkthecount();



        arraylist_image1_base64.add("noimage1");
        arraylist_image2_base64.add("noimage2");
        arraylist_image3_base64.add("noimage3");


        str_image1present=str_image2present=str_image3present="no";







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

        add_newpond_image3_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str_image1="false";
                str_image2="false";

                str_image3="true";

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














    }//end of oncreate()



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



}//end of class
