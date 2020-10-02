package df.farmponds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
[
    {
        "Option_ID": "1",
        "Option_Value": "Machine Failure"
    },
    {
        "Option_ID": "2",
        "Option_Value": "Operator Not Available"
    },
    {
        "Option_ID": "3",
        "Option_Value": "Day/Night Work"
    }
]
}*/


public class Class_RemarksDetails
{

  @SerializedName("Option_ID")
  @Expose
  private String Remarks_ID;

  @SerializedName("Option_Value")
  @Expose
  private String Remarks_Name;

  public String getRemarks_ID() {
    return Remarks_ID;
  }

  public void setRemarks_ID(String remarks_ID) {
    Remarks_ID = remarks_ID;
  }

  public String getRemarks_Name() {
    return Remarks_Name;
  }

  public void setRemarks_Name(String remarks_Name) {
    Remarks_Name = remarks_Name;
  }


  public String toString() {
    return Remarks_Name;
  }

/*  public String getMachine_name() {
        return Machine_Name;
    }
    public void setMachine_name(String machine_name) {
        Machine_Name = machine_name;
    }
    public String getMachine_Code() {
        return Machine_Code;
    }
    public void setMachine_Code(String machine_Code) {
        Machine_Code = machine_Code;
    }
    public String getMachine_id()
    {
        return Machine_id;
    }
    public void setMachine_id(String machine_id) {
        Machine_id = machine_id;
    }
    public String toString() {
        return Machine_Code;
    }*/
}