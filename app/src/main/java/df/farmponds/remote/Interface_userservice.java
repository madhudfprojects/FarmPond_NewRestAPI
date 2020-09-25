package df.farmponds.remote;

import df.farmponds.Class_addfarmponddetails_ToFromServer1;
import df.farmponds.Class_addfarmponddetails_ToFromServer2;
import df.farmponds.Class_cluster_approvereject_request;
import df.farmponds.Class_devicedetails;
import df.farmponds.Models.AddFarmerRequest;
import df.farmponds.Models.AddFarmerResponse;
import df.farmponds.Models.AdminEmpoyeeTotalPondCount;
import df.farmponds.Models.AutoSyncVersion;
import df.farmponds.Models.ClusterAcademicEmployee;
import df.farmponds.Models.ClusterAcademicSummary;
import df.farmponds.Models.ClusterFarmerMain;
import df.farmponds.Models.ClusterFarmpond;
import df.farmponds.Models.GetAppVersion;
import df.farmponds.Models.Location_Data;
import df.farmponds.Models.NormalLogin_Response;
import df.farmponds.Models.UserData;
import df.farmponds.Models.UserDataSummary;
import df.farmponds.Models.ValidateSyncRequest;
import df.farmponds.Models.ValidateSyncResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Interface_userservice {

  /*  @POST("api/Authentication/ValidateLogin")
    @FormUrlEncoded*/
   // Call<NormalLogin_Response> getValidateLoginPost(@FieldMap Map<String, String> params);

    @POST("Authentication/Post_ValidateLogin")
    @FormUrlEncoded
    Call<NormalLogin_Response> getValidateLoginPostNew(@Field("User_Email") String userEmail);

  // @FormUrlEncoded
  @Headers("Content-Type: application/json;charset=utf-8")
  @GET("Authentication/Get_UserLocation")
  Call<Location_Data> getLocationData(@Query("User_ID") String User_ID);

  //  @FormUrlEncoded
  /*@Headers("Content-Type: application/json")
  @GET("Authentication/UserLocation")
  Call<Location_Data> getLocationDataNew(@Query("User_ID") String User_ID);*/

  @Headers("Content-Type: application/json")
  @GET("Authentication/Get_UserData")
  Call<UserData> getUserData(@Query("User_ID") String User_ID);

  @Headers("Content-Type: application/json;charset=utf-8")
  @POST("Authentication/Post_ActionFarmerData")
  Call<AddFarmerResponse> AddFarmer(@Body AddFarmerRequest request);
/*
   Call<AddFarmerRequest> AddFarmer(@Part("Farmer_ID") RequestBody Farmer_ID,
                                     @Part("State_ID") RequestBody State_ID,
                                     @Part("District_ID") RequestBody District_ID,
                                     @Part("Taluka_ID") RequestBody Taluka_ID,
                                     @Part("Panchayat_ID") RequestBody Panchayat_ID,
                                     @Part("Village_ID") RequestBody Village_ID,
                                     @Part("Farmer_First_Name") RequestBody Farmer_First_Name,
                                     @Part("Farmer_Middle_Name") RequestBody Farmer_Middle_Name,
                                     @Part("Farmer_Last_Name") RequestBody Farmer_Last_Name,
                                     @Part("Farmer_Mobile") RequestBody Farmer_Mobile,
                                     @Part("Farmer_ID_Type") RequestBody Farmer_ID_Type,
                                     @Part("Farmer_ID_Number") RequestBody Farmer_ID_Number,
                                     @Part("Farmer_Photo") RequestBody Farmer_Photo,
                                     @Part("Farmer_Age") RequestBody Farmer_Age,
                                     @Part("Farmer_Income") RequestBody Farmer_Income,
                                     @Part("Farmer_Family") RequestBody Farmer_Family,
                                     @Part("Submitted_Date") RequestBody Submitted_Date,
                                     @Part("Created_By") RequestBody Created_By,
                                     @Part("Mobile_Temp_ID") RequestBody Mobile_Temp_ID);*/

  /*@Headers("Content-Type: application/json;charset=utf-8")
  @POST("Authentication/Post_ActionFarmerData_Test")
  Call<AddFarmerResponse> AddFarmerNew(@Body AddFarmerRequestTest request);*/

/*  @Multipart
  @Headers("Content-Type: application/json;charset=utf-8")
  @POST("Authentication/Post_ActionFarmerData_Test")
  Call<ResponseData>Post_ActionFarmerPondData(@Body Class_farmponddetails_ToServer request);*/

  @Headers({ "Content-Type: application/json;charset=UTF-8"})
  @POST("Authentication/Post_ActionFarmerPondData")
  Call<Class_addfarmponddetails_ToFromServer1>Post_ActionFarmerPondData(@Body Class_addfarmponddetails_ToFromServer2 request);


  @Headers({ "Content-Type: application/json;charset=UTF-8"})
  @POST("Authentication/Post_ActionDeviceDetails")
  Call<Class_devicedetails>Post_ActionDeviceDetails(@Body Class_devicedetails request);

  @GET("Authentication/Get_User_FarmerPond_Count")
  Call<AdminEmpoyeeTotalPondCount> getEmpWiseCount(@Query("User_ID") String User_ID);

  @GET("Authentication/Get_App_Version")
  Call<GetAppVersion> getAppVersion();

  @Headers({ "Content-Type: application/json;charset=UTF-8"})
  @POST("Authentication/Post_ValidateSync")
  Call<ValidateSyncResponse> Post_ValidateSync(@Body ValidateSyncRequest request);

  @GET("Authentication/Get_Sync_Version")
  Call<AutoSyncVersion> getAutoSyncVersion(@Query("User_ID") String User_ID);

  @GET("Authentication/Get_UserDataSummary")
  Call<UserDataSummary> getUserDataSummary(@Query("User_ID") String User_ID,@Query("Academic_ID") String Academic_ID);

  @Headers("Content-Type: application/json")
  @GET("Authentication/Get_UserDataReSync")
  Call<UserData> getUserDataReSync(@Query("User_ID") String User_ID);

  @Headers("Content-Type: application/json")
  @GET("Authentication/Get_UserClusterAcademicEmployeeData")
  Call<ClusterAcademicEmployee> get_UserClusterAcademicEmployeeData(@Query("User_ID") String User_ID);

  @Headers("Content-Type: application/json")
  @GET("Authentication/Get_UserClusterAcademicSummary")
  Call<ClusterAcademicSummary> get_UserClusterAcademicSummary(@Query("User_ID") String User_ID, @Query("Academic_ID") String Academic_ID);

  @Headers("Content-Type: application/json")
  @GET("Authentication/Get_UserAcademicEmployeeData")
  Call<ClusterFarmerMain> get_UserAcademicEmployeeData(@Query("User_ID") String User_ID, @Query("Academic_ID") String Academic_ID, @Query("Employee_ID") String Employee_ID, @Query("Type") String Type);

  @Headers("Content-Type: application/json")
  @GET("Authentication/Get_UserAcademicEmployeeDataList")
  Call<ClusterFarmpond> get_UserAcademicEmployeeDataList(@Query("Farmer_ID") String Farmer_ID, @Query("User_ID") String User_ID,@Query("Academic_ID") String Academic_ID);

  @Headers({ "Content-Type: application/json;charset=UTF-8"})
  @POST("Authentication/Post_ActionPondApproval")
  Call<Class_cluster_approvereject_request>Post_ActionPondApproval(@Body Class_cluster_approvereject_request request);


}
