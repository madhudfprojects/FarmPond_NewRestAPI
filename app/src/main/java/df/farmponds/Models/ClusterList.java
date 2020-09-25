package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClusterList {
    @SerializedName("Academic")
    @Expose
    private List<ClusterAcademicList> academic = null;
   /* @SerializedName("Employee")
    @Expose
    private List<ClusterEmployee> employee = null;*/
    @SerializedName("Response")
    @Expose
    private String response;

    public List<ClusterAcademicList> getAcademic() {
        return academic;
    }

    public void setAcademic(List<ClusterAcademicList> academic) {
        this.academic = academic;
    }

  /*  public List<ClusterEmployee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<ClusterEmployee> employee) {
        this.employee = employee;
    }
*/
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
