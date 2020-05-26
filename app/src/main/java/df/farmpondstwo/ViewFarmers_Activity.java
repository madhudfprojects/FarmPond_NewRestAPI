package df.farmpondstwo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import df.farmpondstwo.Models.DefaultResponse;
import df.farmpondstwo.Models.ErrorUtils;
import df.farmpondstwo.Models.Location_Data;
import df.farmpondstwo.Models.Location_DataList;
import df.farmpondstwo.Models.UserData;
import df.farmpondstwo.Models.UserDataList;
import df.farmpondstwo.remote.Class_ApiUtils;
import df.farmpondstwo.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFarmers_Activity extends AppCompatActivity {

    Class_InternetDectector internetDectector2,internetDectector3;
    Boolean isInternetPresent2 = false;

    Interface_userservice userService1;
    Location_DataList[] location_dataLists;
    Location_DataList class_location_dataList = new Location_DataList();

    Spinner yearlist_SP, statelist_SP, districtlist_SP, taluklist_SP, villagelist_SP, grampanchayatlist_SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_farmers);
        userService1 = Class_ApiUtils.getUserService();
    }

    //--------------------------------------------------------------------------------------
    private void GetDropdownValuesRestData(){

           /* String str_token = "Bearer " + str_AUTH_token;
            Log.e("str_token", str_token);
            Log.e("str_AUTH_token", str_AUTH_token);*/
    /*    User_Id request = new User_Id();
        request.setUser_ID("40");*/

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

                            int sizeState=class_locaitonData.getLst().get(i).getState().size();
                            for(int j=0;j<sizeState;j++){
                                Log.e("tag","state name=="+class_locaitonData.getLst().get(i).getState().get(j).getStateName());
                                String StateName = class_locaitonData.getLst().get(i).getState().get(j).getStateName();
                                String StateId = class_locaitonData.getLst().get(i).getState().get(j).getStateID();
                                DBCreate_StatedetailsRest_insert_2SQLiteDB(StateId,StateName,StateId,j);
                            }
                            int sizeDistrict=class_locaitonData.getLst().get(i).getDistrict().size();
                            for(int j=0;j<sizeDistrict;j++){
                                Log.e("tag","District name=="+class_locaitonData.getLst().get(i).getDistrict().get(j).getDistrictName());
                                String DistrictName = class_locaitonData.getLst().get(i).getDistrict().get(j).getDistrictName();
                                String DistrictId = class_locaitonData.getLst().get(i).getDistrict().get(j).getDistrictID();
                                String DistrictStateId = class_locaitonData.getLst().get(i).getDistrict().get(j).getStateID();
                                DBCreate_DistrictdetailsRest_insert_2SQLiteDB(DistrictId,DistrictName,"Y1",DistrictStateId,j);
                            }
                            Log.e("tag","size=="+class_locaitonData.getLst().get(i).getTaluka().size());
                            int sizeTaluka=class_locaitonData.getLst().get(i).getTaluka().size();
                            for(int j=0;j<sizeTaluka;j++){
                                Log.e("tag","Taluka name=="+class_locaitonData.getLst().get(i).getTaluka().get(j).getTalukaName());
                                String TalukaName = class_locaitonData.getLst().get(i).getTaluka().get(j).getTalukaName();
                                String TalukaId = class_locaitonData.getLst().get(i).getTaluka().get(j).getTalukaID();
                                String TalukaDistrictId = class_locaitonData.getLst().get(i).getTaluka().get(j).getDistrictID();
                                DBCreate_TalukdetailsRest_insert_2SQLiteDB(TalukaId,TalukaName,TalukaDistrictId,j);
                            }
                            int sizePanchayat=class_locaitonData.getLst().get(i).getPanchayat().size();
                            for(int j=0;j<sizePanchayat;j++){
                                Log.e("tag","Panchayat name=="+class_locaitonData.getLst().get(i).getPanchayat().get(j).getPanchayatName());
                                String PanchayatName = class_locaitonData.getLst().get(i).getPanchayat().get(j).getPanchayatName();
                                String PanchayatId = class_locaitonData.getLst().get(i).getPanchayat().get(j).getPanchayatID();
                                String PanchayatTalukaId = class_locaitonData.getLst().get(i).getPanchayat().get(j).getTalukaID();
                                DBCreate_GrampanchayatdetailsRest_insert_2SQLiteDB(PanchayatId,PanchayatName,PanchayatTalukaId,j);
                            }
                            int sizeVillage=class_locaitonData.getLst().get(i).getVillage().size();
                            for(int j=0;j<sizeVillage;j++){
                                Log.e("tag","Village name=="+class_locaitonData.getLst().get(i).getVillage().get(j).getVillageName());
                                String VillageName = class_locaitonData.getLst().get(i).getVillage().get(j).getVillageName();
                                String VillageId = class_locaitonData.getLst().get(i).getVillage().get(j).getVillageID();
                                String VillagePanchayatId = class_locaitonData.getLst().get(i).getVillage().get(j).getPanchayatID();
                                String VillageTalukId = class_locaitonData.getLst().get(i).getVillage().get(j).getTalukaID();
                                DBCreate_VillagedetailsRest_insert_2SQLiteDB(VillageName,VillageId,VillageTalukId,VillagePanchayatId,j);
                            }
                            int sizeYear=class_locaitonData.getLst().get(i).getYear().size();
                            for(int j=0;j<sizeYear;j++){
                                Log.e("tag","Year name=="+class_locaitonData.getLst().get(i).getYear().get(j).getAcademic_Name());
                                String YearName = class_locaitonData.getLst().get(i).getYear().get(j).getAcademic_Name();
                                String YearId = class_locaitonData.getLst().get(i).getYear().get(j).getAcademic_ID();
                                DBCreate_YeardetailsRest_insert_2SQLiteDB(YearId,YearName,j);
                            }
                        }

                    } else {
                        Toast.makeText(ViewFarmers_Activity.this, class_locaitonData.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("error message", error.getMsg());

                    Toast.makeText(ViewFarmers_Activity.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(ViewFarmers_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }
    private void GetFarmer_PondValuesRestData(){

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
                           /* Log.e("msg", class_loginresponse.getMessage());
                            Log.e("list", class_loginresponse.getList().get(i).getId());
                            Log.e("list", class_loginresponse.getList().get(i).getProgramCode());
                            Log.e("size", String.valueOf(class_loginresponse.getList().size()));*/


                           /* class_location_dataList.setState((class_locaitonData.getLst().get(i).getState()));
                            class_location_dataList.setDistrict(class_locaitonData.getLst().get(i).getDistrict());
                            class_location_dataList.setPanchayat(class_locaitonData.getLst().get(i).getPanchayat());
                            class_location_dataList.setVillage(class_locaitonData.getLst().get(i).getVillage());*/
                            Log.e("tag","farmer name=="+class_locaitonData.getLst().get(0).getFarmer().get(0).getFarmerFirstName());
                            //   location_dataLists[i] = class_locaitonData;
                            //  Log.e("spinner month", location_dataLists[i].getState().get(i).getStateName());
                            //Log.e("spinner getTotalIdeas", String.valueOf(arraymonthlist[i].getTotalIdeas()));

                            // classMonthCountLists_arr.add(new Class_MonthCountList(class_monthCountList.getId(),class_monthCountList.getMonthName(),class_monthCountList.getMonthCode(),class_monthCountList.getTotalIdeas()));
                            //CreateCardViewProgrammatically();

                        }
                    } else {
                        Toast.makeText(ViewFarmers_Activity.this, class_locaitonData.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("error message", error.getMsg());

                    Toast.makeText(ViewFarmers_Activity.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(ViewFarmers_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }

    //---------------------------------------------------------------------------------------

    //////////////////////////////23May2020/////////////////////////////////

    public void DBCreate_StatedetailsRest_insert_2SQLiteDB(String str_stateID, String str_statename, String str_yearid,int i) {
        SQLiteDatabase db_statelist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_statelist.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR);");


        if(i==0)
        {
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
        db_statelist_delete.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR);");
        Cursor cursor = db_statelist_delete.rawQuery("SELECT * FROM StateListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_statelist_delete.delete("StateListRest", null, null);

        }
        db_statelist_delete.close();
    }

    public void DBCreate_DistrictdetailsRest_insert_2SQLiteDB(String str_districtID, String str_districtname, String str_yearid, String str_stateid,int i) {
        SQLiteDatabase db_districtlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_districtlist.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");



        if(i==0)
        {
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


    public void DBCreate_TalukdetailsRest_insert_2SQLiteDB(String str_talukID, String str_talukname, String str_districtid,int i) {

        SQLiteDatabase db_taluklist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_taluklist.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");


        if(i==0)
        {
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

    public void DBCreate_VillagedetailsRest_insert_2SQLiteDB(String str_villageID, String str_village, String str_talukid,String str_village_panchayatid,int i)
    {
        SQLiteDatabase db_villagelist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_villagelist.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,PanchayatID VARCHAR);");


        if(i==0)
        {
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

    public void DBCreate_GrampanchayatdetailsRest_insert_2SQLiteDB(String str_grampanchayatID, String str_grampanchayat, String str_distid,int i) {

        SQLiteDatabase db_panchayatlist = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db_panchayatlist.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatListRest(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");


        if(i==0)
        {
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

    public void DBCreate_YeardetailsRest_insert_2SQLiteDB(String str_yearID, String str_yearname,int i)
    {
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

/////////////////////////////////////////////////////////////////////////////////////
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

                Intent i = new Intent(ViewFarmers_Activity.this, Activity_FarmerHomeScreen.class);
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

                if(isInternetPresent2)
                {
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
        Intent i = new Intent(ViewFarmers_Activity.this, Activity_FarmerHomeScreen.class);
        startActivity(i);
        finish();

    }
}
