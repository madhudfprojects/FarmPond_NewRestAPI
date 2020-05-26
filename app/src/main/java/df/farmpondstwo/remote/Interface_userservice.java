package df.farmpondstwo.remote;

import com.google.gson.JsonObject;

import java.util.Map;

import df.farmpondstwo.Models.Location_Data;
import df.farmpondstwo.Models.NormalLogin_Response;
import df.farmpondstwo.Models.UserData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
}
