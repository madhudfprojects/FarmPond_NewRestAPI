package df.farmponds;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



//import static java.lang.System.lineSeparator;


/**
 *
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{

    private static final String TAG = "MyFirebaseMsgService";
    Bitmap url2bitmap=null;

    String str_titlewhr2go;

    NotificationCompat.Builder notificationBuilders;
    int x;

    String str_isProfileEdited;


    Intent intent,intent_student,intent_pm,intent_events,intent_story,intent_request,intent_pm_request,intent_pm_tshirt;
    Intent intent_farmerdetails,intent_farmpond_details;




    public static final String PREFBook_LoginTrack= "prefbook_logintrack";  //sharedpreference Book
    public static final String PrefID_WhereToGo = "prefid_wheretogo"; //
    SharedPreferences shardprefLoginTrack_obj;
    String str_loginTrack;
    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    String str_remarks,str_status,str_farmid,str_pondID,str_message;




    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        // Create and show notification

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        notificationBuilders = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

       /* notificationBuilders.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("Hearty365")
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Default notification")
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .setContentInfo("Info");

        notificationManager.notify(*//*notification id*//*1, notificationBuilders.build());
*/




        x=1;
      //  notificationBuilders = new NotificationCompat.Builder(this);

      //  Log.e(TAG, "From: " + remoteMessage.getData().get("title"));

       // Log.e(TAG, "From: " + remoteMessage.getData().get("title"));
         Log.e(TAG, "RawMessage: " + remoteMessage.getData().toString());


         // RawMessage: {Farmpondcode=KA20-03056, Remarks=TEST, Status = Approved, time=17-08-2020 13:11:27,
        // title=Approved, Pond_ID = 9677 , message=TESTY  TESTY: MYSURU: ASPATHREKAVAL: Approved: TEST, Farmer_ID = 11325}


        str_remarks=str_status=str_farmid=str_pondID=str_message="";

        str_remarks=remoteMessage.getData().get("Remarks").trim();
        str_status=remoteMessage.getData().get("Status ").trim();
        str_farmid=remoteMessage.getData().get("Farmer_ID ").trim();
        str_pondID=remoteMessage.getData().get("Pond_ID ").trim();
       str_message =remoteMessage.getData().get("message").trim();



      //  update_statusmessage_PondDetails_DB(str_remarks,str_status,str_farmid,str_pondID);



        shardprefLoginTrack_obj=getSharedPreferences(PREFBook_LoginTrack, Context.MODE_PRIVATE);
        str_loginTrack = shardprefLoginTrack_obj.getString(PrefID_WhereToGo, "").trim();

       // Log.e("logintrack",str_loginTrack);

        // sendNotification(remoteMessage.getNotification().getBody());

       // Log.e(TAG, "From: " + remoteMessage.getData().get("message").toString());
        //sendNotification(remoteMessage.getData().get("title"));

       /* if (remoteMessage.getData().get("message").toString()!=null ||remoteMessage.getData().get("message").toString()!="" ||remoteMessage.getData().get("message").toString().length()!=0)
        {
            sendNotification(remoteMessage.getData().get("message"));
        }*/


       // str_titlewhr2go =  remoteMessage.getData().get("title").trim();
        str_titlewhr2go =  remoteMessage.getData().get("Status ").trim();





        update_statusmessage_PondDetails_DB(str_remarks,str_status,str_farmid,str_pondID);


       // sendNotification(remoteMessage.getData().get("message").trim());

    }

    @SuppressLint("NewApi")
    //private void sendNotification(String messageBody)
    private void sendNotification(String messageBody,String farmer_ID,String farmpond_ID)
    {
        Log.e("MessageBoby: ", " " + messageBody);

        if (messageBody == null || messageBody == "" || messageBody.isEmpty())
        {
            Log.e("empty:", "Empty");
        } else
         {
            Log.e("else: ", " " + messageBody.toString());

             intent_farmerdetails = new Intent(this, Activity_MarketingHomeScreen.class);
             intent=intent_farmerdetails;

             if(str_titlewhr2go.equalsIgnoreCase("Approved")||
                     str_titlewhr2go.equalsIgnoreCase("Rejected")||
                     str_titlewhr2go.equalsIgnoreCase("Pending")||
                     str_titlewhr2go.equalsIgnoreCase("Deleted"))
             {
                 if(farmer_ID.equalsIgnoreCase("no")&&farmpond_ID.equalsIgnoreCase("no"))
                 {
                     intent_farmpond_details= new Intent(this, Activity_MarketingHomeScreen.class);
                 }else
                 {
                     intent_farmpond_details= new Intent(this, EachFarmPondDetails_Activity.class);
                     intent_farmpond_details.putExtra("farmer_ID",farmer_ID);
                     intent_farmpond_details.putExtra("farmpond_ID",farmpond_ID);
                 }


             }


             int breakat =3;
             String splittedmessagex="";
             String[] messsagetobreak = messageBody.split("\\s+");
             for (int i = 0; i < messsagetobreak.length; i++)
             {
                 if(i==breakat)
                 {
                     breakat=breakat+4;
                     splittedmessagex= splittedmessagex+" "+messsagetobreak[i]+ System.lineSeparator();
                     Log.e("Finalx",splittedmessagex.toString());
                 }
                 else
                 { splittedmessagex=splittedmessagex+" "+messsagetobreak[i];}
             }

             messageBody=splittedmessagex;
             Log.e("SplitString",splittedmessagex.toString());
             Log.e("MessageString",messageBody.toString());




        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       intent_farmerdetails.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             intent_farmpond_details.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

       // intent_student.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

       // intent_pm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent_events.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent_story.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent_request.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       //intent_pm_request.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        /*String str_passString=messageBody.toString();
        Log.e("str_passString",str_passString);*/

       // intent.putExtra("2", messageBody);

             //Approved,Rejected Deleted

           //  {time=27-03-2020 18:30:09, title=Approved, Farmer_ID=2214, message=BALAPPA GURAPPA SHIROL  SHIROL: VIJAYAPURA: ADAVISANGAPUR: KA20-01351: Approved: Remarks, Pond_ID=2037}

        if(((str_titlewhr2go.equalsIgnoreCase("Approved")||
                str_titlewhr2go.equalsIgnoreCase("Rejected")||
                str_titlewhr2go.equalsIgnoreCase("Pending")||
                str_titlewhr2go.equalsIgnoreCase("Deleted"))))
        {

            x++;
            int id = (int) System.currentTimeMillis();
            // intent.putExtra("2", str_passString);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent_farmpond_details,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

           NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                /*.setSmallIcon(R.mipmap.ic_launcher_round)*/
           // notificationBuilders
                    .setSmallIcon(R.drawable.laucher_icon_farmpond)
                    .setContentTitle("DF Agri")
                    /*.setContentText(messageBody)*/
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(id /* ID of notification */, notificationBuilder.build());

        }




       else if (str_titlewhr2go.equalsIgnoreCase("Events"))
       {
           x++;
           int id = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent_farmerdetails,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                /*.setSmallIcon(R.mipmap.ic_launcher_round)*/
           //notificationBuilders
                    .setSmallIcon(R.drawable.laucher_icon_farmpond)
                    .setContentTitle("DF Agri")
                    .setContentText(messageBody)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(url2bitmap).setSummaryText(messageBody))/*Notification with Image*/
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
        }


        else
        {
            x++;
            int id = (int) System.currentTimeMillis();

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent_farmerdetails,
                        PendingIntent.FLAG_ONE_SHOT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                /*.setSmallIcon(R.mipmap.ic_launcher_round)*/
            //notificationBuilders
                        .setSmallIcon(R.drawable.laucher_icon_farmpond)
                        .setContentTitle("DF Agri")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);
                        //.setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(id /* ID of notification */, notificationBuilder.build());

        }

    }

    }


    public Bitmap getBitmapfromUrl(String imageUrl)
    {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

    // update_statusmessage_PondDetails_DB(str_remarks,str_status,str_farmid,str_pondID);

    public void update_statusmessage_PondDetails_DB(String str_remarks,String str_status,
                                                    String str_farmid, String str_pondID)
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);


        db1.execSQL("CREATE TABLE IF NOT EXISTS FarmPondDetails_fromServerRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,FIDDB VARCHAR,TempFIDDB VARCHAR," +
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
                "newpondImageId1 VARCHAR,pondImageType1 VARCHAR,newpondImageId2 VARCHAR,pondImageType2 VARCHAR,newpondImageId3 VARCHAR,pondImageType3 VARCHAR,Farmer_Gender VARCHAR,finalfarmpondcodeDB VARCHAR);");




        //FPondidDB

        Log.e("fcm_farmerpondID",str_pondID);
        Log.e("fcm_farmerID",str_farmid);


        str_pondID = str_pondID.replaceAll("\\s", "");
        str_farmid = str_farmid.replaceAll("\\s", "");


        Log.e("Afcm_farmerpondID",str_pondID);
        Log.e("Afcm_farmerID",str_farmid);

        Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE FPondidDB='" +str_pondID+ "'", null);
        Cursor cursor2 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServerRest WHERE FIDDB='" +str_farmid+ "'", null);

        int x = cursor1.getCount();
        Log.e("fcmcount", String.valueOf(x));

        int x2 = cursor2.getCount();
        Log.e("fcmcount2", String.valueOf(x2));

        if(x>0)
        {
            Log.e("fcm_status",str_status);
            Log.e("fcm_remarks",str_remarks);

            ContentValues cv = new ContentValues();
            cv.put("FPondApprovalStatusDB", str_status);
            cv.put("FPondApprovalRemarksDB", str_remarks);

           // db1.update("FarmPondDetails_fromServer", cv, "FIDDB = ?", new String[]{str_farmid});
            db1.update("FarmPondDetails_fromServerRest", cv, "FPondidDB = ?", new String[]{str_pondID});
            db1.close();
        }
        else{
            str_farmid="no";
            str_pondID="no";
            db1.close();
        }

       /* Cursor cursor1 = db1.rawQuery("SELECT * FROM FarmPondDetails_fromServer", null);
        int x = cursor1.getCount();
        db1.close();*/

        //Log.e("countmessage", String.valueOf(x));




        sendNotification(str_message,str_farmid,str_pondID);


    }



}// end of class

