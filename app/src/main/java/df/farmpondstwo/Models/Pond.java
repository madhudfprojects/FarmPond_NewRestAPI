package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pond {
    @SerializedName("Pond_ID")
    @Expose
    private String pondID;
    @SerializedName("Farmer_ID")
    @Expose
    private String farmerID;
    @SerializedName("Academic_ID")
    @Expose
    private String academicID;
    @SerializedName("Machine_ID")
    @Expose
    private String machineID;
    @SerializedName("Pond_Code")
    @Expose
    private String pondCode;
    @SerializedName("Pond_Latitude")
    @Expose
    private String pondLatitude;
    @SerializedName("Pond_Longitude")
    @Expose
    private String pondLongitude;
    @SerializedName("Pond_Length")
    @Expose
    private String pondLength;
    @SerializedName("Pond_Width")
    @Expose
    private String pondWidth;
    @SerializedName("Pond_Depth")
    @Expose
    private String pondDepth;
    @SerializedName("Pond_Start")
    @Expose
    private String pondStart;
    @SerializedName("Pond_End")
    @Expose
    private String pondEnd;
    @SerializedName("Pond_Days")
    @Expose
    private String pondDays;
    @SerializedName("Pond_Cost")
    @Expose
    private String pondCost;
    @SerializedName("Pond_Image_1")
    @Expose
    private Object pondImage1;
    @SerializedName("Pond_Image_2")
    @Expose
    private Object pondImage2;
    @SerializedName("Pond_Image_3")
    @Expose
    private Object pondImage3;
    @SerializedName("Pond_Status")
    @Expose
    private Object pondStatus;
    @SerializedName("Submitted_Date")
    @Expose
    private String submittedDate;
    @SerializedName("Submitted_By")
    @Expose
    private Object submittedBy;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
    @SerializedName("Created_By")
    @Expose
    private String createdBy;
    @SerializedName("Pond_Temp_ID")
    @Expose
    private Object pondTempID;
    @SerializedName("Response")
    @Expose
    private Object response;
    @SerializedName("Created_User")
    @Expose
    private String createdUser;
    @SerializedName("Submitted_User")
    @Expose
    private Object submittedUser;

    public String getPondID() {
        return pondID;
    }

    public void setPondID(String pondID) {
        this.pondID = pondID;
    }

    public String getFarmerID() {
        return farmerID;
    }

    public void setFarmerID(String farmerID) {
        this.farmerID = farmerID;
    }

    public String getAcademicID() {
        return academicID;
    }

    public void setAcademicID(String academicID) {
        this.academicID = academicID;
    }

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

    public String getPondCode() {
        return pondCode;
    }

    public void setPondCode(String pondCode) {
        this.pondCode = pondCode;
    }

    public String getPondLatitude() {
        return pondLatitude;
    }

    public void setPondLatitude(String pondLatitude) {
        this.pondLatitude = pondLatitude;
    }

    public String getPondLongitude() {
        return pondLongitude;
    }

    public void setPondLongitude(String pondLongitude) {
        this.pondLongitude = pondLongitude;
    }

    public String getPondLength() {
        return pondLength;
    }

    public void setPondLength(String pondLength) {
        this.pondLength = pondLength;
    }

    public String getPondWidth() {
        return pondWidth;
    }

    public void setPondWidth(String pondWidth) {
        this.pondWidth = pondWidth;
    }

    public String getPondDepth() {
        return pondDepth;
    }

    public void setPondDepth(String pondDepth) {
        this.pondDepth = pondDepth;
    }

    public String getPondStart() {
        return pondStart;
    }

    public void setPondStart(String pondStart) {
        this.pondStart = pondStart;
    }

    public String getPondEnd() {
        return pondEnd;
    }

    public void setPondEnd(String pondEnd) {
        this.pondEnd = pondEnd;
    }

    public String getPondDays() {
        return pondDays;
    }

    public void setPondDays(String pondDays) {
        this.pondDays = pondDays;
    }

    public String getPondCost() {
        return pondCost;
    }

    public void setPondCost(String pondCost) {
        this.pondCost = pondCost;
    }

    public Object getPondImage1() {
        return pondImage1;
    }

    public void setPondImage1(Object pondImage1) {
        this.pondImage1 = pondImage1;
    }

    public Object getPondImage2() {
        return pondImage2;
    }

    public void setPondImage2(Object pondImage2) {
        this.pondImage2 = pondImage2;
    }

    public Object getPondImage3() {
        return pondImage3;
    }

    public void setPondImage3(Object pondImage3) {
        this.pondImage3 = pondImage3;
    }

    public Object getPondStatus() {
        return pondStatus;
    }

    public void setPondStatus(Object pondStatus) {
        this.pondStatus = pondStatus;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Object getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(Object submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Object getPondTempID() {
        return pondTempID;
    }

    public void setPondTempID(Object pondTempID) {
        this.pondTempID = pondTempID;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Object getSubmittedUser() {
        return submittedUser;
    }

    public void setSubmittedUser(Object submittedUser) {
        this.submittedUser = submittedUser;
    }
}
