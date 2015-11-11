package cmov.goncalobo.trainticketsystem.Entities;

import java.io.Serializable;

public class PaymentCard implements Serializable {
    private String ID = "";
    private String number = "";
    private String validity="";
    private String type ="";
    private boolean active=false;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String display(int state){
        switch (state){
            case 1: return "Type: "+getType()+"\nNo. "+getNumber()+"\nVal: "+getValidity().substring(0,getValidity().indexOf("T"));
            case 2:
            default: return "Error";
        }
    }




}
