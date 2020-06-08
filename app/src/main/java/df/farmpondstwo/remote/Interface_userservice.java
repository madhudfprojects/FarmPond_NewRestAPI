package df.farmpondstwo.remote;

import com.google.android.gms.fido.u2f.api.common.ResponseData;
import com.google.gson.JsonObject;

import java.util.Map;

import df.farmpondstwo.Class_farmponddetails_ToServer;
import df.farmpondstwo.Models.Location_Data;
import df.farmpondstwo.Models.NormalLogin_Response;
import df.farmpondstwo.Models.UserData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Interface_userservice {

  /*  @POST("api/Authentication/ValidateLogin")
    @FormUrlEncoded*/
    Call<NormalLogin_Response> getValidateLoginPost(@FieldMap Map<String, String> params);

    @POST("Authentication/Post_ValidateLogin")
    @FormUrlEncoded
    Call<NormalLogin_Response> getValidateLoginPostNew(@Field("User_Email") String userEmail);

  // @FormUrlEncoded
  @Headers("Content-Type: application/json;charset=utf-8")
  @GET("Authentication/Get_UserLocation")
  Call<Location_Data> getLocationData(@Query("User_ID") String User_ID);

  //  @FormUrlEncoded
  @Headers("Content-Type: application/json")
  @GET("Authentication/UserLocation")
  Call<Location_Data> getLocationDataNew(@Query("User_ID") String User_ID);

  @Headers("Content-Type: application/json")
  @GET("Authentication/Get_UserData")
  Call<UserData> getUserData(@Query("User_ID") String User_ID);


//https://agri.dfindia.org/api/Authentication/Post_ActionFarmerPondData
  @Headers({ "Content-Type: application/json;charset=UTF-8"})
  @POST("Authentication/Post_ActionFarmerPondData")
  Call<ResponseData>Post_ActionFarmerPondData(@Body Class_farmponddetails_ToServer request);



}
