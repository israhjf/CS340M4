package edu.byu.model.domain;
import java.io.Serializable;

/**
 * Represents an auth token in the system
 */
public class AuthToken implements Serializable{
    public String password;
    public String timestamp;

    public AuthToken() { }

    public AuthToken(String password, String timestamp){
        this.password = password;
        this.timestamp = timestamp;
    }
}
