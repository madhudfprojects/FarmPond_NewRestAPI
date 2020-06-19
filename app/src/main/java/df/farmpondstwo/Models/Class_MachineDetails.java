package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_MachineDetails
{
    @SerializedName("Machine_ID")
    @Expose
    private String machine_ID;
    @SerializedName("Machine_Name")
    @Expose
    private String machine_Name;

    public String getMachine_ID() {
        return machine_ID;
    }

    public void setMachine_ID(String machine_ID) {
        this.machine_ID = machine_ID;
    }

    public String getMachine_Name() {
        return machine_Name;
    }

    public void setMachine_Name(String machine_Name) {
        this.machine_Name = machine_Name;
    }

    public String toString() {
        return machine_ID;
    }
}
