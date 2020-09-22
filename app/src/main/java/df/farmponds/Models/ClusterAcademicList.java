package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClusterAcademicList {
    @SerializedName("Year_ID")
    @Expose
    private String yearID;
    @SerializedName("Year_Name")
    @Expose
    private String yearName;

    public String getYearID() {
        return yearID;
    }

    public void setYearID(String yearID) {
        this.yearID = yearID;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    @Override
    public String toString() {
        return yearName;
    }
}
