package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClusterFarmersummaryList {
    @SerializedName("Farmer_ID")
    @Expose
    private String farmerID;
    @SerializedName("Farmer_Name")
    @Expose
    private String farmerName;
    @SerializedName("Farmer_Mobile")
    @Expose
    private String farmerMobile;
    @SerializedName("Pond_Count")
    @Expose
    private String pondCount;
    @SerializedName("Employee_ID")
    @Expose
    private String employeeID;
    @SerializedName("Employee_Name")
    @Expose
    private String employeeName;
    @SerializedName("Farmer_Photo")
    @Expose
    private String farmerPhoto;
    @SerializedName("Village_Name")
    @Expose
    private String villageName;
    @SerializedName("Pond_Status")
    @Expose
    private String pondStatus;

    private String YearId;
    private String Type;

    public ClusterFarmersummaryList() {
    }

    public ClusterFarmersummaryList(String employeeName, String employeeID, String farmerID, String farmerName, String farmerMobile, String pondCount, String pondStatus, String villageName, String farmerPhoto, String YearId,String Type) {
        this.employeeName=employeeName;
        this.employeeID=employeeID;
        this.farmerID=farmerID;
        this.farmerName=farmerName;
        this.farmerMobile=farmerMobile;
        this.pondCount=pondCount;
        this.pondStatus=pondStatus;
        this.villageName=villageName;
        this.farmerPhoto=farmerPhoto;
        this.YearId=YearId;
        this.Type=Type;
    }

    public String getFarmerID() {
        return farmerID;
    }

    public void setFarmerID(String farmerID) {
        this.farmerID = farmerID;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerMobile() {
        return farmerMobile;
    }

    public void setFarmerMobile(String farmerMobile) {
        this.farmerMobile = farmerMobile;
    }

    public String getPondCount() {
        return pondCount;
    }

    public void setPondCount(String pondCount) {
        this.pondCount = pondCount;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getFarmerPhoto() {
        return farmerPhoto;
    }

    public void setFarmerPhoto(String farmerPhoto) {
        this.farmerPhoto = farmerPhoto;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getPondStatus() {
        return pondStatus;
    }

    public void setPondStatus(String pondStatus) {
        this.pondStatus = pondStatus;
    }

    public String getYearId() {
        return YearId;
    }

    public void setYearId(String yearId) {
        YearId = yearId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
