package df.farmponds;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;

public class Class_base64toBitmapConversion
{


    byte[] imageBytes;
    Bitmap bmp_decodedImage1,bmp_decodedImage2,bmp_decodedImage3;
   // private final Context mContext;


    /*public Class_base64toBitmapConversion(Context context) {
        this.mContext = context;

    }*/


   /* Class2 cls2 = new Class2();
cls2.UpdateEmployee();*/

    public ArrayList<Bitmap> base64tobitmap(Class_farmponddetails_offline class_farmponddetails_offline_obj)
    {


        ArrayList<Bitmap> arrayList_bitmap = new ArrayList<Bitmap>();
        if(class_farmponddetails_offline_obj.getImage1_Base64().equalsIgnoreCase("noimage1")||
                (class_farmponddetails_offline_obj.getImage1_Base64().equalsIgnoreCase("0")))
        {
           // arrayList_bitmap.add(null);

        }
        else
            {
                String str_image1_Base64 =class_farmponddetails_offline_obj.getImage1_Base64();

                imageBytes  = Base64.decode(str_image1_Base64, Base64.DEFAULT);
                bmp_decodedImage1 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                arrayList_bitmap.add(bmp_decodedImage1);
        }

        if(class_farmponddetails_offline_obj.getImage2_Base64().equalsIgnoreCase("noimage2") ||
                (class_farmponddetails_offline_obj.getImage2_Base64().equalsIgnoreCase("0")))
        {

        }
        else{
            String str_image2_Base64 =class_farmponddetails_offline_obj.getImage2_Base64();

            imageBytes  = Base64.decode(str_image2_Base64, Base64.DEFAULT);
            bmp_decodedImage2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            arrayList_bitmap.add(bmp_decodedImage2);
        }



        if(class_farmponddetails_offline_obj.getImage3_Base64().equalsIgnoreCase("noimage3")||
                (class_farmponddetails_offline_obj.getImage3_Base64().equalsIgnoreCase("0")))
        {

        }
        else{
            String str_image3_Base64 =class_farmponddetails_offline_obj.getImage3_Base64();

            imageBytes  = Base64.decode(str_image3_Base64, Base64.DEFAULT);
            bmp_decodedImage3 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            arrayList_bitmap.add(bmp_decodedImage3);
        }



return arrayList_bitmap;
    }





}// end of class
