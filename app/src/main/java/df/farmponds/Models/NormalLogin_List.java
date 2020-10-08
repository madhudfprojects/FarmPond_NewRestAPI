package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NormalLogin_List {
    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("User_Name")
    @Expose
    private String userName;
    @SerializedName("User_Email")
    @Expose
    private String userEmail;
    @SerializedName("User_Desigation")
    @Expose
    private String userDesigation;
    @SerializedName("User_Roles")
    @Expose
    private String userRole;
    @SerializedName("User_State")
    @Expose
    private String userState;
    @SerializedName("User_State_Amount")
    @Expose
    private String userStateAmount;
    @SerializedName("Response")
    @Expose
    private String response;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserDesigation() {
        return userDesigation;
    }

    public void setUserDesigation(String userDesigation) {
        this.userDesigation = userDesigation;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserStateAmount() {
        return userStateAmount;
    }

    public void setUserStateAmount(String userStateAmount) {
        this.userStateAmount = userStateAmount;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
