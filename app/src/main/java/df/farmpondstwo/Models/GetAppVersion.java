package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAppVersion {
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("listVersion")
    @Expose
    private List<GetAppVersionList> listVersion = null;

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

    public List<GetAppVersionList> getlistVersion() {
        return listVersion;
    }

    public void setlistVersion(List<GetAppVersionList> lstFarmer) {
        this.listVersion = lstFarmer;
    }

}
