package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutoSyncVersionList {
    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("User_Sync")
    @Expose
    private String userSync;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserSync() {
        return userSync;
    }

    public void setUserSync(String userSync) {
        this.userSync = userSync;
    }
}
