package df.farmponds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import df.farmponds.Models.DefaultResponse;
import df.farmponds.Models.District;
import df.farmponds.Models.ErrorUtils;
import df.farmponds.Models.Farmer;
import df.farmponds.Models.Location_Data;
import df.farmponds.Models.Location_DataList;
import df.farmponds.Models.Panchayat;
import df.farmponds.Models.State;
import df.farmponds.Models.Taluka;
import df.farmponds.Models.UserData;
import df.farmponds.Models.UserDataList;
import df.farmponds.Models.Village;
import df.farmponds.Models.Year;
import df.farmponds.remote.Class_ApiUtils;
import df.farmponds.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFarmers_Activity extends AppCompatActivity {

    Class_InternetDectector internetDectector2,internetDectector3;
    Boolean isInternetPresent2 = false;

    Interface_userservice userService1;
    Location_DataList[] location_dataLists;
    Location_DataList class_location_dataList = new Location_DataList();

    UserDataList[] userDataLists;
    UserDataList class_userDatalist= new UserDataList();

    Spinner yearlist_SP, statelist_SP, districtlist_SP, taluklist_SP, villagelist_SP, grampanchayatlist_SP;
    LinearLayout spinnerlayout_ll;
    ImageButton search_ib, downarrow_ib, uparrow_ib;
    TextView viewspinner_tv;
    TableLayout tableLayout;

    EditText search_et;
    ListView farmer_listview;
    String str_return;
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
    Farmer[] arrayObj_Class_FarmerListDetails2,class_farmerlistdetails_arrayobj2;
    String selected_year, sp_stryear_ID, sp_strstate_ID, selected_district, selected_stateName, sp_strdistrict_ID, sp_strdistrict_state_ID, sp_strTaluk_ID, selected_taluk, sp_strVillage_ID, selected_village, sp_strgrampanchayat_ID, selected_grampanchayat;
    List<Year> yearList;
    int sel_yearsp=0,sel_statesp=0,sel_districtsp=0,sel_taluksp=0,sel_villagesp=0,sel_grampanchayatsp=0;

    private ArrayList<Farmer> originalViewFarmerList = null;
    private ArrayList<Farmer> ViewFarmerList_arraylist;
    FarmerListViewAdapter farmerListViewAdapter;
    ViewHolder holder;
    String str_selected_farmerID,str_selected_farmerID_forimagesaving,str_selected_farmerName;
    String str_farmerbase64;
    String str_imageurltobase64_farmerimage;
    FloatingActionButton addfarmerdetails_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_farmers);
        userService1 = Class_ApiUtils.getUserService();

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
        str_return="no";

        ViewFarmerList_arraylist = new ArrayList<Farmer>();
        farmerListViewAdapter = new FarmerListViewAdapter(ViewFarmers_Activity.this, ViewFarmerList_arraylist);

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

        yearlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_yearDetails = (Year) yearlist_SP.getSelectedItem();
                sp_stryear_ID = Obj_Class_yearDetails.getAcademic_ID().toString();
                selected_year = yearlist_SP.getSelectedItem().toString();
                int sel_yearsp_new = yearlist_SP.getSelectedItemPosition();
                //  farmerListViewAdapter.notifyDataSetChanged();

                if(sel_yearsp_new!=sel_yearsp)
                {
                    sel_yearsp=sel_yearsp_new;
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


               // Class_SaveSharedPreference.setPREF_stateposition(Activity_ViewFarmers.this,position);
                Update_districtid_spinner(sp_strstate_ID);
                if(sel_statesp_new!=sel_statesp)
                {
                    sel_statesp=sel_statesp_new;
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

               // Class_SaveSharedPreference.setPREF_districtposition(Activity_ViewFarmers.this,position);
                Update_TalukId_spinner(sp_strdistrict_ID);

                if(sel_districtsp_new!=sel_districtsp) {
                    sel_districtsp=sel_districtsp_new;
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

             //   Class_SaveSharedPreference.setPREF_talukposition(Activity_ViewFarmers.this,position);
                Update_GramPanchayatID_spinner(sp_strTaluk_ID);
                //Update_VillageId_spinner(sp_strgrampanchayat_ID);
                if(sel_taluksp_new!=sel_taluksp) {
                    sel_taluksp=sel_taluksp_new;
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


               // Class_SaveSharedPreference.setPREF_gramanchayatposition(Activity_ViewFarmers.this,position);

                Update_VillageId_spinner(sp_strgrampanchayat_ID,sp_strTaluk_ID);

                if(sel_grampanchayatsp_new!=sel_grampanchayatsp)
                {
                    sel_grampanchayatsp=sel_grampanchayatsp_new;
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

             //   Class_SaveSharedPreference.setPREF_villageposition(Activity_ViewFarmers.this,position);
                if(sel_villagesp_new!=sel_villagesp) {
                    sel_villagesp=sel_villagesp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();

                    // grampanchayatlist_SP.setSelection(0);
                }


                Log.e("yearselected",yearlist_SP.getSelectedItem().toString());


                if(statelist_SP.getSelectedItem().toString().equalsIgnoreCase("Select")
                        &&districtlist_SP.getSelectedItem().toString().equalsIgnoreCase("Select"))
                {
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();

                }else{
                    Log.e("yearid",sp_stryear_ID+"-"+sp_strstate_ID+"-"+sp_strdistrict_ID+"-"+sp_strTaluk_ID+"-"+sp_strVillage_ID+"-"+sp_strgrampanchayat_ID);

                    Update_ids_farmerlist_listview(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strgrampanchayat_ID);

                       }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addfarmerdetails_fab = (FloatingActionButton) findViewById(R.id.addfarmerdetails_fab);
        addfarmerdetails_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
                // Intent i = new Intent(getApplicationContext(),AddFarmer_Activity.class);
                Intent i = new Intent(getApplicationContext(),AddFarmer_Activity1.class);
                startActivity(i);
                finish();
            }
        });

        uploadfromDB_Yearlist();
        uploadfromDB_Statelist();
        uploadfromDB_Districtlist();
        uploadfromDB_Taluklist();
        uploadfromDB_Villagelist();
        uploadfromDB_Grampanchayatlist();
     //   uploadfromDB_Farmerlist();
    }


    public static class ViewHolder {


        public ImageView farmerimage_iv;
        TextView FarmerNamelabel_tv;
        TextView FarmerName_tv;
        TextView FarmerIDlabel_tv;
        TextView farmercode_tv;
        Button farmpond_bt;
        TextView farmerpondcount_tv;
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

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Farmer Obj_Class_farmerlistdetails = (Farmer) getItem(position);

//
            if (mDisplayedValues != null) {
              //  Log.e("listfarmername", mDisplayedValues.get(position).getFarmerFirstName());
                //Log.e("listfarmerid", Obj_Class_farmerlistdetails.getFarmerID());

                holder.FarmerName_tv.setText(mDisplayedValues.get(position).getFarmerFirstName());
                holder.farmercode_tv.setText(mDisplayedValues.get(position).getFarmerID());

                holder.farmerimage_iv.setTag(position);
                String str_fetched_imgfile = Obj_Class_farmerlistdetails.getFarmerPhoto();
            //    Log.e("Obj_ClFarmerimage()", Obj_Class_farmerlistdetails.getFarmerPhoto());
              //  Log.e("getLocalfarmerimage()", String.valueOf(Obj_Class_farmerlistdetails.getFarmerPhoto()));

                holder.farmerpondcount_tv.setText(mDisplayedValues.get(position).getFarmPond_Count());
//--------------------------------
           /*     if (Obj_Class_farmerlistdetails.getStr_base64() != null) {


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
                    }

                } else {*/
                    //    if (!Obj_Class_farmerlistdetails.getFarmerimage().equalsIgnoreCase("image/profileimg.png")) {


                    if (Obj_Class_farmerlistdetails.getFarmerPhoto() != null) {
                        if (Obj_Class_farmerlistdetails.getFarmerPhoto().toString().contains(".jpeg")
                                || Obj_Class_farmerlistdetails.getFarmerPhoto().toString().contains(".jpg")
                                || Obj_Class_farmerlistdetails.getFarmerPhoto().toString().contains(".png")) {
                        }
                    } else {
                        Log.e("farmerImage", "farmerimage null");
                    }
              //  }

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


            return convertView;

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

    }

    //--------------------------------------------------------------------------------------

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
                Log.e("tag","innerObj_Class_yearList"+innerObj_Class_yearList);

                Log.e("tag","arrayObj_Class_yearDetails2"+arrayObj_Class_yearDetails2[i]);
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_year.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_yearDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            yearlist_SP.setAdapter(dataAdapter);
            if(x>sel_yearsp) {
                yearlist_SP.setSelection(sel_yearsp);
            }
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
            if(x>sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }
        }

    }

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
            districtlist_SP.setAdapter(dataAdapter);
            if(x>sel_districtsp) {
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
            if(x>sel_districtsp) {
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
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_TalukListDetails2 = new Taluka[x];
        if (cursor1.moveToFirst()) {

            do {
                Taluka innerObj_Class_SandboxList = new Taluka();
                innerObj_Class_SandboxList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                if(cursor1.getString(cursor1.getColumnIndex("TalukName")).isEmpty())
                {
                    innerObj_Class_SandboxList.setTalukaName("Empty In DB");
                }else{
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
            if(x>sel_taluksp) {
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




        if(x>0) {
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




        if (x > 0)
        {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            taluklist_SP.setAdapter(dataAdapter);
            if(x>sel_taluksp) {
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
            villagelist_SP.setAdapter(dataAdapter);
            if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }
        }


    }

    public void Update_VillageId_spinner(String str_panchayatID,String str_talukid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        //  db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db1.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,PanchayatID VARCHAR);");
        //Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageListRest WHERE TalukID='" + str_talukid + "'AND PanchayatID='"+str_panchayatID+"'", null);
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
            if(x>sel_villagesp) {
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
            grampanchayatlist_SP.setAdapter(dataAdapter);
            if(x>sel_grampanchayatsp) {
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
            if(x>sel_grampanchayatsp) {
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

    public void uploadfromDB_Farmerlist()
    {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR);");



        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        ViewFarmerList_arraylist.clear();
        arrayObj_Class_FarmerListDetails2 = new Farmer[x];
        if (cursor1.moveToFirst()) {

            do {
                Farmer innerObj_Class_SandboxList = new Farmer();
               // innerObj_Class_SandboxList.setYearid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_YearID")));
                innerObj_Class_SandboxList.setStateID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_StateID")));
                innerObj_Class_SandboxList.setDistrictID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_DistrictID")));
                innerObj_Class_SandboxList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_TalukID")));
                innerObj_Class_SandboxList.setVillageID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_VillageID")));
                innerObj_Class_SandboxList.setPanchayatID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_GrampanchayatID")));
                innerObj_Class_SandboxList.setFarmerID(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerID")));//DispFarmerTable_Farmer_Code VARCHAR
                innerObj_Class_SandboxList.setFarmerFirstName(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName")));
                innerObj_Class_SandboxList.setFarmerPhoto(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage")));
               // innerObj_Class_SandboxList.setFarmerimage(cursor1.getString(cursor1.getColumnIndex("Farmpondcount")));
              //  innerObj_Class_SandboxList.setLocalfarmerimage(cursor1.getBlob(cursor1.getColumnIndex("LocalFarmerImg")));
              //  innerObj_Class_SandboxList.setStr_base64(cursor1.getString(cursor1.getColumnIndex("FarmerImageB64str_DB")));

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
                // String str_farmpondcount=cursor1.getString(cursor1.getColumnIndex("Farmpondcount"));
                byte[] str_LocalImg = cursor1.getBlob(cursor1.getColumnIndex("LocalFarmerImg"));

                Log.e("tag","str_LocalImg inner="+str_LocalImg);
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

                Log.e("str_FarmerName", str_FarmerName);

                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0)
        {

//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            cluster_new_sp.setAdapter(dataAdapter);
            //originalViewFarmerList.clear();
            //ViewFarmerList_arraylist.clear();


            originalViewFarmerList = new ArrayList<Farmer>();
            originalViewFarmerList.addAll(ViewFarmerList_arraylist);

            farmerListViewAdapter.notifyDataSetChanged();
            farmer_listview.setAdapter(farmerListViewAdapter);


//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_FarmerListDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);

            // CustomAdapter_new dataAdapter=new CustomAdapter_new();
            //farmer_listview.setAdapter(dataAdapter);
        }

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
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerListRest WHERE DispFarmerTable_YearID='" + str_yearid +"' AND DispFarmerTable_VillageID='" + str_villageid + "'", null);


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

                Log.e("tag","str_LocalImg oo="+str_LocalImg);

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

    private void GetDropdownValuesRestData(){

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
                                DBCreate_VillagedetailsRest_insert_2SQLiteDB(VillageId,VillageName,VillageTalukId,VillagePanchayatId,j);
                            }
                            int sizeYear=class_locaitonData.getLst().get(i).getYear().size();
                            for(int j=0;j<sizeYear;j++){
                                Log.e("tag","Year name=="+class_locaitonData.getLst().get(i).getYear().get(j).getAcademic_Name());
                                String YearName = class_locaitonData.getLst().get(i).getYear().get(j).getAcademic_Name();
                                String YearId = class_locaitonData.getLst().get(i).getYear().get(j).getAcademic_ID();
                                DBCreate_YeardetailsRest_insert_2SQLiteDB(YearId,YearName,j);
                            }
                            int sizeMachine=class_locaitonData.getLst().get(i).getClassMachineDetails().size();
                            for(int j=0;j<sizeMachine;j++){
                                Log.e("tag","Machine name=="+class_locaitonData.getLst().get(i).getClassMachineDetails().get(j).getMachine_Name());
                                String MachineName = class_locaitonData.getLst().get(i).getClassMachineDetails().get(j).getMachine_Name();
                                String MachineId = class_locaitonData.getLst().get(i).getClassMachineDetails().get(j).getMachine_ID();
                                DBCreate_MachineDetailsRest(MachineName,MachineId);
                            }
                        }

                        uploadfromDB_Yearlist();
                        uploadfromDB_Statelist();
                        uploadfromDB_Districtlist();
                        uploadfromDB_Taluklist();
                        uploadfromDB_Villagelist();
                        uploadfromDB_Grampanchayatlist();

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

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ViewFarmers_Activity.this);
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

                if (response.isSuccessful()) {
                    UserData class_userData = response.body();
                    Log.e("response.body", response.body().getLst().toString());
                    if (class_userData.getStatus().equals(true)) {
                        List<UserDataList> yearlist = response.body().getLst();
                        Log.e("programlist.size()", String.valueOf(yearlist.size()));

                        userDataLists = new UserDataList[yearlist.size()];

                        for (int i = 0; i < userDataLists.length; i++) {

                            String str_farmpondbaseimage_url = null,str_base64image = null, str_base64image1=null;

                            Log.e("status", String.valueOf(class_userData.getStatus()));
                            Log.e("tag","farmer name=="+class_userData.getLst().get(0).getFarmer().get(0).getFarmerFirstName());

                            class_userDatalist.setFarmer((class_userData.getLst().get(i).getFarmer()));
                            class_userDatalist.setPond(class_userData.getLst().get(i).getPond());
                            int sizeFarmer=class_userData.getLst().get(i).getFarmer().size();
                            for(int j=0;j<sizeFarmer;j++){
                                Log.e("tag","Farmer name=="+class_userData.getLst().get(i).getFarmer().get(j).getFarmerFirstName());
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
                                String farmerCode=class_userData.getLst().get(i).getFarmer().get(j).getFarmer_Code();
                                String farmpondcount=class_userData.getLst().get(i).getFarmer().get(j).getFarmPond_Count();
                                String yearID=class_userData.getLst().get(i).getFarmer().get(j).getAcademic_ID();

                                Log.e("tag","str_imageurl="+str_imageurl);


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
                                class_farmerlistdetails_arrayobj2=new Farmer[sizeFarmer];
                                if(str_imageurl==null||str_imageurl.equals("")) {
                                }else{
                                  //  str_imageurl = class_farmerlistdetails_arrayobj2[j].getFarmerPhoto();

                                //   String str_farmpondimageurl = str_imageurl;

                                  /* InputStream inputstream_obj = null;
                                    try {
                                        inputstream_obj = new URL("http://apps.dfindia.org/farmapi/image/5e677077b75fb.jpeg").openStream();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                    byte[] b = baos.toByteArray();
                                    str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

                                    str_imageurltobase64_farmerimage = str_base64image;

                                    str_farmerbase64 = str_base64image;
                                    Log.e("tag","str_farmerbase64="+str_farmerbase64);
*/
                                    Bitmap bm = BitmapFactory.decodeFile("http://apps.dfindia.org/farmapi/image/5e677077b75fb.jpeg");
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                                    byte[] b = baos.toByteArray();

                                    str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

                                    str_imageurltobase64_farmerimage = str_base64image;

                                    str_farmerbase64 = str_base64image;
                                }

                                DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(yearID,stateID,districtID,talukaID,villageID,panchayatID,farmerID,farmerCode,farmerFirstName,str_imageurl,farmpondcount,str_farmerbase64,farmerMiddleName,farmerLastName,farmerAge,farmerMobile,farmerIncome,farmerFamily,farmerIDType,farmerIDNumber,submittedDate,createdBy,createdDate,createdUser,responseoutput,responseAction);
                            }

                            int sizePond=class_userData.getLst().get(i).getPond().size();

                            for(int j=0;j<sizePond;j++){
                                String pondID=class_userData.getLst().get(i).getPond().get(j).getPondID();
                                String farmerID=class_userData.getLst().get(i).getPond().get(j).getFarmerID();
                                String academicID=class_userData.getLst().get(i).getPond().get(j).getAcademicID();
                                String machineID=class_userData.getLst().get(i).getPond().get(j).getMachineID();
                                String pondCode=class_userData.getLst().get(i).getPond().get(j).getPondCode();
                                String pondLatitude=class_userData.getLst().get(i).getPond().get(j).getPondLatitude();
                                String pondLongitude=class_userData.getLst().get(i).getPond().get(j).getPondLongitude();
                                String pondLength=class_userData.getLst().get(i).getPond().get(j).getPondLength();
                                String pondWidth=class_userData.getLst().get(i).getPond().get(j).getPondWidth();
                                String pondDepth=class_userData.getLst().get(i).getPond().get(j).getPondDepth();
                                String pondStart=class_userData.getLst().get(i).getPond().get(j).getPondStart();
                                String pondEnd=class_userData.getLst().get(i).getPond().get(j).getPondEnd();
                                String pondDays=class_userData.getLst().get(i).getPond().get(j).getPondDays();
                                String pondCost=class_userData.getLst().get(i).getPond().get(j).getPondCost();
                                String pondImage1=class_userData.getLst().get(i).getPond().get(j).getPondImage1();
                                String pondImage2=class_userData.getLst().get(i).getPond().get(j).getPondImage2();
                                String pondImage3=class_userData.getLst().get(i).getPond().get(j).getPondImage3();
                                String pondStatus=class_userData.getLst().get(i).getPond().get(j).getPondStatus();
                                String submittedDate=class_userData.getLst().get(i).getPond().get(j).getSubmittedDate();
                                String submittedBy=class_userData.getLst().get(i).getPond().get(j).getSubmittedBy();
                                String createdDate=class_userData.getLst().get(i).getPond().get(j).getCreatedDate();
                                String createdBy=class_userData.getLst().get(i).getPond().get(j).getCreatedBy();
                                String pondTempID=class_userData.getLst().get(i).getPond().get(j).getPondTempID();
                                String responseOutput=class_userData.getLst().get(i).getPond().get(j).getResponse();
                                String createdUser=class_userData.getLst().get(i).getPond().get(j).getCreatedUser();
                                String submittedUser=class_userData.getLst().get(i).getPond().get(j).getSubmittedUser();
                                String farmer_First_Name=class_userData.getLst().get(i).getPond().get(j).getFarmerFirstName();
                                String farmer_Middle_Name=class_userData.getLst().get(i).getPond().get(j).getFarmerMiddleName();
                                String farmer_Last_Name=class_userData.getLst().get(i).getPond().get(j).getSubmittedUser();

                                String Pond_Land_Acre=class_userData.getLst().get(i).getPond().get(j).getPondLandAcre();
                                String Pond_Land_Gunta=class_userData.getLst().get(i).getPond().get(j).getPondLandGunta();
                                String Approval_Status=class_userData.getLst().get(i).getPond().get(j).getApprovalStatus();
                                String Response_Action=class_userData.getLst().get(i).getPond().get(j).getResponseAction();


                               String str_imageurl = class_userData.getLst().get(i).getPond().get(j).getPondImage1();

                                String str_farmpondimageurl = str_imageurl;
                                Log.e("url1","str_farmpondimageurl="+ str_farmpondimageurl);

                                if(str_farmpondimageurl==null){

                                }else {

                                   /* InputStream inputstream_obj = null;
                                    try {
                                        inputstream_obj = new URL(str_farmpondimageurl).openStream();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                    byte[] b = baos.toByteArray();
                                    str_base64image1 = Base64.encodeToString(b, Base64.DEFAULT);*/

//                                    DBCreate_FarmpondRest_details_2SQLiteDB(pondID, farmerID, academicID, machineID, pondCode, pondLatitude, pondLongitude, pondLength, pondWidth,
//                                            pondDepth, pondStart, pondEnd, pondDays, pondCost, pondImage1, pondImage2, pondImage3, pondStatus, submittedDate, submittedBy, createdDate,
//                                            createdBy, pondTempID, responseOutput, createdUser, submittedUser);
                                    DBCreate_FarmpondsRest_details_2SQLiteDB(farmerID, farmer_First_Name, pondID, pondWidth, pondWidth,
                                            pondDepth, pondImage1, str_base64image1, pondImage2, str_base64image1, pondImage3, str_base64image1,
                                            pondDays, createdDate, pondEnd, pondCost, machineID,
                                            pondCode, pondStart, "farmpond_remarks", "farmpond_amtcollected", "str_farmpond_status",
                                            farmer_Middle_Name, farmer_Last_Name, "", "", "", Approval_Status,
                                            "str_approvalremarks", "str_approvedby", "str_approveddate", "str_donorname", pondLatitude, pondLongitude,
                                            Pond_Land_Acre, Pond_Land_Gunta, "str_crop_beforepond", "str_crop_afterpond");
                                }
                            }
                        }

                      //  uploadfromDB_Farmerlist();
                        progressDoalog.dismiss();

                    } else {
                        progressDoalog.dismiss();

                        Toast.makeText(ViewFarmers_Activity.this, class_userData.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDoalog.dismiss();

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

    public void  DBCreate_MachineDetailsRest(String str_machinename,String str_machineid)
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineIDDB VARCHAR);");
            String SQLiteQuery = "INSERT INTO MachineDetails_fromServerRest (MachineNameDB,MachineIDDB)" +
                    " VALUES ('"+str_machinename+"','"+str_machineid+"');";

            db1.execSQL(SQLiteQuery);
        db1.close();

    }
    public void deleteMachineRestTable_B4insertion() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS MachineDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,MachineNameDB VARCHAR,MachineIDDB VARCHAR);");


        Cursor cursor = db1 .rawQuery("SELECT * FROM MachineDetails_fromServerRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db1 .delete("MachineDetails_fromServerRest", null, null);

        }
        db1 .close();
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

        Cursor cursor = db_viewfarmerlist .rawQuery("SELECT * FROM ViewFarmerListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_viewfarmerlist .delete("ViewFarmerListRest", null, null);

        }
        db_viewfarmerlist .close();
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

        Cursor cursor = db_viewfarmpondlist .rawQuery("SELECT * FROM FarmPondDetailsRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_viewfarmpondlist.delete("FarmPondDetailsRest", null, null);

        }
        db_viewfarmpondlist.close();
    }

    public void DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(String str_yearID, String str_stateID, String str_districtID,
                                                                String str_talukid, String str_villageid, String str_grampanchayatid,
                                                                String str_farmerid, String str_farmercode, String str_farmername,
                                                                String str_farmerimage,String farmpondcount,String str_imagebase64,
                                                                String str_mname,String str_lname,String str_age,String str_cellno,
                                                                String str_income,String str_member,String str_idprooftype,String str_idproofno,String Submitted_Date,String Created_By,String Created_Date,String Created_User,String ResponseOutput,String Response_Action) {


        String str_UploadedStatusFarmerprofile="10";  //uploaded

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
                " VALUES ('" + str_yearID + "','" + str_stateID + "','" + str_districtID + "','" + str_talukid + "','" + str_villageid+"','"
                + str_grampanchayatid + "','" + str_farmerid + "','" + str_farmercode + "','" + str_farmername + "','"+str_mname+"'," +
                "'"+str_lname+"','"+str_age+"','"+str_cellno+"','"+str_income+"','"+str_member+"','"+str_idprooftype+"','"+str_idproofno+"'," +
                "'"+str_UploadedStatusFarmerprofile+"','"+str_imagebase64+"','"+ str_farmerimage +"','" + farmpondcount +"','"
                + Submitted_Date +"','" + Created_By +"','" + Created_Date +"','" + Created_User +"','" + ResponseOutput + "','" + Response_Action +"');";

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

    public void  DBCreate_FarmpondsRest_details_2SQLiteDB(String str_farmerid, String str_farmername,String str_farmpond_id,String str_width,
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



        Log.e("remarks",str_farmpond_remarks);
        Log.e("amt",str_farmpond_amtcollected);
        Log.e("status",str_farmpond_status);


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

        db1.execSQL(SQLiteQuery);
        db1.close();

    }
    public void DBCreate_FarmpondRest_details_2SQLiteDB(String pondID,String farmerID,String academicID,String machineID,String pondCode,String pondLatitude,String pondLongitude,String pondLength,String pondWidth,
                                                        String pondDepth,String pondStart,String pondEnd,String pondDays,String pondCost,String pondImage1,String pondImage2,String pondImage3,String pondStatus,String submittedDate,String submittedBy,String createdDate,
                                                        String createdBy,String pondTempID,String responseOutput,String createdUser,String submittedUser){

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
                " VALUES ('" + pondID + "','" + farmerID + "','" + academicID + "','" + machineID + "','" + pondCode+"','"
                + pondLatitude + "','" + pondLongitude + "','" + pondLength + "','" + pondWidth + "','"+pondDepth+"'," +
                "'"+pondStart+"','"+pondEnd+"','"+pondDays+"','"+pondCost+"','"+pondImage1+"','"+pondImage2+"','"+pondImage3+"'," +
                "'"+pondStatus+"','"+submittedDate+"','"+ submittedBy +"','" + createdDate +"','"
                + createdBy +"','" + pondTempID +"','" + responseOutput  +"','" + createdUser + "','" + submittedUser +"');";

        db_viewfarmpondlist.execSQL(SQLiteQuery);

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
                    deleteMachineRestTable_B4insertion();
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
