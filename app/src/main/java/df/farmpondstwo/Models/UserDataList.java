package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDataList {
    @SerializedName("Count")
    @Expose
    private List<UserDataCount> userdatacount = null;
    @SerializedName("Farmer")
    @Expose
    private List<Farmer> farmer = null;
    @SerializedName("Pond")
    @Expose
    private List<Class_farmponddetails> classfarmponddetails = null;
    @SerializedName("Response")
    @Expose
    private String response;

    public List<Farmer> getFarmer() {
        return farmer;
    }

    public void setFarmer(List<Farmer> farmer) {
        this.farmer = farmer;
    }

    public List<Class_farmponddetails> getPond() {
        return classfarmponddetails;
    }

    public void setPond(List<Class_farmponddetails> classfarmponddetails) {
        this.classfarmponddetails = classfarmponddetails;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<UserDataCount> getUserdatacount() {
        return userdatacount;
    }

    public void setUserdatacount(List<UserDataCount> userdatacount) {
        this.userdatacount = userdatacount;
    }
}
