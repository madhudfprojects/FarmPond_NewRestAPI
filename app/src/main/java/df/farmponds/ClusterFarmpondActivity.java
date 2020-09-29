package df.farmponds;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import df.farmponds.Models.ClusterFarmersummaryList;
import df.farmponds.Models.ClusterFarmpond;
import df.farmponds.Models.ClusterFarmpondList;
import df.farmponds.Models.DefaultResponse;
import df.farmponds.Models.ErrorUtils;
import df.farmponds.remote.Class_ApiUtils;
import df.farmponds.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClusterFarmpondActivity extends AppCompatActivity {

    Interface_userservice userService1;

    ClusterFarmpond[] cluster_FarmpondLists;
    ClusterFarmpondList class_cluster_FarmpondList = new ClusterFarmpondList();
    ClusterFarmpondList[] arrayFarmpondlist;

    ClusterFarmpondListViewAdapter clusterFarmpondListViewAdapter;
    private ArrayList<ClusterFarmpondList> clusterFarmpondList;
    private ArrayList<ClusterFarmpondList> originalList = null;

    String str_base64image1 = null,str_base64image2 = null,str_base64image3 = null;
    byte[] imageBytes;
    TextView yearName,stateName,districtName,talukaName,villageName,submitterName;
    TextView FName,MobileNo,pondCode,pondSize,startDate,endDate,latitude,longitude,amount,no_ofDays,location_Status,approval_Status;
    ImageView pond_image1_iv,pond_image2_iv,pond_image3_iv;
    Toolbar toolbar;
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    String EmployeeId,FarmerID,str_userId,YearId,Type;

    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    SharedPreferences sharedpreferencebook_usercredential_Obj;

    private ListView lview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_farmpond);

        userService1 = Class_ApiUtils.getUserService();

        toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Farmpond List");
        getSupportActionBar().setTitle("");

        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_userId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();
        Log.e("tag", "str_userId=" + str_userId);

        clusterFarmpondList = new ArrayList<ClusterFarmpondList>();
        lview=(ListView) findViewById(R.id.clustfarmpondlist_listview);
        FName=(TextView) findViewById(R.id.FName);
        MobileNo=(TextView) findViewById(R.id.MobileNo);

       /* yearName=(TextView) findViewById(R.id.yearName);
        stateName=(TextView) findViewById(R.id.stateName);
        districtName=(TextView) findViewById(R.id.districtName);
        talukaName=(TextView) findViewById(R.id.talukaName);
        villageName=(TextView) findViewById(R.id.villageName);
        submitterName=(TextView) findViewById(R.id.submitterName);

        pondCode=(TextView) findViewById(R.id.pondCode);
        pondSize=(TextView) findViewById(R.id.pondSize);
        startDate=(TextView) findViewById(R.id.startDate);
        endDate=(TextView) findViewById(R.id.endDate);
        latitude=(TextView) findViewById(R.id.latitude);
        longitude=(TextView) findViewById(R.id.longitude);
        amount=(TextView) findViewById(R.id.amount);
        no_ofDays=(TextView) findViewById(R.id.no_ofDays);
        location_Status=(TextView) findViewById(R.id.location_Status);
        approval_Status=(TextView) findViewById(R.id.approval_Status);
        pond_image1_iv=(ImageView) findViewById(R.id.pond_image1_iv);
        pond_image2_iv=(ImageView) findViewById(R.id.pond_image2_iv);
        pond_image3_iv=(ImageView) findViewById(R.id.pond_image3_iv);*/

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            EmployeeId = extras.getString("EmployeeId");
            FarmerID = extras.getString("FarmerID");
            YearId = extras.getString("YearId");
            Type = extras.getString("Type");

            Log.e("tag","EmployeeId="+EmployeeId+"FarmerID="+FarmerID+"Type="+Type+"YearId="+YearId);
        }

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent)
        {
            Get_UserAcademicEmployeeDataList();
        }else{
             Toast.makeText(getApplicationContext(), "Connect to Internet", Toast.LENGTH_LONG).show();
        }
        clusterFarmpondListViewAdapter = new ClusterFarmpondListViewAdapter(ClusterFarmpondActivity.this, clusterFarmpondList);
        lview.setAdapter(clusterFarmpondListViewAdapter);
    }

    private void Get_UserAcademicEmployeeDataList() {

        Call<ClusterFarmpond> call = userService1.get_UserAcademicEmployeeDataList(FarmerID,str_userId,YearId);

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ClusterFarmpondActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<ClusterFarmpond>() {
            @Override
            public void onResponse(Call<ClusterFarmpond> call, Response<ClusterFarmpond> response) {
                //   Log.e("Entered resp", response.message());
                //     Log.e("Entered resp", response.body().getMessage());
                Log.e("TAG", "response Farmpond: " + new Gson().toJson(response));
                Log.e("response farmpond", String.valueOf(response.body()));

                if (response.isSuccessful()) {

                    ClusterFarmpond class_userData = response.body();
                    //   Log.e("response.body", response.body().getLst().toString());
                    if (class_userData.getStatus().equals(true)) {
                        if(class_userData.getMessage().equalsIgnoreCase("Success")) {
                            List<ClusterFarmpondList> yearlist = response.body().getLstSummary();
                            Log.e("getLstFarSummary.size()", String.valueOf(yearlist.size()));
                            clusterFarmpondList.clear();
                            cluster_FarmpondLists = new ClusterFarmpond[yearlist.size()];
                            //   Log.e("tag","Pond Id=="+class_userData.getLst().get(0).getPond().get(0).getPondID());
                            arrayFarmpondlist = new ClusterFarmpondList[yearlist.size()];
                            for (int i = 0; i < cluster_FarmpondLists.length; i++) {

                                Log.e("status", String.valueOf(class_userData.getStatus()));

                                class_cluster_FarmpondList.setYearName((class_userData.getLstSummary().get(i).getYearName()));
                                class_cluster_FarmpondList.setStateName((class_userData.getLstSummary().get(i).getStateName()));
                                class_cluster_FarmpondList.setDistrictName((class_userData.getLstSummary().get(i).getDistrictName()));
                                class_cluster_FarmpondList.setTalukaName((class_userData.getLstSummary().get(i).getTalukaName()));
                                class_cluster_FarmpondList.setVillageName((class_userData.getLstSummary().get(i).getVillageName()));
                                class_cluster_FarmpondList.setUserID((class_userData.getLstSummary().get(i).getUserID()));
                                class_cluster_FarmpondList.setEmployeeName((class_userData.getLstSummary().get(i).getEmployeeName()));
                                class_cluster_FarmpondList.setFarmerID((class_userData.getLstSummary().get(i).getFarmerID()));
                                class_cluster_FarmpondList.setFarmerMobile((class_userData.getLstSummary().get(i).getFarmerMobile()));
                                class_cluster_FarmpondList.setFarmerName((class_userData.getLstSummary().get(i).getFarmerName()));
                                class_cluster_FarmpondList.setFarmerPhoto((class_userData.getLstSummary().get(i).getFarmerPhoto()));
                                class_cluster_FarmpondList.setPondCode((class_userData.getLstSummary().get(i).getPondCode()));
                                class_cluster_FarmpondList.setPondStatus((class_userData.getLstSummary().get(i).getPondStatus()));
                                class_cluster_FarmpondList.setPondID((class_userData.getLstSummary().get(i).getPondID()));
                                class_cluster_FarmpondList.setPondImageLink1((class_userData.getLstSummary().get(i).getPondImageLink1()));
                                class_cluster_FarmpondList.setPondImageLink2((class_userData.getLstSummary().get(i).getPondImageLink2()));
                                class_cluster_FarmpondList.setPondImageLink3((class_userData.getLstSummary().get(i).getPondImageLink3()));
                                class_cluster_FarmpondList.setPondSize((class_userData.getLstSummary().get(i).getPondSize()));
                                class_cluster_FarmpondList.setPondLatitude((class_userData.getLstSummary().get(i).getPondLatitude()));
                                class_cluster_FarmpondList.setPondLongitude((class_userData.getLstSummary().get(i).getPondLongitude()));
                                class_cluster_FarmpondList.setApprovalStatus((class_userData.getLstSummary().get(i).getApprovalStatus()));
                                class_cluster_FarmpondList.setLocationStatus((class_userData.getLstSummary().get(i).getLocationStatus()));

                                arrayFarmpondlist[i] = class_cluster_FarmpondList;
                                Log.e("getEmployeeName", arrayFarmpondlist[i].getEmployeeName());
                                Log.e("getFarmerName", arrayFarmpondlist[i].getFarmerName());

                                String str_imageurl1=arrayFarmpondlist[i].getPondImageLink1();

                                if (str_imageurl1 == null || str_imageurl1.equals("")) {
                                } else {
                                    String str_farmpondimageurl = str_imageurl1;

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
                                    str_base64image1 = Base64.encodeToString(b, Base64.DEFAULT);

                                    Log.e("tag", "byteArray img=" + b);

                                    /*imageBytes = Base64.decode(str_base64image1, Base64.DEFAULT);
                                    Bitmap bmp_Image1 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                    pond_image1_iv.setImageBitmap(bmp_Image1);*/

                                }

                                String str_imageurl2=arrayFarmpondlist[i].getPondImageLink2();

                                if (str_imageurl2 == null || str_imageurl2.equals("")) {
                                } else {
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
                                    str_base64image2 = Base64.encodeToString(b, Base64.DEFAULT);

                                    Log.e("tag", "byteArray img=" + b);

                                    /*imageBytes = Base64.decode(str_base64image2, Base64.DEFAULT);
                                    Bitmap bmp_Image2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                    pond_image2_iv.setImageBitmap(mIcon12);*/
                                }

                                String str_imageurl3=arrayFarmpondlist[i].getPondImageLink3();

                                if (str_imageurl3 == null || str_imageurl3.equals("")) {
                                } else {
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
                                    str_base64image3 = Base64.encodeToString(b, Base64.DEFAULT);

                                    Log.e("tag", "byteArray img=" + b);

                                    /*imageBytes = Base64.decode(str_base64image3, Base64.DEFAULT);
                                    Bitmap bmp_Image3 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                    pond_image3_iv.setImageBitmap(mIcon12);*/
                                }
                             /*   yearName.setText(arrayFarmpondlist[i].getYearName());
                                stateName.setText(arrayFarmpondlist[i].getStateName());
                                districtName.setText(arrayFarmpondlist[i].getDistrictName());
                                talukaName.setText(arrayFarmpondlist[i].getTalukaName());
                                villageName.setText(arrayFarmpondlist[i].getVillageName());

                                pondCode.setText(arrayFarmpondlist[i].getPondCode());
                                pondSize.setText(arrayFarmpondlist[i].getPondSize());
                                startDate.setText(arrayFarmpondlist[i].getConstructionStart());
                                endDate.setText(arrayFarmpondlist[i].getConstructionEnd());
                                latitude.setText(arrayFarmpondlist[i].getPondLatitude());
                                longitude.setText(arrayFarmpondlist[i].getPondLongitude());
                                amount.setText(arrayFarmpondlist[i].getCollectedAmount());
                                no_ofDays.setText(arrayFarmpondlist[i].getConstructionDays());
                                location_Status.setText(arrayFarmpondlist[i].getLocationStatus());
                                approval_Status.setText(arrayFarmpondlist[i].getApprovalStatus());*/

                                FName.setText(arrayFarmpondlist[i].getFarmerName());
                                MobileNo.setText(arrayFarmpondlist[i].getFarmerMobile());

                                ClusterFarmpondList item = null;
                                item = new ClusterFarmpondList(arrayFarmpondlist[i].getYearName(),
                                                               arrayFarmpondlist[i].getStateName(),
                                        arrayFarmpondlist[i].getDistrictName(),
                                        arrayFarmpondlist[i].getTalukaName(),
                                        arrayFarmpondlist[i].getVillageName(),
                                        arrayFarmpondlist[i].getFarmerName(),
                                        arrayFarmpondlist[i].getFarmerMobile(),
                                        arrayFarmpondlist[i].getPondCode(),
                                        arrayFarmpondlist[i].getPondSize(),
                                        arrayFarmpondlist[i].getConstructionStart(),
                                        arrayFarmpondlist[i].getConstructionEnd(),
                                        arrayFarmpondlist[i].getPondLatitude(),
                                        arrayFarmpondlist[i].getPondLongitude(),
                                        arrayFarmpondlist[i].getCollectedAmount(),
                                        arrayFarmpondlist[i].getConstructionCost(),
                                        arrayFarmpondlist[i].getConstructionDays(),
                                        arrayFarmpondlist[i].getLocationStatus(),
                                        arrayFarmpondlist[i].getApprovalStatus(),
                                        arrayFarmpondlist[i].getPondID(),
                                        str_base64image1,str_base64image2,str_base64image3);

                                clusterFarmpondList.add(item);


                            }
                            originalList = new ArrayList<ClusterFarmpondList>();
                            originalList.clear();
                            originalList.addAll(clusterFarmpondList);

                            clusterFarmpondListViewAdapter.notifyDataSetChanged();
                            //  uploadfromDB_Farmerlist();
                        }
                        else{
                            Toast.makeText(ClusterFarmpondActivity.this, class_userData.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDoalog.dismiss();
                    } else {
                        progressDoalog.dismiss();

                        Toast.makeText(ClusterFarmpondActivity.this, class_userData.getMessage(), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(ClusterFarmpondActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }

    @Override
    public void onBackPressed()
    {
        /*Intent i = new Intent(ClusterFarmpondActivity.this, ClusterFarmerListActivity.class);
        i.putExtra("EmployeeId",EmployeeId);
        i.putExtra("YearId",YearId);
        i.putExtra("Type",Type);
        startActivity(i);*/
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
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

            Intent i = new Intent(ClusterFarmpondActivity.this, ContactUs_Activity.class);
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


                AlertDialog.Builder dialog = new AlertDialog.Builder(ClusterFarmpondActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(R.string.alert);
                dialog.setMessage("Are you sure want to Logout?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {

                        SaveSharedPreference.setUserName(ClusterFarmpondActivity.this, "");

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






            } else {
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (id == android.R.id.home)
        {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
           /* Intent i = new Intent(ClusterFarmpondActivity.this, ClusterFarmerListActivity.class);
            i.putExtra("EmployeeId",EmployeeId);
            i.putExtra("YearId",YearId);
            i.putExtra("Type",Type);
            startActivity(i);*/
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}