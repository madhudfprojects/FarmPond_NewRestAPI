package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClusterSummaryList {

    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("Employee_Name")
    @Expose
    private String employeeName;
    @SerializedName("Data_All")
    @Expose
    private String dataAll;
    @SerializedName("Data_Pending")
    @Expose
    private String dataPending;
    @SerializedName("Data_Process")
    @Expose
    private String dataProcess;
    @SerializedName("Data_Approved")
    @Expose
    private String dataApproved;
    @SerializedName("Data_Rejected")
    @Expose
    private String dataRejected;

    private String yearId;
    private String employee_Role;
    private String employeeID;

    public ClusterSummaryList() {
    }

    public ClusterSummaryList(String employeeName, String dataAll, String dataPending, String dataProcess, String dataApproved, String dataRejected, String YearId,String userId,String employeeID,String employee_Role) {
        this.employeeName=employeeName;
        this.dataAll=dataAll;
        this.dataPending=dataPending;
        this.dataProcess=dataProcess;
        this.dataApproved=dataApproved;
        this.dataRejected=dataRejected;
        this.yearId=YearId;
        this.userID=userId;
        this.employeeID=employeeID;
        this.employee_Role=employee_Role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDataAll() {
        return dataAll;
    }

    public void setDataAll(String dataAll) {
        this.dataAll = dataAll;
    }

    public String getDataPending() {
        return dataPending;
    }

    public void setDataPending(String dataPending) {
        this.dataPending = dataPending;
    }

    public String getDataProcess() {
        return dataProcess;
    }

    public void setDataProcess(String dataProcess) {
        this.dataProcess = dataProcess;
    }

    public String getDataApproved() {
        return dataApproved;
    }

    public void setDataApproved(String dataApproved) {
        this.dataApproved = dataApproved;
    }

    public String getDataRejected() {
        return dataRejected;
    }

    public void setDataRejected(String dataRejected) {
        this.dataRejected = dataRejected;
    }

    public String getYearId() {
        return yearId;
    }

    public void setYearId(String yearId) {
        this.yearId = yearId;
    }

    public String getEmployee_Role() {
        return employee_Role;
    }

    public void setEmployee_Role(String employee_Role) {
        this.employee_Role = employee_Role;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    @Override
    public String toString() {
        return employeeName;
    }

}
