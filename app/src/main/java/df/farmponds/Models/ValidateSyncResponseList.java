package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateSyncResponseList {

    @SerializedName("Sync_ID")
    @Expose
    private String syncID;
    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("Sync_Version")
    @Expose
    private String syncVersion;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
    @SerializedName("Completed_Date")
    @Expose
    private String completedDate;
    @SerializedName("Sync_Status")
    @Expose
    private String syncStatus;

    public String getSyncID() {
        return syncID;
    }

    public void setSyncID(String syncID) {
        this.syncID = syncID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSyncVersion() {
        return syncVersion;
    }

    public void setSyncVersion(String syncVersion) {
        this.syncVersion = syncVersion;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
}
