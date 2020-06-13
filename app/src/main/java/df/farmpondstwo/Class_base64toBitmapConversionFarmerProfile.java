package df.farmpondstwo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;

import df.farmpondstwo.Models.Farmer;

public class Class_base64toBitmapConversionFarmerProfile
{


    byte[] imageBytes;
    Bitmap bmp_decodedImage;
   // private final Context mContext;


    /*public Class_base64toBitmapConversion(Context context) {
        this.mContext = context;

    }*/


   /* Class2 cls2 = new Class2();
cls2.UpdateEmployee();*/

    public ArrayList<Bitmap> base64tobitmap(Farmer farmerlist_obj)
    {




        ArrayList<Bitmap> arrayList_bitmap = new ArrayList<Bitmap>();
        arrayList_bitmap.clear();


        if(farmerlist_obj.getStr_base64().equalsIgnoreCase("noimage1")||
                (farmerlist_obj.getStr_base64().equalsIgnoreCase("0"))||
                (farmerlist_obj.getStr_base64().isEmpty()))
        {
           // arrayList_bitmap.add(null);

        }
        else
            {
                String str_image_Base64 =farmerlist_obj.getStr_base64();

                imageBytes  = Base64.decode(str_image_Base64, Base64.DEFAULT);
                bmp_decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                arrayList_bitmap.add(bmp_decodedImage);
        }


return arrayList_bitmap;
    }





}// end of class
