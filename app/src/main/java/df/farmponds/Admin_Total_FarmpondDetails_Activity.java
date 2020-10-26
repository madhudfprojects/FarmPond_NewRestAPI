package df.farmponds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import df.farmponds.Models.AdminEmpTotalPondCountList;
import df.farmponds.Models.AdminEmpoyeeTotalPondCount;
import df.farmponds.Models.AutoSyncVersion;
import df.farmponds.Models.AutoSyncVersionList;
import df.farmponds.Models.Class_farmponddetails;
import df.farmponds.Models.DefaultResponse;
import df.farmponds.Models.ErrorUtils;
import df.farmponds.Models.UserDataSummary;
import df.farmponds.Models.UserDataSummaryList;
import df.farmponds.Models.Year;
import df.farmponds.remote.Class_ApiUtils;
import df.farmponds.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Total_FarmpondDetails_Activity extends AppCompatActivity
{

    Toolbar toolbar;
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";

    SharedPreferences sharedpreferencebook_usercredential_Obj;

    String str_farmerID, str_employee_id;

    AdminEmpTotalPondCountList[] class_manageremployeeisecount_array_obj;
    AdminEmpTotalPondCountList class_manageremployeeisecount_obj;
    private ListView employeecount_listview;
    String str_totalfarmpondcount, str_todaysdate, str_todayTotalCount;
    TextView total_farmpond_allstate_tv, todaysdate_tv, todaysdate_tv1;
    Interface_userservice userService1;
    Year[] arrayObj_Class_yearDetails, arrayObj_Class_yearDetails2;
    Year Obj_Class_yearDetails;
    Spinner yearlist_farmers_sp;
    String selected_year, sp_stryear_ID;
    ImageView downloadIcon,excel_download;
    TextView dataReject,dataApproved,dataPending,dataProcess,dataAll;
    String directory_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_total_farmpond_details);


        toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Submitted Ponds");
        getSupportActionBar().setTitle("");

        userService1 = Class_ApiUtils.getUserService();
        total_farmpond_allstate_tv = (TextView) findViewById(R.id.total_farmpond_allstate_tv);
        todaysdate_tv1 = (TextView) findViewById(R.id.todaysdate_tv1);

        todaysdate_tv = (TextView) findViewById(R.id.todaysdate_tv);
        employeecount_listview = (ListView) findViewById(R.id.employeecount_listview);
        yearlist_farmers_sp = (Spinner) findViewById(R.id.yearlist_farmer_SP);
        downloadIcon = (ImageView) findViewById(R.id.downloadIcon);
        dataAll = (TextView) findViewById(R.id.dataAll);
        dataApproved = (TextView) findViewById(R.id.dataApproved);
        dataPending = (TextView) findViewById(R.id.dataPending);
        dataProcess = (TextView) findViewById(R.id.dataProcess);
        dataReject = (TextView) findViewById(R.id.dataReject);
        excel_download = (ImageView) findViewById(R.id.excel_download);

        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_employee_id = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();


        str_totalfarmpondcount = str_todaysdate = "Not Available";
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        uploadfromDB_Yearlist();

        yearlist_farmers_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_yearDetails = (Year) yearlist_farmers_sp.getSelectedItem();
                sp_stryear_ID = Obj_Class_yearDetails.getAcademic_ID().toString();
                selected_year = yearlist_farmers_sp.getSelectedItem().toString();
                int sel_yearsp_new = yearlist_farmers_sp.getSelectedItemPosition();

                if(isInternetPresent){
                    GetUserDataSummary();
                }
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

        excel_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_TableDeletedFarmPondDetails_Excel();
                getFarmerTableToExcel();
                directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup_excel/";
                final File file = new File(directory_path);
                if (!file.exists()) {
                    file.mkdirs();
                    Log.e("tag","file created");
                }/*else {
                   // if (file.delete()) {
                    File file_delete = new File(directory_path, "FarmPondDetails_Excel.csv");
                    Log.e("tag","file_delete=="+file_delete.toString());
                    if(file_delete.delete()){
                        file.mkdirs();
                        Log.e("tag","file deleted");
                    }else{
                        Log.e("tag","file not deleted");
                    }
                }*/
                SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), "FarmPond_db", directory_path);
                sqliteToExcel.exportSingleTable("FarmPondDetails_Excel", "FarmPondDetails_Excel.csv", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onCompleted(String filePath) {
                        Toast.makeText(Admin_Total_FarmpondDetails_Activity.this, "Successfully Exported", Toast.LENGTH_SHORT).show();
                        try {
                            //FileOpen.openFile(mContext, myFile);

                            File outputFile = new File(file, "FarmPondDetails_Excel.csv");
                          //  Log.e("tag","file=="+file.toString());
                            //Log.e("tag","outputFile=="+outputFile.toString());
                            FileOpen.openFile(getApplicationContext(), outputFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("tag","error:"+e.toString());
                    }
                });
            }
        });
        if (isInternetPresent) {
            GetEmpWiseCount();
            //  AsyncTask_fetch_empwise_count();
        }


    }//end of class

    public  void getFarmerTableToExcel(){
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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR,finalfarmpondcodeDB VARCHAR,Location_Status VARCHAR," +
                "Reading_Start VARCHAR,Reading_End VARCHAR,Reading_Hour VARCHAR,Machine_Name VARCHAR);");


        //  Log.e("tag", "pond FIDDB str_farmerid=" + str_farmerid);
        Cursor cursor1 = db1.rawQuery("SELECT FIDDB,FNameDB,FMNameDB,FLNameDB,FphonenumberDB,FYearIDDB,FStateIDDB,FDistrictIDDB,FTalukIDDB ,FPanchayatIDDB,FVillageIDDB," +
                "FPondidDB,WidthDB,HeightDB,DepthDB,FPondLatitudeDB,FPondLongitudeDB,StartDateDB,ConstructedDateDB,PondCostDB," +
                "FPondAmtTakenDB,FPondStatusDB,FPondDonorDB,Reading_Start,Reading_End,Reading_Hour FROM FarmPondDetails_fromServerRest", null);
        int x = cursor1.getCount();
        if(x>0) {
            if (cursor1.moveToFirst()) {

                do {

                    String FPondidDB,WidthDB,HeightDB,DepthDB,FPondLatitudeDB,FPondLongitudeDB,StartDateDB,ConstructedDateDB,PondCostDB,FPondAmtTakenDB,FPondStatusDB,FPondDonorDB;
                    String str_FphonenumberDB =null,str_FYearIDDB = null,str_FStateIDDB= null,str_FDistrictIDDB= null,str_FTalukIDDB= null,str_FPanchayatIDDB= null,str_FVillageIDDB= null;
                    String str_FIDDB = cursor1.getString(cursor1.getColumnIndex("FIDDB"));
                    String str_FNameDB = cursor1.getString(cursor1.getColumnIndex("FNameDB"));
                    String str_FMNameDB = cursor1.getString(cursor1.getColumnIndex("FMNameDB"));
                    String str_FLNameDB = cursor1.getString(cursor1.getColumnIndex("FLNameDB"));

                  //  String str_FphonenumberDB = cursor1.getString(cursor1.getColumnIndex("FphonenumberDB"));
                    /*String str_FYearIDDB = cursor1.getString(cursor1.getColumnIndex("FYearIDDB"));
                    String str_FStateIDDB = cursor1.getString(cursor1.getColumnIndex("FStateIDDB"));
                    String str_FDistrictIDDB = cursor1.getString(cursor1.getColumnIndex("FDistrictIDDB"));
                    String str_FTalukIDDB = cursor1.getString(cursor1.getColumnIndex("FTalukIDDB"));
                    String str_FPanchayatIDDB = cursor1.getString(cursor1.getColumnIndex("FPanchayatIDDB"));
                    String str_FVillageIDDB = cursor1.getString(cursor1.getColumnIndex("FVillageIDDB"));*/

                    FPondidDB=cursor1.getString(cursor1.getColumnIndex("FPondidDB"));
                    WidthDB=cursor1.getString(cursor1.getColumnIndex("WidthDB"));
                    HeightDB=cursor1.getString(cursor1.getColumnIndex("HeightDB"));
                    DepthDB=cursor1.getString(cursor1.getColumnIndex("DepthDB"));
                    FPondLatitudeDB=cursor1.getString(cursor1.getColumnIndex("FPondLatitudeDB"));
                    FPondLongitudeDB=cursor1.getString(cursor1.getColumnIndex("FPondLongitudeDB"));
                    StartDateDB=cursor1.getString(cursor1.getColumnIndex("StartDateDB"));
                    ConstructedDateDB=cursor1.getString(cursor1.getColumnIndex("ConstructedDateDB"));
                    PondCostDB=cursor1.getString(cursor1.getColumnIndex("PondCostDB"));
                    FPondAmtTakenDB=cursor1.getString(cursor1.getColumnIndex("FPondAmtTakenDB"));
                    FPondStatusDB=cursor1.getString(cursor1.getColumnIndex("FPondStatusDB"));
                    FPondDonorDB=cursor1.getString(cursor1.getColumnIndex("FPondDonorDB"));

                    String Full_Name=str_FNameDB+" "+str_FMNameDB+" "+ str_FLNameDB;
                    String PondSize=HeightDB+"*"+WidthDB+"*"+DepthDB;
                    //Reading_Start,Reading_End,Reading_Hour
                   String str_reading_start=cursor1.getString(cursor1.getColumnIndex("Reading_Start"));
                    String str_reading_end=cursor1.getString(cursor1.getColumnIndex("Reading_End"));
                    String str_reading_hour=cursor1.getString(cursor1.getColumnIndex("Reading_Hour"));

                    SQLiteDatabase db3 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

                    String YearID = null,StateID = null,DistrictID = null,TalukID = null,VillageID= null,PanchayatID= null;
                   // Cursor cursor3 = db3.rawQuery("SELECT ViewFarmerListRest.DispFarmerTable_StateID , ViewFarmerListRest.DispFarmerTable_DistrictID , ViewFarmerListRest.DispFarmerTable_TalukID , ViewFarmerListRest.DispFarmerTable_VillageID , ViewFarmerListRest.DispFarmerTable_GrampanchayatID FROM ViewFarmerListRest INNER JOIN FarmPondDetails_fromServerRest ON ViewFarmerListRest.DispFarmerTable_FarmerID = FarmPondDetails_fromServerRest.FIDDB",null);
                    Cursor cursor3 = db3.rawQuery("SELECT Farmercellno_DB,DispFarmerTable_YearID,DispFarmerTable_StateID , DispFarmerTable_DistrictID , DispFarmerTable_TalukID , DispFarmerTable_VillageID , DispFarmerTable_GrampanchayatID FROM ViewFarmerListRest WHERE DispFarmerTable_FarmerID='" + str_FIDDB+ "'",null);

                    int x3 = cursor3.getCount();
                    if(x3>0) {
                        if (cursor3.moveToFirst()) {

                            do {
                                YearID=cursor3.getString(cursor3.getColumnIndex("DispFarmerTable_YearID"));
                                StateID=cursor3.getString(cursor3.getColumnIndex("DispFarmerTable_StateID"));
                                DistrictID=cursor3.getString(cursor3.getColumnIndex("DispFarmerTable_DistrictID"));
                                TalukID=cursor3.getString(cursor3.getColumnIndex("DispFarmerTable_TalukID"));
                                VillageID =cursor3.getString(cursor3.getColumnIndex("DispFarmerTable_VillageID"));
                                PanchayatID =cursor3.getString(cursor3.getColumnIndex("DispFarmerTable_GrampanchayatID"));
                                str_FphonenumberDB =cursor3.getString(cursor3.getColumnIndex("Farmercellno_DB"));
                                Log.e("tag","StateID="+StateID+"DistrictID="+DistrictID+"TalukID="+TalukID+"VillageID="+VillageID+"PanchayatID="+PanchayatID);

                            }while (cursor3.moveToNext());
                        }
                    }
                    Log.e("tag","2 StateID="+StateID+"DistrictID="+DistrictID+"TalukID="+TalukID+"VillageID="+VillageID+"PanchayatID="+PanchayatID);

                    SQLiteDatabase db_state = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
                    Cursor cursor_state = db_state.rawQuery("SELECT StateName FROM StateListRest WHERE StateID='" + StateID+ "'", null);
                    int xstate = cursor_state.getCount();
                    if(xstate>0) {
                        if (cursor_state.moveToFirst()) {

                            do {
                                str_FStateIDDB=cursor_state.getString(cursor_state.getColumnIndex("StateName"));
                            }while (cursor_state.moveToNext());
                        }
                    }
                    SQLiteDatabase db_District = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
                    Cursor cursor_District = db_District.rawQuery("SELECT DistrictName FROM DistrictListRest WHERE DistrictID='" + DistrictID+ "'", null);
                    int xDistrict = cursor_District.getCount();
                    if(xDistrict>0) {
                        if (cursor_District.moveToFirst()) {
                            do {
                                str_FDistrictIDDB=cursor_District.getString(cursor_District.getColumnIndex("DistrictName"));
                            }while (cursor_District.moveToNext());
                        }
                    }
                    SQLiteDatabase db_Taluk = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
                    Cursor cursor_Taluk = db_Taluk.rawQuery("SELECT TalukName FROM TalukListRest WHERE TalukID='" + TalukID+ "'", null);
                    int xTaluk = cursor_Taluk.getCount();
                    if(xTaluk>0) {
                        if (cursor_Taluk.moveToFirst()) {
                            do {
                                str_FTalukIDDB=cursor_Taluk.getString(cursor_Taluk.getColumnIndex("TalukName"));
                            }while (cursor_Taluk.moveToNext());
                        }
                    }
                    SQLiteDatabase db_Village = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
                    Cursor cursor_Village = db_Village.rawQuery("SELECT Village FROM VillageListRest WHERE VillageID='" + VillageID+ "'", null);
                    int xVillage = cursor_Village.getCount();
                    if(xVillage>0) {
                        if (cursor_Village.moveToFirst()) {

                            do {
                                str_FVillageIDDB=cursor_Village.getString(cursor_Village.getColumnIndex("Village"));
                            }while (cursor_Village.moveToNext());
                        }
                    }
                    SQLiteDatabase db_Gramanchayat = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
                    Cursor cursor_Gramanchayat = db_Gramanchayat.rawQuery("SELECT Gramanchayat FROM GrampanchayatListRest WHERE GramanchayatID='" + PanchayatID+ "'", null);
                    int xGramanchayat = cursor_Gramanchayat.getCount();
                    if(xGramanchayat>0) {
                        if (cursor_Gramanchayat.moveToFirst()) {
                            do {
                                str_FPanchayatIDDB=cursor_Gramanchayat.getString(cursor_Gramanchayat.getColumnIndex("Gramanchayat"));
                            }while (cursor_Gramanchayat.moveToNext());
                        }
                    }
                    SQLiteDatabase db_Year = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
                    Cursor cursor_Year = db_Year.rawQuery("SELECT YearName FROM YearListRest WHERE YearID='" + YearID+ "'", null);
                    int xYear = cursor_Year.getCount();
                    if(xYear>0) {
                        if (cursor_Year.moveToFirst()) {

                            do {
                                str_FYearIDDB=cursor_Year.getString(cursor_Year.getColumnIndex("YearName"));
                            }while (cursor_Year.moveToNext());
                        }
                    }
                    SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);




                    db2.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_Excel(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FarmerID VARCHAR," +
                                    "FarmerName VARCHAR,Year VARCHAR,State VARCHAR,District VARCHAR," +
                                    "Taluk VARCHAR,Panchayat VARCHAR,Village VARCHAR,MobileNo VARCHAR,Pond_ID VARCHAR,Latitude VARCHAR,Longitude VARCHAR,StartDate VARCHAR," +
                                    "ConstructedDate VARCHAR,PondCost VARCHAR,PondAmtTaken VARCHAR," +
                            "PondStatus VARCHAR,PondDonor VARCHAR,PondSize VARCHAR," +
                            "Reading_Start VARCHAR,Reading_End VARCHAR,Reading_Hour VARCHAR);");
                                  //  "FAnnualIncomeDB VARCHAR,FfamilymemberDB VARCHAR,FidprooftypeDB VARCHAR,FidproofnoDB VARCHAR,FphotoDB VARCHAR,FPondidDB VARCHAR,WidthDB VARCHAR);");

                    String SQLiteQuery = "INSERT INTO FarmPondDetails_Excel(FarmerID,FarmerName,Year,State,District," +
                            "Taluk,Panchayat,Village,MobileNo,Pond_ID,Latitude ,Longitude ,StartDate,ConstructedDate ,PondCost ,PondAmtTaken ,PondStatus ,PondDonor,PondSize )" +
                    " VALUES ('" + str_FIDDB + "','" + Full_Name+ "','" + str_FYearIDDB + "','" + str_FStateIDDB + "','" + str_FDistrictIDDB + "','" + str_FTalukIDDB + "'," +
                            "'" + str_FPanchayatIDDB + "','" + str_FVillageIDDB + "','" + str_FphonenumberDB + "','" + FPondidDB +"','" + FPondLatitudeDB +"','" + FPondLongitudeDB +
                            "','" + StartDateDB +"','" + ConstructedDateDB +"','" +
                            PondCostDB +"','" + FPondAmtTakenDB +"','" + FPondStatusDB +"','" + FPondDonorDB+"','" + PondSize+"','" + str_reading_start+"','" + str_reading_end+"','" + str_reading_hour+"');";

                    db2.execSQL(SQLiteQuery);

           /*         innerObj_Class_farmponddetails.setSubmittedDate(cursor1.getString(cursor1.getColumnIndex("SubmittedDateDB")));


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
*/

                } while (cursor1.moveToNext());
            }//if ends
        }

    }
    private void GetEmpWiseCount() {
//        Map<String,String> params = new HashMap<String, String>();
//
//        params.put("User_ID","90");// for dynamic

        retrofit2.Call call = userService1.getEmpWiseCount(str_employee_id);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Admin_Total_FarmpondDetails_Activity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<AdminEmpoyeeTotalPondCount>() {
            @Override
            public void onResponse(Call<AdminEmpoyeeTotalPondCount> call, Response<AdminEmpoyeeTotalPondCount> response) {
                Log.e("response", response.toString());
                Log.e("TAG", "response: " + new Gson().toJson(response));
                Log.e("response body", String.valueOf(response.body()));

                if (response.isSuccessful()) {
                    //  progressDoalog.dismiss();
                    AdminEmpoyeeTotalPondCount class_loginresponse = response.body();
                    Log.e("tag", "res==" + class_loginresponse.toString());
                    if (class_loginresponse.getStatus()) {

                        List<AdminEmpTotalPondCountList> addFarmerResList = response.body().getEmployeeList();
                        Log.e("tag", "addFarmerResList NAme=" + addFarmerResList.get(0).getEmployeeName());
                        Log.e("tag", "addFarmerResList farmerID=" + addFarmerResList.get(0).getEmployeeCount());

                        str_totalfarmpondcount = class_loginresponse.getTotalCount();
                        str_todaysdate = class_loginresponse.getTodaysDate();
                        str_todayTotalCount = class_loginresponse.getTotalTodaysCount();

                        //  JSONArray response_jsonarray = jsonObject.getJSONArray("Employees_wise_details");

                        //    Log.e("employeewise", String.valueOf(response_jsonarray.length()));


                        int int_jsonarraylength = addFarmerResList.size();// response_jsonarray.length();

                        class_manageremployeeisecount_array_obj = new AdminEmpTotalPondCountList[int_jsonarraylength];


                        for (int i = 0; i < int_jsonarraylength; i++) {
                            //  AdminEmpTotalPondCountList class_manageremployeeisecount__innerobj = new Gson().fromJson(String.valueOf(addFarmerResList.get(i).toString()), Class_ManagerEmployeeWiseCount.class);
                            AdminEmpTotalPondCountList adminEmpTotalPondCountList = new AdminEmpTotalPondCountList(addFarmerResList.get(i).getEmployeeName(), addFarmerResList.get(i).getEmployeeCount(), addFarmerResList.get(i).getTodaysCount());
                            class_manageremployeeisecount_array_obj[i] = adminEmpTotalPondCountList;

                        }

                        if (class_manageremployeeisecount_array_obj != null) {
                            CustomAdapter_manageremployeeisecount adapter = new CustomAdapter_manageremployeeisecount();
                            employeecount_listview.setAdapter(adapter);

                            int y = class_manageremployeeisecount_array_obj.length;

                            Log.e("length adapter", "" + y);
                        } else {
                            Log.d("length adapter", "zero");
                        }

                        progressDoalog.dismiss();

                    } else {
                        progressDoalog.dismiss();
                        Toast.makeText(Admin_Total_FarmpondDetails_Activity.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Admin_Total_FarmpondDetails_Activity.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Admin_Total_FarmpondDetails_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void GetUserDataSummary() {

        Log.e("tag","str_employee_id="+str_employee_id+" sp_stryear_ID="+sp_stryear_ID);
        retrofit2.Call call = userService1.getUserDataSummary(str_employee_id,sp_stryear_ID);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Admin_Total_FarmpondDetails_Activity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<UserDataSummary>() {
            @Override
            public void onResponse(Call<UserDataSummary> call, Response<UserDataSummary> response) {
                Log.e("tag","response UserDataSummary="+response.toString());
                Log.e("TAG", "response UserDataSummary: " + new Gson().toJson(response));
                Log.e("response body", String.valueOf(response.body()));

                if (response.isSuccessful()) {
                    //  progressDoalog.dismiss();
                    UserDataSummary getUserDataSummary = response.body();
                    Log.e("tag", "res==" + getUserDataSummary.toString());
                  //  str_VersionStatus = String.valueOf(getUserDataSummary.getStatus());

                    if (getUserDataSummary.getStatus()) {

                        List<UserDataSummaryList> addFarmerResList = response.body().getLstSummary();
                      //  Log.e("tag", "getDataAll =" + addFarmerResList.get(0).getDataAll());
                        //Log.e("tag", "getDataApproved =" + addFarmerResList.get(0).getDataApproved());
String DataAll="0",DataApproved="0",DataPending="0",DataProcess="0",DataRejected="0",ExcelLink = null;

                        for(int i=0;i<addFarmerResList.size();i++){
                            DataAll=addFarmerResList.get(i).getDataAll();
                            DataApproved=addFarmerResList.get(i).getDataApproved();
                            DataPending=addFarmerResList.get(i).getDataPending();
                            DataProcess=addFarmerResList.get(i).getDataProcess();
                            DataRejected=addFarmerResList.get(i).getDataRejected();
                            ExcelLink=addFarmerResList.get(i).getExcelLink();
                        }
                        dataAll.setText(DataAll);
                        dataApproved.setText(DataApproved);
                        dataPending.setText(DataPending);
                        dataProcess.setText(DataProcess);
                        dataReject.setText(DataRejected);

                        if(ExcelLink==null||ExcelLink.equalsIgnoreCase("")){
                            downloadIcon.setVisibility(View.GONE);
                        }else{
                       //     downloadIcon.setVisibility(View.VISIBLE);
                        }
                        //  str_VersionStatus = String.valueOf(class_loginresponse.getStatus());
                        progressDoalog.dismiss();

                    } else {
                        progressDoalog.dismiss();

                        Log.e("tag", "class_loginresponse.getMessage() =" + getUserDataSummary.getMessage());

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
                Toast.makeText(Admin_Total_FarmpondDetails_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

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

    public static class FileOpen {

        public static void openFile(Context context, File url) throws IOException {
            // Create URI
            File file=url;
            //  Uri uri = Uri.fromFile(file);
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            // Check what kind of file you are trying to open, by comparing the url with extensions.
            // When the if condition is matched, plugin sets the correct intent (mime) type,
            // so Android knew what application to use to open the file
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if(url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if(url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if(url.toString().contains(".csv")||url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if(url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if(url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if(url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if(url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if(url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if(url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if(url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }

             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        }
    }


    /*  private void AsyncTask_fetch_empwise_count() {

        final ProgressDialog pdLoading = new ProgressDialog(Admin_Total_FarmpondDetails_Activity.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String str_fetchfarmponddetails_url = Class_URL.URL_managerlogin_empwise_count.toString().trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pdLoading.dismiss();

                        Log.e("volley mngempcount resp", response);//volley response: {"statusMessage":"success"}
                        parse_emplyoeewise_count(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdLoading.dismiss();
                        Toast.makeText(Admin_Total_FarmpondDetails_Activity.this, "WS:" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();


                try {
                    params.put("employee_id", "6");//str_employee_id

                } catch (Exception e) {
                    Log.e("upload error", e.getMessage());
                    e.printStackTrace();
                }

                //Log.e("image", arraylist_image1_base64.get(0).toString());
                //  Log.e("Edit request", String.valueOf(params));
                return params;

            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("request", stringRequest.toString());
        requestQueue.add(stringRequest);
    }
*/
    public void parse_emplyoeewise_count(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            //"StatusMessage": "Success"
            if (jsonObject.getString("Status Message").equalsIgnoreCase("Success"))
            //if (2>1)
            {
                // Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();

                str_totalfarmpondcount = jsonObject.getString("farmpond_Total_Count");
                str_todaysdate = jsonObject.getString("Today_date");


                JSONArray response_jsonarray = jsonObject.getJSONArray("Employees_wise_details");

                Log.e("employeewise", String.valueOf(response_jsonarray.length()));


                int int_jsonarraylength = response_jsonarray.length();

                class_manageremployeeisecount_array_obj = new AdminEmpTotalPondCountList[int_jsonarraylength];


                for (int i = 0; i < int_jsonarraylength; i++) {
                    //  Class_ManagerEmployeeWiseCount class_manageremployeeisecount__innerobj = new Gson().fromJson(String.valueOf(response_jsonarray.get(i).toString()), Class_ManagerEmployeeWiseCount.class);
                    AdminEmpTotalPondCountList adminEmpTotalPondCountList = new AdminEmpTotalPondCountList();
                    class_manageremployeeisecount_array_obj[i] = adminEmpTotalPondCountList;

                }

                if (class_manageremployeeisecount_array_obj != null) {
                    CustomAdapter_manageremployeeisecount adapter = new CustomAdapter_manageremployeeisecount();
                    employeecount_listview.setAdapter(adapter);

                    int y = class_manageremployeeisecount_array_obj.length;

                    Log.e("length adapter", "" + y);
                } else {
                    Log.d("length adapter", "zero");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error ponds", e.toString());
        }

    }

    private class Holder_offline {
        TextView holder_employeename;
        TextView holder_totalfarmpond_added;
        TextView holder_todayfarmpond_added;
        TextView holder_todaysdate;
    }

    public class CustomAdapter_manageremployeeisecount extends BaseAdapter {


        public CustomAdapter_manageremployeeisecount() {

            super();
            Log.d("Inside CustomAdapter()", "Inside CustomAdapter()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(class_manageremployeeisecount_array_obj.length);
            System.out.println("class_farmponddetails_offline_array_obj.length" + x);
            return class_manageremployeeisecount_array_obj.length;
        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            Log.d("getItem position", "x");
            return class_manageremployeeisecount_array_obj[position];
        }


        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);

            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView1, ViewGroup parent) {

            final Holder_offline holder;

            Log.d("CustomAdapter", "position: " + position);

            if (convertView1 == null) {
                holder = new Holder_offline();
                convertView1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_row_item_employeecount, parent, false);
                total_farmpond_allstate_tv.setText(str_totalfarmpondcount);
                todaysdate_tv1.setText(str_todayTotalCount);
                String str_submitted = "submitted " + str_todaysdate;
                Log.e("tag", "total count:" + str_totalfarmpondcount + "todaytotalcount:" + str_todaysdate);
                todaysdate_tv.setText(str_submitted);
                holder.holder_employeename = (TextView) convertView1.findViewById(R.id.employeename_tv);
                holder.holder_totalfarmpond_added = (TextView) convertView1.findViewById(R.id.totalpondcount_tv);
                holder.holder_todayfarmpond_added = (TextView) convertView1.findViewById(R.id.todayscount_tv);
                holder.holder_todaysdate = (TextView) convertView1.findViewById(R.id.todaysdate_tv);

                Log.d("Inside If convertView1", "Inside If convertView1");

                convertView1.setTag(holder);

            } else {
                holder = (Holder_offline) convertView1.getTag();
                Log.d("else convertView1", "else convertView1");
            }

            class_manageremployeeisecount_obj = (AdminEmpTotalPondCountList) getItem(position);

            int int_total = 0;
            if (class_manageremployeeisecount_obj != null) {
                if (2 > 1) {
                    Log.e("employeename", class_manageremployeeisecount_obj.getEmployeeName().toString());
                    holder.holder_employeename.setText(class_manageremployeeisecount_obj.getEmployeeName());
                    holder.holder_totalfarmpond_added.setText(class_manageremployeeisecount_obj.getEmployeeCount());
                    holder.holder_todayfarmpond_added.setText(class_manageremployeeisecount_obj.getTodaysCount());

                    int_total = (int_total + Integer.parseInt(class_manageremployeeisecount_obj.getTodaysCount()));

                    Log.e("total", String.valueOf(int_total));

                    holder.holder_todaysdate.setText(str_todaysdate);
                } else {

                }


            }// end if 1

            return convertView1;

        }//End of custom getView
    }//End of CustomAdapter

    public void delete_TableDeletedFarmPondDetails_Excel() {

        SQLiteDatabase db_deleteTable = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db_deleteTable.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_Excel(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FarmerID VARCHAR," +
                "FarmerName VARCHAR,Year VARCHAR,State VARCHAR,District VARCHAR," +
                "Taluk VARCHAR,Panchayat VARCHAR,Village VARCHAR,MobileNo VARCHAR,Pond_ID VARCHAR,Latitude VARCHAR,Longitude VARCHAR,StartDate VARCHAR," +
                "ConstructedDate VARCHAR,PondCost VARCHAR,PondAmtTaken VARCHAR,PondStatus VARCHAR,PondDonor VARCHAR,PondSize VARCHAR);");

        Cursor cursor = db_deleteTable.rawQuery("SELECT * FROM FarmPondDetails_Excel", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_deleteTable.delete("FarmPondDetails_Excel", null, null);
            Log.e("delete_Table", "FarmPondDetails_Excel");

        }
        db_deleteTable.close();

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Activity_HomeScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Admin_Total_FarmpondDetails_Activity.this, Activity_HomeScreen.class);
            // i.putExtra("value_constant","1");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

}// end of class
