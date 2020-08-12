package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDataSummaryList {
    @SerializedName("Data_All")
    @Expose
    private String dataAll;
    @SerializedName("Data_Pending")
    @Expose
    private String dataPending;
    @SerializedName("Data_Process")
    @Expose
    private String dataProcess;
    @SerializedName("Data_Approved")
    @Expose
    private String dataApproved;
    @SerializedName("Data_Rejected")
    @Expose
    private String dataRejected;
    @SerializedName("Excel_Link")
    @Expose
    private String excelLink;

    public String getDataAll() {
        return dataAll;
    }

    public void setDataAll(String dataAll) {
        this.dataAll = dataAll;
    }

    public String getDataPending() {
        return dataPending;
    }

    public void setDataPending(String dataPending) {
        this.dataPending = dataPending;
    }

    public String getDataProcess() {
        return dataProcess;
    }

    public void setDataProcess(String dataProcess) {
        this.dataProcess = dataProcess;
    }

    public String getDataApproved() {
        return dataApproved;
    }

    public void setDataApproved(String dataApproved) {
        this.dataApproved = dataApproved;
    }

    public String getDataRejected() {
        return dataRejected;
    }

    public void setDataRejected(String dataRejected) {
        this.dataRejected = dataRejected;
    }

    public String getExcelLink() {
        return excelLink;
    }

    public void setExcelLink(String excelLink) {
        this.excelLink = excelLink;
    }

}
