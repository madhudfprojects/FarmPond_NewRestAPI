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

public class Class_addfarmponddetails_ToFromServer1
{




    @SerializedName("Message")
    @Expose
    private String Message;

    @SerializedName("Status")
    @Expose
    private String status;


   /* @SerializedName("lst")
    @Expose
    //private List<Class_addfarmponddetails_ToFromServer2> class_addfarmponddetails_toFromServer2_obj = new ArrayList<Class_addfarmponddetails_ToFromServer2>();
    private List<Class_addfarmponddetails_ToFromServer2> class_addfarmponddetails_toFromServer2_obj=null;

*/

   /* @SerializedName("lst")
    @Expose
    private List<Class_addfarmponddetails_ToFromServer2> lst = null;*/

    @SerializedName("lst")
    @Expose
    private Class_addfarmponddetails_ToFromServer2 lst2 = null;


    public String getMessage() {
        return Message;
    }

    public String getStatus() {
        return status;
    }

    /*public List<Class_addfarmponddetails_ToFromServer2> getClass_addfarmponddetails_toFromServer2_obj() {
        return class_addfarmponddetails_toFromServer2_obj;
    }*/


   /* public List<Class_addfarmponddetails_ToFromServer2> getLst() {
        return lst;
    }*/

    public Class_addfarmponddetails_ToFromServer2 getLst2() {
        return lst2;
    }
}
