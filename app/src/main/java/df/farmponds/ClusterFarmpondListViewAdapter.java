package df.farmponds;

import android.app.Activity;
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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import df.farmponds.Models.ClusterFarmpond;
import df.farmponds.Models.ClusterFarmpondList;
import df.farmponds.Models.ClusterSummaryList;
import df.farmponds.Models.DefaultResponse;
import df.farmponds.Models.ErrorUtils;
import df.farmponds.remote.Class_ApiUtils;
import df.farmponds.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ClusterFarmpondListViewAdapter extends BaseAdapter
{
    public ArrayList<ClusterFarmpondList> feesPaidList;
    Activity activity;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    Interface_userservice userService1;

    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    SharedPreferences sharedpreferencebook_usercredential_Obj;


    String str_loginuserId,str_employeerole;

    String str_pondid,str_cluster_comments;
    String str_latitude,str_longitude;



    public ClusterFarmpondListViewAdapter(Activity activity, ArrayList<ClusterFarmpondList> feesPaidList) {
        super();
        this.activity = activity;
        this.feesPaidList = feesPaidList;
    }

    @Override
    public int getCount() {
        //return projList.size();
        return feesPaidList.size();
    }

    @Override
    public ClusterFarmpondList getItem(int position) {

        //return projList.get(position);
        return feesPaidList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {

        TextView yearName,stateName,districtName,talukaName,villageName,submitterName;
        TextView FName,MobileNo,pondCode,pondSize,startDate,endDate,latitude,longitude,
                amount,no_ofDays,location_Status,approval_Status,pondid_tv;
        ImageView pond_image1_iv,pond_image2_iv,pond_image3_iv;
        ImageView maplocationfinal_iv;
        LinearLayout approve_reject_ll,clustercomments_ll;
        Button cancel_bt,reject_bt,approve_bt;
        TextView clusterlatitude_tv,clusterlongitude_tv;
        EditText clustercomments_et;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();
        String str_base64image1 = null,str_base64image2 = null,str_base64image3 = null;
        byte[] imageBytes;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_cluster_farmpond_list, null);
            holder = new ViewHolder();

            holder.yearName=(TextView) convertView.findViewById(R.id.yearName);
            holder.stateName=(TextView) convertView.findViewById(R.id.stateName);
            holder.districtName=(TextView) convertView.findViewById(R.id.districtName);
            holder.talukaName=(TextView) convertView.findViewById(R.id.talukaName);
            holder.villageName=(TextView) convertView.findViewById(R.id.villageName);
            holder.submitterName=(TextView) convertView.findViewById(R.id.submitterName);
            /*holder.FName=(TextView) convertView.findViewById(R.id.FName);
            holder.MobileNo=(TextView) convertView.findViewById(R.id.MobileNo);*/
            holder.pondCode=(TextView) convertView.findViewById(R.id.pondCode);
            holder.pondSize=(TextView) convertView.findViewById(R.id.pondSize);
            holder.startDate=(TextView) convertView.findViewById(R.id.startDate);
            holder.endDate=(TextView) convertView.findViewById(R.id.endDate);
            holder.clusterlatitude_tv=(TextView)convertView.findViewById(R.id.clusterlatitude_tv);
            holder.clusterlongitude_tv=(TextView)convertView.findViewById(R.id.clusterlongitude_tv);
           /* holder.latitude=(TextView) convertView.findViewById(R.id.latitude);
            holder.longitude=(TextView) convertView.findViewById(R.id.longitude);*/
            holder.amount=(TextView) convertView.findViewById(R.id.amount);
            holder.no_ofDays=(TextView) convertView.findViewById(R.id.no_ofDays);
            holder.location_Status=(TextView) convertView.findViewById(R.id.location_Status);
            holder.approval_Status=(TextView) convertView.findViewById(R.id.approval_Status);
            holder.pond_image1_iv=(ImageView) convertView.findViewById(R.id.pond_image1_iv);
            holder.pond_image2_iv=(ImageView) convertView.findViewById(R.id.pond_image2_iv);
            holder.pond_image3_iv=(ImageView) convertView.findViewById(R.id.pond_image3_iv);
            holder.maplocationfinal_iv=(ImageView)convertView.findViewById(R.id.maplocationfinal_iv);
            holder.cancel_bt=(Button)convertView.findViewById(R.id.cancel_bt);
            holder.reject_bt=(Button)convertView.findViewById(R.id.reject_bt);
            holder.approve_bt=(Button)convertView.findViewById(R.id.approve_bt);
            holder.clustercomments_et=(EditText)convertView.findViewById(R.id.clustercomments_et);
            holder.pondid_tv=(TextView)convertView.findViewById(R.id.pondid_tv);

                    holder.approve_reject_ll=(LinearLayout)convertView.findViewById(R.id.approve_reject_ll);
            holder.clustercomments_ll=(LinearLayout)convertView.findViewById(R.id.clustercomments_ll);

            //maplocation_ll
            //


                    convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ClusterFarmpondList item = feesPaidList.get(position);


        sharedpreferencebook_usercredential_Obj = activity.getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();
        Log.e("tag", "str_loginuserId=" + str_loginuserId);

        str_employeerole=sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeecategory, "").trim();

        if(item.getEmployeeName()!=null) {
            holder.submitterName.setText(item.getEmployeeName());


        }
        if(item.getYearName()!=null) {
            holder.yearName.setText(item.getYearName());
        }

        if(item.getStateName()!=null) {
            holder.stateName.setText(item.getStateName());
        }

        if(item.getTalukaName()!=null) {
            holder.talukaName.setText(item.getTalukaName().toString());
        }
        if(item.getDistrictName()!=null) {
            holder.districtName.setText(item.getDistrictName().toString());
        }
        if(item.getVillageName()!=null) {
            holder.villageName.setText(item.getVillageName());
        }

       /* if(item.getFarmerName()!=null) {
            holder.FName.setText(item.getFarmerName().toString());
        }
        if(item.getFarmerMobile()!=null) {
            holder.MobileNo.setText(item.getFarmerMobile().toString());
        }*/
        if(item.getPondCode()!=null) {
            holder.pondCode.setText(item.getPondCode().toString());
        }
        if(item.getPondSize()!=null) {
            holder.pondSize.setText(item.getPondSize().toString());
        }
        if(item.getConstructionStart()!=null) {
            holder.startDate.setText(item.getConstructionStart().toString());
        }
        if(item.getConstructionEnd()!=null) {
            holder.endDate.setText(item.getConstructionEnd().toString());
        }
        if(item.getCollectedAmount()!=null&&item.getConstructionCost()!=null) {
            String amt=item.getCollectedAmount()+"/"+item.getConstructionCost();
            holder.amount.setText(amt);
        }
        if(item.getApprovalStatus()!=null)
        {
            holder.approval_Status.setText(item.getApprovalStatus().toString());

            if(item.getApprovalStatus().equalsIgnoreCase("Waiting for Approval"))
            {
                holder.approval_Status.setBackgroundResource(R.color.yellow_color);
            }
            if(item.getApprovalStatus().equalsIgnoreCase("Rejected"))
            {
                holder.approval_Status.setBackgroundResource(R.color.color_red);
            }
            if(item.getApprovalStatus().equalsIgnoreCase("Approved"))
            {
                holder.approval_Status.setBackgroundResource(R.color.dark_green);
            }

            if(item.getApprovalStatus().equalsIgnoreCase("Waiting for Approval"))
            {
                holder.approve_reject_ll.setVisibility(View.VISIBLE);
                holder.clustercomments_ll.setVisibility(View.VISIBLE);
            }else{
                holder.approve_reject_ll.setVisibility(View.GONE);
                holder.clustercomments_ll.setVisibility(View.GONE);
            }
        }


        if(str_employeerole.equalsIgnoreCase("Admin"))
        {
            holder.approve_reject_ll.setVisibility(View.GONE);
            holder.clustercomments_ll.setVisibility(View.GONE);
        }
        if(item.getLocationStatus()!=null) {
            holder.location_Status.setText(item.getLocationStatus().toString());

        }
        if(item.getPondLatitude()!=null) {
            holder.clusterlatitude_tv.setText(item.getPondLatitude().toString());
        }
        if(item.getPondLongitude()!=null) {
            holder.clusterlongitude_tv.setText(item.getPondLongitude().toString());
        }

        if(item.getPondID()!=null)
        {
            holder.pondid_tv.setText(item.getPondID());
        }

        str_base64image1=item.getPondImageLink1();
        if (item.getPondImageLink1() != null){
            imageBytes = Base64.decode(str_base64image1, Base64.DEFAULT);
            Bitmap bmp_decodedImage1 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.pond_image1_iv.setImageBitmap(bmp_decodedImage1);
        }
        str_base64image2=item.getPondImageLink2();
        if (item.getPondImageLink2() != null){
            imageBytes = Base64.decode(str_base64image2, Base64.DEFAULT);
            Bitmap bmp_decodedImage1 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.pond_image2_iv.setImageBitmap(bmp_decodedImage1);
        }
        str_base64image3=item.getPondImageLink3();
        if (item.getPondImageLink3() != null){
            imageBytes = Base64.decode(str_base64image3, Base64.DEFAULT);
            Bitmap bmp_decodedImage1 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.pond_image3_iv.setImageBitmap(bmp_decodedImage1);
        }
       // Log.e("tag","YearId="+holder.mYearId.getText().toString()+"mEmpId="+holder.mEmpId.getText().toString());

       /* holder.mAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmerListActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("YearId",holder.mYearId.getText().toString());
                i.putExtra("Type","All");
                v.getContext().startActivity(i);
            }
        });
        holder.mPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmerListActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("YearId",holder.mYearId.getText().toString());
                i.putExtra("Type","Pending");
                v.getContext().startActivity(i);
            }
        });
        holder.mProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmerListActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("YearId",holder.mYearId.getText().toString());
                i.putExtra("Type","Progress");
                v.getContext().startActivity(i);
            }
        });
        holder.mApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmerListActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("YearId",holder.mYearId.getText().toString());
                i.putExtra("Type","Approved");
                v.getContext().startActivity(i);
            }
        });
        holder.mRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmerListActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("YearId",holder.mYearId.getText().toString());
                i.putExtra("Type","Rejected Farmers");
                v.getContext().startActivity(i);
            }
        });*/





        /*holder.cancel_bt=(Button)convertView.findViewById(R.id.cancel_bt);
        holder.reject_bt=(Button)convertView.findViewById(R.id.reject_bt);
        holder.approve_bt=(Button)convertView.findViewById(R.id.approve_bt);*/


        holder.maplocationfinal_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                if(item.getPondLatitude()!=null) {
                    str_latitude=holder.clusterlatitude_tv.getText().toString();
                }
                if(item.getPondLongitude()!=null) {
                    str_longitude=holder.clusterlongitude_tv.getText().toString();
                }
                internetDectector = new Class_InternetDectector(activity.getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (isInternetPresent)
                {
                    Intent i = new Intent(activity,Cluster_MapsActivity.class);
                    i.putExtra("latitude", str_latitude);
                    i.putExtra("longitude", str_longitude);
                    activity.startActivity(i);

                }else{
                    Toast.makeText(activity.getApplicationContext(), "Connect to Internet", Toast.LENGTH_LONG).show();
                }
            }
        });


        holder.cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                activity.finish();
            }
        });

        holder.approve_bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*Post_ActionPondApproval
                        Created_By
                Pond_ID
                        Approval_Status
                Approval_Remarks*/

                 str_pondid=holder.pondid_tv.getText().toString();
                str_cluster_comments=holder.clustercomments_et.getText().toString();

                internetDectector = new Class_InternetDectector(activity.getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (isInternetPresent)
                {
                    Log.e("parameter","str_loginuserId:"+str_loginuserId+"/str_pondid:"+str_pondid
                    +"/str_cluster_comments:"+str_cluster_comments);

                    //Toast.makeText(activity.getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

                    if(str_cluster_comments.trim().length()>0)
                    {
                        if(str_cluster_comments.trim().length()>7)
                        {

                            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                            dialog.setCancelable(false);
                            dialog.setTitle(R.string.app_name);
                            dialog.setMessage("Are you sure want to Approve");

                            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    post_actionpond_rejectapprove(str_loginuserId, str_pondid, "Approved",str_cluster_comments );
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


                             //post_actionpond_rejectapprove(str_loginuserId, str_pondid, "Approved",str_cluster_comments );
                        }else{
                            holder.clustercomments_et.setError("Minimum 10 characters");
                            holder.clustercomments_et.requestFocus();
                        }
                    }else{
                        holder.clustercomments_et.setError("Enter Comments");
                        holder.clustercomments_et.requestFocus();
                    }
                }else{
                    Toast.makeText(activity.getApplicationContext(), "Connect to Internet", Toast.LENGTH_LONG).show();
                }

            }
        });



        holder.reject_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str_pondid=holder.pondid_tv.getText().toString();
                str_cluster_comments=holder.clustercomments_et.getText().toString();

                internetDectector = new Class_InternetDectector(activity.getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (isInternetPresent)
                {
                    {
                        Log.e("parameter","str_loginuserId:"+str_loginuserId+"/str_pondid:"+str_pondid
                                +"/str_cluster_comments:"+str_cluster_comments);

                        //Toast.makeText(activity.getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

                        if(str_cluster_comments.trim().length()>0)
                        {
                            if(str_cluster_comments.trim().length()>7)
                            {

                                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                                dialog.setCancelable(false);
                                dialog.setTitle(R.string.app_name);
                                dialog.setMessage("Are you sure want to Reject");

                                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        post_actionpond_rejectapprove(str_loginuserId, str_pondid, "Rejected",str_cluster_comments );
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


                                //post_actionpond_rejectapprove(str_loginuserId, str_pondid, "Approved",str_cluster_comments );
                            }else{
                                holder.clustercomments_et.setError("Minimum 10 characters");
                                holder.clustercomments_et.requestFocus();
                            }
                        }else{
                            holder.clustercomments_et.setError("Enter Comments");
                            holder.clustercomments_et.requestFocus();
                        }
                    }


                }


            }
        });





        return convertView;
    }


    /*public void filter(String charText, ArrayList<ClusterFarmpondList> feesList, String selectedCollegeName) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.feesPaidList.clear();

        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.feesPaidList.addAll(feesList);
                } else {
                    for (ClusterFarmpondList wp : feesList) {

                        if(selectedCollegeName == null) {
                            if ((wp.getEmployeeName()!=null && wp.getEmployeeName().toLowerCase().contains(charText.toLowerCase())) || ( wp.getDataAll()!=null && wp.getDataAll().toLowerCase().contains(charText.toLowerCase())) || ( wp.getDataProcess()!=null && wp.getDataProcess().toLowerCase().contains(charText.toLowerCase())) || ( wp.getDataRejected()!=null && wp.getDataRejected().toLowerCase().contains(charText.toLowerCase()))) {
                                this.feesPaidList.add(wp);
                            }
                        }else{
                            if ((wp.getEmployeeName()!=null && wp.getEmployeeName().equals(selectedCollegeName)) && ((wp.getEmployeeName()!=null && wp.getEmployeeName().toLowerCase().contains(charText.toLowerCase())) || (wp.getDataAll()!=null && wp.getDataAll().toLowerCase().contains(charText.toLowerCase())) || (wp.getDataProcess()!=null && wp.getDataProcess().toLowerCase().contains(charText.toLowerCase())) || (wp.getDataRejected()!=null && wp.getDataRejected().toLowerCase().contains(charText.toLowerCase())))) {
                                this.feesPaidList.add(wp);
                            }
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }*/


    private void post_actionpond_rejectapprove(String str_loginuserId,String str_pondid,String str_approvereject_status,
                                               String str_cluster_comments) {

        //Call<ClusterFarmpond> call = userService1.Post_ActionPondApproval

        final String str_status=str_approvereject_status;
        Class_cluster_approvereject_request request = new Class_cluster_approvereject_request();

        request.setCreated_By(str_loginuserId);
        request.setPond_ID(str_pondid);
        request.setApproval_Status(str_approvereject_status);
        request.setApproval_Remarks(str_cluster_comments);



        Log.e("finalrequest", "Request: " + new Gson().toJson(request));

        if (1 < 2) {
            userService1 = Class_ApiUtils.getUserService();
            Call<Class_cluster_approvereject_request> call = userService1.Post_ActionPondApproval(request);

            final ProgressDialog progressDoalog;
            progressDoalog = new ProgressDialog(activity);
            progressDoalog.setMessage("Loading....");
            progressDoalog.setTitle("Please wait....");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // show it
            progressDoalog.show();

            call.enqueue(new Callback<Class_cluster_approvereject_request>() {
                @Override
                public void onResponse(Call<Class_cluster_approvereject_request> call, Response<Class_cluster_approvereject_request> response) {
                    //   Log.e("Entered resp", response.message());
                    //     Log.e("Entered resp", response.body().getMessage());
                    Log.e("response_appreject", "response_appreject: " + new Gson().toJson(response));
                    Log.e("response", String.valueOf(response.body()));

                    if (response.isSuccessful()) {

                        //Class_cluster_approvereject_request class_userData = response.body();
                        progressDoalog.dismiss();

                        Toast.makeText(activity.getApplicationContext(), ""+str_status, Toast.LENGTH_SHORT).show();
                        activity.finish();


                    } else {
                        progressDoalog.dismiss();


                        Log.e("Entered resp else", "");
                        DefaultResponse error = ErrorUtils.parseError(response);
                        // … and use it to show error information

                        // … or just log the issue like we’re doing :)
                        Log.e("error message", error.getMsg());


                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(activity.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            });// end of call

        }

    }







}
