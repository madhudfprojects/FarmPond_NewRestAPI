package df.farmpondstwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity_FarmerHomeScreen extends AppCompatActivity {

    ImageView viewfarmer_ib;
    Class_InternetDectector internetDectector,internetDectector1;
    Boolean isInternetPresent = false;
    Boolean isInternetPresent1 = false;

    public static final String sharedpreferenc_selectedspinner = "sharedpreferenc_selectedspinner";
    public static final String Key_sel_yearsp = "sel_yearsp";
    public static final String Key_sel_statesp = "sel_statesp";
    public static final String Key_sel_districtsp = "sel_districtsp";
    public static final String Key_sel_taluksp = "sel_taluksp";
    public static final String Key_sel_villagesp = "sel_villagesp";
    public static final String Key_sel_grampanchayatsp = "sel_grampanchayatsp";
    SharedPreferences sharedpref_spinner_Obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__farmer_home_screen);

        viewfarmer_ib = (ImageView) findViewById(R.id.viewFarmer_IB);


        viewfarmer_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();
                Intent i = new Intent(Activity_FarmerHomeScreen.this, ViewFarmers_Activity.class);
                /*SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
                myprefs_spinner.putString(Key_sel_yearsp, "0");
                myprefs_spinner.putString(Key_sel_statesp, "0");
                myprefs_spinner.putString(Key_sel_districtsp, "0");
                myprefs_spinner.putString(Key_sel_taluksp, "0");
                myprefs_spinner.putString(Key_sel_villagesp, "0");
                myprefs_spinner.putString(Key_sel_grampanchayatsp, "0");

                myprefs_spinner.apply();*/
                startActivity(i);
                //  finish();


                //   AsyncTask_fetch_farmponddetails();



            }
        });
    }
}
