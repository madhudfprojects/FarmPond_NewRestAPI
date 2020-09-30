package df.farmponds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



/*
<ArrayOfvmHelpDemo1>
        <vmHelpDemo1>
        <Content>Help....</Content>
        <Response>Success</Response>
        <Title>Help</Title>
        </vmHelpDemo1>
        <vmHelpDemo1>
        <Content>Contact No</Content>
        <Response>Success</Response>
        <Title>Contact</Title>
        </vmHelpDemo1>
        <vmHelpDemo1>
        <Content>Email Address</Content>
        <Response>Success</Response>
        <Title>Email</Title>
        </vmHelpDemo1>
        <vmHelpDemo1>
        <Content>1.2.5</Content>
        <Response>Success</Response>
        <Title>Version</Title>
        </vmHelpDemo1>
        </ArrayOfvmHelpDemo1>
*/

import java.util.List;

public class Class_gethelp_Response
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
