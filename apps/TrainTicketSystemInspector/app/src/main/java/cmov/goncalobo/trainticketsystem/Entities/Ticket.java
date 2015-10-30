package cmov.goncalobo.trainticketsystem.Entities;

import java.io.Serializable;

/**
 * Created by goncalobo on 20/10/2015.
 */
public class Ticket implements Serializable {

    private String ID;
    private String from;
    private String to;
    private String departure;
    private String arrival;
    private String duration;
    private String price;
    private int capacity;
    private int maxCapacity;
    private boolean isValid;

    private String seat;

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        if(from.equals("Central Station"))
            this.from = "C. Station";
        else this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        if(to.equals("Central Station"))
            this.to = "C. Station";
        else this.to = to;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDuration() {
        if(getDeparture()!=null&&getArrival()!=null) {
            int departureSecs =
                    Integer.parseInt(getDeparture().substring(0, getDeparture().indexOf("h"))) * 60 * 60
                            +
                            Integer.parseInt(getDeparture().substring(getDeparture().indexOf("h") + 1, getDeparture().length())) * 60;

            int arrivalSecs =
                    Integer.parseInt(getArrival().substring(0, getArrival().indexOf("h"))) * 60 * 60
                            +
                            Integer.parseInt(getArrival().substring(getArrival().indexOf("h") + 1, getArrival().length())) * 60;

            int durationSecs = arrivalSecs - departureSecs;

            String hours = "" + (int)((durationSecs / 60) / 60);
            String minutes = "" + (int)((durationSecs / 60)-(Integer.parseInt(hours)*60));

            return hours+"h"+minutes;
        }
        else return "?";


    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(String v) {
        if(v.equals("true"))
            this.isValid = true;
        else this.isValid = false;
    }

    public String display(int state){
        switch (state){
            case 1:
                if(getFrom().length()>2||getTo().length()>2)
                    return  "From: " + getFrom() + " (" + getDeparture() + ")\nTo: " + getTo() + " (" + getArrival() +")";
                else return "From:   " + getFrom() + " (" + getDeparture() + ")\nTo:        " + getTo() + " (" + getArrival() +")";
            case 2: return "\uD83D\uDCB0   " + getPrice() + "€\n⌚   " + getDuration();
            case 3: return "Seat\n" + getSeat();
            case 4: return "From:   " + getFrom() + " (" + getDeparture() + ")\nTo:        " + getTo() + " (" + getArrival() +")\n\nSeat:     " + getSeat() + "\nDuration: " + getDuration();
            default: return "Error";
        }
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(String maxCapacity) {
        this.maxCapacity = Integer.parseInt(maxCapacity);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = Integer.parseInt(capacity);
    }
}
