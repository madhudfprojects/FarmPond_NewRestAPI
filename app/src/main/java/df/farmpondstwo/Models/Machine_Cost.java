package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Machine_Cost {
    @SerializedName("Academic_ID")
    @Expose
    private String academic_ID;
    @SerializedName("State_ID")
    @Expose
    private String state_ID;
    @SerializedName("Machine_Cost")
    @Expose
    private String machine_Cost;

    public String getAcademic_ID() {
        return academic_ID;
    }

    public void setAcademic_ID(String academic_ID) {
        this.academic_ID = academic_ID;
    }

    public String getState_ID() {
        return state_ID;
    }

    public void setState_ID(String state_ID) {
        this.state_ID = state_ID;
    }

    public String getMachine_Cost() {
        return machine_Cost;
    }

    public void setMachine_Cost(String machine_Cost) {
        this.machine_Cost = machine_Cost;
    }
}
