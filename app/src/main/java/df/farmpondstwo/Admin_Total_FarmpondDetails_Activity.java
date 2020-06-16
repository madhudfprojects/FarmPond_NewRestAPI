package df.farmpondstwo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import df.farmpondstwo.Models.AddFarmerResList;
import df.farmpondstwo.Models.AddFarmerResponse;
import df.farmpondstwo.Models.AdminEmpTotalPondCountList;
import df.farmpondstwo.Models.AdminEmpoyeeTotalPondCount;
import df.farmpondstwo.Models.DefaultResponse;
import df.farmpondstwo.Models.ErrorUtils;
import df.farmpondstwo.Models.NormalLogin_Response;
import df.farmpondstwo.remote.Class_ApiUtils;
import df.farmpondstwo.remote.Interface_userservice;
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

    String str_farmerID,str_employee_id;

    AdminEmpTotalPondCountList[] class_manageremployeeisecount_array_obj;
    AdminEmpTotalPondCountList class_manageremployeeisecount_obj;
    private ListView employeecount_listview;
    String str_totalfarmpondcount,str_todaysdate,str_todayTotalCount;
    TextView total_farmpond_allstate_tv,todaysdate_tv,todaysdate_tv1;
    Interface_userservice userService1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_total_farmpond_details);


        toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Submitted Ponds");
        getSupportActionBar().setTitle("");

        userService1 = Class_ApiUtils.getUserService();
        total_farmpond_allstate_tv=(TextView)findViewById(R.id.total_farmpond_allstate_tv);
        todaysdate_tv1=(TextView)findViewById(R.id.todaysdate_tv1);

        todaysdate_tv=(TextView)findViewById(R.id.todaysdate_tv);
        employeecount_listview = (ListView) findViewById(R.id.employeecount_listview);

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_employee_id=sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();


        str_totalfarmpondcount=str_todaysdate="Not Available";
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent)
        {
            GetEmpWiseCount();
          //  AsyncTask_fetch_empwise_count();
        }

    }//end of class

    private void GetEmpWiseCount(){
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
        call.enqueue(new Callback<AdminEmpoyeeTotalPondCount>()
        {
            @Override
            public void onResponse(Call<AdminEmpoyeeTotalPondCount> call, Response<AdminEmpoyeeTotalPondCount> response)
            {
                Log.e("response",response.toString());
                Log.e("TAG", "response: "+new Gson().toJson(response) );
                Log.e("response body", String.valueOf(response.body()));

                if(response.isSuccessful())
                {
                    //  progressDoalog.dismiss();
                    AdminEmpoyeeTotalPondCount  class_loginresponse = response.body();
                    Log.e("tag","res=="+class_loginresponse.toString());
                    if(class_loginresponse.getStatus()) {

                        List<AdminEmpTotalPondCountList> addFarmerResList = response.body().getEmployeeList();
                        Log.e("tag","addFarmerResList NAme="+addFarmerResList.get(0).getEmployeeName());
                        Log.e("tag","addFarmerResList farmerID="+addFarmerResList.get(0).getEmployeeCount());

                        str_totalfarmpondcount=class_loginresponse.getTotalCount();
                        str_todaysdate=class_loginresponse.getTodaysDate();
                        str_todayTotalCount=class_loginresponse.getTotalTodaysCount();

                      //  JSONArray response_jsonarray = jsonObject.getJSONArray("Employees_wise_details");

                    //    Log.e("employeewise", String.valueOf(response_jsonarray.length()));


                        int int_jsonarraylength=addFarmerResList.size();// response_jsonarray.length();

                        class_manageremployeeisecount_array_obj = new AdminEmpTotalPondCountList[int_jsonarraylength];


                        for(int i=0;i<int_jsonarraylength;i++)
                        {
                          //  AdminEmpTotalPondCountList class_manageremployeeisecount__innerobj = new Gson().fromJson(String.valueOf(addFarmerResList.get(i).toString()), Class_ManagerEmployeeWiseCount.class);
                            AdminEmpTotalPondCountList adminEmpTotalPondCountList=new AdminEmpTotalPondCountList(addFarmerResList.get(i).getEmployeeName(),addFarmerResList.get(i).getEmployeeCount(),addFarmerResList.get(i).getTodaysCount());
                            class_manageremployeeisecount_array_obj[i]=adminEmpTotalPondCountList;

                        }

                        if (class_manageremployeeisecount_array_obj != null)
                        {
                            CustomAdapter_manageremployeeisecount adapter = new CustomAdapter_manageremployeeisecount();
                            employeecount_listview.setAdapter(adapter);

                            int y = class_manageremployeeisecount_array_obj.length;

                            Log.e("length adapter", ""+y);
                        } else {
                            Log.d("length adapter", "zero");
                        }

                        progressDoalog.dismiss();

                    }else{
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
            public void onFailure(Call call, Throwable t)
            {
                Log.e("TAG", "onFailure: "+t.toString() );

                Log.e("tag","Error:"+t.getMessage());
                Toast.makeText(Admin_Total_FarmpondDetails_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void AsyncTask_fetch_empwise_count()
    {

        final ProgressDialog pdLoading = new ProgressDialog(Admin_Total_FarmpondDetails_Activity.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String str_fetchfarmponddetails_url = Class_URL.URL_managerlogin_empwise_count.toString().trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        pdLoading.dismiss();

                        Log.e("volley mngempcount resp",response);//volley response: {"statusMessage":"success"}
                        parse_emplyoeewise_count(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading.dismiss();
                        Toast.makeText(Admin_Total_FarmpondDetails_Activity.this,"WS:"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {


                Map<String,String> params = new HashMap<String, String>();




                try{
                    params.put("employee_id","6");//str_employee_id

                }
                catch (Exception e)
                {
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
        Log.e("request",stringRequest.toString());
        requestQueue.add(stringRequest);
    }

    public void parse_emplyoeewise_count(String response)
    {

        try {
            JSONObject jsonObject = new JSONObject(response);

            //"StatusMessage": "Success"
            if (jsonObject.getString("Status Message").equalsIgnoreCase("Success"))
            //if (2>1)
            {
               // Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();

                str_totalfarmpondcount=jsonObject.getString("farmpond_Total_Count");
                str_todaysdate=jsonObject.getString("Today_date");


                JSONArray response_jsonarray = jsonObject.getJSONArray("Employees_wise_details");

                Log.e("employeewise", String.valueOf(response_jsonarray.length()));


                int int_jsonarraylength=response_jsonarray.length();

                class_manageremployeeisecount_array_obj = new AdminEmpTotalPondCountList[int_jsonarraylength];


                for(int i=0;i<int_jsonarraylength;i++)
                {
                  //  Class_ManagerEmployeeWiseCount class_manageremployeeisecount__innerobj = new Gson().fromJson(String.valueOf(response_jsonarray.get(i).toString()), Class_ManagerEmployeeWiseCount.class);
                    AdminEmpTotalPondCountList adminEmpTotalPondCountList=new AdminEmpTotalPondCountList();
                    class_manageremployeeisecount_array_obj[i]=adminEmpTotalPondCountList;

                }

                if (class_manageremployeeisecount_array_obj != null)
                {
                    CustomAdapter_manageremployeeisecount adapter = new CustomAdapter_manageremployeeisecount();
                    employeecount_listview.setAdapter(adapter);

                    int y = class_manageremployeeisecount_array_obj.length;

                    Log.e("length adapter", ""+y);
                } else {
                    Log.d("length adapter", "zero");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error ponds",e.toString());
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
        public int getCount()
        {

            String x = Integer.toString(class_manageremployeeisecount_array_obj.length);
            System.out.println("class_farmponddetails_offline_array_obj.length" + x);
            return class_manageremployeeisecount_array_obj.length;
        }

        @Override
        public Object getItem(int position)
        {
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
        public View getView(int position, View convertView1, ViewGroup parent)
        {

            final Holder_offline holder;

            Log.d("CustomAdapter", "position: " + position);

            if (convertView1 == null)
            {
                holder = new Holder_offline();
                convertView1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_row_item_employeecount, parent, false);
                total_farmpond_allstate_tv.setText(str_totalfarmpondcount);
                todaysdate_tv1.setText(str_todayTotalCount);
                String str_submitted="submitted "+str_todaysdate;
                Log.e("tag","total count:"+str_totalfarmpondcount+"todaytotalcount:"+str_todaysdate);
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

            int int_total=0;
            if (class_manageremployeeisecount_obj != null)
            {
                if(2>1)
                {
                    Log.e("employeename",class_manageremployeeisecount_obj.getEmployeeName().toString());
                    holder.holder_employeename.setText(class_manageremployeeisecount_obj.getEmployeeName());
                    holder.holder_totalfarmpond_added.setText(class_manageremployeeisecount_obj.getEmployeeCount());
                    holder.holder_todayfarmpond_added.setText(class_manageremployeeisecount_obj.getTodaysCount());

                    int_total= (int_total+Integer.parseInt(class_manageremployeeisecount_obj.getTodaysCount()));

                    Log.e("total", String.valueOf(int_total));

                    holder.holder_todaysdate.setText(str_todaysdate);
                }
                else{

                }


            }// end if 1

            return convertView1;

        }//End of custom getView
    }//End of CustomAdapter




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
