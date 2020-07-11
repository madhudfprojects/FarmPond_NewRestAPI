package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFarmerRequest {

    @SerializedName("Farmer_ID")
    @Expose
    private String farmerID;
    @SerializedName("State_ID")
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
    private String panchayatID;
    @SerializedName("Village_ID")
    @Expose
    private String villageID;
    @SerializedName("Farmer_First_Name")
    @Expose
    private String farmerFirstName;
    @SerializedName("Farmer_Middle_Name")
    @Expose
    private String farmerMiddleName;
    @SerializedName("Farmer_Last_Name")
    @Expose
    private String farmerLastName;
    @SerializedName("Farmer_Mobile")
    @Expose
    private String farmerMobile;
    @SerializedName("Farmer_ID_Type")
    @Expose
    private Object farmerIDType;
    @SerializedName("Farmer_ID_Number")
    @Expose
    private String farmerIDNumber;
    @SerializedName("Farmer_Photo")
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
    private Object farmerFamily;
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

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    public String getTalukaID() {
        return talukaID;
    }

    public void setTalukaID(String talukaID) {
        this.talukaID = talukaID;
    }

    public String getPanchayatID() {
        return panchayatID;
    }

    public void setPanchayatID(String panchayatID) {
        this.panchayatID = panchayatID;
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

    public String getFarmerMiddleName() {
        return farmerMiddleName;
    }

    public void setFarmerMiddleName(String farmerMiddleName) {
        this.farmerMiddleName = farmerMiddleName;
    }

    public String getFarmerLastName() {
        return farmerLastName;
    }

    public void setFarmerLastName(String farmerLastName) {
        this.farmerLastName = farmerLastName;
    }

    public String getFarmerMobile() {
        return farmerMobile;
    }

    public void setFarmerMobile(String farmerMobile) {
        this.farmerMobile = farmerMobile;
    }

    public Object getFarmerIDType() {
        return farmerIDType;
    }

    public void setFarmerIDType(Object farmerIDType) {
        this.farmerIDType = farmerIDType;
    }

    public String getFarmerIDNumber() {
        return farmerIDNumber;
    }

    public void setFarmerIDNumber(String farmerIDNumber) {
        this.farmerIDNumber = farmerIDNumber;
    }

    public String getFarmerPhoto() {
        return farmerPhoto;
    }

    public void setFarmerPhoto(String farmerPhoto) {
        this.farmerPhoto = farmerPhoto;
    }

    public String getFarmerAge() {
        return farmerAge;
    }

    public void setFarmerAge(String farmerAge) {
        this.farmerAge = farmerAge;
    }

    public Object getFarmerIncome() {
        return farmerIncome;
    }

    public void setFarmerIncome(Object farmerIncome) {
        this.farmerIncome = farmerIncome;
    }

    public Object getFarmerFamily() {
        return farmerFamily;
    }

    public void setFarmerFamily(Object farmerFamily) {
        this.farmerFamily = farmerFamily;
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



}
