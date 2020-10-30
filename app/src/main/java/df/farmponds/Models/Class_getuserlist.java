package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Class_getuserlist {
    @SerializedName("Status")
    @Expose
    private Boolean status;

    @SerializedName("Message")
    @Expose
    private String message;
    //StatusStatus
    @SerializedName("lstSummary")
    @Expose
    private List<Class_userlist> listUser = null;

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


    public List<Class_userlist> getListUser() {
        return listUser;
    }

    public void setListUser(List<Class_userlist> listUser) {
        this.listUser = listUser;
    }
}
