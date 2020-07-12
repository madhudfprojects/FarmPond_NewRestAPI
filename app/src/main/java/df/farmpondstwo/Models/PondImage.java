package df.farmpondstwo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PondImage {
    @SerializedName("Image_ID")
    @Expose
    private String imageID;
    @SerializedName("Pond_ID")
    @Expose
    private String pondID;
    @SerializedName("Image_Data_1")
    @Expose
    private Object imageData1;
    @SerializedName("Image_Data")
    @Expose
    private Object imageData;
    @SerializedName("Image_Link")
    @Expose
    private String imageLink;
    @SerializedName("Image_Type")
    @Expose
    private String imageType;
    @SerializedName("Image_Status")
    @Expose
    private String imageStatus;

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getPondID() {
        return pondID;
    }

    public void setPondID(String pondID) {
        this.pondID = pondID;
    }

    public Object getImageData1() {
        return imageData1;
    }

    public void setImageData1(Object imageData1) {
        this.imageData1 = imageData1;
    }

    public Object getImageData() {
        return imageData;
    }

    public void setImageData(Object imageData) {
        this.imageData = imageData;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }
}
