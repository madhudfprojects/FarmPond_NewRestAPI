package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDataSummary {
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("lstSummary")
    @Expose
    private List<UserDataSummaryList> lstSummary = null;

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

    public List<UserDataSummaryList> getLstSummary() {
        return lstSummary;
    }

    public void setLstSummary(List<UserDataSummaryList> lstSummary) {
        this.lstSummary = lstSummary;
    }

}
