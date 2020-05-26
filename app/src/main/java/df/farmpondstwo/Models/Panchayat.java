package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Panchayat {
    @SerializedName("Taluka_ID")
    @Expose
    private String talukaID;
    @SerializedName("Panchayat_ID")
    @Expose
    private String panchayatID;
    @SerializedName("Panchayat_Name")
    @Expose
    private String panchayatName;

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

    public String getPanchayatName() {
        return panchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        this.panchayatName = panchayatName;
    }
}
