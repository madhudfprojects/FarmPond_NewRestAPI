package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Class_gethelp_resplist
{


    @SerializedName("Title")
    @Expose
    private String title;


    @SerializedName("Content")
    @Expose
    private String content;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

