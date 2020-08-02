package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFarmerRequestTest {

    @SerializedName("Farmer_ID")
    @Expose
    private String farmerID;
   /* @SerializedName("State_ID")
    @Expose
    private String stateID;
    @SerializedName("District_ID")
    @Expose
    private String districtID;
    @SerializedName("Taluka_ID")
    @Expose
    private String talukaID;
    @SerializedName("Panchayat_ID")
    @Expose
    private String panchayatID;*/
    @SerializedName("Village_ID")
    @Expose
    private String villageID;
    @SerializedName("Farmer_First_Name")
    @Expose
    private String farmerFirstName;
    /*@SerializedName("Farmer_Middle_Name")
    @Expose
    private String farmerMiddleName;
    @SerializedName("Farmer_Last_Name")
    @Expose
    private String farmerLastName;*/
    @SerializedName("Farmer_Mobile")
    @Expose
    private String farmerMobile;
   /* @SerializedName("Farmer_ID_Type")
    @Expose
    private Object farmerIDType;
    @SerializedName("Farmer_ID_Number")
    @Expose
    private String farmerIDNumber;*/
   /* @SerializedName("Farmer_Photo")
    @Expose
    private String farmerPhoto;
    @SerializedName("Farmer_Age")
    @Expose
    private String farmerAge;
    @SerializedName("Farmer_Income")
    @Expose
    private Object farmerIncome;
    @SerializedName("Farmer_Family")
    @Expose
    private Object farmerFamily;*/
    @SerializedName("Submitted_Date")
    @Expose
    private String submittedDate;
    @SerializedName("Created_By")
    @Expose
    private String createdBy;
    @SerializedName("Mobile_Temp_ID")
    @Expose
    private Object mobileTempID;

    public String getFarmerID() {
        return farmerID;
    }

    public void setFarmerID(String farmerID) {
        this.farmerID = farmerID;
    }

    public String getVillageID() {
        return villageID;
    }

    public void setVillageID(String villageID) {
        this.villageID = villageID;
    }

    public String getFarmerFirstName() {
        return farmerFirstName;
    }

    public void setFarmerFirstName(String farmerFirstName) {
        this.farmerFirstName = farmerFirstName;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Object getMobileTempID() {
        return mobileTempID;
    }

    public void setMobileTempID(Object mobileTempID) {
        this.mobileTempID = mobileTempID;
    }

    public String getFarmerMobile() {
        return farmerMobile;
    }

    public void setFarmerMobile(String farmerMobile) {
        this.farmerMobile = farmerMobile;
    }
}
