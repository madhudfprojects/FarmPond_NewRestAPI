package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClusterAcademicSummary {
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("lstSummary")
    @Expose
    private List<ClusterSummaryList> lstSummary = null;

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

    public List<ClusterSummaryList> getLstSummary() {
        return lstSummary;
    }

    public void setLstSummary(List<ClusterSummaryList> lstSummary) {
        this.lstSummary = lstSummary;
    }
}
