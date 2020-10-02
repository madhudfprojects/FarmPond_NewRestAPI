package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Class_getdemo_Response
{

    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;




    @SerializedName("lst")
    @Expose
    private List<Class_getdemo_resplist> lst = null;



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

    public List<Class_getdemo_resplist> getLst() {
        return lst;
    }

    public void setLst(List<Class_getdemo_resplist> lst) {
        this.lst = lst;
    }
}
