package df.farmponds;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import df.farmponds.Models.Class_farmponddetails;
import df.farmponds.Models.Class_getdemo_resplist;
import df.farmponds.Models.Class_gethelp_Response;
import df.farmponds.Models.Class_gethelp_resplist;

public class ContactUs_Activity extends AppCompatActivity
{


    Toolbar toolbar;
    ImageView add_newfarmpond_iv;

    Class_gethelp_resplist[] class_gethelp_resplist_arrayObj;
    Class_getdemo_resplist[] class_getdemo_resplist_arrayObj;

    String str_link;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        if(count_from_HelpDetails_table()>0)
        {
            setContentView(R.layout.aboutus_activity);


            toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
            // Set upon the actionbar
            setSupportActionBar(toolbar);
            // Now use actionbar methods to show navigation icon and title
            // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Set upon the actionbar

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            TextView title = (TextView) toolbar.findViewById(R.id.title_name);
            add_newfarmpond_iv = (ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
            title.setText("About Us");
            getSupportActionBar().setTitle("");
            add_newfarmpond_iv.setVisibility(View.GONE);

           /* LinearLayout main_ll = findViewById(R.id.main2);
            TextView title_tv = new TextView(this);
            title_tv.setText("Contact us");



            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT

            );

            title_tv.setLayoutParams(params);
            main_ll.addView(title_tv);
*/
            Data_from_HelpDetails_table();


        }else{
            setContentView(R.layout.contactus_activity);

            toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
            // Set upon the actionbar
            setSupportActionBar(toolbar);
            // Now use actionbar methods to show navigation icon and title
            // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Set upon the actionbar

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            TextView title = (TextView) toolbar.findViewById(R.id.title_name);
            add_newfarmpond_iv = (ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
            title.setText("About Us");
            getSupportActionBar().setTitle("");
            add_newfarmpond_iv.setVisibility(View.GONE);
            setContentView(R.layout.contactus_activity);
        }


      //  Data_from_HelpDetails_table();




    }// end of Oncreate



    public int count_from_HelpDetails_table()
    {
        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM HelpDetails_table", null);
        int x = cursor.getCount();
        return x;
    }


    public void Data_from_HelpDetails_table()
    {
        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM HelpDetails_table", null);
        int x = cursor.getCount();

        Log.e("helpcount", String.valueOf(x));

        //class_gethelp_responses_arrayObj

        class_gethelp_resplist_arrayObj = new Class_gethelp_resplist[x];

        int i=0;

        if(x>0) {
            if (cursor.moveToFirst())
            {
                do{

                    Class_gethelp_resplist innerObj_Class_gethelp_resplist = new Class_gethelp_resplist();
                    innerObj_Class_gethelp_resplist.setTitle(cursor.getString(cursor.getColumnIndex("TitleDB")));
                    innerObj_Class_gethelp_resplist.setContent(cursor.getString(cursor.getColumnIndex("ContentDB")));

                    Log.e("title",cursor.getString(cursor.getColumnIndex("TitleDB")).toString());
                    Log.e("content",cursor.getString(cursor.getColumnIndex("TitleDB")).toString());

                    class_gethelp_resplist_arrayObj[i]=innerObj_Class_gethelp_resplist;
                    i++;
                } while (cursor.moveToNext());
            }

        }


        LinearLayout help_ll = findViewById(R.id.help_ll);



        for(int k=0;k<class_gethelp_resplist_arrayObj.length;k++)
        {
            if(x>0)
            {
                String str_num= String.valueOf(k);
                /*TextView title_tv = new TextView(this);
                title_tv.setText("Contact us");*/


                TextView title_tv = new TextView(this);
                TextView content_tv = new TextView(this);
                TextView nextline_tv = new TextView(this);
                //0123

                title_tv.setText(class_gethelp_resplist_arrayObj[k].getTitle());
                title_tv.setTypeface(null, Typeface.BOLD);

                title_tv.setTextSize(18);
                    //text.setGravity(Gravity.LEFT);
                title_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                help_ll.addView(title_tv);

                content_tv.setText(class_gethelp_resplist_arrayObj[k].getContent());
                content_tv.setTextSize(12);
                //text.setGravity(Gravity.LEFT);
                content_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                help_ll.addView(content_tv);


                nextline_tv.setText("\n");
                help_ll.addView(nextline_tv);


            }
        }

//demo_ll

        Data_from_DemoDetails_table();
    }


    public void Data_from_DemoDetails_table()
    {
        SQLiteDatabase db2 = this.openOrCreateDatabase("FarmPond_db", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS DemoDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,LanguageDB VARCHAR,LinkDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM DemoDetails_table", null);
        int x = cursor.getCount();

        Log.e("democount", String.valueOf(x));

        class_getdemo_resplist_arrayObj = new Class_getdemo_resplist[x];

        int i=0;

        if(x>0) {
            if (cursor.moveToFirst())
            {
                do{

                    Class_getdemo_resplist innerObj_Class_getdemo_resplist = new Class_getdemo_resplist();
                    innerObj_Class_getdemo_resplist.setLanguage_Name(cursor.getString(cursor.getColumnIndex("LanguageDB")));
                    innerObj_Class_getdemo_resplist.setLanguage_Link(cursor.getString(cursor.getColumnIndex("LinkDB")));

                    Log.e("LanguageDB",cursor.getString(cursor.getColumnIndex("LanguageDB")).toString());
                    Log.e("LinkDB",cursor.getString(cursor.getColumnIndex("LinkDB")).toString());

                    class_getdemo_resplist_arrayObj[i]=innerObj_Class_getdemo_resplist;
                    i++;
                } while (cursor.moveToNext());
            }

        }


        LinearLayout demo_ll = findViewById(R.id.demo_ll);



        for(int k=0;k<class_getdemo_resplist_arrayObj.length;k++)
        {
            if(x>0)
            {


                TextView language_tv = new TextView(this);
                TextView link_tv = new TextView(this);
                TextView nextline_tv = new TextView(this);
                //0123

                language_tv.setText(class_getdemo_resplist_arrayObj[k].getLanguage_Name());
                language_tv.setTypeface(null, Typeface.BOLD);

                language_tv.setTextSize(18);
                //text.setGravity(Gravity.LEFT);
                language_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                demo_ll.addView(language_tv);

                link_tv.setText(class_getdemo_resplist_arrayObj[k].getLanguage_Link());
                link_tv.setTextSize(12);
                //text.setGravity(Gravity.LEFT);
                link_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));


                str_link=class_getdemo_resplist_arrayObj[k].getLanguage_Link();
                link_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(class_getdemo_resplist_arrayObj[k].getLanguage_Link()));
                        /*Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(class_getdemo_resplist_arrayObj[k].getLanguage_Link()));*/


                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str_link));
                        startActivity(intent);
                        try {
                            //startActivity(appIntent);
                            //startActivity(webIntent);
                        } catch (ActivityNotFoundException ex) {
                           //
                        }
                    }
                });

                demo_ll.addView(link_tv);


                nextline_tv.setText("\n");
                demo_ll.addView(nextline_tv);


            }
        }



    }







    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(ContactUs_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.alert);
        dialog.setMessage("Are you sure want to go back");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {


                finish();
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                        dialog.dismiss();
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
           /* Intent i = new Intent(EditFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
            startActivity(i);
             finish();*/
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
