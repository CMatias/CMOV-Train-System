package cmov.goncalobo.trainticketsystem.Entities;

import java.io.Serializable;

public class User implements Serializable {
    private String name="";
    private String username="";
    private String password="";
    private String token="";

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
}
