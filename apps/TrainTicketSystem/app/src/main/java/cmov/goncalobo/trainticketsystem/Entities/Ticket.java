package cmov.goncalobo.trainticketsystem.Entities;

import java.io.Serializable;

/**
 * Created by goncalobo on 20/10/2015.
 */
public class Ticket implements Serializable {

    private String ID;
    private String from;
    private String to;
    private String departureHour;
    private String departureDate;
    private String arrivalHour;
    private String arrivalDate;
    private String duration;
    private String price;
    private int capacity;
    private int maxCapacity;
    private boolean isValid;

    public String getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(String hours, String minutes) {
        this.waitingTime = hours + "h" + minutes;
    }

    // if has waiting time
    private String waitingTime;
    private String departureHourMS;
    private String departureDateMS;
    private String arrivalHourMS;
    private String arrivalDateMS;

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
        if(from.equals("MS"))
            this.from = "M. Station";
        else this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        if(to.equals("MS"))
            this.to = "M. Station";
        else this.to = to;
    }

    public String getDeparture(int state) {
        switch (state) {
            case 1: return departureDate;
            case 2: return departureHour;
            default: break;
        }
        return "";
    }

    public void setDeparture(String departure) {
        this.departureDate = departure.substring(0,departure.indexOf("T"));
        this.departureHour = departure.substring(departure.indexOf("T")+1,departure.lastIndexOf(":")).replace(":","h");
    }

    public String getArrival(int state) {
        switch (state) {
            case 1: return arrivalDate;
            case 2: return arrivalHour;
            default: break;
        }
        return "";
    }

    public void setArrival(String arrival) {
        this.arrivalDate = arrival.substring(0,arrival.indexOf("T"));
        this.arrivalHour = arrival.substring(arrival.indexOf("T")+1,arrival.lastIndexOf(":")).replace(":","h");
    }

    public String getDepartureMS(int state) {
        switch (state) {
            case 1: return departureDateMS;
            case 2: return departureHourMS;
            default: break;
        }
        return "";
    }

    public void setDepartureMS(String departure) {
        this.departureDateMS = departure.substring(0,departure.indexOf("T"));
        this.departureHourMS = departure.substring(departure.indexOf("T")+1,departure.lastIndexOf(":")).replace(":","h");
    }

    public String getArrivalMS(int state) {
        switch (state) {
            case 1: return arrivalDateMS;
            case 2: return arrivalHourMS;
            default: break;
        }
        return "";
    }

    public void setArrivalMS(String arrival) {
        this.arrivalDateMS = arrival.substring(0,arrival.indexOf("T"));
        this.arrivalHourMS = arrival.substring(arrival.indexOf("T")+1,arrival.lastIndexOf(":")).replace(":","h");
    }

    public String getDuration() {
        if(getDeparture(2)!=null&&getArrival(2)!=null) {
            int departureSecs =
                    Integer.parseInt(getDeparture(2).substring(0, getDeparture(2).indexOf("h"))) * 60 * 60
                            +
                            Integer.parseInt(getDeparture(2).substring(getDeparture(2).indexOf("h") + 1, getDeparture(2).length())) * 60;

            int arrivalSecs =
                    Integer.parseInt(getArrival(2).substring(0, getArrival(2).indexOf("h"))) * 60 * 60
                            +
                            Integer.parseInt(getArrival(2).substring(getArrival(2).indexOf("h") + 1, getArrival(2).length())) * 60;

            int durationSecs = arrivalSecs - departureSecs;

            String hours = "" + (int)((durationSecs / 60) / 60);
            String minutes = "" + (int)((durationSecs / 60)-(Integer.parseInt(hours)*60));

            return hours+"h"+minutes;
        }
        else return duration;

    }

    public void setDuration(String duration) {
        this.duration = duration.substring(duration.indexOf("T")+1,duration.lastIndexOf(":")).replace(":", "h");
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
        // json asks if it was used, therefore if used is false the ticket is valid
        if(v.equals("false"))
            this.isValid = true;
        else this.isValid = false;
    }

    public String display(int state){
        switch (state){
            case 1:
                if(getFrom().length()>2||getTo().length()>2)
                    return  getDeparture(1)+"\nFrom: " + getFrom() + " (" + getDeparture(2) + ")\nTo: " + getTo() + " (" + getArrival(2) +")\n"+getArrival(1);
                else return getDeparture(1)+"\nFrom:   " + getFrom() + " (" + getDeparture(2) + ")\nTo:        " + getTo() + " (" + getArrival(2) +")\n"+getArrival(1);
            case 2: return "\uD83D\uDCB0   " + getPrice() + "€\n⌚   " + getDuration();
            case 3: return "Seat\n" + getSeat();
            case 4: return "From:   " + getFrom() + " (" + getDeparture(2) + ")\nTo:        " + getTo() + " (" + getArrival(2) +")\n\nSeat:     " + getSeat() + "\nDuration: " + getDuration();
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

    public boolean hasWaitingTime() {
        return (waitingTime!=null);
    }

}
