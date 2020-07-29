package edu.byu.model.services.request;
import edu.byu.model.domain.User;

public class StatusRequest {
    public User author;
    public String message;
    public String postTimeStamp;

    public StatusRequest () {}

    public StatusRequest(User author, String message, String postTimestamp) {
        this.author = author;
        this.message = message;
        this.postTimeStamp = postTimestamp;
    }

    public User getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getPostTimeStamp() {
        return postTimeStamp;
    }
}
