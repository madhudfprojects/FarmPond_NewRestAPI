package df.farmpondstwo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static df.farmpondstwo.AddFarmer_Activity1.add_farmerimage_iv;

/*import static com.det.skillinvillage.Activity_EditRegistration.key_flagcamera;
import static com.det.skillinvillage.Activity_EditRegistration.photo_edit_iv;
import static com.det.skillinvillage.Activity_EditRegistration.sharedpreferenc_camera;*/
//import static df.farmponds.Camera_Activity.img_profilePick;


public class CameraPhotoCapture extends Activity {

    final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
     
     
    Uri imageUri                      = null;
    //static TextView imageDetails      = null;
    public  static ImageView showImg  = null;
    CameraPhotoCapture CameraActivity ;
    Button clearimage_bt,uploadimage_bt,cancelimage_bt;
     
    public static String imagepathfordeletion="";
    public static String imagepathforupload="";
    
    public static String compressedfilepaths="";
    
    SharedPreferences pref;
	  Editor editor;
	  public static final String  DigitalsignfilePREF= "digitalsignpreffile";
	  public static final String DigitalsignpathKEY = "digitalsignpath";
    Bitmap scaledBitmap = null;
    String str_cameraflag="";
    SharedPreferences sharedpref_camera_Obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_photo_capture);
        CameraActivity = this;


//        SharedPreferences myprefs = this.getSharedPreferences(sharedpreferenc_camera, Context.MODE_PRIVATE);
//        str_cameraflag = myprefs.getString(key_flagcamera, "nothing");

       /* sharedpref_camera_Obj=getSharedPreferences(sharedpreferenc_camera, Context.MODE_PRIVATE);
        str_cameraflag = sharedpref_camera_Obj.getString(key_flagcamera, "").trim();*/

        //imageDetails = (TextView) findViewById(R.id.imageDetails);
         
        showImg = findViewById(R.id.showImg);
         
        final Button photo = findViewById(R.id.photo);
        
        clearimage_bt = findViewById(R.id.clearall_BT);
        uploadimage_bt = findViewById(R.id.upload_BT);
        cancelimage_bt= findViewById(R.id.cancel_BT);
         
        clearimage_bt.setEnabled(false);
        uploadimage_bt.setEnabled(false);
        
        clearimage_bt.setVisibility(View.GONE);
        uploadimage_bt.setVisibility(View.GONE);



        Camera_call();
        //*-----------------
      //SharedPreferences
        pref = getApplicationContext().getSharedPreferences(DigitalsignfilePREF, MODE_PRIVATE);
 		 editor = pref.edit();
 		 
 		editor.putString(DigitalsignpathKEY, "");
		 editor.commit();
		 
        
//        photo.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//
//              /*************************** Camera Intent Start ************************/
//
//                // Define the file-name to save photo taken by Camera activity
//
//                String fileName = "Camera_Example.jpg";
//
//                // Create parameters for Intent with filename
//
//                ContentValues values = new ContentValues();
//
//                values.put(MediaStore.Images.Media.TITLE, fileName);
//
//                values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
//
//                // imageUri is the current activity attribute, define and save it for later usage
//
//                imageUri = getContentResolver().insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//                /**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****/
//
//
//                // Standard Intent action that can be sent to have the camera
//                // application capture an image and return it.
//
//                Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
//
//                 intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//
//                 intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//
//                startActivityForResult( intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//
//             /*************************** Camera Intent End ************************/
//
//                photo.setEnabled(false);
//                clearimage_bt.setEnabled(true);
//                uploadimage_bt.setEnabled(true);
//                photo.setVisibility(View.GONE);
//                clearimage_bt.setVisibility(View.VISIBLE);
//                uploadimage_bt.setVisibility(View.VISIBLE);
//
//            }
//
//        });
    }

    public void Camera_call() {
        /*************************** Camera Intent Start ************************/

        // Define the file-name to save photo taken by Camera activity

        String fileName = "Camera_Example.jpg";

        // Create parameters for Intent with filename

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, fileName);

        values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");

        // imageUri is the current activity attribute, define and save it for later usage

        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        /**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****/


        // Standard Intent action that can be sent to have the camera
        // application capture an image and return it.

        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult( intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        /*************************** Camera Intent End ************************/

       // photo.setEnabled(false);
       // clearimage_bt.setEnabled(true);
        uploadimage_bt.setEnabled(true);
        //photo.setVisibility(View.GONE);
      //  clearimage_bt.setVisibility(View.VISIBLE);
        uploadimage_bt.setVisibility(View.VISIBLE);

    }


    @Override
     protected void onActivityResult( int requestCode, int resultCode, Intent data)
        {
            if ( requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                 
                if ( resultCode == RESULT_OK) {
                 
                   /*********** Load Captured Image And Data Start ****************/
                     
                    String imageId = convertImageUriToFile( imageUri,CameraActivity);
                     
 
                   //  Create and excecute AsyncTask to load capture image
 
                    new LoadImagesFromSDCard().execute(""+imageId);
                     
                  /*********** Load Captured Image And Data End ****************/
                     
                
                } else if ( resultCode == RESULT_CANCELED) {
                     
                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
                } else {
                     
                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
                }
            }
        }
      
      
     /************ Convert Image Uri path to physical path **************/
      
     public static String convertImageUriToFile ( Uri imageUri, Activity activity )  {
      
            Cursor cursor = null;
            int imageID = 0;
             
            try {
             
                /*********** Which columns values want to get *******/
                String [] proj={
                                 MediaStore.Images.Media.DATA,
                                 MediaStore.Images.Media._ID,
                                 MediaStore.Images.Thumbnails._ID,
                                 MediaStore.Images.ImageColumns.ORIENTATION
                               };
                 
//                cursor = activity.managedQuery(
//
//                                imageUri,         //  Get data for specific image URI
//                                proj,             //  Which columns to return
//                                null,             //  WHERE clause; which rows to return (all rows)
//                                null,             //  WHERE clause selection arguments (none)
//                                null              //  Order-by clause (ascending by name)
//
//                             );



                cursor = activity.getContentResolver().query(

                        imageUri,         //  Get data for specific image URI
                        proj,             //  Which columns to return
                        null,             //  WHERE clause; which rows to return (all rows)
                        null,             //  WHERE clause selection arguments (none)
                        null              //  Order-by clause (ascending by name)

                );






                //  Get Query Data
                 
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
                int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                 
                //int orientation_ColumnIndex = cursor.
                //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
                 
                int size = cursor.getCount();
                 
                /*******  If size is 0, there are no images on the SD Card. *****/
                 
                if (size == 0) {
 
 
                    //imageDetails.setText("No Image");
                }
                else
                {
                
                    int thumbID = 0;
                    if (cursor.moveToFirst()) {
                         
                        /**************** Captured image details ************/
                         
                        /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                        imageID     = cursor.getInt(columnIndex);
                         
                        thumbID     = cursor.getInt(columnIndexThumb);
                         
                        String Path = cursor.getString(file_ColumnIndex);
                        
                        
                        imagepathfordeletion = Path;
                        imagepathforupload= Path;

                        Log.v("log_tag","imagepathforupload"+imagepathforupload);
                        //String orientation =  cursor.getString(orientation_ColumnIndex);
                         
                        String CapturedImageDetails = " CapturedImageDetails : \n\n"
                                                          +" ImageID :"+imageID+"\n"
                                                          +" ThumbID :"+thumbID+"\n"
                                                          +" Path :"+Path+"\n";
                         
                        // Show Captured Image detail on activity
                        //imageDetails.setText( CapturedImageDetails );
                         
                    }
                }    
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
             
            // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )
             
            return ""+imageID;
        }
      
      
         /**
         * Async task for loading the images from the SD card.
         *
         * @author Android Example
         *
         */
          
        // Class with extends AsyncTask class
         
     public class LoadImagesFromSDCard  extends AsyncTask<String, Void, Void> {
             
            private ProgressDialog Dialog = new ProgressDialog(CameraPhotoCapture.this);
             
            Bitmap mBitmap;
             
            protected void onPreExecute() {
                /****** NOTE: You can call UI Element here. *****/
                 
                // Progress Dialog
                Dialog.setMessage(" Loading the image..");
                Dialog.show();
            }
 
 
            // Call after onPreExecute method
            protected Void doInBackground(String... urls) {
                 
                Bitmap bitmap = null;
                Bitmap newBitmap = null;
                Uri uri = null;       
                     
                     
                    try {
                         
                        /**  Uri.withAppendedPath Method Description
                        * Parameters
                        *    baseUri  Uri to append path segment to
                        *    pathSegment  encoded path segment to append
                        * Returns
                        *    a new Uri based on baseUri with the given segment appended to the path
                        */
                         
                        uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);
                         Log.i("log_tag","URI"+uri);
                        /**************  Decode an input stream into a bitmap. *********/
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                         
                        compressImage(imagepathfordeletion); // imagepathfordeletion is original path of image
                        									 // calling the compressImage method
                        if (bitmap != null) {
                             
                            /********* Creates a new bitmap, scaled from an existing bitmap. ***********/
 
                            newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);
                             
                            bitmap.recycle();
                             
                            if (newBitmap != null) {
                                 
                                mBitmap = newBitmap;
                            }
                        }
                    } catch (IOException e) {
                        // Error fetching image, try to recover
                         
                        /********* Cancel execution of this task. **********/
                        cancel(true);
                    }
                 
                return null;
            }
             
             
            protected void onPostExecute(Void unused) {
                 
                // NOTE: You can call UI Element here.
                 
                // Close progress dialog
                  Dialog.dismiss();
                 
                if(mBitmap != null)
                {
                  // Set Image to ImageView
                    //Commented and added by shivaleela on aug 9th 2019
//                    showImg.setImageBitmap(mBitmap);
//
//                   photo_iv.setImageBitmap(mBitmap);

                    if(str_cameraflag.equals("1"))
                    {
                        showImg.setImageBitmap(scaledBitmap);
                        //img_profilePick.setImageBitmap(scaledBitmap);
                        add_farmerimage_iv.setImageBitmap(scaledBitmap);
//                        SharedPreferences myprefs_flag_camera = CameraPhotoCapture.this.getSharedPreferences("cameraflag", MODE_PRIVATE);
//                        myprefs_flag_camera.edit().putString("flag_camera", "0").apply();
//

                       /* Editor  myprefs_camera= sharedpref_camera_Obj.edit();
                        myprefs_camera.putString(key_flagcamera, "0");
                        myprefs_camera.apply();*/


                    }else{
                        showImg.setImageBitmap(scaledBitmap);
                        //img_profilePick.setImageBitmap(scaledBitmap);
                        add_farmerimage_iv.setImageBitmap(scaledBitmap);
                    }
                }
                 
            }
             
        }
	
	
	
     public void clearimage(View view)
     {

    	    ContentResolver contentResolver = getContentResolver();
    	    contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
    	                MediaStore.Images.ImageColumns.DATA + "=?" , new String[]{imagepathfordeletion  });
    	   
    	    imagepathforupload="";
    	    compressedfilepaths="";
//    	 Intent i = new Intent(getApplicationContext(), CameraPhotoCapture.class);
//     	startActivity(i);
     	finish();
     }
	
	
     public void uploadimage(View view)
     {
    	 
    	// editor.putString(DigitalsignpathKEY, imagepathforupload.toString());
    	 //editor.putString(DigitalsignpathKEY, compressedfilepaths.toString());
		// editor.commit();
    	 /*Intent i = new Intent(getApplicationContext(), RegistA.class);
      	startActivity(i);*/
		 
		 /*Bundle b = new Bundle();
         b.putString("status", "done");
         Intent intent = new Intent();
         intent.putExtras(b);
         setResult(RESULT_OK,intent);   
         */

		 //Commented and added by shivaleela on aug 9th 2019
		// Activity_Register.fee_checkbox.setEnabled(false);
//           Intent i = new Intent(getApplicationContext(), Activity_Register_New.class);
//           startActivity(i);


         //*---------------------------------------------
      	finish();
    	 
     }
	
	
     public void cancelimage(View view)
     {
         //Commented and added by shivaleela on aug 9th 2019
//         Activity_Register.fee_checkbox.setEnabled(true);
//         Activity_Register.fee_checkbox.setChecked(false);
//         Activity_Register.receipt_no_tv.setVisibility(View.GONE);
//         Activity_Register.receipt_no_label_tv.setVisibility(View.GONE);

//
         if(str_cameraflag.equals("1"))
         {
             //img_profilePick.setImageResource(R.drawable.profileimg);
             add_farmerimage_iv.setImageResource(R.drawable.profileimg);
             showImg.setImageBitmap(null);
//             SharedPreferences myprefs_flag_camera = this.getSharedPreferences("cameraflag", MODE_PRIVATE);
//             myprefs_flag_camera.edit().putString("flag_camera", "0").apply();


            /* Editor  myprefs_camera= sharedpref_camera_Obj.edit();
             myprefs_camera.putString(key_flagcamera, "0");
             myprefs_camera.apply();*/

             finish();
         }else {
            // img_profilePick.setImageResource(R.drawable.profileimg);
             add_farmerimage_iv.setImageResource(R.drawable.profileimg);
             showImg.setImageBitmap(null);
             finish();
         }
     }
	
	
     public void onBackPressed()
	 {
	     Toast.makeText(this, "Click on cancel to exit from application!", Toast.LENGTH_SHORT).show();
     }
	
	
     
     
     //compression of image
     
     private String getRealPathFromURI(String contentURI) {
         Uri contentUri = Uri.parse(contentURI);
         Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
         if (cursor == null) {
             return contentUri.getPath();
         } else {
             cursor.moveToFirst();
             int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
             return cursor.getString(index);
         }
     }
     
     public String getFilename() {
         File file = new File(Environment.getExternalStorageDirectory().getPath(), "DetSkillsSign/Images");
         if (!file.exists()) {
             file.mkdirs();
         }
        // String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
         String uriSting = (file.getAbsolutePath() + "/" + "f"+ System.currentTimeMillis() + ".png");
         return uriSting;

     }
     
     public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
         final int height = options.outHeight;
         final int width = options.outWidth;
         int inSampleSize = 1;

         if (height > reqHeight || width > reqWidth) {
             final int heightRatio = Math.round((float) height / (float) reqHeight);
             final int widthRatio = Math.round((float) width / (float) reqWidth);
             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
         }
         final float totalPixels = width * height;
         final float totalReqPixelsCap = reqWidth * reqHeight * 2;
         while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
             inSampleSize++;
         }

         return inSampleSize;
     }
     
     public String compressImage(String imageUri) {

         String filePath = getRealPathFromURI(imageUri);
//         Bitmap scaledBitmap = null;

         BitmapFactory.Options options = new BitmapFactory.Options();

//       by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//       you try the use the bitmap here, you will get null.
         options.inJustDecodeBounds = true;
         Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

         int actualHeight = options.outHeight;
         int actualWidth = options.outWidth;

//       max Height and width values of the compressed image is taken as 816x612

         /*float maxHeight = 816.0f;
         float maxWidth = 612.0f;*/
         
         
         float maxHeight = 216.0f;
         float maxWidth = 212.0f;
         float imgRatio = actualWidth / actualHeight;
         float maxRatio = maxWidth / maxHeight;

//       width and height values are set maintaining the aspect ratio of the image

         if (actualHeight > maxHeight || actualWidth > maxWidth) {
             if (imgRatio < maxRatio) {
                 imgRatio = maxHeight / actualHeight;
                 actualWidth = (int) (imgRatio * actualWidth);
                 actualHeight = (int) maxHeight;
             } else if (imgRatio > maxRatio) {
                 imgRatio = maxWidth / actualWidth;
                 actualHeight = (int) (imgRatio * actualHeight);
                 actualWidth = (int) maxWidth;
             } else {
                 actualHeight = (int) maxHeight;
                 actualWidth = (int) maxWidth;

             }
         }

//       setting inSampleSize value allows to load a scaled down version of the original image

         options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//       inJustDecodeBounds set to false to load the actual bitmap
         options.inJustDecodeBounds = false;

//       this options allow android to claim the bitmap memory if it runs low on memory
         options.inPurgeable = true;
         options.inInputShareable = true;
         options.inTempStorage = new byte[16 * 1024];

         try {
//           load the bitmap from its path
             bmp = BitmapFactory.decodeFile(filePath, options);
         } catch (OutOfMemoryError exception) {
             exception.printStackTrace();

         }
         try {
             scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
         } catch (OutOfMemoryError exception) {
             exception.printStackTrace();
         }

         float ratioX = actualWidth / (float) options.outWidth;
         float ratioY = actualHeight / (float) options.outHeight;
         float middleX = actualWidth / 2.0f;
         float middleY = actualHeight / 2.0f;

         Matrix scaleMatrix = new Matrix();
         scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

         Canvas canvas = new Canvas(scaledBitmap);
         canvas.setMatrix(scaleMatrix);
         canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//       check the rotation of the image and display it properly
         ExifInterface exif;
         try {
             exif = new ExifInterface(filePath);

             int orientation = exif.getAttributeInt(
                     ExifInterface.TAG_ORIENTATION, 0);
             Log.d("EXIF", "Exif: " + orientation);
             Matrix matrix = new Matrix();
             if (orientation == 6) {
                 matrix.postRotate(90);
                 Log.d("EXIF", "Exif: " + orientation);
             } else if (orientation == 3) {
                 matrix.postRotate(180);
                 Log.d("EXIF", "Exif: " + orientation);
             } else if (orientation == 8) {
                 matrix.postRotate(270);
                 Log.d("EXIF", "Exif: " + orientation);
             }
             scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                     scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                     true);
         } catch (IOException e) {
             e.printStackTrace();
         }

         FileOutputStream out = null;
         String filename = getFilename();
        		 compressedfilepaths=filename;

         try {
             out = new FileOutputStream(filename);

//           write the compressed bitmap at the destination specified by filename.
             //scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

             scaledBitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
             	
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

         return filename;

     }
     
     
     
	
	
}// end of class



	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_photo_capture);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera_photo_capture, menu);
		return true;
	}

}
*/