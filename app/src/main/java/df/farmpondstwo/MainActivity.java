package df.farmpondstwo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
/*
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
*/
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import df.farmpondstwo.Models.DefaultResponse;
import df.farmpondstwo.Models.ErrorUtils;
import df.farmpondstwo.Models.NormalLogin_List;
import df.farmpondstwo.Models.NormalLogin_Response;
import df.farmpondstwo.remote.Class_ApiUtils;
import df.farmpondstwo.remote.Interface_userservice;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{

    Interface_userservice userService1;
    //commit

    private static final int RC_SIGN_IN = 234;////a constant for detecting the login intent result
    private static final String TAG = "dffarmpond";
   // GoogleSignInClient googlesigninclient_obj;
    FirebaseAuth firebaseauth_obj;
    SignInButton google_signin_bt;

    public final static String COLOR = "#1565C0";


    //GoogleSignInAccount account;

    String str_googletokenid;


    EditText username_et;
    Button normallogin_bt,managerlogin_bt;
    String str_gmailid;

    Context context_obj;

    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";
    public static final String KeyValue_perdayamount = "KeyValue_perdayamount";

    SharedPreferences sharedpreferencebook_usercredential_Obj;

    public static final String sharedpreferencebook_User_Credential = "sharedpreferencebook_User_Credential";
    public static final String KeyValue_User_ID = "KeyValue_User_ID";
    public static final String KeyValue_User_Name = "KeyValue_User_Name";
    public static final String KeyValue_User_Email = "KeyValue_User_Email";
    public static final String KeyValue_User_Desigation = "KeyValue_User_Desigation";
    public static final String KeyValue_User_Role = "KeyValue_User_Role";
    public static final String KeyValue_User_State = "KeyValue_User_State";
    public static final String KeyValue_User_State_Amount = "KeyValue_User_State_Amount";
    public static final String KeyValue_Response = "KeyValue_Response";

    SharedPreferences sharedpreferencebookRest_usercredential_Obj;
    SharedPreferences.Editor editor_obj;


    String str_token;
    String str_tokenfromprefrence;



    public static final String sharedpreferenc_username = "googlelogin_name";
    public static final String Key_username = "name_googlelogin";
    SharedPreferences sharedpref_userimage_Obj;
    public static final String sharedpreferenc_userimage = "googlelogin_img";
    public static final String key_userimage = "profileimg_googlelogin";
    SharedPreferences sharedpref_username_Obj;

    String str_loginusername,str_profileimage;


    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.INTERNET
    };
    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.



    NormalLogin_List[] arrayObj_Class_yeardetails;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userService1 = Class_ApiUtils.getUserService();

        sharedpref_username_Obj=getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_loginusername = sharedpref_username_Obj.getString(Key_username, "").trim();
        sharedpref_userimage_Obj=getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_profileimage = sharedpref_userimage_Obj.getString(key_userimage, "").trim();

        sharedpreferencebookRest_usercredential_Obj=this.getSharedPreferences(sharedpreferencebook_User_Credential, Context.MODE_PRIVATE);
        sharedpreferencebook_usercredential_Obj=this.getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);

        normallogin_bt =(Button)findViewById(R.id.normallogin_bt);

        //normal login comment while releasing apk
        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            // call Login Activity
        }
        else
        {
            Log.e("sharedvalue",SaveSharedPreference.getUserName(MainActivity.this).toString());

            Intent i=new Intent(MainActivity.this,Activity_HomeScreen.class);
            startActivity(i);
            finish();

            // Stay at the current activity.
        }

        normallogin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               /* str_gmailid=username_et.getText().toString();
                AsyncTask_loginverify();*/



              /*  Intent i=new Intent(MainActivity.this,MapsActivity.class);
                startActivity(i);
                finish();*/
                //AsyncTask_NormalLogin();
                // NormalLogin();


                if (checkPermissions())
                {
                    //  permissions  granted.
                    NormalLoginNew();
                }



            }
        });


        //normal login comment while releasing apk


    }// end of onCreate()

    private void NormalLoginNew(){
        Map<String,String> params = new HashMap<String, String>();

        params.put("User_Email","eventtest464@gmail.com ");// for dynamic

        //retrofit2.Call call = userService1.getValidateLoginPostNew("eventtest464@gmail.com");
        retrofit2.Call call = userService1.getValidateLoginPostNew("mis@dfmail.org");

        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(retrofit2.Call call, Response response)
            {

                // Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                // Log.e("response",response.body().toString());
                NormalLogin_Response user_object= new NormalLogin_Response();
                user_object = (NormalLogin_Response) response.body();
               // String x=response.body().toString();

try{
                    JSONObject jsonObject = new JSONObject(response.body().toString());
              String y=jsonObject.getString("Status").toString();
                Toast.makeText(getApplicationContext(),"S:"+y,Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error ponds",e.toString());
        }


                Log.e("response",user_object.getStatus().toString());

                Toast.makeText(MainActivity.this, ""+user_object.getStatus().toString(), Toast.LENGTH_LONG).show();

                Toast.makeText(MainActivity.this, ""+user_object.getLst().get(0).getUserEmail(), Toast.LENGTH_LONG).show();

                SaveSharedPreference.setUserMailID(MainActivity.this,user_object.getLst().get(0).getUserID());
                SaveSharedPreference.setUserName(MainActivity.this,user_object.getLst().get(0).getUserName());

                editor_obj = sharedpreferencebookRest_usercredential_Obj.edit();

                editor_obj.putString(KeyValue_User_ID, user_object.getLst().get(0).getUserID());
                editor_obj.putString(KeyValue_User_Name, user_object.getLst().get(0).getUserName());
                editor_obj.putString(KeyValue_User_Email, user_object.getLst().get(0).getUserEmail());
                editor_obj.putString(KeyValue_User_Role, user_object.getLst().get(0).getUserRole());
                editor_obj.putString(KeyValue_User_State, user_object.getLst().get(0).getUserState());
                editor_obj.putString(KeyValue_User_State_Amount, user_object.getLst().get(0).getUserStateAmount());
                editor_obj.putString(KeyValue_User_Desigation, user_object.getLst().get(0).getUserDesigation());
                editor_obj.putString(KeyValue_Response, user_object.getLst().get(0).getResponse());


                editor_obj.commit();

                editor_obj = sharedpreferencebook_usercredential_Obj.edit();

                editor_obj.putString(KeyValue_employeeid, user_object.getLst().get(0).getUserID());
                editor_obj.putString(KeyValue_employeename, user_object.getLst().get(0).getUserName());
                editor_obj.putString(KeyValue_employee_mailid, user_object.getLst().get(0).getUserEmail());
                editor_obj.putString(KeyValue_employeecategory, user_object.getLst().get(0).getUserRole());
                editor_obj.putString(KeyValue_employeesandbox, user_object.getLst().get(0).getUserState());
                editor_obj.putString(KeyValue_perdayamount, user_object.getLst().get(0).getUserStateAmount());

                editor_obj.commit();

                Log.e("tag","mailid=="+SaveSharedPreference.getUsermailID(MainActivity.this));
                Intent i=new Intent(MainActivity.this,Activity_HomeScreen.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
                Log.e("tag","error"+t.getMessage());
                Toast.makeText(MainActivity.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }





}// end of class

