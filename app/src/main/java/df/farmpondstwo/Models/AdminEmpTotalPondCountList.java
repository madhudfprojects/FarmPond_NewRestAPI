package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminEmpTotalPondCountList {
    @SerializedName("Employee_Name")
    @Expose
    private String employeeName;
    @SerializedName("Employee_Count")
    @Expose
    private String employeeCount;
    @SerializedName("Todays_Date")
    @Expose
    private String todaysDate;
    @SerializedName("Todays_Count")
    @Expose
    private String todaysCount;
    @SerializedName("Total_Count")
    @Expose
    private String totalCount;
    @SerializedName("Total_Todays_Count")
    @Expose
    private String totalTodaysCount;
    @SerializedName("Response")
    @Expose
    private String response;

    public AdminEmpTotalPondCountList(String employeeName, String employeeCount, String todaysCount) {
        this.employeeName=employeeName;
        this.employeeCount=employeeCount;
        this.todaysCount=todaysCount;
    }

    public AdminEmpTotalPondCountList() {

    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(String employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getTodaysDate() {
        return todaysDate;
    }

    public void setTodaysDate(String todaysDate) {
        this.todaysDate = todaysDate;
    }

    public String getTodaysCount() {
        return todaysCount;
    }

    public void setTodaysCount(String todaysCount) {
        this.todaysCount = todaysCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalTodaysCount() {
        return totalTodaysCount;
    }

    public void setTotalTodaysCount(String totalTodaysCount) {
        this.totalTodaysCount = totalTodaysCount;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
