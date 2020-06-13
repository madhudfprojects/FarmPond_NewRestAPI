package df.farmpondstwo;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Class_CurrentDateDDMMYYYY
{
    String str_submitteddatetime;

    public String currentdate()
    {
        Calendar c = Calendar.getInstance();
        //System.out.println("Current time => "+c.getTime());
        //
        //        //  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //        // SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Log.e("date",formattedDate);

        str_submitteddatetime=formattedDate;

        return str_submitteddatetime;


    }

}
