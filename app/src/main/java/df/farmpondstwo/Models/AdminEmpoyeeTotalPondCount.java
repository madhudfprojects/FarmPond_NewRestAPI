package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminEmpoyeeTotalPondCount {
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
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
    @SerializedName("EmployeeList")
    @Expose
    private List<AdminEmpTotalPondCountList> employeeList = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public List<AdminEmpTotalPondCountList> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<AdminEmpTotalPondCountList> employeeList) {
        this.employeeList = employeeList;
    }
}
