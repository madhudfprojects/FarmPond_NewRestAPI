package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClusterFarmpondList {

    @SerializedName("Year_Name")
    @Expose
    private String yearName;
    @SerializedName("State_Name")
    @Expose
    private String stateName;
    @SerializedName("District_Name")
    @Expose
    private String districtName;
    @SerializedName("Taluka_Name")
    @Expose
    private String talukaName;
    @SerializedName("Village_Name")
    @Expose
    private String villageName;
    @SerializedName("User_ID")
    @Expose
    private Object userID;
    @SerializedName("Employee_Name")
    @Expose
    private String employeeName;
    @SerializedName("Farmer_ID")
    @Expose
    private Integer farmerID;
    @SerializedName("Farmer_Name")
    @Expose
    private String farmerName;
    @SerializedName("Farmer_Mobile")
    @Expose
    private String farmerMobile;
    @SerializedName("Pond_Code")
    @Expose
    private String pondCode;
    @SerializedName("Donor_Name")
    @Expose
    private String donorName;
    @SerializedName("Pond_Size")
    @Expose
    private String pondSize;
    @SerializedName("Pond_ID")
    @Expose
    private String pondID;
    @SerializedName("Construction_Start")
    @Expose
    private String constructionStart;
    @SerializedName("Construction_End")
    @Expose
    private String constructionEnd;
    @SerializedName("Construction_Cost")
    @Expose
    private String constructionCost;
    @SerializedName("Collected_Amount")
    @Expose
    private String collectedAmount;
    @SerializedName("Farmer_Photo")
    @Expose
    private String farmerPhoto;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
    @SerializedName("Submitted_Date")
    @Expose
    private String submittedDate;
    @SerializedName("Construction_Days")
    @Expose
    private String constructionDays;
    @SerializedName("Pond_Latitude")
    @Expose
    private String pondLatitude;
    @SerializedName("Pond_Longitude")
    @Expose
    private String pondLongitude;
    @SerializedName("Approval_Status")
    @Expose
    private String approvalStatus;
    @SerializedName("Approval_Remarks")
    @Expose
    private Object approvalRemarks;
    @SerializedName("Pond_Status")
    @Expose
    private String pondStatus;
    @SerializedName("Farmer_Age")
    @Expose
    private Object farmerAge;
    @SerializedName("Family_Members")
    @Expose
    private Object familyMembers;
    @SerializedName("Farmer_Income")
    @Expose
    private Object farmerIncome;
    @SerializedName("ID_Type")
    @Expose
    private Object iDType;
    @SerializedName("ID_proof")
    @Expose
    private Object iDProof;
    @SerializedName("Location_Status")
    @Expose
    private String locationStatus;
    @SerializedName("Pond_Image_Link1")
    @Expose
    private String pondImageLink1;
    @SerializedName("Pond_Image_Link2")
    @Expose
    private String pondImageLink2;
    @SerializedName("Pond_Image_Link3")
    @Expose
    private String pondImageLink3;


    public ClusterFarmpondList() {
    }

    public ClusterFarmpondList(String yearName, String stateName, String districtName, String talukaName, String villageName,
                               String farmerName, String farmerMobile, String pondCode, String pondSize, String constructionStart,
                               String constructionEnd, String pondLatitude, String pondLongitude, String collectedAmount,
                               String constructionCost, String constructionDays, String locationStatus, String approvalStatus,
                               String pondid,
                               String str_base64image1, String str_base64image2, String str_base64image3) {
        this.yearName=yearName;
        this.stateName=stateName;
        this.districtName=districtName;
        this.talukaName=talukaName;
        this.villageName=villageName;
        this.farmerName=farmerName;
        this.farmerMobile=farmerMobile;
        this.pondCode=pondCode;
        this.pondSize=pondSize;
        this.constructionStart=constructionStart;
        this.constructionEnd=constructionEnd;
        this.pondLatitude=pondLatitude;
        this.pondLongitude=pondLongitude;
        this.collectedAmount=collectedAmount;
        this.constructionCost=constructionCost;
        this.constructionDays=constructionDays;
        this.locationStatus=locationStatus;
        this.approvalStatus=approvalStatus;
        this.pondID=pondid;
        this.pondImageLink1=str_base64image1;
        this.pondImageLink2=str_base64image2;
        this.pondImageLink3=str_base64image3;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Object getUserID() {
        return userID;
    }

    public void setUserID(Object userID) {
        this.userID = userID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getFarmerID() {
        return farmerID;
    }

    public void setFarmerID(Integer farmerID) {
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

    public String getPondCode() {
        return pondCode;
    }

    public void setPondCode(String pondCode) {
        this.pondCode = pondCode;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getPondSize() {
        return pondSize;
    }

    public void setPondSize(String pondSize) {
        this.pondSize = pondSize;
    }

    public String getPondID() {
        return pondID;
    }

    public void setPondID(String pondID) {
        this.pondID = pondID;
    }

    public String getConstructionStart() {
        return constructionStart;
    }

    public void setConstructionStart(String constructionStart) {
        this.constructionStart = constructionStart;
    }

    public String getConstructionEnd() {
        return constructionEnd;
    }

    public void setConstructionEnd(String constructionEnd) {
        this.constructionEnd = constructionEnd;
    }

    public String getConstructionCost() {
        return constructionCost;
    }

    public void setConstructionCost(String constructionCost) {
        this.constructionCost = constructionCost;
    }

    public String getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(String collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public String getFarmerPhoto() {
        return farmerPhoto;
    }

    public void setFarmerPhoto(String farmerPhoto) {
        this.farmerPhoto = farmerPhoto;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getConstructionDays() {
        return constructionDays;
    }

    public void setConstructionDays(String constructionDays) {
        this.constructionDays = constructionDays;
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

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Object getApprovalRemarks() {
        return approvalRemarks;
    }

    public void setApprovalRemarks(Object approvalRemarks) {
        this.approvalRemarks = approvalRemarks;
    }

    public String getPondStatus() {
        return pondStatus;
    }

    public void setPondStatus(String pondStatus) {
        this.pondStatus = pondStatus;
    }

    public Object getFarmerAge() {
        return farmerAge;
    }

    public void setFarmerAge(Object farmerAge) {
        this.farmerAge = farmerAge;
    }

    public Object getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(Object familyMembers) {
        this.familyMembers = familyMembers;
    }

    public Object getFarmerIncome() {
        return farmerIncome;
    }

    public void setFarmerIncome(Object farmerIncome) {
        this.farmerIncome = farmerIncome;
    }

    public Object getIDType() {
        return iDType;
    }

    public void setIDType(Object iDType) {
        this.iDType = iDType;
    }

    public Object getIDProof() {
        return iDProof;
    }

    public void setIDProof(Object iDProof) {
        this.iDProof = iDProof;
    }

    public String getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(String locationStatus) {
        this.locationStatus = locationStatus;
    }

    public String getPondImageLink1() {
        return pondImageLink1;
    }

    public void setPondImageLink1(String pondImageLink1) {
        this.pondImageLink1 = pondImageLink1;
    }

    public String getPondImageLink2() {
        return pondImageLink2;
    }

    public void setPondImageLink2(String pondImageLink2) {
        this.pondImageLink2 = pondImageLink2;
    }

    public String getPondImageLink3() {
        return pondImageLink3;
    }

    public void setPondImageLink3(String pondImageLink3) {
        this.pondImageLink3 = pondImageLink3;
    }

}
