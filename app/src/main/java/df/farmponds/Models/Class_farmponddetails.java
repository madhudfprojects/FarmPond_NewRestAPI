package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Class_farmponddetails
{
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

    @SerializedName("Pond_Collected_Amount")
    @Expose
    private String pondCollectedAmount;


    @SerializedName("Pond_Status")
    @Expose
    private String pondStatus;

    @SerializedName("Submitted_Date")
    @Expose
    private String submittedDate;
    @SerializedName("Submitted_By")
    @Expose
    private String submittedBy;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
    @SerializedName("Created_By")
    @Expose
    private String createdBy;
    @SerializedName("Pond_Temp_ID")
    @Expose
    private String pondTempID;
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("Created_User")
    @Expose
    private String createdUser;
    @SerializedName("Submitted_User")
    @Expose
    private String submittedUser;
    @SerializedName("Pond_Land_Acre")
    @Expose
    private String pondLandAcre;
    @SerializedName("Pond_Land_Gunta")
    @Expose
    private String pondLandGunta;
    @SerializedName("Approval_Status")
    @Expose
    private String approvalStatus;
    @SerializedName("Approval_Remarks")
    @Expose
    private String approvalRemarks;
    @SerializedName("Approval_By")
    @Expose
    private String approvalBy;
    @SerializedName("Approval_User")
    @Expose
    private String approvalUser;
    @SerializedName("Farmer_ID_Type")
    @Expose
    private String farmerIDType;
    @SerializedName("Farmer_ID_Number")
    @Expose
    private String farmerIDNumber;
    @SerializedName("Donor_Name")
    @Expose
    private String donorName;
    @SerializedName("Response_Action")
    @Expose
    private String responseAction;
    @SerializedName("Farmer_First_Name")
    @Expose
    private String farmerFirstName;
    @SerializedName("Farmer_Middle_Name")
    @Expose
    private String farmerMiddleName;
    @SerializedName("Farmer_Last_Name")
    @Expose
    private String farmerLastName;
    @SerializedName("Crop_Before")
    @Expose
    private String cropBefore;

    @SerializedName("Crop_After")
    @Expose
    private String cropAfter;

    @SerializedName("pond_remarks")
    @Expose
    private String pond_remarks;


    @SerializedName("PondImage")
    @Expose
    private List<PondImage> pondImage = null;

    @SerializedName("Pond_Image_1")
    @Expose
    private String pondImage1;
    @SerializedName("Pond_Image_2")
    @Expose
    private String pondImage2;
    @SerializedName("Pond_Image_3")
    @Expose
    private String pondImage3;

    @SerializedName("Location_Status")
    @Expose
    private String location_Status;

    public String getPondImage1() {
        return pondImage1;
    }

    public void setPondImage1(String pondImage1) {
        this.pondImage1 = pondImage1;
    }

    public String getPondImage2() {
        return pondImage2;
    }

    public void setPondImage2(String pondImage2) {
        this.pondImage2 = pondImage2;
    }

    public String getPondImage3() {
        return pondImage3;
    }

    public void setPondImage3(String pondImage3) {
        this.pondImage3 = pondImage3;
    }

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

    public String getPondStatus() {
        return pondStatus;
    }

    public void setPondStatus(String pondStatus) {
        this.pondStatus = pondStatus;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
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

    public String getPondTempID() {
        return pondTempID;
    }

    public void setPondTempID(String pondTempID) {
        this.pondTempID = pondTempID;
    }


    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
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

    public List<PondImage> getPondImage() {
        return pondImage;
    }

    public void setPondImage(List<PondImage> pondImage) {
        this.pondImage = pondImage;
    }

    public String getPondCollectedAmount() {
        return pondCollectedAmount;
    }

    public void setPondCollectedAmount(String pondCollectedAmount) {
        this.pondCollectedAmount = pondCollectedAmount;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSubmittedUser() {
        return submittedUser;
    }

    public void setSubmittedUser(String submittedUser) {
        this.submittedUser = submittedUser;
    }

    public String getPondLandAcre() {
        return pondLandAcre;
    }

    public void setPondLandAcre(String pondLandAcre) {
        this.pondLandAcre = pondLandAcre;
    }

    public String getPondLandGunta() {
        return pondLandGunta;
    }

    public void setPondLandGunta(String pondLandGunta) {
        this.pondLandGunta = pondLandGunta;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalRemarks() {
        return approvalRemarks;
    }

    public void setApprovalRemarks(String approvalRemarks) {
        this.approvalRemarks = approvalRemarks;
    }

    public String getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(String approvalBy) {
        this.approvalBy = approvalBy;
    }

    public String getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(String approvalUser) {
        this.approvalUser = approvalUser;
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

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getResponseAction() {
        return responseAction;
    }

    public void setResponseAction(String responseAction) {
        this.responseAction = responseAction;
    }

    public void setCropBefore(String cropBefore) {
        this.cropBefore = cropBefore;
    }

    public void setCropAfter(String cropAfter) {
        this.cropAfter = cropAfter;
    }

    public String getCropBefore() {
        return cropBefore;
    }

    public String getCropAfter() {
        return cropAfter;
    }


    public String getPond_remarks() {
        return pond_remarks;
    }

    public void setPond_remarks(String pond_remarks) {
        this.pond_remarks = pond_remarks;
    }

    public String getLocation_Status() {
        return location_Status;
    }

    public void setLocation_Status(String location_Status) {
        this.location_Status = location_Status;
    }
}


