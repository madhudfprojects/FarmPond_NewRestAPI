package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClusterEmployee {
    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("Employee_Name")
    @Expose
    private String employeeName;

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
}
