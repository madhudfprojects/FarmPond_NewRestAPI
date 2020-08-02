package df.farmponds;

/* {

         "User_ID":"102",
         "DeviceId":"AbPiZJeOtkGN3W5hjL0GdhjDE4-N9oBVEEuqyx3JCzuIP1pC7g-FdWXT9jLyDdXbbsMvoCmVY",
         "OSVersion":"9",
         "Manufacturer":"LenovoXY",
         "ModelNo":"Lenovo PB-6705M",
         "SDKVersion":"28",
         "DeviceSrlNo":"868922042285220",
         "ServiceProvider":"",
         "SIMSrlNo":"",
         "DeviceWidth":"1080",
         "DeviceHeight":"2028",
         "AppVersion":"1.2.4"

         }*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_devicedetails
{

    @SerializedName("Status")
    @Expose
    private String Status;


    @SerializedName("Message")
    @Expose
    private String Message;




    @SerializedName("ModelNo")
    @Expose
    private String ModelNo;

    @SerializedName("DeviceHeight")
    @Expose
    private String DeviceHeight;

    @SerializedName("AppVersion")
    @Expose
    private String AppVersion;

    @SerializedName("DeviceSrlNo")
    @Expose
    private String DeviceSrlNo;

    @SerializedName("SIMSrlNo")
    @Expose
    private String SIMSrlNo;

    @SerializedName("DeviceId")
    @Expose
    private String DeviceId;

    @SerializedName("ServiceProvider")
    @Expose
    private String ServiceProvider;

    @SerializedName("Manufacturer")
    @Expose
    private String Manufacturer;

    @SerializedName("OSVersion")
    @Expose
    private String OSVersion;

    @SerializedName("User_ID")
    @Expose
    private String User_ID;

    @SerializedName("SDKVersion")
    @Expose
    private String SDKVersion;

    @SerializedName("DeviceWidth")
    @Expose
    private String DeviceWidth;


    public String getModelNo() {
        return ModelNo;
    }

    public void setModelNo(String modelNo) {
        ModelNo = modelNo;
    }

    public String getDeviceHeight() {
        return DeviceHeight;
    }

    public void setDeviceHeight(String deviceHeight) {
        DeviceHeight = deviceHeight;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getDeviceSrlNo() {
        return DeviceSrlNo;
    }

    public void setDeviceSrlNo(String deviceSrlNo) {
        DeviceSrlNo = deviceSrlNo;
    }

    public String getSIMSrlNo() {
        return SIMSrlNo;
    }

    public void setSIMSrlNo(String SIMSrlNo) {
        this.SIMSrlNo = SIMSrlNo;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getServiceProvider() {
        return ServiceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        ServiceProvider = serviceProvider;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public void setOSVersion(String OSVersion) {
        this.OSVersion = OSVersion;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getSDKVersion() {
        return SDKVersion;
    }

    public void setSDKVersion(String SDKVersion) {
        this.SDKVersion = SDKVersion;
    }

    public String getDeviceWidth() {
        return DeviceWidth;
    }

    public void setDeviceWidth(String deviceWidth) {
        DeviceWidth = deviceWidth;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
