package df.farmponds;





         /*Post_ActionPondApproval
                        Created_By
                Pond_ID
                        Approval_Status
                Approval_Remarks*/



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_cluster_approvereject_request
{


    @SerializedName("Created_By")
    @Expose
    private String Created_By;


    @SerializedName("Pond_ID")
    @Expose
    private String Pond_ID;


    @SerializedName("Approval_Status")
    @Expose
    private String Approval_Status;


    @SerializedName("Approval_Remarks")
    @Expose
    private String Approval_Remarks;



    public String getPond_ID() {
        return Pond_ID;
    }

    public void setPond_ID(String pond_ID) {
        Pond_ID = pond_ID;
    }

    public String getApproval_Status() {
        return Approval_Status;
    }

    public void setApproval_Status(String approval_Status) {
        Approval_Status = approval_Status;
    }

    public String getApproval_Remarks() {
        return Approval_Remarks;
    }

    public void setApproval_Remarks(String approval_Remarks) {
        Approval_Remarks = approval_Remarks;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }
}
