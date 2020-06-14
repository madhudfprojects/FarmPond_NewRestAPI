package df.farmpondstwo;


/*
"Pond_ID":"0",
        "Farmer_ID":"9276",
        "Academic_ID":"2020",
        "Machine_ID":"2",
        "Pond_Latitude":"15.879789",
        "Pond_Longitude":"75.768687",
        "Pond_Length":"40",
        "Pond_Width":"50",
        "Pond_Depth":"60",
        "Pond_Start":"29-05-2020",
        "Pond_End":"29-05-2020",
        "Pond_Days":"1",
        "Pond_Cost":"2000",
        "Pond_Image_1":"iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAARuAAAEbgHQo7JoAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAGxQTFRF////AP//KK6GJK2JJK+JJq2HJa6HJa+JJa6IJa6IJa6IJq6IKK+JKK+KK7CLLrGNM7OPNLOQNbSRObWTPLeVQLiWRLmZRrqaVsCjY8WqacetgNC5htK9j9XCltjGndrJruHTtuTXueXZvufcQhO/KQAAAAp0Uk5TAAEmcH+As7Xm9myQZpsAAAB3SURBVBhXZY9HEgMhEAMFLLPQzjnH/f8ffcCmtkzfNFXSSJIkHy0li14FFzIA5OAkyfVUeicpMCJIPle1XZG9YtXLYQOd7Kfn7wNgSjBbAJPnESApwW7Yw+NMORiwfp2ul2K0Ejq9375J3fgtkH1brK3ejPub/wG/CwjxA06BTgAAAABJRU5ErkJggg==",
        "Pond_Image_2":"iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAARuAAAEbgHQo7JoAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAGxQTFRF////AP//KK6GJK2JJK+JJq2HJa6HJa+JJa6IJa6IJa6IJq6IKK+JKK+KK7CLLrGNM7OPNLOQNbSRObWTPLeVQLiWRLmZRrqaVsCjY8WqacetgNC5htK9j9XCltjGndrJruHTtuTXueXZvufcQhO/KQAAAAp0Uk5TAAEmcH+As7Xm9myQZpsAAAB3SURBVBhXZY9HEgMhEAMFLLPQzjnH/f8ffcCmtkzfNFXSSJIkHy0li14FFzIA5OAkyfVUeicpMCJIPle1XZG9YtXLYQOd7Kfn7wNgSjBbAJPnESApwW7Yw+NMORiwfp2ul2K0Ejq9375J3fgtkH1brK3ejPub/wG/CwjxA06BTgAAAABJRU5ErkJggg==",
        "Pond_Image_3":"iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAARuAAAEbgHQo7JoAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAGxQTFRF////AP//KK6GJK2JJK+JJq2HJa6HJa+JJa6IJa6IJa6IJq6IKK+JKK+KK7CLLrGNM7OPNLOQNbSRObWTPLeVQLiWRLmZRrqaVsCjY8WqacetgNC5htK9j9XCltjGndrJruHTtuTXueXZvufcQhO/KQAAAAp0Uk5TAAEmcH+As7Xm9myQZpsAAAB3SURBVBhXZY9HEgMhEAMFLLPQzjnH/f8ffcCmtkzfNFXSSJIkHy0li14FFzIA5OAkyfVUeicpMCJIPle1XZG9YtXLYQOd7Kfn7wNgSjBbAJPnESApwW7Yw+NMORiwfp2ul2K0Ejq9375J3fgtkH1brK3ejPub/wG/CwjxA06BTgAAAABJRU5ErkJggg==",
        "Submitted_Date":"29-05-2020",
        "Created_By": "6",
        "Pond_Temp_ID":"TempFarmpond2905202012567",
        "Pond_Land_Gunta":"10",
        "Pond_Land_Acre":"10"
*/


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_addfarmponddetails_ToFromServer2
{

    @SerializedName("Pond_ID")
    @Expose
    private String Pond_ID;

    @SerializedName("Farmer_ID")
    @Expose
    private String Farmer_ID;

    @SerializedName("Academic_ID")
    @Expose
    private String Academic_ID;

    @SerializedName("Machine_ID")
    @Expose
    private String Machine_ID;

    @SerializedName("Pond_Latitude")
    @Expose
    private String Pond_Latitude;

    @SerializedName("Pond_Longitude")
    @Expose
    private String Pond_Longitude;

    @SerializedName("Pond_Length")
    @Expose
    private String Pond_Length;

    @SerializedName("Pond_Width")
    @Expose
    private String Pond_Width;

    @SerializedName("Pond_Depth")
    @Expose
    private String Pond_Depth;

    @SerializedName("Pond_Start")
    @Expose
    private String Pond_Start;

    @SerializedName("Pond_End")
    @Expose
    private String Pond_End;

    @SerializedName("Pond_Days")
    @Expose
    private String Pond_Days;

    @SerializedName("Pond_Cost")
    @Expose
    private String Pond_Cost;

    @SerializedName("Pond_Image_1")
    @Expose
    private String Pond_Image_1;

    @SerializedName("Pond_Image_2")
    @Expose
    private String Pond_Image_2;

    @SerializedName("Pond_Image_3")
    @Expose
    private String Pond_Image_3;

    @SerializedName("Submitted_Date")
    @Expose
    private String Submitted_Date;

    @SerializedName("Created_By")
    @Expose
    private String Created_By;

    @SerializedName("Pond_Temp_ID")
    @Expose
    private String Pond_Temp_ID;

    @SerializedName("Pond_Land_Gunta")
    @Expose
    private String Pond_Land_Gunta;

    @SerializedName("Pond_Land_Acre")
    @Expose
    private String Pond_Land_Acre;




    public String getPond_ID() {
        return Pond_ID;
    }

    public void setPond_ID(String pond_ID) {
        Pond_ID = pond_ID;
    }

    public String getFarmer_ID() {
        return Farmer_ID;
    }

    public void setFarmer_ID(String farmer_ID) {
        Farmer_ID = farmer_ID;
    }

    public String getAcademic_ID() {
        return Academic_ID;
    }

    public void setAcademic_ID(String academic_ID) {
        Academic_ID = academic_ID;
    }

    public String getMachine_ID() {
        return Machine_ID;
    }

    public void setMachine_ID(String machine_ID) {
        Machine_ID = machine_ID;
    }

    public String getPond_Latitude() {
        return Pond_Latitude;
    }

    public void setPond_Latitude(String pond_Latitude) {
        Pond_Latitude = pond_Latitude;
    }

    public String getPond_Longitude() {
        return Pond_Longitude;
    }

    public void setPond_Longitude(String pond_Longitude) {
        Pond_Longitude = pond_Longitude;
    }

    public String getPond_Length() {
        return Pond_Length;
    }

    public void setPond_Length(String pond_Length) {
        Pond_Length = pond_Length;
    }

    public String getPond_Width() {
        return Pond_Width;
    }

    public void setPond_Width(String pond_Width) {
        Pond_Width = pond_Width;
    }

    public String getPond_Depth() {
        return Pond_Depth;
    }

    public void setPond_Depth(String pond_Depth) {
        Pond_Depth = pond_Depth;
    }

    public String getPond_Start() {
        return Pond_Start;
    }

    public void setPond_Start(String pond_Start) {
        Pond_Start = pond_Start;
    }

    public String getPond_End() {
        return Pond_End;
    }

    public void setPond_End(String pond_End) {
        Pond_End = pond_End;
    }

    public String getPond_Days() {
        return Pond_Days;
    }

    public void setPond_Days(String pond_Days) {
        Pond_Days = pond_Days;
    }

    public String getPond_Cost() {
        return Pond_Cost;
    }

    public void setPond_Cost(String pond_Cost) {
        Pond_Cost = pond_Cost;
    }

    public String getPond_Image_1() {
        return Pond_Image_1;
    }

    public void setPond_Image_1(String pond_Image_1) {
        Pond_Image_1 = pond_Image_1;
    }

    public String getPond_Image_2() {
        return Pond_Image_2;
    }

    public void setPond_Image_2(String pond_Image_2) {
        Pond_Image_2 = pond_Image_2;
    }

    public String getPond_Image_3() {
        return Pond_Image_3;
    }

    public void setPond_Image_3(String pond_Image_3) {
        Pond_Image_3 = pond_Image_3;
    }

    public String getSubmitted_Date() {
        return Submitted_Date;
    }

    public void setSubmitted_Date(String submitted_Date) {
        Submitted_Date = submitted_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public String getPond_Temp_ID() {
        return Pond_Temp_ID;
    }

    public void setPond_Temp_ID(String pond_Temp_ID) {
        Pond_Temp_ID = pond_Temp_ID;
    }

    public String getPond_Land_Gunta() {
        return Pond_Land_Gunta;
    }

    public void setPond_Land_Gunta(String pond_Land_Gunta) {
        Pond_Land_Gunta = pond_Land_Gunta;
    }

    public String getPond_Land_Acre() {
        return Pond_Land_Acre;
    }



    public void setPond_Land_Acre(String pond_Land_Acre) {
        Pond_Land_Acre = pond_Land_Acre;
    }


}