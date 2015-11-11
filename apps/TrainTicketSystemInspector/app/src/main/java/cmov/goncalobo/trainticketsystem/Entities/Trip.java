package cmov.goncalobo.trainticketsystem.Entities;

/**
 * Created by goncalobo on 10/11/2015.
 */
public class Trip {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getDate() {
        return date.substring(0,date.indexOf("T"));
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String display(){
        return getTrain()+" "+getDate();
    }

    private String id;
    private String train;
    private String date;
}
