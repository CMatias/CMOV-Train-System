package cmov.goncalobo.trainticketsystem.Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name="";
    private String username="";
    private String password="";
    private String token="";
    ArrayList<PaymentCard> cards = new ArrayList<PaymentCard>();
    private String ID;


    public String getName() {
        return(name);
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getUsername() {
        return(username);
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public String toString() {
        return(getName());
    }

    public void setPassword(String pw) {
        this.password=pw;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void addCard(PaymentCard c){
        cards.add(c);
    }

    public PaymentCard getActiveCard(PaymentCard get){
        for(int i = 0; i<cards.size();i++)
            if(cards.get(i).isActive())
                return cards.get(i);
        return null;
    }

    public ArrayList<PaymentCard> getCards() {
        return cards;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID(){
        return ID;
    }

    public void clearCards() {
        cards.clear();
    }
}
