package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDataCount {
    @SerializedName("Farmer_Count")
    @Expose
    private String farmer_Count;
    @SerializedName("Pond_Count")
    @Expose
    private String pond_Count;

    public String getFarmer_Count() {
        return farmer_Count;
    }

    public void setFarmer_Count(String farmer_Count) {
        this.farmer_Count = farmer_Count;
    }

    public String getPond_Count() {
        return pond_Count;
    }

    public void setPond_Count(String pond_Count) {
        this.pond_Count = pond_Count;
    }
}
