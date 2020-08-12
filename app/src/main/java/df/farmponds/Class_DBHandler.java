package df.farmponds;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Class_DBHandler extends SQLiteOpenHelper
{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FarmPond_db";
    //  table name

    private static final String TABLE_FarmPondDetails_fromServerRest = "FarmPondDetails_fromServerRest";



    public Class_DBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR,TempFIDDB VARCHAR," +
                "FNameDB VARCHAR,FMNameDB VARCHAR,FLNameDB VARCHAR,FYearIDDB VARCHAR,FStateIDDB VARCHAR,FDistrictIDDB VARCHAR," +
                "FTalukIDDB VARCHAR,FPanchayatIDDB VARCHAR,FVillageIDDB VARCHAR,FageDB VARCHAR,FphonenumberDB VARCHAR," +
                "FAnnualIncomeDB VARCHAR,FfamilymemberDB VARCHAR,FidprooftypeDB VARCHAR,FidproofnoDB VARCHAR,FphotoDB VARCHAR,FPondidDB VARCHAR,WidthDB VARCHAR," +
                "HeightDB VARCHAR,DepthDB VARCHAR,LatitudeDB VARCHAR,LongitudeDB VARCHAR,Imageid1DB VARCHAR,Image1Base64DB VARCHAR," +
                "Imageid2DB VARCHAR,Image2Base64DB VARCHAR,Imageid3DB VARCHAR,Image3Base64DB VARCHAR,EmployeeIDDB VARCHAR,SubmittedDateDB VARCHAR," +
                "TotalDaysDB VARCHAR,StartDateDB VARCHAR,ConstructedDateDB VARCHAR,PondCostDB VARCHAR,McodeDB VARCHAR,FPondCodeDB VARCHAR," +
                "FPondRemarksDB VARCHAR,FPondAmtTakenDB VARCHAR,FPondStatusDB VARCHAR," +
                "FPondApprovalStatusDB VARCHAR,FPondApprovalRemarksDB VARCHAR,FPondApprovedbyDB VARCHAR,FPondApprovedDateDB VARCHAR,FPondDonorDB VARCHAR," +
                "FPondLatitudeDB VARCHAR,FPondLongitudeDB VARCHAR," +
                "FPondAcresDB VARCHAR,FPondGuntaDB VARCHAR,FPondCropBeforeDB VARCHAR,FPondCropAfterDB VARCHAR," +
                "UploadedStatusFarmerprofile VARCHAR,UploadedStatus VARCHAR," +
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FarmPondDetails_fromServerRest);
        onCreate(db);

    }


    public int get_DB_newfarmpond_offline_data_count()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatus='" + 2 + "'", null);
        int x = cursor1.getCount();
        Log.e("edited_count", String.valueOf(x));
        db.close();

        return x;
    }


    public int get_DB_edited_offline_data_count()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatus='" + 1 + "'", null);
        int x = cursor1.getCount();
        Log.e("edited_count", String.valueOf(x));
        db.close();

        return x;
    }




    public Class_farmponddetails_offline[] get_DB_edited_offline_data()
    {
        /*String countQuery = "SELECT  * FROM " + TABLE_FarmPondDetails_fromServerRest;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count1 = cursor.getCount();
        Log.e("count",String.valueOf(count1));
        db.close();*/

        Class_farmponddetails_offline[] class_farmponddetails_offline_array_obj;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE UploadedStatus='" + 1 + "'", null);
        int x = cursor1.getCount();



        Log.e("uploadstatus_count", String.valueOf(x));



        int i = 0;
        class_farmponddetails_offline_array_obj = new Class_farmponddetails_offline[x];
        if(x>0)
        {
            if (cursor1.moveToFirst()) {

                do {
                    Class_farmponddetails_offline innerObj_Class_farmponddetails_offline = new Class_farmponddetails_offline();
                    innerObj_Class_farmponddetails_offline.setfarmer_id(cursor1.getString(cursor1.getColumnIndex("FIDDB")));
                    innerObj_Class_farmponddetails_offline.setFarmer_Name(cursor1.getString(cursor1.getColumnIndex("FNameDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_Id(cursor1.getString(cursor1.getColumnIndex("FPondidDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_Width(cursor1.getString(cursor1.getColumnIndex("WidthDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_Height(cursor1.getString(cursor1.getColumnIndex("HeightDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_Depth(cursor1.getString(cursor1.getColumnIndex("DepthDB")));


                    // "FPondAcresDB VARCHAR,FPondGuntaDB VARCHAR,FPondCropBeforeDB VARCHAR,FPondCropAfterDB VARCHAR," +
                    innerObj_Class_farmponddetails_offline.setFarmpond_acres(cursor1.getString(cursor1.getColumnIndex("FPondAcresDB")));
                    innerObj_Class_farmponddetails_offline.setFarmpond_gunta(cursor1.getString(cursor1.getColumnIndex("FPondGuntaDB")));

                    innerObj_Class_farmponddetails_offline.setLatitude(cursor1.getString(cursor1.getColumnIndex("LatitudeDB")));
                    innerObj_Class_farmponddetails_offline.setLongitude(cursor1.getString(cursor1.getColumnIndex("LongitudeDB")));

                    Log.e("editLatitude",cursor1.getString(cursor1.getColumnIndex("LatitudeDB")));
                    Log.e("editLongitude",cursor1.getString(cursor1.getColumnIndex("LongitudeDB")));


                    innerObj_Class_farmponddetails_offline.setImage1_ID(cursor1.getString(cursor1.getColumnIndex("Imageid1DB")));
                    //innerObj_Class_farmponddetails_offline.setImage1_Base64(cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")));
                    if(cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")).equalsIgnoreCase("0") ||
                            cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")).equalsIgnoreCase("noimage1") )
                    {
                        innerObj_Class_farmponddetails_offline.setImage1_Base64("");
                    }else{
                        innerObj_Class_farmponddetails_offline.setImage1_Base64(cursor1.getString(cursor1.getColumnIndex("Image1Base64DB")));}


                    innerObj_Class_farmponddetails_offline.setImage2_ID(cursor1.getString(cursor1.getColumnIndex("Imageid2DB")));
                    // innerObj_Class_farmponddetails_offline.setImage2_Base64(cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")));
                    if(cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")).equalsIgnoreCase("0") ||
                            cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")).equalsIgnoreCase("noimage2") )
                    {
                        innerObj_Class_farmponddetails_offline.setImage2_Base64("");
                    }else{
                        innerObj_Class_farmponddetails_offline.setImage2_Base64(cursor1.getString(cursor1.getColumnIndex("Image2Base64DB")));}

                    innerObj_Class_farmponddetails_offline.setImage3_ID(cursor1.getString(cursor1.getColumnIndex("Imageid3DB")));
                    // innerObj_Class_farmponddetails_offline.setImage3_Base64(cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")));
                    if(cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")).equalsIgnoreCase("0") ||
                            cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")).equalsIgnoreCase("noimage3") )
                    {
                        innerObj_Class_farmponddetails_offline.setImage3_Base64("");
                    }else {
                        innerObj_Class_farmponddetails_offline.setImage3_Base64(cursor1.getString(cursor1.getColumnIndex("Image3Base64DB")));
                    }

                    innerObj_Class_farmponddetails_offline.setUploadedStatus(cursor1.getString(cursor1.getColumnIndex("UploadedStatus")));

                    Log.e("employeeid",cursor1.getString(cursor1.getColumnIndex("EmployeeIDDB")));

                    innerObj_Class_farmponddetails_offline.setEmployeeID(cursor1.getString(cursor1.getColumnIndex("EmployeeIDDB")));

                    innerObj_Class_farmponddetails_offline.setSubmittedDateTime(cursor1.getString(cursor1.getColumnIndex("SubmittedDateDB")));





                    innerObj_Class_farmponddetails_offline.setTotal_no_days(cursor1.getString(cursor1.getColumnIndex("TotalDaysDB")));
                    innerObj_Class_farmponddetails_offline.setConstructedDate(cursor1.getString(cursor1.getColumnIndex("ConstructedDateDB")));
                    innerObj_Class_farmponddetails_offline.setPondCost(cursor1.getString(cursor1.getColumnIndex("PondCostDB")));




                    innerObj_Class_farmponddetails_offline.setMachineCode(cursor1.getString(cursor1.getColumnIndex("McodeDB")));

                    innerObj_Class_farmponddetails_offline.setStartDate(cursor1.getString(cursor1.getColumnIndex("StartDateDB")));

                    innerObj_Class_farmponddetails_offline.setFarmpond_remarks(cursor1.getString(cursor1.getColumnIndex("FPondRemarksDB")));

                    if(cursor1.getString(cursor1.getColumnIndex("FPondAmtTakenDB")).equals(null))
                    {  innerObj_Class_farmponddetails_offline.setFarmpond_amttaken("0");
                    }else
                    { innerObj_Class_farmponddetails_offline.setFarmpond_amttaken(cursor1.getString(cursor1.getColumnIndex("FPondAmtTakenDB")));
                    }

                    //innerObj_Class_farmponddetails_offline.setFarmpond_amttaken(cursor1.getString(cursor1.getColumnIndex("FPondAmtTakenDB")));
                    // "FPondRemarksDB VARCHAR,FPondAmtTakenDB VARCHAR,FPondStatusDB VARCHAR," +

                    class_farmponddetails_offline_array_obj[i] = innerObj_Class_farmponddetails_offline;
                    i++;
                    Log.e("editdays", cursor1.getString(cursor1.getColumnIndex("TotalDaysDB")));
                    Log.e("editconstructed", cursor1.getString(cursor1.getColumnIndex("ConstructedDateDB")));
                    Log.e("editpondcost", cursor1.getString(cursor1.getColumnIndex("PondCostDB")));
                    Log.e("editMcode", cursor1.getString(cursor1.getColumnIndex("McodeDB")));
                    Log.e("Farmerid", cursor1.getString(cursor1.getColumnIndex("FIDDB")));
                    Log.e("FarmerName", cursor1.getString(cursor1.getColumnIndex("FNameDB")));
                    Log.e("Imageid1DB", cursor1.getString(cursor1.getColumnIndex("Imageid1DB")));
                    Log.e("Imageid2DB", cursor1.getString(cursor1.getColumnIndex("Imageid2DB")));
                    Log.e("Imageid3DB", cursor1.getString(cursor1.getColumnIndex("Imageid3DB")));
                } while (cursor1.moveToNext());
            }//if ends
        }
        db.close();
        Log.e("length", String.valueOf(class_farmponddetails_offline_array_obj.length));
return class_farmponddetails_offline_array_obj;
    }

}//end of class
