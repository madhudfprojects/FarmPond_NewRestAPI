package df.farmponds;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
*/
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import df.farmponds.Models.NormalLogin_List;
import df.farmponds.Models.NormalLogin_Response;
import df.farmponds.remote.Class_ApiUtils;
import df.farmponds.remote.Interface_userservice;
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
    GoogleSignInClient googlesigninclient_obj;
    GoogleSignInAccount account;


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

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        google_signin_bt =(SignInButton)findViewById(R.id.google_signin_bt);
        google_signin_bt.setColorScheme(SignInButton.COLOR_DARK);
        setGooglePlusButtonText(google_signin_bt,"  Sign in with DF mail  ");


        userService1 = Class_ApiUtils.getUserService();

        sharedpref_username_Obj=getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_loginusername = sharedpref_username_Obj.getString(Key_username, "").trim();
        sharedpref_userimage_Obj=getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_profileimage = sharedpref_userimage_Obj.getString(key_userimage, "").trim();

        sharedpreferencebookRest_usercredential_Obj=this.getSharedPreferences(sharedpreferencebook_User_Credential, Context.MODE_PRIVATE);
        sharedpreferencebook_usercredential_Obj=this.getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);

        normallogin_bt =(Button)findViewById(R.id.normallogin_bt);




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


        //Google Sign initializing
        firebaseauth_obj = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_ID_type3))
                .requestEmail()
                .build();
        googlesigninclient_obj = GoogleSignIn.getClient(this, gso);
        //Google Sign initializing






        // Signout function

        Intent myIntent = getIntent();

        if(myIntent!=null)
        {

            String logout="no";
            logout = myIntent.getStringExtra("Key_Logout");
            if(logout!=null && (logout.equalsIgnoreCase("yes")))
            {
                signOut();
            }
        }

        // Signout function


        google_signin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (checkPermissions())
                {
                    if (isInternetPresent)
                    {
                    google_sign();
                    }else{
                        Toast.makeText(MainActivity.this, "Kindly connect to internet", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });









        //normal login comment while releasing apk
        normallogin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (checkPermissions())
                {
                    //  permissions  granted.
                    AsyncTask_loginverify();
                }



            }
        });


        //normal login comment while releasing apk


    }// end of onCreate()





    private void google_sign() {
        //getting the google signin intent
        Intent signInIntent = googlesigninclient_obj.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {
            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                account = task.getResult(ApiException.class);
                //authenticating with firebase
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct)
    {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        str_gmailid=acct.getEmail().toString();

        //Now using firebase we are signing in the user here
        firebaseauth_obj.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseauth_obj.getCurrentUser();


                            SharedPreferences.Editor myprefs_Username = sharedpref_username_Obj.edit();
                            myprefs_Username.putString(Key_username, acct.getDisplayName());
                            myprefs_Username.apply();
                            SaveSharedPreference.setUserName(MainActivity.this,account.getDisplayName());
                            SaveSharedPreference.setUserMailID(MainActivity.this,str_gmailid);

                            SharedPreferences.Editor myprefs_UserImg = sharedpref_userimage_Obj.edit();
                            myprefs_UserImg.putString(key_userimage, String.valueOf(acct.getPhotoUrl()));
                            myprefs_UserImg.apply();
//////////////////////////////////////////


                            Toast.makeText(MainActivity.this, "User Signed In:"+str_gmailid, Toast.LENGTH_SHORT).show();

                            AsyncTask_loginverify();


                            /*try {
                                postRequest();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/

                        } else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }










    private void AsyncTask_loginverify()
    {

        final ProgressDialog login_progressDoalog;
        login_progressDoalog = new ProgressDialog(MainActivity.this);
        login_progressDoalog.setMessage("Fetching the crendentials....");
        login_progressDoalog.setTitle("Please wait....");
        login_progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        login_progressDoalog.show();

       /* Map<String,String> params = new HashMap<String, String>();

        params.put("User_Email","eventtest464@gmail.com ");// for dynamic*/

        //retrofit2.Call call = userService1.getValidateLoginPostNew("eventtest464@gmail.com");
       // retrofit2.Call call = userService1.getValidateLoginPostNew(str_gmailid);
       // str_gmailid="anandkanade.tech@dfmail.org";
       // str_gmailid="eventtest464@gmail.com";
       // str_gmailid="kanadeanand@gmail.com";
      //  str_gmailid="testdev326@gmail.com";

        str_gmailid="madhushree.kubsad@dfmail.org";

        retrofit2.Call call = userService1.getValidateLoginPostNew(str_gmailid);

        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {

                // Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                // Log.e("response",response.body().toString());
                NormalLogin_Response user_object = new NormalLogin_Response();
                user_object = (NormalLogin_Response) response.body();
                // String x=response.body().toString();

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String y = jsonObject.getString("Status").toString();
                    Toast.makeText(getApplicationContext(), "S:" + y, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error ponds", e.toString());
                }


                if (user_object.getStatus().toString().equalsIgnoreCase("true"))
                {


                    Log.e("response", user_object.getStatus().toString());

                    Toast.makeText(MainActivity.this, "" + user_object.getStatus().toString(), Toast.LENGTH_LONG).show();

                    Toast.makeText(MainActivity.this, "" + user_object.getLst().get(0).getUserEmail(), Toast.LENGTH_LONG).show();

                    SaveSharedPreference.setUserMailID(MainActivity.this, user_object.getLst().get(0).getUserID());
                    SaveSharedPreference.setUserName(MainActivity.this, user_object.getLst().get(0).getUserName());

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

                    login_progressDoalog.dismiss();

                    Log.e("tag", "mailid==" + SaveSharedPreference.getUsermailID(MainActivity.this));
                    Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
                    startActivity(i);

                } else {

                    login_progressDoalog.dismiss();
                    signOut_InvalidUser();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
                login_progressDoalog.dismiss();
                Log.e("WS","error"+t.getMessage());
                Toast.makeText(MainActivity.this, "WS:"+t.getMessage(), Toast.LENGTH_LONG).show();
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




    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText)
    {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                // tv.setBackgroundColor(Color.CYAN);
                tv.setBackgroundDrawable(
                        new ColorDrawable(Color.parseColor(COLOR)));
                tv.setTextColor(Color.WHITE);
                tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/laouibold.ttf"));
                return;
            }
        }
    }




    private void signOut_InvalidUser()
    {
        googlesigninclient_obj.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MainActivity.this,"Sigined Out: InValid User",Toast.LENGTH_SHORT).show();
                    }
                });
    }





    private void signOut() {
        googlesigninclient_obj.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MainActivity.this,"Sigined Out Successfully",Toast.LENGTH_SHORT).show();
                    }
                });
    }




}// end of class

