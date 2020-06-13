package df.farmpondstwo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Class_SaveSharedPreference
{
    static final String PREF_USER_NAME= "username";

    static final String PREF_TECHSUPPORTEMAIL= "email_techsuport";
    static final String PREF_TECHSUPPORTPHONE= "mobile_techsuport";

    static final String PREF_FLAG_USERMANUAL= "flag_usermanual";


    static final String PREF_Received_Studentid_bysubmitting= "stuid_bysubmittingappln";
    static final String PREF_Received_Stu_ApplNo_bysubmitting= "applno_bysubmittingappln";
    static final String PREF_Received_Sync_Date= "sync_date_received";


    static final String PREF_stateposition= "state_position";
    static final String PREF_districtposition= "district_position";
    static final String PREF_talukposition= "taluk_position";
    static final String PREF_gramanchayatposition= "gramanchayat_position";
    static final String PREF_villageposition= "village_position";




    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }


    public static void setSupportEmail(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TECHSUPPORTEMAIL, email);
        editor.commit();
    }

    public static String getSupportEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TECHSUPPORTEMAIL, "");
    }


    public static void setSupportPhone(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TECHSUPPORTPHONE, email);
        editor.commit();
    }

    public static String getSupportPhone(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TECHSUPPORTPHONE, "");
    }



    public static void setPrefFlagUsermanual(Context ctx, String flag)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_FLAG_USERMANUAL, flag);
        editor.commit();
    }

    public static String getPrefFlagUsermanual(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_FLAG_USERMANUAL, "");
    }

    public static String getPREF_Received_Studentid_bysubmitting(Context ctx) {
       // return PREF_Received_Studentid_bysubmitting;
        Log.e("ctx", String.valueOf(ctx));
        return getSharedPreferences(ctx).getString(PREF_Received_Studentid_bysubmitting, "");

    }

    public static void setPREF_Received_Studentid_bysubmitting(Context ctx, String studentid)
    {
        Log.e("studentid",studentid);
        Log.e("ctx", String.valueOf(ctx));
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_Received_Studentid_bysubmitting, studentid);
        editor.commit();
    }



    public static String getPREF_Received_ApplNo_bysubmitting(Context ctx) {
        // return PREF_Received_Studentid_bysubmitting;
      //  Log.e("ctx", String.valueOf(ctx));
        return getSharedPreferences(ctx).getString(PREF_Received_Stu_ApplNo_bysubmitting, "");

    }

    public static void setPREF_Received_ApplNo_bysubmitting(Context ctx, String studentapplno)
    {
        Log.e("studentapplno",studentapplno);
       // Log.e("ctx", String.valueOf(ctx));
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_Received_Stu_ApplNo_bysubmitting, studentapplno);
        editor.commit();
    }

//PREF_Received_Sync_Date
public static String getPREF_Received_Sync_Date(Context ctx) {

    return getSharedPreferences(ctx).getString(PREF_Received_Sync_Date, "");

}

    public static void setPREF_Received_Sync_Date(Context ctx, String syncdate)
    {
        Log.e("syncdate",syncdate);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_Received_Sync_Date, syncdate);
        editor.commit();
    }




    //PREF_stateposition

    public static int getPREF_stateposition(Context ctx) {

        return getSharedPreferences(ctx).getInt(PREF_stateposition, 0);

    }

    public static void setPREF_stateposition(Context ctx, int str_statepos)
    {
        Log.e("statepos", String.valueOf(str_statepos));
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_stateposition, str_statepos);
        editor.apply();
    }



//district
    public static int getPREF_districtposition(Context ctx) {

        return getSharedPreferences(ctx).getInt(PREF_districtposition, 0);

    }

    public static void setPREF_districtposition(Context ctx, int str_statepos)
    {
        Log.e("districtpos", String.valueOf(str_statepos));
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_districtposition, str_statepos);
        editor.apply();
    }


    //taluk
    public static int getPREF_talukposition(Context ctx) {

        return getSharedPreferences(ctx).getInt(PREF_talukposition, 0);

    }

    public static void setPREF_talukposition(Context ctx, int str_statepos)
    {
        Log.e("talukpos", String.valueOf(str_statepos));
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_talukposition, str_statepos);
        editor.apply();
    }

    //gramanachayat

    public static int getPREF_gramanchayatposition(Context ctx) {

        return getSharedPreferences(ctx).getInt(PREF_gramanchayatposition, 0);

    }

    public static void setPREF_gramanchayatposition(Context ctx, int str_statepos)
    {
        Log.e("ggrampanchaatpos", String.valueOf(str_statepos));
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_gramanchayatposition, str_statepos);
        editor.apply();
    }

//village
public static int getPREF_villageposition(Context ctx) {

    return getSharedPreferences(ctx).getInt(PREF_villageposition, 0);

}

    public static void setPREF_villageposition(Context ctx, int str_statepos)
    {
        Log.e("villagepos", String.valueOf(str_statepos));
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_villageposition, str_statepos);
        editor.apply();
    }

    /*
    /district
    public static String getPREF_districtposition(Context ctx) {

        return getSharedPreferences(ctx).getString(PREF_districtposition, "");

    }

    public static void setPREF_districtposition(Context ctx, String str_statepos)
    {
        Log.e("districtpos", str_statepos);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_districtposition, str_statepos);
        editor.apply();
    }

     */

}