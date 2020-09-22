package df.farmponds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import df.farmponds.Models.ClusterFarmpondList;
import df.farmponds.Models.ClusterSummaryList;

public class ClusterFarmpondListViewAdapter extends BaseAdapter {
    public ArrayList<ClusterFarmpondList> feesPaidList;
    Activity activity;

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
        TextView FName,MobileNo,pondCode,pondSize,startDate,endDate,latitude,longitude,amount,no_ofDays,location_Status,approval_Status;
        ImageView pond_image1_iv,pond_image2_iv,pond_image3_iv;

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
       /*     holder.latitude=(TextView) convertView.findViewById(R.id.latitude);
            holder.longitude=(TextView) convertView.findViewById(R.id.longitude);*/
            holder.amount=(TextView) convertView.findViewById(R.id.amount);
            holder.no_ofDays=(TextView) convertView.findViewById(R.id.no_ofDays);
            holder.location_Status=(TextView) convertView.findViewById(R.id.location_Status);
            holder.approval_Status=(TextView) convertView.findViewById(R.id.approval_Status);
            holder.pond_image1_iv=(ImageView) convertView.findViewById(R.id.pond_image1_iv);
            holder.pond_image2_iv=(ImageView) convertView.findViewById(R.id.pond_image2_iv);
            holder.pond_image3_iv=(ImageView) convertView.findViewById(R.id.pond_image3_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ClusterFarmpondList item = feesPaidList.get(position);

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
        if(item.getApprovalStatus()!=null) {
            holder.approval_Status.setText(item.getApprovalStatus().toString());
        }
        if(item.getLocationStatus()!=null) {
            holder.location_Status.setText(item.getLocationStatus().toString());
        }
       /* if(item.getPondLatitude()!=null) {
            holder.latitude.setText(item.getPondLatitude().toString());
        }
        if(item.getPondLongitude()!=null) {
            holder.longitude.setText(item.getPondLongitude().toString());
        }*/

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
}
