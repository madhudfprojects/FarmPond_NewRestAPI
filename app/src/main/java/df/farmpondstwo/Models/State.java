package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {
    @SerializedName("State_ID")
    @Expose
    private String stateID;
    @SerializedName("State_Name")
    @Expose
    private String stateName;
    private String YearID;

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getYearID() {
        return YearID;
    }

    public void setYearID(String yearID) {
        YearID = yearID;
    }

    @Override
    public String toString() {
        return stateName;
    }
}
