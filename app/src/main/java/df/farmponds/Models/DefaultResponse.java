package df.farmponds.Models;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

@SerializedName("Status")
private boolean Status;

@SerializedName("Message")
private String Message;

    public DefaultResponse(){

    }
    public DefaultResponse(boolean status, String msg) {
        this.Status = status;
        this.Message = msg;
    }

    public boolean getstatus() {
        return Status;
    }

    public String getMsg() {
        return Message;
    }
}
