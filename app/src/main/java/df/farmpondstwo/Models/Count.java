package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Count {
    @SerializedName("State_Count")
    @Expose
    private String stateCount;
    @SerializedName("District_Count")
    @Expose
    private String districtCount;
    @SerializedName("Taluka_Count")
    @Expose
    private String talukaCount;
    @SerializedName("Panchayat_Count")
    @Expose
    private String panchayatCount;
    @SerializedName("Village_Count")
    @Expose
    private String villageCount;
    @SerializedName("Year_Count")
    @Expose
    private String yearCount;
    @SerializedName("Machine_Count")
    @Expose
    private String machineCount;
    @SerializedName("Machine_Cost_Count")
    @Expose
    private String machineCostCount;
    @SerializedName("Sync_ID")
    @Expose
    private String sync_ID;

    public String getStateCount() {
        return stateCount;
    }

    public void setStateCount(String stateCount) {
        this.stateCount = stateCount;
    }

    public String getDistrictCount() {
        return districtCount;
    }

    public void setDistrictCount(String districtCount) {
        this.districtCount = districtCount;
    }

    public String getTalukaCount() {
        return talukaCount;
    }

    public void setTalukaCount(String talukaCount) {
        this.talukaCount = talukaCount;
    }

    public String getPanchayatCount() {
        return panchayatCount;
    }

    public void setPanchayatCount(String panchayatCount) {
        this.panchayatCount = panchayatCount;
    }

    public String getVillageCount() {
        return villageCount;
    }

    public void setVillageCount(String villageCount) {
        this.villageCount = villageCount;
    }

    public String getYearCount() {
        return yearCount;
    }

    public void setYearCount(String yearCount) {
        this.yearCount = yearCount;
    }

    public String getMachineCount() {
        return machineCount;
    }

    public void setMachineCount(String machineCount) {
        this.machineCount = machineCount;
    }

    public String getMachineCostCount() {
        return machineCostCount;
    }

    public void setMachineCostCount(String machineCostCount) {
        this.machineCostCount = machineCostCount;
    }

    public String getSync_ID() {
        return sync_ID;
    }

    public void setSync_ID(String sync_ID) {
        this.sync_ID = sync_ID;
    }
}
