package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateSyncRequest {

    @SerializedName("Sync_ID")
    @Expose
    private String syncID;
    @SerializedName("Sync_Version")
    @Expose
    private String syncVersion;
    @SerializedName("Sync_Status")
    @Expose
    private String syncStatus;

    public String getSyncID() {
        return syncID;
    }

    public void setSyncID(String syncID) {
        this.syncID = syncID;
    }

    public String getSyncVersion() {
        return syncVersion;
    }

    public void setSyncVersion(String syncVersion) {
        this.syncVersion = syncVersion;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
}
