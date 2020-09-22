package df.farmponds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import df.farmponds.Models.ClusterFarmersummaryList;
import df.farmponds.Models.ClusterSummaryList;

public class ClusterFarmerListAdapter extends BaseAdapter {
    public ArrayList<ClusterFarmersummaryList> feesPaidList;
    Activity activity;

    public ClusterFarmerListAdapter(Activity activity, ArrayList<ClusterFarmersummaryList> feesPaidList) {
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
    public ClusterFarmersummaryList getItem(int position) {

        //return projList.get(position);
        return feesPaidList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView mFname;
        TextView mMobileNo;
        TextView mPonds;
        TextView mLocation;
        TextView mEmpId;
        TextView mFempId;
        TextView mYearId;
        TextView mType;
        ImageView mclustfarmerimage;
        LinearLayout farmerlist_ll;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();
        String str_base64image = null;
        byte[] imageBytes;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cluster_farmerlistrow, null);
            holder = new ViewHolder();

            holder.mFname = convertView.findViewById(R.id.txt_Fname);
            holder.mMobileNo = convertView.findViewById(R.id.txt_mobileno);
            holder.mPonds = convertView.findViewById(R.id.txt_Ponds);
            holder.mLocation = convertView.findViewById(R.id.txt_location);
            holder.mEmpId = convertView.findViewById(R.id.txt_EmpId);
            holder.mFempId = convertView.findViewById(R.id.txt_FempId);
            holder.mYearId = convertView.findViewById(R.id.txt_YearId);
            holder.mType = convertView.findViewById(R.id.txt_Type);
            holder.mclustfarmerimage = convertView.findViewById(R.id.clustfarmerimage_iv);
            holder.farmerlist_ll = convertView.findViewById(R.id.farmerlist_ll);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ClusterFarmersummaryList item = feesPaidList.get(position);

        if (item.getFarmerName() != null) {
            holder.mFname.setText(item.getFarmerName());
        }
        if (item.getFarmerMobile() != null) {
            holder.mMobileNo.setText(item.getFarmerMobile());
        }

        if (!item.getPondCount().equals(null)) {
            holder.mPonds.setText(item.getPondCount());
        }

        if (item.getVillageName() != null) {
            holder.mLocation.setText(item.getVillageName().toString());
        }
        if (item.getEmployeeID() != null) {
            holder.mEmpId.setText(item.getEmployeeID().toString());
        }
        if (item.getFarmerID() != null) {
            holder.mFempId.setText(item.getFarmerID().toString());
        }
        if (item.getYearId() != null) {
            holder.mYearId.setText(item.getYearId().toString());
        }
        if (item.getType() != null) {
            holder.mType.setText(item.getType().toString());
        }
        str_base64image=item.getFarmerPhoto();
        if (item.getFarmerPhoto() != null){
            imageBytes = Base64.decode(str_base64image, Base64.DEFAULT);
            Bitmap bmp_decodedImage1 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.mclustfarmerimage.setImageBitmap(bmp_decodedImage1);
        }
        holder.farmerlist_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmpondActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("FarmerID",holder.mFempId.getText().toString());
                i.putExtra("YearId",holder.mYearId.getText().toString());
                i.putExtra("Type",holder.mType.getText().toString());
                v.getContext().startActivity(i);
            }
        });
    /*    if(item.getDataRejected()!=null) {
            holder.mRejected.setText(item.getDataRejected());
        }

        if(item.getUserID()!=null) {
            holder.mEmpId.setText(item.getUserID().toString());
        }

        holder.mAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmerListActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("Type","All Farmers");
                v.getContext().startActivity(i);
            }
        });
        holder.mPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmerListActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("Type","Pending Farmers");
                v.getContext().startActivity(i);
            }
        });
        holder.mProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmerListActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("Type","Progress Farmers");
                v.getContext().startActivity(i);
            }
        });
        holder.mApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmerListActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("Type","Approved Farmers");
                v.getContext().startActivity(i);
            }
        });
        holder.mRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClusterFarmerListActivity.class);
                i.putExtra("EmployeeId",holder.mEmpId.getText().toString());
                i.putExtra("Type","Rejected Farmers");
                v.getContext().startActivity(i);
            }
        });*/
        return convertView;
    }


    public void filter(String charText, ArrayList<ClusterFarmersummaryList> feesList, String selectedCollegeName) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.feesPaidList.clear();

        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.feesPaidList.addAll(feesList);
                } else {
                    for (ClusterFarmersummaryList wp : feesList) {

                        if(selectedCollegeName == null) {
                            if ((wp.getFarmerName()!=null && wp.getFarmerName().toLowerCase().contains(charText.toLowerCase())) || ( wp.getFarmerMobile()!=null && wp.getFarmerMobile().toLowerCase().contains(charText.toLowerCase())) || ( wp.getVillageName()!=null && wp.getVillageName().toLowerCase().contains(charText.toLowerCase())) || ( wp.getPondCount()!=null && wp.getPondCount().toLowerCase().contains(charText.toLowerCase()))) {
                                this.feesPaidList.add(wp);
                            }
                        }else{
                            if ((wp.getFarmerName()!=null && wp.getFarmerName().equals(selectedCollegeName)) && ((wp.getFarmerName()!=null && wp.getFarmerName().toLowerCase().contains(charText.toLowerCase())) || (wp.getFarmerMobile()!=null && wp.getFarmerMobile().toLowerCase().contains(charText.toLowerCase())) || (wp.getVillageName()!=null && wp.getVillageName().toLowerCase().contains(charText.toLowerCase())) || (wp.getPondCount()!=null && wp.getPondCount().toLowerCase().contains(charText.toLowerCase())))) {
                                this.feesPaidList.add(wp);
                            }
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }
}
