package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDataList {
    @SerializedName("Farmer")
    @Expose
    private List<Farmer> farmer = null;
    @SerializedName("Pond")
    @Expose
    private List<Pond> pond = null;
    @SerializedName("Response")
    @Expose
    private String response;

    public List<Farmer> getFarmer() {
        return farmer;
    }

    public void setFarmer(List<Farmer> farmer) {
        this.farmer = farmer;
    }

    public List<Pond> getPond() {
        return pond;
    }

    public void setPond(List<Pond> pond) {
        this.pond = pond;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
