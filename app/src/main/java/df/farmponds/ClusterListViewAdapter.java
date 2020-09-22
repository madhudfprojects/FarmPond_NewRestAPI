package df.farmponds;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import df.farmponds.Models.ClusterSummaryList;

public class ClusterListViewAdapter extends BaseAdapter {
    public ArrayList<ClusterSummaryList> feesPaidList;
    Activity activity;

    public ClusterListViewAdapter(Activity activity, ArrayList<ClusterSummaryList> feesPaidList) {
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
    public ClusterSummaryList getItem(int position) {

        //return projList.get(position);
        return feesPaidList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView mEname;
        TextView mAll;
        TextView mPending;
        TextView mProcess;
        TextView mApproved;
        TextView mRejected;
        TextView mEmpId;
        TextView mYearId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cluster_listrow, null);
            holder = new ViewHolder();

            holder.mEname = convertView.findViewById(R.id.txt_Ename);
            holder.mAll = convertView.findViewById(R.id.txt_All);
            holder.mPending = convertView.findViewById(R.id.txt_Pending);
            holder.mProcess = convertView.findViewById(R.id.txt_Process);
            holder.mApproved = convertView.findViewById(R.id.txt_Approved);
            holder.mRejected = convertView.findViewById(R.id.txt_Rejected);
            holder.mEmpId = convertView.findViewById(R.id.txt_empId);
            holder.mYearId = convertView.findViewById(R.id.txt_YearId);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ClusterSummaryList item = feesPaidList.get(position);

        if(item.getEmployeeName()!=null) {
            holder.mEname.setText(item.getEmployeeName());
        }
        if(item.getDataAll()!=null) {
            holder.mAll.setText(item.getDataAll());
        }

        if(!item.getDataPending().equals(null)) {
            holder.mPending.setText(item.getDataPending());
        }

        if(item.getDataProcess()!=null) {
            holder.mProcess.setText(item.getDataProcess().toString());
        }
        if(item.getDataApproved()!=null) {
            holder.mApproved.setText(item.getDataApproved().toString());
        }
        if(item.getDataRejected()!=null) {
            holder.mRejected.setText(item.getDataRejected());
        }

        if(item.getUserID()!=null) {
            holder.mEmpId.setText(item.getUserID().toString());
        }
        if(item.getYearId()!=null) {
            holder.mYearId.setText(item.getYearId().toString());
        }

        Log.e("tag","YearId="+holder.mYearId.getText().toString()+"mEmpId="+holder.mEmpId.getText().toString());

        holder.mAll.setOnClickListener(new View.OnClickListener() {
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
        });
        return convertView;
    }


    public void filter(String charText, ArrayList<ClusterSummaryList> feesList, String selectedCollegeName) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.feesPaidList.clear();

        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.feesPaidList.addAll(feesList);
                } else {
                    for (ClusterSummaryList wp : feesList) {

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
    }
}
