package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Year {
    @SerializedName("Academic_ID")
    @Expose
    private String academic_ID;
    @SerializedName("Academic_Name")
    @Expose
    private String academic_Name;

    public String getAcademic_ID() {
        return academic_ID;
    }

    public void setAcademic_ID(String academic_ID) {
        this.academic_ID = academic_ID;
    }

    public String getAcademic_Name() {
        return academic_Name;
    }

    public void setAcademic_Name(String academic_Name) {
        this.academic_Name = academic_Name;
    }

    @Override
    public String toString() {
        return academic_Name;
    }
}
