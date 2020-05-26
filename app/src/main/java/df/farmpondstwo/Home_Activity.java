package df.farmpondstwo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import df.farmpondstwo.Models.DefaultResponse;
import df.farmpondstwo.Models.ErrorUtils;
import df.farmpondstwo.Models.Location_Data;
import df.farmpondstwo.Models.Location_DataList;
import df.farmpondstwo.remote.Interface_userservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Activity extends AppCompatActivity {

    Interface_userservice userService1;
    Location_DataList[] location_dataLists;
    Location_DataList class_location_dataList = new Location_DataList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LinearLayout stuRegistration_relativeLayout = (LinearLayout) findViewById(R.id.student_registration_RL);
        stuRegistration_relativeLayout.setVisibility(LinearLayout.VISIBLE);

        stuRegistration_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home_Activity.this, Activity_FarmerHomeScreen.class);
                startActivity(i);

            }
        });

    }
}
