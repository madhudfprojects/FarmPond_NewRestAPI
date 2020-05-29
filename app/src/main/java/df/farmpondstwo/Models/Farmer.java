package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Farmer {

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
    private String farmerIDType;
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
    private String farmerIncome;
    @SerializedName("Farmer_Family")
    @Expose
    private String farmerFamily;
    @SerializedName("Submitted_Date")
    @Expose
    private String submittedDate;
    @SerializedName("Created_By")
    @Expose
    private String createdBy;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
    @SerializedName("Mobile_Temp_ID")
    @Expose
    private String mobileTempID;
    @SerializedName("Created_User")
    @Expose
    private String createdUser;
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("Response_Action")
    @Expose
    private String responseAction;
    @SerializedName("Academic_ID")
    @Expose
    private String academic_ID;
    @SerializedName("Academic_Name")
    @Expose
    private String academic_Name;
    @SerializedName("Farmer_Code")
    @Expose
    private String farmer_Code;
    @SerializedName("FarmPond_Count")
    @Expose
    private String farmPond_Count;

    String str_base64;
    byte[] localfarmerimage;

    public Farmer(String dispFarmerTable_yearID, String dispFarmerTable_stateID, String dispFarmerTable_districtID, String dispFarmerTable_talukID, String dispFarmerTable_villageID,
                  String dispFarmerTable_grampanchayatID, String dispFarmerTable_farmerID, String dispFarmerTable_farmerName, String dispFarmerTable_farmerImage, String farmerImageB64str_db,
                  String farmerMName_db, String farmerLName_db, String farmerage_db, String farmercellno_db, String fIncome_db, String ffamilymember_db,
                  String fiDprooftype_db, String fidProofNo_db, String farmpondcount, byte[] localFarmerImgs) {
    academic_ID=dispFarmerTable_yearID;
    stateID=dispFarmerTable_stateID;
    districtID=dispFarmerTable_districtID;
    talukaID=dispFarmerTable_talukID;
    villageID=dispFarmerTable_villageID;
    panchayatID=dispFarmerTable_grampanchayatID;
    farmerID=dispFarmerTable_farmerID;
    farmerFirstName=dispFarmerTable_farmerName;
    farmerPhoto=dispFarmerTable_farmerImage;
    farmerMiddleName=farmerMName_db;
    farmerLastName=farmerLName_db;
    farmerAge=farmerage_db;
    farmerMobile=farmercellno_db;
    farmerIncome=fIncome_db;
    farmerFamily=ffamilymember_db;
    farmerIDType=fiDprooftype_db;
    farmerIDNumber=fidProofNo_db;
    farmPond_Count=farmpondcount;
    str_base64=farmerImageB64str_db;
    localfarmerimage=localFarmerImgs;
    }

    public Farmer() {

    }

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

    public String getFarmerIDType() {
        return farmerIDType;
    }

    public void setFarmerIDType(String farmerIDType) {
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

    public String getFarmerIncome() {
        return farmerIncome;
    }

    public void setFarmerIncome(String farmerIncome) {
        this.farmerIncome = farmerIncome;
    }

    public String getFarmerFamily() {
        return farmerFamily;
    }

    public void setFarmerFamily(String farmerFamily) {
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getMobileTempID() {
        return mobileTempID;
    }

    public void setMobileTempID(String mobileTempID) {
        this.mobileTempID = mobileTempID;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponseAction() {
        return responseAction;
    }

    public void setResponseAction(String responseAction) {
        this.responseAction = responseAction;
    }

    public String getAcademic_ID() {
        return academic_ID;
    }

    public void setAcademic_ID(String academic_ID) {
        this.academic_ID = academic_ID;
    }

    public String getAcademic_Name() {
        return academic_Name;
    }

    public void setAcademic_Name(String academic_Name) {
        this.academic_Name = academic_Name;
    }

    public String getFarmer_Code() {
        return farmer_Code;
    }

    public void setFarmer_Code(String farmer_Code) {
        this.farmer_Code = farmer_Code;
    }

    public String getFarmPond_Count() {
        return farmPond_Count;
    }

    public void setFarmPond_Count(String farmPond_Count) {
        this.farmPond_Count = farmPond_Count;
    }

    public String getStr_base64() {
        return str_base64;
    }

    public void setStr_base64(String str_base64) {
        this.str_base64 = str_base64;
    }

    public byte[] getLocalfarmerimage() {
        return localfarmerimage;
    }

    public void setLocalfarmerimage(byte[] localfarmerimage) {
        this.localfarmerimage = localfarmerimage;
    }

    @Override
    public String toString() {
        return farmerFirstName;
    }
}


