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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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
import java.util.Locale;

import df.farmponds.Models.ClusterAcademicSummary;
import df.farmponds.Models.ClusterFarmerMain;
import df.farmponds.Models.ClusterFarmersummaryList;
import df.farmponds.Models.ClusterSummaryList;
import df.farmponds.Models.DefaultResponse;
import df.farmponds.Models.ErrorUtils;
import df.farmponds.remote.Class_ApiUtils;
import df.farmponds.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClusterFarmerListActivity extends AppCompatActivity {

    TextView txt_Header;
    Toolbar toolbar;
    String Type,EmployeeId,YearId,Name,UserId;

    Interface_userservice userService1;
    String str_farmerID, str_userId;

    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    String Employee_Role;

    ClusterFarmerMain[] cluster_FsummaryLists;
    ClusterFarmersummaryList class_cluster_FsummaryList = new ClusterFarmersummaryList();
    ClusterFarmersummaryList[] arrayFsummarylist;

    ClusterFarmerListAdapter clusterFarmerListAdapter;
    private ArrayList<ClusterFarmersummaryList> clusterFarmerList;
    private ArrayList<ClusterFarmersummaryList> originalList = null;
    private EditText etSearch;
    private ListView lview;

    String str_base64image = null;
    String str_imageurltobase64_farmerimage;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_farmer_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = (TextView) toolbar.findViewById(R.id.title_name);


        userService1 = Class_ApiUtils.getUserService();
        txt_Header=(TextView)findViewById(R.id.txt_Header);
        lview = (NonScrollListView) findViewById(R.id.listview_clusterfarmerlist);
        etSearch = (EditText) findViewById(R.id.etSearch);

        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_userId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();
        Log.e("tag", "str_userId=" + str_userId);

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        Employee_Role=sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeecategory, "").trim();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            EmployeeId = extras.getString("EmployeeId");
            Type = extras.getString("Type");
            YearId = extras.getString("YearId");
            Name = extras.getString("Name");
            UserId = extras.getString("UserId");
        }

        txt_Header.setText(Name+"(Field Facilitator)");
        title.setText(Type + " Farmer List");
        getSupportActionBar().setTitle("");
        clusterFarmerList = new ArrayList<ClusterFarmersummaryList>();

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent)
        {
            Get_UserAcademicEmployeeData();
        }else{
             Toast.makeText(getApplicationContext(), "Connect to Internet", Toast.LENGTH_LONG).show();
        }


        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //adapter.getFilter().filter(s.toString());
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                clusterFarmerListAdapter.filter(text,originalList,null);
            }
        });
        clusterFarmerListAdapter = new ClusterFarmerListAdapter(ClusterFarmerListActivity.this, clusterFarmerList);
        lview.setAdapter(clusterFarmerListAdapter);

    } //end oncreate

    private void Get_UserAcademicEmployeeData() {

        Call<ClusterFarmerMain> call = userService1.get_UserAcademicEmployeeData(UserId,YearId,EmployeeId,Type);
        Log.e("request farmermain", "userId="+UserId+"YearId="+YearId+"EmployeeId="+EmployeeId+"Type"+Type);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ClusterFarmerListActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<ClusterFarmerMain>() {
            @Override
            public void onResponse(Call<ClusterFarmerMain> call, Response<ClusterFarmerMain> response) {
                //   Log.e("Entered resp", response.message());
                //     Log.e("Entered resp", response.body().getMessage());
                Log.e("TAG", "response Farmermain: " + new Gson().toJson(response));
                Log.e("response farmermain", String.valueOf(response.body()));

                if (response.isSuccessful()) {

                    ClusterFarmerMain class_userData = response.body();
                    //   Log.e("response.body", response.body().getLst().toString());
                    if (class_userData.getStatus().equals(true)) {
                        if(class_userData.getMessage().equalsIgnoreCase("Success")) {
                            List<ClusterFarmersummaryList> yearlist = response.body().getLstSummary();
                            Log.e("getLstFarSummary.size()", String.valueOf(yearlist.size()));
                            clusterFarmerList.clear();
                            cluster_FsummaryLists = new ClusterFarmerMain[yearlist.size()];
                            //   Log.e("tag","Pond Id=="+class_userData.getLst().get(0).getPond().get(0).getPondID());
                            arrayFsummarylist = new ClusterFarmersummaryList[yearlist.size()];
                            for (int i = 0; i < cluster_FsummaryLists.length; i++) {

                                Log.e("status", String.valueOf(class_userData.getStatus()));

                                class_cluster_FsummaryList.setEmployeeID((class_userData.getLstSummary().get(i).getEmployeeID()));
                                class_cluster_FsummaryList.setFarmerID((class_userData.getLstSummary().get(i).getFarmerID()));
                                class_cluster_FsummaryList.setFarmerMobile((class_userData.getLstSummary().get(i).getFarmerMobile()));
                                class_cluster_FsummaryList.setPondCount((class_userData.getLstSummary().get(i).getPondCount()));
                                class_cluster_FsummaryList.setFarmerPhoto((class_userData.getLstSummary().get(i).getFarmerPhoto()));
                                class_cluster_FsummaryList.setEmployeeName((class_userData.getLstSummary().get(i).getEmployeeName()));
                                class_cluster_FsummaryList.setPondStatus((class_userData.getLstSummary().get(i).getPondStatus()));
                                class_cluster_FsummaryList.setVillageName((class_userData.getLstSummary().get(i).getVillageName()));
                                class_cluster_FsummaryList.setFarmerName((class_userData.getLstSummary().get(i).getFarmerName()));

                                arrayFsummarylist[i] = class_cluster_FsummaryList;
                                Log.e("getEmployeeName", arrayFsummarylist[i].getEmployeeName());
                                Log.e("getFarmerName", arrayFsummarylist[i].getFarmerName());

                                String str_imageurl=arrayFsummarylist[i].getFarmerPhoto();


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

                                    //   str_base64image1 = str_base64image;

                                }
                                ClusterFarmersummaryList item = null;
                                item = new ClusterFarmersummaryList(arrayFsummarylist[i].getEmployeeName(),EmployeeId,arrayFsummarylist[i].getFarmerID(),arrayFsummarylist[i].getFarmerName(),arrayFsummarylist[i].getFarmerMobile(),arrayFsummarylist[i].getPondCount(),arrayFsummarylist[i].getPondStatus(),arrayFsummarylist[i].getVillageName(),str_base64image,YearId,Type);

                                clusterFarmerList.add(item);
                            }
                            originalList = new ArrayList<ClusterFarmersummaryList>();
                            originalList.clear();
                            originalList.addAll(clusterFarmerList);

                            clusterFarmerListAdapter.notifyDataSetChanged();
                            //  uploadfromDB_Farmerlist();
                        }
                        else{
                            Toast.makeText(ClusterFarmerListActivity.this, class_userData.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDoalog.dismiss();
                    } else {
                        progressDoalog.dismiss();

                        Toast.makeText(ClusterFarmerListActivity.this, class_userData.getMessage(), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(ClusterFarmerListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(ClusterFarmerListActivity.this, ClusterHomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.home_menu, menu);
        menu.findItem(R.id.filedfacilater).setTitle("Field Facilitator");

        if(Employee_Role.equalsIgnoreCase("Cluster Head_Field Facilitator"))
        {
            menu.findItem(R.id.filedfacilater).setVisible(true);
        }else{
            menu.findItem(R.id.filedfacilater).setVisible(false);
        }
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

            Intent i = new Intent(ClusterFarmerListActivity.this, ContactUs_Activity.class);
            startActivity(i);
            finish();
            return true;


        }

        if(id==R.id.filedfacilater)
        {

            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {

                Intent i = new Intent(ClusterFarmerListActivity.this, Activity_HomeScreen.class);
                startActivity(i);
                finish();
                return true;
            }
            else {
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }


        }



        if (id == R.id.logout)
        {
            // Toast.makeText(CalenderActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent)
            {


                AlertDialog.Builder dialog = new AlertDialog.Builder(ClusterFarmerListActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(R.string.alert);
                dialog.setMessage("Are you sure want to Logout?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {

                        SaveSharedPreference.setUserName(ClusterFarmerListActivity.this, "");

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
            Intent i = new Intent(ClusterFarmerListActivity.this, ClusterHomeActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}