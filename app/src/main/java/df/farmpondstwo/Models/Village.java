package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Village {
    @SerializedName("Taluka_ID")
    @Expose
    private String talukaID;
    @SerializedName("Panchayat_ID")
    @Expose
    private String panchayatID;
    @SerializedName("Village_ID")
    @Expose
    private String villageID;
    @SerializedName("Village_Name")
    @Expose
    private String villageName;

    public String getTalukaID() {
        return talukaID;
    }

    public void setTalukaID(String talukaID) {
        this.talukaID = talukaID;
    }

    public String getPanchayatID() {
        return panchayatID;
    }

    public void setPanchayatID(String panchayatID) {
        this.panchayatID = panchayatID;
    }

    public String getVillageID() {
        return villageID;
    }

    public void setVillageID(String villageID) {
        this.villageID = villageID;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
}
